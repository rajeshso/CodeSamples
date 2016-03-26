package biz.tlg.javatest.dto;


import static biz.tlg.javatest.utils.Utils.LOG_FMT;

import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class is responsible for holding payment specific information.
 * 
 * @author daniel.webber
 * 
 */
public class TransactionDTO {

    //Time HH:MM:SS 
    private static final Pattern TRANSACTION_TIME_PATTERN = Pattern.compile("([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
    
    /** The type i.e. <TRANSACTION type> */
    private TransactionType transactionType;

    /** The customer present/not present flag i.e. <TRANSACTION customer> */
    private CustomerPresent customerPresent;

    /** The time of the transaction */
    private Date time = null;
    
    /** The amount of the transaction */
    private String amount = null;
    

    /**
     * The PaymentDTO Constructor.
     * 
     */
    public TransactionDTO() {

    }

 
    /**
     * {@inheritDoc}
     * 
     * @return A formatted string that displays all payment specific details for
     *         the transaction.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("Transaction Details%n");
        
        if (this.transactionType != null) {
            formatter.format(LOG_FMT, "Transaction Type:", this.transactionType.getType());
        }

        if (this.customerPresent != null) {
            formatter.format(LOG_FMT, "Customer:", this.customerPresent);
        }
        
        if (this.time != null) {
            formatter.format(LOG_FMT, "Time:", this.time);
        }
        
        if (this.amount != null) {
            formatter.format(LOG_FMT, "Amount:", this.amount);
        }
        
        /* Make Eclipse Juno happy by closing the resources */
        String builtString = formatter.toString();
        formatter.close();
        return builtString;
    }

    /**
     * 
     * @param tt
     *            The {@link TransactionType} enumerated value.
     */
    public void setTransactionType(TransactionType tt) {

        this.transactionType = tt;
    }

    /**
     * 
     * @return The {@link TransactionType} enumerated value.
     */
    public TransactionType getTransactionType() {

        return this.transactionType;
    }

    /**
     * @param customerPresent
     *            The {@link CustomerPresent} enumerated value.
     */
    public void setCustomerPresent(CustomerPresent customerPresent) {

        this.customerPresent = customerPresent;
    }

    /**
     * 
     * @return The {@link CustomerPresent} enumerated value.
     */
    public CustomerPresent getCustomerPresent() {

        return this.customerPresent;
    }
    
    /**
     * 
     * @param time
     *            The {@link Date} transaction time value.
     */
    public void setTransactionTime(Date time) {

        this.time = time;
    }

    /**
     * 
     * @return The {@link Date} transaction time.
     */
    public Date getTransactionTime() {

        return (Date) this.time.clone();
    }
    
    /**
     * 
     * @param amount
     *            The {@link String} transaction amount.
     */
    public void setTransactionAmount(String amount) {

        this.amount = amount;
    }

    /**
     * 
     * @return The {@link String} transaction amount.
     */
    public String getTransactionAmount() {

        return this.amount;
    }
    
    /**
     * 
     * @param txTime The time as HH:MM:SS, with HH from 0 to 23, MM and SS from 00 to 59
     * @throws IllegalStateException If the input does not match the pattern
     */
    public void parseAndSetTransactionTime(String txTime) throws IllegalStateException {
        Matcher matcher = TRANSACTION_TIME_PATTERN.matcher(txTime);
        if (matcher.matches()) {
        	int hour = Integer.parseInt(matcher.group(1));
        	int min = Integer.parseInt(matcher.group(2));
        	int sec = Integer.parseInt(matcher.group(3));
        	Calendar calendar = Calendar.getInstance();
        	calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, min, sec);
        	Date txdate = calendar.getTime();
        	setTransactionTime(txdate);
        }
    }
}

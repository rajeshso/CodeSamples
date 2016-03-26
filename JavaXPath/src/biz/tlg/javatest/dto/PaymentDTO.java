package biz.tlg.javatest.dto;

import static biz.tlg.javatest.utils.Utils.LOG_FMT;

import java.util.Formatter;

public class PaymentDTO {
    /** Card Details stored in the CardDTO */
	private CardDTO cardDTO;
	/** Transaction Details stored in the TransactionDTO */	
	private TransactionDTO transactionDTO;
	public PaymentDTO(CardDTO cardDTO, TransactionDTO transactionDTO) {
		this.cardDTO = cardDTO;
		this.transactionDTO = transactionDTO;
	}
    /**
     * 
     * @return The CardDTO as a {@link cardDTO}.
     */	
	public CardDTO getCardDTO() {
		return cardDTO;
	}
    /**
     * 
     * @param cardDTO
     *            The cardDTO code as a {@link cardDTO}.
     */
	public void setCardDTO(CardDTO cardDTO) {
		this.cardDTO = cardDTO;
	}
	public TransactionDTO getTransactionDTO() {
		return transactionDTO;
	}
	public void setTransactionDTO(TransactionDTO transactionDTO) {
		this.transactionDTO = transactionDTO;
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
        formatter.format("Payment Details%n");
        if (this.cardDTO != null) {
            sb.append(this.cardDTO.toString());
        }
        
        if (this.getTransactionDTO().getTransactionType() != null) {
            formatter.format(LOG_FMT, "Transaction Type:", this.getTransactionDTO().getTransactionType());
        }

        if (this.getTransactionDTO().getCustomerPresent() != null) {
            formatter.format(LOG_FMT, "Customer:", this.getTransactionDTO().getCustomerPresent());
        }
        
        if (this.getTransactionDTO().getTransactionTime() != null) {
            formatter.format(LOG_FMT, "Time:", this.getTransactionDTO().getTransactionTime() );
        }
        
        if (this.getTransactionDTO().getTransactionAmount() != null) {
            formatter.format(LOG_FMT, "Amount:", this.getTransactionDTO().getTransactionAmount());
        }
        
        /* Make Eclipse Juno happy by closing the resources */
        String builtString = formatter.toString();
        formatter.close();
        return builtString;
    }
	
}

package biz.tlg.javatest.dto;

/**
 * 
 * @author paul.manning
 * 
 */
public enum TransactionType {


    PURCHASE("purchase"), PURCHASE_N_CASHBACK("purchase_n_cashback"), CASH("cash"), REFUND("refund");

    private final String type;

    private TransactionType(String type) {

        this.type = type;
    }

    public static TransactionType getTransactionType(String type) throws IllegalArgumentException {

        for (TransactionType it : TransactionType.values()) {
            if (it.type.equalsIgnoreCase(type)) {
                return it;
            }
        }
        throw new IllegalArgumentException("Transaction type "+ type+ " is invalid");
    }

    public String getType() {

        return type;
    }

    @Override
	public String toString() {

        return type;
    }

}

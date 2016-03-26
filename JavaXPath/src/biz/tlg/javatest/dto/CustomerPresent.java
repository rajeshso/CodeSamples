package biz.tlg.javatest.dto;

public enum CustomerPresent {

    PRESENT("present"), NOT_PRESENT("not_present"), INTERNET("internet");

    private final String presence;

    private CustomerPresent(String presence) {

        this.presence = presence;
    }

    public static CustomerPresent getCustomerPresent(String presence) {

        for (CustomerPresent it : CustomerPresent.values()) {
            if (it.presence.equalsIgnoreCase(presence)) {
                return it;
            }
        }
        throw new IllegalArgumentException("CustomerPresent "+ presence+ " is invalid");
    }

    public String getPresence() {

        return this.presence;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return this.presence;
    }

}

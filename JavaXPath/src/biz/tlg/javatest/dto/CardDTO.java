package biz.tlg.javatest.dto;

import static biz.tlg.javatest.utils.Utils.LOG_FMT;
import static biz.tlg.javatest.utils.Utils.isNotNullOrEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains all card details associated with a transaction
 * 
 * @author paul.manning
 * 
 */
public class CardDTO {
    //Expiry Date YYYY-MM 
    private static final Pattern PAN_EXPIRY_DATE_PATTERN = Pattern.compile("([0-9]{4})-(0[1-9]|1[0-2])");
    private static final Pattern PAN_PATTERN = Pattern.compile("([0-9]{13,16})");
    /** The card PAN */
    private String pan = "";

    /** The end date of the card */
    private String panExpiryDate = "";

    /** The track 2 data read off the card's magnetic stripe */
    private String track2 = "";

    /** The token(s) that represents the card details */
    private Collection<TokenDTO> tokens = new ArrayList<TokenDTO>();

    /**
     * Constructor for the CardDTO when card details are keyed.
     * 
     * @param pan
     *            The PAN as a {@link String}.
     * @param end
     *            The end date as a {@link String}.
     */
    public CardDTO(String pan, String end) throws IllegalArgumentException {

        if (!isNotNullOrEmpty(pan)) {
            throw new IllegalArgumentException("PAN must not be null or empty");
        }

        if (!isNotNullOrEmpty(end)) {
            throw new IllegalArgumentException("Expiry date must not be null or empty");
        }
        
        Matcher matcher = PAN_EXPIRY_DATE_PATTERN.matcher(end); 
        if (!matcher.matches()) {
        	throw new IllegalArgumentException("Expiry date must match YYYY-MM and have valid values");
        }
        matcher = PAN_PATTERN.matcher(pan);
        if (!matcher.matches()) {
        	throw new IllegalArgumentException("PAN must be 13 to 16 numerical digits");
        }
        this.pan = pan;
        this.panExpiryDate = end;

    }

    /**
     * @return the pan
     */
    public String getPan() {

        return this.pan;
    }

    /**
     * @param pan
     *            the pan to set, must not be null
     */
    public void setPan(String pan) {

        this.pan = pan;
    }

    /**
     * @return the end
     */
    public String getExpiryDate() {

        return this.panExpiryDate;
    }

    /**
     * @param expDate
     *            the expDate to set, must not be null
     */
    public void setExpiryDate(String end) {

        this.panExpiryDate = end;
    }

    /**
     * @return the track2
     */
    public String getTrack2() {

        return this.track2;
    }

    /**
     * @param track2
     *            the track2 to set, must not be null
     */
    public void setTrack2(String track2) {

        this.track2 = track2;
    }

    /**
     * @param tokens
     *            the tokens to set
     */
    public void setTokens(Collection<TokenDTO> tokens) {

        this.tokens = tokens;
    }

    /**
     * @return the tokens
     */
    public Collection<TokenDTO> getTokens() {

        return this.tokens;
    }

    /**
     * {@inheritDoc}
     * 
     * @return A formatted string that displays all identifier details for the card details.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Card \n");
        Formatter formatter = new Formatter(sb);

        formatter.format(LOG_FMT, "PAN:", this.pan);
        formatter.format(LOG_FMT, "Expiry Date:",  this.panExpiryDate);
        formatter.format(LOG_FMT, "Track 2:",  this.track2);


        if (tokens != null && tokens.size() > 0) {
            for (TokenDTO token : tokens) {
                sb.append(token.toString());
            }
        }

        /* Make Eclipse Juno happy by closing the resources */
        String builtString = formatter.toString();
        formatter.close();
        return builtString;
    }


}

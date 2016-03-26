/**
 * 
 */
package biz.tlg.javatest.dto;

import static biz.tlg.javatest.utils.Utils.LOG_FMT;
import static biz.tlg.javatest.utils.Utils.isNotNullOrEmpty;

import java.util.Formatter;

/**
 * This class contains information about a token. The start, end and issue fields are used to override the data that is stored
 * with the token.
 * 
 * @author daniel.webber
 * 
 */
public class TokenDTO {

    /** The token that represents the card details */
    private final String token;

    /** The origin of the token (i.e. central/local) */
    private final String origin;

    /**
     * TokenDTO Constructor.
     * 
     * @param token
     *            The token that represents the card details as a {@link String} .
     * @param origin
     *            The origin of the token as a {@link TokenOrigin}.
     */
    public TokenDTO(String token, String origin) {

        if (!isNotNullOrEmpty(token)) {
            throw new IllegalArgumentException("Invalid arguments passed to token class");
        }

        if (origin == null) {
            throw new IllegalArgumentException("Origin cannot be null");
        }

        this.token = token;
        this.origin = origin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof TokenDTO) {
            TokenDTO tokenDTO = (TokenDTO) obj;
            if (!this.token.equals(tokenDTO.getToken())) {
                return false;
            }

            if (!this.origin.equals(tokenDTO.getOrigin())) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }
    @Override
    public int hashCode() {
    	  assert false : "hashCode not designed";
    	  return 1; 
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        Formatter formatter = new Formatter(new StringBuilder());
        formatter.format("Token%n");
        formatter.format(LOG_FMT, "Token:", this.token);
        formatter.format(LOG_FMT, "Origin:", this.origin);
        /* Make Eclipse Juno happy by closing the resources */
        String builtString = formatter.toString();
        formatter.close();
        return builtString;
    }



    /**
     * @return the token
     */
    public String getToken() {

        return token;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {

        return origin;
    }


}

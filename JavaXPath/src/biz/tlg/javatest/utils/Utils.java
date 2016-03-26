package biz.tlg.javatest.utils;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * 
 * Utility class with static example methods to create a document, get an Element and get an Attribute
 * 
 */
public class Utils {

    public static final String LOG_FMT = "%1$-20s %2$-30s%n";
    
    /**
     * This method determines whether a {@link String} is null or empty.
     * 
     * @param string
     *            The {@link String} to test.
     * @return true if the {@link String} is not null or empty, false otherwise.
     */
    public static boolean isNotNullOrEmpty(String string) {

        if (string != null && !string.isEmpty()) {
            return true;
        }
        return false;
    }
    
    /**
     * Utility method, converts a {@link String} representation of some configuration into a JDOM {@link Document}
     * 
     * @param input
     *            A valid {@link String} to convert to a {@link Document}, must not be null
     * 
     * @return A JDOM {@link Document} representation of the given input
     * @throws JDOMException,IOException  
     * @throws IOException
     *             If an {@link IOException} occurs
     * @throws JDOMException
     *             If a {@link JDOMException} occurs
     * 
     */
    public static Document createDocument(String input) throws JDOMException,IOException  {

        /* Validate input */
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        /* Build the Document */
        Document builtDoc = null;
        try {
            builtDoc = new SAXBuilder().build(new StringReader(input));
        } catch (JDOMException | IOException e) {
            throw e;
        }

        return builtDoc;
    }

    /**
     * Extracts the {@link Element} from the {@link XPath} and returns it.
     * 
     * @param msg
     *            The {@link Document} to examine
     * @param xPath
     *            {@link XPath} of the element
     * 
     * @return the found {@link Element} or null if the {@link Element} cannot be found
     * @throws JDOMException
     *             If a {@link JDOMException} is thrown
     */
    public static Element getElement(Document msg, String xPath) throws JDOMException {

        if (msg == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        if (xPath == null) {
            throw new IllegalArgumentException("XPath cannot be null");
        }

        return (Element) XPath.selectSingleNode(msg, xPath);

    }

    /**
     * Given an {@link XPath} to an {@link Attribute}, returns the {@link Attribute} if found or else null is returned
     * 
     * @param msg
     *            The message to get the {@link Attribute} from.
     * @param xPath
     *            The {@link XPath} of where is {@link Attribute} is located
     * @return The {@link Attribute} or null if not found
     * @throws JDOMException
     *             If a {@link JDOMException} is thrown
     */
    public static Attribute getAttribute(Document msg, String xPath) throws JDOMException {

        if (msg == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        if (xPath == null) {
            throw new IllegalArgumentException("XPath cannot be null");
        }

        /* Get the attribute from the given XPath */
        return (Attribute) XPath.selectSingleNode(msg, xPath);

    }

}

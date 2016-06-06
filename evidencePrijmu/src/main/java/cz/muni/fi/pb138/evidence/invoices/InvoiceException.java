package cz.muni.fi.pb138.evidence.invoices;

/**
 * Uniform exception for transformating invoices.
 * @author Miloš Šilhár
 */
public class InvoiceException extends RuntimeException {

    /**
     * Creates a new instance of <code>InvoiceException</code> without detail
     * message.
     */
    public InvoiceException() {
    }

    /**
     * Constructs an instance of <code>InvoiceException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvoiceException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>InvoiceException</code> with the
     * specified detail message and cause.
     * 
     * @param msg the detail message.
     * @param cause the cause of the exception.
     */
    public InvoiceException(String msg, Exception cause) {
        super(msg, cause);
    }
}

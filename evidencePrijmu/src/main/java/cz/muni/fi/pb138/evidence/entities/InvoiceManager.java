package cz.muni.fi.pb138.evidence.entities;

/**
 *
 * @author L
 */
public interface InvoiceManager {
    
    public void createInvoice(Invoice invoice);
    public Invoice getInvoiceById(long id);
    
}

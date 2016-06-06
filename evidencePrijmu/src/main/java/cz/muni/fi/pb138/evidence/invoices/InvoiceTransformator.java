package cz.muni.fi.pb138.evidence.invoices;

import cz.muni.fi.pb138.evidence.entities.Invoice;
import java.io.File;

/**
 * Transform given invoice into various formats.
 * Currently only docbook and pdf are supported.
 * @author Miloš Šilhár
 */
public interface InvoiceTransformator {
    
    /**
     * Transforms and stores invoice in docbook format in exist-db.
     * @param invoice invoice to store.
     */
    void storeInvoiceDocbook(Invoice invoice);
    
    /**
     * Transforms and stores invoice in PDF format in filesystem.
     * @param invoice invoice to store.
     */
    void storeInvoicePdf(Invoice invoice, File f);
    
    /**
     * Gets url to invoice stored in exist-db. Then only redirect is required.
     * @param invoice Invoice to get URL for.
     * @return Url to invoice docbook resource.
     */
    String getInvoiceDocbookUrl(Invoice invoice);
    
    /**
     * Transforms invoice into docbook content and returns it.
     * @param invoice Invoice which transform to docbook.
     * @return XML formatted string with docbook content for given invoice.
     */
    String transformToDocbook(Invoice invoice);
    
    /**
     * Transforms invoice into PDF content and returns it.
     * @param invoice Invoice which transform to PDF.
     * @return PDF formatted byte array with content from given invoice.
     */
    byte[] transformToPdf(Invoice invoice);
    
}

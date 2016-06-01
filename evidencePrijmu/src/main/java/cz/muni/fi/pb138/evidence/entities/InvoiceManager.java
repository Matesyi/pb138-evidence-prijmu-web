package cz.muni.fi.pb138.evidence.entities;

import java.util.List;

/**
 *
 * @author L
 */
public interface InvoiceManager {
    
    public void createInvoice(Invoice invoice);
    public Invoice getInvoiceById(long id);
    public List<Invoice> findInvoicesByFilter(int employeeId, String surname, 
            int yearFrom, int monthFrom, int yearTo, int monthTo);
    
}

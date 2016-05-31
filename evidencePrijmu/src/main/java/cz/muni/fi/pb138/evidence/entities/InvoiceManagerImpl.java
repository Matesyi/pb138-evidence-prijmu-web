package cz.muni.fi.pb138.evidence.entities;

import cz.muni.fi.pb138.evidence.xmlEdit.ExecuteQuery;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.base.XMLDBException;

/**
 *
 * @author L
 */
public class InvoiceManagerImpl implements InvoiceManager {

    //Submethod used in createInvoice() to get ID. Next ID is stored in xml file. This method gets
    //this ID and updates value in xml file (autoincrement).
    public static long countID() {
        String query = "for $x in /root/invoices/nextID return $x/text()";
        long currentID;
        try {
            currentID = Long.parseLong(ExecuteQuery.executeQuery(query));
            long newID = currentID + 1;
            String updateQuery = "update value /root/invoices/nextID with \"" + newID + "\"";
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.INFO, updateQuery);
            ExecuteQuery.executeQuery(updateQuery);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException | NumberFormatException ex) {
            String msg = "Error when manipulating invoiceID";
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
        return currentID;
    }
    
    @Override
    public void createInvoice(Invoice invoice) {
        invoice.setId(countID());
        //String works is later used as substring of main query
        String works = "";
        for (Map.Entry<Work, Integer> entry: invoice.getWorks().entrySet()) {
            works += "<work> <id>" + entry.getKey().getWork_id() + "</id>\n"
                    + "<type>" + entry.getKey().getWork_type()+ "</type>\n"
                    + "<price>" + entry.getKey().getPrice()+ "</price>\n"
                    + "<amount>" + entry.getValue() + "</amount> </work>";
        }         
        String query = "update insert <invoice> \n"
                + "<id>" + invoice.getId()+ "</id>\n"
                + "<employer>" + invoice.getEmployer() + "</employer>\n"
                + "<employee> <id>" + invoice.getEmployee().getPersonal_number() + "</id>\n"
                    + "<name>" + invoice.getEmployee().getName() + "</name>"
                    + "<surname>" + invoice.getEmployee().getSurname() + "</surname>"
                    + "<address>" + invoice.getEmployee().getAddress()+ "</address>"
                    + "<postCode>" + invoice.getEmployee().getPostCode()+ "</postCode>"
                    + "<city>" + invoice.getEmployee().getCity() + "</city></employee>"
                //employee attribute 'active' is not copied
                + "<yearMonth>" + invoice.getYear()+ invoice.getMonth() + "</yearMonth>\n"
                + "<works>" + works + "</works>\n"
                + "</invoice> \n"
                + "into /root/invoices";
        Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.INFO, query);
        try {
            ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when storing invoice to database";
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
    }

    @Override
    public Invoice getInvoiceById(long id) {
//        Invoice invoice = new Invoice();
//        String query = "for $p in /root/invoices/invoice where "
//                + "($p/id = " + Long.toString(id) +  ") return "
//                + "(data($p/id), \";\", data($p/employee/id), \";\", "
//                + "data($p/employee/name), \";\", data($p/employee/surname), \";\" ,"
//                + "data($p/employee/address), \";\" ,"
//                + "data($p/employee/postCode), \";\" ,data($p/employee/city))";
//        String result = "";
//        try {
//            result = ExecuteQuery.executeQuery(query);
//        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
//            String msg = "Error when retrieving invoice by ID from database";
//            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
//        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
//    public void tryQuery() {
//        String query = "for $p in /root/invoices/invoice where ($p/id = \"2\") return $p/works/work";
//        String result = "";
//        try {
//            result = ExecuteQuery.executeQuery(query);
//        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
//            String msg = "Error when retrieving invoice by ID from database";
//            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
//        }
////        String worksQuery = "for $p in /root/invoices/invoice where ($p/id = \"2\") return ($p/works/work/id, \";\", $p/works/work/amount, \":\")";
//        System.out.println(result);
//    }

}

package cz.muni.fi.pb138.evidence.entities;

import cz.muni.fi.pb138.evidence.xmlEdit.ExecuteQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xmldb.api.base.XMLDBException;

/**
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
        for (Map.Entry<Work, Integer> entry : invoice.getWorks().entrySet()) {
            works += "<work> <id>" + entry.getKey().getWork_id() + "</id>\n"
                    + "<type>" + entry.getKey().getWork_type() + "</type>\n"
                    + "<price>" + entry.getKey().getPrice() + "</price>\n"
                    + "<amount>" + entry.getValue() + "</amount> </work>";
        }
        String query = "update insert <invoice> \n"
                + "<id>" + invoice.getId() + "</id>\n"
                + "<employer>" + invoice.getEmployer() + "</employer>\n"
                + "<employee> <id>" + invoice.getEmployee().getPersonal_number() + "</id>\n"
                + "<name>" + invoice.getEmployee().getName() + "</name>"
                + "<surname>" + invoice.getEmployee().getSurname() + "</surname>"
                + "<address>" + invoice.getEmployee().getAddress() + "</address>"
                + "<postCode>" + invoice.getEmployee().getPostCode() + "</postCode>"
                + "<city>" + invoice.getEmployee().getCity() + "</city></employee>"
                //employee attribute 'active' is not copied
                + "<yearMonth>" + invoice.getYear() + invoice.getMonth() + "</yearMonth>\n"
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
        Invoice invoice = new Invoice();
        //finds all values except works
        String query = "for $p in /root/invoices/invoice where "
                + "($p/id = " + Long.toString(id) + ") return "
                + "(data($p/id), \";\", data($p/employee/id), \";\", "
                + "data($p/employee/name), \";\", data($p/employee/surname), \";\", "
                + "data($p/employee/address), \";\", "
                + "data($p/employee/postCode), \";\", data($p/employee/city), \";\", "
                + "data($p/employer), \";\", data($p/yearMonth))";
        String resultA = "";
        //finds works
        String queryW = "for $p in /root/invoices/invoice where ($p/id = " + Long.toString(id) + ") return "
                + "for $w in $p/works/work return (data($w/id), \";\",data($w/type), \";\", "
                + "data($w/price), \";\",data($w/amount), \"SeparatorA\")";
        String resultW = "";
        try {
            resultA = ExecuteQuery.executeQuery(query);
            resultW = ExecuteQuery.executeQuery(queryW);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when retrieving invoice by ID from database";
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
        }
        invoice = resultToInvoiceA(resultA, invoice);
        invoice = resultToInvoiceW(resultW, invoice);
        return invoice;
    }

    //Filter values are optional. 
    //But if all of them are empty, result is an empty List.
    //Month must be in format mm, i.e. June is 06 not 6.
    @Override
    public List<Invoice> findInvoicesByFilter(int employeeId, String surname, int yearFrom, int monthFrom, int yearTo, int monthTo) {
        List<Invoice> result = new ArrayList<>();
        String condition = "where (";
        if (employeeId != 0) {
            condition += "$p/employee/id = " + Integer.toString(employeeId);
        }
        if (surname != null) {
            if (!condition.endsWith("(")) {
                condition += " and ";
            }
            condition += "$p/employee/surname = " + "\"" + surname + "\"";
        }
        if (yearFrom != 0) {
            if (!condition.endsWith("(")) {
                condition += " and ";
            }
            String yearMonth = Integer.toString(yearFrom) + Integer.toString(monthFrom);
            condition += "$p/yearMonth >= " + yearMonth;
        }
        if (yearTo != 0) {
            if (!condition.endsWith("(")) {
                condition += " and ";
            }
            String yearMonth = Integer.toString(yearTo) + Integer.toString(monthTo);
            condition += "$p/yearMonth <= " + yearMonth;
        }
        condition += ")";
//        System.out.println(condition);
        //finds all values except works
        String query = "for $p in /root/invoices/invoice " + condition + " return "
                + "(\"SeparatorB\", data($p/id), \";\", data($p/employee/id), \";\", "
                + "data($p/employee/name), \";\", data($p/employee/surname), \";\", "
                + "data($p/employee/address), \";\", "
                + "data($p/employee/postCode), \";\", data($p/employee/city), \";\", "
                + "data($p/employer), \";\", data($p/yearMonth))";
        String resultA = "";
        //finds works
        String queryW = "for $p in /root/invoices/invoice " + condition + " return "
                + "(\"SeparatorB\", for $w in $p/works/work return (data($w/id), \";\",data($w/type), \";\", "
                + "data($w/price), \";\",data($w/amount), \"SeparatorA\"))";
        String resultW = "";
        try {
            resultA = ExecuteQuery.executeQuery(query);
            resultW = ExecuteQuery.executeQuery(queryW);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when retrieving invoice by filter from database";
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
        }
        String[] arrayA = resultA.split("SeparatorB");
        String[] arrayW = resultW.split("SeparatorB");
        //Iteration from 1., not from 0. element (0. is empty String).
        //Both arrays should have the same length.
        for (int i = 1; i < arrayA.length; i++) {
            Invoice invoice = new Invoice();
            invoice = resultToInvoiceA(arrayA[i], invoice);
            invoice = resultToInvoiceW(arrayW[i], invoice);
            result.add(invoice);
        }
        return result;
    }

    //fills invoice with all attribute values except works
    private Invoice resultToInvoiceA(String result, Invoice invoice) {
        String[] resultArray = result.split(";");
        Employee employee = new Employee();
        employee.setPersonal_number(Integer.parseInt(resultArray[1]));
        employee.setName(resultArray[2]);
        employee.setSurname(resultArray[3]);
        employee.setAddress(resultArray[4]);
        employee.setPostCode(Integer.parseInt(resultArray[5]));
        employee.setCity(resultArray[6]);
        invoice.setEmployee(employee);
        invoice.setId(Long.parseLong(resultArray[0]));
        invoice.setEmployer(resultArray[7]);
        invoice.setYear(Integer.parseInt(resultArray[8].substring(0, 4)));
        invoice.setMonth(Integer.parseInt(resultArray[8].substring(4)));
        return invoice;
    }

    //fills invoice with works attribute
    private Invoice resultToInvoiceW(String resultW, Invoice invoice) {
        Map<Work, Integer> works = new HashMap<>();
        String[] worksArray = resultW.split("SeparatorA");
        for (int i = 0; i < worksArray.length; i++) {
            Work work = new Work();
            String[] workArray = worksArray[i].split(";");
            work.setWork_id(Integer.parseInt(workArray[0]));
            work.setWork_type(workArray[1]);
            work.setPrice(Integer.parseInt(workArray[2]));
            works.put(work, Integer.parseInt(workArray[3]));
        }
        invoice.setWorks(works);
        return invoice;
    }
}

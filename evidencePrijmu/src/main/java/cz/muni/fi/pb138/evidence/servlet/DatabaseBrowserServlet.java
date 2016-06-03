package cz.muni.fi.pb138.evidence.servlet;

import com.google.gson.Gson;
import cz.muni.fi.pb138.evidence.entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lukas on 28.5.16.
 * Handle incoming request to database browser.
 */
public class DatabaseBrowserServlet extends HttpServlet {

    /**
     * @param req  data from java server page
     * @param resp response to java server page
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        InvoiceManagerImpl invoiceManager = new InvoiceManagerImpl();
        // show all invoices
        if (url.equals("/database-browser")) {
            req.setAttribute("employeesJson", getActiveEmployeesJson());
            List<Invoice> invoices = invoiceManager.findInvoicesByFilter(0, null, 1950, 0, 3000, 0);
            req.setAttribute("invoicesJson", getInvoicesJson(invoices));
            RequestDispatcher view = req.getRequestDispatcher("/database-browser.jsp");
            view.forward(req, resp);

        } else {
            // show request invoice detail
            String urlParts[] = url.split("/", 3);
            if (urlParts[1].equals("invoice-detail")) {
                Invoice invoice = invoiceManager.getInvoiceById(Integer.parseInt(urlParts[2]));
                JSONArray invoiceJsonArray = new JSONArray();
                JSONObject object1 = new JSONObject();
                object1.put("personal_number", invoice.getEmployee().getPersonal_number());
                object1.put("name", invoice.getEmployee().getName());
                object1.put("surname", invoice.getEmployee().getSurname());
                object1.put("address", invoice.getEmployee().getAddress());
                object1.put("post_code", invoice.getEmployee().getPostCode());
                object1.put("city", invoice.getEmployee().getCity());
                invoiceJsonArray.add(object1);
                JSONObject object2 = new JSONObject();
                object2.put("invoice_id", invoice.getId());
                object2.put("date", invoice.getMonth() + "/" + invoice.getYear());
                int priceSum = 0;
                for (Map.Entry<Work, Integer> entry : invoice.getWorks().entrySet()) {
                    priceSum += entry.getValue() * entry.getKey().getPrice();
                }
                object2.put("price", priceSum);
                object2.putAll(invoice.getWorks());
                invoiceJsonArray.add(object2);
                req.setAttribute("invoiceJson", invoiceJsonArray);
                RequestDispatcher view = req.getRequestDispatcher("/invoice-detail.jsp");
                view.forward(req, resp);
            }
        }
    }

    /**
     * @param req  data from java server page
     * @param resp response to java server page
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employeesJson", getActiveEmployeesJson());
        String personalNumberString = req.getParameter("employee");
        int personalNumber = 0;
        if (personalNumberString != null)
            personalNumber = Integer.parseInt(personalNumberString);
        String surname = req.getParameter("employee_surname");

        String dateFromInput = req.getParameter("date_from");
        int yearFrom = 1000;
        int monthFrom = 0;
        if (!dateFromInput.isEmpty()) {
            String dateFrom[] = dateFromInput.split("/");
            yearFrom = Integer.parseInt(dateFrom[0]);
            monthFrom = Integer.parseInt(dateFrom[1]);
        }
        String dateToInput = req.getParameter("date_to");
        int yearTo = 99999;
        int monthTo = 0;
        if (!dateToInput.isEmpty()) {
            String dateTo[] = dateFromInput.split("/");
            yearTo = Integer.parseInt(dateTo[0]);
            monthTo = Integer.parseInt(dateTo[1]);
        }
        InvoiceManagerImpl invoiceManager = new InvoiceManagerImpl();
        List<Invoice> invoices = invoiceManager.findInvoicesByFilter(personalNumber, surname, yearFrom, monthFrom, yearTo, monthTo);
        req.setAttribute("invoicesJson", getInvoicesJson(invoices));
        RequestDispatcher view = req.getRequestDispatcher("/database-browser.jsp");
        view.forward(req, resp);
    }

    /**
     * Create json string containing all active Employees
     *
     * @return json String
     */
    private String getActiveEmployeesJson() {
        EmployeeManagerImpl employeeManager = new EmployeeManagerImpl();
        List<Employee> employees = employeeManager.findActiveEmployees();
        return new Gson().toJson(employees);
    }


    /**
     * Return all invoices formatted in JSONArray
     *
     * @param invoices
     * @return JSONArray invoices
     */
    private JSONArray getInvoicesJson(List<Invoice> invoices) {
        JSONArray invoicesJsonArray = new JSONArray();
        for (Invoice invoice : invoices) {
            JSONObject object = new JSONObject();
            object.put("id", invoice.getId());
            object.put("employee", invoice.getEmployee().getName() + " " + invoice.getEmployee().getSurname());
            object.put("date", invoice.getMonth() + "/" + invoice.getYear());
            int priceSum = 0;
            for (Map.Entry<Work, Integer> entry : invoice.getWorks().entrySet()) {
                priceSum += entry.getValue() * entry.getKey().getPrice();
            }
            object.put("price", priceSum);
            invoicesJsonArray.add(object);
        }
        return invoicesJsonArray;
    }
}

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
 */
public class DatabaseBrowserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeManagerImpl employeeManager = new EmployeeManagerImpl();
        List<Employee> employees = employeeManager.findActiveEmployees();
        String employeesJson = new Gson().toJson(employees);
        request.setAttribute("employeesJson", employeesJson);
        InvoiceManagerImpl invoiceManager = new InvoiceManagerImpl();
        List<Invoice> invoices = invoiceManager.findInvoicesByFilter(0, null, 1950, 0, 3000, 0);
        JSONArray invoicesJsonArray = new JSONArray();
        for (Invoice invoice : invoices) {
            JSONObject object = new JSONObject();
            object.put("employee", invoice.getEmployee().getName() + " " + invoice.getEmployee().getSurname());
            object.put("date", invoice.getMonth() + "/" + invoice.getYear());
            int priceSum = 0;
            for (Map.Entry<Work, Integer> entry : invoice.getWorks().entrySet()) {
                priceSum += entry.getValue()*entry.getKey().getPrice();
            }
            object.put("price", priceSum);
            invoicesJsonArray.add(object);
        }
        request.setAttribute("invoicesJson", invoicesJsonArray);
        RequestDispatcher view = request.getRequestDispatcher("/database-browser.jsp");
        view.forward(request, response);
    }
}

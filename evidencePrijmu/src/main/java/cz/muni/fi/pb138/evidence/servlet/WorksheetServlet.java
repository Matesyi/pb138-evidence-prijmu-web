package cz.muni.fi.pb138.evidence.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import cz.muni.fi.pb138.evidence.entities.*;
import cz.muni.fi.pb138.evidence.entities.Employee;

/**
 * Created by lukas on 25.5.16.
 */
public class WorksheetServlet extends HttpServlet {

    /**
     * @param req  data from java server page
     * @param resp response to java server page
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //  create manager
        EmployeeManagerImpl employeeManager = new EmployeeManagerImpl();
        //  get all active employees
        List<Employee> employees = employeeManager.findActiveEmployees();
        //  create json from List
        String employeesJson = new Gson().toJson(employees);
        //  pass employeesJson to view
        req.setAttribute("employeesJson", employeesJson);

        WorkManagerImpl workManager = new WorkManagerImpl();
        List<Work> works = workManager.findAllWorks();
        String worksJson = new Gson().toJson(works);
        req.setAttribute("worksJson", worksJson);

        // create view with worksheet.jsp template
        RequestDispatcher view = req.getRequestDispatcher("/worksheet.jsp");
        // go to template with variables
        view.forward(req, resp);
    }

    /**
     * @param req  data from java server page
     * @param resp response to java server page
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        switch (url) {
            case "/worksheet/create":
                Invoice invoice = new Invoice();
                EmployeeManagerImpl employeeManager = new EmployeeManagerImpl();
                Employee employee = employeeManager.getEmployeeById(Integer.parseInt(req.getParameter("employee")));
                invoice.setEmployee(employee);
                invoice.setEmployer("Microsoft");
                String date = req.getParameter("date");
                String dateParts[] = date.split("/");
                invoice.setYear(Integer.parseInt(dateParts[0]));
                invoice.setMonth(Integer.parseInt(dateParts[1]));
                Map<Work, Integer> worksMap = new HashMap<>();
                int workCount = Integer.parseInt(req.getParameter("workCount"));
                WorkManagerImpl workManager = new WorkManagerImpl();
                // get all works from page
                for (int i = 0; i < workCount; i++) {
                    int workId = Integer.parseInt(req.getParameter("work_type-" + Integer.toString(i + 1)));
                    Work work = workManager.getWorkById(workId);
                    int sumHours = 0;
                    if (worksMap.containsKey(work)) {
                        int hoursOld = worksMap.get(work);
                        sumHours = hoursOld + Integer.parseInt(req.getParameter("work_amount-" + Integer.toString(i + 1)));
                    } else {
                        sumHours = Integer.parseInt(req.getParameter("work_amount-" + Integer.toString(i + 1)));
                    }
                    worksMap.put(work, sumHours);
                }
                invoice.setWorks(worksMap);
                InvoiceManagerImpl invoiceManager = new InvoiceManagerImpl();
                invoiceManager.createInvoice(invoice);
        }
        resp.sendRedirect("/worksheet");
    }

}
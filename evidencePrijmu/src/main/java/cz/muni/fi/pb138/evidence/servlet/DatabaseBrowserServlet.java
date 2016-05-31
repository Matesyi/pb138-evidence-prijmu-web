package cz.muni.fi.pb138.evidence.servlet;

import com.google.gson.Gson;
import cz.muni.fi.pb138.evidence.entities.Employee;
import cz.muni.fi.pb138.evidence.entities.EmployeeManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lukas on 28.5.16.
 */
public class DatabaseBrowserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeManagerImpl employeeManager = new EmployeeManagerImpl();
        List<Employee> employees = employeeManager.findActiveEmployees();
        String employeesJson = new Gson().toJson(employees);
        request.setAttribute("employeesJson", employeesJson);
        RequestDispatcher view = request.getRequestDispatcher("/database-browser.jsp");
        view.forward(request, response);
    }
}

package cz.muni.fi.pb138.evidence.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import cz.muni.fi.pb138.evidence.entities.*;
import cz.muni.fi.pb138.evidence.entities.Employee;

/**
 * Created by lukas on 25.5.16.
 */
public class WorksheetServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  create manager
        EmployeeManagerImpl employeeManager = new EmployeeManagerImpl();
        //  get all active employees
        List<Employee> employees = employeeManager.findActiveEmployees();
        //  create json from List
        String employeesJson = new Gson().toJson(employees);
        //  pass employeesJson to view
        request.setAttribute("employeesJson", employeesJson);
        // create view with worksheet.jsp template
        RequestDispatcher view = request.getRequestDispatcher("/worksheet.jsp");
        // go to template with variables
        view.forward(request, response);
    }

}
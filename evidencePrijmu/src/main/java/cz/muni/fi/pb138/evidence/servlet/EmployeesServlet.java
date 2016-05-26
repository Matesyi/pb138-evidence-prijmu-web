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
public class EmployeesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        EmployeeManagerImpl employeeManager = new EmployeeManagerImpl();
        switch (url) {
            case "/employees":
                List<Employee> employees = employeeManager.findActiveEmployees();
                String employeesJson = new Gson().toJson(employees);
                req.setAttribute("employeesJson", employeesJson);
                RequestDispatcher viewEmployees = req.getRequestDispatcher("/employees.jsp");
                viewEmployees.forward(req, resp);
                break;
            case "/employee":
                RequestDispatcher viewEmployee = req.getRequestDispatcher("/employee.jsp");
                viewEmployee.forward(req, resp);
                break;
            default:
                String urlParts[] = url.split("/", 4);
                Employee employee = employeeManager.getEmployeeById(Integer.parseInt(urlParts[3]));
                employeeManager.deleteEmployee(employee);
                resp.sendRedirect("/employees");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        switch (url) {
            case "/employee/create":
                Employee employee = new Employee();

                employee.setName(req.getParameter("name"));
                employee.setSurname(req.getParameter("surname"));
                employee.setAddress(req.getParameter("address"));
                employee.setCity(req.getParameter("city"));
                employee.setPostCode(Integer.parseInt(req.getParameter("post_code")));
                employee.setActive(true);
                EmployeeManagerImpl employeeManager = new EmployeeManagerImpl();
                if (req.getParameter("personal_number").isEmpty()) {
                    employeeManager.createEmployee(employee);
                } else {
                    employee.setPersonal_number(Integer.parseInt(req.getParameter("personal_number")));
                    employeeManager.updateEmployee(employee);
                }
        }
        resp.sendRedirect("/employees");
    }
}
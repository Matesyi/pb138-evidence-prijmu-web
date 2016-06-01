package cz.muni.fi.pb138.evidence.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.base.XMLDBException;
import cz.muni.fi.pb138.evidence.xmlEdit.ExecuteQuery;

/**
 * Implementation of CRUD methods for entity Employee. Uses extended XQuery from
 * eXist database.
 *
 * @author Lumir
 */
public class EmployeeManagerImpl implements EmployeeManager {

    //Submethod used in createEmployee() to get ID. Next ID is stored in xml file. This method gets
    //this ID and updates value in xml file (autoincrement).
    public static int countID() {
        String query = "for $x in /root/employees/nextID return $x/text()";
        int currentID;
        try {
            currentID = Integer.parseInt(ExecuteQuery.executeQuery(query));
            int newID = currentID + 1;
            String updateQuery = "update value /root/employees/nextID with \"" + newID + "\"";
            Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.INFO, updateQuery);
            ExecuteQuery.executeQuery(updateQuery);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException | NumberFormatException ex) {
            String msg = "Error when manipulating employeeID";
            Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
        return currentID;
    }

    //Stores new employee to xml database.
    @Override
    public void createEmployee(Employee employee) {
        employee.setPersonal_number(countID());
        String query = "update insert <employee>\n"
                + "<id>" + employee.getPersonal_number() + "</id>\n"
                + "<name>" + employee.getName() + "</name>\n"
                + "<surname>" + employee.getSurname() + "</surname>\n"
                + "<address>" + employee.getAddress() + "</address>\n"
                + "<postCode>" + employee.getPostCode() + "</postCode>\n"
                + "<city>" + employee.getCity() + "</city>\n"
                + "<active>" + employee.isActive() + "</active>\n"
                + "</employee>\n"
                + "into /root/employees";
        Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.INFO, query);
        try {
            ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when storing employee to database";
            Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
    }

    //Chooses an employee by id. Id remains, other values are updated
    //from values of object given in parameter.
    @Override
    public void updateEmployee(Employee employee) {
        int employeeID = employee.getPersonal_number();
        String query = "update replace /root/employees/employee[./id = \""
                + employeeID + "\"] with <employee>\n"
                + "<id>" + employee.getPersonal_number() + "</id>\n"
                + "<name>" + employee.getName() + "</name>\n"
                + "<surname>" + employee.getSurname() + "</surname>\n"
                + "<address>" + employee.getAddress() + "</address>\n"
                + "<postCode>" + employee.getPostCode() + "</postCode>\n"
                + "<city>" + employee.getCity() + "</city>\n"
                + "<active>" + employee.isActive() + "</active>\n"
                + "</employee>";
        Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.INFO, query);
        try {
            ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when updating employee in database";
            Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
    }

    //If employee with given id is not in database, it runs normally but nothing is deleted.
    @Override
    public void deleteEmployee(Employee employee) {
        int employeeID = employee.getPersonal_number();
        String query = "update delete /root/employees/employee[./id = \"" + employeeID + "\"]";
        Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.INFO, query);
        try {
            ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when deleting employee from database";
            Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
    }

    @Override
    public void setEmployeeInactive(Employee employee) {
        int employeeID = employee.getPersonal_number();
        String query = "update replace /root/employees/employee[./id = \""
                + employeeID + "\"] with <employee>\n"
                + "<id>" + employee.getPersonal_number() + "</id>\n"
                + "<name>" + employee.getName() + "</name>\n"
                + "<surname>" + employee.getSurname() + "</surname>\n"
                + "<address>" + employee.getAddress() + "</address>\n"
                + "<postCode>" + employee.getPostCode() + "</postCode>\n"
                + "<city>" + employee.getCity() + "</city>\n"
                + "<active>" + false + "</active>\n"
                + "</employee>";
        Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.INFO, query);
        try {
            ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when deleting employee from database";
            Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
    }

    @Override
    public void setEmployeeActive(Employee employee) {
        int employeeID = employee.getPersonal_number();
        String query = "update replace /root/employees/employee[./id = \""
                + employeeID + "\"] with <employee>\n"
                + "<id>" + employee.getPersonal_number() + "</id>\n"
                + "<name>" + employee.getName() + "</name>\n"
                + "<surname>" + employee.getSurname() + "</surname>\n"
                + "<address>" + employee.getAddress() + "</address>\n"
                + "<postCode>" + employee.getPostCode() + "</postCode>\n"
                + "<city>" + employee.getCity() + "</city>\n"
                + "<active>" + true + "</active>\n"
                + "</employee>";
        Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.INFO, query);
        try {
            ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when deleting employee from database";
            Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
    }

    @Override
    public Employee getEmployeeById(int id) {
        String query = "for $p in /root/employees/employee where ($p/id) = \""
                + Integer.toString(id) + "\" return (data($p/id), \";\" , data($p/name), \";\""
                + ", data($p/surname), \";\" ,data($p/address), \";\" ,"
                + "data($p/postCode), \";\" ,data($p/city), \";\" ,data($p/active))";
        String result = "";
        try {
            result = ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when retrieving employee by ID from database";
            Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
        }
//        System.out.println(result);
        return resultToEmployee(result);
    }

    @Override
    public List<Employee> findActiveEmployees() {
        List<Employee> result = new ArrayList<>();
        String query = "for $p in /root/employees/employee where ($p/active) = \"true\""
                + " return (\"SeparatorA\", data($p/id), \";\" , data($p/name), \";\""
                + ", data($p/surname), \";\" ,data($p/address), \";\" ,"
                + "data($p/postCode), \";\" ,data($p/city), \";\" ,data($p/active))";
        String resultStr = "";
        try {
            resultStr = ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when retrieving all active employees from database";
            Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
        }
        String[] resultArray = resultStr.split("SeparatorA");
        //Iteration from 1., not from 0. element (0. is empty String)
        for (int i = 1; i < resultArray.length; i++) {
            result.add(resultToEmployee(resultArray[i]));
        }
//        System.out.println(result);
        return result;
    }

    @Override
    public List<Employee> findInactiveEmplyees() {
        List<Employee> result = new ArrayList<>();
        String query = "for $p in /root/employees/employee where ($p/active) = \"false\""
                + " return (\"SeparatorA\", data($p/id), \";\" , data($p/name), \";\""
                + ", data($p/surname), \";\" ,data($p/address), \";\" ,"
                + "data($p/postCode), \";\" ,data($p/city), \";\" ,data($p/active))";
        String resultStr = "";
        try {
            resultStr = ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when retrieving all active employees from database";
            Logger.getLogger(EmployeeManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
        }
        String[] resultArray = resultStr.split("SeparatorA");
        //Iteration from 1., not from 0. element (0. is empty String)
        for (int i = 1; i < resultArray.length; i++) {
            result.add(resultToEmployee(resultArray[i]));
        }
//        System.out.println(result);
        return result;
    }

    public Employee resultToEmployee(String result) {
        Employee employee = new Employee();
        String[] resultArray = result.split(";");
        employee.setPersonal_number(Integer.parseInt(resultArray[0]));
        employee.setName(resultArray[1]);
        employee.setSurname(resultArray[2]);
        employee.setAddress(resultArray[3]);
        employee.setPostCode(Integer.parseInt(resultArray[4]));
        employee.setCity(resultArray[5]);
        employee.setActive(Boolean.parseBoolean(resultArray[6]));
//        System.out.println(employee.toString());
        return employee;
    }
}

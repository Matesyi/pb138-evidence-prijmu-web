package entities;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.base.XMLDBException;
import xmlEdit.ExecuteQuery;

/**
 * Implementation of CRUD methods for entity Employee.
 * Uses extended XQuery from eXist database.
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
        employee.setId(countID());
        String query = "update insert <employee>\n"
                + "<id>" + employee.getId() + "</id>\n"
                + "<name>" + employee.getName()+ "</name>\n"
                + "<surname>" + employee.getSurname() + "</surname>\n"
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
        int employeeID = employee.getId();
        String query = "update replace /root/employees/employee[./id = \"" 
                + employeeID + "\"] with <employee>\n"
                + "<id>" + employee.getId() + "</id>\n"
                + "<name>" + employee.getName()+ "</name>\n"
                + "<surname>" + employee.getSurname() + "</surname>\n"
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
        int employeeID = employee.getId();
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

}

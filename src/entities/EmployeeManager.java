package entities;

/**
 *
 * @author L
 */
public interface EmployeeManager {
    
    //These methods stores changes to xml file.  
    public void createEmployee(Employee employee);
    public void updateEmployee(Employee employee);
    public void deleteEmployee(Employee employee);
    
}

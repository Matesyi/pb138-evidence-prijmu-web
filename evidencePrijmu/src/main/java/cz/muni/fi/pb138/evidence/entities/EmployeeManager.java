package cz.muni.fi.pb138.evidence.entities;

import java.util.List;

/**
 *
 * @author L
 */
public interface EmployeeManager {
    
    //These methods store changes to xml file.  
    public void createEmployee(Employee employee);
    public void updateEmployee(Employee employee);
    public void deleteEmployee(Employee employee);
    public void setEmployeeInactive(Employee employee);
    public void setEmployeeActive(Employee employee);
    //These methods retrieve data from xml file.
    public Employee getEmployeeById (int id);
    public List<Employee> findActiveEmployees();
    public List<Employee> findInactiveEmplyees();
    
}

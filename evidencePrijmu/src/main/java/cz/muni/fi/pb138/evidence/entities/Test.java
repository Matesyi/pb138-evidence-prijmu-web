package cz.muni.fi.pb138.evidence.entities;

/**
 * This class serves for testing only
 * @author Lumir
 */
public class Test {
    
    private static Employee exampleEmployee1() {
        Employee employee = new Employee();
        employee.setName("Pavel");
        employee.setSurname("Novák");
        employee.setAddress("Pěkná Ulice 4");
        employee.setPostCode(11002);
        employee.setCity("Brno");
        return employee;
    }
    
    private static Employee exampleEmployee2() {
        Employee employee = new Employee();
        employee.setName("Jan");
        employee.setSurname("Novotný");
        return employee;
    }
    
    private static Employee exampleEmployeeUpdate() {
        Employee employee = new Employee();
        employee.setName("Unknown");
        employee.setSurname("Anonymus");
        employee.setPersonal_number(10);//choose some number according to database content
        return employee;
    }
    
        private static Employee exampleEmployeeDelete() {
        Employee employee = new Employee();
        employee.setName("Unknown");
        employee.setSurname("Anonymus");
         employee.setAddress("Pěkná Ulice 4");
        employee.setPostCode(11002);
        employee.setCity("Brno");
        employee.setPersonal_number(3);//choose some number according to database content
        return employee;
    }
    
    private static EmployeeManagerImpl employeeManager;
    
    public static void main(String[] args) {
//        int newID = EmployeeManagerImpl.countID();
        employeeManager = new EmployeeManagerImpl();
//        employeeManager.createEmployee(exampleEmployee1());
//        employeeManager.setEmployeeActive(exampleEmployeeDelete());
//        employeeManager.deleteEmployee(exampleEmployeeDelete());
//        employeeManager.updateEmployee(exampleEmployeeUpdate());
//        employeeManager.getEmployeeById(3);
        System.out.println("Active: " + employeeManager.findActiveEmployees());
        System.out.println("Inactive" + employeeManager.findInactiveEmplyees());
    }
}

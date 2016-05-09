package entities;

/**
 * This class serves for testing only
 * @author Lumir
 */
public class Test {
    
    private static Employee exampleEmployee1() {
        Employee employee = new Employee();
        employee.setName("Pavel");
        employee.setSurname("Novák");
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
        employee.setId(10);//choose some number according to database content
        return employee;
    }
    
        private static Employee exampleEmployeeDelete() {
        Employee employee = new Employee();
        employee.setName("Unknown");
        employee.setSurname("Anonymus");
        employee.setId(13);//choose some number according to database content
        return employee;
    }
    
    private static EmployeeManagerImpl employeeManager;
    
    public static void main(String[] args) {
//        int newID = EmployeeManagerImpl.countID();
        employeeManager = new EmployeeManagerImpl();
        employeeManager.createEmployee(exampleEmployee2());
        employeeManager.deleteEmployee(exampleEmployeeDelete());
        employeeManager.updateEmployee(exampleEmployeeUpdate());
    }
}

package chapter_4;

import chapter_4.com.horstmann.corejava.Employee;
import static java.lang.System.*;

public class list4_6 {
    public static void main(String[] args) {
        // because of the import statement, we don't have to use
        // com.horstmann.corejava.Employee here
        Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        harry.raiseSalary(5);

        // because of the static import statement, we don't have to use System.out here
        out.println("name=" + harry.getName() + ",salary=" + harry.getSalary());
    }
}

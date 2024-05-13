package app;

import java.util.*;

public class Main {

    private static final String STAFF_TXT = "data/staff.txt";

    public static void main(String[] args) {
        List<Employee> staff = EmployeeUtils.loadStaffFromFile(STAFF_TXT);

        Employee employeeMaxSalary = findEmployeeWithHighestSalary(staff, 2017);
        System.out.println(employeeMaxSalary);
    }

    public static Employee findEmployeeWithHighestSalary(List<Employee> staff, int year) {
        //TODO Метод должен вернуть сотрудника с максимальной зарплатой среди тех,
        // кто пришёл в году, указанном в переменной year

        return staff.stream()
                .filter((e) -> e.getWorkStart().getYear() == year)
                .max((e1, e2) ->
                        e1.getSalary().compareTo(e2.getSalary()))
                .get();
    }
}
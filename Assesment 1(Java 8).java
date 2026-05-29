//1. Filter Objects Using Lambda Expressions
import java.util.*;
import java.util.function.Predicate;

class Employee {
    String name;
    int age;
    double salary;

    Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}

public class Main {
    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("John", 35, 45000),
                new Employee("Mike", 25, 40000),
                new Employee("David", 40, 60000)
        );

        Predicate<Employee> condition =
                emp -> emp.age > 30 && emp.salary < 50000;

        for (Employee emp : employees) {
            if (condition.test(emp)) {
                System.out.println(emp.name);
            }
        }
    }
}

//Output : John

//2. Partition Strings into Even and Odd Length Using Lambda Expression
import java.util.*;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {

        List<String> words =
                Arrays.asList("Java", "Spring", "API", "Code", "Lambda");

        List<String> even = new ArrayList<>();
        List<String> odd = new ArrayList<>();

        Predicate<String> isEven =
                str -> str.length() % 2 == 0;

        for (String word : words) {
            if (isEven.test(word)) {
                even.add(word);
            } else {
                odd.add(word);
            }
        }

        System.out.println("Even Length: " + even);
        System.out.println("Odd Length: " + odd);
    }
}

//3. Division Operation Using Anonymous Inner Class and Lambda
@FunctionalInterface
interface Operation {
    void perform(int a, int b);
}

public class Main {

    public static void main(String[] args) {

        // Anonymous Inner Class
        Operation divide = new Operation() {
            @Override
            public void perform(int a, int b) {
                try {
                    System.out.println(a / b);
                } catch (ArithmeticException e) {
                    System.out.println("Arithmetic Exception: Division by zero");
                }
            }
        };

        divide.perform(10, 0);

        // Lambda Expression
        Operation lambdaOperation = (a, b) -> {
            try {
                String str = null;
                System.out.println(str.length());
            } catch (Exception e) {
                System.out.println("Other Exception: "
                        + e.getClass().getSimpleName());
            }
        };

        lambdaOperation.perform(10, 5);
    }
}

//4. Functional Interface Calculator
@FunctionalInterface
interface Calculator {
    int calculate(int num1, int num2);
}

public class Main {
    public static void main(String[] args) {

        Calculator sum = (a, b) -> a + b;

        System.out.println(sum.calculate(2, 4));
    }
}

//Output : 6

//5. Anonymous Inner Class Implementing Runnable
public class Main {

    public static void main(String[] args) {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println(
                        "Thread is running: "
                                + Thread.currentThread().getName());
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }
}

//6. Default Method calculateArea in Shape Interface
interface Shape {

    default double calculateArea() {
        return 0.0;
    }
}

class Circle implements Shape {

    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

public class Main {
    public static void main(String[] args) {

        Shape shape = new Circle(5);

        System.out.println(shape.calculateArea());
    }
}

//7. Logger Interface with Static Factory Method
interface Logger {

    void log(String message);

    static Logger createLogger() {
        return new ConsoleLogger();
    }
}

class ConsoleLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}

public class Main {
    public static void main(String[] args) {

        Logger logger = Logger.createLogger();

        logger.log("Application Started");
    }
}

//8. Multiple Interfaces Having Same Default Method
interface Shape {

    default String getDescription() {
        return "Shape Interface";
    }
}

interface Color {

    default String getDescription() {
        return "Color Interface";
    }
}

class ColoredCircle implements Shape, Color {

    @Override
    public String getDescription() {
        return "This is a Colored Circle";
    }
}

public class Main {

    public static void main(String[] args) {

        ColoredCircle circle = new ColoredCircle();

        System.out.println(circle.getDescription());
    }
}

//1. Use the map method to convert a list of strings to uppercase.
List<String> words =
                Arrays.asList("Java","Spring","Microservices","API","Lambda");

List<String> words2 = words.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
System.out.println(words2);

//2. Given a list of words, create a stream that returns a list of unique characters present in those words.
List<String> words =
                Arrays.asList("Java","Spring","Microservices","API","Lambda");

List<Character> chars = words.stream()
                .flatMap(word -> word.chars().mapToObj(c -> (char)c))
                .distinct()
                .collect(Collectors.toList());

System.out.println("Unique Characters:"+chars);

//3. Calculate the difference between two LocalDate objects.
LocalDate date1 = LocalDate.of(2024, 1, 1);
LocalDate date2 = LocalDate.of(2024, 2, 15);

long days = ChronoUnit.DAYS.between(date1, date2);

System.out.println(days);

//4. Convert a sequential stream to a parallel stream and print the elements concurrently.
List<Integer> list = List.of(1,2,6,8,0,9);
list.stream().parallel().forEach(n-> System.out.println(n+" ->"+Thread.currentThread().getName()+ " "));

//5. Create a BiConsumer that accepts two sets of integers. The consumer should square each element of the first set and add the result to the corresponding element in the second set.

/*6. Create a list of Person objects, where each Person has attributes name and age. Use lambda expressions to perform the following tasks:
->Filter the list to include only persons with age greater than 25.
->Sort the resulting list based on their names.
->Print the names of the filtered and sorted persons. */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Person> list = Arrays.asList(
                new Person("Aman",28),
                new Person("Rahul",22),
                new Person("Darshan",26)
        );

        list.stream()
                .filter(person -> person.getAge()>25)
                .sorted(Comparator.comparing(Person::getName))
                .map(Person::getName)
                .forEach(name-> System.out.println(name));


    }
    static class Person{
        String name;
        int age;
        Person(String name, int age){
            this.name = name;
            this.age = age;
        }
        public String getName(){
            return name;
        }
        public int getAge(){
            return age;
        }
    }
}


//7. Use the filter method to get the even numbers from a list.
List<Integer> list = Arrays.asList(1,5,7,94,26,98,22);
List<Integer> even = list.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
System.out.println(even);


//8. Calculate the number of days between the current date and the next occurring Friday the 13th.
//9. Use a stream to calculate the product of the squares of the first 5 odd numbers using the forEach method.
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        long product =
                IntStream.iterate(1, n -> n+2)
                        .limit(5)
                        .map(n -> n*n)
                        .reduce(1,(a,b)->a*b);

        System.out.println(product);
    }
}


//10. Implement a BiFunction that takes two strings and returns a new string consisting of alternating characters from each input string. For example, if the inputs are "hello" and "world", the output should be "hweolrllod".
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        BiFunction<String,String,String> merge = (s1,s2)->{
            StringBuilder sb = new StringBuilder();
            int max = Math.max(s1.length(),s2.length());
            for(int i=0;i<max;i++){
                if(i<s1.length()){
                    sb.append(s1.charAt(i));
                }
                if(i<s2.length()){
                    sb.append(s2.charAt(i));
                }
            }
            return sb.toString();
        };
        System.out.println(merge.apply("Hello","World"));
    }
}

//11. Create an interface named Shape with a default method calculateArea(). Have two classes Circle and Rectangle implement this interface. The default method should return a default area if not overridden.
public class Main {
    public static void main(String[] args) {
        Shape circle = new Circle(4.0);
        System.out.println("Default Areaof circle:"+circle.calculateArea());

        Shape rectangle = new Rectangle(4.0,6.0);
        System.out.println("Default Area of rectangle:"+rectangle.calculateArea());

    }
    interface Shape{
        default double calculateArea(){
            return 1.0;
        }
    }
    static class Circle implements Shape{
        double radius;
        Circle(double radius){
            this.radius = radius;
        }

    }
    static class Rectangle implements Shape{
        double length;
        double breadth;
        Rectangle(double length, double breadth){
            this.length = length;
            this.breadth = breadth;
        }
    }

}

//12. Use the collect method to create a map of strings and their lengths.
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
       List<String> words = Arrays.asList("Java","Spring","Microservices","API","Lambda");
       Map<String,Integer> map = words.stream()
                       .collect(Collectors.toMap(s->s,String::length));
        System.out.println(map);
    }

}


//13. Generate an infinite stream of random integers, sort them in descending order, skip the first 3, and then print the next 5.

import java.util.Comparator;
import java.util.Random;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        Stream.generate(() -> random.nextInt(1000))
                .limit(100)
                .sorted(Comparator.reverseOrder())
                .skip(3)
                .limit(5)
                .forEach(System.out::println);
    }
}

//14. Create a class representing a Book with parameters title and author. Write a method that takes two strings and creates a list of Book objects using constructor reference.
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        BiFunction<String,String,Book> create = Book::new;
        List<Book> books = Arrays.asList(
                create.apply("Ramayan","Valmiki"),
                create.apply("Computer","Sumita Arora")
        );
        System.out.println(books);
    }
    static class Book{
        String title;
        String author;
        Book(String title, String author){
            this.title = title;
            this.author = author;
        }
        public String toString(){
            return "Book name:"+title+", Author:"+author;
        }
    }
}
//15. Generate an infinite stream of random numbers and retrieve the first 10 prime numbers from the stream.

import java.util.Random;
public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        random.ints(2,1000)
                .filter(Main::isPrime)
                .distinct()
                .limit(10)
                .forEach(System.out::println);
    }
    private static boolean isPrime(int n){
        if (n <= 1) return false;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}

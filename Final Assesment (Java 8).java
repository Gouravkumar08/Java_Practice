/*# Java 8 Final Assessment Solutions

This repository contains solutions for the Java 8 Final Assessment covering:

- Lambda Expressions
- Anonymous Inner Classes
- Default Methods
- Predicate
- Function
- Supplier
- Consumer
- BiPredicate
- BiFunction
- BiConsumer
- UnaryOperator
- BinaryOperator
- Constructor References
- Streams API
- Parallel Streams
- flatMap
- Collectors.partitioningBy
- Date & Time API

---*/

//# 1. Use lambda expressions to transform a list of strings into a new list where each string is reversed.

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<String> words =
                List.of("Hello", "am", "Software Engineer");

        List<String> result = words.stream()
                .map(s -> new StringBuilder(s)
                        .reverse()
                        .toString())
                .collect(Collectors.toList());

        System.out.println(result);
    }
}
```

---

//# 2. Write a Java program that uses an anonymous inner class to implement the Runnable interface for multi-threading.

public class Main {

    public static void main(String[] args) {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println(
                        "Running : "
                                + Thread.currentThread().getName()
                );
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }
}
```

---

//# 3. Create an interface with a default method to concatenate two strings. In a class implementing this interface, override the default method to reverse the concatenated string.

public class Main {

    interface Concat {
        default String process(String s1, String s2) {
            return s1.concat(s2);
        }
    }

    static class Operate implements Concat {

        @Override
        public String process(String s1, String s2) {
            return new StringBuilder(
                    s1.concat(s2)
            ).reverse().toString();
        }
    }

    public static void main(String[] args) {

        Concat ops = new Operate();

        System.out.println(
                ops.process("Hello", "World")
        );
    }
}
```

---

//# 4. Create a method that takes a list of strings and a predicate. The method should return a new list containing only those strings that satisfy the predicate, where the predicate checks if the string is a palindrome.

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {

        Predicate<String> palindrome =
                s -> s.equalsIgnoreCase(
                        new StringBuilder(s)
                                .reverse()
                                .toString()
                );

        List<String> words =
                List.of("racecar", "am", "leeteel");

        System.out.println(
                filter(words, palindrome)
        );
    }

    private static List<String> filter(
            List<String> words,
            Predicate<String> predicate
    ) {

        List<String> result = new ArrayList<>();

        for (String word : words) {
            if (predicate.test(word)) {
                result.add(word);
            }
        }

        return result;
    }
}
```

---

//# 5. Implement a higher-order function that takes a Function and two integers, and returns the result of applying the function to the sum of the two integers. Then use this function to calculate the square of the sum of two numbers.
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {

        Function<Integer, Integer> square =
                n -> n * n;

        int result = calculate(square, 3, 6);

        System.out.println(result);
    }

    private static int calculate(
            Function<Integer, Integer> function,
            int a,
            int b
    ) {

        return function.apply(a + b);
    }
}
```

---

//# 6. Develop a class that has a static method returning a Supplier. The Supplier should provide a random integer between 1 and 100 every time its get() method is called.
import java.util.Random;
import java.util.function.Supplier;

public class Main {

    static class Generator {

        static Supplier<Integer> process() {

            Random random = new Random();

            return () -> random.nextInt(100) + 1;
        }
    }

    public static void main(String[] args) {

        System.out.println(
                Generator.process().get()
        );
    }
}
```

---

//# 7. Write a program that uses a Consumer to modify a list of strings. The modification should involve appending the string " - Processed" to each string in the list. However, if a string starts with a vowel, it should instead be prepended with "Processed - ". Print the modified strings after applying the Consumer.
import java.util.List;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {

        Consumer<String> processor = s -> {

            if (isVowel(s.charAt(0))) {
                System.out.println(
                        "Processed - " + s
                );
            } else {
                System.out.println(
                        s + " - Processed"
                );
            }
        };

        List<String> words =
                List.of("Hello", "am", "Software Engineer");

        words.forEach(processor);
    }

    private static boolean isVowel(char ch) {

        return "AEIOUaeiou"
                .indexOf(ch) != -1;
    }
}
```

---

//# 8. Write a program that manages a sports league. Design a class representing a team with attributes like name and points. Implement a BiPredicate that checks if two teams have the same number of points and have names with the same length. Apply the BiPredicate to find and print pairs of teams with similar points and name lengths from a list.

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

public class Main {

    static class Team {

        private String name;
        private int points;

        Team(String name, int points) {
            this.name = name;
            this.points = points;
        }

        public String getName() {
            return name;
        }

        public int getPoints() {
            return points;
        }

        @Override
        public String toString() {
            return name + " : " + points;
        }
    }

    public static void main(String[] args) {

        BiPredicate<Team, Team> check =
                (t1, t2) ->
                        t1.getPoints() == t2.getPoints()
                                && t1.getName().length()
                                == t2.getName().length();

        List<Team> teams = Arrays.asList(
                new Team("A", 10),
                new Team("B", 20),
                new Team("CD", 10),
                new Team("D", 10),
                new Team("Tea", 10),
                new Team("BEE", 10)
        );

        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {

                if (check.test(
                        teams.get(i),
                        teams.get(j))) {

                    System.out.println(
                            teams.get(i)
                                    + " and "
                                    + teams.get(j)
                    );
                }
            }
        }
    }
}
```

---

//# 9. Create a program that tracks student performance. Design a class representing a student with attributes like name and grade. Implement a BiFunction that takes two students and returns a new student with a name concatenation and an average grade.
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Main {

    static class Student {

        private String name;
        private int grade;

        Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public int getGrade() {
            return grade;
        }

        @Override
        public String toString() {
            return name + " : " + grade;
        }
    }

    public static void main(String[] args) {

        BiFunction<Student, Student, Student> combine =
                (s1, s2) ->
                        new Student(
                                s1.getName()
                                        + " "
                                        + s2.getName(),
                                (s1.getGrade()
                                        + s2.getGrade()) / 2
                        );

        List<Student> students =
                Arrays.asList(
                        new Student("Darshan", 80),
                        new Student("Rahul", 90),
                        new Student("Madhav", 70)
                );

        for (int i = 0;
             i < students.size() - 1;
             i++) {

            System.out.println(
                    combine.apply(
                            students.get(i),
                            students.get(i + 1)
                    )
            );
        }
    }
}
```

---

//# 10. Imagine a program managing a music playlist. Design a class representing a song with attributes like title and artist. Implement a BiConsumer that updates the title of two songs.

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class Main {

    static class Song {

        String title;
        String artist;

        Song(String title, String artist) {
            this.title = title;
            this.artist = artist;
        }

        @Override
        public String toString() {
            return title + " - " + artist;
        }
    }

    public static void main(String[] args) {

        BiConsumer<Song, Song> updateSongs =
                (s1, s2) -> {

                    if (s1.artist.equalsIgnoreCase(
                            s2.artist)) {

                        s1.title =
                                s1.title
                                        + " & "
                                        + s2.title;

                        System.out.println(s1);

                    } else {

                        System.out.println(
                                "Different artists : "
                                        + s1.artist
                                        + " and "
                                        + s2.artist
                        );
                    }
                };

        List<Song> songs = Arrays.asList(
                new Song("Maruti",
                        "Dhanda Nyoliwala"),
                new Song("Bulleya",
                        "Amit Trivedi"),
                new Song("Tension",
                        "Dhanda Nyoliwala")
        );

        updateSongs.accept(
                songs.get(0),
                songs.get(1)
        );

        updateSongs.accept(
                songs.get(0),
                songs.get(2)
        );
    }
}
```

---

//# 11. Use UnaryOperator to modify a list of integers by doubling each odd number and halving each even number.

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        UnaryOperator<Integer> process =
                n -> n % 2 == 0
                        ? n / 2
                        : n * 2;

        List<Integer> nums =
                Arrays.asList(
                        1, 2, 3, 4, 5, 6
                );

        List<Integer> result =
                nums.stream()
                        .map(process)
                        .collect(
                                Collectors.toList()
                        );

        System.out.println(result);
    }
}
```

---

//# 12. Create a simple program to handle product inventory. Design a class representing a product with attributes like name and quantity. Implement a BinaryOperator to combine the quantities of two products if they have the same name.

import java.util.function.BinaryOperator;

public class Main {

    static class Product {

        String name;
        int quantity;

        Product(String name,
                int quantity) {

            this.name = name;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return name + " : "
                    + quantity;
        }
    }

    public static void main(String[] args) {

        BinaryOperator<Product> combine =
                (p1, p2) -> {

                    if (p1.name.equalsIgnoreCase(
                            p2.name)) {

                        return new Product(
                                p1.name,
                                p1.quantity
                                        + p2.quantity
                        );
                    }

                    return p1;
                };

        Product p1 =
                new Product("Laptop", 10);

        Product p2 =
                new Product("Laptop", 20);

        System.out.println(
                combine.apply(p1, p2)
        );
    }
}
```

---

//# 13. Design a class Person with a constructor that takes a name as a parameter. Create a list of names, and use a constructor reference to convert each name into a Person object.

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    static class Person {

        String name;

        Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {

        List<String> names =
                Arrays.asList(
                        "Darshan",
                        "Rahul",
                        "Madhav"
                );

        List<Person> persons =
                names.stream()
                        .map(Person::new)
                        .collect(
                                Collectors.toList()
                        );

        persons.forEach(
                System.out::println
        );
    }
}
```

---

//# 15. Create a stream of integers, filter out the prime numbers, and then find the average of the remaining elements using parallel processing.

import java.util.Arrays;
import java.util.List;

public class Main {

    public static boolean isPrime(int n) {

        if (n < 2)
            return false;

        for (int i = 2;
             i <= Math.sqrt(n);
             i++) {

            if (n % i == 0)
                return false;
        }

        return true;
    }

    public static void main(String[] args) {

        List<Integer> nums =
                Arrays.asList(
                        2, 5, 5, 7, 0, 3, 9
                );

        double avg =
                nums.parallelStream()
                        .filter(
                                n -> !isPrime(n)
                        )
                        .mapToInt(
                                Integer::intValue
                        )
                        .average()
                        .orElse(0);

        System.out.println(avg);
    }
}
```

---

//# 16. Given a list of words, use a stream and the forEach method to print the words in uppercase, sorted alphabetically.

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<String> names =
                Arrays.asList(
                        "Darshan",
                        "Rahul",
                        "Madhav"
                );

        names.stream()
                .sorted()
                .forEach(
                        s -> System.out.println(
                                s.toUpperCase()
                        )
                );
    }
}
```

---

//# 17. Create a program that generates a stream of random integers. Use Collectors.partitioningBy to separate the even and odd numbers into two lists. Print both lists.

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        Random random = new Random();

        Map<Boolean, List<Integer>> result =
                IntStream.generate(
                                () -> random.nextInt(100)
                        )
                        .limit(10)
                        .boxed()
                        .collect(
                                Collectors.partitioningBy(
                                        n -> n % 2 == 0
                                )
                        );

        System.out.println(
                "Even Numbers : "
                        + result.get(true)
        );

        System.out.println(
                "Odd Numbers : "
                        + result.get(false)
        );
    }
}
```

---

//# 18. Design a program that takes a list of sentences and returns a list of unique words found in those sentences. Use map to split each sentence into words and flatMap to flatten the resulting lists.

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<String> sentences =
                Arrays.asList(
                        "Hi am Jarvis",
                        "What can i do for you today",
                        "Yo it's a Friday"
                );

        List<String> words =
                sentences.stream()
                        .map(
                                s -> s.split(" ")
                        )
                        .flatMap(
                                Arrays::stream
                        )
                        .distinct()
                        .collect(
                                Collectors.toList()
                        );

        System.out.println(words);
    }
}
```

---

//# 19. Generate a stream of random doubles between 0 and 1. Use the limit method to print the sum of the first 3 distinct numbers.

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random random = new Random();

        double sum =
                random.doubles(0, 1)
                        .distinct()
                        .limit(3)
                        .sum();

        System.out.println(
                "Sum = " + sum
        );
    }
}
```

---

//# 20. Find out how many days we need to wait from today until the next time the 19th day of a month falls on a Friday.

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        LocalDate today =
                LocalDate.now();

        LocalDate nextFriday19 =
                Stream.iterate(
                                today,
                                date -> date.plusDays(1)
                        )
                        .filter(
                                date ->
                                        date.getDayOfWeek()
                                                == DayOfWeek.FRIDAY
                                                &&
                                                date.getDayOfMonth()
                                                        == 19
                        )
                        .findFirst()
                        .orElseThrow();

        long days =
                ChronoUnit.DAYS.between(
                        today,
                        nextFriday19
                );

        System.out.println(
                "Days until a Friday on 19th : "
                        + days
        );
    }
}
```


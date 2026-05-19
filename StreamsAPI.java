import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        /* =========================================================
           1. Filter Even Numbers
           Problem:
           Given a list of integers, filter all even numbers.

           Input:
           [1, 2, 3, 4, 5, 6]

           Expected Output:
           [2, 4, 6]
        ========================================================= */

        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5, 6);

        List<Integer> evenNumbers = numbers1.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("1. " + evenNumbers);

        /*
        Output:
        [2, 4, 6]
        */


        /* =========================================================
           2. Find First String Starting with 'A'
           Problem:
           Find the first string starting with A.

           Input:
           [John, Alice, Bob, Alex]

           Expected Output:
           Alice
        ========================================================= */

        List<String> names = Arrays.asList("John", "Alice", "Bob", "Alex");

        String firstA = names.stream()
                .filter(name -> name.startsWith("A"))
                .findFirst()
                .orElse("Not Found");

        System.out.println("2. " + firstA);

        /*
        Output:
        Alice
        */


        /* =========================================================
           3. Convert Strings to Uppercase

           Input:
           [apple, banana, cherry]

           Expected Output:
           [APPLE, BANANA, CHERRY]
        ========================================================= */

        List<String> words1 = Arrays.asList("apple", "banana", "cherry");

        List<String> upperWords = words1.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("3. " + upperWords);

        /*
        Output:
        [APPLE, BANANA, CHERRY]
        */


        /* =========================================================
           4. Count Empty Strings

           Input:
           [abc, "", def, "", ghi]

           Expected Output:
           2
        ========================================================= */

        List<String> list = Arrays.asList("abc", "", "def", "", "ghi");

        long emptyCount = list.stream()
                .filter(String::isEmpty)
                .count();

        System.out.println("4. " + emptyCount);

        /*
        Output:
        2
        */


        /* =========================================================
           5. Find Maximum Value

           Input:
           [23, 5, 89, 16, 45]

           Expected Output:
           89
        ========================================================= */

        List<Integer> nums1 = Arrays.asList(23, 5, 89, 16, 45);

        int max = nums1.stream()
                .max(Integer::compare)
                .orElse(0);

        System.out.println("5. " + max);

        /*
        Output:
        89
        */


        /* =========================================================
           6. Sort Strings Alphabetically

           Input:
           [Banana, Apple, Mango, Grapes]

           Expected Output:
           [Apple, Banana, Grapes, Mango]
        ========================================================= */

        List<String> fruits = Arrays.asList("Banana", "Apple", "Mango", "Grapes");

        List<String> sortedFruits = fruits.stream()
                .sorted()
                .collect(Collectors.toList());

        System.out.println("6. " + sortedFruits);

        /*
        Output:
        [Apple, Banana, Grapes, Mango]
        */


        /* =========================================================
           7. Group Words by Length

           Input:
           [one, two, three, four, six]

           Expected Output:
           {3=[one, two, six], 4=[four], 5=[three]}
        ========================================================= */

        List<String> words2 = Arrays.asList("one", "two", "three", "four", "six");

        Map<Integer, List<String>> groupedWords = words2.stream()
                .collect(Collectors.groupingBy(String::length));

        System.out.println("7. " + groupedWords);

        /*
        Output:
        {3=[one, two, six], 4=[four], 5=[three]}
        */


        /* =========================================================
           8. Remove Duplicates

           Input:
           [1, 2, 2, 3, 4, 4, 5]

           Expected Output:
           [1, 2, 3, 4, 5]
        ========================================================= */

        List<Integer> numbers2 = Arrays.asList(1, 2, 2, 3, 4, 4, 5);

        List<Integer> uniqueNumbers = numbers2.stream()
                .distinct()
                .collect(Collectors.toList());

        System.out.println("8. " + uniqueNumbers);

        /*
        Output:
        [1, 2, 3, 4, 5]
        */


        /* =========================================================
           9. Sum of All Odd Numbers

           Input:
           [1, 2, 3, 4, 5]

           Expected Output:
           9
        ========================================================= */

        List<Integer> nums2 = Arrays.asList(1, 2, 3, 4, 5);

        int oddSum = nums2.stream()
                .filter(n -> n % 2 != 0)
                .reduce(0, Integer::sum);

        System.out.println("9. " + oddSum);

        /*
        Output:
        9
        */


        /* =========================================================
           10. Frequency of Each Word

           Input:
           [apple, banana, apple, orange, banana]

           Expected Output:
           {apple=2, banana=2, orange=1}
        ========================================================= */

        List<String> words3 = Arrays.asList(
                "apple",
                "banana",
                "apple",
                "orange",
                "banana"
        );

        Map<String, Long> frequencyMap = words3.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        System.out.println("10. " + frequencyMap);

        /*
        Output:
        {banana=2, orange=1, apple=2}
        */


        /* =========================================================
           11. Product of Even Numbers Greater Than 10

           Input:
           [5, 12, 18, 7, 22, 3]

           Expected Output:
           4752
        ========================================================= */

        List<Integer> nums3 = Arrays.asList(5, 12, 18, 7, 22, 3);

        int product = nums3.stream()
                .filter(n -> n % 2 == 0 && n > 10)
                .reduce(1, (a, b) -> a * b);

        System.out.println("11. " + product);

        /*
        Output:
        4752
        */


        /* =========================================================
           12. Total Length of Unique Words Starting With 'S'

           Input:
           [Sun, Sky, Sea, Sand, Sky, Sun, Stone]

           Expected Output:
           18
        ========================================================= */

        List<String> words4 = Arrays.asList(
                "Sun",
                "Sky",
                "Sea",
                "Sand",
                "Sky",
                "Sun",
                "Stone"
        );

        int totalLength = words4.stream()
                .distinct()
                .filter(word -> word.startsWith("S"))
                .map(String::length)
                .reduce(0, Integer::sum);

        System.out.println("12. " + totalLength);

        /*
        Output:
        18
        */


        /* =========================================================
           13. Concatenate Capitalized Names Longer Than 3 Characters

           Input:
           [john, al, mark, susan, zoe]

           Expected Output:
           John,Mark,Susan
        ========================================================= */

        List<String> names2 = Arrays.asList(
                "john",
                "al",
                "mark",
                "susan",
                "zoe"
        );

        String result = names2.stream()
                .filter(name -> name.length() > 3)
                .map(name ->
                        Character.toUpperCase(name.charAt(0))
                                + name.substring(1))
                .collect(Collectors.joining(","));

        System.out.println("13. " + result);

        /*
        Output:
        John,Mark,Susan
        */


        /* =========================================================
           14. Sum of Squares of Odd Numbers from Nested Lists

           Input:
           [[1,2,3],[4,5],[6,7]]

           Expected Output:
           84
        ========================================================= */

        List<List<Integer>> data = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5),
                Arrays.asList(6, 7)
        );

        int sumOfSquares = data.stream()
                .flatMap(List::stream)
                .filter(n -> n % 2 != 0)
                .map(n -> n * n)
                .reduce(0, Integer::sum);

        System.out.println("14. " + sumOfSquares);

        /*
        Output:
        84
        */


        /* =========================================================
           15. Longest Word Starting With Vowel

           Input:
           [apple, banana, orange, umbrella, ice, grape]

           Expected Output:
           umbrella
           Length = 8
        ========================================================= */

        List<String> words5 = Arrays.asList(
                "apple",
                "banana",
                "orange",
                "umbrella",
                "ice",
                "grape"
        );

        String longestVowelWord = words5.stream()
                .filter(word ->
                        "aeiouAEIOU".indexOf(word.charAt(0)) != -1)
                .max(Comparator.comparingInt(String::length))
                .orElse("");

        System.out.println("15. " + longestVowelWord);
        System.out.println("Length = " + longestVowelWord.length());

        /*
        Output:
        umbrella
        Length = 8
        */

    }
}

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
```java
// 1. Count Total Vowels in a String
// Problem:
// Count total vowels in a string.
//
// Input:
// "Gourav Kumar"
//
// Expected Output:
// 5

String str1 = "Gourav Kumar";

long vowelCount = str1.toLowerCase()
        .chars()
        .filter(ch -> "aeiou".indexOf(ch) != -1)
        .count();

System.out.println(vowelCount);


// Output:
// 5


// ============================================================


// 2. Print All Vowels
// Problem:
// Print all vowels from string.
//
// Input:
// "Interview"
//
// Expected Output:
// ieie

String str2 = "Interview";

String vowels = str2.toLowerCase()
        .chars()
        .filter(ch -> "aeiou".indexOf(ch) != -1)
        .mapToObj(ch -> String.valueOf((char) ch))
        .collect(Collectors.joining());

System.out.println(vowels);


// Output:
// ieie


// ============================================================


// 3. Remove All Vowels
// Problem:
// Remove all vowels from string.
//
// Input:
// "Gourav"
//
// Expected Output:
// Grv

String str3 = "Gourav";

String noVowels = str3.chars()
        .filter(ch -> "aeiouAEIOU".indexOf(ch) == -1)
        .mapToObj(ch -> String.valueOf((char) ch))
        .reduce("", (a, b) -> a + b);

System.out.println(noVowels);


// Output:
// Grv


// ============================================================


// 4. Count Each Vowel Frequency
// Problem:
// Count frequency of each vowel.
//
// Input:
// "education"
//
// Expected Output:
// {a=1, e=1, i=1, o=1, u=1}

String str4 = "education";

Map<Character, Long> vowelFreq =
        str4.toLowerCase()
        .chars()
        .mapToObj(ch -> (char) ch)
        .filter(ch -> "aeiou".indexOf(ch) != -1)
        .collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
        ));

System.out.println(vowelFreq);


// Output:
// {a=1, e=1, i=1, o=1, u=1}


// ============================================================


// 5. Find First Vowel
// Problem:
// Find first vowel from string.
//
// Input:
// "Strong"
//
// Expected Output:
// o

String str5 = "Strong";

Character firstVowel = str5.toLowerCase()
        .chars()
        .mapToObj(ch -> (char) ch)
        .filter(ch -> "aeiou".indexOf(ch) != -1)
        .findFirst()
        .get();

System.out.println(firstVowel);


// Output:
// o


// ============================================================


// 6. Check if String Contains Any Vowel
// Problem:
// Check whether string contains vowel.
//
// Input:
// "Sky"
//
// Expected Output:
// false

String str6 = "Sky";

boolean hasVowel = str6.toLowerCase()
        .chars()
        .anyMatch(ch -> "aeiou".indexOf(ch) != -1);

System.out.println(hasVowel);


// Output:
// false


// ============================================================


// 7. Count Consonants
// Problem:
// Count consonants in string.
//
// Input:
// "Gourav"
//
// Expected Output:
// 3

String str7 = "Gourav";

long consonantCount = str7.toLowerCase()
        .chars()
        .filter(ch ->
                Character.isLetter(ch) &&
                "aeiou".indexOf(ch) == -1
        )
        .count();

System.out.println(consonantCount);


// Output:
// 3


// ============================================================


// 8. Reverse String Using Streams
// Problem:
// Reverse string using streams.
//
// Input:
// "Java"
//
// Expected Output:
// avaJ

String str8 = "Java";

String reversed = new StringBuilder(str8)
        .reverse()
        .chars()
        .mapToObj(ch -> String.valueOf((char) ch))
        .collect(Collectors.joining());

System.out.println(reversed);


// Output:
// avaJ


// ============================================================


// 9. Find Duplicate Characters
// Problem:
// Find duplicate characters in string.
//
// Input:
// "programming"
//
// Expected Output:
// r=2
// g=2
// m=2

String str9 = "programming";

Map<Character, Long> duplicateMap =
        str9.chars()
        .mapToObj(ch -> (char) ch)
        .collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
        ));

duplicateMap.entrySet()
        .stream()
        .filter(entry -> entry.getValue() > 1)
        .forEach(System.out::println);


// Output:
// r=2
// g=2
// m=2


// ============================================================


// 10. First Non-Repeating Character
// Problem:
// Find first non-repeating character.
//
// Input:
// "swiss"
//
// Expected Output:
// w

String str10 = "swiss";

Map<Character, Long> freqMap =
        str10.chars()
        .mapToObj(ch -> (char) ch)
        .collect(Collectors.groupingBy(
                Function.identity(),
                LinkedHashMap::new,
                Collectors.counting()
        ));

Character nonRepeating = freqMap.entrySet()
        .stream()
        .filter(entry -> entry.getValue() == 1)
        .map(Map.Entry::getKey)
        .findFirst()
        .get();

System.out.println(nonRepeating);


// Output:
// w


// ============================================================


// 11. Convert String to Character List
// Problem:
// Convert string into character list.
//
// Input:
// "Java"
//
// Expected Output:
// [J, a, v, a]

String str11 = "Java";

List<Character> charList = str11.chars()
        .mapToObj(ch -> (char) ch)
        .collect(Collectors.toList());

System.out.println(charList);


// Output:
// [J, a, v, a]


// ============================================================


// 12. Sort Characters Alphabetically
// Problem:
// Sort characters alphabetically.
//
// Input:
// "dcba"
//
// Expected Output:
// abcd

String str12 = "dcba";

String sorted = str12.chars()
        .sorted()
        .mapToObj(ch -> String.valueOf((char) ch))
        .collect(Collectors.joining());

System.out.println(sorted);


// Output:
// abcd


// ============================================================


// 13. Count Words in Sentence
// Problem:
// Count words in sentence.
//
// Input:
// "Java Stream API"
//
// Expected Output:
// 3

String sentence1 = "Java Stream API";

long wordCount = Arrays.stream(sentence1.split(" "))
        .count();

System.out.println(wordCount);


// Output:
// 3


// ============================================================


// 14. Find Longest Word in Sentence
// Problem:
// Find longest word from sentence.
//
// Input:
// "I love SpringBoot"
//
// Expected Output:
// SpringBoot

String sentence2 = "I love SpringBoot";

String longestWord = Arrays.stream(sentence2.split(" "))
        .max((a, b) -> a.length() - b.length())
        .get();

System.out.println(longestWord);


// Output:
// SpringBoot


// ============================================================


// 15. Sum of Digits in String
// Problem:
// Find sum of digits in string.
//
// Input:
// "a1b2c3"
//
// Expected Output:
// 6

String str15 = "a1b2c3";

int digitSum = str15.chars()
        .filter(Character::isDigit)
        .map(ch -> ch - '0')
        .sum();

System.out.println(digitSum);


// Output:
// 6
```


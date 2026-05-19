/*
1. Greeting with Lambda
Task:
Create a functional interface Greeting with a method void greet(String name).
Instructions:
Implement it using a lambda to print: "Hello, <name>!"
Call it with multiple names in the main() method.

2. Math Operations with Lambdas
Task:
Create a functional interface Operation with int apply(int a, int b).
Instructions:
Implement lambdas for:
Addition
Subtraction
Multiplication\
Division
Write a method calculate(Operation op, int a, int b) to use it.
*/
interface greet{
        String say(String name);
}
interface Operation{
        int calc(int a, int b);
}
public static void main(String[] args) {

        String name = "Gourav";
        greet hello = s-> "Hello " + s;
        System.out.println(hello.say(name));

        Operation add = (a,b)->a+b;
        Operation sub = (a,b) -> a-b;
        Operation multi = (a,b) -> a*b;


        System.out.println(add.calc(2,5));
        System.out.println(sub.calc(15,6));
        System.out.println(multi.calc(4,6));

}
/*3. Simple String Processor
Task:
Define a functional interface StringProcessor with String process(String input).
Instructions:
●Write 3 lambda implementations:
●○ Convert to uppercase
○ Reverse a string
○ Replace spaces with dashes
Call and print output for each.*/
interface StringProcessor{
        String process(String s);
}

public static void main(String[] args) {
        StringProcessor convertTo = s-> s.toUpperCase();
        System.out.println(convertTo.process("gourav"));

        StringProcessor reverse = s-> new StringBuilder(s).reverse().toString();
        System.out.println(reverse.process("Hithere"));

        StringProcessor replacer = s-> s.replaceAll(" ", "_");
        System.out.println(replacer.process("Hi There"));
}

/*
4. Lambda for Array Search
Task:
Create a functional interface Checker with method boolean check(int x).
Instructions:
●Implement a method countMatches(int[] arr, Checker checker)
●Pass lambdas to count:

Even numbers
Numbers greater than 100
Negative numbers
*/
public static void main(String[] args) {
        int[] nums = {2,4,1,-5,6,25,102};
        int evenCount = countMatches(nums, x->x%2==0);
        System.out.println(evenCount);

        int greaterThan100 = countMatches(nums,x->x>100);
        System.out.println(greaterThan100);

        int negatives = countMatches(nums,x->x<0);
        System.out.println(negatives);
    }
    interface Checker{
        boolean check(int num);
    }
    static int countMatches(int[] arr, Checker checker){
        int count = 0;
        for(int num:arr){
            if(checker.check(num)){
                count++;
            }
        }
        return count;
    }

/*
5. Simple Conditional Message
Task:
Create a functional interface Condition with boolean test(int value).
Instructions:
Use a lambda to print "Pass" or "Fail" based on whether value > 50.
Test this in a loop from 1 to 100.
*/
    public static void main(String[] args) {
        Condition condition = value-> value>50;
        for(int i=0;i<100;i++){
            if(condition.test(i)){
                System.out.println("Pass");
            }else{
                System.out.println("Fail");
            }
        }
    }
    @FunctionalInterface
    interface Condition{
        boolean test(int num);
    }
/*
6. Character Filter in String
Task:
Define a functional interface CharFilter with boolean accept(char c).
Instructions:
●Implement a method printAcceptedChars(String s, CharFilter filter)
●Use lambdas to:
○Print only vowels
○Print only uppercase letters
○Print only digits
*/
    public static void main(String[] args) {
        String s = "Hi There, my name is Gourav.1";
        System.out.println("Vowels :");
        String vowels = acceptedChars(s, c->"aeiouAEIOU".indexOf(c)!=-1);
        System.out.println(vowels);

        String uppercase = acceptedChars(s,c->Character.isUpperCase(c));
        System.out.println(uppercase);

        String onlyNos = acceptedChars(s, c-> Character.isDigit(c));
        System.out.println(onlyNos);
    }
    @FunctionalInterface
    interface charFilter{
        boolean accept(char c);
    }
    static String acceptedChars(String s, charFilter filter){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(filter.accept(c)){
                sb.append(c);
            }
        }
        return sb.toString();
    }

/*
7. Lambda with Static Method
Task: Use a functional interface Printer with void print().
Instructions:
Create a static method static void printStars() that prints *****
Assign it to a lambda or method reference and call print()
*/
    public static void main(String[] args) {
        Printer p = Main::printStars;
        p.print();
    }
    @FunctionalInterface
    interface Printer{
        void print();
    }
    static void printStars(){
        System.out.println("****");
    }

/*
8. Temperature Conversion
Task:
Define a functional interface Converter with double convert(double value).
Instructions:
●Use lambdas to convert:
●○ Celsius to Fahrenheit
○ Fahrenheit to Celsius
Call each with example values and print results.
*/
    public static void main(String[] args) {

        // Celsius to Fahrenheit
        Converter cToF =
                c -> (c * 9 / 5) + 32;

        // Fahrenheit to Celsius
        Converter fToC =
                f -> (f - 32) * 5 / 9;

        System.out.println(
                "25 Celsius = " +
                cToF.convert(25) +
                " Fahrenheit"
        );

        System.out.println(
                "77 Fahrenheit = " +
                fToC.convert(77) +
                " Celsius"
        );
    }
/*
9. Length Comparison
Task:
Create a functional interface CompareLength with boolean isLonger(String s1, String s2).
Instructions:
●
●
Implement a lambda to compare string lengths.
Call it with various string pairs to find which is longer.
*/
public static void main(String[] args) {
        String s1 = "heillpo";
        String s2 = "heyy";
        compareLength compareLength = (s,t)-> s.length()>t.length();
        System.out.println(compareLength.isLonger(s1,s2));
    }
    @FunctionalInterface
    interface compareLength{
        boolean isLonger(String s1, String s2);
    }

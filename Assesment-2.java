//Implement a predicate that checks if a string is a palindrome.
Predicate<String> palindrome = str-> str.equalsIgnoreCase(new StringBuilder(str).reverse().toString());
System.out.println(palindrome.test("racecar"));

//Implement a Function that converts a string to uppercase.
Function<String,String> toUppercase = s-> s.toUpperCase();
System.out.println(toUppercase.apply("hello"));

//Create a Consumer to print each element of a list.
Consumer<List<Integer>> print = n-> System.out.println(n+" ");
print.accept(Arrays.asList(1,4,6,8,9));

//Create a supplier that generates a list of 5 random strings.
Supplier<List<String>> supplier = ()->{
            List<String> values = new ArrayList<>();
            for(int i=0;i<=5;i++){
                values.add(UUID.randomUUID().toString().substring(0,8));
            }
            return values;
};
System.out.println(supplier.get());

//Write a BiPredicate to check if two strings are of equal length.
BiPredicate<String,String> isEqual = (s1,s2)-> s1.length() == s2.length();
System.out.println(isEqual.test("hello","hello"));

//Write a bi-consumer that merges two maps, adding values for common keys.
BiConsumer<Map<String,Integer>,Map<String,Integer>> mergeMaps = (map1,map2)->
                map2.forEach((key,value)->map1.merge(key,value,Integer::sum));
        Map<String,Integer> map1 = new HashMap<>();
        map1.put("A",3);
        map1.put("B",2);

        Map<String,Integer> map2 = new HashMap<>();
        map2.put("B",4);
        map2.put("C",5);

        mergeMaps.accept(map1,map2);
        System.out.println(map1);

//Use a BiConsumer to print the sum of two integers.

BiConsumer<Integer,Integer> sum = (a,b)-> System.out.println(a+b);
sum.accept(2,6);

//Implement a unary operator that squares each element of an array of doubles.

UnaryOperator<Double> square = n-> n*n;
        List<Double> list = new ArrayList<>(Arrays.asList(4.0,6.0,7.0));
        for(int i=0;i<list.size();i++){
            list.set(i,square.apply(list.get(i)));
}
System.out.println(list);

//Implement a BinaryOperator to find the maximum of two integers.

BinaryOperator<Integer> Greater = (a,b)-> {
            if(a>b) return a;
            else return b;
};
System.out.println(Greater.apply(3,6));

//Write a binary operator that concatenates two strings, separating them with a space.

BinaryOperator<String> concat = (s1,s2) -> s1+" "+s2;
System.out.println(concat.apply("I'm","Batman"));

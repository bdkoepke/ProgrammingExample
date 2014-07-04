public class Program {
    private static String getPersonFullName(Person p) {
        return p.getGivenName() + " " + p.getSurname();
    }

    private static void printPerson(String prefix, Person p) {
        System.out.println(prefix + " " + getPersonFullName(p) + " is programming in java");
    }

    private static void testNameImplementations(int age, Name name, int oldAge) {
        String given = name.given();
        String surname = name.surname();

        Person person = new StringPerson(age, given, surname);
        printPerson("StringPerson: ", person);
        person = NamePerson.newPersonFromString(age, given, surname);
        printPerson("NamePerson: ", person);
        // this is beneficial because now the name contains the full name.
        // this makes it easier to create a new person with a different age.
        person = NamePerson.newPerson(age, name);
        printPerson("NamePerson from name: ", person);

        person = NamePerson.newPerson(oldAge, name);
        System.out.println(getPersonFullName(person) + " is really old and probably dead at age " + person.getAge());
    }

    private static void testBlocks() {
        System.out.println("\nExample of blocks");
        int i = 10;
        System.out.println("i outside block: " + i);
        {
            // this j is only accessible from inside the block
            int j = 10;
            System.out.println("j inside block: " + j);
            // we can access i though
            System.out.println("access i inside block: " + i);
            // change the value of i
            i = 5;
            System.out.println("access i inside block: " + i);
            {
                int k = 3;
                System.out.println("k inside two blocks: " + k);
            }

            int k = 10;
            System.out.println("redefined k inside block: " + k);
        }
        // see that i is still 5 and not 10 because we modified it
        // inside the block
        System.out.println("i outside block: " + i);
        // we can redefine j here because the j above was local to that block
        int j = 9;
        System.out.println("redefined j outside block: " + j);
    }

    private static void newline() {
        System.out.println();
    }

    public static void main(String... args) {
        Debug.getInstance().disable();

        // won't compile because BasePerson doesn't implement getGivenName or getSurname
        // the constructor is also protected which prevents it from being initialized.
        // Person brandon = new BasePerson(25);
        testNameImplementations(25, Name.from("Brandon", "Koepke"), 100);
        newline();
        testNameImplementations(26, Name.from("Mo", "Kandy"), 110);
        newline();

        System.out.println("Recursive for loop that prints 0 to 9");
        Recursion._for(0, new Predicate<Integer>() {
            public boolean apply(Integer i) {
                return i < 10;
            }
        }, new BiFunction<Integer>() {
            public Integer apply(Integer i) {
                return i + 1;
            }
        }, new Action<Integer>() {
            public void apply(Integer i) {
                System.out.println("The value of i is: " + i);
            }
        });
        newline();
        System.out.println("Same thing with a regular for loop");
        for (int i = 0; i < 10; i++)
            System.out.println("The value of i is: " + i);

        // NOTE: the reason the functional version looks so ugly is because java before
        // java 8 didn't have first class functions. (And it still doesn't). In functional
        // languages the functional for loop is expressed as small as the iterative approach
        // and the iterative approach looks complicated...
    }
}

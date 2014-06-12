public class Program {
	// NOTE: usually these static methods would be hidden inside the
	// classes that they are used in but I put them here in order
	// to keep them in the same file.
	private static int requireValidAge(final int age) {
		if (age < 0 || age > 150)
			throw new IllegalArgumentException();
		return age;
	}
	private static String requireValidName(final String name) {
		if (name == null)
			throw new NullPointerException();
		return name;
	}

	private interface Person {
		/**
		 * Sets the person's age.
		 * @throws ArgumentOutOfRangeException thrown if the age is less than 0 or greater 
	than 150.
		 */
		public void setAge(int newAge);

		/**
		 * Gets the person's age.
		 * @return the age of the person.
		 */
		public int getAge();

		/** 
		 * Gets the person's given name.
		 * @return the person's given name.
		 */
		public String getGivenName();

		/**
		 * Gets the person's surname.
		 * @return the person's surname. 
		 */
		public String getSurname();
	}

	/**
	 * This example uses base class inheritance for sharing implementation
	 * details. You cannot create a BasePerson object, you can only inherit
	 * from it in another class.
	 */
	private static abstract class BasePerson implements Person {
		private int age;

		protected BasePerson(int age) {
			this.age = requireValidAge(age);
		}

		@Override
		public void setAge(int age) {
			this.age = requireValidAge(age);
		}

		@Override
		public int getAge() {
			return this.age;
		}

		@Override
		public abstract String getSurname();
		@Override
		public abstract String getGivenName();
	}

	private static class StringPerson extends BasePerson {
		private String given;
		private String surname;

		public StringPerson(int age, String given, String surname) {
			super(age);
			this.given = requireValidName(given);
			this.surname = requireValidName(surname);
		}

		@Override
		public String getGivenName() {
			return this.given;
		}

		@Override
		public String getSurname() {
			return this.surname;
		}
	}

	private static class Name {
		private String given;
		private String surname;

		private static String requiresValidName(String name) {
			if (name == null || ! name.matches("^[a-zA-Z\\s]+"))
				throw new IllegalArgumentException();
			return name;
		}

		private Name(String given, String surname) {
			this.given = requiresValidName(given);
			this.surname = requiresValidName(surname);
		}

		public static Name from(String given, String surname) {
			return new Name(given, surname);
		}

		public String given() {
			return this.given;
		}

		public String surname() {
			return this.surname;
		}

		@Override
		public String toString() {
			return surname() + ", " + given();
		}
	}

	private static class NamePerson extends BasePerson {
		private Name name;

		private NamePerson(int age, Name name) {
			super(age);
			this.name = name;
		}

		/** 
		 * This is an example of dependency injection.
		 * We expect the user to initialize a name for
		 * us so we don't have to worry about that 
		 * ourselves. It also enables someone to replace
		 * the implementation of name, to say "enhanced name" 
		 * or something else without us having to recompile.
		 * Otherwise we would have to use either Name.from or
		 * this.name = new Name() both of which are bad. The
		 * example below is included just as a utility method
		 * to show different examples of static initializers.
		 */
		public static Person newPerson(int age, Name name) {
			return new NamePerson(age, name);
		}

		/**
		 * Example of a different way of creating classes.
		 * Some programming languages don't even have constructors
		 * and only allow you to initialize objects through static
		 * methods. The reason this is preferred is that you can give these
		 * methods descriptive names and you can throw exceptions/etc when
		 * there are errors. This also enables you to hide the name of the
		 * real class that implements it (in this case NamePerson) and just
		 * return a Person instead. That way this isn't even available at all
		 * in the public API except through this method.
		 */
		public static Person newPersonFromString(int age, String given, String surname) {
			return newPerson(age, Name.from(given, surname));
		}

		public String getGivenName() {
			return name.given();
		}

		public String getSurname() {
			return name.surname();
		}
	}

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

	private interface Action {
		public void apply(int i);
	}

	private interface Function<T> {
		public T apply(int i);
	}

	private interface Predicate<T> { 
		public boolean apply(T i);
	}

	/**
	 * For loop implemented using recursion
	 */
	private static void _for(int i, Predicate<Integer> _continue, Function<Integer> increment, Action sideEffect) {
		System.out.println("_for(" + i + ", _continue, increment, sideEffect)");
		// is i greater than 10?
		// using underscore in the variable name because continue is a reserved word
		if (! _continue.apply(i)) {
			System.out.println("i should be = 10, actual: " + i);
			System.out.println("Now we can exit");
			return;
		}
		// perform the action we actually wanted to run.
		sideEffect.apply(i);
		// i++
		int _i = increment.apply(i);
		System.out.println("calling _for again with parameters: _for(" + _i + ", _continue, increment, sideEffect)");
		System.out.println("The value of i is unchanged though: " + i);
		_for(_i, _continue, increment, sideEffect);
	}

	private static void newline() {
		System.out.println();
	}

	public static void main(String... args) {
		// won't compile because BasePerson doesn't implement getGivenName or getSurname
		// the constructor is also protected which prevents it from being initialized. 
		// Person brandon = new BasePerson(25);
		testNameImplementations(25, Name.from("Brandon", "Koepke"), 100);
		newline();
		testNameImplementations(26, Name.from("Mo", "Kandy"), 110);
		newline();

		System.out.println("Recursive for loop that prints 0 to 9");
		_for(0, new Predicate<Integer>() { public boolean apply(Integer i) { return i < 10; } }, new Function<Integer>() { public Integer apply(int i) { return i + 1; } }, new Action() { public void apply(int i) { System.out.println("The value of i is: " + i); } });
		newline();
		System.out.println("Same thing with a regular for loop");
		for (int i = 0; i < 10; i++)
			System.out.println("The value of i is: " + i);

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

		// NOTE: the reason the functional version looks so ugly is because java before
		// java 8 didn't have first class functions. (And it still doesn't). In functional
		// languages the functional for loop is expressed as small as the iterative approach
		// and the iterative approach looks complicated...
	}
}

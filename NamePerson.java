public class NamePerson extends BasePerson {
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

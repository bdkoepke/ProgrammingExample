public class StringPerson extends BasePerson {
	private String given;
	private String surname;

	public StringPerson(int age, String given, String surname) {
		super(age);
		this.given = Contract.nonNull(given);
		this.surname = Contract.nonNull(surname);
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

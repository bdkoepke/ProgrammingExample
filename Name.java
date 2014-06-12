class Name {
	private String given;
	private String surname;

	private Name(String given, String surname) {
		this.given = Contract.nonNull(given);
		this.surname = Contract.nonNull(surname);
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

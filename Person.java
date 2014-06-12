public interface Person {
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

/**
 * This example uses base class inheritance for sharing implementation
 * details. You cannot create a BasePerson object, you can only inherit
 * from it in another class.
 */
public abstract class BasePerson implements Person {
	private static int requireValidAge(final int age) {
    if (age < 0 || age > 150)
      throw new IllegalArgumentException();
    return age;
  }

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

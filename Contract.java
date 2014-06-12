public class Contract {
	public static <T> T nonNull(final T _T) {
		if (_T == null)
			throw new NullPointerException();
		return _T;
	}
}

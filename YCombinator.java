import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class YCombinator {
	public static UnaryOperator<BigInteger> fibonacci(final UnaryOperator<BigInteger> operator) {
		final BigInteger TWO = BigInteger.valueOf(2);
		return (n) -> {
			if (n.equals(BigInteger.ZERO)) return BigInteger.ZERO;
			if (n.equals(BigInteger.ONE)) return BigInteger.ONE;
			return operator.apply(n.subtract(BigInteger.ONE)).add(operator.apply(n.subtract(TWO)));
		};
	}
	public static <T> UnaryOperator<T> ycombinator(final UnaryOperator<UnaryOperator<T>> operator) {
		// cannot use lambda because we need 'this' reference...
		return new UnaryOperator<T>() {
			public T apply(T t) {
				return operator.apply(this).apply(t);
			}
		};
	}
	public static <T> UnaryOperator<T> memoizedYCombinator(final UnaryOperator<UnaryOperator<T>> operator) {
		final Map<T, T> memoize = new HashMap<>();
		return new UnaryOperator<T>() {
			public T apply(T t) {
				if (! memoize.containsKey(t))
					memoize.put(t, operator.apply(this).apply(t));
				return memoize.get(t);
			}
		};
	}
	public static void main(String... args) {
		BigInteger n = BigInteger.valueOf(1000);
		UnaryOperator<BigInteger> fastFibonacci = memoizedYCombinator(t -> fibonacci(t));
		UnaryOperator<BigInteger> slowFibonacci = ycombinator(t -> fibonacci(t));
		// completes in <1 second
		System.out.println(fastFibonacci.apply(n));
		// never completes...
		System.out.println(slowFibonacci.apply(n));
	}
}

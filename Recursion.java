public class Recursion { 
	private static Debug d = Debug.getInstance();

  /**
   * For loop implemented using recursion
   */
  public static void _for(int i, Predicate<Integer> _continue, BiFunction<Integer> increment, Action<Integer> sideEffect) {
		if (d.isDebugEnabled())
    	d.println("_for(" + i + ", _continue, increment, sideEffect)");
    // is i greater than 10?
    // using underscore in the variable name because continue is a reserved word
    if (! _continue.apply(i)) {
			if (d.isDebugEnabled()) {
      	d.println("i should be = 10, actual: " + i);
      	d.println("Now we can exit");
			}
      return;
    }
    // perform the action we actually wanted to run.
    sideEffect.apply(i);
    // i++
    int _i = increment.apply(i);
		if (d.isDebugEnabled()) {
    	d.println("calling _for again with parameters: _for(" + _i + ", _continue, increment, sideEffect)");
    	d.println("The value of i is unchanged though: " + i);
		}
    _for(_i, _continue, increment, sideEffect);
  }	
}

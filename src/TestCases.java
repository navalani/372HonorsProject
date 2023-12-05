import java.lang.reflect.InvocationTargetException;

public class TestCases {
	
	/*
		~(p -> q) or ~(q -> p) (1)
		
		≡ ¬(¬p ∨ q) ∨ ¬(¬q ∨ p) (2) - DeMorgan's And
		≡ (¬¬p ∧ ¬q) ∨ (¬¬q ∧ ¬p) (3) - DeMorgan's Or
		≡ (p and ~q) or (q and ~p) (4) - Double Negation
	*/
	
	/*
	public void test1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] outputArr = UI.runUI();
	}
	*/
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		UI.runUI();
	}
}

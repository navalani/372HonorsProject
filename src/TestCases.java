import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

public class TestCases {
	
	/*
	 identity law test:
	 
	 Starting expression: p and T
	 Ending expression: p
	 
	 p and T
	 p
	 
	 Laws Used:
	 identity
	*/
	
	public static void test1Identity() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] output = UI.runUI();
		
		Stack<String> proofAns = new Stack<>();
		Stack<String> lawsAns = new Stack<>();
		
		proofAns.add("p and T");
		proofAns.add("p");
		lawsAns.add("iden");
		
		if (output[0].equals(proofAns.toString()) && output[1].equals(lawsAns.toString())) {
			System.out.println("Test 1 passed!");
		}
		else {
			System.out.println("Test 1 failed.");
		}
	}
	
	/*
	 * domination law test:
	 * Starting expression: p and F
	 * Ending Expression: F
	 * 
	 * p and F
	 * F
	 * 
	 * laws used: 
	 * domination
	 */
	public static void test2Domination() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] output = UI.runUI();
		Stack<String> proofAns = new Stack<>();
		Stack<String> lawsAns = new Stack<>();
		
		proofAns.add("p and F");
		proofAns.add("F");
		lawsAns.add("dom");
		
		if (output[0].equals(proofAns.toString()) && output[1].equals(lawsAns.toString())) {
			System.out.println("Test 2 passed!");
		}
		else {
			System.out.println("Test 2 failed.");
		}
	}
	
	/*
	 * Starting expression: ~(~p -> ~q)
	 * Ending expression: ~p and q
	 * 
	 * cond
	 * dneg
	 * DMor
	 * dneg
	 * 
	 */
	
	public static void test3Example1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] output = UI.runUI();
		Stack<String> proofAns = new Stack<>();
		Stack<String> lawsAns = new Stack<>();
		
		proofAns.add("~(~p -> ~q)");
		proofAns.add("~(~(~p) or ~q)");
		proofAns.add("~(p or ~q)");
		proofAns.add("~p and ~(~q)");
		proofAns.add("~p and q");
		
		lawsAns.add("cond");
		lawsAns.add("dneg");
		lawsAns.add("DMor");
		lawsAns.add("dneg");
		
		
		if (output[0].equals(proofAns.toString()) && output[1].equals(lawsAns.toString())) {
			System.out.println("Test 3 passed!");
		}
		else {
			System.out.println("Test 3 failed.");
		}
	}
	
	/*
	 * Starting Expression: (~p -> q) -> ~r
	 * Ending Expression: ~p and ~q or ~r
	 * 
	 * 1. (~p -> q) -> ~r
	 * cond
	 * 2. (~~p or q) -> ~r
	 * dneg
	 * 3. p or q -> ~r
	 * cond
	 * 4. ~(p or q) or ~r
	 * 	-> Issue here: 
	 * 		using cond law changes expression 3 to be (~(p -> q)) -> ~r instead
	 * 	    of what is shown in expresison 4
	 * DMor
	 * 5. ~p and ~q or ~r
	 * 
	 */
	public static void test4() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] output = UI.runUI();
		
		Stack<String> proofAns = new Stack<>();
		Stack<String> lawsAns = new Stack<>();
		
		proofAns.add("(~p -> q) -> ~r");
		proofAns.add("(~~p or q) -> ~r");
		proofAns.add("p or q -> ~r");
		proofAns.add("~(p or q) or ~r");
		proofAns.add("~p and ~q or ~r");
		
		lawsAns.add("cond");
		lawsAns.add("dneg");
		lawsAns.add("cond");
		lawsAns.add("DMor");
		
		if (output[0].equals(proofAns.toString()) && output[1].equals(lawsAns.toString())) {
			System.out.println("Test 4 passed!");
		}
		else {
			System.out.println("Test 4 failed.");
		}
	}
	
	/*
	 * Starting expression: (p and q) -> (q or r)
	 * Ending expression: T
	 * 
	 * 1. (p and q) -> (q or r)
	 * cond
	 * 2. ~(p and q) or (q or r)
	 * 	-> Issue here:
	 * 		Using cond on expression 1 changes it to (p and q) -> (~(q -> r))
	 * 		instead of what is shown in expression 2
	 * 
	 * DMand
	 * 3. (~p or ~q) or (q or r)
	 * assoc
	 * 4. ~p or (~q or q) or r
	 * neg
	 * 5. ~p or T or r
	 * comm
	 * 6. (~p or r) or T
	 * dom
	 * 7. T
	 * 
	 */
	public static void test5() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] output = UI.runUI();
		
		Stack<String> proofAns = new Stack<>();
		Stack<String> lawsAns = new Stack<>();
		
		proofAns.add("(p and q) -> (q or r)");
		proofAns.add("~(p and q) or (q or r)");
		proofAns.add("(~p or ~q) or (q or r)");
		proofAns.add("~p or (~q or q) or r");
		proofAns.add("~p or T or r");
		proofAns.add("(~p or r) or T");
		proofAns.add("T");
		
		lawsAns.add("cond");
		lawsAns.add("DMand");
		lawsAns.add("assoc");
		lawsAns.add("neg");
		lawsAns.add("comm");
		lawsAns.add("dom");
		
		if (output[0].equals(proofAns.toString()) && output[1].equals(lawsAns.toString())) {
			System.out.println("Test 5 passed!");
		}
		else {
			System.out.println("Test 5 failed.");
		}
	}
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//System.out.println("---------- Test 1 ----------");
		//test1Identity();
		//System.out.println("---------- Test 2 ----------");
		//test2Domination();
		System.out.println("---------- Test 3 ----------");
		test3Example1();
		//System.out.println("---------- Test 4 ----------");
		//test4();
		//System.out.println("---------- Test 5 ----------");
		//test5();
	}
}

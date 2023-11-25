import java.util.Scanner;
import java.util.Stack;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class UI {
	
	private static int callFunction(String law, HashMap<String, Method> methods, Stack<String> curStack, Stack<String> undoStack, int step) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Check if the "law" entered was the instruction "redo"
		if (law.equals("undo")) {
			// Cannot undo if we are at the start of the proof
			if (curStack.size() == 1) {
				System.out.println("Already at beginning of proof.");
			}
			else {
				// Pop the top of the stack and add it to the undo stack if the user wants to use it again
				undoStack.push(curStack.pop());
				step--;
			}
		}
		
		// Add the top of the undo stack to the current stack, which redos the user's previous input
		else if (law.equals("redo")) {
			curStack.push(undoStack.pop());
			step++;
		}
		
		else {
			// Invoke the method associated with the law
			String result = (String) methods.get(law).invoke(new equivalences(), curStack.peek());
			
			// No match means the law doesn't apply to the current expression
			if (result.equals("No match")) {
				System.out.println("Law does not apply: " + law);
			}
			// Otherwise put onto the stack what the result of calling the method is
			else {
				curStack.push(result);
				step++;
			}
		}
		return step;
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Scanner kb = new Scanner(System.in);
		Stack<String> proofStack = new Stack<>();
		Stack<String> undoStack = new Stack<>();
		
		// Add the function shorthand and its associated method to a hashmap
		HashMap<String, Method> functions = new HashMap<>();
		functions.put("iden", equivalences.class.getDeclaredMethod("identity", String.class));
		functions.put("dom", equivalences.class.getDeclaredMethod("domination", String.class));
		functions.put("idem", equivalences.class.getDeclaredMethod("idempotent", String.class));
		functions.put("dneg", equivalences.class.getDeclaredMethod("double_negation", String.class));
		functions.put("neg", equivalences.class.getDeclaredMethod("negation", String.class));
		functions.put("comm", equivalences.class.getDeclaredMethod("commutative", String.class));
		functions.put("assoc", equivalences.class.getDeclaredMethod("associative", String.class));
		functions.put("dist", equivalences.class.getDeclaredMethod("distributive", String.class));
		functions.put("absorp", equivalences.class.getDeclaredMethod("absorption", String.class));
		
		// User enters the desired starting expression and ending expression
		System.out.println("Enter the starting expression: ");
		String starting = kb.nextLine();
		proofStack.push(starting);
		
		System.out.println("Enter the ending expression: ");
		String ending = kb.nextLine();
		
		// Start the proof
		System.out.println("\n---------- Beginning of Proof ----------");
		int i = 1;
		
		// Keep going until the top of the stack is the same as the ending expression
		while (!proofStack.peek().equals(ending)) {
			// Print out the part of the proof we are at
			System.out.println(i + ". " + proofStack.peek());
			// Ask the user to input a law to us on the expression at the top of the stack
			String law = kb.nextLine();
			// Make sure the law entered exists in the hashmap
			if (functions.containsKey(law)) {
				i = callFunction(law, functions, proofStack, undoStack, i);
			}
			else {
				System.out.println("No such law: " + law);
			}
			
		}
		// Print out the ending expression
		System.out.println(i + ". " + proofStack.peek());
		System.out.println("------------- End of Proof -------------");
		kb.close();
	}

}

import java.util.Scanner;
import java.util.Stack;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class UI {
	
	private static int callFunction(String law, HashMap<String, Method> methods, Stack<String> curStack, Stack<String> undoStack, int step) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (law.equals("undo")) {
			if (curStack.size() == 1) {
				System.out.println("Already at beginning of proof.");
			}
			else {
				undoStack.push(curStack.pop());
				step--;
			}
		}
		
		else if (law.equals("redo")) {
			curStack.push(undoStack.pop());
			step++;
		}
		
		else {
			String result = (String) methods.get(law).invoke(new equivalences(), curStack.peek());
			if (result.equals("No match")) {
				System.out.println("Law does not apply: " + law);
			}
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
		
		System.out.println("Enter the starting expression: ");
		String starting = kb.nextLine();
		proofStack.push(starting);
		
		System.out.println("Enter the ending expression: ");
		String ending = kb.nextLine();
		
		System.out.println("\n---------- Beginning of Proof ----------");
		int i = 1;
		
		while (!proofStack.peek().equals(ending)) {
			System.out.println(i + ". " + proofStack.peek());
			String law = kb.nextLine();
			if (functions.containsKey(law)) {
				i = callFunction(law, functions, proofStack, undoStack, i);
			}
			else {
				System.out.println("No such law: " + law);
			}
			
		}
		System.out.println(i + ". " + proofStack.peek());
		System.out.println("------------- End of Proof -------------");
		kb.close();
	}

}

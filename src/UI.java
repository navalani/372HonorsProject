import java.util.Scanner;
import java.util.Stack;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class UI {
	
	private int addition(int a, int b) {
		return a + b;
	}
	
	private int subtraction(int a, int b) {
		return a - b;
	}
	
	private static void testReflection(HashMap<String, Method> map, String operation, int a, int b) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int result = (int) map.get(operation).invoke(new UI(), a, b);
		System.out.println(result);
	}
	
	private static int callFunction(String law, Stack<String> curStack, Stack<String> undoStack, int step) {
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
			curStack.push(law);
			step++;
		}
		return step;
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Scanner kb = new Scanner(System.in);
		Stack<String> proofStack = new Stack<>();
		Stack<String> undoStack = new Stack<>();
		
		HashMap<String, Method> functions = new HashMap<>();
		functions.put("add", UI.class.getDeclaredMethod("addition", int.class, int.class));
		functions.put("sub", UI.class.getDeclaredMethod("subtraction", int.class, int.class));
		
		testReflection(functions, "add", 5, 4);
		System.out.println("Enter the starting expression: ");
		String starting = kb.next();
		proofStack.push(starting);
		
		System.out.println("Enter the ending expression: ");
		String ending = kb.next();
		
		System.out.println("\n---------- Beginning of Proof ----------");
		int i = 1;
		
		while (!proofStack.peek().equals(ending)) {
			System.out.println(i + ". " + proofStack.peek());
			String law = kb.next();
			i = callFunction(law, proofStack, undoStack, i);
			
		}
		System.out.println(i + ". " + proofStack.peek());
		System.out.println("------------- End of Proof -------------");
		kb.close();
	}

}

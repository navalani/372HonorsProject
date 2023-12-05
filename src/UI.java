/*
 * Author: Josh Samadder
 * 
 * Purpose: UI class that allows the user to enter in starting and ending expressions.
 * 			Using laws implemented in equivalences.java, allows the user to go through the proof
 * 			from the starting expression to the ending expression.
 */


import java.util.Scanner;
import java.util.Stack;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class UI {
	
	private static int callFunction(String law, HashMap<String, Method> methods, Stack<String> curStack, Stack<String> undoStack, Stack<String> usedLaws, Stack<String> undoLaws, int step) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Check if the "law" entered was the instruction "undo"
		if (law.equals("undo")) {
			// Cannot undo if we are at the start of the proof
			if (curStack.size() == 1) {
				System.out.println("Already at beginning of proof.");
			}
			else {
				// Pop the top of the stack and add it to the undo stack if the user wants to use it again
				undoStack.push(curStack.pop());
				undoLaws.push(usedLaws.pop());
				step--;
			}
		}
		
		// Add the top of the undo stack to the current stack, which redos the user's previous input
		else if (law.equals("redo")) {
			// Cannot redo if there is nothing in our stack of undone expressions
			if (undoStack.size() == 0) {
				System.out.println("Cannot redo any further.");
			}
			else {
				// Put back on the proof stack the top of the undo stack
				curStack.push(undoStack.pop());
				usedLaws.push(undoLaws.pop());
				step++;
			}
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
				usedLaws.push(law);
				step++;
			}
		}
		return step;
	}
	
	public static String[] runUI() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		// Create scanner and stacks
		Scanner kb = new Scanner(System.in);
		Stack<String> proofStack = new Stack<>();
		Stack<String> undoStack = new Stack<>();
		Stack<String> usedLaws = new Stack<>();
		Stack<String> undoLaws = new Stack<>();
		
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
		functions.put("cond", equivalences.class.getDeclaredMethod("conditional", String.class));
		functions.put("DMor", equivalences.class.getDeclaredMethod("DMor", String.class));
		functions.put("DMand", equivalences.class.getDeclaredMethod("DMand", String.class));
		
		// User enters the desired starting expression
		System.out.println("Enter the starting expression: ");
		String starting = kb.nextLine();
		proofStack.push(starting);
		
		// User enters the desired ending expression
		System.out.println("Enter the ending expression: ");
		String ending = kb.nextLine();
		
		// Print out the shorthands for the equivalence laws for reference by the user
		System.out.println("\nEquivalences Shorthand:\n");
		for (int i = 0; i < functions.size(); i+=2) {
			String longhand1;
			String equi1 = (String)(functions.keySet().toArray()[i]);	
			
			// Make it more clear what "DMor" and "DMand" stand for
			if (equi1.equals("DMor")) {
				longhand1 = "DeMorgan's Law (Or)";
			}
			
			else if (equi1.equals("DMand")) {
				longhand1 = "DeMorgan's Law (And)";
			}
			
			else {
				longhand1 = functions.get(equi1).getName();
			}
			
			String st1 = equi1 + " - " + longhand1;
			
			// Print out the equivalence shorthand with formatting
			if (i + 1 <= functions.size() - 1) {
				String longhand2;
				String equi2 = (String)(functions.keySet().toArray()[i + 1]);
				
				if (equi2.equals("DMor")) {
					longhand2 = "DeMorgan's Law (Or)";
				}
				
				else if (equi2.equals("DMand")) {
					longhand2 = "DeMorgan's Law (And)";
				}
				
				else {
					longhand2 = functions.get(equi2).getName();
				}
				
				String st2 = equi2 + " - " + longhand2;
				System.out.printf("%-30.30s  %-30.30s%n", st1, st2);
			}
			else {
				System.out.printf("%-30.30s%n", st1);
			}
		}
		
		// Start the proof
		System.out.println("\n---------- Beginning of Proof ----------");
		int i = 1;
		
		// Keep going until the top of the stack is the same as the ending expression
		while (!proofStack.peek().equals(ending)) {
			// Print out the part of the proof we are at
			System.out.println(i + ". " + proofStack.peek());
			// Ask the user to input a law to us on the expression at the top of the stack
			String law = kb.nextLine();
			// Make sure the law entered exists in the hashmap (or is undo/redo)
			if (functions.containsKey(law) || law.equals("undo") || law.equals("redo")) {
				i = callFunction(law, functions, proofStack, undoStack, usedLaws, undoLaws, i);
			}
			else {
				System.out.println("No such law: " + law);
			}
			
		}
		// Print out the ending expression
		System.out.println(i + ". " + proofStack.peek());
		System.out.println("------------- End of Proof -------------");
		// Print out the laws used to get from the starting expression to the ending expression
		System.out.println("Laws used:");
		for (int j = 0; j < usedLaws.size(); j++) {
			System.out.println(functions.get(usedLaws.toArray()[j]).getName());
		}
		kb.close();
		
		// Return an array of the string representation of the proof stack and the used laws for testing 
		// purposes
		return new String[]{proofStack.toString(), usedLaws.toString()};
	}
	
	// User runs the UI file to start
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		UI.runUI();
	}
}

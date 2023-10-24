import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class equivalences {
	
	public String matches(String input, String law) {
		if(law.contains("distributive")) {
			return distributive(input);
		}
		return "No match";
	}
	
	public String identity(String input) {
		String true_regex = "[a-z] and T|T and [a-z]";
		String false_regex = "[a-z] or F|F or [a-z]";
		Pattern pattern1 = Pattern.compile(true_regex);
		Pattern pattern2 = Pattern.compile(false_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		// matcher1.group(0).charAt(0) => variable or T or F
		// matcher1.group(0).charAt(5) => T or F or variable
		if(matcher1.matches()) {
			if(matcher1.group(0).charAt(0) != 'T') {
				return String.valueOf(matcher1.group(0).charAt(0));
			}
			return String.valueOf(matcher1.group(0).charAt(5));
		}
		else if(matcher2.matches()) {
			if(matcher2.group(0).charAt(0) != 'F') {
				return String.valueOf(matcher2.group(0).charAt(0));
			}
			return String.valueOf(matcher2.group(0).charAt(5));
		}
		return "No match";
	}
	
	public String domination(String input) {
		String true_regex = "[a-z] or T|T or [a-z]";
		String false_regex = "[a-z] and F|F and [a-z]";
		Pattern pattern1 = Pattern.compile(true_regex);
		Pattern pattern2 = Pattern.compile(false_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if(matcher1.find()) {
			return "T";
		}
		else if(matcher2.find()) {
			return "F";
		}
		return "No match";
	}
	
	public String idempotent(String input) {
		String and_regex = "[a-z] and [a-z]";
		String or_regex = "[a-z] or [a-z]";
		Pattern pattern1 = Pattern.compile(and_regex);
		Pattern pattern2 = Pattern.compile(or_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		// matcher1.group(0).charAt(0) => variable or T or F
		// matcher1.group(0).charAt(5) => T or F or variable
		if(matcher1.matches() || matcher2.matches()) {
			return String.valueOf(matcher1.group(0).charAt(0));
		}	
		return "No match";
	}
	
	public String double_negation(String input) {
		String regex = "~[(]~[a-z][)]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		
		if(matcher.matches()) {
			return String.valueOf(matcher.group(0).charAt(3));
		}
		return "No match";
	}
	
	public String negation(String input) {
		String and_regex = "[a-z] and ~[a-z]|~[a-z] and [a-z]";
		String or_regex = "[a-z] or ~[a-z]|~[a-z] or [a-z]";
		Pattern pattern1 = Pattern.compile(and_regex);
		Pattern pattern2 = Pattern.compile(or_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if(matcher1.find()) {
			return "F";
		}
		else if(matcher2.find()) {
			return "T";
		}
		return "No match";
	}
	
	public String commutative(String input) {
		String or_regex = "[a-z] or [a-z]";
		String and_regex = "[a-z] and [a-z]";
		Pattern pattern1 = Pattern.compile(or_regex);
		Pattern pattern2 = Pattern.compile(and_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if(matcher1.find()) {
			return String.valueOf(matcher1.group(0).charAt(5)) + " or " 
				   + String.valueOf(matcher1.group(0).charAt(0));
		}
		else if(matcher2.find()) {
			return String.valueOf(matcher2.group(0).charAt(6)) + " and " 
				   + String.valueOf(matcher2.group(0).charAt(0));
		}
		return "No match";
	}
	
	public String associative(String input) {
		String or_regex = "[(][a-z] or [a-z][)] or [a-z]";
		String or_regex1 = "[a-z] or [(][a-z] or [a-z][)]";
		String and_regex = "[(][a-z] and [a-z][)] and [a-z]";
		String and_regex1 = "[a-z] and [(][a-z] and [a-z][)]";
		Pattern pattern1 = Pattern.compile(or_regex);
		Pattern pattern2 = Pattern.compile(or_regex1);
		Pattern pattern3 = Pattern.compile(and_regex);
		Pattern pattern4 = Pattern.compile(and_regex1);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		Matcher matcher3 = pattern3.matcher(input);
		Matcher matcher4 = pattern4.matcher(input);
		
		if(matcher1.find()) {
			return String.valueOf(matcher1.group(0).charAt(1)) + " or (" 
				   + String.valueOf(matcher1.group(0).charAt(6)) + " or "
				   + String.valueOf(matcher1.group(0).charAt(12)) + ")";
		}
		else if(matcher2.find()) {
			return "(" + String.valueOf(matcher2.group(0).charAt(1)) + " or " 
					   + String.valueOf(matcher2.group(0).charAt(6)) + ") or "
					   + String.valueOf(matcher2.group(0).charAt(12));
		}
		else if(matcher3.find()) {
			System.out.println("Matcher 3 find");
			return String.valueOf(matcher3.group(0).charAt(1)) + " and (" 
					   + String.valueOf(matcher3.group(0).charAt(7)) + " and "
					   + String.valueOf(matcher3.group(0).charAt(14)) + ")";
		}
		else if(matcher4.find()) {
			return "(" + String.valueOf(matcher4.group(0).charAt(1)) + " and " 
					   + String.valueOf(matcher4.group(0).charAt(7)) + ") and "
					   + String.valueOf(matcher4.group(0).charAt(14));
		}
		return "No match";
	}
	
	/*
	 * Change 
	 */
	public String distributive(String input) {
		String or_regex1 = "[a-z] or [(][a-z] and [a-z][)]";
		String or_regex2 = "[(][a-z] or [a-z][)] and [(][a-z] or [a-z][)]";
		String and_regex1 = "[a-z] and [(][a-z] or [a-z][)]";
		String and_regex2 = "[(][a-z] and [a-z][)] or [(][a-z] and [a-z][)]";
		Pattern pattern1 = Pattern.compile(or_regex1);
		Pattern pattern2 = Pattern.compile(or_regex2);
		Pattern pattern3 = Pattern.compile(and_regex1);
		Pattern pattern4 = Pattern.compile(and_regex2);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		Matcher matcher3 = pattern3.matcher(input);
		Matcher matcher4 = pattern4.matcher(input);
		
		if(matcher1.find()) {
			return "(" + String.valueOf(matcher1.group(0).charAt(0)) + " or " + 
		           String.valueOf(matcher1.group(0).charAt(6)) + ") and (" + 
				   String.valueOf(matcher1.group(0).charAt(0)) + " or " + 
		           String.valueOf(matcher1.group(0).charAt(12)) + ")";
		}
		else if(matcher2.find()) {
			return String.valueOf(matcher1.group(0).charAt(0)) + " or (" + 
			           String.valueOf(matcher1.group(0).charAt(6)) + " and " + 
			           String.valueOf(matcher1.group(0).charAt(12)) + ")";
		}
		else if(matcher3.find()) {
			return "(" + String.valueOf(matcher1.group(0).charAt(0)) + " and " + 
			           String.valueOf(matcher1.group(0).charAt(6)) + ") or (" + 
					   String.valueOf(matcher1.group(0).charAt(0)) + " and " + 
			           String.valueOf(matcher1.group(0).charAt(12)) + ")";
		}
		else if(matcher4.find()) {
			return String.valueOf(matcher1.group(0).charAt(0)) + " and (" + 
			           String.valueOf(matcher1.group(0).charAt(6)) + " or " + 
			           String.valueOf(matcher1.group(0).charAt(12)) + ")";
		}
		return "No match";
	}
	
	public String absorption(String input) {
		String first = String.valueOf(input.charAt(0));
		String and_regex = first + " and (" + first + " or [a-z][)]";
		String or_regex = first + " or (" + first + " and [a-z][)]";
		Pattern pattern1 = Pattern.compile(and_regex);
		Pattern pattern2 = Pattern.compile(or_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if(matcher1.find() || matcher2.find()) {
			return first;
		}
		return "No match";
	}
}

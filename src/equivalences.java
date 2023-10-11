import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class equivalences {
	
	public String matches(String input) {
		String id = identity(input);
		if(!id.equals("No match")) {
			return id;
		}
		String dom = domination(input);
		if(!dom.equals("No match")) {
			return dom;
		}
		String idem = idempotent(input);
		if(!idem.equals("No match")) {
			return idem;
		}
		String double_neg = double_negation(input);
		if(!double_neg.equals("No match")) {
			return double_neg;
		}
		String neg = negation(input);
		if(!neg.equals("No match")) {
			return neg;
		}
		else {
			return "No match";
		}
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
		String regex = "~(~[a-z])";
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
}

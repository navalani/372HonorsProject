import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class equivalences {
	
	public String expr_regex = "[(]?(\\s*)[A-Z|a-z|T|F](\\s*)(and|or)?(\\s*)[A-Z|a-z|T|F]?(\\s*)[)]?";
	
	public String matches(String input, String law) {
		if(law.contains("identity")) {
			return identity(input);
		}
		if(law.contains("conditional")) {
			return conditional(input);
		}
		if(law.contains("double")) {
			return double_negation(input);
		}
		if(law.contains("DM1")) {
			return DMor(input);
		}
		if(law.contains("DM2")) {
			return DMand(input);
		}
		if(law.contains("commutative")) {
			return commutative(input);
		}
		if(law.contains("expression")) {
			return expression(input);
		}
		return "No match";
	}
	
	public String expression(String input) {
		String regex = "[(]?(\\s*)[a-z|T|F](\\s*)(and|or)?(\\s*)[a-z|T|F]?(\\s*)[)]?";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if(matcher.matches()) {
			return input;
		}
		return "Not an expression";
	}
	
	// Tested
	public String identity(String input) {
		String[] inputArray = input.split("\\(|\\)");
		String true_regex = "(\\s*)[(]?(" + expr_regex + ")*(\\s+)and(\\s+)T[)]?(\\s*)|(\\s*)[(]?T(\\s+)and(\\s+)(" + expr_regex + ")*(\\s*)";
		String false_regex = "(\\s*)[(]?(" + expr_regex + ")*(\\s+)or(\\s+)F[)]?(\\s*)|(\\s*)[(]?F(\\s+)or(\\s+)(" + expr_regex + ")*(\\s*)";
		Pattern pattern1 = Pattern.compile(true_regex);
		Pattern pattern2 = Pattern.compile(false_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
//		String left = inputArray[0];
//		String right = inputArray[2];
//		if(inputArray[0].length() == 0) {
//			left = inputArray[1];
//			right = inputArray[2];
//		}
		if(matcher1.matches()) {
			System.out.println("Matcher 1 matches");
			String variable = matcher1.group(0);
			return inputArray[1];
		}
		else if(matcher2.matches()) {
			return "matcher2";
		}
		return "No match";
	}
	
	//tested
	public String domination(String input) {
		String[] inputArray = input.split("\\s+|\\(|\\)");
		String true_regex = "(\\s*)[a-z](\\s+)or(\\s+)T(\\s*)|(\\s*)T(\\s+)or(\\s+)[a-z](\\s*)";
		String false_regex = "(\\s*)[a-z](\\s+)and(\\s+)F(\\s*)|(\\s*)F(\\s+)and(\\s+)[a-z](\\s*)";
		Pattern pattern1 = Pattern.compile(true_regex);
		Pattern pattern2 = Pattern.compile(false_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		String left = inputArray[0];
		String right = inputArray[2];
		if(inputArray[0].length() == 0) {
			left = inputArray[1];
			right = inputArray[3];
		}
		if(matcher1.find()) {
			return "T";
		}
		else if(matcher2.find()) {
			return "F";
		}
		return "No match";
	}
	
	//tested
	public String idempotent(String input) {
		String[] inputArray = input.split("\\s+|\\(|\\)");
		String and_regex = "(\\s*)[(]?[a-z][)]?(\\s+)and(\\s+)[(]?[a-z][)]?(\\s*)";
		String or_regex = "(\\s*)[(]?[a-z][)]?(\\s+)or(\\s+)[(]?[a-z][)]?(\\s*)";
		Pattern pattern1 = Pattern.compile(and_regex);
		Pattern pattern2 = Pattern.compile(or_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		// matcher1.group(0).charAt(0) => variable or T or F
		// matcher1.group(0).charAt(5) => T or F or variable
		if(matcher1.matches() || matcher2.matches()) {
			return inputArray[1];
		}	
		return "No match";
	}
	
	//tested
	public String double_negation(String input) {
		String[] inputArray = input.split("\\s+|\\(|\\)");
		String regex = "(\\s*)~(\\s*)[(](\\s*)~(\\s*)[a-z](\\s*)[)](\\s*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		
		if(matcher.matches()) {
			return String.valueOf(matcher.group(0).charAt(5));
		}
		return "~(p or ~q)";
	}

	//tested
	public String negation(String input) {
		String and_regex = "(\\s*)[(]?[a-z][)]?(\\s+)and(\\s+)[(]?~(\\s*)[a-z][)]?(\\s*)|(\\s*)[(]?~(\\s*)[a-z][)]?(\\s+)and(\\s+)[(]?[a-z][)]?(\\s*)";
		String or_regex = "(\\s*)[(]?[a-z][)]?(\\s+)or(\\s+)[(]?~(\\s*)[a-z][)]?(\\s*)|(\\s*)[(]?~(\\s*)[a-z][)]?(\\s+)or(\\s+)[(]?[a-z][)]?(\\s*)";
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
	
	//Tested
	public String commutative(String input) {
		String[] inputArray = input.split("\\s+|\\(|\\)");
		String expression = "";
		//String or_regex = "(\\s*)[(]?[a-z][)]?(\\s+)or(\\s+)[(]?[a-z][)]?(\\s*)";
		//String and_regex = "(\\s*)[(]?[a-z][)]?(\\s+)and(\\s+)[(]?[a-z][)]?(\\s*)";
		String regex3 = "\\((\\s*)[a-z](\\s*)and(\\s*)[a-z](\\s*)\\)(\\s*)or(\\s*)\\((\\s*)[a-z](\\s*)and(\\s*)[a-z](\\s*)\\)";
		//Pattern pattern1 = Pattern.compile(or_regex);
		//Pattern pattern2 = Pattern.compile(and_regex);
		Pattern pattern3 = Pattern.compile(regex3);
		//Matcher matcher1 = pattern1.matcher(input);
		//Matcher matcher2 = pattern2.matcher(input);
		Matcher matcher3 = pattern3.matcher(input);
		String left = inputArray[0];
		String right = inputArray[2];
		if(inputArray[0].length() == 0) {
			left = inputArray[1];
			right = inputArray[3];
		}
		//if(matcher1.find()) {
			//return right + " or " 
				//   + left;
		//}
		//if(matcher2.find()) {
//			return right + " and " 
//				   + left;
//		}
		if(matcher3.find()) {
		System.out.println("Matcher 3 found");
		}
		return "No match";
	}
	
	//Tested
	public String associative(String input) {
		String or_regex = "(\\s*)[(](\\s*)[a-z](\\s+)or(\\s+)[a-z](\\s+)[)](\\s+)or(\\s+)[a-z](\\s*)";
		String or_regex1 = "(\\s*)[a-z](\\s+)or(\\s+)[(](\\s*)[a-z](\\s+)or(\\s+)[a-z](\\s*)[)](\\s*)";
		String and_regex = "(\\s*)[(](\\s*)[a-z](\\s+)and(\\s+)[a-z](\\s*)[)](\\s+)and(\\s+)[a-z](\\s*)";
		String and_regex1 = "(\\s*)[a-z](\\s+)and(\\s+)[(](\\s*)[a-z](\\s+)and(\\s+)[a-z](\\s*)[)](\\s*)";
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
	
	//Tested
	public String distributive(String input) {
		String or_regex1 = "(\\s*)[a-z](\\s+)or(\\s+)[(](\\s*)[a-z](\\s+)and(\\s+)[a-z](\\s*)[)](\\s*)";
		String or_regex2 = "(\\s*)[(](\\s*)[a-z](\\s+)or(\\s+)[a-z](\\s*)[)](\\s+)and(\\s+)[(](\\s*)[a-z](\\s+)or(\\s+)[a-z](\\s*)[)](\\s*)";
		String and_regex1 = "(\\s*)[a-z](\\s+)and(\\s+)[(](\\s*)[a-z](\\s+)or(\\s+)[a-z](\\s*)[)](\\s*)";
		String and_regex2 = "(\\s*)[(](\\s*)[a-z](\\s+)and(\\s+)[a-z](\\s*)[)](\\s+)or(\\s+)[(](\\s*)[a-z](\\s+)and(\\s+)[a-z](\\s*)[)](\\s*)";
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
			System.out.println(matcher2.group(0));
			return String.valueOf(matcher2.group(0).charAt(1)) + " or (" + 
			           String.valueOf(matcher2.group(0).charAt(6)) + " and " + 
			           String.valueOf(matcher2.group(0).charAt(14)) + ")";
		}
		else if(matcher3.find()) {
			return "(" + String.valueOf(matcher3.group(0).charAt(0)) + " and " + 
			           String.valueOf(matcher3.group(0).charAt(7)) + ") or (" + 
					   String.valueOf(matcher3.group(0).charAt(0)) + " and " + 
			           String.valueOf(matcher3.group(0).charAt(12)) + ")";
		}
		else if(matcher4.find()) {
			return String.valueOf(matcher4.group(0).charAt(1)) + " and (" + 
			           String.valueOf(matcher4.group(0).charAt(7)) + " or " + 
			           String.valueOf(matcher4.group(0).charAt(14)) + ")";
		}
		return "No match";
	}
	
	//Tested
	public String absorption(String input) {
		String first = String.valueOf(input.charAt(0));
		String and_regex = "(\\s*)" + first + "(\\s+)and(\\s+)[(](\\s*)" + first + "(\\s+)or(\\s+)[a-z](\\s*)[)](\\s*)";
		String or_regex = "(\\s*)" + first + "(\\s+)or(\\s+)[(](\\s*)" + first + "(\\s+)and(\\s+)[a-z](\\s*)[)](\\s*)";
		Pattern pattern1 = Pattern.compile(and_regex);
		Pattern pattern2 = Pattern.compile(or_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if(matcher1.find() || matcher2.find()) {
			return first;
		}
		return "No match";
	}
	
	// Tested
	public String conditional(String input) {
		String[] inputArray = input.split("\\s+");
		String regex = "(\\s*)~?[a-z](\\s+)->(\\s+)~?[a-z](\\s*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		String left = inputArray[0];
		String right = inputArray[2];
		if(left.length() > 1) {
			left = "(" + left + ")";
		}
//		if(right.length() > 1) {
//			right = "(" + right + ")";
//		}
		if(matcher.find()){
			return "~" + left + " or " + right;
		}
		return "No match";
	}
	
	public String DMor(String input){
		String[] inputArray1 = input.split("\\s+|\\(|\\)|\\~");
		String[] inputArray = new String[inputArray1.length];
		int j = 0;
		for(int i = 0; i < inputArray1.length; i++) {
			if(inputArray1[i].length() != 0) {
				inputArray[j] = inputArray1[i]; 
				j++;
			}
		}
		String regex1 = "(\\s*)~(\\s*)[(](\\s*)[a-z](\\s*)or(\\s*)[a-z](\\s*)[)](\\s*)";
		String regex2 = "(\\s*)~(\\s*)[a-z](\\s*)and(\\s*)~(\\s*)[a-z](\\s*)";
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		if(matcher1.find()){
			return "~" + inputArray[0] + " and ~" + inputArray[2];
		}
		else if(matcher2.find()){
			return "~(" + inputArray[0] + " or " + inputArray[2] + ")";
		} 
		return "No match";
	}
	
	public String DMand(String input){
		String[] inputArray1 = input.split("\\s+|\\(|\\)|\\~");
		String[] inputArray = new String[inputArray1.length];
		int j = 0;
		for(int i = 0; i < inputArray1.length; i++) {
			if(inputArray1[i].length() != 0) {
				inputArray[j] = inputArray1[i]; 
				j++;
			}
		}
		String regex1 = "(\\s*)~(\\s*)[(](\\s*)[a-z](\\s*)and(\\s*)[a-z](\\s*)[)](\\s*)";
		String regex2 = "(\\s*)~(\\s*)[a-z](\\s*)or(\\s*)~(\\s*)[a-z](\\s*)";
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		if(matcher1.find()){
			return "~" + inputArray[0] + " or ~" + inputArray[2];
		}
		else if(matcher2.find()){
			return "~(" + inputArray[0] + " and " + inputArray[2] + ")";
		} 
		return "No match";
	}
}

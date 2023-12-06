/*
* Name: Nick Avalani
* File: equivalences.java
* Description: This file describes the backend of the project and uses regex
* to match an equivalence with a law and apply that law to the equivalence by
* translate it to a new logical equivalence
*/

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class equivalences {

	// Regex for an expression that can be used as an operand
	public String expr_regex = "\\(+\\s*.*\\s*\\)+";

	// Negate law:  ~T = F and ~F = T
	public String negate(String input) {
		String true_regex = "(~\\s*T)";
		String false_regex = "(~\\s*F)";
		Pattern pattern1 = Pattern.compile(true_regex);
		Pattern pattern2 = Pattern.compile(false_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if(matcher1.find()) {
			return matcher1.replaceAll("F");
		}
		else if(matcher2.find()) {
			return matcher2.replaceAll("T");
		}
		return "No match";
	}

	/* 
 	 * Identity law: p /\ T = p 
	 *               p \/ F = p 
	 */
	public String identity(String input) {
		String true_regex = "\\s*(" + expr_regex + ")\\s+and\\s+(T)\\s*|\\s*(T)\\s+and\\s+(" + expr_regex
				+ ")\\s*|\\s*([a-z]+|T|F)\\s+and\\s+(T)\\s*|\\s*(T)\\s+and\\s+([a-z]+|T|F)\\s*";
		String false_regex = "\\s*(" + expr_regex + ")\\s+or\\s+(F)\\s*|\\s*(F)\\s+or\\s+(" + expr_regex
				+ ")\\s*|\\s*([a-z]+|T|F)\\s+or\\s+(F)\\s*|\\s*(F)\\s+or\\s+([a-z]+|T|F)\\s*";
		Pattern pattern1 = Pattern.compile(true_regex);
		Pattern pattern2 = Pattern.compile(false_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		String left = "";
		String right = "";
		
		if (matcher1.find()) {
			int groupCount = matcher1.groupCount();
			for(int i = 1; i <= groupCount; i++) {
				if(matcher1.group(i) != null) {
					left = matcher1.group(i);
					right = matcher1.group(i+1);
					break;
				}
			}
			String result = "";
			if(left.equals("T")) {
				result = matcher1.replaceAll(right);
			}
			else {
				result = matcher1.replaceAll(left);
			}
			return result;
		}
		else if(matcher2.find()) {
			int groupCount = matcher2.groupCount();
			for(int i = 1; i <= groupCount; i++) {
				if(matcher2.group(i) != null) {
					left = matcher2.group(i);
					right = matcher2.group(i+1);
					break;
				}
			}
			String result = "";
			if(left.equals("F")) {
				result = matcher2.replaceAll(right);
			}
			else {
				result = matcher2.replaceAll(left);
			}
			return result;
		}
		return "No match";
	}
	
	/*
	 * Domination law: p /\ F = F
	 *                 p \/ T = T
	 */
	public String domination(String input) {
		String true_regex = "[(]?\\s*(" + expr_regex + ")\\s+or\\s+(T)\\s*[)]?|[(]?\\s*(T)\\s+or\\s+(" + expr_regex
				+ ")\\s*[)]?|[(]?\\s*([a-zA-Z~]+|T|F)\\s+or\\s+(T)\\s*[)]?|[(]?\\s*(T)\\s+or\\s+([a-zA-Z~]+|T|F)\\s*[)]?";
		String false_regex = "[(]?\\s*(" + expr_regex + ")\\s+and\\s+(F)\\s*[)]?|[(]?\\s*(F)\\s+and\\s+(" + expr_regex
				+ ")\\s*[)]?|[(]?\\s*([a-z]+|T|F)\\s+and\\s+(F)\\s*[)]?|[(]?\\s*(F)\\s+and\\s+([a-z]+|T|F)\\s*[)]?";
		Pattern pattern1 = Pattern.compile(true_regex);
		Pattern pattern2 = Pattern.compile(false_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if (matcher1.find()) {
			String result = matcher1.replaceAll("T");
			return result;
		}
		else if(matcher2.find()) {
			String result = matcher2.replaceAll("F");
			return result;
		}
		return "No match";
	}

	/*
	 * Idempotent law: p /\ p = p
	 *                 p \/ p = p
	 */
	public String idempotent(String input) {
		String and_regex = "\\(?\\s*([a-zA-Z~|T|F]+)\\s+and\\s+\\1\\s*\\)?"; 
		String or_regex = "\\(?\\s*([a-zA-Z~|T|F]+)\\s+or\\s+\\1\\s*\\)?";
		Pattern pattern1 = Pattern.compile(and_regex);
		Pattern pattern2 = Pattern.compile(or_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if (matcher1.find()) {
			String val = matcher1.group(1);
			String result = "";
			result = matcher1.replaceAll(val);
			return result;
		}
		else if(matcher2.find()) {
			String val = matcher2.group(1);
			String result = "";
			result = matcher2.replaceAll(val);
			return result;
		}
		return "No match";
	}

	/*
	 * Double negation law: ~(~p) = p
	 */
	public String double_negation(String input) {
		String str_regex = "\\~\\s*\\(\\s*\\~([a-z|T|F])\\)"; 
		Pattern pattern1 = Pattern.compile(str_regex);
		Matcher matcher1 = pattern1.matcher(input);
		
		if (matcher1.find()) {
			String val = matcher1.group(1);
			String result = "";
			result = matcher1.replaceAll(val);
			return result;
		}
		
		return "No match";
	}

	/*
	* Negation law: p \/ ~p = T
        *               p /\ ~p = F
	*/
	public String negation(String input) {
		String or_regex = "([a-zA-Z~()|T|F]+)\\s+or\\s+~\\1|~([a-zA-Z()~|T|F]+)\\s+or\\s+\\1";
		String and_regex = "([a-zA-Z~()|T|F]+)\\s+and\\s+~\\1|([a-zA-Z~()|T|F]+)\\s+and\\s+~\\1"; 
		
		Pattern pattern1 = Pattern.compile(or_regex);
		Pattern pattern2 = Pattern.compile(and_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if (matcher1.find()) {
			String result = matcher1.replaceAll("T");
			return result;
		}
		else if(matcher2.find()) {
			String result = matcher2.replaceAll("F");
			return result;
		}
		return "No match";
	}

	/*
	 * Commutative law: p /\ q = q /\ p
	 *                  p \/ q = q \/ p
	 */
	public String commutative(String input) {
		String or_regex = "([a-zA-Z|T|F])\\s+or\\s+([a-zA-Z|T|F])";
		String and_regex = "([a-zA-Z|T|F])\\s+and\\s+([a-zA-Z|T|F])";
		
		Pattern pattern1 = Pattern.compile(or_regex);
		Pattern pattern2 = Pattern.compile(and_regex);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if (matcher1.find()) {
			String val = matcher1.group(2) + " or " + matcher1.group(1);
			String result = matcher1.replaceAll(val);
			return result;
		}
		else if(matcher2.find()) {
			String val = matcher2.group(2) + " and " + matcher1.group(1);
			String result = matcher2.replaceAll(val);
			return result;
		}
		return "No match";
	}

	/*
	 * Associative law: (p /\ q) /\ r = p /\ (q /\ r)
	 *                  (p \/ q) \/ r = p \/ (q \/ r)
	 */
	public String associative(String input) {
		String and_regex1 = "\\((\\s*[a-zA-Z|T|F])\\s+and\\s+(\\s*[a-zA-Z|T|F])\\s*\\)\\s+and\\s+([a-zA-Z|T|F])";
		String and_regex2 = "([a-zA-Z|T|F])\\s+and\\s+\\(\\s*([a-zA-Z|T|F])\\s+and\\s+([a-zA-Z|T|F])\\s*\\)";
		String or_regex1 = "\\(([a-zA-Z|T|F])\\s+or\\s+([a-zA-Z|T|F])\\)\\s+or\\s+([a-zA-Z|T|F])";
		String or_regex2 = "([a-zA-Z|T|F])\\s+or\\s+\\(\\s*([a-zA-Z|T|F])\\s+or\\s+([a-zA-Z|T|F])\\s*\\)";
		
		Pattern pattern1 = Pattern.compile(and_regex1);
		Pattern pattern2 = Pattern.compile(and_regex2);
		Pattern pattern3 = Pattern.compile(or_regex1);
		Pattern pattern4 = Pattern.compile(or_regex2);
		
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		Matcher matcher3 = pattern3.matcher(input);
		Matcher matcher4 = pattern4.matcher(input);
		
		if (matcher1.find()) {
			String val1 = matcher1.group(1);
			String val2 = matcher1.group(2);
			String val3 = matcher1.group(3);
			String val = val1 + " and (" + val2 + " and " + val3 + ")";
			String result = matcher1.replaceAll(val);
			return result;
		}
		else if(matcher2.find()) {
			String val1 = matcher2.group(1);
			String val2 = matcher2.group(2);
			String val3 = matcher2.group(3);
			String val = "( "+ val1 + " and " + val2 + ") and " + val3;
			String result = matcher2.replaceAll(val);
			return result;
		}
		else if(matcher3.find()) {
			String val1 = matcher3.group(1);
			String val2 = matcher3.group(2);
			String val3 = matcher3.group(3);
			String val = val1 + " or (" + val2 + " or " + val3 + ")";
			String result = matcher3.replaceAll(val);
			return result;
		}
		else if(matcher4.find()) {
			String val1 = matcher4.group(1);
			String val2 = matcher4.group(2);
			String val3 = matcher4.group(3);
			String val = "( "+ val1 + " or " + val2 + ") or " + val3;
			String result = matcher4.replaceAll(val);
			return result;
		}
		return "No match";
	}

	/*
	 * Distributive law: p \/ (q /\ r) = (p \/ q) /\ (p \/ r)
	 *                  (p \/ q) \/ r = p \/ (q \/ r)
	 */
	public String distributive(String input) {
		String and_regex1 = "([a-zA-Z|T|F])\\s+or\\s+\\(\\s*([a-zA-Z|T|F])\\s+and\\s+([a-zA-Z|T|F])\\s*\\)";
		String and_regex2 = "\\(\\s*([a-zA-Z|T|F])\\s+or\\s+([a-zA-Z|T|F])\\s*\\)\\s+and\\s+\\(\\s*\\1\\s+or\\s+([a-zA-Z|T|F])\\s*\\)";
		String or_regex1 = "([a-zA-Z|T|F])\\s+and\\s+\\(\\s*([a-zA-Z|T|F])\\s+or\\s+([a-zA-Z|T|F])\\s*\\)";
		String or_regex2 = "\\(\\s*([a-zA-Z|T|F])\\s+and\\s+([a-zA-Z|T|F])\\s*\\)\\s+or\\s+\\(\\s*\\1\\s+and\\s+([a-zA-Z|T|F])\\s*\\)";
		
		Pattern pattern1 = Pattern.compile(and_regex1);
		Pattern pattern2 = Pattern.compile(and_regex2);
		Pattern pattern3 = Pattern.compile(or_regex1);
		Pattern pattern4 = Pattern.compile(or_regex2);
		
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		Matcher matcher3 = pattern3.matcher(input);
		Matcher matcher4 = pattern4.matcher(input);
		
		if (matcher1.find()) {
			String val1 = matcher1.group(1);
			String val2 = matcher1.group(2);
			String val3 = matcher1.group(3);
			String val = "(" + val1 + " or " + val2 + ") and (" + val1 + " or " + val3 + ")";
			String result = matcher1.replaceAll(val);
			return result;
		}
		else if(matcher2.find()) {
			String val1 = matcher2.group(1);
			String val2 = matcher2.group(2);
			String val3 = matcher2.group(3);
			String val = val1 + " or (" + val2 + " and " + val3 + ")";
			String result = matcher2.replaceAll(val);
			return result;
		}
		else if(matcher3.find()) {
			String val1 = matcher3.group(1);
			String val2 = matcher3.group(2);
			String val3 = matcher3.group(3);
			String val = "(" + val1 + " and " + val2 + ") or (" + val1 + " and " + val3 + ")";
			String result = matcher3.replaceAll(val);
			return result;
		}
		else if(matcher4.find()) {
			String val1 = matcher4.group(1);
			String val2 = matcher4.group(2);
			String val3 = matcher4.group(3);
			String val = val1 + " and (" + val2 + " or " + val3 + ")";
			String result = matcher4.replaceAll(val);
			return result;
		}
		return "No match";
	}
	
	/*
	 * Absorption law: p \/ (p /\ q) = p
	 *                 p /\ (p \/ q) = p
	 */
	public String absorption(String input) {
		String and_regex = "([a-zA-Z|T|F])\\s+and\\s+\\(\\s*\\1\\s+or\\s+([a-zA-Z|T|F])\\s*\\)"; 
		String or_regex = "([a-zA-Z|T|F])\\s+or\\s+\\(\\s*\\1\\s+and\\s+([a-zA-Z|T|F])\\s*\\)";
		
		Pattern pattern1 = Pattern.compile(and_regex);
		Pattern pattern2 = Pattern.compile(or_regex);
		
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if (matcher1.find() || matcher2.find()) {
			return matcher1.replaceAll(matcher1.group(1).trim());
		}
		return "No match";
	}

	/*
	 * De Morgan's or law: ~(p \/ q) = ~p /\ ~q
	 */
	public String DMor(String input){
		String regex1 = "~\\s*\\(\\s*([a-zA-Z~()|T|F]+)\\s*or\\s*([a-zA-Z~()|T|F]+)\\s*\\)";
		String regex2 = "~\\s*([a-zA-z~()|T|F]+)\\s*and\\s*~\\s*([a-zA-Z~()|T|F]+)\\s*";
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		if(matcher1.find()){
			String left = matcher1.group(1).trim();
			String right = matcher1.group(2).trim();
			String val = "";
			if(matcher1.group(1).trim().charAt(0) == '~') {
				left = "(" + matcher1.group(1) + ")";
			}
			if(matcher1.group(2).trim().charAt(0) == '~') {
				right = "(" + matcher1.group(2) + ")";
			}
			val = "~" + left + " and ~" + right;
			return matcher1.replaceAll(val);
		}
		else if(matcher2.find()){
			String val = "";
			String left = matcher2.group(1);
			String right = matcher2.group(2);
			if(matcher2.group(1).trim().charAt(0) == '~') {
				left = "(" + matcher2.group(1) + ")";
			}
			if(matcher2.group(2).trim().charAt(0) == '~') {
				right = "(" + matcher2.group(2) + ")";
			}
			val = "~(" + left + " or " + right + ")";
			return matcher2.replaceAll(val);
		} 
		return "No match";
	}

	/*
	 * De Morgan's and law: ~(p /\ q) = ~p \/ ~q
	 */
	public String DMand(String input){
		String regex1 = "~\\s*\\(\\s*([a-zA-Z~|T|F]+)\\s*and\\s*([a-zA-Z~|T|F]+)\\s*)\\)";
		String regex2 = "~\\s*([a-zA-z~|T|F]+)\\s*or\\s*~\\s*([a-zA-Z~|T|F]+)\\s*";
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		if(matcher1.find()){
			String val = "";
			String left = matcher1.group(1);
			String right = matcher1.group(2);
			if(matcher1.group(1).trim().charAt(0) == '~') {
				left = "(" + matcher1.group(1) + ")";
			}
			if(matcher1.group(2).trim().charAt(0) == '~') {
				right = "(" + matcher1.group(2) + ")";
			}
			val = "~" + left + " or ~" + right;
			return matcher1.replaceAll(val);
		}
		else if(matcher2.find()){
			String val = "";
			String left = matcher2.group(1);
			String right = matcher2.group(2);
			if(matcher2.group(1).trim().charAt(0) == '~') {
				left = "(" + matcher2.group(1) + ")";
			}
			if(matcher2.group(2).trim().charAt(0) == '~') {
				right = "(" + matcher2.group(2) + ")";
			}
			val = "~(" + left + " and " + right + ")";
			return matcher2.replaceAll(val);
		} 
		return "No match";
	}
	
	/*
	 * Conditional law: p -> q = ~p \/ q
	 */
	public String conditional(String input) {
		String regex1 = "([a-zA-Z~|T|F]+)\\s+->\\s+([a-zA-Z~|T|F]+)";
		String regex2 = "([a-zA-Z~|T|F]+)\\s+or\\s+([a-zA-Z~|T|F]+)";
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Matcher matcher1 = pattern1.matcher(input);
		Matcher matcher2 = pattern2.matcher(input);
		
		if(matcher1.find()){
			String val = "";
			String left = matcher1.group(1);
			String right = matcher1.group(2);
			if(matcher1.group(1).trim().charAt(0) == '~') {
				left = "(" + matcher1.group(1) + ")";
			}
			val = "~" + left + " or " + right;
			return matcher1.replaceAll(val);
		}
		else if(matcher2.find()){
			String val = "";
			String left = matcher2.group(1);
			String right = matcher2.group(2);
			if(matcher2.group(2).trim().charAt(0) == '~') {
				right = "(" + matcher2.group(2) + ")";
			}
			val = "~(" + left + " -> " + right + ")";
			return matcher2.replaceAll(val);
		}
		return "No match";
	}
}

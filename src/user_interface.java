import static org.junit.Assert.assertEquals;

import java.util.Arrays;

public class user_interface {
	public static void main(String[] args) {
		equivalences eq1 = new equivalences();
		String input = "((p and q or r)) and T";
		
		String output = eq1.matches(input, "identity");
		System.out.println(output);

	}
}



/*
* Old tester file
*/
public class user_interface {
	public static void main(String[] args) {
		equivalences eq1 = new equivalences();
		String input = "p or (q and r)"; 
		System.out.println(eq1.matches(input, "distributive"));
	}
}

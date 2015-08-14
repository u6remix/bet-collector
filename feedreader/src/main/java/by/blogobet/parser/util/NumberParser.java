package by.blogobet.parser.util;

public class NumberParser {
	public static double parseDouble(String input){
		input = preHandle(input);
		return Double.parseDouble(input);
	}
	
	public static int parseInt(String input){
		input = preHandle(input);
		return Integer.parseInt(input);		
	}
	
	private static String preHandle(String input){
		input = input.trim().replaceAll("\u00A0", "").replaceAll("%","");
		if(input.startsWith("+")){
			//result +3.25
			input = input.substring(1);
		}else if(input.contains("/")){
			// stake 4/10 for ex.
			input = input.substring(0, 1);
		}
		return input;
	}

}

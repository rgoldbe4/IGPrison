package us.ignitiongaming.util.convert;

import java.text.NumberFormat;

public class CurrencyConverter {

	public static String convertToCurrency(double amount) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(amount);
	}
	
	public static String convertToCurrency(String amount) {
		double amt = 0.0;
		amount = ChatConverter.stripCurrency(amount);
		amount = ChatConverter.removeSpecialCharacters(amount);
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		if (amount.equalsIgnoreCase("free")) 
			return formatter.format(amt);
		else {
			amt = Double.parseDouble(amount);
			return formatter.format(amt);
		}
	}
}

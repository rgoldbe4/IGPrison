package us.ignitiongaming.util.convert;

import java.text.NumberFormat;

public class CurrencyConverter {

	public static String convertToCurrency(double amount) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(amount);
	}
}

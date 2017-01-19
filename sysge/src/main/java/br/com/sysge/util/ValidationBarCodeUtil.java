package br.com.sysge.util;

public class ValidationBarCodeUtil {

	public static boolean isValidarCodigoDeBarras(String codigoDeBarras) {
		int digit;
		int calculated;
		String ean;
		String checkSum = "131313131313";
		int sum = 0;
		if (codigoDeBarras.length() == 8 || codigoDeBarras.length() == 13) {
			digit = Integer.parseInt("" + codigoDeBarras.charAt(codigoDeBarras.length() - 1));
			ean = codigoDeBarras.substring(0, codigoDeBarras.length() - 1);
			for (int i = 0; i <= ean.length() - 1; i++) {
				sum += (Integer.parseInt("" + ean.charAt(i))) * (Integer.parseInt("" + checkSum.charAt(i)));
			}
			calculated = 10 - (sum % 10);
			return (digit == calculated);
		} else {
			return false;
		}
	}
}

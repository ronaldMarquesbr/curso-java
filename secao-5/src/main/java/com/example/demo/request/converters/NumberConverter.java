package com.example.demo.request.converters;

public class NumberConverter {

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null) return false;

        String formattedNumber = strNumber.replaceAll(",", ".");
        return formattedNumber.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static Double convertToDouble(String strNumber) {;
        String formattedNumber = strNumber.replace(",", ".");

        return Double.parseDouble(formattedNumber);
    }

}

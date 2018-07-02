package com.utils;

import java.util.Random;

public class NumberHandler {
    public static String numberFormat(double number) {
        /*DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(number);*/
        return String.format("%.2f", number);
    }

    public static String numberFormat(String number) throws RuntimeException {
        return numberFormat(Double.parseDouble(number));
    }

    public static int generateRandomInteger() {
        Random random = new Random();
        return random.nextInt();
    }

    public static long generateRandomLong() {
        Random random = new Random();
        return random.nextLong();
    }
}

package ru.lyubimov.cryptotracker.utils;

public final class StringUtils {

    private StringUtils() {}

    //99cryptocoins зачем-то добавляют "," для разделения в %, от них нужно избавляться.
    public static String clearStringToDouble(String param) {
        StringBuilder sb = new StringBuilder();
        for (char c : param.toCharArray()) {
            if (c == ',') {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}

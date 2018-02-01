package ru.lyubimov.cryptotracker;

import android.content.res.Resources;
import android.widget.TextView;

/**
 * Created by Alex on 31.01.2018.
 */

public class ViewTextUtils {

    public static void setupChangeView(Resources resources, TextView textView, String param) {
        if(param != null) {
            Double numParam = Double.valueOf(param);
            String textToView;
            if(numParam >= 0) {
                textView.setTextColor(resources.getColor(R.color.colorGreen));
                textToView = "+" + param + "%";
                textView.setText(textToView);
            } else {
                textView.setTextColor(resources.getColor(R.color.colorRed));
                textToView = param + "%";
                textView.setText(textToView);
            }
        } else {
            textView.setText("-");
            textView.setTextColor(resources.getColor(R.color.colorBlack));
        }
    }
}

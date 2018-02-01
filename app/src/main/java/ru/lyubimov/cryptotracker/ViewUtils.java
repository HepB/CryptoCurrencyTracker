package ru.lyubimov.cryptotracker;

import android.content.res.Resources;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * Created by Alex on 31.01.2018.
 */

public class ViewUtils {

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
        }
    }

    public static void setupCurCostView(Resources resources, TextView textView, String param) {
        if(param != null) {
            Double numParam = Double.valueOf(param);
            String textToView = resources.getString(R.string.price_usd_n, String.format(resources.getConfiguration().locale,"%-10.2f", numParam));
            textView.setText(textToView);
            textView.setTextColor(resources.getColor(R.color.colorBlack));
        } else {
            String text = resources.getString(R.string.price_usd_n, "-");
            textView.setText(text);
        }
    }

    public static void setupBtcCostView(Resources resources, TextView textView, String param) {
        if (param != null) {
            Double numParam = Double.valueOf(param);
            String textToView = resources.getString(R.string.price_btc_n, String.format(resources.getConfiguration().locale, "%-10.9f", numParam));
            textView.setText(textToView);
            textView.setTextColor(resources.getColor(R.color.colorBlack));
        } else {
            String text = resources.getString(R.string.price_btc_n, "-");
            textView.setText(text);
        }
    }

    public static void setupVolumeView(Resources resources, int stringId, TextView textView, String param) {
        if (param != null) {
            Double numParam = Double.valueOf(param);
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            String textToView = resources.getString(stringId, numberFormat.format(numParam));
            textView.setText(textToView);
            textView.setTextColor(resources.getColor(R.color.colorBlack));
        } else {
            String text = resources.getString(stringId, "-");
            textView.setText(text);
        }
    }
}

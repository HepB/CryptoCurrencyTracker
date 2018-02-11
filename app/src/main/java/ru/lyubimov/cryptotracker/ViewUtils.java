package ru.lyubimov.cryptotracker;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * Created by Alex on 31.01.2018.
 */

class ViewUtils {

    static void setupTitleView(TextView textView, String curName, String curSymbol, Integer num) {
        StringBuilder sb = new StringBuilder();
        if(num != null) {
            sb.append(num).append(".");
        }
        sb.append(curName);
        if (curSymbol != null) {
            sb.append("(").append(curSymbol).append(")");
        }
        textView.setText(sb.toString().toUpperCase());
    }

    static void setupChangeView(Resources resources, TextView textView, String param) {
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
            textView.setTextColor(resources.getColor(R.color.colorBlack));
            textView.setText("-");
        }
    }

    static void setupCurCostView(Resources resources, TextView textView, String param) {
        if(param != null) {
            Double numParam = Double.valueOf(param);
            String format;
            if(numParam > 0.01) {
                format = "%-10.4f";
            } else {
                format = "%-10.2f";
            }
            String textToView = resources.getString(R.string.price_usd, String.format(resources.getConfiguration().locale, format, numParam));
            textView.setText(textToView);
            textView.setTextColor(resources.getColor(R.color.colorBlack));
        } else {
            String text = resources.getString(R.string.price_usd, "-");
            textView.setText(text);
        }
    }

    static void setupBtcCostView(Resources resources, TextView textView, String param) {
        if (param != null) {
            Double numParam = Double.valueOf(param);
            String textToView = resources.getString(R.string.price_btc, String.format(resources.getConfiguration().locale, "%-10.9f", numParam));
            textView.setText(textToView);
            textView.setTextColor(resources.getColor(R.color.colorBlack));
        } else {
            String text = resources.getString(R.string.price_btc, "-");
            textView.setText(text);
        }
    }

    static void setupVolumeView(Resources resources, int stringId, TextView textView, String param) {
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

    static void setCurViewIcon(Resources resources, TextView curIconView, AssetFetcher assetFetcher, String symbol) {
        Drawable curIcon = assetFetcher.getDrawableFromAssets(symbol);
        if (curIcon != null) {
            curIconView.setBackground(curIcon);
            curIconView.setText(null);
        } else {
            curIcon = resources.getDrawable(R.drawable.def_n);
            curIconView.setText(symbol);
            curIconView.setTextColor(resources.getColor(R.color.colorWhite));
            curIconView.setBackground(curIcon);
        }
    }
}

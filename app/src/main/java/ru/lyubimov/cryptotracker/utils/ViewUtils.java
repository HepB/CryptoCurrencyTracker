package ru.lyubimov.cryptotracker.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import ru.lyubimov.cryptotracker.AssetFetcher;
import ru.lyubimov.cryptotracker.R;

/**
 * Created by Alex on 31.01.2018.
 */

public final class ViewUtils {

    private ViewUtils() {}

    public static void setupTitleView(TextView textView, String curName, String curSymbol, Integer num) {
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

    public static void setupChangeView(Context context, TextView textView, String param) {
        if(param != null) {
            String textToView;
            if(param.charAt(0) == '-') {
                textView.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
                textToView = param + "%";
                textView.setText(textToView);
            } else {
                textView.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
                textToView = "+" + param + "%";
                textView.setText(textToView);
            }
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            textView.setText("-");
        }
    }

    public static void setupCurCostView(Context context, TextView textView, String param) {
        if(param != null) {
            Double numParam = Double.valueOf(param);
            String format;
            if(numParam > 0.01) {
                format = "%.2f";
            } else {
                format = "%.4f";
            }
            String textToView = context.getString(R.string.price_usd, String.format(Locale.getDefault(), format, numParam), "$");
            textView.setText(textToView);
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
        } else {
            String text = context.getString(R.string.price_usd, "-", null);
            textView.setText(text);
        }
    }

    public static void setToBtcChangeView(Context context, TextView textView, String param) {
        if (param != null) {
            String textToView = context.getString(R.string.price_btc, param + "%");
            textView.setText(textToView);
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
        } else {
            String text = context.getString(R.string.price_btc, "-");
            textView.setText(text);
        }
    }

    public static void setupVolumeView(Context context, int stringId, TextView textView, String param) {
        if (param != null) {
            Double numParam = Double.valueOf(param);
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            String textToView = context.getString(stringId, numberFormat.format(numParam), "$");
            textView.setText(textToView);
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
        } else {
            String text = context.getString(stringId, "-", null);
            textView.setText(text);
        }
    }

    public static void setCurViewIcon(Resources resources, TextView curIconView, AssetFetcher assetFetcher, String symbol) {
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

    public static void showError(Fragment currentFragment, String errorText) {
        if(currentFragment.isAdded()) {
            Toast.makeText(currentFragment.getActivity(), errorText, Toast.LENGTH_SHORT).show();
        }
    }
}

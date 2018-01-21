package ru.lyubimov.cryptotracker;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Alex on 21.01.2018.
 */

public class AssetFetcher {
    private static final String TAG = "AssetFetcher";

    private AssetManager mAssetManager;

    public AssetFetcher(AssetManager assetManager) {
        mAssetManager = assetManager;
    }

    @Nullable
    public Drawable getDrawableFromAssets(String fileName) {
        Drawable drawable = null;
        String path = assetConstants.ICON_PATH + "/" + fileName.toLowerCase() + assetConstants.PNG;
        try {
            InputStream is = mAssetManager.open(path);
            if (is != null) {
                drawable = Drawable.createFromStream(is, null);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return drawable;
    }

    public interface assetConstants {
        String PNG = ".png";
        String ICON_PATH = "crypto_currency_icon";
    }
}

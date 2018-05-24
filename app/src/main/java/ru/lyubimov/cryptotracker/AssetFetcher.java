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

    private static final String PNG = ".png";
    private static final String ICON_PATH = "crypto_currency_icon";
    private static final String SLASH = "/";

    private final AssetManager mAssetManager;

    public AssetFetcher(AssetManager assetManager) {
        mAssetManager = assetManager;
    }

    @Nullable
    public Drawable getDrawableFromAssets(String fileName) {
        Drawable drawable = null;
        String path = ICON_PATH + SLASH;
        InputStream is = null;
        try {
            is = mAssetManager.open(path + fileName.toLowerCase() + PNG);
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
        return drawable;
    }
}

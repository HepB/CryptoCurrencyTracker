package ru.lyubimov.cryptotracker;

import java.util.Comparator;

/**
 * Created by Alex on 23.01.2018.
 */

public class CryptoCurrencyComparator {

    private static final String TAG = "CryptoCurrencyComp";

    public static Comparator<CryptoCurrency> compareByRank(){
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sRangA = a.getRank();
                String sRangB = b.getRank();
                return Integer.compare(Integer.valueOf(sRangA), Integer.valueOf(sRangB));
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByVolume() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sVolumeA = a.getDayVolumeCur();
                String sVolumeB = b.getDayVolumeCur();

                if (sVolumeA == null && sVolumeB != null) {
                    return 1;
                } else if(sVolumeA != null && sVolumeB == null) {
                    return -1;
                } else if (sVolumeA == null && sVolumeB == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sVolumeB), Double.valueOf(sVolumeA));
                }
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByCost() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sCostA = a.getPriceCur();
                String sCostB = b.getPriceCur();

                if (sCostA == null && sCostB != null) {
                    return 1;
                } else if(sCostA != null && sCostB  == null) {
                    return -1;
                } else if (sCostA == null && sCostB == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sCostB), Double.valueOf(sCostA));
                }
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByRise() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sHourChangeA = a.getHourPercentChange();
                String sHourChangeB = b.getHourPercentChange();

                if (sHourChangeA == null && sHourChangeB != null) {
                    return 1;
                } else if(sHourChangeA != null && sHourChangeB == null) {
                    return -1;
                } else if (sHourChangeA == null && sHourChangeB == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sHourChangeB), Double.valueOf(sHourChangeA));
                }
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByFallingDown() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sHourChangeA = a.getHourPercentChange();
                String sHourChangeB = b.getHourPercentChange();

                if (sHourChangeA == null && sHourChangeB != null) {
                    return 1;
                } else if(sHourChangeA != null && sHourChangeB == null) {
                    return -1;
                } else if (sHourChangeA == null && sHourChangeB == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sHourChangeA), Double.valueOf(sHourChangeB));
                }
            }
        };
    }
}

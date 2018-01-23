package ru.lyubimov.cryptotracker;

import java.util.Comparator;

/**
 * Created by Alex on 23.01.2018.
 */

public class CryptoCurrencyComparator {

    public static Comparator<CryptoCurrency> compareByRank(){
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sRangA = a.getRank();
                String sRangB = b.getRank();

                if(sRangA == null) {
                    return 1;
                }
                if(sRangB == null) {
                    return -1;
                }
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

                if(sVolumeA == null) {
                    return 1;
                }
                if(sVolumeB == null) {
                    return -1;
                }
                return Double.compare(Double.valueOf(sVolumeB), Double.valueOf(sVolumeA));
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByCost() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sCostA = a.getPriceCur();
                String sCostB = b.getPriceCur();

                if(sCostA == null) {
                    return 1;
                }
                if(sCostB == null) {
                    return -1;
                }
                return Double.compare(Double.valueOf(sCostB), Double.valueOf(sCostA));
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByRise() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sHourChangeA = a.getHourPercentChange();
                String sHourChangeB = b.getHourPercentChange();

                if(sHourChangeA == null) {
                    return 1;
                }
                if(sHourChangeB == null) {
                    return -1;
                }
                return Double.compare(Double.valueOf(sHourChangeB), Double.valueOf(sHourChangeA));
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByFallingDown() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sHourChangeA = a.getHourPercentChange();
                String sHourChangeB = b.getHourPercentChange();

                if(sHourChangeA == null) {
                    return 1;
                }
                if(sHourChangeB == null) {
                    return -1;
                }
                return Double.compare(Double.valueOf(sHourChangeA), Double.valueOf(sHourChangeB));
            }
        };
    }
}

package ru.lyubimov.cryptotracker.utils;

import java.util.Comparator;

import ru.lyubimov.cryptotracker.model.nine.CCurrency;

/**
 * Created by Alex on 23.01.2018.
 */

public final class ComparatorUtils {

    private ComparatorUtils() {}

    private static final String TAG = "CryptoCurrencyComp";

    public static Comparator<CCurrency> compareByRank(){
        return new Comparator<CCurrency>() {
            @Override
            public int compare(CCurrency a, CCurrency b) {
                String sRangA = a.getRankingMarketCap();
                String sRangB = b.getRankingMarketCap();
                return Integer.compare(Integer.valueOf(sRangA), Integer.valueOf(sRangB));
            }
        };
    }

    public static Comparator<CCurrency> compareByVolume() {
        return new Comparator<CCurrency>() {
            @Override
            public int compare(CCurrency a, CCurrency b) {
                String sVolumeA = a.getRankingVolume();
                String sVolumeB = b.getRankingVolume();
                return Integer.compare(Integer.valueOf(sVolumeA), Integer.valueOf(sVolumeB));
            }
        };
    }

    public static Comparator<CCurrency> compareByCost() {
        return new Comparator<CCurrency>() {
            @Override
            public int compare(CCurrency a, CCurrency b) {
                String sCostA = a.getPriceUsd();
                String sCostB = b.getPriceUsd();

                if (sCostA == null && sCostB != null) {
                    return 1;
                } else if(sCostA != null && sCostB  == null) {
                    return -1;
                } else if (sCostA == null) {
                    return 0;
                } else {
                    //99creyptocoins зачем-то для разделения используют ",", приходится фильтровать.
                    sCostA = sCostA.replace(",", "");
                    sCostB = sCostB.replace(",", "");
                    return Double.compare(Double.valueOf(sCostB), Double.valueOf(sCostA));
                }
            }
        };
    }

    public static Comparator<CCurrency> compareByHourRise() {
        return new Comparator<CCurrency>() {
            @Override
            public int compare(CCurrency a, CCurrency b) {
                String sHourChangeA = a.getPercentChange1h();
                String sHourChangeB = b.getPercentChange1h();

                if (sHourChangeA == null && sHourChangeB != null) {
                    return 1;
                } else if(sHourChangeA != null && sHourChangeB == null) {
                    return -1;
                } else if (sHourChangeA == null) {
                    return 0;
                } else {
                    sHourChangeA = sHourChangeA.replace(",", "");
                    sHourChangeB = sHourChangeB.replace(",", "");
                    return Double.compare(Double.valueOf(sHourChangeB), Double.valueOf(sHourChangeA));
                }
            }
        };
    }

    public static Comparator<CCurrency> compareByHourFallingDown() {
        return new Comparator<CCurrency>() {
            @Override
            public int compare(CCurrency a, CCurrency b) {
                String sHourChangeA = a.getPercentChange1h();
                String sHourChangeB = b.getPercentChange1h();

                if (sHourChangeA == null && sHourChangeB != null) {
                    return 1;
                } else if(sHourChangeA != null && sHourChangeB == null) {
                    return -1;
                } else if (sHourChangeA == null) {
                    return 0;
                } else {
                    sHourChangeA = sHourChangeA.replace(",", "");
                    sHourChangeB = sHourChangeB.replace(",", "");
                    return Double.compare(Double.valueOf(sHourChangeA), Double.valueOf(sHourChangeB));
                }
            }
        };
    }


    public static Comparator<CCurrency> compareByDayRise() {
        return new Comparator<CCurrency>() {
            @Override
            public int compare(CCurrency a, CCurrency b) {
                String sDayChangeA = a.getPercentChange24h();
                String sDayChangeB = b.getPercentChange24h();

                if (sDayChangeA == null && sDayChangeB != null) {
                    return 1;
                } else if(sDayChangeA != null && sDayChangeB == null) {
                    return -1;
                } else if (sDayChangeA == null) {
                    return 0;
                } else {
                    sDayChangeA = sDayChangeB.replace(",", "");
                    sDayChangeB = sDayChangeB.replace(",", "");
                    return Double.compare(Double.valueOf(sDayChangeB), Double.valueOf(sDayChangeA));
                }
            }
        };
    }

    public static Comparator<CCurrency> compareByDayFallingDown() {
        return new Comparator<CCurrency>() {
            @Override
            public int compare(CCurrency a, CCurrency b) {
                String sDayChangeA = a.getPercentChange24h();
                String sDayChangeB = b.getPercentChange24h();

                if (sDayChangeA == null && sDayChangeB != null) {
                    return 1;
                } else if(sDayChangeA != null && sDayChangeB == null) {
                    return -1;
                } else if (sDayChangeA == null) {
                    return 0;
                } else {
                    sDayChangeA = sDayChangeB.replace(",", "");
                    sDayChangeB = sDayChangeB.replace(",", "");
                    return Double.compare(Double.valueOf(sDayChangeA), Double.valueOf(sDayChangeB));
                }
            }
        };
    }

    public static Comparator<CCurrency> compareByWeekRise() {
        return new Comparator<CCurrency>() {
            @Override
            public int compare(CCurrency a, CCurrency b) {
                String sWeekChangeA = a.getPercentChange7d();
                String sWeekChangeB = b.getPercentChange7d();

                if (sWeekChangeA == null && sWeekChangeB != null) {
                    return 1;
                } else if(sWeekChangeA != null && sWeekChangeB == null) {
                    return -1;
                } else if (sWeekChangeA == null) {
                    return 0;
                } else {
                    sWeekChangeA = sWeekChangeA.replace(",", "");
                    sWeekChangeB = sWeekChangeB.replace(",", "");
                    return Double.compare(Double.valueOf(sWeekChangeB), Double.valueOf(sWeekChangeA));
                }
            }
        };
    }

    public static Comparator<CCurrency> compareByWeekFallingDown() {
        return new Comparator<CCurrency>() {
            @Override
            public int compare(CCurrency a, CCurrency b) {
                String sWeekChangeA = a.getPercentChange7d();
                String sWeekChangeB = b.getPercentChange7d();

                if (sWeekChangeA == null && sWeekChangeB != null) {
                    return 1;
                } else if(sWeekChangeA != null && sWeekChangeB == null) {
                    return -1;
                } else if (sWeekChangeA == null) {
                    return 0;
                } else {
                    sWeekChangeA = sWeekChangeA.replace(",", "");
                    sWeekChangeB = sWeekChangeB.replace(",", "");
                    return Double.compare(Double.valueOf(sWeekChangeA), Double.valueOf(sWeekChangeB));
                }
            }
        };
    }
}

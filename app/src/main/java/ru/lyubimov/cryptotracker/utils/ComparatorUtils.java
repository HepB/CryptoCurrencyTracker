package ru.lyubimov.cryptotracker.utils;

import java.util.Comparator;

import ru.lyubimov.cryptotracker.model.nine.CryptoCurrency;

/**
 * Created by Alex on 23.01.2018.
 */

public final class ComparatorUtils {

    private ComparatorUtils() {}

    public static Comparator<CryptoCurrency> compareByRank(){
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sRangA = a.getRankingMarketCap();
                String sRangB = b.getRankingMarketCap();
                return Integer.compare(Integer.valueOf(sRangA), Integer.valueOf(sRangB));
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByVolume() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sVolumeA = a.getRankingVolume();
                String sVolumeB = b.getRankingVolume();
                return Integer.compare(Integer.valueOf(sVolumeA), Integer.valueOf(sVolumeB));
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByCost() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
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

    public static Comparator<CryptoCurrency> compareByHourRise() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
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

    public static Comparator<CryptoCurrency> compareByHourFallingDown() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
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


    public static Comparator<CryptoCurrency> compareByDayRise() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sDayChangeA = a.getPercentChange24h();
                String sDayChangeB = b.getPercentChange24h();

                if (sDayChangeA == null && sDayChangeB != null) {
                    return 1;
                } else if(sDayChangeA != null && sDayChangeB == null) {
                    return -1;
                } else if (sDayChangeA == null) {
                    return 0;
                } else {
                    sDayChangeA = sDayChangeA.replace(",", "");
                    sDayChangeB = sDayChangeB.replace(",", "");
                    return Double.compare(Double.valueOf(sDayChangeB), Double.valueOf(sDayChangeA));
                }
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByDayFallingDown() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sDayChangeA = a.getPercentChange24h();
                String sDayChangeB = b.getPercentChange24h();

                if (sDayChangeA == null && sDayChangeB != null) {
                    return 1;
                } else if(sDayChangeA != null && sDayChangeB == null) {
                    return -1;
                } else if (sDayChangeA == null) {
                    return 0;
                } else {
                    sDayChangeA = sDayChangeA.replace(",", "");
                    sDayChangeB = sDayChangeB.replace(",", "");
                    return Double.compare(Double.valueOf(sDayChangeA), Double.valueOf(sDayChangeB));
                }
            }
        };
    }

    public static Comparator<CryptoCurrency> compareByWeekRise() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
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

    public static Comparator<CryptoCurrency> compareByWeekFallingDown() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
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

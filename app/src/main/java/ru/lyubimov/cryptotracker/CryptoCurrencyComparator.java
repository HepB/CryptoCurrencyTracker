package ru.lyubimov.cryptotracker;

import java.util.Comparator;

import ru.lyubimov.cryptotracker.model.CryptoCurrency;

/**
 * Created by Alex on 23.01.2018.
 */

class CryptoCurrencyComparator {

    private static final String TAG = "CryptoCurrencyComp";

    static Comparator<CryptoCurrency> compareByRank(){
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sRangA = a.getRank();
                String sRangB = b.getRank();
                return Integer.compare(Integer.valueOf(sRangA), Integer.valueOf(sRangB));
            }
        };
    }

    static Comparator<CryptoCurrency> compareByVolume() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sVolumeA = a.getDayVolumeCur();
                String sVolumeB = b.getDayVolumeCur();

                if (sVolumeA == null && sVolumeB != null) {
                    return 1;
                } else if(sVolumeA != null && sVolumeB == null) {
                    return -1;
                } else if (sVolumeA == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sVolumeB), Double.valueOf(sVolumeA));
                }
            }
        };
    }

    static Comparator<CryptoCurrency> compareByCost() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sCostA = a.getPriceCur();
                String sCostB = b.getPriceCur();

                if (sCostA == null && sCostB != null) {
                    return 1;
                } else if(sCostA != null && sCostB  == null) {
                    return -1;
                } else if (sCostA == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sCostB), Double.valueOf(sCostA));
                }
            }
        };
    }

    static Comparator<CryptoCurrency> compareByHourRise() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sHourChangeA = a.getHourPercentChange();
                String sHourChangeB = b.getHourPercentChange();

                if (sHourChangeA == null && sHourChangeB != null) {
                    return 1;
                } else if(sHourChangeA != null && sHourChangeB == null) {
                    return -1;
                } else if (sHourChangeA == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sHourChangeB), Double.valueOf(sHourChangeA));
                }
            }
        };
    }

    static Comparator<CryptoCurrency> compareByHourFallingDown() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sHourChangeA = a.getHourPercentChange();
                String sHourChangeB = b.getHourPercentChange();

                if (sHourChangeA == null && sHourChangeB != null) {
                    return 1;
                } else if(sHourChangeA != null && sHourChangeB == null) {
                    return -1;
                } else if (sHourChangeA == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sHourChangeA), Double.valueOf(sHourChangeB));
                }
            }
        };
    }


    static Comparator<CryptoCurrency> compareByDayRise() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sDayChangeA = a.getDayPercentChange();
                String sDayChangeB = b.getDayPercentChange();

                if (sDayChangeA == null && sDayChangeB != null) {
                    return 1;
                } else if(sDayChangeA != null && sDayChangeB == null) {
                    return -1;
                } else if (sDayChangeA == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sDayChangeB), Double.valueOf(sDayChangeA));
                }
            }
        };
    }

    static Comparator<CryptoCurrency> compareByDayFallingDown() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sDayChangeA = a.getDayPercentChange();
                String sDayChangeB = b.getDayPercentChange();

                if (sDayChangeA == null && sDayChangeB != null) {
                    return 1;
                } else if(sDayChangeA != null && sDayChangeB == null) {
                    return -1;
                } else if (sDayChangeA == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sDayChangeA), Double.valueOf(sDayChangeB));
                }
            }
        };
    }

    static Comparator<CryptoCurrency> compareByWeekRise() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sWeekChangeA = a.getWeekPercentChange();
                String sWeekChangeB = b.getWeekPercentChange();

                if (sWeekChangeA == null && sWeekChangeB != null) {
                    return 1;
                } else if(sWeekChangeA != null && sWeekChangeB == null) {
                    return -1;
                } else if (sWeekChangeA == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sWeekChangeB), Double.valueOf(sWeekChangeA));
                }
            }
        };
    }

    static Comparator<CryptoCurrency> compareByWeekFallingDown() {
        return new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency a, CryptoCurrency b) {
                String sWeekChangeA = a.getWeekPercentChange();
                String sWeekChangeB = b.getWeekPercentChange();

                if (sWeekChangeA == null && sWeekChangeB != null) {
                    return 1;
                } else if(sWeekChangeA != null && sWeekChangeB == null) {
                    return -1;
                } else if (sWeekChangeA == null) {
                    return 0;
                } else {
                    return Double.compare(Double.valueOf(sWeekChangeA), Double.valueOf(sWeekChangeB));
                }
            }
        };
    }
}

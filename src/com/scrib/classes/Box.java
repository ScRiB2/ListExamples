package com.scrib.classes;

import com.scrib.utils.ArrayListUtils;

public class Box {

    public enum Selectors {SMALLER_SIDE, BIG_SIDE};

    private final int l;
    private final int w;
    private final int h;


    public Box(int l, int w, int h) {
        this.l = l;
        this.w = w;
        this.h = h;
    }

    public int getVolume() {
        return l * w * h;
    }

    public int getSmallerSide() {
        int temp = Math.min(l, w);
        return Math.min(temp, h);
    }

    public int getBigSide() {
        int temp = Math.max(l, w);
        return Math.max(temp, h);
    }

    private int compareBySmallerSide(Box other) {
        if (getSmallerSide() > other.getSmallerSide()) return 1;
        else if (getSmallerSide() < other.getSmallerSide()) return -1;
        return 0;
    }

    private int compareByBigSide(Box other) {
        if (getBigSide() > other.getBigSide()) return 1;
        else if (getBigSide() < other.getBigSide()) return -1;
        return 0;
    }

    public int compareBy(Selectors selector, ArrayListUtils.Order order, Box other) {
        int result;
        switch (selector) {
            case BIG_SIDE -> {
                result = compareByBigSide(other);
            }
            case SMALLER_SIDE -> {
                result = compareBySmallerSide(other);
            }
            default -> result = 0;
        }

        if (order == ArrayListUtils.Order.DESC) {
            result = -1 * result;
        }
        return result;
    }

    @Override
    public String toString() {
        return l + " " + w + " " + h;
    }
}

package com.estebes.usefulcrops.util;

import java.util.Random;

public class XorShiftRandom extends Random {
    private long x;
    private int maxNumber;

    public XorShiftRandom(int maxNumber) {
        this(maxNumber, System.currentTimeMillis());
    }

    public XorShiftRandom(int maxNumber, long seed) {
        this.x = seed;
        this.maxNumber = maxNumber;
    }

    @Override
    protected int next(int nbits) {
        x ^= (x << 21);
        x ^= (x >>> 35);
        x ^= (x << 4);
        int out = (int) x % maxNumber;
        return (out < 0) ? -out : out;
    }
}

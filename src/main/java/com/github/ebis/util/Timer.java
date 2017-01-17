package com.github.ebis.util;

public class Timer{
    public long time;

    public void timer(Runnable runnable){
        long begin = System.nanoTime();
        runnable.run();
        time = System.nanoTime() - begin;
    }

    public long time(){
        return time;
    }

    public double time(Unit toUnit){
        return toUnit.convert(time(), Unit.NANO_SECONDS);
    }
}

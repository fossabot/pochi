package com.github.pochi.runner.scripts.helper;

import com.github.pochi.runner.birthmarks.BirthmarkComparator;
import com.github.pochi.runner.birthmarks.PairMaker;
import com.github.pochi.runner.birthmarks.comparators.Comparisons;
import com.github.pochi.runner.birthmarks.entities.Birthmarks;
import com.github.pochi.runner.config.Configuration;

public class Comparator {
    private BirthmarkComparator comparator;
    private Configuration config;

    public Comparator(BirthmarkComparator comparator, Configuration config){
        this.comparator = comparator;
        this.config = config;
    }

    public Comparisons compare(Birthmarks birthmarks, PairMaker maker){
        return comparator.compare(birthmarks, maker, config);
    }
}

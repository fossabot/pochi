package com.github.pochi.runner.birthmarks.comparators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.pochi.birthmarks.comparators.AbstractComparator;
import com.github.pochi.birthmarks.comparators.ComparatorType;
import com.github.pochi.birthmarks.config.Configuration;
import com.github.pochi.birthmarks.entities.Birthmark;
import com.github.pochi.birthmarks.entities.Element;

public abstract class IndexComparator extends AbstractComparator {
    public IndexComparator(ComparatorType type, Configuration config){
        super(type, config);
    }

    Set<Element> union(Birthmark left, Birthmark right){
        Set<Element> union = new HashSet<>();
        left.forEach(union::add);
        right.forEach(union::add);
        return union;
    }

    List<Element> intersect(Birthmark left, Birthmark right){
        List<Element> intersection = new ArrayList<>();
        right.filter(left::contains)
        .forEach(intersection::add);
        return intersection;
    }
}
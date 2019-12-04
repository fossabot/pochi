package jp.cafebabe.pochi.izumo.builders;

import java.util.Objects;

import jp.cafebabe.pochi.birthmarks.config.Configuration;
import jp.cafebabe.pochi.birthmarks.pairs.PairMatcher;
import jp.cafebabe.pochi.birthmarks.pairs.PairMatcherType;
import jp.cafebabe.pochi.izumo.RoundRobinPairMatcher;

public class RoundRobinPairMatcherBuilder<T> extends AbstractPairMatcherBuilder<T>{
    public RoundRobinPairMatcherBuilder(boolean withSamePair) {
        super(createType(withSamePair));
    }

    public RoundRobinPairMatcherBuilder() {
        this(false);
    }

    @Override
    public PairMatcher<T> build(Configuration config) {
        if(Objects.equals(type(), RoundRobinPairMatcher.TYPE))
            return new RoundRobinPairMatcher<>(type(), false, config);
        return new RoundRobinPairMatcher<>(type(), true, config);
    }

    private static PairMatcherType createType(boolean withSamePair) {
        if(withSamePair)
            return RoundRobinPairMatcher.SAME_PAIR_TYPE;
        return RoundRobinPairMatcher.TYPE;
    }
}
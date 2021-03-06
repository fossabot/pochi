package jp.cafebabe.pochi.pairs.builders;

import jp.cafebabe.birthmarks.config.Configuration;
import jp.cafebabe.birthmarks.pairs.PairMatcher;
import jp.cafebabe.birthmarks.pairs.PairMatcherBuilder;
import jp.cafebabe.birthmarks.pairs.PairMatcherType;

public abstract class AbstractPairMatcherBuilder<T> implements PairMatcherBuilder<T> {
    private PairMatcherType type;

    public AbstractPairMatcherBuilder(PairMatcherType type) {
        this.type = type;
    }

    @Override
    public final PairMatcherType type() {
        return type;
    }

    @Override
    public abstract PairMatcher<T> build(Configuration config);
}

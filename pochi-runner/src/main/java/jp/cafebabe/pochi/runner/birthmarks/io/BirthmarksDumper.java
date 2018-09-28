package jp.cafebabe.pochi.runner.birthmarks.io;

import java.io.PrintWriter;
import java.util.StringJoiner;

import jp.cafebabe.pochi.birthmarks.entities.Birthmark;
import jp.cafebabe.pochi.birthmarks.entities.Birthmarks;
import jp.cafebabe.pochi.birthmarks.entities.Metadata;

public class BirthmarksDumper extends AbstractDumper<Birthmarks>{
    public BirthmarksDumper(PrintWriter out) {
        super(out);
    }

    public void print(Birthmarks birthmarks) {
        birthmarks.stream()
        .forEach(this::print);
        flush();
    }

    public void print(Birthmark birthmark) {
        Metadata source = birthmark.metadata();
        out().printf("%s,%s%n", source, dump(birthmark));
    }

    private String dump(Birthmark birthmark) {
        StringJoiner joiner = new StringJoiner(",");
        birthmark.forEach(element -> joiner.add(element.toString()));
        return joiner.toString();
    }
}
package com.github.ebis.birthmarks.entities;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.github.kunai.entries.ClassName;

public class Birthmark implements Serializable{
    private static final long serialVersionUID = -2383836180204233756L;

    private Metadata metadata;
    private Elements elements;

    public Birthmark(Metadata metadata, Elements elements){
        this.metadata = metadata;
        this.elements = elements;
    }

    public void forEach(Consumer<Element> consumer){
        elements.forEach(consumer);
    }

    public void forEach(Predicate<Element> predicate, Consumer<Element> consumer){
        elements.forEach(predicate, consumer);
    }

    public boolean is(ClassName name){
        return metadata.hasSameName(name);
    }

    public Metadata metadata(){
        return metadata;
    }
}

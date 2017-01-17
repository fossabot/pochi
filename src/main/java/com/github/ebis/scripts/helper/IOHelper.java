package com.github.ebis.scripts.helper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.ebis.birthmarks.entities.ExtractionResults;
import com.github.ebis.birthmarks.io.Dumper;
import com.github.kunai.entries.KunaiException;
import com.github.kunai.source.DataSource;
import com.github.kunai.source.factories.DefaultDataSourceFactory;

public class IOHelper {
    private PrintWriter out;

    public IOHelper(){
        this(new PrintWriter(System.out));
    }

    public IOHelper(PrintWriter out){
        this.out = out;
    }

    public DataSource open(File file) throws IOException, KunaiException{
        DefaultDataSourceFactory factory = new DefaultDataSourceFactory();
        return factory.build(file);
    }

    public DataSource open(Path path) throws IOException, KunaiException{
        DefaultDataSourceFactory factory = new DefaultDataSourceFactory();
        return factory.build(path);
    }

    public DataSource open(String path) throws IOException, KunaiException{
        return open(Paths.get(path));
    }

    public void print(ExtractionResults set){
        Dumper dumper = new Dumper(out);
        dumper.print(set);
    }
}
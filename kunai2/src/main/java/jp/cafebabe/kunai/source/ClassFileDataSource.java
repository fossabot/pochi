package jp.cafebabe.kunai.source;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import jp.cafebabe.kunai.entries.ClassName;
import jp.cafebabe.kunai.entries.Entry;
import jp.cafebabe.kunai.entries.PathEntry;

public class ClassFileDataSource extends AbstractDataSource implements PathResolver{
    private ClassName name = null;

    public ClassFileDataSource(Path path){
        super(path);
    }

    @Override
    public Stream<Entry> stream() {
        return Stream.of(new PathEntry(base(), this));
    }

    @Override
    public void close() throws IOException {
        // do nothing.
    }

    @Override
    public InputStream openStream(Path path) throws IOException {
        return Files.newInputStream(path);
    }

    @Override
    public ClassName parseClassName(Path path) {
        if(name == null)
            name = extractClassName(path);
        return name;
    }

    private ClassName extractClassName(Path path){
        try{
            return resolveClassName(path);
        } catch(IOException e){ }
        return null;
    }
}

package jp.cafebabe.kunai.sink;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import jp.cafebabe.kunai.entries.Entry;
import jp.cafebabe.kunai.entries.KunaiException;

public class ClassFileDataSink extends AbstractDataSink {
    private Path path;

    public ClassFileDataSink(Path path){
        this.path = path;
    }

    Path path(){
        return path;
    }

    @Override
    public void consume(InputStream in, Entry entry) throws KunaiException {
        createDirectories(path().getParent());
        try {   Files.copy(in, path());   }
        catch (IOException e) {
            throw new KunaiException(e.getMessage());
        }
    }

    protected void createDirectories(Path path){
        if(!Files.exists(path))
            createDirectoriesImpl(path);
    }

    private void createDirectoriesImpl(Path path){
        try{ Files.createDirectories(path); }
        catch(IOException e){ }
    }
}

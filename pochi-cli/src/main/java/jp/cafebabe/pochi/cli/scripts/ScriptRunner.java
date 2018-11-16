package jp.cafebabe.pochi.cli.scripts;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import jp.cafebabe.pochi.birthmarks.config.Configuration;
import jp.cafebabe.pochi.birthmarks.config.ItemKey;
import jp.cafebabe.pochi.birthmarks.config.ItemValue;
import jp.cafebabe.pochi.cli.scripts.helper.BirthmarkSystemHelper;
import jp.cafebabe.pochi.cli.scripts.helper.IOHelper;
import jp.cafebabe.pochi.cli.scripts.helper.SystemInfoHelper;
import jp.cafebabe.pochi.util.LogHelper;

public class ScriptRunner {
    public static final String DEFAULT_SCRIPT_ENGINE_NAME = "JavaScript";

    private ScriptEngine engine;

    public ScriptRunner(ScriptEngine engine, Configuration configuration){
        this.engine = engine;
        registerVariables(configuration);
        initialize(getClass().getResource("/js/initializer.js"));
        initializeUserScript(configuration.property(ItemKey.of("initialize.script")));
    }

    private void registerVariables(Configuration configuration){
        engine.put("config", configuration);
        engine.put("fs",     new IOHelper());
        engine.put("sys",    new SystemInfoHelper());
        engine.put("bmsys",  new BirthmarkSystemHelper());
    }

    private void initialize(URL location) {
        try {
            engine.eval("load('" + location + "')");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    private void initializeUserScript(Optional<ItemValue> value) {
        value.map(ItemValue::toString)
        .flatMap(this::toUrl).ifPresent(v -> initialize(v));
    }

    private Optional<URL> toUrl(String value) {
        try {
            return Optional.of(Paths.get(value).toUri().toURL());
        } catch (MalformedURLException e) {
            return Optional.empty();
        }
    }

    public void perform(Reader in) throws IOException{
        try{
            engine.eval(in);
        } catch(ScriptException e){
            LogHelper.warn(this, e);
        }
    }

    public void oneLiner(String script) throws ScriptException{
        PrintWriter out = new PrintWriter(System.out);
        Optional<Object> object = Optional.ofNullable(engine.eval(script));
        object.ifPresent(out::println);
    }

    public void runsScript(String[] arguments) throws IOException, ScriptException{
        engine.put("argv", arguments);
        engine.put("filename", arguments[0]);
        try(Reader in = openReader(arguments[0])){
            engine.eval(in);
        }
    }

    private Reader openReader(String fileName) throws IOException{
        return new FileReader(fileName);
    }

    public void startInteraction() throws IOException{
        try{
            runInteractiveMode(buildLineReader(buildTerminal()));
        }
        catch(EndOfFileException e){
            // ignore exception, because it is finish of this application.
        }
    }

    private Terminal buildTerminal() throws IOException{
        return TerminalBuilder.builder()
                .system(true)
                .build();
    }

    private LineReader buildLineReader(Terminal terminal){
        return LineReaderBuilder.builder()
                .terminal(terminal)
                .appName("pochi")
                .completer(new JavaScriptKeywordsCompleter())
                .build();
    }

    private void runInteractiveMode(LineReader reader){
        PrintWriter out = reader.getTerminal().writer();
        out.printf("ScriptRunner start (%s)%n", engine.getFactory().getEngineName());

        String line;
        while((line = reader.readLine("pochi> ")) != null){
            interactEachLine(line, out);
        }
    }

    private void interactEachLine(String line, PrintWriter out) {
        try{
            Optional<Object> object = Optional.ofNullable(engine.eval(line));
            object.ifPresent(out::println);
        } catch(EndOfFileException e){
            throw e;
        } catch(Exception e){
            out.printf("%s: %s%n", e.getClass().getName(), e.getMessage());
        }
        out.flush();
    }
}
---
title: ":newspaper: What is pochi"
date: 2020-08-28
draft: false
weight: 50
---

## :key: Key idea

Almost traditional birthmark systems are usually simple input and output.
For example, an user specifies the type of target birthmark, $p$ and $q$ as input, and obtains result as output.
The process of such system is hard to use.
Because, when the user would perform some process (ex. filtering results), it requires the update of birthmark system.

In general, the process of birthmarking is composed of extraction phase, and comparison phase.
Also, the both phases are performed to huge amount of programs.

Therefore, the birthmark system accept the script file as input, and the user describes extraction and comparison of birthmarks in the script file.
If the user would perform filtering process, it is easy to perform the process.

{{< gototop >}}

## :fork_and_knife: Usage

### :runner: CLI Interface

```sh
pochi [OPTIONS] [SCRIPT_FILE [ARGV...]]
OPTIONS
    -c, --classpath <CLASSPATH>      specifies classpaths for Groovy (JVM) separated with colon (:).
    -C, --config <CONFIG_FILE>       specifies configuration file.
    -e, --expression <EXPRESSION>    specifies command line script.

    -h, --help                       prints this message.
SCRIPT_FILE [ARGV...]
    Groovy script file name and its arguments.
    If no script files and no expression were given, pochi runs on interactive mode.
```

#### Script file

The script files are parsed by the Groovy.
For more detail, see [:ant: Examples](../examples).

### :whale: Docker

Container images of **pochi** for Docker are:

* `tamada/pochi`
    * `2.0.0`, `latest`
        * accept only `.groovy` script files.
    * `1.0.0`
        * accept only `.js` script files.

To run **pochi** on Docker container OS

```sh
$ docker run --rm -v "$PWD":/home/pochi tamada/pochi:2.0.0 [OPTIONS] [SCRIPT [ARGV...]]
```

* `OPTIONS`: the options for **pochi**.
* `[SCRIPT [ARGV...]]`: script file for **pochi**.
* `--rm`: remove the container after running.
* `-v "$PWD":/home/pochi`: share volume `$PWD` in host OS to `/home/pochi` in the container OS.
    * `$PWD` must be the absolute path.

#### Environments in the docker container

* `USER`: `pochi`
* `WORKDIR`: `/home/pochi`
* `JAVA_HOME`: `/opt/java` (symbolic link from `/opt/openjdk-11-minimal`)
* `GROOVY_HOME`: `/opt/groovy` (symbolic link from `/opt/groovy-3.0.5`)
* `POCHI_HOME`: `/opt/pochi` (symbolic link from `/opt/pochi-2.0.0`)

{{< gototop >}}

## :swimmer: The birthmarking flow

The birthmarking process in **pochi** shows in Figure 1.

{{< image src="images/birthmarking_process.svg" caption="Figure 1: birthmarking process in pochi" >}}

The followings are the description of the nodes and edges in the flowchart.

* `Classes`
    * is the Java class files, almost included in the jar files, and the directory.
      **pochi** treats them as the object of [`DataSource`](../apidocs/jp.cafebabe.kunai/jp/cafebabe/kunai/source/DataSource.html)
* `Birthmarks`
    * shows the extracted characteristics from Java class files by certain method.
      In the **pochi**, [`Birthmarks`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/entities/Birthmarks.html) and [`Birthmark`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/entities/Birthmark.html) show the extracted birthmarks.
      The string representation of [`Birthmark`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/entities/Birthmark.html) (the return value of `toString` method) is CSV format, therefore, we can store them into some csv file.
* `Pairs`
    * shows the pair list of extracted birthmarks.
      **pochi** shows these objects as [`Pair<Birthmark>`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/entities/Pair.html).
* `Comparisons`
    * represents the comparison results of birthmarks by the certain similarity calculation algorithm.
      **pochi** shows these objects as [`Comparisons`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/comparators/Comparisons.html) and [`Comparison`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/comparators/Comparison.html).
* `Extractor`
    * extracts birthmarks from given class files by the certain algorithm.
      In the script file, `pochi.extractor("ALGORITHM_NAME")` obtains the instance of [`Extractor`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/extractors/Extractor.html).
* `Parser`
    * parses the given csv file and build [`Birthmarks`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/entities/Birthmarks.html) object.
      To get the instance of [`BirthmarkParser`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/BirthmarkParser.html), call `pochi.parser()` method in the script file.
* `PairMatcher`
    * matches the pair of birthmarks by some algorithm.
      `pochi.matcher("ALGORITHM_NAME")` returns the instance of [`PairMatcher`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/pairs/PairMatcher.html)
* `Comparator`
    * calculates similarity between two given birthmaks by the certain algorithm.
      In the script file, `pochi.comparator("ALGORITHM_NAME")` gets the instance of [`Comparator`](../apidocs/jp.cafebabe.birthmarks/jp/cafebabe/birthmarks/comparators/Comparator.html)

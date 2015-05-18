# log4j_trunctate_poc
poc testing how to truncate stack traces


# Intro 

Log4j has since 1.2.16 shipped with an `EnhancedPatternLayout` class.

`EnhancedPatternLayout` is capable of truncating stack trace messages, so they don't fill up log files.

To enable this functionality, per output, you will need to modify switch from `PatternLayout` to `EnhancedPatternLayout` and add an extra flag to the `ConversionPattern`.


As an example changing this:

```
log4j.appender.CONSOLE.layout=org.apache.log4j.EnhancedPatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=%-4r [%t] %-5p %c %x \u2013 %m%n %throwable{short}
```

To this:

```
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=%-4r [%t] %-5p %c %x \u2013 %m%n
```

As a result, a logged stack trace generated like this:

```
throw new Exception(new Exception("Inner Exception"));
```

Will instead of generating this:

```
0    [ie.hunt.Main.main()] ERROR root  – An error
 java.lang.Exception: java.lang.Exception: Inner Exception
        at ie.hunt.Main.main(Main.java:14)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:483)
        at org.codehaus.mojo.exec.ExecJavaMojo$1.run(ExecJavaMojo.java:293)
        at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.Exception: Inner Exception
        ... 7 more
```

Generate this:
```
1    [ie.hunt.Main.main()] ERROR root null – An error
 java.lang.Exception: java.lang.Exception: Inner Exception
```

Nice, eh?

# Running the example

```
mvn clean compile exec:java -Dexec.mainClass="ie.hunt.Main"
```

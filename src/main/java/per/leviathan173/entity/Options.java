package per.leviathan173.entity;

import org.kohsuke.args4j.Option;

public class Options {
    @Option(name = "-d", usage = "Set debug level, 0=none 1=normal 2=all massage")
    public static int DEBUG_LEVEL = 1;

    @Option(name = "--enable-email", usage = "Enable email function")
    public static boolean ENABLE_MAIL = false;

    @Option(name="-m", usage = "Set maximum number of articles which rss file content")
    public static int MAX_NUMBER_OF_ARTICLES = 10;
}



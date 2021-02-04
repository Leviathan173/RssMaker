package per.leviathan173.entity;

import org.kohsuke.args4j.Option;

public class Options {
    @Option(name = "-d", usage = "Set debug level, 0=none 1=normal 2=all massage")
    public int DEBUG_LEVEL = 1;

    @Option(name = "--enable-email", usage = "Enable email function")
    public boolean ENABLE_MAIL = false;

}



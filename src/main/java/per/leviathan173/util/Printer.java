package per.leviathan173.util;

public class Printer {
    private static final int PROGRESS_BAR_LENGTH = 16;
    private static final String HEAD = "Waiting:";
    private static int maxLength = 0;
    /**
     * 0=不输出
     * 1=输出基础信息
     * 2=输出debug信息
     * 错误输出不受影响
     */
    public static int DEBUG_LEVEL = 0;

    public static void printLn(Object T) {
        if (DEBUG_LEVEL > 0)
            System.out.println(T);
    }

    public static void log(Object T){
        if (DEBUG_LEVEL > 1)
            System.out.println(T);
    }

    public static void printErr(Object T) {
        System.err.println(T);
    }

    public static void waitForSecond(int sec) {
        float percentage;
        for (float i = 1; i <= sec; i++) {
            percentage = (i / sec) * 100;
            // 转为之前的10,000倍再取整再除以10,000，保留了原数的四位小数
            rePrint((float) (Math.round(percentage * 10000)) / 10000);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        maxLength = 0;
        System.out.print("\n");

    }

    private static void gotoStart() {
        for (int i = maxLength; i > 0; i--) {
            System.out.print('\b');
        }
    }

    static void rePrint(float percentage) {
        StringBuilder s = new StringBuilder(HEAD + percentage + "%[");
        gotoStart();
        for (int index = 0; index < PROGRESS_BAR_LENGTH; index++) {
            int c = (index * 100) / PROGRESS_BAR_LENGTH;
            if (c == 0) continue;
            if (c < percentage) {
                s.append("■");
            } else {
                s.append(" ");
            }
        }
        s.append("]");
        if (maxLength < s.length()) maxLength = s.length();
        else {
            while (s.length() < maxLength){
                s.append(" ");
            }
        }
        System.out.print(s.toString());
    }

}


public class Print {
    private static final int PROGRESS_BAR_LENGTH = 16;
    private static final String HEAD = "Waiting:"; // 或许可以删掉
    private static int maxLength = 0;

    public static void Println(Object T) {
        System.out.println(T);
    }

    public static void PrintErr(Object T) {
        System.err.println(T);
    }

    public static void Print(Object T) {
        System.out.print(T);
    }

    // 或许这个方法在Print类里面不是很适合
    public static void WaitForSecond(int sec) {
        float percentage;
        for (float i = 1; i <= sec; i++) {
            percentage = (i / sec) * 100;
            // 转为之前的10,000倍再取整再除以10,000，保留了原数的四位小数
            RePrint((float) (Math.round(percentage * 10000)) / 10000);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 因为Main类是直接继承Print类，maxLength不会重置，等待结束后需要手动重置
        maxLength = 0;
        Print("\n");

    }

    private static void GotoStart() {
        for (int i = maxLength; i > 0; i--) {
            Print('\b');
        }
    }


    static void RePrint(float percentage) {
        StringBuilder s = new StringBuilder(HEAD + percentage + "%[");
        GotoStart();
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
        Print(s.toString());
    }
}

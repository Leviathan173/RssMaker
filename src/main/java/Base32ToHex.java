import org.apache.commons.codec.binary.Base32;

import java.io.UnsupportedEncodingException;

public class Base32ToHex {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    //    public static void main(String[] args) throws UnsupportedEncodingException {
//        String origin = "EM756NVFWW3VG2TG2D4S3WEWQWGKJWYR";
//        String expect = "233FDF36A5B5B7536A66D0F92DD896858CA4DB11";
//        Base32 base32 = new Base32();
//        String output = bytesToHex(base32.decode(origin));
//        Printer.Println(output);
//        Printer.Println("success?"+(expect.equals(output)));
//    }
    public static String DecodeBase32(String target) {
        return bytesToHex(new Base32().decode(target));
    }

    // 根据https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}

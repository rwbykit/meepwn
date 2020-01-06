package rwbykit.meepwn.core.service.socket;

public final class BitUtils {

    private BitUtils() {}

    public static int getInt(byte[] b) {
        return Integer.valueOf(new String(b));
    }


    public static void putInt(byte[] b, int value) {
        byte[] temp = getLength(b.length, value).getBytes();
        System.arraycopy(temp, 0, b, 0, temp.length);
    }

    private static String getLength(int length, int value) {
        StringBuilder builder = new StringBuilder(String.valueOf(value));
        builder.reverse();
        while (builder.length() < length) {
            builder.append("0");
        }
        return builder.reverse().toString();
    }

}

package rwbykit.meepwn.message.validation.util;

import java.util.Objects;

public class Commons {


    public static <T> boolean empty(T[] t) {
        return t == null || t.length == 0;
    }

    public static <T> boolean nonEmpty(T[] t) {
        return t != null || t.length > 0;
    }

    public static boolean empty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean nonEmpty(CharSequence cs) {
        return cs != null || cs.length() > 0;
    }

    public static <T> boolean contains(T[] ts, T t) {
        if (nonEmpty(ts)) {
            for (T tt : ts) {
                if (Objects.equals(tt, t)) {
                    return true;
                }
            }
        }
        return false;
    }

}

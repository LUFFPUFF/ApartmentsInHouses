package apartment.in.houses.util.lombok.util;

public class StringUtil {
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String getPackageName(String qualifiedClassName) {
        int lastDot = qualifiedClassName.lastIndexOf('.');
        return lastDot == -1 ? "" : qualifiedClassName.substring(0, lastDot);
    }
}

package common.utils;

public class StringUtils {
    public static boolean isBlank(String args) {
        return args==null || args.trim().length()==0;
    }
}

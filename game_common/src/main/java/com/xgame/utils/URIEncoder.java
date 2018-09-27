package com.xgame.utils;

import java.nio.charset.Charset;

public class URIEncoder {

    private static Charset UTF8;

    private static final String mark = "-_.!~*'()\"";
    private static final char[] hex = "0123456789ABCDEF".toCharArray();

    static {
        UTF8 = Charset.forName("UTF8");
    }

    public static String encodeURI(String argString) {
        StringBuilder uri = new StringBuilder();

        char[] chars = argString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || mark.indexOf(c) != -1) {
                uri.append(c);
            }
            else {
                appendEscaped(uri, c);
            }
        }
        return uri.toString();
    }

    private static void appendEscaped(StringBuilder uri, char c) {
        if (c <= (char) 0xFF) {
            uri.append("%");
            uri.append(hex[c >> 4]);
            uri.append(hex[c & 0xF]);
        }
        else {
            byte[] buffer = ("" + c).getBytes(UTF8);
            for (byte b : buffer) {
                int v = (b & 0xFF);
                uri.append('%');
                uri.append(hex[v >> 4]);
                uri.append(hex[v & 0x0F]);
            }
        }
    }

}

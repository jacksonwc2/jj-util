package com.jj.util.push.windows;

public class PayloadWindows {

    private String title;
    private String subtitle;
    private String parameter;

    public PayloadWindows(String title, String message) {
        this.title = title;
        this.subtitle = message;
    }

    public byte[] build() throws Exception {
        String message = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<wp:Notification xmlns:wp=\"WPNotification\">" + "<wp:Toast>"
                + ifNonNull(title, "<wp:Text1>" + escapeXml(title) + "</wp:Text1>")
                + ifNonNull(subtitle, "<wp:Text2>" + escapeXml(subtitle) + "</wp:Text2>")
                + ifNonNull(parameter, "<wp:Param>" + escapeXml(parameter) + "</wp:Param>") + "</wp:Toast> " + "</wp:Notification>";

        return message.getBytes("UTF-8");
    }

    public static String ifNonNull(Object cond, String value) {
        return cond != null ? value : "";
    }

    public static String escapeXml(String value) {
        if (value == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); ++i) {
            char ch = value.charAt(i);
            switch (ch) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                default:
                    sb.append(ch);
            }
        }

        return sb.toString();
    }

}

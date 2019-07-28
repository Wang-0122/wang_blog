package com.wang.wangblog.utils;


/**
 * 扩展HttpServletRequest的功能，所有请求参数获取都通过该类方法来获取。
 */
public class RequestUtil {

    /**
     * contentType常见文件类型java设置
     * 
     * @param postfix
     * @return
     */
    public static String getContentType(String postfix) {
        String contentType = "x-msdownload";
        postfix = postfix.toUpperCase();
        if (postfix.endsWith("XLS") || postfix.endsWith("XLT") || postfix.endsWith(".XLW") || postfix.endsWith("CSV")) {
            contentType = "application/vnd.ms-excel";
        } else if (postfix.endsWith("DOC")) {
            contentType = "application/msword";
        } else if (postfix.endsWith("RTF")) {
            contentType = "application/rtf";
        } else if (postfix.endsWith("TEXT") || postfix.endsWith("TXT")) {
            contentType = "text/plain";
        } else if (postfix.endsWith("XML")) {
            contentType = "text/xml";
        } else if (postfix.endsWith("BMP")) {
            contentType = "image/bmp";
        } else if (postfix.endsWith("JPG") || postfix.endsWith("JPEG")) {
            contentType = "image/jpeg";
        } else if (postfix.endsWith("GIF")) {
            contentType = "image/gif";
        } else if (postfix.endsWith("AVI")) {
            contentType = "video/x-msvideo";
        } else if (postfix.endsWith("MP3")) {
            contentType = "audio/mpeg";
        } else if (postfix.endsWith("MPA") || postfix.endsWith("MPE") || postfix.endsWith("MPEG") || postfix.endsWith("MPG")) {
            contentType = "video/mpeg";
        } else if (postfix.endsWith("PPT") || postfix.endsWith("PPS")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (postfix.endsWith("PDF")) {
            contentType = "application/pdf";
        } else if (postfix.endsWith("ZIP") || postfix.endsWith("RAR")) {
            contentType = "application/zip";
        }
        return contentType;
    }

}

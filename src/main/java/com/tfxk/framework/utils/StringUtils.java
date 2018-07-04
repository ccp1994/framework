package com.tfxk.framework.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public final class StringUtils {
    public static String md5(String src) {
        try {
            int i;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            byte b[] = md.digest();
            StringBuffer buf = new StringBuffer();
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @param str
     * @return check email address
     */
    public static boolean checkEmail(String str) {
        Pattern pattern = Pattern.compile("\\w+@(\\w+\\.){1,3}\\w+");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static String addParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(s).append(")");
        return sb.toString();
    }

    /**
     *
     * @param val
     * @return if val =="" or val==null return true
     */
    public static boolean isEmpty(String val) {
        return null == val || "".equals(val.trim()) || "null".equalsIgnoreCase(val.trim());
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String getMd5(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    public static boolean isNumber(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isIp(String ipStr) {
        try {
            String number = ipStr.substring(0, ipStr.indexOf('.'));
            if (Integer.parseInt(number) > 255)
                return false;
            ipStr = ipStr.substring(ipStr.indexOf('.') + 1);
            number = ipStr.substring(0, ipStr.indexOf('.'));
            if (Integer.parseInt(number) > 255)
                return false;
            ipStr = ipStr.substring(ipStr.indexOf('.') + 1);
            number = ipStr.substring(0, ipStr.indexOf('.'));
            if (Integer.parseInt(number) > 255)
                return false;
            number = ipStr.substring(ipStr.indexOf('.') + 1);
            return Integer.parseInt(number) <= 255;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param noStr
     * @return check mobile phone number in China
     */
    public static boolean checkPhoneNo(String noStr) {
        if (noStr.length() == 11 && noStr.matches("\\d+") && noStr.startsWith("1")) {
            switch (noStr.charAt(1)) {
                case '3':
                case '5':
                case '6':
                case '7':
                case '8':
                    return true;
            }
        }
        return false;
    }

    public static boolean checkPassLength(String noStr) {
        if (6 > noStr.length()) return false;
        return true;
    }

    public static boolean checkPassNo(String noStr) {
        if (noStr.contains(" ") || noStr.contains("*") || noStr.contains("/") || noStr.contains("\\")) return false;
        return true;
    }

    /**
     * @param phonenumber
     * @return check fixed line telephone number in China
     */
    public static boolean checkFixedTelephone(String phonenumber) {
        String phone = "0\\d{2,3}-?\\d{7,8}";
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(phonenumber);
        return m.matches();
    }

    public static boolean checkUppoadNo(String number) {
        if (number.length() >= 5 || number.length() <= 18) {
            char[] c = number.toCharArray();
            for (char ch : c) {
                if (ch < '0' || ch > '9') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean checkPwd(String pwd) {
        if (pwd.length() < 6 || pwd.length() >= 15) {
            return false;
        } else {
            for (int i = 0; i < pwd.length(); i++) {
                char c = pwd.charAt(i);
                if (c < '0' || (c > '9' && c < 'A') || (c > 'Z' && c < 'a') || c > 'z') {
                    return false;
                }
            }
        }
        return true;
    }

    public static String addQuotes(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(s).append("\"");
        return sb.toString();
    }

    /**
     * @param price
     * @return add RMB sign like "￥100"
     */
    public static String getRMB(double price) {
        String rmbSign = Currency.getInstance("CNY").getSymbol(Locale.CHINA);
        StringBuilder sb = new StringBuilder();
        sb.append(rmbSign).append(String.format("%.2f", price));
        return sb.toString();
    }

    public static List<String> stringToArray(String imageUrl) {
        List<String> list = new ArrayList<String>();
        String[] split = imageUrl.split(",");
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
        return list;
    }
    public static String getRMBSymbol(){
        String rmbSign = Currency.getInstance("CNY").getSymbol(Locale.CHINA);
        return rmbSign;
    }
    public static String priceText(int price) {
        return 0 != price ? getRMB(price) : getRMBSymbol()+"0";
    }
    public static String priceText(double price) {
        return 0 != price ? getRMB(price) : getRMBSymbol()+"0";
    }

    public static String temperText(double temper) {
        return temper+"℃";
    }

    public static String toQuotationString(String string) {
        return "\""+string+"\"";
    }
}

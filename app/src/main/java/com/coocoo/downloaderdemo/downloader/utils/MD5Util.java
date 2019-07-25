package com.coocoo.downloaderdemo.downloader.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getMD5(String origin) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(origin.getBytes("UTF8"));
            byte[] digest = md.digest();
            String result = "";
            for (int i = 0; i < digest.length; i++) {
                result += Integer.toHexString((0x000000FF & digest[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/*
 * Md5Utils    2017-03-31
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.filedownload.file;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-31
 */
public class Md5Utils {

    public static String generateCode(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(url.getBytes());
            byte[] cipher = digest.digest();

            for (byte b : cipher) {
                String hexStr = Integer.toHexString(b & 0xff);
                buffer.append(hexStr.length() == 1 ? "0" + hexStr : hexStr);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
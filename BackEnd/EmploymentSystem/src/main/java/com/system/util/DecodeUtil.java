package com.system.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Author Legion
 * @Date 2021/6/18 10:48
 * @Description
 */
@Slf4j
public class DecodeUtil {
    public static String decode(String str) {
        try {
            str = URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException");
        }
        return str;
    }
}

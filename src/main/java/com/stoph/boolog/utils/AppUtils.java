package com.stoph.boolog.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUtils {

    //대소문자의 영문으로 이루어진 랜덤 문자열 반환
    public static String createPassword() {
        StringBuffer password = new StringBuffer("");
        SecureRandom sr = new SecureRandom();
        int cnt = 1;

        while (cnt != 10) {
            int num = (int) ((sr.nextDouble() * 57) + 65);
            if (num < 91 || num > 96) {
                password.append((char) num);
                cnt += 1;
            }
        }
        return password.toString();
    }
}

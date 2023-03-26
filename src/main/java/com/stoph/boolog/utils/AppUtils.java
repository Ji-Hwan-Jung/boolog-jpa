package com.stoph.boolog.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUtils {

    //대소문자의 영문으로 이루어진 랜덤 문자열 반환
    public static String createPassword() {
        StringBuffer password = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        int cnt = 0;

        while (cnt != 10) {
            int num = (int) ((sr.nextDouble() * 53) + 65);
            if (num > 90 && num < 97) {
                num = (num%2 != 0)? num - 26 : num + 26;
            }
            password.append((char) num);
            cnt++;
        }
        return password.toString();
    }
}

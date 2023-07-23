package ru.otus.hrapp.util;

import java.util.Random;

public class PasswordGenerator {
    private static final String charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTWXYZ";

    public static String generatePassword() {
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int pos = rand.nextInt(charset.length());
            sb.append(charset.charAt(pos));
        }

        return sb.toString();
    }
}

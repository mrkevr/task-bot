package dev.mrkevr.taskbot.util;

import java.security.SecureRandom;
import java.util.Random;

public final class RandomUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6;
    private static final Random RANDOM = new SecureRandom();

    private RandomUtils() {
    }

    public static String generateRandomAlphanumeric() {
        StringBuilder builder = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            builder.append(CHARACTERS.charAt(randomIndex));
        }
        return builder.toString();
    }
}

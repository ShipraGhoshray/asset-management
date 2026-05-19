package com.selflearning.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdGeneratorUtil {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    private static String generate(String prefix) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        int random = RANDOM.nextInt(1_000_000); // 0 to 999999
        return String.format("%s-%s-%06d", prefix, timestamp, random);
    }

    public static String orderId() {
        return generate("ORD");
    }

    public static String tradeId() {
        return generate("TRD");
    }

    public static String executionId() {
        return generate("EXE");
    }

    public static String reportId() {
        return generate("RPT");
    }
}
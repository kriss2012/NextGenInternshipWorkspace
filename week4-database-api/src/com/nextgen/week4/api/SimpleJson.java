package com.nextgen.week4.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Lightweight, zero-dependency JSON parser & serializer for REST API payloads.
 */
public class SimpleJson {

    /**
     * Parses simple flat JSON string into key-value map.
     */
    public static Map<String, String> parseObject(String json) {
        Map<String, String> map = new HashMap<>();
        if (json == null) return map;
        String trimmed = json.trim();
        if (trimmed.startsWith("{")) trimmed = trimmed.substring(1);
        if (trimmed.endsWith("}")) trimmed = trimmed.substring(0, trimmed.length() - 1);

        String[] pairs = trimmed.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String pair : pairs) {
            String[] kv = pair.split(":(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", 2);
            if (kv.length == 2) {
                String key = clean(kv[0]);
                String val = clean(kv[1]);
                map.put(key, val);
            }
        }
        return map;
    }

    private static String clean(String str) {
        String s = str.trim();
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

    public static String successResponse(String message, String dataJson) {
        return String.format("{\"success\":true,\"message\":\"%s\",\"data\":%s}",
                escape(message), dataJson != null ? dataJson : "null");
    }

    public static String errorResponse(String message, int statusCode) {
        return String.format("{\"success\":false,\"error\":\"%s\",\"statusCode\":%d}",
                escape(message), statusCode);
    }

    private static String escape(String s) {
        return (s == null) ? "" : s.replace("\"", "\\\"");
    }
}

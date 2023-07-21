package com.yeem.utils;

import java.io.IOException;
import java.io.InputStream;

public class ExecutorUtils {
    public static String execute(String command) {
        Runtime runtime = Runtime.getRuntime();
        StringBuilder result = new StringBuilder();
        try {
            Process process = runtime.exec(command);
            InputStream inputStream = process.getInputStream();
            while (true) {
                byte[] response = new byte[1024];
                int read = inputStream.read(response);
                if (read == -1) {
                    break;
                }
                result.append(new String(response, "gbk"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}

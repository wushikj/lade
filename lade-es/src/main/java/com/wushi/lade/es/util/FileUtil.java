package com.wushi.lade.es.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static String readFileFromClasspathNew(String url) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            ClassPathResource classPathResource = new ClassPathResource(url);
            InputStreamReader inputStreamReader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(url));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            String lineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(lineSeparator);
            }
        } catch (Exception e) {
            log.debug(String.format("Failed to load file from url: %s: %s", url, e.getMessage()));
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.debug(String.format("Unable to close buffered reader.. %s", e.getMessage()));
                }
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 从classpath下读取文件
     *
     * @param url
     * @return
     */
    public static String readFileFromClasspath(String url) {
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader bufferedReader = null;

        try {
            ClassPathResource classPathResource = new ClassPathResource(url);
            InputStreamReader inputStreamReader = new InputStreamReader(classPathResource.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            String lineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(lineSeparator);
            }
        } catch (Exception e) {
            log.debug(String.format("Failed to load file from url: %s: %s", url, e.getMessage()));
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.debug(String.format("Unable to close buffered reader.. %s", e.getMessage()));
                }
            }
        }

        return stringBuilder.toString();
    }

}

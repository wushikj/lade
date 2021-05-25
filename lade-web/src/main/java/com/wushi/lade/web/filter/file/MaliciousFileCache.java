package com.wushi.lade.web.filter.file;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author wushi
 */
public class MaliciousFileCache {

    private HashSet<String> maliciousFileSet = new HashSet<>();

    public MaliciousFileCache() {
        String[] maliciousFiles = new String[]{
                ".exe", ".aspx", ".ashx", ".jsp", ".dll", ".so", ".js", ".cmd", ".bat", ".ps1", ".sh", ".bash", ".sys", ".pl",
                ".com", ".vbs", ".py", ".rb", ".php", ".asp", ".xml", ".css", ".swf", ".go"
        };
        maliciousFileSet.addAll(Arrays.asList(maliciousFiles));
    }


    /**
     * @param maliciousFiles 恶意文件列表，多文件以","号间隔。
     */
    public void addMaliciousFiles(String maliciousFiles) {
        if (StringUtils.isEmpty(maliciousFiles)) {
            return;
        }
        String[] malicious = maliciousFiles.split(",");
        if (malicious.length == 0) {
            return;
        }
        Collections.addAll(maliciousFileSet, malicious);
    }

    public HashSet<String> getRetMaliciousFiles() {
        return maliciousFileSet;
    }
}

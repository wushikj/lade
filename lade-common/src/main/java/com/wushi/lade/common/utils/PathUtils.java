package com.wushi.lade.common.utils;


import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.jar.JarFile;

/**
 * @author wushi
 */
public class PathUtils {

    private static final String FLAG_RUN_WITH_JAR = "BOOT-INF";
    private static final String FLAG_RUN_WITH_TOMCAT = "WEB-INF";


    /**
     * 分为三种情况。
     * <br/>
     * 1、IDEA Debug模式，返回target目录。
     * <br/>
     * 2、Tomcat部署方式，返回项目名称文件夹目录。
     * <br/>
     * 3、jar包运行方式，返回到jar文件同级目录。
     *
     * @return 项目绝对路径 or null
     */
    public static String getBasePath() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        if (url == null) {
            return null;
        }
        String path = url.getPath();
        //在java中获取文件路径的时候，有时候会获取到空格，但是在中文编码环境下，空格会变成“%20”从而使得路径错误.
        try {
            path = URLDecoder.decode(path, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (path.contains(FLAG_RUN_WITH_JAR)) {
            //内置Tomcat
            path = findHomeDir(findSource()).getPath();
        } else if (path.contains(FLAG_RUN_WITH_TOMCAT)) {
            //外置Tomcat
            path = new File(path).getParentFile().getParentFile().getPath();
        } else {
            //IDEA开发模式
            path = new File(path).getParentFile().getPath();
        }
        return path;
    }


    private static File findHomeDir(File source) {
        File homeDir = source;
        homeDir = (homeDir != null) ? homeDir : findDefaultHomeDir();
        if (homeDir.isFile()) {
            homeDir = homeDir.getParentFile();
        }
        homeDir = homeDir.exists() ? homeDir : new File(".");
        return homeDir.getAbsoluteFile();
    }

    private static File findDefaultHomeDir() {
        String userDir = System.getProperty("user.dir");
        if (userDir != null && !userDir.isEmpty()) {
            return new File(userDir);
        } else {
            return new File(".");
        }
    }

    private static File findSource() {
        try {
            ProtectionDomain domain = PathUtils.class.getProtectionDomain();
            CodeSource codeSource = (domain != null) ? domain.getCodeSource() : null;
            URL location = (codeSource != null) ? codeSource.getLocation() : null;
            File source = (location != null) ? findSource(location) : null;
            if (source != null && source.exists() && !isUnitTest()) {
                return source.getAbsoluteFile();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    private static boolean isUnitTest() {
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (int i = stackTrace.length - 1; i >= 0; i--) {
                if (stackTrace[i].getClassName().startsWith("org.junit.")) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private static File findSource(URL location) throws IOException {
        URLConnection connection = location.openConnection();
        if (connection instanceof JarURLConnection) {
            return getRootJarFile(((JarURLConnection) connection).getJarFile());
        }
        return new File(location.getPath());
    }


    private static File getRootJarFile(JarFile jarFile) {
        String name = jarFile.getName();
        int separator = name.indexOf("!/");
        if (separator > 0) {
            name = name.substring(0, separator);
        }
        return new File(name);
    }
}

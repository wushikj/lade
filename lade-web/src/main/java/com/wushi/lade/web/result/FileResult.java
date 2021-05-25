package com.wushi.lade.web.result;

import java.io.FileInputStream;

/**
 * 返回文件
 *
 * @author wushi
 * @date 2020/3/19 16:04
 * @description
 */
public class FileResult {
    /**
     * 文件流
     */
    private FileInputStream fileInputStream;

    /**
     * 文件名称，如果设置filename直接在浏览器打开
     */
    private String fileName;

    /**
     * mime类型
     */
    private String contentType;

    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public FileResult() {
    }

    /**
     * @param fileInputStream 文件流
     * @param contentType     文件mime类型
     * @return
     * @author wushi
     * @date 2020/3/19 17:22
     * @description
     */
    public FileResult(FileInputStream fileInputStream, String contentType) {
        this.fileInputStream = fileInputStream;
        this.contentType = contentType;
    }

    /**
     * @param fileInputStream 文件流
     * @param fileName        如果设置filename直接在浏览器打开
     * @param contentType     文件mime类型
     * @return
     * @author wushi
     * @date 2020/3/19 17:22
     * @description
     */
    public FileResult(FileInputStream fileInputStream, String fileName, String contentType) {
        this.fileInputStream = fileInputStream;
        this.fileName = fileName;
        this.contentType = contentType;
    }
}

package com.wushi.lade.web.filter.paramsign;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wushi
 * @date 2020/3/16 15:16
 * @description
 */
public class CachedBodyServletInputStream extends ServletInputStream {
    private InputStream cachedBodyInputStream;

    public CachedBodyServletInputStream(byte[] bytes) throws IOException {
        this.cachedBodyInputStream = new ByteArrayInputStream(bytes);
    }

    @Override
    public int read() throws IOException {
        return cachedBodyInputStream.read();
    }

    @Override
    public boolean isFinished() {
        try {
            return cachedBodyInputStream.available() == 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {

    }
}

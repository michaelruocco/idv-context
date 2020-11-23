package uk.co.idv.app.spring.filters.masking;

import lombok.RequiredArgsConstructor;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
public class RewriteResponseServletOutputStream extends ServletOutputStream {

    private final ByteArrayOutputStream outputStream;

    @Override
    public void write(int b) {
        outputStream.write(b);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {
        // intentionally blank
    }

}

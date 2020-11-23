package uk.co.idv.app.spring.filters.masking;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;

public class RewriteResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream body;

    private ServletOutputStream output;
    private PrintWriter writer;

    public RewriteResponseWrapper(HttpServletResponse response) {
        super(response);
        body = new ByteArrayOutputStream(response.getBufferSize());
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }
        if (output == null) {
            output = new RewriteResponseServletOutputStream(body);
        }
        return output;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (output != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }
        if (writer == null) {
            writer = new PrintWriter(new OutputStreamWriter(body, getCharacterEncoding()));
        }
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        super.flushBuffer();
        if (writer != null) {
            writer.flush();
        } else if (output != null) {
            output.flush();
        }
    }

    public byte[] getBodyAsBytes() {
        try {
            if (writer != null) {
                writer.close();
            } else if (output != null) {
                output.close();
            }
            return body.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getBodyAsString() {
        try {
            return new String(getBodyAsBytes(), getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            throw new UncheckedIOException(e);
        }
    }

    public boolean has2xxStatus() {
        int status = getStatus();
        return status >= 200 && status <= 299;
    }

}

package uk.co.idv.app.spring.filters.masking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public class RewriteResponseBodyFilter implements Filter {

    private final Function<RewriteBodyRequest, String> rewriteBody;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RewriteResponseWrapper wrappedResponse = new RewriteResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, wrappedResponse);
        RewriteBodyRequest rewriteBodyRequest = RewriteBodyRequest.builder()
                .request((HttpServletRequest) request)
                .response(wrappedResponse)
                .build();
        String body = rewriteBody.apply(rewriteBodyRequest);
        response.getWriter().write(body);
    }

}

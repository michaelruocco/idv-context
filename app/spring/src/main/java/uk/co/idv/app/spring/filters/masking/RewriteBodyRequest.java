package uk.co.idv.app.spring.filters.masking;

import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Builder
@Data
public class RewriteBodyRequest {

    private final HttpServletRequest request;
    private final RewriteResponseWrapper response;

}

package uk.co.idv.app.spring.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.context.ContextModule;
import uk.co.idv.identity.adapter.json.IdentityParentModule;
import uk.co.idv.lockout.adapter.json.LockoutParentModule;
import uk.co.idv.method.adapter.json.MethodMapping;
import uk.co.idv.method.adapter.json.otp.OtpMapping;

import java.util.Collection;
import java.util.Collections;

public class SpringObjectMapperFactory {

    private SpringObjectMapperFactory() {
        // utility class
    }

    public static ObjectMapper build() {
        return ObjectMapperFactory.build(
                new JavaTimeModule(),
                new IdentityParentModule(),
                new LockoutParentModule(),
                new ContextModule(methodMappings())
        );
    }

    private static Collection<MethodMapping> methodMappings() {
        return Collections.singleton(new OtpMapping());
    }
}

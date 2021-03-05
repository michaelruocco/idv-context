package uk.co.idv.app.spring.context;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.app.plain.Application;
import uk.co.idv.context.entities.context.ApiContext;
import uk.co.idv.context.usecases.context.ContextConverter;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.GetVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/contexts")
public class ContextController {

    private final Application application;
    private final ContextConverter contextConverter;

    @PostMapping
    public ResponseEntity<ApiContext> createContext(@RequestBody FacadeCreateContextRequest request) {
        ApiContext context = contextConverter.toApiContext(application.create(request));
        return ResponseEntity
                .created(buildGetContextUri(context.getId()))
                .body(context);
    }

    @GetMapping("/{id}")
    public ApiContext getContext(@PathVariable("id") UUID id) {
        return contextConverter.toApiContext(application.findContext(id));
    }

    @PostMapping("/verifications")
    public ResponseEntity<Verification> createVerification(@RequestBody CreateVerificationRequest request) {
        Verification verification = application.create(request);
        return ResponseEntity
                .created(buildGetVerificationUri(request.getContextId(), verification.getId()))
                .body(verification);
    }

    @GetMapping("/{contextId}/verifications/{verificationId}")
    public Verification getVerification(@PathVariable("contextId") UUID contextId,
                                        @PathVariable("verificationId") UUID verificationId) {
        GetVerificationRequest request = GetVerificationRequest.builder()
                .contextId(contextId)
                .verificationId(verificationId)
                .build();
        return application.get(request);
    }

    @PatchMapping("/verifications")
    public Verification completeVerification(@RequestBody CompleteVerificationRequest request) {
        return application.complete(request);
    }

    private static URI buildGetContextUri(UUID id) {
        return linkTo(methodOn(ContextController.class).getContext(id)).toUri();
    }

    private static URI buildGetVerificationUri(UUID contextId, UUID verificationId) {
        return linkTo(methodOn(ContextController.class).getVerification(contextId, verificationId)).toUri();
    }

}

package uk.co.idv.app.spring.context;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.app.manual.Application;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/contexts")
public class ContextController {

    private final Application application;

    @PostMapping
    public ResponseEntity<Context> createContext(@RequestBody FacadeCreateContextRequest request) {
        Context context = application.create(request);
        return ResponseEntity
                .created(buildGetUri(context.getId()))
                .body(context);
    }

    @GetMapping("/{id}")
    public Context getContext(@PathVariable("id") UUID id) {
        return application.findContext(id);
    }

    @GetMapping("/{id}/nextMethods")
    public Verification getNextMethods(@PathVariable("id") UUID id,
                                       @RequestParam("methodName") String methodName) {
        CreateVerificationRequest request = CreateVerificationRequest.builder()
                .contextId(id)
                .methodName(methodName)
                .build();
        return application.findNextMethods(request);
    }

    @PatchMapping("/results")
    public Context recordResult(@RequestBody FacadeRecordResultRequest request) {
        return application.record(request);
    }

    private static URI buildGetUri(UUID id) {
        return linkTo(methodOn(ContextController.class).getContext(id)).toUri();
    }

}

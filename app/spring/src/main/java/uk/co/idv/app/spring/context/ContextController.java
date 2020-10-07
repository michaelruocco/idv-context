package uk.co.idv.app.spring.context;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.usecases.context.ContextFacade;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contexts")
public class ContextController {

    private final ContextFacade facade;

    @PostMapping
    public ResponseEntity<Context> createContext(@RequestBody FacadeCreateContextRequest request) {
        Context context = facade.create(request);
        return ResponseEntity
                .created(buildGetUri(context.getId()))
                .body(context);
    }

    @GetMapping("/{id}")
    public Context getContext(@PathVariable("id") UUID id) {
        return facade.find(id);
    }

    @PostMapping("/results")
    public Context recordResult(@RequestBody FacadeRecordResultRequest request) {
        return facade.record(request);
    }

    private static URI buildGetUri(UUID id) {
        return linkTo(methodOn(ContextController.class).getContext(id)).toUri();
    }

}

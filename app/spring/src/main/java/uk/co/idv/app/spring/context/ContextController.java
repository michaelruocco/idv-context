package uk.co.idv.app.spring.context;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.usecases.context.ContextFacade;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contexts")
public class ContextController {

    private final ContextFacade facade;

    @PostMapping
    public Context createContext(@RequestBody CreateContextRequest request) {
        return facade.create(request);
    }

    @GetMapping("/{id}")
    public Context getContext(@PathVariable("id") UUID id) {
        return facade.find(id);
    }

}

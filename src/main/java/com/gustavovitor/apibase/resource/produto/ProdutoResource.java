package com.gustavovitor.apibase.resource.produto;

import com.gustavovitor.apibase.config.events.CreatedResourceEvent;
import com.gustavovitor.apibase.domain.produto.Produto;
import com.gustavovitor.apibase.service.produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/produto")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_READ_PRODUTOS') and #oauth2.hasScope('read')")
    public Produto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    //@PreAuthorize("hasAuthority('ROLE_READ_PRODUTOS') and #oauth2.hasScope('read')")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/empresa/{id}")
    //@PreAuthorize("hasAuthority('ROLE_READ_PRODUTOS') and #oauth2.hasScope('read')")
    public ResponseEntity findAllByIdEmpresa(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllByIdEmpresa(id));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_DELETE_PRODUTOS') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivate(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('ROLE_WRITE_PRODUTOS') and #oauth2.hasScope('write')")
    public ResponseEntity<Produto> insert(@Valid @RequestBody Produto o, HttpServletResponse response) {
        Produto savedObject = service.insert(o);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedObject.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedObject);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_WRITE_PRODUTOS') and #oauth2.hasScope('write')")
    public ResponseEntity<Produto> update(@PathVariable Long id, @Valid @RequestBody Produto o) {
        return ResponseEntity.ok(service.update(id, o));
    }

}

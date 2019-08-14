package com.gustavovitor.apibase.resource.empresa;

import com.gustavovitor.apibase.config.events.CreatedResourceEvent;
import com.gustavovitor.apibase.domain.empresa.Empresa;
import com.gustavovitor.apibase.service.empresa.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/empresa")
public class EmpresaResource {

    @Autowired
    private EmpresaService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_READ_EMPRESAS') and #oauth2.hasScope('read')")
    public Empresa findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    //@PreAuthorize("hasAuthority('ROLE_READ_EMPRESAS') and #oauth2.hasScope('read')")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_DELETE_EMPRESAS') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivate(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping
    //PreAuthorize("hasAuthority('ROLE_WRITE_EMPRESAS') and #oauth2.hasScope('write')")
    public ResponseEntity<Empresa> insert(@Valid @RequestBody Empresa o, HttpServletResponse response) {
        Empresa savedObject = service.insert(o);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedObject.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedObject);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_WRITE_EMPRESAS') and #oauth2.hasScope('write')")
    public ResponseEntity<Empresa> update(@PathVariable Long id, @Valid @RequestBody Empresa o) {
        return ResponseEntity.ok(service.update(id, o));
    }

}

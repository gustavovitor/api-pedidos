package com.gustavovitor.apibase.resource.pedido;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavovitor.apibase.config.events.CreatedResourceEvent;
import com.gustavovitor.apibase.domain.pedido.Pedido;
import com.gustavovitor.apibase.domain.security.base.AuthUser;
import com.gustavovitor.apibase.repository.user.UserRepository;
import com.gustavovitor.apibase.service.pedido.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/pedido")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_READ_PEDIDOS') and #oauth2.hasScope('read')")
    public Pedido findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    //@PreAuthorize("hasAuthority('ROLE_READ_PEDIDOS') and #oauth2.hasScope('read')")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/usuario/{id}")
    //@PreAuthorize("hasAuthority('ROLE_READ_PEDIDOS') and #oauth2.hasScope('read')")
    public ResponseEntity findAllByIdUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllByIdUsuario(id));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_DELETE_PEDIDOS') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivate(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('ROLE_WRITE_PEDIDOS') and #oauth2.hasScope('write')")
    public ResponseEntity<Pedido> insert(@Valid @RequestBody Pedido o, HttpServletResponse response, HttpServletRequest request) throws IOException {
        /* Extrai o usuário do Token JWT, para melhor segurança, não enviá-lo na requisição e sim do token que está fazendo a operação. */
        Jwt jwt = JwtHelper.decode(request.getHeader("Authorization").replace("Bearer ", ""));
        HashMap claims = new ObjectMapper().readValue(jwt.getClaims(), HashMap.class);
        Long userId = Long.parseLong(claims.get("userId").toString());
        AuthUser user = userRepository.getOne(userId);

        o.setUsuario(user);

        Pedido savedObject = service.insert(o);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedObject.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedObject);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_WRITE_PEDIDOS') and #oauth2.hasScope('write')")
    public ResponseEntity<Pedido> update(@PathVariable Long id, @Valid @RequestBody Pedido o) {
        return ResponseEntity.ok(service.update(id, o));
    }

}

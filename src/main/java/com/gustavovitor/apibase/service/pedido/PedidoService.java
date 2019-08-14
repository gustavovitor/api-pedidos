package com.gustavovitor.apibase.service.pedido;

import com.gustavovitor.apibase.domain.pedido.Pedido;
import com.gustavovitor.apibase.domain.security.base.AuthUser;
import com.gustavovitor.apibase.repository.pedido.PedidoRepository;
import com.gustavovitor.apibase.repository.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Pedido findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Erro ao buscar o módulo pelo identificador."));
    }

    public List<Pedido> getAll() {
        return repository.findAll();
    }

    public List<Pedido> getAllByIdUsuario(Long id) {
        return repository.findAllByUsuario(userRepository.getOne(id));
    }

    @Transactional
    public Pedido insert(Pedido object) {
        return repository.save(object);
    }

    @Transactional
    public Pedido update(Long id, Pedido object) {
        Pedido savedObject = findById(id);
        BeanUtils.copyProperties(object, savedObject, "id");
        return repository.save(savedObject);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }
    
}

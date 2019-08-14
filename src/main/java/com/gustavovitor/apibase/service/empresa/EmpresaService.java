package com.gustavovitor.apibase.service.empresa;

import com.gustavovitor.apibase.domain.empresa.Empresa;
import com.gustavovitor.apibase.repository.empresa.EmpresaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;

    public Empresa findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Erro ao buscar o módulo pelo identificador."));
    }

    public List<Empresa> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Empresa insert(Empresa object) {
        return repository.save(object);
    }

    @Transactional
    public Empresa update(Long id, Empresa object) {
        Empresa savedObject = findById(id);
        BeanUtils.copyProperties(object, savedObject, "id");
        return repository.save(savedObject);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }

}

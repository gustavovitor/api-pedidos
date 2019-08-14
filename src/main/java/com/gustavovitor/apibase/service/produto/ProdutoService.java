package com.gustavovitor.apibase.service.produto;

import com.gustavovitor.apibase.domain.produto.Produto;
import com.gustavovitor.apibase.repository.produto.ProdutoRepository;
import com.gustavovitor.apibase.service.empresa.EmpresaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private EmpresaService empresaService;

    public Produto findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Erro ao buscar o módulo pelo identificador."));
    }

    public List<Produto> getAll() {
        return repository.findAll();
    }

    public List<Produto> getAllByIdEmpresa(Long id) {
        return repository.findAllByEmpresa(empresaService.findById(id));
    }

    @Transactional
    public Produto insert(Produto object) {
        return repository.save(object);
    }

    @Transactional
    public Produto update(Long id, Produto object) {
        Produto savedObject = findById(id);
        BeanUtils.copyProperties(object, savedObject, "id");
        return repository.save(savedObject);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }

}

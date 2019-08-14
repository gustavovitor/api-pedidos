package com.gustavovitor.apibase.repository.produto;

import com.gustavovitor.apibase.domain.empresa.Empresa;
import com.gustavovitor.apibase.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllByEmpresa(Empresa empresa);
}

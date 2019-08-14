package com.gustavovitor.apibase.repository.empresa;

import com.gustavovitor.apibase.domain.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {}

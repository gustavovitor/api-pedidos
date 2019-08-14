package com.gustavovitor.apibase.repository.pedido;

import com.gustavovitor.apibase.domain.pedido.Pedido;
import com.gustavovitor.apibase.domain.security.base.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByUsuario(AuthUser user);
}

package com.gustavovitor.apibase.domain.pedido;

import com.gustavovitor.apibase.domain.produto.Produto;
import com.gustavovitor.apibase.domain.security.base.AuthUser;
import com.gustavovitor.apibase.util.Endereco;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "pedido")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private AuthUser usuario;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pedido_produto_item",
            joinColumns = @JoinColumn(name = "pedido_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "produto", column = @Column(name = "produto_id")),
            @AttributeOverride(name = "qtdProduto", column = @Column(name = "qtdProduto")),
            @AttributeOverride(name = "key", column = @Column(name = "key"))
    })
    private List<ProdutoItem> produtoList = new ArrayList<>();

    @NotNull
    @Embedded
    private Endereco enderecoEntrega;
}

package com.gustavovitor.apibase.domain.pedido;

import com.gustavovitor.apibase.domain.produto.Produto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class ProdutoItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @OneToOne
    private Produto produto;
    private Integer qtdProduto;
    private Integer key;
}

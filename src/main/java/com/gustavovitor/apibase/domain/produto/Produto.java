package com.gustavovitor.apibase.domain.produto;

import com.gustavovitor.apibase.domain.empresa.Empresa;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    private Empresa empresa;

    @Size(max = 64)
    @NotNull
    private String nome;

    private String imgUrl;

    @Size(max = 1024)
    @NotNull
    private String descricao;

    @NotNull
    private Float preco;
}

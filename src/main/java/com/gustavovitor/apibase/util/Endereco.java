package com.gustavovitor.apibase.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 64)
    private String cep;

    @NotNull
    @Size(max = 128)
    private String logradouro;

    @NotNull
    @Size(max = 64)
    private String numero;

    @NotNull
    @Size(max = 128)
    private String bairro;

    @NotNull
    @Size(max = 128)
    private String cidade;

    @NotNull
    @Size(max = 64)
    private String estado;

}

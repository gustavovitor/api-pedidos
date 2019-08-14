package com.gustavovitor.apibase.domain.empresa;

import com.gustavovitor.apibase.util.Endereco;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "empresa")
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 128)
    private String nome;

    @CNPJ
    @NotNull
    private String cnpj;

    @NotNull
    @Size(max = 20)
    private String ie;
    private LocalDateTime dataAbertura;

    @Email
    @Size(max = 128)
    private String email;

    @Embedded
    private Endereco endereco;

    @NotNull
    @Size(max = 64)
    private String fone;
}

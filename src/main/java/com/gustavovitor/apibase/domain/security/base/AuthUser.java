package com.gustavovitor.apibase.domain.security.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users", schema = "security")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/** {@link AuthUser} é a classe inicial para se fazer o login com o Spring Security. * */
public class AuthUser implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 128)
    @Column(name = "username")
    private String user;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = 3, max = 256)
    @JsonIgnore
    private String pass;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_permissions", schema = "security",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;
}

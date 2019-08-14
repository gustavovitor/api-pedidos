package com.gustavovitor.apibase.service.security;

import com.gustavovitor.apibase.domain.security.base.AuthUser;
import com.gustavovitor.apibase.domain.security.base.SystemUser;
import com.gustavovitor.apibase.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** {@link AppUserDetailsService} é usada para o gerenciamento do login/permissões dos usuários no Spring Security dessa aplicação.
 * */
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /** loadUserByUsername carrega o usuário a partir do nome de usuário para que o WebSecutiry configuration consiga usá-lo.
     * @param s username do usuário.
     * @return UserDetails com o usuário/permissões atrelados. */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AuthUser user = userRepository.findByUser(s).orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválido(a)."));
        return new SystemUser(user, getPermissoes(user));
    }

    /* Este método extrai as permissões dos usuários e retorna-os para a montagem do UserDetails no loadUserByUsername. */
    private Collection<? extends GrantedAuthority> getPermissoes(AuthUser user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getPermissions().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescription().toUpperCase())));
        return authorities;
    }
}

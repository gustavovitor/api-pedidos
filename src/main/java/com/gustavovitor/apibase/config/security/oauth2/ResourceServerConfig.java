package com.gustavovitor.apibase.config.security.oauth2;

import com.gustavovitor.apibase.service.security.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/** {@link ResourceServerConfig} é responsável pelos fluxos do OAuth2, o básico do HttpSecurity no
 * Spring Boot. Ele implementará as configurações principais do projeto. */
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    /* Este método é injetável no OAuth2, porém no security basic ele é sobrescrito.
     * Basicamente, ele gerencia a forma na qual o usuário vai logar, no nosso caso, ele está buscando um usuário
     * da memória e não do banco de dados, já que o usuário fora injetado pela appUserDetailsService ao logar-se. */
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService).passwordEncoder(encoder);
    }

    /* Apenas informa para o servidor de segurança que não haverá sessões gravadas, já que todas as requisições,
    * vão se movimentar via JWT. */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) { resources.stateless(true); }

    /* Realiza a configuração primária da segurança. */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
        .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                .csrf().disable();
    }
}

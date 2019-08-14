package com.gustavovitor.apibase.config.security.oauth2;

import com.gustavovitor.apibase.config.security.oauth2.token.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /* Este injetável gerencia as autenticações. Ele que pega o usuário e senha da aplicação.*/
    @Autowired
    private AuthenticationManager authenticationManager;

    /** passwordEncoder cria uma instância do BCrypt.
     * @return BCryptPasswordEncoder pronto para uso. */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }


    /** Configura a forma na qual será requisitada o token JWT, aqui também se configura o tempo de vida
     * do token para a aplicação.
     * */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /* Configuração inMemory, com o cliente "oauth2-client-api", senha proposta e o escopo do usuário, ler, escrever e assim por diante.
         * Nos vamos utilizar o password-flow, onde a aplicação de consumo vai receber o usuário e senha, enviará para o servidor e receber o
         * access token.
         *
         * authorizedGrantType escolhe o fluxo de autenticação e o acessTokenValiditySeconds, o tempo do token em segundos. */
        clients.inMemory()
            .withClient("oauth2-client-api")
            .secret(passwordEncoder().encode("*Y*%bXQ#<5,p~[Vk9bb&&X9rsw7V~J`_"))
            .scopes("read", "write")
        .authorizedGrantTypes("password", "refresh_token")
            .accessTokenValiditySeconds(3600)
            .refreshTokenValiditySeconds(3600 * 24);

        /* TODO: Ajustar o tepo de validade do access_token e do refresh_token. */
    }

    /** Neste método nós vamos configurar o token-store, onde vamos armazenar o token para que a aplicação consiga buscar o token
     * e depois retornar a string para validarmos se o token ainda está ativo.
     *
     * Aqui nós passamos o nosso authenticationManager, para que seja possível validar a autenticação do usuário. */

    /* ReuseRefreshToken, falso, o token sempre vai dar refresh enquanto o usuário estiver usando a aplicação, se ele passar
     * mais de 24 horas sem utilizar, (tempo definido no método acima no refreshTokenValiditySeconds) ai sim, ele será deslogado. */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .reuseRefreshTokens(false)
                .authenticationManager(authenticationManager);
    }

    /* Este método é responsável por incrementar no token de acesso, algum incremento. Como por exemplo, o nome de usuário
     * ou o tanacy-id. :D */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    /* Aqui, ele decodifica o token para o algorítmo do JWT, onde nós passamos no configure de endpoints o accessTokenConverter. */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("$5wH$WY94D`7y,V&G}\"!}b+gw55R\\cAz{gv'gN^\\H:m>g'@GWAevU<wYxBVE+fw>");
        return accessTokenConverter;
    }

    /* Salva o token em JWT. */
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }
}

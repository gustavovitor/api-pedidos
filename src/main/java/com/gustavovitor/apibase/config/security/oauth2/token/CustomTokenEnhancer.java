package com.gustavovitor.apibase.config.security.oauth2.token;

import com.gustavovitor.apibase.domain.security.base.SystemUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** {@link CustomTokenEnhancer} é responsável por customizar o token do JWT.  * */
public class CustomTokenEnhancer implements TokenEnhancer {

    /** enhance é responsável por adicionar um campo personalizado no JWT.
     * */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        SystemUser user = (SystemUser) oAuth2Authentication.getPrincipal();

        /* TODO: Implementar aqui o que você quer adicional no token para futuras leituras. */
        Map<String, Object> addInfo = new HashMap<>();
        addInfo.put("username", user.getUsername());

        addInfo.put("userId", user.getAuthUser().getId());

        List<String> authorities = new ArrayList<>();
        user.getAuthUser().getPermissions().forEach(x -> authorities.add(x.getDescription()));
        addInfo.put("authorities", authorities);

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(addInfo);
        return oAuth2AccessToken;
    }
}

package com.gustavovitor.apibase.filters.token;

import com.gustavovitor.apibase.config.properties.APIProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Este controller vai interceptar todas as respostas com o OAuth2AccessToken. Para que nós possamos adicionar cookies e outras
* operações aqui. */
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

    @Autowired
    private APIProperty apiProperty;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getMethod().getName().equals("postAccessToken");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken oAuth2AccessToken,
                                             MethodParameter methodParameter,
                                             MediaType mediaType,
                                             Class<? extends HttpMessageConverter<?>> aClass,
                                             ServerHttpRequest serverHttpRequest,
                                             ServerHttpResponse serverHttpResponse) {

        HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;

        String refreshToken = oAuth2AccessToken.getRefreshToken().getValue();

        addRefreshTokenIntoCookie(refreshToken, request, response);
        removeRefreshTokenForBody(token);

        return oAuth2AccessToken;
    }

    /* Este método implementará o refresh token dentro do Cookie HTTP, no retorno do OAuth2AccessToken quando o nome do método de
    * resposta for o "postAccessToken".
    *
    * Este cookie expira em 30 dias.*/
    private void addRefreshTokenIntoCookie(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(apiProperty.getSecurity().isEnableHttps());
        refreshTokenCookie.setPath(request.getContextPath() + "/oauth/token");
        refreshTokenCookie.setMaxAge(3600 * 24 * 30);
        response.addCookie(refreshTokenCookie);
    }

    /* Este método remove o refresh token do corpo da nossa requisição, que passará a usar apenas os cookies. */
    private void removeRefreshTokenForBody(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }
}

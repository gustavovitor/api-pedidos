package com.gustavovitor.apibase.filters.token;

import lombok.Getter;
import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

/* Este componente será responsável por interceptar as entradas do /oath/token que vinherem com o
* refresh token dentro dos cookies. */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

    /* Método responsável por filtrar o cookie e extrair o refresh token.*/
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest r = (HttpServletRequest) request;

        /* ..se estiver entrando uma nova requisição que contenha o refresh_token dentro dos cookies,
        * este método irá sobrescrever essa requisição inserindo no mapa de parâmetros o refresh token. */
        if("/oauth/token".equalsIgnoreCase(r.getRequestURI())
                && "refresh_token".equals(r.getParameter("grant_type"))
                && r.getCookies() != null){
            for(Cookie c : r.getCookies()){
                if(c.getName().equals("refreshToken")){
                    String refreshToken = c.getValue();
                    r = new MyServletRequestWrapper(r, refreshToken);
                }
            }
        }

        chain.doFilter(r, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Getter
    static class MyServletRequestWrapper extends HttpServletRequestWrapper {

        private String refreshToken;

        public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;
        }

        /* Aqui ele implementará o refresh token dentro do mapping da requisição. */
        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> map = new ParameterMap(getRequest().getParameterMap());
            map.put("refresh_token", new String[] { refreshToken });
            map.setLocked(true);
            return map;
        }
    }
}

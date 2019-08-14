package com.gustavovitor.apibase.filters.cors;

import com.gustavovitor.apibase.config.properties.APIProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Autowired
    private APIProperty apiProperty;

    private String[] allowOrigin;

    /* Neste método, interceptamos todas as requisições e adicionamos alguns headers conforme o necessário.
     * Por exemplo, temos o Allow-Origim para a origem da requisição. Também é possível colocar para todas as
     * origens, apenas com um '*'. */
    @Override
    public void doFilter(ServletRequest rq, ServletResponse rp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) rq;
        HttpServletResponse response = (HttpServletResponse) rp;

        response.setHeader("Access-Control-Allow-Credentials", "true");

        Boolean _originAccepted = false;
        /* Implementação para aceitar multiplas origens. */
        for (String origin : allowOrigin) {
            _originAccepted = origin.equals(request.getHeader("Origin"));
            if (_originAccepted) {
                response.setHeader("Access-Control-Allow-Origin", origin);
                break;
            }
        }

        if("OPTIONS".equals(request.getMethod()) && _originAccepted){
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            chain.doFilter(rq, rp);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.allowOrigin = apiProperty.getSecurity().getAllowOrigin();
    }
}

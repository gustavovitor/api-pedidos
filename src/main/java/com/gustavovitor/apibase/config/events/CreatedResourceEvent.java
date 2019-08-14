package com.gustavovitor.apibase.config.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
public class CreatedResourceEvent extends ApplicationEvent {
    private static long serialVersionUID = 1L;

    private HttpServletResponse response;
    private Long id;

    public CreatedResourceEvent(Object source, HttpServletResponse response, Long id) {
        super(source);
        this.response = response;
        this.id = id;
    }
}

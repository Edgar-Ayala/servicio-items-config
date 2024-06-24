package com.formacionbdi.springboot.app.zuu.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

//Anotacion para indicar que esta clase es un componente de Spring que se gestiona automaticamente.
@Component
public class PreTiempoTranscurridoFilter extends ZuulFilter {

    //Logger para registrar mensajes de registro
    private static Logger Log = LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class);
    
    //Metodo para especificar el tipo de filtro ("pre" en este caso
    @Override
    public String filterType() {
        return "pre";
    }

    //Metodo para definir el orden de ejecucion del filtro dentro del tipo de filtro especificado ("pre).
    @Override
    public int filterOrder() {
        return 1; //Orden de ejecucion, menor numero se ejecuta antes.
    }

    //Metodo para determinar si el filtro debe ejecutarse o no
    @Override
    public boolean shouldFilter() {
        return true; //Siempre se ejecuta este filtro
    }

    // Metodo principal que se ejecuta cuando el filtro se activa
    @Override
    public Object run() throws ZuulException {
        // Obtener el contexto de la solicitud actual de Zuul.
        RequestContext ctx= RequestContext.getCurrentContext();
        // Obtener la solicitud HTTP original.
        HttpServletRequest request = ctx.getRequest();
        //Registrar en el log el metodo y la URL de la solicitud enrutada por Zuul
        Log.info(String.format("%s request enrutado a %s", request.getMethod(),request.getRequestURL().toString()));
        //Registrar el tiempo de inicio de la solicitud en un atributo de la solicitud.
        long tiempoInicio = System.currentTimeMillis();
        request.setAttribute("tiempoInicio", tiempoInicio);
        return null;
    }
}

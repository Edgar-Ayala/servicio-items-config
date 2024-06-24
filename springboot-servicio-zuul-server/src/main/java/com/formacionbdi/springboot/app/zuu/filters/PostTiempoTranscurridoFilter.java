package com.formacionbdi.springboot.app.zuu.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static java.lang.String.format;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);
    // Metodo principal que se ejecuta cuando el filtro se activa.
    @Override
    public Object run() throws ZuulException {
        //Obtener el contexto de la solicitud actual de Zuul.
        RequestContext ctx= RequestContext.getCurrentContext();
        //Obtener la solicitud HTTP original.
        HttpServletRequest request = ctx.getRequest();
        //Registrar en el log que se esta entrando al filtro post.
        log.info("Entrando a post filter");

        //Obtener el tiempo de inicio registrado en el filtro pre
        long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
        //Obtener el tiempo actual para calcular el tiempo transcurrido.
        long tiempoFinal = System.currentTimeMillis();
        //Calcular el tiempo transcurrido en milisegundos.
        long tiempoTranscurrido = tiempoFinal - tiempoInicio;
        //Registrar en el log el tiempo transcurrido en segundos y milisegundos.
        log.info(String.format("Tiempo transcurrido en segundos %s seg.", tiempoTranscurrido / 1000.00));
        log.info(String.format("Tiempo transcurrido en milisegundos %s ms.", tiempoTranscurrido));
        return null;
    }
    //Metodo para especificar el tipo de filtro("post" en este caso).
    @Override
    public String filterType() {
        return "post";
    }

    //Metodo para definir el orden de ejecucion del filtro dentro del tipo de filtro especificado ("post").
    @Override
    public int filterOrder() {
        return 1;//Orden de ejecucion, menor numero se ejecuta antes.
    }

    // Metodo para determinar si el filtro debe ejecutarse o no.
    @Override
    public boolean shouldFilter() {
        return true; //Siempre se ejecuta este filtro.
    }


}

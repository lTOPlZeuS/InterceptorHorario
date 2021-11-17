package com.bolsaideas.springboot.horariointerceptor.app.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.lang.Exception ;
import java.util.Calendar;

@Component("horario")
public class HorarioInterceptors implements HandlerInterceptor{
  @Value("${config.horario.apertura}")
  private Integer apertura;
  @Value("${config.horario.cierre}")
  private Integer cierre;

  public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
    Calendar calendario  = Calendar.getInstance();
    int hora = calendario.get(Calendar.HOUR_OF_DAY);
    if (apertura >= hora  && cierre < hora) {
      StringBuilder mensaje = new StringBuilder("Bienvenido al horario de atencion a clientes,");
      mensaje.append(" el horario de atencion es de ");
      mensaje.append(apertura);
      mensaje.append(" a ");
      mensaje.append(cierre);
      mensaje.append(" horas");;
      request.setAttribute("mensaje", mensaje.toString());
      return true;
    }
    response.sendRedirect(request.getContextPath().concat("/cerrado"));
    return false;
  }

  public void postHandle(HttpServletRequest  request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
    String mensaje = (String) request.getAttribute("mensaje");
    if (mensaje != null && handler instanceof HandlerMethod) {
      modelAndView.addObject("mensaje", mensaje);
    }
  }
  
}

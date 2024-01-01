package com.jsfcourse.params;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import jakarta.faces.view.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.inject.Inject;
import jakarta.inject.Named;


@Named
@ViewScoped
public class ParamReceiverBB implements Serializable {
	private static final long serialVersionUID = 1L;
	private String param1;
	private String param2;

	@Inject
	FacesContext ctx;
	
	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}
	
	public List<Param> requestParams(){
		//pobranie obiektu żądania
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		List<Param> list = new LinkedList<Param>();
		
		//pętla po przechowywanych obiektach
		Enumeration<String> enumeration = req.getParameterNames();
	    while (enumeration.hasMoreElements()) {
	        String parameterName = enumeration.nextElement();
	        //gdy znana jest nazwa parametru można pobrać jego wartość
	        Object parameterValue = req.getParameter(parameterName);
	        
	        //dodanie nazwy parametru i jego wartości do listy
	        Param param = new Param(parameterName, parameterValue);
	        list.add(param);
	    }
	    
		return list;
	}
	
	public List<Param> requestAttribs(){
		//pobranie obiektu żądania
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		List<Param> list = new LinkedList<Param>();
		
		//pętla po przechowywanych obiektach
		Enumeration<String> enumeration = req.getAttributeNames();
	    while (enumeration.hasMoreElements()) {
	        String atrName = enumeration.nextElement();
	        //gdy znana jest nazwa atrybutu można pobrać jego wartość
	        Object atrValue = req.getAttribute(atrName);
	        
	        //dodanie nazwy parametru i jego wartości do listy
	        Param param = new Param(atrName, atrValue);
	        list.add(param);
	    }
	    
		return list;
	}

	public List<Param> sessionAttribs(){
		//pobranie obiektu sesji
		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
		List<Param> list = new LinkedList<Param>();
		
		//pętla po przechowywanych obiektach
		Enumeration<String> enumeration = session.getAttributeNames();
	    while (enumeration.hasMoreElements()) {
	        String atrName = enumeration.nextElement();
	        //gdy znana jest nazwa atrybutu można pobrać jego wartość
	        Object atrValue = session.getAttribute(atrName);
	        
	        //dodanie nazwy parametru i jego wartości do listy
	        Param param = new Param(atrName, atrValue);
	        list.add(param);
	    }
	    
		return list;	    
	}
	
	public String backToIndex(){
		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
	    session.invalidate(); //usunięcie wszystkich zapamiętanych obiektów z sesji
	    return "index?faces-redirect=true";
	}

	public List<Param> requestData(){
		//pobranie obiektu żądania
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		List<Param> list = new LinkedList<Param>();
		
        Param param = null;
        param = new Param("header(\"Accept-Encoding\")", req.getHeader("Accept-Encoding"));      
        list.add(param);       
        param = new Param("header(\"Accept-Language\")", req.getHeader("Accept-Language"));      
        list.add(param);       
        param = new Param("header(\"User-Agent\")", req.getHeader("User-Agent"));      
        list.add(param);       
        param = new Param("contextPath", req.getContextPath());      
        list.add(param);       
        param = new Param("characterEncoding", req.getCharacterEncoding());      
        list.add(param);       
        param = new Param("locale.language", req.getLocale().getLanguage());      
        list.add(param);       
        param = new Param("localAddr", req.getLocalAddr());      
        list.add(param);       
        param = new Param("localName", req.getLocalName());      
        list.add(param);       
        param = new Param("localPort", req.getLocalPort());      
        list.add(param);       
        param = new Param("method", req.getMethod());      
        list.add(param);       
        param = new Param("protocol", req.getProtocol());      
        list.add(param);       
        param = new Param("queryString", req.getQueryString());      
        list.add(param);       
        param = new Param("remoteAddr", req.getRemoteAddr());
        list.add(param);       
        param = new Param("remoteHost", req.getRemoteHost());
        list.add(param);       
        param = new Param("remotePort", req.getRemotePort());
        list.add(param);       
        param = new Param("requestSessionId", req.getRequestedSessionId());      
        list.add(param);       
        param = new Param("requestURI", req.getRequestURI());
        list.add(param);       
        param = new Param("requestURL", req.getRequestURL());
        list.add(param);       
        param = new Param("serverName", req.getServerName());
        list.add(param);       
        param = new Param("serverPort", req.getServerPort());      
        list.add(param);       
        param = new Param("servletPath", req.getServletPath());      
        list.add(param);       
        param = new Param("session.id", req.getSession().getId());      
        list.add(param);       
        param = new Param("requestedSessionIdFromCookie", req.isRequestedSessionIdFromCookie());      
        list.add(param);       
        param = new Param("requestedSessionIdFromURL", req.isRequestedSessionIdFromURL());      
        list.add(param);       
        param = new Param("session.servletContext.realPath(\"test\")", req.getSession().getServletContext().getRealPath("test"));      
        list.add(param);       
        param = new Param("secure", req.isSecure());      
        list.add(param);       
        
		return list;
	}

	public List<Param> requestHeaders(){
		//pobranie obiektu żądania
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		List<Param> list = new LinkedList<Param>();
		
		//pętla po nagłówkach
		Enumeration<String> enumeration = req.getHeaderNames();
	    while (enumeration.hasMoreElements()) {
	        String headName = enumeration.nextElement();
	        //gdy znana jest nazwa nagłówka można pobrać jego wartość
	        Object headValue = req.getHeader(headName);
	        
	        //dodanie nazwy nagłówka i jego wartości do listy
	        Param param = new Param(headName, headValue);
	        list.add(param);
	    }
        
		return list;
	}

	public List<Param> requestCookies(){
		//pobranie obiektu żądania
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		List<Param> list = new LinkedList<Param>();
		
		//pętla po ciastkach
		Cookie cookies[] = req.getCookies();
	    for (Cookie c:cookies) {	        
	        //dodanie ciacha do wyświetlenia
	        Param param = new Param(c.getName(), c.getValue());
	        list.add(param);
	    }
        
		return list;
	}

	public List<Param> sessionData(){
		//pobranie obiektu sesji
		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
		List<Param> list = new LinkedList<Param>();
		
        Param param = null;
        param = new Param("id", session.getId());      
        list.add(param);       
        param = new Param("lastAccessedTime", session.getLastAccessedTime());      
        list.add(param);       
        param = new Param("maxInactiveInterval", session.getMaxInactiveInterval());      
        list.add(param);       
        param = new Param("servletContext.realPath(\"test\")", session.getServletContext().getRealPath("test"));      
        list.add(param);       
        param = new Param("servletContext.serverInfo", session.getServletContext().getServerInfo());      
        list.add(param);       
	    
		return list;	    
	}
	
}

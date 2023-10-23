package com.jsfcourse.calc;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@RequestScoped
public class DeltaBB {
	private String a;
	private String b;
	private String c;
	private Double result;
	
	@Inject
	FacesContext ctx;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}
	
	public boolean countDelta() {
		try {
			double a = Double.parseDouble(this.a);
			double b = Double.parseDouble(this.b);
			double c = Double.parseDouble(this.c);
			
			result =  ((b*b) - (4 * a * c));
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
			return true;
		} catch (Exception e) {
			ctx.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów", null));
			return false;
		}
	}
	//zrobimy ajaxowo *spróbujemy*
	public String delta_AJAX() {
		if (countDelta()) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delta wynosi: " + result, null));
		}
		return null;
	}
	
	public String index() {
		return "index";
	}
	
}
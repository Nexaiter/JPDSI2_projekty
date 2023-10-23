package com.jsfcourse.calc;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@RequestScoped
public class DeltaBB {
    private String a;
    private String b;
    private String c;
    private Double result;
    private Double x1;
    private Double x2;
    private static Double resultStatic;
    private static Double x1Static;
    private static Double x2Static;
    
    
    private boolean resultsVisible = false; // Flag to control result visibility

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

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    public boolean isResultsVisible() {
        return resultsVisible;
    }

    public void setResultsVisible(boolean resultsVisible) {
        this.resultsVisible = resultsVisible;
    }

    // Funkcja walidacyjna
    public boolean validateInput(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean countDelta() {
        if (!validateInput(a) || !validateInput(b) || !validateInput(c)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne dane wejściowe, podaj poprawne liczby", null));
            return false;
        }
        
        try {
            double a = Double.parseDouble(this.a);
            double b = Double.parseDouble(this.b);
            double c = Double.parseDouble(this.c);

            result = (b * b) - (4 * a * c);

            if (result >= 0) {
                x1 = (-b + Math.sqrt(result)) / (2 * a);
                x2 = (-b - Math.sqrt(result)) / (2 * a);

                x1 = Math.round(x1 * 100.0) / 100.0;
                x2 = Math.round(x2 * 100.0) / 100.0;
                               
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie, proszę czekać", null));
                
             
            } else {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delta ujemna - brak miejsc zerowych", null));
            }

            return true;
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów", null));
            return false;
        }
    }
    
    public void showDelta() {
    	ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delta wynosi: " + resultStatic, null));
        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "x1 = " + x1Static, null));
        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "x2 = " + x2Static, null));
    }

    // AJAXowo
    public String delta_AJAX() {
        if (countDelta()) {
            if (result >= 0) {           
            	resultStatic = result;
            	x1Static = x1;
            	x2Static = x2;
                resultsVisible = true;
                
            }
        }
        return null;
    }

   
}

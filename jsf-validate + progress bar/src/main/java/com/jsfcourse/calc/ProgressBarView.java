package com.jsfcourse.calc;

import java.io.Serializable;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ProgressBarView implements Serializable {

    private Integer progress1;
    private Integer progress2;

    public void longRunning() throws InterruptedException {
        setProgress2(0);
        Integer k = getProgress2();
        while (k == null || k < 100) {
            k = updateProgress(k);
            setProgress2(k);
        }
    }

    private static Integer updateProgress(Integer progress) {
        if (progress == null) {
            progress = 0;
        }
        else {
            progress = progress + (int) (Math.random() * 50);

            if (progress > 100) {
                progress = 100;
            }
        }

        return progress;
    }

    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Progress Completed"));
        setProgress1(100); // Set progress to 100% when completed
    }

    public void cancel() {
        progress1 = null;
        progress2 = null;
    }

    public Integer getProgress1() {
        progress1 = updateProgress(progress1);
        return progress1;
    }

    public Integer getProgress2() {
        return progress2;
    }

    public void setProgress1(Integer progress1) {
        this.progress1 = progress1;
    }

    public void setProgress2(Integer progress2) {
        this.progress2 = progress2;
    }
}
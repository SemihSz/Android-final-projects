package com.example.semihprojects;

public class Scores {

    private String midterm;
    private String project;
    private String finalscores;
    private String dates;
    private String result;
    private String general;

    public Scores(String general) {
        this.general = general;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public Scores() {
    }

    public Scores(String midterm, String project, String finalscores, String dates, String result) {
        this.midterm = midterm;
        this.project = project;
        this.finalscores = finalscores;
        this.dates = dates;
        this.result = result;
    }

    public Scores(String dates, String result) {
        this.dates = dates;
        this.result = result;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Scores(String midterm, String project, String finalscores) {
        this.midterm = midterm;
        this.project = project;
        this.finalscores = finalscores;
    }

    public String getMidterm() {
        return midterm;
    }

    public void setMidterm(String midterm) {
        this.midterm = midterm;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getFinalscores() {
        return finalscores;
    }

    public void setFinalscores(String finalscores) {
        this.finalscores = finalscores;
    }
}

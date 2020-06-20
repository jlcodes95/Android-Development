package com.example.labtest2;

import java.io.Serializable;

public class Case implements Serializable {

    private int totalCases;
    private int totalRecovered;
    private int totalDeaths;
    private String province;

    public Case() {}

    public Case(int totalCases, int totalRecovered, int totalDeaths, String province) {
        this.totalCases = totalCases;
        this.totalRecovered = totalRecovered;
        this.totalDeaths = totalDeaths;
        this.province = province;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getProvince() {
        return province;
    }

    public void setProvinces(String province) {
        this.province = province;
    }
}

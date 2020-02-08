package com.example.countriesapp;

import java.util.Comparator;

public class Country implements Comparable<Country>{

    private String countryName;
    private String capital;
    private long population;

    public Country(String countryName, String capital, long population){
        this.countryName = countryName;
        this.capital = capital;
        this.population = population;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public int compareTo(Country country) {
        return ((int)this.getPopulation()) - ((int) country.getPopulation());
    }

    public static Comparator<Country> CountryComparator = new Comparator<Country>() {
        public int compare(Country country1, Country country2){

            return country1.compareTo(country2);
        }

    };

}

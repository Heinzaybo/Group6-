package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }


    @Test
    void printSalariesTestEmpty()
    {
        ArrayList<Country> employess = new ArrayList<Country>();
        app.reportCountry(employess,"Hello");
    }

    @Test
    void printSalariesTestContains()
    {
        ArrayList<Country> employess = new ArrayList<Country>();
        employess.add(null);
        app.reportCountry(employess,"Hello");
    }

    @Test
    void countryClassTesting()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        Country emp= new Country();
        emp.setCode("HZB");
        emp.setName("Hein Zay Bo");
        emp.setContinent("Asia");
        emp.setRegion("South East Asia");
        emp.setPopulation(55000);
        emp.setCapital("2710");
        countries.add(emp);
        App.reportCountry(countries,"HZB");
    }

    @Test
    void unitTest10()
    {
        assertThrows(NullPointerException.class, this::throwsException);
    }

    void throwsException() throws NullPointerException
    {
        throw new NullPointerException();
    }

}
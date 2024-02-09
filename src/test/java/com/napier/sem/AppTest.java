package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);
    }

    @Test
    public void testReportCountryIsEmpty() {
        // Prepare test data
        ArrayList<Country> emptyList = new ArrayList<>();
        // Call the method
        app.reportCountry(emptyList, "testReportEmpty.txt");
        // Verify output to console
        // This depends on how you want to assert console output.
        // You can use System.setOut and ByteArrayOutputStream for more advanced testing.
        // For simplicity, let's just assume it prints "No Countries" for now.
        // Verify file creation
        assertFalse(new File("./reports/testReportEmpty.txt").exists());
    }
    @Test
    public void testReportCountryIsNull() {
        // Prepare test data
        ArrayList<Country> emptyList = new ArrayList<>();
        emptyList = null;
        // Call the method
        app.reportCountry(emptyList, "testReportEmpty.txt");
        // Verify output to console
        // This depends on how you want to assert console output.
        // You can use System.setOut and ByteArrayOutputStream for more advanced testing.
        // For simplicity, let's just assume it prints "No Countries" for now.
        // Verify file creation
        assertFalse(new File("./reports/testReportEmpty.txt").exists());
    }
    @Test
    public void testReportCountryContainsNull() {
        // Prepare test data
        ArrayList<Country> emptyList = new ArrayList<>();
        emptyList.add(null);
        // Call the method
        app.reportCountry(emptyList, "testReportEmpty.txt");
        // Verify output to console
        // This depends on how you want to assert console output.
        // You can use System.setOut and ByteArrayOutputStream for more advanced testing.
        // For simplicity, let's just assume it prints "No Countries" for now.
        // Verify file creation
        assertFalse(new File("./reports/testReportEmpty.txt").exists());
    }
    @Test
    void reportCountryNormalData()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        Country c = new Country();
        c.setCode("MMR");
        c.setName("Myanmar");
        c.setPopulation(100000);
        c.setRegion("South East Asia");
        c.setContinent("Asia");
        c.setCapital("2710");
        countries.add(c);
        app.reportCountry(countries,"file");
    }
    @Test
    public void testGetCountryIsNull() {
        String a = null;
        assertNull(app.getCountries(a));
    }

    @Test
    public void testGetCountryIsEmpty() {
        String a = "";
        assertNull(app.getCountries(a));
    }
    @Test
    public void testGetCountryNormalData() {
        String a = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Code = 'MMR' ORDER BY Population DESC";
        ArrayList<Country> countries = app.getCountries(a);
        for (Country c : countries){
            assertEquals("Myanmar",c.getName());
        }
    }
    @Test
    public void testGetCountryWorld(){
        ArrayList<Country> cities= app.getCountriesWorld();
        assertNotNull(cities);
    }
    @Test
    public void testGetCountryWorld10(){
        ArrayList<Country> cities= app.getCountriesWorld10();
        assertNotNull(cities);
    }

    @Test
    public void testGetCountryContinent(){
        ArrayList<Country> cities= app.getCountriesContinent();
        assertNotNull(cities);
    }

    @Test
    public void testGetCountryContinent10(){
        ArrayList<Country> cities= app.getCountriesContinent10();
        assertNotNull(cities);
    }

    @Test
    public void testGetCountryRegion(){
        ArrayList<Country> cities= app.getCountriesRegion(false);
        assertNotNull(cities);
    }
    @Test
    public void testGetCountryRegion10(){
        List<Country> cities= app.getCountriesRegion10();
        assertNotNull(cities);
    }

    @Test
    public void testGetCitiesWorld(){
        ArrayList<City> cities= app.getCitiesWorld();
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesWorld10(){
        ArrayList<City> cities= app.getCitiesWorld10();
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesContinent(){
        ArrayList<City> cities= app.getCitiesContinent();
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesContinent10(){
        ArrayList<City> cities= app.getCitiesContinent10();
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesRegion(){
        ArrayList<City> cities= app.getCitiesRegion();
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesRegion10(){
        List<City> cities= app.getCitiesRegion10();
        assertNotNull(cities);
    }

    @Test
    public void testGetCitiesCountry(){
        List<City> cities= app.getCitiesCountry();
        assertNotNull(cities);
    }

    @Test
    public void testGetCitiesDistrict(){
        List<City> cities= app.getCitiesDistrict();
        assertNotNull(cities);
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
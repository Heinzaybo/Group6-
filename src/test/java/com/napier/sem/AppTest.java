package com.napier.sem;

import com.mysql.cj.protocol.Resultset;
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
    }

    @Test
    public void testReportCountryIsEmpty() {
        app.connect("localhost:33060", 100);

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
        app.connect("localhost:33060", 100);

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
        app.connect("localhost:33060", 100);

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
        app.connect("localhost:33060", 100);

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
        app.connect("localhost:33060", 100);
        String a = null;
        assertNull(app.getCountries(a));
    }

    @Test
    public void testGetCountryIsEmpty() {
        app.connect("localhost:33060", 100);
        String a = "";
        assertNull(app.getCountries(a));
    }
    @Test
    public void testGetCountryNormalData() {
        app.connect("localhost:33060", 100);
        String a = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Code = 'MMR' ORDER BY Population DESC";
        ArrayList<Country> countries = app.getCountries(a);
        for (Country c : countries){
            assertEquals("Myanmar",c.getName());
        }
    }
    @Test
    public void testGetCountryWorld(){
        app.connect("localhost:33060", 100);
        ArrayList<Country> cities= app.getCountriesWorld();
        assertNotNull(cities);
    }
    @Test
    public void testGetCountryWorldDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        ArrayList<Country> cities= app.getCountriesWorld();
        assertNull(cities);
    }
    @Test
    public void testGetCountryWorld10(){
        app.connect("localhost:33060", 100);
        ArrayList<Country> cities= app.getCountriesWorld10();
        assertNotNull(cities);
    }

    @Test
    public void testGetCountryContinent(){
        app.connect("localhost:33060", 100);
        ArrayList<Country> cities= app.getCountriesContinent();
        assertNotNull(cities);
    }

    @Test
    public void testGetCountryContinent10(){
        app.connect("localhost:33060", 100);
        ArrayList<Country> cities= app.getCountriesContinent10();
        assertNotNull(cities);
    }
    @Test
    public void testGetCountryContinent10Disconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        ArrayList<Country> cities= app.getCountriesContinent10();
        assertNull(cities);
    }

    @Test
    public void testGetCountryRegion(){
        app.connect("localhost:33060", 100);
        ArrayList<Country> cities= app.getCountriesRegion(false);
        assertNotNull(cities);
    }
    @Test
    public void testGetCountryRegionDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        ArrayList<Country> cities= app.getCountriesRegion(false);
        assertNull(cities);
    }
    @Test
    public void testGetCountryRegion10(){
        app.connect("localhost:33060", 100);
        List<Country> cities= app.getCountriesRegion10();
        assertNotNull(cities);
    }

    @Test
    public void testGetCountryRegion10Disconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        List<Country> cities= app.getCountriesRegion10();
        assertNull(cities);
    }

    @Test
    public void testGetCitiesWorld(){
        app.connect("localhost:33060", 100);
        ArrayList<City> cities= app.getCitiesWorld();
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesWorldDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        ArrayList<City> cities= app.getCitiesWorld();
        assertNull(cities);
    }
    @Test
    public void testGetCitiesWorld10(){
        app.connect("localhost:33060", 100);
        ArrayList<City> cities= app.getCitiesWorld10();
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesWorld10Disconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        ArrayList<City> cities= app.getCitiesWorld10();
        assertNull(cities);
    }
    @Test
    public void testGetCitiesContinent(){
        app.connect("localhost:33060", 100);
        ArrayList<City> cities= app.getCitiesContinent();
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesContinent10(){
        app.connect("localhost:33060", 100);
        ArrayList<City> cities= app.getCitiesContinent10();
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesRegion(){
        app.connect("localhost:33060", 100);
        ArrayList<City> cities= app.getCitiesRegion(true);
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesRegion10(){
        app.connect("localhost:33060", 100);
        List<City> cities= app.getCitiesRegion10();
        assertNotNull(cities);
    }

    @Test
    public void testGetCitiesCountry(){
        app.connect("localhost:33060", 100);
        List<City> cities= app.getCitiesCountry();
        assertNotNull(cities);
    }

    @Test
    public void testGetCitiesDistrict(){
        app.connect("localhost:33060", 100);
        List<City> cities= app.getCitiesDistrict();
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesCapitalCitiesWorld(){
        app.connect("localhost:33060", 100);
        List<City> cities= app.getCapitalCitiesWorld(true);
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesCapitalCitiesContinent(){
        app.connect("localhost:33060", 100);
        List<City> cities= app.getCapitalCitiesContinent(true);
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesCapitalCitiesRegion(){
        app.connect("localhost:33060", 100);
        List<City> cities= app.getCapitalCitiesRegion(true);
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesCapitalCitiesWorld10(){
        app.connect("localhost:33060", 100);
        List<City> cities= app.getCapitalCitiesWorld10(true);
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesCapitalCitiesContinent10(){
        app.connect("localhost:33060", 100);
        List<City> cities= app.getCapitalCitiesContinent10(true);
        assertNotNull(cities);
    }
    @Test
    public void testGetCitiesCapitalCitiesRegion10(){
        app.connect("localhost:33060", 100);
        List<City> cities= app.getCapitalCitiesRegion10(true);
        assertNotNull(cities);
    }
    @Test
    public void PopulationRegion(){
        app.connect("localhost:33060", 100);
        Resultset cities= app.populationRegion();
        assertNotNull(cities);
    }
    @Test
    public void PopulationRegionDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        Resultset cities= app.populationRegion();
        assertNull(cities);
    }
    @Test
    public void testPopulationContinent(){
        app.connect("localhost:33060", 100);
        Resultset cities= app.populationContinent();
        assertNotNull(cities);
    }
    @Test
    public void testPopulationContinentDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        Resultset cities= app.populationContinent();
        assertNull(cities);
    }

    @Test
    public void testPopulationRegion(){
        app.connect("localhost:33060", 100);
        Resultset cities= app.populationRegion();
        assertNotNull(cities);
    }
    @Test
    public void testPopulationRegionDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        Resultset cities= app.populationRegion();
        assertNull(cities);
    }

    @Test
    public void testPopulationCountry(){
        app.connect("localhost:33060", 100);
        String cities= app.populationCountry();
        assertNotNull(cities);
    }
    @Test
    public void testPopulationCountryDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        String cities= app.populationCountry();
        assertNull(cities);
    }
    @Test
    public void testContinentPopulation(){
        app.connect("localhost:33060", 100);
        long population = app.ContinentPopulation();
        assertNotEquals(0,population);
    }
    @Test
    public void testContinentPopulationDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        long population = app.ContinentPopulation();
        assertEquals(0,population);
    }
    @Test
    public void testRegionPopulation(){
        app.connect("localhost:33060", 100);
        long population = app.ContinentPopulation();
        assertNotEquals(0,population);
    }
    @Test
    public void testRegionPopulationDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        long population = app.ContinentPopulation();
        assertEquals(0,population);
    }
    @Test
    public void testCountryPopulation(){
        app.connect("localhost:33060", 100);
        long population = app.CountryPopulation();
        assertNotEquals(0,population);
    }
    @Test
    public void testCountryPopulationDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        long population = app.CountryPopulation();
        assertEquals(0,population);
    }
    @Test
    public void testDistrictPopulation(){
        app.connect("localhost:33060", 100);
        long population = app.DistrictPopulation();
        assertNotEquals(0,population);
    }
    @Test
    public void testDistrictPopulationDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        long population = app.DistrictPopulation();
        assertEquals(0,population);
    }
    @Test
    public void testCityPopulation(){
        app.connect("localhost:33060", 100);
        long population = app.CityPopulation();
        assertNotEquals(0,population);
    }
    @Test
    public void testCityPopulationDisconnect(){
        app.connect("localhost:33060", 100);
        app.disconnect();
        long population = app.CityPopulation();
        assertEquals(0,population);
    }

    @Test
    void testDisconnect() {
        App app = new App();
        app.connect("localhost:3306", 100); // Establish connection first
        app.disconnect(); // Disconnect
        assertNull(app.getCon()); // Check if connection is closed
    }





}
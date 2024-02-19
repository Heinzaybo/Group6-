package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 100);
    }


    @Test
    void testGetCountries()
    {
        String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Code = 'MMR' ORDER BY Population DESC";
        ArrayList<Country> country = app.getCountries(query);
        for(Country c : country){
            assertEquals(c.getName(), "Myanmar");
            assertEquals(c.getContinent(), "Asia");
            assertEquals(c.getRegion(), "Southeast Asia");
        }
    }
    @Test
    void testGetCountriesWorld(){
        ArrayList<Country> countries = app.getCountriesWorld();
        assertEquals(countries.get(0).getName(), "China");
    }
    @Test
    void testGetCountriesWorld10(){
        ArrayList<Country> countries = app.getCountriesWorld10();
        assertEquals( "China",countries.get(0).getName());
    }
    @Test
    void testGetCountriesContinent(){
        ArrayList<Country> countries = app.getCountriesContinent();
        assertEquals( "China",countries.get(0).getName());
    }
    @Test
    void testGetCountriesContinent10(){
        ArrayList<Country> countries = app.getCountriesContinent10();
        assertEquals( "China",countries.get(0).getName());
    }
    @Test
    void testGetCountriesRegion(){
        ArrayList<Country> countries = app.getCountriesRegion(true);
        assertEquals("Indonesia",countries.get(0).getName() );
    }
    @Test
    void testGetCountriesRegion10(){
        List<Country> countries = app.getCountriesRegion10();
        assertEquals("Indonesia",countries.get(0).getName());
    }
    @Test
     void testGetCitiesWorld(){
        ArrayList<City> cities = app.getCitiesWorld();
        assertEquals("Mumbai (Bombay)",cities.get(0).getName());
    }
    @Test
    void testGetCitiesWorld10(){
        ArrayList<City> cities = app.getCitiesWorld10();
        assertEquals("Mumbai (Bombay)",cities.get(0).getName());
    }

    @Test
    void testGetCitiesContinent(){
        ArrayList<City> cities = app.getCitiesContinent();
        assertEquals("Mumbai (Bombay)",cities.get(0).getName());
    }
    @Test
    void testGetCitiesContinent10(){
        ArrayList<City> cities = app.getCitiesContinent10();
        assertEquals("Mumbai (Bombay)",cities.get(0).getName());
    }
    @Test
    void testGetCitiesRegion(){
        ArrayList<City> cities = app.getCitiesRegion();
        assertEquals("Jakarta",cities.get(0).getName());
    }
    @Test
    void testGetCitiesRegion10(){
        List<City> cities = app.getCitiesRegion10();
        assertEquals("Jakarta",cities.get(0).getName());
    }

    @Test
    void testGetCitiesCountry(){
        ArrayList<City> cities = app.getCitiesCountry();
        assertEquals("Rangoon (Yangon)",cities.get(0).getName());
    }

    @Test
    void testGetCitiesDistrict(){
        List<City> cities = app.getCitiesDistrict();
        assertEquals("Mandalay",cities.get(0).getName());
    }

    @Test
    void testGetCitiesDistrict10(){
        List<City> cities = app.getCitiesDistrict();
        assertEquals("Mandalay",cities.get(0).getName());
    }
}
package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);
    }

    @Test
    void testGetCitiesWorld()
    {
        app.getCitiesWorld();
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
}
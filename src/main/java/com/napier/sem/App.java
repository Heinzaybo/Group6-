package com.napier.sem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private static Connection con = null;

    public static Connection getCon() {
        return con;
    }

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " +                                  Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
    /**
     * Retrieve information about countries from the database and generate a report.
     */
    public ArrayList<Country> getCountriesWorld(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC";
            ArrayList<Country> countries = getCountries(strSelect);
            reportCountry(countries,"Countries_in_World");
            return countries;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Country> getCountriesWorld10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC LIMIT 10";
            ArrayList<Country> countries = getCountries(strSelect);
            reportCountry(countries,"Countries_in_World");
            return countries;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Country> getCountriesContinent() {
        return getCountriesContinent(true); // Delegate to the overloaded method with default parameter value
    }
    public ArrayList<Country> getCountriesContinent(boolean isOutput){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = 'Asia' ORDER BY Population DESC";
            ArrayList<Country> countries = getCountries(strSelect);
            if(isOutput){
                reportCountry(countries,"Countries_in_Continent");
            }
            return countries;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Country> getCountriesContinent10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = 'Asia' ORDER BY Population DESC LIMIT 10 ";
            ArrayList<Country> countries = getCountries(strSelect);
            reportCountry(countries,"Countries_in_World");
            return countries;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Country> getCountriesRegion(boolean isOutput){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Region = 'Southeast Asia' ORDER BY Population DESC";
            ArrayList<Country> countries = getCountries(strSelect);
            if(isOutput){
                reportCountry(countries,"Countries_in_World");
            }
            return countries;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Country> getCountriesRegion10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Region = 'Southeast Asia' ORDER BY Population DESC LIMIT 10 ";
            ArrayList<Country> countries = getCountries(strSelect);
            reportCountry(countries,"Countries_in_World");
            return countries;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void  reportCountry(ArrayList<Country> countries,String filename){
        if (countries == null || countries.isEmpty()){
            System.out.println("No Countries");
            return;
        }

        //Creating String builder for formatting string
        StringBuilder sb = new StringBuilder();
        //Formatting string for header
        sb.append(String.format("%-10s %-50s %-20s %-30s %-20s %-10s\n", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        for (Country country : countries)
        {
            if(country == null){
                System.out.println("No Country");
                return;
            }
            String cname = null;
            try {
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect;
                strSelect = "SELECT Name FROM city WHERE ID ="+country.getCapital()+" ORDER BY Population DESC";
                ResultSet rset = stmt.executeQuery(strSelect);
                while (rset.next()){
                    cname = rset.getString("Name");
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Failed to get cities details");

            }
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            sb.append(String.format("%-10s %-50s %-20s %-30s %-20s %-10s\n",
                    country.getCode(), country.getName(), country.getContinent(), country.getRegion(), numberFormatter.format(country.getPopulation()), cname));
        }
        //displaying output to console
        System.out.println(sb.toString());

        //Writing Report file
        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Country> getCountries(String strSelect)
    {
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next())
            {
                Country c = new Country();
                c.setCode(rset.getString("Code"));
                c.setName(rset.getString("Name"));
                c.setContinent(rset.getString("Continent"));
                c.setRegion(rset.getString("Region"));
                c.setPopulation(rset.getInt("Population"));
                c.setCapital(rset.getString("Capital"));
                countries.add(c);
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Countries details");
            return null;
        }
    }

    public ArrayList<City> getCitiesWorld(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT CountryCode, Name, District, Population FROM city ORDER BY Population DESC";
            ArrayList<City> cities = getCities(strSelect);
            reportCities(cities,"Cities_in_World");
            return cities;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<City> getCitiesWorld10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT CountryCode, Name, District, Population FROM city ORDER BY Population DESC LIMIT 10";
            ArrayList<City> cities =getCities(strSelect);
            reportCities(cities,"Cities_in_World");
            return cities;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<City> getCitiesContinent(){
        return getCitiesContinent(true);
    }

    public ArrayList<City> getCitiesContinent(boolean isOutput){
        try{
            // Create string for SQL statement
            ArrayList<Country> asia =  getCountriesContinent(false);
            ArrayList<City> cities = new ArrayList<City>();
            for (Country country : asia){
                String query = "SELECT CountryCode, Name, District, Population FROM city WHERE CountryCode ='"+country.getCode()+"' ORDER BY Population DESC";
                cities.addAll(getCities(query));
            }
            // Sort the ArrayList by population
            cities.sort(new Comparator<City>() {
                @Override
                public int compare(City city1, City city2) {
                    return Integer.compare(city2.getPopulation(), city1.getPopulation());
                }
            });
            if (isOutput){
                reportCities(cities,"Cities_in_Continent");
            }
            return cities;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<City> getCitiesContinent10(){
        try {
            ArrayList<City> cities = getCitiesContinent(false);
            ArrayList<City> top10Cities = new ArrayList<>(cities.subList(0, Math.min(cities.size(), 10)));
            reportCities(top10Cities,"Cities_Continent_10");
            return top10Cities;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<City> getCitiesRegion(){
        return getCitiesRegion(true);
    }

    public ArrayList<City> getCitiesRegion(boolean isOutput){
        try{
            // Create string for SQL statement
            ArrayList<Country> asia =  getCountriesRegion(false);
            ArrayList<City> cities = new ArrayList<City>();
            for (Country country : asia){
                String query = "SELECT CountryCode, Name, District, Population FROM city WHERE CountryCode ='"+country.getCode()+"' ORDER BY Population DESC";
                cities.addAll(getCities(query));
            }
            // Sort the ArrayList by population
            cities.sort(new Comparator<City>() {
                @Override
                public int compare(City city1, City city2) {
                    return Integer.compare(city2.getPopulation(), city1.getPopulation());
                }
            });
            if (isOutput){
                reportCities(cities,"Cities_in_Continent");
            }
            return cities;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<City> getCitiesRegion10(){
        try {
            ArrayList<City> cities = getCitiesRegion(false);
            ArrayList<City> top10Cities = new ArrayList<>(cities.subList(0, Math.min(cities.size(), 10)));
            reportCities(top10Cities,"Cities_Region_10");
            return top10Cities;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<City> getCitiesCountry(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT CountryCode, Name, District, Population FROM city WHERE CountryCode='MMR' ORDER BY Population DESC";
            ArrayList<City> cities = getCities(strSelect);
            reportCities(cities,"Cities_in_Country");
            return cities;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<City> getCitiesDistrict(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT CountryCode, Name, District, Population FROM city WHERE District='Mandalay' ORDER BY Population DESC";
            ArrayList<City> cities = getCities(strSelect);
            reportCities(cities,"Cities_in_Region");
            return cities;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    public ArrayList<City> getCities(String strSelect)
    {
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            ArrayList<City> cities = new ArrayList<City>();
            while (rset.next())
            {
                City c = new City();
                c.setName(rset.getString("Name"));
                c.setCountryCode(rset.getString("CountryCode"));
                c.setDistrict(rset.getString("District"));
                c.setPopulation(rset.getInt("Population"));
                cities.add(c);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Countries details");
            return null;
        }
    }

    /**
     Reporting Cities
     */
    public static void  reportCities(ArrayList<City> cities,String filename){
        if(cities == null){
            System.out.println("No Cities");
            return ;
        }
        //Creating String builder for formatting string
        StringBuilder sb = new StringBuilder();
        //Formatting string for header
        sb.append(String.format("%-30s %-50s %-50s %-30s\n",  "Name", "Country", "District", "Population"));
        for (City city : cities)
        {
            String cname = null;
            try {
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect;
                strSelect = "SELECT Name FROM country WHERE Code ='"+city.getCountryCode()+"' ORDER BY Population DESC ";
                ResultSet rset = stmt.executeQuery(strSelect);
                while (rset.next()){
                    cname = rset.getString("Name");
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Failed to get cities details");

            }
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            sb.append(String.format("%-30s %-50s %-50s %-30s\n",
                    city.getName(), cname, city.getDistrict(), numberFormatter.format(city.getPopulation())));
        }
        //displaying output to console
        System.out.println(sb.toString());

        //Writing Report file
        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // Report Country

    public static void main(String[] args) {
        App a = new App();
        if(args.length < 1){
            a.connect("localhost:33060", 30000);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }
        System.out.println("-----Countries in the Whole World-----");
        a.getCountriesWorld();
        System.out.println("-----Top 10 Most Populated Countries in the Whole World-----");
        a.getCountriesWorld10();
        System.out.println("-----Countries in the Asia Continent-----");
        a.getCountriesContinent();
        System.out.println("-----Top 10 Most Populated Countries in Asia Continent-----");
        a.getCountriesContinent10();
        System.out.println("-----Countries in the Southeast Asia Region-----");
        a.getCountriesRegion(true);
        System.out.println("-----Top 10 Most Populated Countries in Southeast Asia Region-----");
        a.getCountriesRegion10();
        System.out.println("----- Cities in the whole world-----");
        a.getCitiesWorld();
        System.out.println("----- Cities in Asia Continent-----");
        a.getCitiesContinent(true);
        System.out.println("----- Cities in Southeast Asia Region-----");
        a.getCitiesRegion(true);
        System.out.println("----- Cities in Myanmar-----");
        a.getCitiesCountry();
        System.out.println("----- Cities in Mandalay District-----");
        a.getCitiesDistrict();

        a.disconnect();
    }

}
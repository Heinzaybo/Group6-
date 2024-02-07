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

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
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

    public void getCountriesWorld(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC";
            reportCountry(getCountries(strSelect),"Countries_in_World");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void getCountriesWorld10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC LIMIT 10";
            reportCountry(getCountries(strSelect),"Countries_in_World");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
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
    public void getCountriesContinent10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = 'Asia' ORDER BY Population DESC LIMIT 10 ";
            reportCountry(getCountries(strSelect),"Countries_in_World");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
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

    public void getCountriesRegion10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Region = 'Southeast Asia' ORDER BY Population DESC LIMIT 10 ";
            reportCountry(getCountries(strSelect),"Countries_in_World");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void  reportCountry(ArrayList<Country> countries,String filename){


        //Creating String builder for formatting string
        StringBuilder sb = new StringBuilder();
        //Formatting string for header
        sb.append(String.format("%-10s %-50s %-20s %-30s %-20s %-10s\n", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        for (Country country : countries)
        {
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



    public static void main(String[] args) {
        App a = new App();
        a.connect();
        a.getCountriesContinent();
        a.disconnect();
    }

}
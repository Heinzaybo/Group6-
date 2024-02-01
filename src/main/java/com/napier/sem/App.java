package com.napier.sem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

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

    public ArrayList<Country> getCountries(String query)
    {

        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect;
            if(query.equals("continent")){
                System.out.println("Continent query is selected");
                 strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital " + "FROM country WHERE Continent = 'Asia' ORDER BY Population DESC";
            }
            else if (query.equals("world")){
                 strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital " + "FROM country ORDER BY Population DESC";
            }
            else if(query.equals("region")){
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital " + "FROM country WHERE Region = 'Southeast Asia' ORDER BY Population DESC";
            }
            else {
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital " + "FROM country ORDER BY Population DESC";
            }
            // Execute SQL statement
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


    public static void  reportCountry(ArrayList<Country> countries,String filename){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-50s %-20s %-30s %-10s %-10s\n", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        for (Country country : countries)
        {
         sb.append(String.format("%-10s %-50s %-20s %-30s %-10s %-10s\n",
                 country.getCode(), country.getName(), country.getContinent(), country.getRegion(), country.getPopulation(), country.getCapital()));
        }
        System.out.println(sb.toString());
        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        countries.forEach(country->System.out.println(country.getName()));
    }
    public static void main(String[] args) {
        App a = new App();
        a.connect();

        ArrayList<Country> countries = a.getCountries("region");
        reportCountry(countries,"region");

        a.disconnect();
    }

}
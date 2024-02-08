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
                                + "/world?useSSL=false",
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
        // Check if the connection object is not null
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                // Handle any exception that occur during the closing process
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Retrieve information about countries from the database and generate a report.
     */
    public void getCountriesWorld(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC";
            // Call method to execute the SQL statement and retrieve countries data
            // Pass the result set to a method for generating a report
            reportCountry(getCountries(strSelect),"Countries_in_World");
        }
        catch (Exception e){
            // Catch any exceptions that occur during the process
            // Print the error message to the console
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieve information about the top 10 most populous countries in the world from the database
     * and generate a report.
     */
    public void getCountriesWorld10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC LIMIT 10";
            // Call method to execute the SQL statement and retrieve countries data
            // Pass the result set to a method for generating a report
            reportCountry(getCountries(strSelect),"Countries_in_World");
        }
        catch (Exception e){
            // Catch any exceptions that occur during the process
            // Print the error message to the console
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieve information about countries in a specific continent (default: Asia) from the database
     * and optionally generate a report;
     *
     * @return An ArrayList of Country objects representing the countries in the specified continent.
     */
    public ArrayList<Country> getCountriesContinent() {
        // Delegate to the overloaded method with default parameter value
        return getCountriesContinent(true); // Delegate to the overloaded method with default parameter value
    }

    /**
     * Retrieve information about countries in a specific continent (default: Asia) from the database
     * and optionally generate a report
     * @param isOutput A boolean indicating whether to generate a report or not.
     * @return An Arraylist of Country objects representing the countries in the specified continent.
     */
    public ArrayList<Country> getCountriesContinent(boolean isOutput){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = 'Asia' ORDER BY Population DESC";

            // Execute the SQL statement to retrieve data about countries in the specified continent
            ArrayList<Country> countries = getCountries(strSelect);

            // Generate a report if isOutput is true
            if(isOutput){
                reportCountry(countries,"Countries_in_Continent");
            }
            return countries; // Return the ArrayList of Country objects
        }
        catch (Exception e){
            // Catch any exceptions that occur during the process
            System.out.println(e.getMessage());
            return null; // Return null in case of an exception
        }
    }

    /**
     * Retrieve information about the top 10 most populous countries in a specific continent (default: Asia)
     * from the database and generate a report
     *
     * This method specifically focuses on the continent of Asia.
     */
    public void getCountriesContinent10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = 'Asia' ORDER BY Population DESC LIMIT 10 ";

            // Call method to execute the SQL statement and retrieve countries data
            // Pass the result set to a method for generating a report
            reportCountry(getCountries(strSelect),"Countries_in_World");
        }
        catch (Exception e){
            // Catch any exceptions that occur during the process
            // Print the error message to the console
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieve information about countries in a specific region (default: Southeast Asia) from the database
     * and optionally generate a report.
     *
     * @param isOutput A boolean indicating whether to generate a report or not.
     * @return An ArrayList of Country objects representing the countries in the specified region.
     */
    public ArrayList<Country> getCountriesRegion(boolean isOutput){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Region = 'Southeast Asia' ORDER BY Population DESC";
            // Execute the SQL statement to retrieve countries data
            ArrayList<Country> countries = getCountries(strSelect);

            // Generate a report if isOutput is true
            if(isOutput){
                reportCountry(countries,"Countries_in_World");
            }
            return countries; // Return the ArrayList of Country objects
        }
        catch (Exception e){
            // Catch any exceptions that occur during the process
            System.out.println(e.getMessage()); // Print the error message
            return null;
        }
    }

    /**
     * Retrieve information about the top 10 most populous countries in a specific region (default: Southeast Asia)
     * from the database and generate a report
     *
     * This method specifically focuses on the region of Southeast Asia.
     */
    public void getCountriesRegion10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Region = 'Southeast Asia' ORDER BY Population DESC LIMIT 10 ";

            // Call method to execute the SQL statement and retrieve countries data
            // Pass the result set to a method for generating a report
            reportCountry(getCountries(strSelect),"Countries_in_World");
        }
        catch (Exception e){
            // Catch any exceptions that occur during the process
            // Print the error message to the console
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generate a report based on the information of countries provided in the ArrayList.
     * The report includes details such as country code, name, continent, region, population and capital city.
     * Additionally, the report is saved to a file with the specified filename in the reports directory.
     *
     * @param countries An ArrayList containing Country objects representing countries.
     * @param filename The name of the file to which the report will be saved.
     */
    public static void  reportCountry(ArrayList<Country> countries,String filename){
        if(countries.isEmpty()){
            System.out.println("Country is Empty");
            return;
        }
        //Creating String builder for formatting string
        StringBuilder sb = new StringBuilder();
        //Formatting string for header
        sb.append(String.format("%-10s %-50s %-20s %-30s %-20s %-10s\n", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        for (Country country : countries)
        {
            if(country == null ){
                System.out.println("Country is null");
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
                // Handle any exceptions that occur during the process of retrieving capital city name
                System.out.println(e.getMessage());
                System.out.println("Failed to get cities details");

            }
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            // Append country details to the string builder with proper formatting
            sb.append(String.format("%-10s %-50s %-20s %-30s %-20s %-10s\n",
                    country.getCode(), country.getName(), country.getContinent(), country.getRegion(), numberFormatter.format(country.getPopulation()), cname));
        }
        //displaying output to console
        System.out.println(sb.toString());

        //Writing Report file
        try {
            new File("./reports/").mkdir();// Create the reports directory if it doesn't exist
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            // Handle any IOException that might occur during the file writing process
            e.printStackTrace();
        }

    }

    /**
     * Retrieve countries information from the database based on the provided SQL SELECT statement.
     *
     * @param strSelect the SQL SELECT statement used to retrieve countries' information.
     * @return An ArrayList containing Country objects representing the retrieved countries' information.
     */
    public ArrayList<Country> getCountries(String strSelect)
    {
        try
        {
            // Create a statement object to execute the SQL query
            Statement stmt = con.createStatement();

            // Execute the SQL query and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            // Return new employee if valid.
            // Check one is returned
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next())
            {
                // Create new Country object
                Country c = new Country();

                //Set the attributes of the Country object based on the retrieved data from the result set
                c.setCode(rset.getString("Code"));
                c.setName(rset.getString("Name"));
                c.setContinent(rset.getString("Continent"));
                c.setRegion(rset.getString("Region"));
                c.setPopulation(rset.getInt("Population"));
                c.setCapital(rset.getString("Capital"));

                // Add the Country object to the ArrayList
                countries.add(c);
            }
            // Return ArrayList containing the retrieved countries' information
            return countries;
        }
        catch (Exception e)
        {
            // Handle any exceptions that occur during the process
            // Print the error message to the console
            System.out.println(e.getMessage());
            System.out.println("Failed to get Countries details");
            return null;
        }
    }



    public static void main(String[] args) {
        // Create an instance of the App class
        App a = new App();
        if(args.length < 1){
            a.connect("localhost:33060", 30000);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        // Connect to SQL database
        a.getCountriesContinent();
        // Disconnect from the MySQL database
        a.disconnect();
    }

}
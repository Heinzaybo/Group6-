package com.napier.sem;

import com.mysql.cj.protocol.Resultset;

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

    public Connection getCon() {
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
            // Exit the program with error status
            System.exit(-1);
        }
               
        // Number of retries to connect to the database
        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            // Print status message indicating attempt to connect
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
                con = null;
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
    /**
     * Retrieve information about countries from the database and generate a report.
     */

    public ArrayList<Country> getCountriesWorld10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC LIMIT 10";
            // Retrieve countries based on the SQL query
            ArrayList<Country> countries = getCountries(strSelect);
            // Retrieve countries based on the SQL query
            reportCountry(countries,"Countries_in_World");
            return countries;
        }
        catch (Exception e){
            // Print any exceptions that occur during the process
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
            // Retrieve countries based on the SQL query
            ArrayList<Country> countries = getCountries(strSelect);
           // If specified, report the countries to console and file
            if(isOutput){
                reportCountry(countries,"Countries_in_Continent");
            }
            return countries;
        }
        catch (Exception e){
         // Print any exceptions that occur during the process
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Country> getCountriesContinent10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = 'Asia' ORDER BY Population DESC LIMIT 10 ";
            // Retrieve countries based on the SQL query
            ArrayList<Country> countries = getCountries(strSelect);
            // Generate and report the countries to console and file
            reportCountry(countries,"Countries_in_World");
            return countries;
        }
        catch (Exception e){
            // Print any exceptions that occur during the process
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
            // Retrieve countries based on the SQL query
            ArrayList<Country> countries = getCountries(strSelect);
            // Generate and report the countries to console and file
            reportCountry(countries,"Countries_in_World");
            return countries;
        }
        catch (Exception e){
            // Print any exceptions that occur during the process
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Method to report countries to console and file
    public static void  reportCountry(ArrayList<Country> countries,String filename){
        if (countries == null || countries.isEmpty()){
            // If no countries are retrieved, print a message and return
            System.out.println("No Countries");
            return;
        }

        //Creating String builder for formatting string
        StringBuilder sb = new StringBuilder();
        //Formatting string for header
        sb.append(String.format("%-10s %-50s %-20s %-30s %-20s %-10s\n", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        // Iterate over each country to retrieve details and format them
        for (Country country : countries)
        {
            if(country == null){
                // If a country is null, print a message and return
                System.out.println("No Country");
                return;
            }
            String cname = null;
            try {
                // Create statement to execute SQL query to get capital city name
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect;
                strSelect = "SELECT Name FROM city WHERE ID ="+country.getCapital()+" ORDER BY Population DESC";
                // Execute query and retrieve capital city name
                ResultSet rset = stmt.executeQuery(strSelect);
                while (rset.next()){
                    cname = rset.getString("Name");
                }

            }catch (Exception e){
                // Print any exceptions that occur during the process
                System.out.println(e.getMessage());
                System.out.println("Failed to get cities details");

            }
            // Format population number with locale-specific formatting
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            // Append country details to StringBuilder
            sb.append(String.format("%-10s %-50s %-20s %-30s %-20s %-10s\n",
                    country.getCode(), country.getName(), country.getContinent(), country.getRegion(), numberFormatter.format(country.getPopulation()), cname));
        }
        //displaying output to console
        System.out.println(sb.toString());

        //Writing Report file
        try {
            // Create directory for reports if it doesn't exist
            new File("./reports/").mkdir();
            // Write formatted output to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            // Print any exceptions that occur during the file writing process
            e.printStackTrace();
        }

    }

    public ArrayList<Country> getCountries(String strSelect)
    {
        try
        {
            // Create a statement object to execute SQL queries
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next())
            {
                // Create a new Country object
                Country c = new Country();
                // Set the properties of the Country object using data from the result set
                c.setCode(rset.getString("Code"));
                c.setName(rset.getString("Name"));
                c.setContinent(rset.getString("Continent"));
                c.setRegion(rset.getString("Region"));
                c.setPopulation(rset.getInt("Population"));
                c.setCapital(rset.getString("Capital"));
                // Add the Country object to the ArrayList
                countries.add(c);
            }
            // Return the ArrayList of countries
            return countries;
        }
        // Catch any exceptions that occur during execution
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            // Print a message indicating failure to get country details
            System.out.println("Failed to get Countries details");
            return null;
        }
    }

    public ArrayList<City> getCitiesWorld(){
        try{
            // Create string for SQL statement
            String strSelect;
            // Construct the SQL query to select cities ordered by population
            strSelect = "SELECT CountryCode, Name, District, Population FROM city ORDER BY Population DESC";

            // Call the getCities method passing the SQL query as a parameter
            ArrayList<City> cities = getCities(strSelect);
            
            
            // Generate a report for the cities retrieved
            reportCities(cities,"Cities_in_World");
            return cities;
        }
        // Catch any exceptions that occur during execution
        catch (Exception e){
            // Print the error message
            System.out.println(e.getMessage());

            // Return null to indicate failure
            return null;
        }
    }
    // Retrieves and return information for top 10 crities in the world.
    public ArrayList<City> getCitiesWorld10(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT CountryCode, Name, District, Population FROM city ORDER BY Population DESC LIMIT 10";
            // Retrieve cities based on the SQL query.
            ArrayList<City> cities =getCities(strSelect);
            // Report the cicities with custom identifier and display
            reportCities(cities,"Cities_in_World");
            // Return cities.
            return cities;
        }
        catch (Exception e){
            // Print the error message
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Retrieves and return information about cities in continent
    public ArrayList<City> getCitiesContinent(){
        return getCitiesContinent(true);
    }

    // Retrieves and return information about cities in a continent 
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
            // Report and display the cities
            if (isOutput){
                reportCities(cities,"Cities_in_Continent");
            }
             // Return the sorted list of cities
            return cities;
        }
        catch (Exception e){
            // Print the error message
            System.out.println(e.getMessage());
            // Return null to indicate failure
            return null;
        }
    }

    //Retrieves and return information for top 10 crities in a continent.
    public ArrayList<City> getCitiesContinent10(){
        try {
            // Create string for SQL statement
            ArrayList<City> cities = getCitiesContinent(false);
            // Initialize an ArrayList contain top 10 cities
            ArrayList<City> top10Cities = new ArrayList<>(cities.subList(0, Math.min(cities.size(), 10)));
            // Report top 10 cities
            reportCities(top10Cities,"Cities_Continent_10");
            return top10Cities;
        }
        catch (Exception e){
            // Print the error message
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Retrieves and returns information about cities in a specific region.
    public ArrayList<City> getCitiesRegion(){
        return getCitiesRegion(true);
    }

    // Retrieve and returns information about cities in a region based on the flag.
    public ArrayList<City> getCitiesRegion(boolean isOutput){
        try{
            // Create string for SQL statement for getting cities from Southeast Asia
            String query1 = "SELECT city.CountryCode, city.Name, city.District, city.Population FROM city JOIN country ON city.CountryCode = country.Code WHERE country.Region = 'Southeast Asia' ORDER BY city.Population DESC;";
            // Retrieve cities based on SQL query
            ArrayList<City> cities = getCities(query1);
           // Report and display cities in the region.
            if (isOutput){
                reportCities(cities,"Cities_in_Continent");
            }
            return cities;
        }
        catch (Exception e){
            // Print the error message
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Retrieves and returns information about the top 10 crities in a region
    public List<City> getCitiesRegion10(){
        try {
            // Create string for SQL statement
            ArrayList<City> cities = getCitiesRegion(false);

            // Create a new ArrayList containing the top 10 cities.
            ArrayList<City> top10Cities = new ArrayList<>(cities.subList(0, Math.min(cities.size(), 10)));
            // Report and display top 10 cities
            reportCities(top10Cities,"Cities_Region_10");
            return top10Cities;
        }
        catch (Exception e){
            // Print the error message 
            System.out.println(e.getMessage());
            return null;
        }
    }
    // Retrieves and return information about cities in country (Myanmar)
    // Method construts a SQL query to select cities from the country (Myanmar)
    // Order to result by population in descending order and retrieves the data.
    // If the operation is successful, it reports and disiplay cities.
    // If an exception occurs, it prints the error message.

    public ArrayList<City> getCitiesCountry(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT CountryCode, Name, District, Population FROM city WHERE CountryCode='MMR' ORDER BY Population DESC";
            // Retrieve cities based on the SQL query.
            ArrayList<City> cities = getCities(strSelect);
            reportCities(cities,"Cities_in_Country");
            return cities;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

// Retrieves and returns information about cities in a specific distrcit of Mandalay.
// Method construts a SQL query to select cities from the specified district.
// Order to result by population in descending order and retrieves the data.
// If the operation is successful, it reports and disiplay cities.
// If an exception occurs, it prints the error message.

    public ArrayList<City> getCitiesDistrict(){
        try{
            // Create string for SQL statement
            String strSelect;
            strSelect = "SELECT CountryCode, Name, District, Population FROM city WHERE District='Mandalay' ORDER BY Population DESC";
            // Retrieve cities based on the specified SQL query.
            ArrayList<City> cities = getCities(strSelect);
            reportCities(cities,"Cities_in_Region");
            return cities;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    // Retrieve capital cities world wide and optionally report the results

    public ArrayList<City> getCapitalCitiesWorld(boolean isOutput){
        String query = "SELECT city.Name, city.CountryCode, city.District, city.Population FROM city JOIN country ON city.ID = country.Capital ORDER BY city.Population DESC;";
        // Retrieve cities using the getCities method
        ArrayList<City> cities = getCities(query);
        if (isOutput){
            reportCities(cities,"Capital Cities World");
        }
        return cities;
    }

    // Retrieve capital cities by continent and optionally report the results
    public ArrayList<City> getCapitalCitiesContinent(boolean isOutput){
        // SQL query to select capital cities in Asia.
        String query = "SELECT city.Name, city.CountryCode, city.District, city.Population FROM city JOIN country ON city.ID = country.Capital WHERE country.Continent = 'Asia' ORDER BY city.Population DESC;";
        ArrayList<City> cities = getCities(query);
        if (isOutput){
            reportCities(cities,"Capital Cities Continent");
        }
        // Return the list cities.
        return cities;
    }
    // Retrieve capital cities by region and optionally report the results
    public ArrayList<City> getCapitalCitiesRegion(boolean isOutput){
        // SQL query to select capital cities in Southeast Asia.
        String query = "SELECT city.Name, city.CountryCode, city.District, city.Population FROM city JOIN country ON city.ID = country.Capital WHERE country.Region = 'Southeast Asia' ORDER BY city.Population DESC;";
        // Retrieve cities using the getCities method
        ArrayList<City> cities = getCities(query);
        if (isOutput){
            reportCities(cities,"Capital Cities in Region");
        }
        return cities;
    }
    // Retrieve the top 10 capital cities worldwide by population and optionally report the results.
    public ArrayList<City> getCapitalCitiesWorld10(boolean isOutput){
        String query = "SELECT city.Name, city.CountryCode, city.District, city.Population FROM city JOIN country ON city.ID = country.Capital ORDER BY city.Population DESC LIMIT 10;";
        ArrayList<City> cities = getCities(query);
        if (isOutput){
            reportCities(cities,"Capital Cities World");
        }
        return cities;
    }
    
    /** 
     * Retrieves the top 10 capital cities in Asia based on pupulation.
     *
     * @param isOutput Flag to determine whether to report the cities or not.
     * @return ArrayList of City objects representing the capital cities.
     */
    
    public ArrayList<City> getCapitalCitiesContinent10(boolean isOutput){
        String query = "SELECT city.Name, city.CountryCode, city.District, city.Population FROM city JOIN country ON city.ID = country.Capital WHERE country.Continent = 'Asia' ORDER BY city.Population DESC LIMIT 10;";
        // Retrieve the capital cities based on the provided query
        ArrayList<City> cities = getCities(query);
        if (isOutput){
            reportCities(cities,"Capital Cities Continent");
        }
        return cities;
    }
    // Retrieves the top 10 capital cities in the region
    public ArrayList<City> getCapitalCitiesRegion10(boolean isOutput){
        String query = "SELECT city.Name, city.CountryCode, city.District, city.Population FROM city JOIN country ON city.ID = country.Capital WHERE country.Region = 'Southeast Asia' ORDER BY city.Population DESC LIMIT 10;";
        // Retrieve the capital cities based on the provided query
        ArrayList<City> cities = getCities(query);
        if (isOutput){
            reportCities(cities,"Capital Cities in Region");
        }
        return cities;
    }

// Retrieves a list of City objects based on the provided SQL query
    public ArrayList<City> getCities(String strSelect)
    {
        try
        {
            // Create a statement and execute the provided SQL query
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            ArrayList<City> cities = new ArrayList<City>();
            // Iterate through the ResultSet and populate the City objects
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
            // Print error messages
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
    /**
    *Retrieves population statistics for each continent including total population, population in cities, and population not in cities.
    */
    public Resultset populationContinent(){
        try
        {
            // SQL query to retrieve population statistics for each continent including total population, population in cities, and population not in cities.
            String query = "SELECT country.Continent,SUM(country.Population) AS TotalPopulation, SUM(city.Population) AS PopulationInCities, SUM(country.Population) - SUM(city.Population) AS PopulationNotInCities FROM country LEFT JOIN city ON country.Code = city.CountryCode GROUP BY country.Continent;";
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-30s %-50s %-50s %-30s\n", "Continent",  "Population of People", "People Living In Cities", "People Not Living In Cities"));
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);
            
            // Loop through the ResultSet to build the result string
            while (rset.next())
            {
                // Handling null values for population in cities and population not in cities
                String pC = rset.getString("PopulationInCities");
                long pc =0;
                String pNC = rset.getString("PopulationNotInCities");
                long pnc = 0;

                if(pC==null || pNC == null){
                    pc = 0;
                    pnc = 0;
                }else {
                    pc = rset.getLong("PopulationInCities");
                    pnc = rset.getLong("PopulationNotInCities");
                }
                // Appending population statistics to the result string
                sb.append(String.format("%-30s %-50s %-50s %-30s\n",
                        rset.getString("Continent"), numberFormatter.format(rset.getLong("TotalPopulation")), numberFormatter.format(pc), numberFormatter.format(pnc)));
            }

            // Printing the result string
            System.out.println(sb.toString());
            return (Resultset) rset;
        }
        catch (Exception e)
        {
            // Handling exceptions
            System.out.println(e.getMessage());
            System.out.println("Failed to get Countries details");
            return  null;
        }
    }
    public Resultset populationRegion(){
        try
        {
            String query = "SELECT country.Region ,SUM(country.Population) AS TotalPopulation, SUM(city.Population) AS PopulationInCities, SUM(country.Population) - SUM(city.Population) AS PopulationNotInCities FROM country LEFT JOIN city ON country.Code = city.CountryCode GROUP BY country.Region;";
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-30s %-50s %-50s %-30s\n", "Region",  "Population of People", "People Living In Cities", "People Not Living In Cities"));
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            while (rset.next())
            {
                String pC = rset.getString("PopulationInCities");
                long pc =0;
                String pNC = rset.getString("PopulationNotInCities");
                long pnc = 0;

                if(pC==null || pNC == null){
                    pc = 0;
                    pnc = 0;
                }else {
                    pc = rset.getLong("PopulationInCities");
                    pnc = rset.getLong("PopulationNotInCities");
                }
                sb.append(String.format("%-30s %-50s %-50s %-30s\n",
                        rset.getString("Region"), numberFormatter.format(rset.getLong("TotalPopulation")), numberFormatter.format(pc), numberFormatter.format(pnc)));
            }
            System.out.println(sb.toString());
            return (Resultset) rset;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Countries details");
            return null;
        }
    }

    /**
     * Retrieves population statistics for each region including total population, population in cities, and population not in cities.
     */
    public String populationCountry(){
        try
        {
            String query = "SELECT country.Name ,SUM(country.Population) AS TotalPopulation, SUM(city.Population) AS PopulationInCities, SUM(country.Population) - SUM(city.Population) AS PopulationNotInCities FROM country LEFT JOIN city ON country.Code = city.CountryCode GROUP BY country.Name;";
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-30s %-50s %-50s %-30s\n", "Country",  "Population of People", "People Living In Cities", "People Not Living In Cities"));
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            while (rset.next())
            {
                String pC = rset.getString("PopulationInCities");
                long pc =0;
                String pNC = rset.getString("PopulationNotInCities");
                long pnc = 0;

                if(pC==null || pNC == null){
                    pc = 0;
                    pnc = 0;
                }else {
                    pc = rset.getLong("PopulationInCities");
                    pnc = rset.getLong("PopulationNotInCities");
                }
                sb.append(String.format("%-30s %-50s %-50s %-30s\n",
                        rset.getString("Name"), numberFormatter.format(rset.getLong("TotalPopulation")), numberFormatter.format(pc), numberFormatter.format(pnc)));
            }
            System.out.println(sb.toString());
            return sb.toString();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Countries details");
            return null;

        }
    }

    /**
     * Retrieves the total world population.
     */
    public long WorldPopulation() {
        try {
            String query = "SELECT SUM(Population) AS WorldPopulation FROM country;";
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            if (rset.next()) {
                long result = rset.getLong("WorldPopulation");
                System.out.println("World Population: "+numberFormatter.format(result));
                return rset.getLong("WorldPopulation");

            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving world population: " + e.getMessage());
            return 0;
        }
    }
    /**
     * Continnent population returning the long number format
     * @return
     */
    public long ContinentPopulation() {
        try {
            String query = "SELECT SUM(Population) AS ContinentPopulation FROM country WHERE Continent = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, "Asia");
            ResultSet rset = pstmt.executeQuery();
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            if (rset.next()) {
                System.out.println("Continent Population: "+numberFormatter.format(rset.getLong("ContinentPopulation")));
                return rset.getLong("ContinentPopulation");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving continent population: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Retrieves the population of a specific region (e.g., Southeast Asia).
     */
    public long RegionPopulation() {
        try {
            String query = "SELECT SUM(Population) AS RegionPopulation FROM country WHERE Region = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, "Southeast Asia");
            ResultSet rset = pstmt.executeQuery();
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            if (rset.next()) {
                long result = rset.getLong("RegionPopulation");
                System.out.println("Region Population: "+numberFormatter.format(result));
                return result;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving region population: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Retrieves the population of a specific country (e.g., Myanmar).
     */
    public long CountryPopulation() {
        try {
            String query = "SELECT Population FROM country WHERE Code = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, "MMR");
            ResultSet rset = pstmt.executeQuery();
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            if (rset.next()) {
                System.out.println("Country Population: "+numberFormatter.format(rset.getLong("Population")));
                return rset.getLong("Population");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving country population: " + e.getMessage());
            return 0;
        }
    }
    /**
     * @return Returning District Population
     */
    public long DistrictPopulation() {
        try {
            String query = "SELECT SUM(Population) AS DistrictPopulation FROM city WHERE District = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, "Mandalay");
            ResultSet rset = pstmt.executeQuery();
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            if (rset.next()) {
                System.out.println("District Population: "+numberFormatter.format(rset.getLong("DistrictPopulation")));
                return rset.getLong("DistrictPopulation");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving district population: " + e.getMessage());
            return 0;
        }
    }

    /**
     * @return Returning CitiesPopulation
     */
    public long CityPopulation() {
        try {
            String query = "SELECT Population FROM city WHERE Name = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, "Mandalay");
            ResultSet rset = pstmt.executeQuery();
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);

            if (rset.next()) {
                System.out.println("City Population: "+numberFormatter.format(rset.getLong("Population")));
                return rset.getLong("Population");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving city population: " + e.getMessage());
            return 0;
        }
    }

    /**
     * @return Returning Languages
     */
    public Resultset language(){

        String query = "SELECT cl.Language, SUM(cl.Percentage / 100 * c.Population) AS TotalSpeakers, (SUM(cl.Percentage / 100 * c.Population) / (SELECT SUM(Population) FROM country)) * 100 AS PercentageOfWorldPopulation FROM countrylanguage cl JOIN country c ON cl.CountryCode = c.Code WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') GROUP BY cl.Language ORDER BY TotalSpeakers DESC;";
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);
            while (rset.next()){
                System.out.println(numberFormatter.format(rset.getLong("TotalSpeakers"))+" people is used "+rset.getString("Language")+" and it is "+rset.getLong("PercentageOfWorldPopulation")+"% of World Population");
            }
            return (Resultset) rset;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return  null;
        }
        // Create string for SQL statement

    }
    public static void main(String[] args) {
        App a = new App();
        if(args.length < 1){
            a.connect("localhost:33060", 100);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }
//        System.out.println("-----Countries in the Whole World-----");
//        a.getCountriesWorld();
//        System.out.println("-----Top 10 Most Populated Countries in the Whole World-----");
//        a.getCountriesWorld10();
//        System.out.println("-----Countries in the Asia Continent-----");
//        a.getCountriesContinent();
//        System.out.println("-----Top 10 Most Populated Countries in Asia Continent-----");
//        a.getCountriesContinent10();
//        System.out.println("-----Countries in the Southeast Asia Region-----");
//        a.getCountriesRegion(true);
//        System.out.println("-----Top 10 Most Populated Countries in Southeast Asia Region-----");
//        a.getCountriesRegion10();
//        System.out.println("----- Cities in the whole world-----");
//        a.getCitiesWorld();
//        System.out.println("----- Cities in Asia Continent-----");
//        a.getCitiesContinent(true);
//        System.out.println("----- Cities in Southeast Asia Region-----");
//        a.getCitiesRegion(true);
//        System.out.println("----- Cities in Myanmar-----");
//        a.getCitiesCountry();
//        System.out.println("----- Cities in Mandalay District-----");
//        a.getCitiesDistrict();
//        System.out.println("-----Capital Cities in the World-----");
//
//        a.getCapitalCitiesWorld(true);
//        System.out.println("-----Capital Cities in the Asia-----");
//        a.getCapitalCitiesContinent(true);
//        System.out.println("-----Capital Cities in the Southeast Asia-----");
//        a.getCapitalCitiesRegion(true);
//        System.out.println("-----Top 10 Capital Cities in the World-----");
//        a.getCapitalCitiesWorld10(true);
//        System.out.println("-----Top 10 Capital Cities in the Asia-----");
//        a.getCapitalCitiesContinent10(true);
//        System.out.println("-----Top 10 Capital Cities in the Southeast Asia-----");
//        a.getCapitalCitiesRegion10(true);
//        a.populationContinent();
//        a.populationRegion();
//        a.populationCountry();

//        a.WorldPopulation();
//        a.ContinentPopulation();
//        a.RegionPopulation();
//        a.DistrictPopulation();
//        a.CountryPopulation();
//        a.CityPopulation();
//
//
//        a.language();
        a.disconnect();
    }
}

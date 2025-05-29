package com.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Singleton class for managing the database connection.
 * It reads credentials from a configuration file and
 * provides a shared connection to the application.
 */
public class Database 
{
    // Base URL and name of the MySQL database
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "proyecto_final_fct";

    // Singleton instance of the Database class
    private static Database singleInstance = null;

    // Connection object used to interact with the database
    private Connection connection;

    // Username and password for database access
    private static String USER = "";
    private static String PASSWORD = "";

    /**
     * Private constructor to enforce Singleton pattern.
     * It initializes the database connection.
     *
     * @throws SQLException if connection fails or is closed
     */
    private Database() throws SQLException 
    {
        LoadCredentials(); // Load USER and PASSWORD from the file

        try 
        {
            connection = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);

            if (connection.isClosed())
                throw new SQLException("Connection error: The connection is closed");

        } catch (SQLException ex) 
        {
            throw new SQLException("Error connecting to the database: " + ex.getMessage());
        }
    }

    /**
     * Loads database credentials from a local configuration file.
     * Expected format: key=value with keys USER and PASS.
     */
    private void LoadCredentials() 
    {
        try 
        {
            File file = new File("C:\\Users\\meker\\Desktop\\Proyecto_FCT_Adrian_Martinez_Melero\\Java Proyect\\proyecto_interomodular\\conf.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("=");

                if (parts.length == 2) 
                {
                    String key = parts[0].trim();
                    String value = parts[1].trim().replace("(", "").replace(")", "");

                    if (key.equalsIgnoreCase("USER")) 
                        USER = value;
                    
                    else if (key.equalsIgnoreCase("PASS")) 
                        PASSWORD = value;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) 
        {
            System.out.println("Error: Configuration file not found.");
        }
    }

    /**
     * Returns the single instance of the Database class.
     * Creates the instance if it doesn't already exist.
     *
     * @return Database instance
     * @throws SQLException if connection cannot be established
     */
    public static Database getInstance() throws SQLException 
    {
        if (singleInstance == null)
            singleInstance = new Database();

        return singleInstance;
    }

    /**
     * Provides access to the underlying SQL connection.
     *
     * @return SQL Connection object
     */
    public Connection getConnection() 
    {
        return connection;
    }

    /**
     * Closes the SQL connection if it is open.
     *
     * @throws SQLException if closing the connection fails
     */
    public void CloseConnection() throws SQLException 
    {
        if (connection != null && !connection.isClosed())
            connection.close();
    }
}

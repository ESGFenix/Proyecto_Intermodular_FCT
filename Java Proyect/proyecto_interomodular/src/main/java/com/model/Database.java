package com.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Database 
{
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "proyecto_final_fct";

    private static Database singleInstance = null;
    private Connection connection;

    private static String USER = "";
    private static String PASSWORD = "";

    private Database() throws SQLException 
    {
        loadCredentials(); // Load USER and PASSWORD from the file

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

    private void loadCredentials() 
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

    public static Database getInstance() throws SQLException 
    {
        if (singleInstance == null)
            singleInstance = new Database();

        return singleInstance;
    }

    public Connection getConnection() 
    {
        return connection;
    }

    public void closeConnection() throws SQLException 
    {
        if (connection != null && !connection.isClosed())
            connection.close();
    }
}

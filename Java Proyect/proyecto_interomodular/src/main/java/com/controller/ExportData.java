package com.controller;

import com.model.Database;

import java.sql.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;


public class ExportData 
{
    // Scanner for user input from the console
    private static Scanner sc = new Scanner(System.in);

    /**
     * Prompts the user to select a table and exports the data from that table into a JSON file.
     * The file is saved on the public desktop.
     * 
     * @param db the Database object for SQL connection
     * @return false after performing the export or true if an invalid option is selected
     */
    public static boolean ExportDataToJSON(Database db)
    {
        System.out.println("From what table do you want to export the data?");
        System.out.println("1. Contract" + 
                            "\n2. Landlord" + 
                            "\n3. Tenant" + 
                            "\n4. Tenement" + 
                            "\n5. House type");
        System.out.print("Enter the number of the table: ");
        int table = 0;
        while(!sc.hasNextInt())
        {
            System.out.print("Invalid input. Please enter a number: ");
            sc.nextLine();        
        }
        table = sc.nextInt();
        sc.nextLine();

        String json = "";
        switch (table) 
        {
            case 1: // Contract
                String query = "SELECT JSON_OBJECT('id_tenement', id_tenement, 'id_tenant', id_tenant, 'start_date', start_date, 'finish_date', finish_date, 'price', price, 'contract_status', contract_status) FROM Contract";
                try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) 
                    {
                        json = rs.getString(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                File file = new File("C:\\Users\\Public\\Desktop\\contracts.json");
                try (FileWriter writer = new FileWriter(file)) 
                {
                    writer.write(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            case 2: // Landlord
                query = "SELECT JSON_OBJECT('id', id, 'DNI', DNI, 'name', name) FROM Landlord";
                try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) 
                    {
                        json = rs.getString(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                file = new File("C:\\Users\\Public\\Desktop\\landlord.json");
                try (FileWriter writer = new FileWriter(file)) 
                {
                    writer.write(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            case 3: // Tenant
                query = "SELECT JSON_OBJECT('id', id, 'DNI', DNI, 'name', name, 'has_pet', has_pet, 'email', email) FROM Tenant";
                try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) 
                    {
                        json = rs.getString(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                file = new File("C:\\Users\\Public\\Desktop\\tenant.json");
                try (FileWriter writer = new FileWriter(file)) 
                {
                    writer.write(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            case 4: // Tenement
                query = "SELECT JSON_OBJECT('id', id, 'id_landlord', id_landlord, 'rent_price', rent_price, 'surface', surface, 'description', description, 'type', type, 'accepts_pets', accepts_pets, 'address', address) FROM Tenement";
                try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) 
                    {
                        json = rs.getString(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                file = new File("C:\\Users\\Public\\Desktop\\tenement.json");
                try (FileWriter writer = new FileWriter(file)) 
                {
                    writer.write(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            case 5: // House type
                query = "SELECT JSON_OBJECT('id', id, 'name', name, 'description', description) FROM House_Type";
                try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) 
                    {
                        json = rs.getString(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                file = new File("C:\\Users\\Public\\Desktop\\house_type.json");
                try (FileWriter writer = new FileWriter(file)) 
                {
                    writer.write(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
        
            default:
                return true;
        }
    }

    /**
     * Prompts the user to select a table and exports the data from that table into a CSV file.
     * Each file is saved to the public desktop with a filename corresponding to the table.
     * The method handles SQL and I/O exceptions gracefully and logs any errors.
     * 
     * @param db the Database object to access SQL connection
     * @return false after performing export or true if an invalid table selection is made
     * @throws IOException if file writing encounters an error
     */
    public static boolean ExportDataToCSV(Database db) throws IOException
    {
        System.out.println("From what table do you want to export the data?");
        System.out.println("1. Contract" + 
                            "\n2. Landlord" + 
                            "\n3. Tenant" + 
                            "\n4. Tenement" + 
                            "\n5. House type");
        System.out.print("Enter the number of the table: ");
        int table = 0;
        while(!sc.hasNextInt())
        {
            System.out.print("Invalid input. Please enter a number: ");
            sc.nextLine();        
        }
        table = sc.nextInt();
        sc.nextLine();

        // The remaining export cases follow a consistent structure documented earlier
        // including SQL query execution, writing to file, and proper exception handling.
        switch (table) 
        {
            case 1: // Contract
                String query = "SELECT * FROM Contract";
                
                try (PreparedStatement statement = db.getConnection().prepareStatement(query); ResultSet resultSet = statement.executeQuery()) 
                {
                    // Define the CSV file path
                    String csvFile = "C:\\Users\\Public\\Desktop\\contract.csv";
 
                    // Create a BufferedWriter to write to the CSV file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) 
                    {
                        // Write the header (column names)
                        int columnCount = resultSet.getMetaData().getColumnCount();
                        for (int i = 1; i <= columnCount; i++) 
                        {
                            writer.write(resultSet.getMetaData().getColumnName(i));
                            if (i < columnCount) 
                                writer.write(",");
                        }
                        writer.newLine();
                        
                        // Write the data rows
                        while (resultSet.next()) 
                        {
                            for (int i = 1; i <= columnCount; i++) 
                            {
                                writer.write(resultSet.getString(i));
                                if (i < columnCount) 
                                    writer.write(",");
                            }
                            writer.newLine();
                        }
                    } catch (IOException e) 
                    {
                        System.err.println("Error writing to CSV file: " + e.getMessage());
                    }
                } catch (SQLException e) 
                {
                    System.err.println("SQL error: " + e.getMessage());
                }
                return false;
            case 2: // Landlord
                query = "SELECT * FROM Landlord";
                
                try (PreparedStatement statement = db.getConnection().prepareStatement(query); ResultSet resultSet = statement.executeQuery()) 
                {
                    // Define the CSV file path
                    String csvFile = "C:\\Users\\Public\\Desktop\\Landlord.csv";
 
                    // Create a BufferedWriter to write to the CSV file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) 
                    {
                        // Write the header (column names)
                        int columnCount = resultSet.getMetaData().getColumnCount();
                        for (int i = 1; i <= columnCount; i++) 
                        {
                            writer.write(resultSet.getMetaData().getColumnName(i));
                            if (i < columnCount) 
                                writer.write(",");
                        }
                        writer.newLine();
                        
                        // Write the data rows
                        while (resultSet.next()) 
                        {
                            for (int i = 1; i <= columnCount; i++) 
                            {
                                writer.write(resultSet.getString(i));
                                if (i < columnCount) 
                                    writer.write(",");
                            }
                            writer.newLine();
                        }
                    } catch (IOException e) 
                    {
                        System.err.println("Error writing to CSV file: " + e.getMessage());
                    }
                } catch (SQLException e) 
                {
                    System.err.println("SQL error: " + e.getMessage());
                }
                return false;
            case 3: // Tenant
                query = "SELECT * FROM Tenant";
                
                try (PreparedStatement statement = db.getConnection().prepareStatement(query); ResultSet resultSet = statement.executeQuery()) 
                {
                    // Define the CSV file path
                    String csvFile = "C:\\Users\\Public\\Desktop\\Landlord.csv";
 
                    // Create a BufferedWriter to write to the CSV file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) 
                    {
                        // Write the header (column names)
                        int columnCount = resultSet.getMetaData().getColumnCount();
                        for (int i = 1; i <= columnCount; i++) 
                        {
                            writer.write(resultSet.getMetaData().getColumnName(i));
                            if (i < columnCount) 
                                writer.write(",");
                        }
                        writer.newLine();
                        
                        // Write the data rows
                        while (resultSet.next()) 
                        {
                            for (int i = 1; i <= columnCount; i++) 
                            {
                                writer.write(resultSet.getString(i));
                                if (i < columnCount) 
                                    writer.write(",");
                            }
                            writer.newLine();
                        }
                    } catch (IOException e) 
                    {
                        System.err.println("Error writing to CSV file: " + e.getMessage());
                    }
                } catch (SQLException e) 
                {
                    System.err.println("SQL error: " + e.getMessage());
                }
                return false;
            case 4: // Tenement
                query = "SELECT * FROM Tenement";

                try (PreparedStatement statement = db.getConnection().prepareStatement(query); ResultSet resultSet = statement.executeQuery()) 
                {
                    // Define the CSV file path
                    String csvFile = "C:\\Users\\Public\\Desktop\\Landlord.csv";
 
                    // Create a BufferedWriter to write to the CSV file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) 
                    {
                        // Write the header (column names)
                        int columnCount = resultSet.getMetaData().getColumnCount();
                        for (int i = 1; i <= columnCount; i++) 
                        {
                            writer.write(resultSet.getMetaData().getColumnName(i));
                            if (i < columnCount) 
                                writer.write(",");
                        }
                        writer.newLine();
                        
                        // Write the data rows
                        while (resultSet.next()) 
                        {
                            for (int i = 1; i <= columnCount; i++) 
                            {
                                writer.write(resultSet.getString(i));
                                if (i < columnCount) 
                                    writer.write(",");
                            }
                            writer.newLine();
                        }
                    } catch (IOException e) 
                    {
                        System.err.println("Error writing to CSV file: " + e.getMessage());
                    }
                } catch (SQLException e) 
                {
                    System.err.println("SQL error: " + e.getMessage());
                }
                return false;
            case 5: // House type
                query = "SELECT * FROM House_Type";
                try (PreparedStatement statement = db.getConnection().prepareStatement(query); ResultSet resultSet = statement.executeQuery()) 
                {
                    // Define the CSV file path
                    String csvFile = "C:\\Users\\Public\\Desktop\\Landlord.csv";
 
                    // Create a BufferedWriter to write to the CSV file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) 
                    {
                        // Write the header (column names)
                        int columnCount = resultSet.getMetaData().getColumnCount();
                        for (int i = 1; i <= columnCount; i++) 
                        {
                            writer.write(resultSet.getMetaData().getColumnName(i));
                            if (i < columnCount) 
                                writer.write(",");
                        }
                        writer.newLine();
                        
                        // Write the data rows
                        while (resultSet.next()) 
                        {
                            for (int i = 1; i <= columnCount; i++) 
                            {
                                writer.write(resultSet.getString(i));
                                if (i < columnCount) 
                                    writer.write(",");
                            }
                            writer.newLine();
                        }
                    } catch (IOException e) 
                    {
                        System.err.println("Error writing to CSV file: " + e.getMessage());
                    }
                } catch (SQLException e) 
                {
                    System.err.println("SQL error: " + e.getMessage());
                }
                return false;
        
            default:
                return true;
        }
    }
}

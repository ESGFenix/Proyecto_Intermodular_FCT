package com.view;

import com.model.Database;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Main class serves as the entry point for the Alquilaria renting software application.
 * <p>
 * Initializes database connection, enters main menu loop, and closes connection on exit.
 * </p>
 */
public class Main 
{

    /**
     * Application entry point. Establishes a singleton database connection,
     * repeatedly displays the main menu until user exits, then closes connection.
     *
     * @param args command-line arguments (unused)
     * @throws SQLException if database connection initialization or closing fails
     */
    public static void main(String[] args) throws SQLException, IOException
    {
        // Obtain singleton Database instance
        Database db = Database.getInstance();

        // Loop until showMenu returns false (user selects exit)
        while (Menu.showMenu(db));

        // Close database connection before exiting
        db.CloseConnection();
    }
}

package com.view;

import com.model.Database;
import java.sql.SQLException;

public class Main 
{
    
    public static void main(String[] args) throws SQLException
    {
        Database db = Database.getInstance();

        while(Menu.showMenu(db));

        db.CloseConnection();
    }
}
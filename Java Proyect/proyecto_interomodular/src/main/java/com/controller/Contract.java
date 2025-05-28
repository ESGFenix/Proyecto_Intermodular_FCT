package com.controller;

import com.model.Database;
import java.sql.*;
import java.sql.Date;

public class Contract 
{
    public static void InsertContract(String id_tenement, int id_tenant, Date start_date, Date finish_date, float price, int contract_status, Database db)
    {
        String contractStatusString;
        if (contract_status == 1)
            contractStatusString = "PENDING";
        else if (contract_status == 2)
            contractStatusString = "ACTIVE";
        else
            contractStatusString = "EXPIRED";

        String sql = "INSERT INTO Contract (id_tenement, id_tenant, start_date, finish_date, price, contract_status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, id_tenement);
            pstmt.setInt(2, id_tenant);
            pstmt.setDate(3, start_date);
            pstmt.setDate(4, finish_date);
            pstmt.setFloat(5, price);
            pstmt.setString(6, contractStatusString);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    } 

    public static void DeleteContract(String id_tenement, int id_tenant, Date start_date, Database db)
    {
        String sql = "DELETE FROM Contract WHERE id_tenement = ? AND id_tenant = ? AND start_date = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, id_tenement);
            pstmt.setInt(2, id_tenant);
            pstmt.setDate(3, start_date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ModifyContract(String id_tenement, int id_tenant, Date start_date, Date finish_date, float price, int contract_status, Database db)
    {
        String contractStatusString;
        if (contract_status == 1)
            contractStatusString = "PENDING";
        else if (contract_status == 2)
            contractStatusString = "ACTIVE";
        else
            contractStatusString = "EXPIRED";

        String sql = "UPDATE Contract SET finish_date = ?, price = ?, contract_status = ? WHERE id_tenement = ? AND id_tenant = ? AND start_date = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setDate(1, finish_date);
            pstmt.setFloat(2, price);
            pstmt.setString(3, contractStatusString);
            pstmt.setString(4, id_tenement);
            pstmt.setInt(5, id_tenant);
            pstmt.setDate(6, start_date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void SelectContract(String id_tenement, int id_tenant, Date start_date, Database db)
    {
        String sql = "SELECT * FROM Contract WHERE id_tenement = ? AND id_tenant = ? AND start_date = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, id_tenement);
            pstmt.setInt(2, id_tenant);
            pstmt.setDate(3, start_date);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                System.out.println("---------------------------------------------------------------" +
                                    "\n\tTenement's ID: " + rs.getString("id_tenement") + 
                                    "\n\tTenant's ID: " + rs.getInt("id_tenant") + 
                                    "\n\tContract's start day: " + rs.getDate("start_date") + 
                                    "\n\tContract's finish day: " + rs.getDate("finish_date") + 
                                    "\n\tContract's status: "  + rs.getString("contract_status") +
                                    "\n------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ContractStateChange(String id_tenement, int id_tenant, Date start_date, int contract_status, Database db)
    {
        String contractStatusString;
        if (contract_status == 1)
            contractStatusString = "PENDING";
        else if (contract_status == 2)
            contractStatusString = "ACTIVE";
        else
            contractStatusString = "EXPIRED";

        String sql = "UPDATE Contract SET contract_status = ? WHERE id_tenement = ? AND id_tenant = ? AND start_date = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, contractStatusString);
            pstmt.setString(2, id_tenement);
            pstmt.setInt(3, id_tenant);
            pstmt.setDate(4, start_date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

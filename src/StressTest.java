
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */
public class StressTest
{
    String dbUrl = "jdbc:mysql://group9p3.cloudapp.net:3306/personsdatabase";
    String user = "Andy";
    String pass = "4ibss6al";
    Connection conn = null;
    Statement stmt = null;
    CallableStatement cstmt = null;
    ResultSet rs = null;
    Timer timer = new Timer();
    
    public StressTest()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(dbUrl, user, pass);
            System.out.println("Connected database successfully...");
            DatabaseMetaData md = conn.getMetaData();
            // ListTables
            System.out.println("\nTABLES:");
            rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    
    public void addStations()
    {
        long endTime;
        long startTime = System.nanoTime();
        for (int x = 0;x<500;x++)
        {
            addStation(String.valueOf((int)(x)));
        }
        endTime = System.nanoTime();
        System.out.println("Time elapsed: " + (endTime-startTime)/1000000000.0);
    }
    
    public void addThreadStations()
    {
        for (int x =0;x<50;x++)
        {
            (new stressTestThread()).start();
        }
    }
    
    public void removeStations()
    {
        String query = "DELETE FROM `personsdatabase`.`warning` WHERE `warningstatus`='1';";
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("done");
    }
    
    public void addStation(String stationID)
    {
        String query = "INSERT INTO `personsdatabase`.`warning` (`station`, `warningstatus`) VALUES ('"+stationID+"', '1');";
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String args[])
    {
        StressTest tester = new StressTest();
        tester.removeStations();
        //tester.addStations();
        //tester.addThreadStations();
        //tester.removeStations();
    }
}

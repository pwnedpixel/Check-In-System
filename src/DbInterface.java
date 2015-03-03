
import java.sql.*;
import java.time.*;
import java.util.LinkedList;

public class DbInterface
{

    String dbUrl = "jdbc:mysql://group9p3.cloudapp.net:3306/personsdatabase";
    //theslave
    //String dbUrl = "jdbc:mysql://localhost:3306/personsdatabase";
    //String user = "";
    //String pass = "";
    String user = "Andy";
    String pass = "4ibss6al";
    Connection conn = null;
    Statement stmt = null;
    CallableStatement cstmt = null;
    ResultSet rs = null;

    /**
     * Constructor. Loads the JDBC drivers and establishes a connection with the
     * database.
     */
    public DbInterface()
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

    public DbInterface(Connection newConn)
    {
        conn = newConn;
    }

    /**
     * Safely closes the connection to the database
     */
    public void closeConnection()
    {
        // finally block used to close resources
        try {
            if (stmt != null) {
                conn.close();
            }
        } catch (SQLException se) {
        }// do nothing
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        System.err.println("Connection Closed");
    }
    
    public LinkedList getStations()
    {
        LinkedList stationNumbers = new LinkedList();
        String query = "SELECT * FROM personsdatabase.warning";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getInt("warningstatus")==1)
                {
                    stationNumbers.add(rs.getString("station"));
                }
            }
            System.out.println("\n");
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
        return stationNumbers;
    }

    /**
     * Adds a Person with given parameters (First name and Last name)to the
     * database.
     *
     * @param firstName The persons first name
     * @param lastName the persons last name
     */
    public void addPerson(String firstName, String lastName, String bDay, String address)
    {
        LocalDateTime dateAdded = LocalDateTime.now();
        String query = "INSERT INTO `personsdatabase`.`personstable` (`FirstName`, `LastName`, `DateAdded`, `DOB`, `Address`) VALUES ('" + firstName + "', '" + lastName + "', '" + dateAdded + "', '" + bDay + "', '" + address + "');";
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
            resetIDCount();
            System.out.println(firstName + " " + lastName + " added to list");
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

    /**
     * Removes a person based on the ID entered
     *
     * @param ID The id associated with the person
     */
    public void removePerson(String ID)
    {
        String query = "DELETE FROM `personsdatabase`.`personstable` WHERE `personstable`.`ID` = '" + ID + "'";
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
            resetIDCount();
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

    /**
     * Prints out the database of people into the console.
     */
    public void printTable()
    {
        String query = "SELECT * FROM personsdatabase.personstable;";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int IDnumber = rs.getInt("ID");
                String FirstName = rs.getString("FirstName");
                String LastName = rs.getString("LastName");
                System.out.println(IDnumber + " " + FirstName + " " + LastName);
            }
            System.out.println("\n");
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
    
    public void addStation(String stationID)
    {
        String query = "INSERT INTO `personsdatabase`.`warning` (`station`, `warningstatus`) VALUES ('"+stationID+"', '0');";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
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
    

    /**
     * Gets an array of strings containing information on each person in the
     * database, each string contains the id, first name as well as last name.
     *
     * @param column The column to which the sort is applied
     * @param order The way the list is sorted (Ascending or descending)
     * @return returns a list of people sorted by the given parameters.
     */
    public String[] getList(String column, String order)
    {
        /*Order can be ASC for accending or DESC for decending */
        String[] listOfPeople = new String[10];
        String query;
        if (column.equals("") && order.equals("")) {
            query = "SELECT * FROM personsdatabase.personstable;";
        } else {
            query = "SELECT * FROM `personstable` ORDER BY `" + column + "` " + order;
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            int x = 0;
            while (rs.next()) {
                String IDnumber = rs.getString("ID");
                String address = rs.getString("Address");
                String FirstName = rs.getString("FirstName");
                String LastName = rs.getString("LastName");
                String bDay = rs.getString("DOB");
                listOfPeople[x] = (IDnumber + " " + FirstName + " " + LastName + " " + bDay + " " + address);
                x++;
                if (x > listOfPeople.length - 1) {
                    listOfPeople = expandArray(listOfPeople);
                }
            }
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
        return listOfPeople;
    }

    /**
     * returns an array of strings containing information on each person
     *
     * @return array of peoples information, one string per person.
     */
    public String[] getList()
    {
        String[] listOfPeople;
        listOfPeople = getList("", "");
        return listOfPeople;
    }

    /**
     * Gets a property of a certain user based on their ID number
     *
     * @param ID The persons Id number
     * @param property The column that you want to get
     * @return returns the property demanded
     */
    public String getFromID(String ID, String property)
    {
        String toReturn = "";
        String query = "SELECT * FROM personsdatabase.personstable WHERE `personstable`.`ID` = '" + ID + "';";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                toReturn = rs.getString(property);
            }
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

        return toReturn;
    }

    public Connection getConn()
    {
        return conn;
    }

    public LinkedList searchDB(String firstName, String lastName, String bDay, String address)
    {
        LinkedList searchResults = new LinkedList();
        boolean queryOkay = false;
        String query = "SELECT * FROM personsdatabase.personstable WHERE ";
        if (!firstName.equals("")) {
            query = query + "FirstName = '" + firstName + "'";
            queryOkay = true;
        }
        if (!lastName.equals("")) {
            if (queryOkay) {
                query = query + " AND ";
            }
            query = query + "LastName = '" + lastName + "'";
            queryOkay = true;
        }
        if (!bDay.equals("")) {
            if (queryOkay) {
                query = query + " AND ";
            }
            query = query + "DOB = '" + bDay + "'";
            queryOkay = true;
        }
        if (!address.equals("")) {
            if (queryOkay) {
                query = query + " AND ";
            }
            query = query + "Address = '" + address + "'";
            queryOkay = true;
        }
        if (queryOkay) {
            query = query + ";";
        }
        if (!queryOkay) {
            return searchResults;
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String IDnumber = rs.getString("ID");
                String newAddress = rs.getString("Address");
                String FirstName = rs.getString("FirstName");
                String LastName = rs.getString("LastName");
                String newBDay = rs.getString("DOB");
                searchResults.add(IDnumber + " " + FirstName + " " + LastName + " " + newBDay + " " + newAddress);
            }
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
        return searchResults;
    }

    /**
     * Re assigns an ID to each person starting at 1 in the order they were
     * entered into the database.
     */
    public void resetIDCount() //currently not used
    {
        /*
         try
         {
         String query = "SET @count = 0;";
         stmt = conn.createStatement();
         stmt.execute(query);
         query = "UPDATE `personstable` SET `personstable`.`ID` = @count:= @count + 1;";
         stmt.execute(query);
         } catch (SQLException e)
         {
         System.err.println(e);
         } finally
         {
         if (stmt != null)
         {
         try
         {
         stmt.close();
         } catch (SQLException e)
         {
         e.printStackTrace();
         }
         }
         }
         */
    }

    /**
     * Gets the requested title in the requested language from database
     *
     * @param fieldID The name of the field for which you need the new title
     * @param language The language in which you would like the title
     * @return Returns the new title in the requested language.
     */
    public String getTitle(String fieldID, String language)
    {
        String title = "";
        try {
            String query = "SELECT * FROM personsdatabase.languages WHERE fieldID = '" + fieldID + "';";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                title = rs.getString(language);

            }
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
        return title;
    }

    /**
     * Takes and array that is not large enough and returns a new array that is
     * larger
     *
     * @param inputArray the smaller array
     * @return returns a larger array that still contains the contents of the
     * original array
     */
    private String[] expandArray(String[] inputArray)
    {
        String[] newArray = new String[inputArray.length + 1];
        for (int x = 0; x < inputArray.length; x++) {
            newArray[x] = inputArray[x];
        }
        return newArray;
    }
}

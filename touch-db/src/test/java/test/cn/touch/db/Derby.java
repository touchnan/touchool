package test.cn.touch.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

/**
 * The Embedded mode is limited by that we can't run simultaneously two programs (two JVM instances) using a same
 * database (databaseName is the same).
 * <p>
 * But we can instead use the NetworkServer mode to avoid this case, it is to say the "Client/Server" mode. In this
 * mode, you have to first start the NetworkServer by this command :
 * 
 * <pre>
 * java org.apache.derby.drda.NetworkServerControl start [-h hostIP -p portNumber]
 * </pre>
 * 
 * Or use the API :
 * 
 * <pre>
 * NetworkServerControl serverControl = new NetworkServerControl(InetAddress.getByName(&quot;myhost&quot;), 1621);
 * </pre>
 * 
 * <pre>
 * serverControl.shutdown();
 * </pre>
 * 
 * schema is above the table/view. In MySQL schema is equivalent to database. So in MySQL, create database==create
 * schema, but create database is not applicable to Java Derby.
 * <p>
 * In Derby, schema is also equivalent to a user name.
 * 
 * @author HAN
 * 
 */
public class Derby {
    private static Connection con = null;
    private static String port = null;
    private static String ip = null;

    /**
     * The port will be set to default: 1527
     */
    public static void setPortToDefault() {
        port = "1527";
    }

    public static void setPort(String port) {
        Derby.port = port;
    }

    public static void setServer(String ip) {
        Derby.ip = ip;
    }

    /**
     * This express loading driver is not necessary for Java 6 and later, JDBC 4.0 and later. Because it can be added
     * automatically by <code>DriverManager</code> when connecting to a database.
     */
    public static void loadDriver() {
        // load the driver
        if (port == null) {
            if (ip != null) {
                System.out
                        .println("You seem to have set an ip address, if so, you have also to assign a port, or else an embedded database will be automatically used");
            }
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                System.out.println("The embedded driver is successfully loaded");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                System.out.println("The client driver is successfully loaded");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * create and connect a database
     * 
     * @param databaseName
     * @param user
     * @param password
     * @return a connection to the URL
     * @throws SQLException
     */
    public static Connection createDatabaseAndGetConnection(String databaseName, String user, String password)
            throws SQLException {
        // create and connect the database
        Properties props = new Properties();
        props.put("user", user);
        props.put("password", password);
        if (port == null) {
            if (ip != null) {
                System.out
                        .println("You seem to have set an ip address, if so, you have also to assign a port before loading the driver, or else an embedded database is automatically used");
            }
            con = DriverManager.getConnection("jdbc:derby:" + databaseName + ";create=true", props);
            System.out.println("Connection is successfully established, it uses an Embedded database");
        } else if (ip == null) {
            con =
                    DriverManager.getConnection("jdbc:derby://localhost:" + port + "/" + databaseName + ";create=true",
                            props);
            System.out
                    .println("Connection is sucessfully established, it uses an network database but stored in the local host via the port: "
                            + port);
        } else {
            con =
                    DriverManager.getConnection(
                            "jdbc:derby://" + ip + ":" + port + "/" + databaseName + ";create=true", props);
            System.out.println("Connection is sucessfully established, it uses an network database whose host ip is: "
                    + ip + " and via the port: " + port);
        }
        return con;
    }

    /**
     * Shut down a specified database. But it doesn't matter that later we could also connect to another database.
     * Because the Derby engine is not closed.
     * 
     * @param databaseName
     */
    public static void shutdownDatabase(String databaseName) {
        boolean gotSQLExc = false;
        if (port == null) {
            try {
                DriverManager.getConnection("jdbc:derby:" + databaseName + ";shutdown=true");
            } catch (SQLException se) {
                if (se.getSQLState().equals("08006")) {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
            } else {
                System.out.println("Database: " + databaseName + " shut down normally");
            }
        } else if (ip == null) {
            try {
                DriverManager.getConnection("jdbc:derby://localhost:" + port + "/" + databaseName + ";shutdown=true");
            } catch (SQLException se) {
                // TODO Auto-generated catch block
                if (se.getSQLState().equals("08006")) {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
            } else {
                System.out.println("Database: " + databaseName + " shut down normally");
            }
        } else {
            try {
                DriverManager.getConnection("jdbc:derby://" + ip + ":" + port + "/" + databaseName + ";shutdown=true");
            } catch (SQLException se) {
                if (se.getSQLState().equals("08006")) {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
            } else {
                System.out.println("Database: " + databaseName + " shut down normally");
            }
        }
    }

    /**
     * shut down all opened databases and close the Derby engine. The effect is that after the execution of this method,
     * we will not permitted to use Derby again in the rest of our program. Or else, an exception of
     * "can't find a suitable driver for [a database URL]" will be thrown. However, you can still use another approach
     * to resolve this problem: newInstance() For example,
     * 
     * <pre>
     * Class.forName(&quot;org.apache.derby.jdbc.EmbeddedDriver&quot;).newInstance();
     * </pre>
     */
    public static void shutdownAll() {
        boolean gotSQLExc = false;
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException se) {
            // TODO Auto-generated catch block
            if (se.getSQLState().equals("XJ015")) {
                gotSQLExc = true;
            }
        }
        if (!gotSQLExc) {
            System.out.println("Database did not shut down normally");
        } else {
            System.out.println("All databases shut down normally and Derby completely closed");
        }
    }

    /**
     * Just connect to a database desired by providing the appropriate parameters.
     * 
     * @param databaseName
     * @param user
     * @param password
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(String databaseName, String user, String password) throws SQLException {
        if (port == null) {
            if (ip != null) {
                System.out
                        .println("You seem to have set an ip address, if so, you have also to assign a port before loading the driver, or else an embedded database is automatically used");
            }
            con = DriverManager.getConnection("jdbc:derby:" + databaseName, user, password);
            System.out.println("Connection is sucessfully established, it uses an Embedded database");
        } else if (ip == null) {
            con = DriverManager.getConnection("jdbc:derby://localhost:" + port + "/" + databaseName, user, password);
            System.out
                    .println("Connection is sucessfully established, it uses an network database but stored in the local host via the port: "
                            + port);
        } else {
            con = DriverManager.getConnection("jdbc:derby://" + ip + ":" + port + "/" + databaseName, user, password);
            System.out.println("Connection is sucessfully established, it uses an network database whose host ip is: "
                    + ip + " and via the port: " + port);
        }
        return con;
    }

    public static HashSet<String> listAllTables(Connection con) throws SQLException {
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getTables(null, null, null, new String[] { "TABLE" });
        HashSet<String> set = new HashSet<String>();
        while (res.next()) {
            set.add(res.getString("TABLE_NAME"));
            // use TABLE_SCHEM to view all users or schemas
            // set.add(res.getString("TABLE_SCHEM"));
        }
        System.out.println("All the tables associated to current connection are :");
        System.out.println(set);
        return set;
    }

    public static boolean isTableExists(String table, Connection con) throws SQLException {
        if (listAllTables(con).contains(table.toUpperCase())) {
            return true;
        } else {
            return false;
        }
    }

    public static HashSet<String> listAllSchemas(Connection con) throws SQLException {
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getSchemas(null, null);
        HashSet<String> set = new HashSet<String>();
        while (res.next()) {
            set.add(res.getString("TABLE_SCHEM"));
        }
        System.out.println("All the schemas associated to current connection are :");
        System.out.println(set);
        return set;
    }

    public static HashMap<String, String> listAllSchemasAndTables(Connection con) throws SQLException {
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getTables(null, null, null, new String[] { "TABLE" });
        HashMap<String, String> map = new HashMap<String, String>();
        while (res.next()) {
            map.put(res.getString("TABLE_SCHEM"), res.getString("TABLE_NAME"));
        }
        System.out.println("All the tables and their corresponding schemas associated to current connection are :");
        System.out.println(map);
        return map;
    }
}

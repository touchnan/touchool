package cn.touch.common.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * Created by <a href="mailto:88052350@qq.com">touchnan</a> on 2016/3/25.
 */
public class JdbcExample {
    public static final Logger _log = LoggerFactory.getLogger(JdbcExample.class);

    public static void main(String[] args) throws SQLException {
//        test();
    }

    public static final String driverClass = "";
    public static final String url = "";
    public static final String user = "";
    public static final String password = "";
    public static final String schema = user;
    public static final String catalog = null;

    public void test() throws SQLException {
        registerDriver(driverClass);

        Connection conn = DriverManager.getConnection(url, user, password);

        parseAllTables(conn);

        PreparedStatement ps = conn.prepareStatement("");
//        ps.setQueryTimeout();
        for (int i = 1; i < 3; i++) {
            ps.setObject(i, String.valueOf(i));
            ps.addBatch();
        }
        int[] ints = ps.executeBatch();


    }

    public void registerDriver(String driverClass) {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("not found jdbc driver class:[" + driverClass + "]", e);
        }
    }

    public String getCatalog() {
        return schema;
    }

    public String getSchema() {
        return catalog;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    public List<String> getAllTableNames(Connection conn) throws SQLException {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet rs = dbMetaData.getTables(getCatalog(), getSchema(), null, null);
        List<String> tables = new ArrayList<String>();
        while (rs.next()) {
            String realTableName = rs.getString("TABLE_NAME");
            tables.add(realTableName);
        }
        return tables;
    }

    public void parseAllTables(Connection conn) throws SQLException {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet rs = dbMetaData.getTables(getCatalog(), getSchema(), null, null);
        while (rs.next()) {
            parseTable(conn, rs);
        }
    }


    public void parseTable(Connection conn, String sqlTableName) throws Exception {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet rs = dbMetaData.getTables(getCatalog(), getSchema(), sqlTableName, null);
        while (rs.next()) {
            parseTable(conn, rs);
        }
        throw new RuntimeException("not found table with give name:" + sqlTableName);
    }

    private void parseTable(Connection conn, ResultSet rs) throws SQLException {
        String realTableName = null;
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            String schemaName = rs.getString("TABLE_SCHEM") == null ? "" : rs.getString("TABLE_SCHEM");
            realTableName = rs.getString("TABLE_NAME");
            String tableType = rs.getString("TABLE_TYPE");
            String remarks = rs.getString("REMARKS");
            if (remarks == null && isOracleDataBase()) {
                remarks = getOracleTableComments(realTableName);
            }
            String synonymOwner = null;
            if ("SYNONYM".equals(tableType) && isOracleDataBase()) {
                synonymOwner = getSynonymOwner(realTableName);
            }

            retriveTableColumns(realTableName, synonymOwner);

            initExportedKeys(conn.getMetaData(), realTableName);
            initImportedKeys(conn.getMetaData(), realTableName);
        } catch (SQLException e) {
            throw new RuntimeException("create table object error,tableName:" + realTableName, e);
        }
    }

    private static final String PKTABLE_NAME = "PKTABLE_NAME";
    private static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
    private static final String FKTABLE_NAME = "FKTABLE_NAME";
    private static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
    private static final String KEY_SEQ = "KEY_SEQ";

    public void initExportedKeys(DatabaseMetaData dbmd, String tableSQLName) throws java.sql.SQLException {
        // get Exported keys
        ResultSet fkeys = dbmd.getExportedKeys(catalog, schema, tableSQLName);

        while (fkeys.next()) {
            String pktable = fkeys.getString(PKTABLE_NAME);
            String pkcol = fkeys.getString(PKCOLUMN_NAME);
            String fktable = fkeys.getString(FKTABLE_NAME);
            String fkcol = fkeys.getString(FKCOLUMN_NAME);
            String seq = fkeys.getString(KEY_SEQ);
            Integer iseq = new Integer(seq);
        }
        fkeys.close();
    }

    public void initImportedKeys(DatabaseMetaData dbmd, String tableSQLName) throws java.sql.SQLException {
        // get imported keys a
        ResultSet fkeys = dbmd.getImportedKeys(catalog, schema, tableSQLName);

        while (fkeys.next()) {
            String pktable = fkeys.getString(PKTABLE_NAME);
            String pkcol = fkeys.getString(PKCOLUMN_NAME);
            String fktable = fkeys.getString(FKTABLE_NAME);
            String fkcol = fkeys.getString(FKCOLUMN_NAME);
            String seq = fkeys.getString(KEY_SEQ);
            Integer iseq = new Integer(seq);
        }
        fkeys.close();
    }


    private boolean isOracleDataBase() {
        boolean ret = false;
        try {
            ret = (getMetaData().getDatabaseProductName().toLowerCase()
                    .indexOf("oracle") != -1);
        } catch (Exception ignore) {
        }
        return ret;
    }

    private String getSynonymOwner(String synonymName) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String ret = null;
        try {
            ps = getConnection().prepareStatement("select table_owner from sys.all_synonyms where table_name=? and owner=?");
            ps.setString(1, synonymName);
            ps.setString(2, getSchema());
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = rs.getString(1);
            } else {
                String databaseStructure = getDatabaseStructureInfo();
                throw new RuntimeException("Wow! Synonym " + synonymName + " not found. How can it happen? " + databaseStructure);
            }
        } catch (SQLException e) {
            String databaseStructure = getDatabaseStructureInfo();
            _log.error(e.getMessage(), e);
            throw new RuntimeException("Exception in getting synonym owner " + databaseStructure);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
        }
        return ret;
    }

    private String getDatabaseStructureInfo() {
        ResultSet schemaRs = null;
        ResultSet catalogRs = null;
        String nl = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer(nl);
        // Let's give the user some feedback. The exception
        // is probably related to incorrect schema configuration.
        sb.append("Configured schema:").append(getSchema()).append(nl);
        sb.append("Configured catalog:").append(getCatalog()).append(nl);

        try {
            schemaRs = getMetaData().getSchemas();
            sb.append("Available schemas:").append(nl);
            while (schemaRs.next()) {
                sb.append("  ").append(schemaRs.getString("TABLE_SCHEM")).append(nl);
            }
        } catch (SQLException e2) {
            _log.warn("Couldn't get schemas", e2);
            sb.append("  ?? Couldn't get schemas ??").append(nl);
        } finally {
            try {
                schemaRs.close();
            } catch (Exception ignore) {
            }
        }

        try {
            catalogRs = getMetaData().getCatalogs();
            sb.append("Available catalogs:").append(nl);
            while (catalogRs.next()) {
                sb.append("  ").append(catalogRs.getString("TABLE_CAT")).append(nl);
            }
        } catch (SQLException e2) {
            _log.warn("Couldn't get catalogs", e2);
            sb.append("  ?? Couldn't get catalogs ??").append(nl);
        } finally {
            try {
                catalogRs.close();
            } catch (Exception ignore) {
            }
        }
        return sb.toString();
    }

    private DatabaseMetaData getMetaData() throws SQLException {
        return getConnection().getMetaData();
    }

    private void retriveTableColumns(String tableSQLName, String ownerSynonymName) throws SQLException {
        _log.debug("-------setColumns(" + tableSQLName + ")");

        List primaryKeys = getTablePrimaryKeys(tableSQLName, ownerSynonymName);


        // get the indices and unique columns
        List indices = new LinkedList();
        // maps index names to a list of columns in the index
        Map uniqueIndices = new HashMap();
        // maps column names to the index name.
        Map uniqueColumns = new HashMap();
        ResultSet indexRs = null;

        try {

            if (ownerSynonymName != null) {
                indexRs = getMetaData().getIndexInfo(getCatalog(), ownerSynonymName, tableSQLName, false, true);
            } else {
                indexRs = getMetaData().getIndexInfo(getCatalog(), getSchema(), tableSQLName, false, true);
            }
            while (indexRs.next()) {
                String columnName = indexRs.getString("COLUMN_NAME");
                if (columnName != null) {
                    _log.debug("index:" + columnName);
                    indices.add(columnName);
                }

                // now look for unique columns
                String indexName = indexRs.getString("INDEX_NAME");
                boolean nonUnique = indexRs.getBoolean("NON_UNIQUE");

                if (!nonUnique && columnName != null && indexName != null) {
                    List l = (List) uniqueColumns.get(indexName);
                    if (l == null) {
                        l = new ArrayList();
                        uniqueColumns.put(indexName, l);
                    }
                    l.add(columnName);
                    uniqueIndices.put(columnName, indexName);
                    _log.debug("unique:" + columnName + " (" + indexName + ")");
                }
            }
        } catch (Throwable t) {
            // Bug #604761 Oracle getIndexInfo() needs major grants
            // http://sourceforge.net/tracker/index.php?func=detail&aid=604761&group_id=36044&atid=415990
        } finally {
            if (indexRs != null) {
                indexRs.close();
            }
        }

        List<Column> columns = getTableColumns(tableSQLName, ownerSynonymName, primaryKeys, indices, uniqueIndices, uniqueColumns);
        for (Iterator i = columns.iterator(); i.hasNext(); ) {
            Column column = (Column) i.next();
//            table.addColumn(column);
        }

        // In case none of the columns were primary keys, issue a warning.
        if (primaryKeys.size() == 0) {
            _log.warn("WARNING: The JDBC driver didn't report any primary key columns in " + tableSQLName);
        }
    }

    private List<Column> getTableColumns(String tableSQLName, String ownerSynonymName, List primaryKeys, List indices, Map uniqueIndices, Map uniqueColumns) throws SQLException {
        // get the columns
        List<Column> columns = new LinkedList<Column>();
        ResultSet columnRs = getColumnsResultSet(tableSQLName, ownerSynonymName);

        while (columnRs.next()) {
            int sqlType = columnRs.getInt("DATA_TYPE");
            String sqlTypeName = columnRs.getString("TYPE_NAME");
            String columnName = columnRs.getString("COLUMN_NAME");
            String columnDefaultValue = columnRs.getString("COLUMN_DEF");

            String remarks = columnRs.getString("REMARKS");
            if (remarks == null && isOracleDataBase()) {
                remarks = getOracleColumnComments(tableSQLName, columnName);
            }

            // if columnNoNulls or columnNullableUnknown assume "not nullable"
            boolean isNullable = (DatabaseMetaData.columnNullable == columnRs.getInt("NULLABLE"));
            int size = columnRs.getInt("COLUMN_SIZE");
            int decimalDigits = columnRs.getInt("DECIMAL_DIGITS");

            boolean isPk = primaryKeys.contains(columnName);
            boolean isIndexed = indices.contains(columnName);
            String uniqueIndex = (String) uniqueIndices.get(columnName);
            List columnsInUniqueIndex = null;
            if (uniqueIndex != null) {
                columnsInUniqueIndex = (List) uniqueColumns.get(uniqueIndex);
            }


            boolean isUnique = columnsInUniqueIndex != null && columnsInUniqueIndex.size() == 1;
            if (isUnique) {
                _log.debug("unique column:" + columnName);
            }
            Column column = new Column(
//                    table,
                    sqlType,
                    sqlTypeName,
                    columnName,
                    size,
                    decimalDigits,
                    isPk,
                    isNullable,
                    isIndexed,
                    isUnique,
                    columnDefaultValue,
                    remarks);
            columns.add(column);
        }
        columnRs.close();
        return columns;
    }

    private ResultSet getColumnsResultSet(String tableSQLName, String ownerSynonymName) throws SQLException {
        ResultSet columnRs = null;
        if (ownerSynonymName != null) {
            columnRs = getMetaData().getColumns(getCatalog(), ownerSynonymName, tableSQLName, null);
        } else {
            columnRs = getMetaData().getColumns(getCatalog(), getSchema(), tableSQLName, null);
        }
        return columnRs;
    }

    private List getTablePrimaryKeys(String tableSQLName, String ownerSynonymName) throws SQLException {
        // get the primary keys
        List primaryKeys = new LinkedList();
        ResultSet primaryKeyRs = null;
        if (ownerSynonymName != null) {
            primaryKeyRs = getMetaData().getPrimaryKeys(getCatalog(), ownerSynonymName, tableSQLName);
        } else {
            primaryKeyRs = getMetaData().getPrimaryKeys(getCatalog(), getSchema(), tableSQLName);
        }
        while (primaryKeyRs.next()) {
            String columnName = primaryKeyRs.getString("COLUMN_NAME");
            _log.debug("primary key:" + columnName);
            primaryKeys.add(columnName);
        }
        primaryKeyRs.close();
        return primaryKeys;
    }

    private String getOracleTableComments(String table) {
        String sql = "SELECT comments FROM user_tab_comments WHERE table_name='" + table + "'";
        return queryForString(sql);
    }

    private String queryForString(String sql) {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = getConnection().createStatement();
            rs = s.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (s != null)
                    s.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
            }
        }
    }

    private String getOracleColumnComments(String table, String column) {
        String sql = "SELECT comments FROM user_col_comments WHERE table_name='" + table + "' AND column_name = '" + column + "'";
        return queryForString(sql);
    }

}


class DatabaseDataTypesUtils {

    private final static IntStringMap _preferredJavaTypeForSqlType = new IntStringMap();

    public static boolean isFloatNumber(int sqlType,int size,int decimalDigits) {
        String javaType = getPreferredJavaType(sqlType,size,decimalDigits);
        if(javaType.endsWith("Float") || javaType.endsWith("Double") || javaType.endsWith("BigDecimal")) {
            return true;
        }
        return false;
    }

    public static boolean isIntegerNumber(int sqlType,int size,int decimalDigits) {
        String javaType = getPreferredJavaType(sqlType,size,decimalDigits);
        if(javaType.endsWith("Long") || javaType.endsWith("Integer") || javaType.endsWith("Short") || javaType.endsWith("Byte")) {
            return true;
        }
        return false;
    }

    public static boolean isDate(int sqlType,int size,int decimalDigits) {
        String javaType = getPreferredJavaType(sqlType,size,decimalDigits);
        if(javaType.endsWith("Date") || javaType.endsWith("Timestamp") || javaType.endsWith("Time")) {
            return true;
        }
        return false;
    }

    public static boolean isString(int sqlType,int size,int decimalDigits) {
        String javaType = getPreferredJavaType(sqlType,size,decimalDigits);
        if(javaType.endsWith("String")) {
            return true;
        }
        return false;
    }

    public static String getPreferredJavaType(int sqlType, int size,
                                              int decimalDigits) {
        if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC)
                && decimalDigits == 0) {
            if (size == 1) {
                // https://sourceforge.net/tracker/?func=detail&atid=415993&aid=662953&group_id=36044
                return "boolean";
            } else if (size <= 10) {
                return "int";
            } else if (size <= 19) {
                return "Long";
            } else {
                return "BigDecimal";
            }
        }
        String result = _preferredJavaTypeForSqlType.getString(sqlType);
        if (result == null) {
            result = "java.lang.Object";
        }
        return result;
    }

    static {
        //  "String", "boolean", "Date", "int", "Integer", "Long", "double", "float", "BigDecimal"
        _preferredJavaTypeForSqlType.put(Types.TINYINT, "int");
        _preferredJavaTypeForSqlType.put(Types.SMALLINT, "int");
        _preferredJavaTypeForSqlType.put(Types.INTEGER, "Integer");
        _preferredJavaTypeForSqlType.put(Types.BIGINT, "Long");
        _preferredJavaTypeForSqlType.put(Types.REAL, "float");
        _preferredJavaTypeForSqlType.put(Types.FLOAT, "double");
        _preferredJavaTypeForSqlType.put(Types.DOUBLE, "double");
        _preferredJavaTypeForSqlType.put(Types.DECIMAL, "BigDecimal");
        _preferredJavaTypeForSqlType.put(Types.NUMERIC, "BigDecimal");
        _preferredJavaTypeForSqlType.put(Types.BIT, "boolean");
        _preferredJavaTypeForSqlType.put(Types.BOOLEAN, "boolean");
        _preferredJavaTypeForSqlType.put(Types.CHAR, "String");
        _preferredJavaTypeForSqlType.put(Types.VARCHAR, "String");
        // according to resultset.gif, we should use java.io.Reader, but String is more convenient for EJB
        _preferredJavaTypeForSqlType.put(Types.LONGVARCHAR, "String");
//      _preferredJavaTypeForSqlType.put(Types.BINARY, "byte[]");
//      _preferredJavaTypeForSqlType.put(Types.VARBINARY, "byte[]");
//      _preferredJavaTypeForSqlType.put(Types.LONGVARBINARY, "byte[]");
        _preferredJavaTypeForSqlType.put(Types.DATE, "Date");
        _preferredJavaTypeForSqlType.put(Types.TIME, "Date");
        _preferredJavaTypeForSqlType.put(Types.TIMESTAMP, "Date");
        _preferredJavaTypeForSqlType.put(Types.CLOB, "String");
        _preferredJavaTypeForSqlType.put(Types.BLOB, "String");
//      _preferredJavaTypeForSqlType.put(Types.ARRAY, "java.sql.Array");
//      _preferredJavaTypeForSqlType.put(Types.REF, "java.sql.Ref");
//      _preferredJavaTypeForSqlType.put(Types.STRUCT, "java.lang.Object");
//      _preferredJavaTypeForSqlType.put(Types.JAVA_OBJECT, "java.lang.Object");
    }

    /*-
    public static String getPreferredJavaType(int sqlType, int size,
            int decimalDigits) {
        if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC)
                && decimalDigits == 0) {
            if (size == 1) {
                // https://sourceforge.net/tracker/?func=detail&atid=415993&aid=662953&group_id=36044
                return "java.lang.Boolean";
            } else if (size < 3) {
                return "java.lang.Byte";
            } else if (size < 5) {
                return "java.lang.Short";
            } else if (size < 10) {
                return "java.lang.Integer";
            } else if (size < 19) {
                return "java.lang.Long";
            } else {
                return "java.math.BigDecimal";
            }
        }
        String result = _preferredJavaTypeForSqlType.getString(sqlType);
        if (result == null) {
            result = "java.lang.Object";
        }
        return result;
    }

   static {
      _preferredJavaTypeForSqlType.put(Types.TINYINT, "java.lang.Byte");
      _preferredJavaTypeForSqlType.put(Types.SMALLINT, "java.lang.Short");
      _preferredJavaTypeForSqlType.put(Types.INTEGER, "java.lang.Integer");
      _preferredJavaTypeForSqlType.put(Types.BIGINT, "java.lang.Long");
      _preferredJavaTypeForSqlType.put(Types.REAL, "java.lang.Float");
      _preferredJavaTypeForSqlType.put(Types.FLOAT, "java.lang.Double");
      _preferredJavaTypeForSqlType.put(Types.DOUBLE, "java.lang.Double");
      _preferredJavaTypeForSqlType.put(Types.DECIMAL, "java.math.BigDecimal");
      _preferredJavaTypeForSqlType.put(Types.NUMERIC, "java.math.BigDecimal");
      _preferredJavaTypeForSqlType.put(Types.BIT, "java.lang.Boolean");
      _preferredJavaTypeForSqlType.put(Types.BOOLEAN, "java.lang.Boolean");
      _preferredJavaTypeForSqlType.put(Types.CHAR, "java.lang.String");
      _preferredJavaTypeForSqlType.put(Types.VARCHAR, "java.lang.String");
      // according to resultset.gif, we should use java.io.Reader, but String is more convenient for EJB
      _preferredJavaTypeForSqlType.put(Types.LONGVARCHAR, "java.lang.String");
      _preferredJavaTypeForSqlType.put(Types.BINARY, "byte[]");
      _preferredJavaTypeForSqlType.put(Types.VARBINARY, "byte[]");
      _preferredJavaTypeForSqlType.put(Types.LONGVARBINARY, "byte[]");
      _preferredJavaTypeForSqlType.put(Types.DATE, "java.sql.Date");
      _preferredJavaTypeForSqlType.put(Types.TIME, "java.sql.Time");
      _preferredJavaTypeForSqlType.put(Types.TIMESTAMP, "java.sql.Timestamp");
      _preferredJavaTypeForSqlType.put(Types.CLOB, "java.sql.Clob");
      _preferredJavaTypeForSqlType.put(Types.BLOB, "java.sql.Blob");
      _preferredJavaTypeForSqlType.put(Types.ARRAY, "java.sql.Array");
      _preferredJavaTypeForSqlType.put(Types.REF, "java.sql.Ref");
      _preferredJavaTypeForSqlType.put(Types.STRUCT, "java.lang.Object");
      _preferredJavaTypeForSqlType.put(Types.JAVA_OBJECT, "java.lang.Object");
   }
   */
    private static class IntStringMap extends HashMap {

        public String getString(int i) {
            return (String) get(new Integer(i));
        }

        public String[] getStrings(int i) {
            return (String[]) get(new Integer(i));
        }

        public void put(int i, String s) {
            put(new Integer(i), s);
        }

        public void put(int i, String[] sa) {
            put(new Integer(i), sa);
        }
    }

}


class Column {

    /**
     * The java.sql.Types type
     */
    private final int _sqlType;

    /**
     * The sql typename. provided by JDBC driver
     */
    private final String _sqlTypeName;

    /**
     * The name of the column
     */
    private final String _sqlName;

    /**
     * True if the column is a primary key
     */
    private boolean _isPk;

    /**
     * True if the column is a foreign key
     */
    private boolean _isFk;

    /**
     * @todo-javadoc Describe the column
     */
    private final int _size;

    /**
     * @todo-javadoc Describe the column
     */
    private final int _decimalDigits;

    /**
     * True if the column is nullable
     */
    private final boolean _isNullable;

    /**
     * True if the column is indexed
     */
    private final boolean _isIndexed;

    /**
     * True if the column is unique
     */
    private final boolean _isUnique;

    /**
     * Null if the DB reports no default value
     */
    private final String _defaultValue;

    /**
     * The comments of column
     */
    private final String _remarks;

    /**
     * Get static reference to Log4J Logger
     */
    private static Logger _log = LoggerFactory.getLogger(Column.class);

//	String description;
//
//	String humanName;
//
//	int order;
//
//	boolean isHtmlHidden;
//
//	String validateString;

    /**
     * Describe what the DbColumn constructor does
     *
     * @param sqlType
     *            Describe what the parameter does
     * @param sqlTypeName
     *            Describe what the parameter does
     * @param sqlName
     *            Describe what the parameter does
     * @param size
     *            Describe what the parameter does
     * @param decimalDigits
     *            Describe what the parameter does
     * @param isPk
     *            Describe what the parameter does
     * @param isNullable
     *            Describe what the parameter does
     * @param isIndexed
     *            Describe what the parameter does
     * @param defaultValue
     *            Describe what the parameter does
     * @param isUnique
     *            Describe what the parameter does
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for constructor
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for method parameter
     */
    public Column(int sqlType, String sqlTypeName,
                  String sqlName, int size, int decimalDigits, boolean isPk,
                  boolean isNullable, boolean isIndexed, boolean isUnique,
                  String defaultValue,String remarks) {
//		_table = table;
        _sqlType = sqlType;
        _sqlName = sqlName;
        _sqlTypeName = sqlTypeName;
        _size = size;
        _decimalDigits = decimalDigits;
        _isPk = isPk;
        _isNullable = isNullable;
        _isIndexed = isIndexed;
        _isUnique = isUnique;
        _defaultValue = defaultValue;
        _remarks = remarks;

        _log.debug(sqlName + " isPk -> " + _isPk);

    }

    /**
     * Gets the SqlType attribute of the Column object
     *
     * @return The SqlType value
     */
    public int getSqlType() {
        return _sqlType;
    }

    /**
     * Gets the Size attribute of the DbColumn object
     *
     * @return The Size value
     */
    public int getSize() {
        return _size;
    }

    /**
     * Gets the DecimalDigits attribute of the DbColumn object
     *
     * @return The DecimalDigits value
     */
    public int getDecimalDigits() {
        return _decimalDigits;
    }

    /**
     * Gets the SqlTypeName attribute of the Column object
     *
     * @return The SqlTypeName value
     */
    public String getSqlTypeName() {
        return _sqlTypeName;
    }

    /**
     * Gets the SqlName attribute of the Column object
     *
     * @return The SqlName value
     */
    public String getSqlName() {
        return _sqlName;
    }

    public String getUnderscoreName() {
        return getSqlName().toLowerCase();
    }

    /**
     * Gets the Pk attribute of the Column object
     *
     * @return The Pk value
     */
    public boolean isPk() {
        return _isPk;
    }

    /**
     * Gets the Fk attribute of the Column object
     *
     * @return The Fk value
     */
    public boolean isFk() {
        return _isFk;
    }

    /**
     * Gets the Nullable attribute of the Column object
     *
     * @return The Nullable value
     */
    public final boolean isNullable() {
        return _isNullable;
    }

    /**
     * Gets the Indexed attribute of the DbColumn object
     *
     * @return The Indexed value
     */
    public final boolean isIndexed() {
        return _isIndexed;
    }

    /**
     * Gets the Unique attribute of the DbColumn object
     *
     * @return The Unique value
     */
    public boolean isUnique() {
        return _isUnique;
    }

    /**
     * Gets the DefaultValue attribute of the DbColumn object
     *
     * @return The DefaultValue value
     */
    public final String getDefaultValue() {
        return _defaultValue;
    }

    public final String getRemarks() {
        return _remarks;
    }

    /**
     * Describe what the method does
     *
     * @return Describe the return value
     * @todo-javadoc Write javadocs for method
     * @todo-javadoc Write javadocs for return value
     */
//	public int hashCode() {
//		return (getTable().getSqlName() + "#" + getSqlName()).hashCode();
//	}

    /**
     * Describe what the method does
     *
     * @param o
     *            Describe what the parameter does
     * @return Describe the return value
     * @todo-javadoc Write javadocs for method
     * @todo-javadoc Write javadocs for method parameter
     * @todo-javadoc Write javadocs for return value
     */
//	public boolean equals(Object o) {
//		// we can compare by identity, since there won't be dupes
//		return this == o;
//	}

    /**
     * Describe what the method does
     *
     * @return Describe the return value
     * @todo-javadoc Write javadocs for method
     * @todo-javadoc Write javadocs for return value
     */
    public String toString() {
        return getSqlName();
    }

    /**
     * Describe what the method does
     *
     * @return Describe the return value
     * @todo-javadoc Write javadocs for method
     * @todo-javadoc Write javadocs for return value
     */
//	protected final String prefsPrefix() {
//		return "tables/" + getTable().getSqlName() + "/columns/" + getSqlName();
//	}

    /**
     * Sets the Pk attribute of the DbColumn object
     *
     * @param flag
     *            The new Pk value
     */
    void setFk(boolean flag) {
        _isFk = flag;
    }

//	public String getColumnName() {
//	    return isPk() ? "id" : ExchangeRuleHelper.parsePropertyNameFromSqlColumnName(getSqlName().toLowerCase());
//	}

//	public String getColumnNameFirstLower() {
//	    return Strings.lowerFirst(getColumnName());
//	}
//
//	public String getColumnNameLowerCase() {
//		return getColumnName().toLowerCase();
//	}

    public String getJdbcSqlTypeName() {
        String result = JdbcType.getJdbcSqlTypeName(getSqlType());
        //if(result == null) throw new RuntimeException("jdbcSqlTypeName is null column:"+getSqlName()+" sqlType:"+getSqlType());
        return result;
    }

//	public String getColumnAlias() {
//	    return !Strings.isBlank(getRemarks()) ? getRemarks():  getColumnName();
//	}

//	public String getConstantName() {
//	    //TODO
//	    return getSqlName().toUpperCase();
////		return StringHelper.toUnderscoreName(getSqlName()).toUpperCase();
//	}

    public boolean getIsNotIdOrVersionField() {
        return !isPk();
    }

    public String getValidateString() {
        String result = getNoRequiredValidateString();
        if(!isNullable()) {
            result = "required " + result;
        }
        return result;
    }

    public String getNoRequiredValidateString() {
        String result = "";
        if(getSqlName().indexOf("mail") >= 0) {
            result += "validate-email ";
        }
        if(DatabaseDataTypesUtils.isFloatNumber(getSqlType(), getSize(), getDecimalDigits())) {
            result += "validate-number ";
        }
        if(DatabaseDataTypesUtils.isIntegerNumber(getSqlType(), getSize(), getDecimalDigits())) {
            result += "validate-integer ";
            if(getJavaType().indexOf("Short") >= 0) {
                result += "max-value-"+Short.MAX_VALUE;
            }else if(getJavaType().indexOf("Integer") >= 0) {
                result += "max-value-"+Integer.MAX_VALUE;
            }else if(getJavaType().indexOf("Byte") >= 0) {
                result += "max-value-"+Byte.MAX_VALUE;
            }
        }
//		if(DatabaseDataTypesUtils.isDate(getSqlType(), getSize(), getDecimalDigits())) {
//			result += "validate-date ";
//		}
        return result;
    }

    public boolean getIsStringColumn() {
        return DatabaseDataTypesUtils.isString(getSqlType(), getSize(), getDecimalDigits());
    }

    public boolean getIsDateTimeColumn() {
        return DatabaseDataTypesUtils.isDate(getSqlType(), getSize(), getDecimalDigits());
    }

    public boolean getIsNumberColumn() {
        return DatabaseDataTypesUtils.isFloatNumber(getSqlType(), getSize(), getDecimalDigits()) || DatabaseDataTypesUtils.isIntegerNumber(getSqlType(), getSize(), getDecimalDigits());
    }

//	public boolean isHtmlHidden() {
//		return isPk() && _table.isSingleId();
//	}

    public String getJavaType() {
        return DatabaseDataTypesUtils.getPreferredJavaType(getSqlType(), getSize(), getDecimalDigits());
    }

}
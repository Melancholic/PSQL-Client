package com.anagorny.psqclient;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import javax.sql.rowset.CachedRowSet;
import java.beans.PropertyVetoException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sosnov
 * Date: 20.07.13
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */

public class ClientBase {
    // private Connection connect = null;
    // private Statement stmt = null;
    private String Url;
    private String fullUrl;
    private String dbName;
    private String Usr;
    private String Pass;
    private ComboPooledDataSource ConnectPools; // Пул C3P0 ( Hello, StarWars! ^_^ )

    public ClientBase(String Driver, String url, String name, String user, String pass) throws SQLException {
        Url = url;
        dbName = name;
        fullUrl = Driver + "://" + Url + "/" + dbName;
        System.out.println(fullUrl);
        Usr = user;
        Pass = pass;

        ConnectPools = new ComboPooledDataSource();
        try {
            ConnectPools.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }

        ConnectPools.setJdbcUrl(fullUrl);
        ConnectPools.setUser(Usr);
        ConnectPools.setPassword(Pass);
        ConnectPools.setMinPoolSize(25);

     /*   try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }

        Connection connect = DriverManager.getConnection(fullUrl, Usr, Pass);
        Statement stmt = connect.createStatement();

        DriverManager.setLoginTimeout(20);
               */
        System.out.println("Database opened");


    }

    public Connection getNewConnect() {
        Connection con = null;
        try {
          //  Class.forName("org.postgresql.Driver");
          //  con = DriverManager.getConnection(fullUrl, Usr, Pass);
            con=ConnectPools.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }
        return con;
    }

    public void closeConnect(Connection con) {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }
    }

    public void createTable(String Name, ArrayList<String> lines) {
        String IDName = lines.get(0).substring(0, lines.get(0).indexOf(" "));
        System.out.println(IDName);
        String sql_func = "CREATE OR REPLACE  FUNCTION " + Name + "_trg_" + IDName + "_func() RETURNS trigger AS" + "\n" +
                "$BODY$" + "\n" +
                "BEGIN" + "\n" +
                "NEW." + IDName + ":=nextval(\'" + Name + "_" + IDName + "_seq\');" + "\n" +
                "return NEW;" + "\n" +
                "END;" + "\n" +
                " $BODY$" + "\n" +
                "LANGUAGE  plpgsql VOLATILE;";
        String sql_trig = "CREATE TRIGGER " + Name + "_trg_" + IDName + "\n" +
                "BEFORE INSERT ON " + Name + "\n" +
                "FOR EACH ROW" + "\n" +
                "EXECUTE PROCEDURE " + Name + "_trg_" + IDName + "_func();";

        System.out.println(sql_func);
        System.out.println(sql_trig);

        String sql = "CREATE TABLE " + Name + " (";
        for (String i : lines) {
            if (i != lines.get(lines.size() - 1)) {
                sql += (i + ", ");
            } else {
                sql += (i + " )");
            }
        }
        //sql += ")";
        System.out.println("SQL: \n" + sql);
        try {
            Connection connect = null;
            connect = getNewConnect();
            connect= ConnectPools.getConnection();
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql_func);
            stmt.executeUpdate(sql_trig);
            closeConnect(connect);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table Created");

    }

    public void remTable(String Name) {
        String sql = " DROP TABLE IF EXISTS  " + Name + ";";
        try {
            Connection connect = null;
            connect = getNewConnect();
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(sql);
            closeConnect(connect);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table removed");

    }

    ArrayList<String> getTableList() {
        ArrayList<String> Tables = new ArrayList();
        try {
            Connection connect = null;
            connect = getNewConnect();
            DatabaseMetaData metaData = connect.getMetaData();
            String[] types = {"TABLE", "VIEW"};
            ResultSet rs = metaData.getTables(dbName, null, "%", types);
            while (rs.next()) {
                Tables.add(rs.getString("TABLE_NAME"));
            }
            closeConnect(connect);
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return Tables;
    }

    private String SQLTypesToString(int value) {
        Field fields[] = java.sql.Types.class.getFields();
        for (int i = 0; i < fields.length; ++i) {
            try {
                if (value == (Integer) fields[i].get(null)) {
                    return fields[i].getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return "UNKNOW";
    }

    boolean SQLTypeIsText(int SqlType) {
        return SqlType == Types.VARCHAR | SqlType == Types.CHAR | SqlType == Types.LONGVARCHAR | SqlType == Types.LONGNVARCHAR;
    }

    ArrayList<String> getColumnsName(String tableName) {
        ArrayList<String> paramName = new ArrayList<String>();
        try {
            Connection connect = null;
            connect = getNewConnect();
            DatabaseMetaData metaData = connect.getMetaData();

            ResultSet columns = metaData.getColumns(null, null, tableName, "%");
            while (columns.next()) {
                // get the info from the resultset (eg the java.sql.Types value):
                paramName.add(columns.getString("COLUMN_NAME"));

            }
            closeConnect(connect);
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return paramName;
    }

    ArrayList<String> getColumnsType(String tableName) {
        ArrayList<String> paramName = new ArrayList<String>();
        try {
            Connection connect = null;
            connect = getNewConnect();
            DatabaseMetaData metaData = connect.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, "%");
            while (columns.next()) {
                // get the info from the resultset (eg the java.sql.Types value):
                paramName.add(SQLTypesToString(columns.getInt("DATA_TYPE")));

            }
            closeConnect(connect);
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return paramName;
    }

    ArrayList<Integer> getColumnsTypeToInt(String tableName) {
        ArrayList<Integer> paramName = new ArrayList();
        try {
            Connection connect = null;
            connect = getNewConnect();
            DatabaseMetaData metaData = connect.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, "%");
            while (columns.next()) {
                // get the info from the resultset (eg the java.sql.Types value):
                paramName.add((columns.getInt("DATA_TYPE")));

            }
            closeConnect(connect);
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return paramName;
    }

    int getType(String tableName, String colName) {
        int result = Integer.MIN_VALUE;
        try {
            Connection connect = null;
            connect = getNewConnect();
            DatabaseMetaData metaData = connect.getMetaData();

            ResultSet columns = metaData.getColumns(null, null, tableName, "%");
            while (columns.next()) {
                // get the info from the resultset (eg the java.sql.Types value):
                if (columns.getString("COLUMN_NAME").equals(colName)) {
                    result = columns.getInt("DATA_TYPE");
                }

            }
            closeConnect(connect);
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return result;
    }

    boolean insert(String tableName, ArrayList<String> argsName, ArrayList<String> argsVal) {
        if (argsVal.size() != argsName.size()) {
            //Тут лог об ошибке
            System.err.println("Err in size args");
            return false;
        }
        try {
            String sql = "INSERT INTO " + tableName + "(";
            for (String i : argsName) {
                sql += i;
                if (!i.equals(argsName.get(argsName.size() - 1))) {
                    sql += ",";

                } else {
                    sql += ")";
                }
            }
            sql += " VALUES (";
            for (int i = 0; i < argsVal.size(); ++i) {
                if (argsVal.get(i).equals("") && i != argsVal.size() - 1) continue;
                System.out.println(argsName.get(i));
                // if (getColumnsType(tableName).get(i).equals("text") || getColumnsType(tableName).get(i).equals("character")) {
                if (getType(tableName, argsName.get(i)) == Types.CHAR || (getType(tableName, argsName.get(i)) == Types.VARCHAR)) {
                    sql += ("\'" + argsVal.get(i) + "\'");
                } else {
                    sql += argsVal.get(i);
                }
                if (i == argsVal.size() - 1) {
                    sql += ");";

                } else {
                    sql += ",";
                }
            }
            Connection connect = null;
            connect = getNewConnect();
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Record create");
            closeConnect(connect);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return true;

    }

    ArrayList<String[]> select(String tableName, String operand) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        try {
            Connection connect = null;
            connect = getNewConnect();
            ResultSet rs = connect.createStatement().executeQuery("SELECT " + operand + " FROM " + tableName + ";");
            ResultSetMetaData rsmd = rs.getMetaData();
            ArrayList<String> colType = getColumnsType(tableName);
            ArrayList<String> colName = getColumnsName(tableName);
            int cols = rsmd.getColumnCount();
            String c[] = new String[cols];
            for (int i = 0; i < cols; i++) {
                c[i] = rsmd.getColumnName(i + 1);
            }
            result.add(c);
            while (rs.next()) {
                String row[] = new String[cols];
                for (int i = 0; i < cols; i++) {
                    row[i] = rs.getString(i + 1);
                }
                result.add(row);
            }
            closeConnect(connect);
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return result;
    }

    void selectToCachRow(String tableName, String operand, CachedRowSet CachRS) throws Exception {
        Connection connect = null;
        connect = getNewConnect();
        CachRS.setUrl(fullUrl);
        CachRS.setUsername(Usr);
        CachRS.setPassword(Pass);
        CachRS.setCommand("SELECT " + operand + " FROM " + tableName + ";");
        CachRS.setTableName(tableName);
        CachRS.execute(connect);
        closeConnect(connect);
    }

    void selectToCachRow(String sql, CachedRowSet CachRS) throws Exception {
        selectToCachRow(sql, " * ", CachRS);
    }

    void newRequest(String sql) throws Exception {
        Connection connect = null;
        connect = getNewConnect();
        Statement stmt = connect.createStatement();
        connect = getNewConnect();
        stmt = connect.createStatement();
        stmt.executeUpdate(sql);
        closeConnect(connect);
        stmt.close();

    }

    void delete(String tableName, String colName, String colVal) throws Exception {
        Connection connect = null;
        connect = getNewConnect();
        Statement stmt = connect.createStatement();
        stmt = connect.createStatement();
        String sql = "DELETE from " + tableName + " where " + colName + "=";
        sql += (SQLTypeIsText(getType(tableName, colName))) ? "\'" + colVal + "\'" : colVal;
        sql += ";";
        System.out.print(sql);
        stmt.executeUpdate(sql);
        //connect.commit();
        closeConnect(connect);
        stmt.close();
    }

    String getPrimaryKey(String tableName) {
        String result = new String();
        String sql = "SELECT c2.relname=\'" + tableName + "\', a.attname " +
                "FROM pg_class c, pg_class c2, pg_index i, pg_attribute a " +
                "WHERE c.relname = \'" + tableName + "\' AND c.oid = i.indrelid AND i.indexrelid = c2.oid " +
                "AND i.indisprimary AND i.indisunique " +
                "AND a.attrelid=c2.oid " +
                "AND a.attnum>0;";
        try {
            Connection connect = null;
            connect = getNewConnect();
            Statement stmt = connect.createStatement();
            System.err.print("SQL: " + sql);
            connect = getNewConnect();
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                result = rs.getString("attname");
            }
            closeConnect(connect);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return result;

    }

    ArrayList<String> getNullsName(String tableName) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            Connection connect = null;
            connect = getNewConnect();
            ResultSet columns = connect.getMetaData().getColumns(null, "%", tableName, "%");
            while (columns.next()) {
                boolean isNull = (1 == columns.getInt("NULLABLE"));
                if (isNull) {
                    String columnName = columns.getString("COLUMN_NAME");
                    if (isPrimary(tableName, columnName)) {
                        continue;
                    } else {
                        result.add(columnName);
                    }
                }
            }
            closeConnect(connect);
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return result;
    }

    boolean isNull(String tableName, String colName) {
        ArrayList<String> nullable = getNullsName(tableName);
        for (String i : nullable) {
            if (i.equals(colName)) {
                return true;
            }
        }
        return false;
    }

    boolean isPrimary(String tableName, String colName) {
        ArrayList<String> primaries = getPrimaryKeysName(tableName);
        for (String i : primaries) {
            System.err.println("pr: " + i);
            if (i.equals(colName)) {
                return true;
            }
        }
        return false;
    }

    ArrayList<String> getPrimaryKeysName(String tableName) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            Connection connect = null;
            connect = getNewConnect();
            ResultSet rs = connect.getMetaData().getPrimaryKeys(dbName, tableName, null);
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= cols; ++i) {
                    result.add(rs.getString(i));
                    System.err.println("rs: " + rs.getString(i));

                }
            }
            closeConnect(connect);
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return result;
    }

    public void cleaner() {
        try {
            DataSources.destroy(ConnectPools);
            /*if (connect != null && !connect.isClosed()) {
                connect.close();
                connect = null;
            }
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
                stmt = null;
            }
            System.out.println("Все почищено:)"); */
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }


    }

    public String getUrl() {
        return Url;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUsr() {
        return Usr;
    }

 /*   public CachedRowSet getCachRS() {
        return CachRS;
    }  */

  /*  public Connection getConnect() {
        return connect;
    }
  */
}

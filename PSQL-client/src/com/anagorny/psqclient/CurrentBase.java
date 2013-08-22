package com.anagorny.psqclient;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: sosnov
 * Date: 26.07.13
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
public class CurrentBase {
    static private ClientBase Base;

    public static void init(String driver, String url, String dbName, String usr, String pass) throws SQLException {
        Base = new ClientBase(driver, url, dbName, usr, pass);
    }

    public static ClientBase getBase() {
        return Base;
    }

    public static void cleaner() {
        Base = null;
    }
}

package com.anagorny.psqclient;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sosnov
 * Date: 01.08.13
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class DBTable extends JTable {
    public DBTable() {
        super();
    }

    public void mkTable(ArrayList<String[]> table) {
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

}

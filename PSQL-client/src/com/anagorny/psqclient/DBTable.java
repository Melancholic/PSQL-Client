package com.anagorny.psqclient;

import javax.swing.*;
import java.awt.event.MouseEvent;
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
        //   this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        String result = "";
        int column = columnAtPoint(event.getPoint());
        if (column != -1) {
            result = getModel().getColumnClass(column).getName();
            result = result.substring(result.lastIndexOf(".") + 1);
        }
        return result;
    }

}

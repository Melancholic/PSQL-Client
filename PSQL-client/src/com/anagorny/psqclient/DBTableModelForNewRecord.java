package com.anagorny.psqclient;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Created with IntelliJ IDEA.
 * User: sosnov
 * Date: 03.08.13
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class DBTableModelForNewRecord implements TableModel {
    private DBTableModel dbTableM;
    private Object[] arr;

    public DBTableModelForNewRecord(DBTableModel tableModel) {
        dbTableM = tableModel;
        arr = new Object[getColumnCount()];
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return dbTableM.getColumnCount();
    }

    @Override
    public String getColumnName(int i) {
        return dbTableM.getColumnName(i);
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return dbTableM.getColumnClass(i);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return dbTableM.isCellEditable(row, col);
        //return true;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return arr[col];
    }

    @Override
    public void setValueAt(Object o, int row, int col) {
        arr[col] = o;
    }

    @Override
    public void addTableModelListener(TableModelListener tablLisener) {
    }

    @Override
    public void removeTableModelListener(TableModelListener tablLisener) {
    }
}

package com.anagorny.psqclient;

import javax.sql.rowset.CachedRowSet;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: sosnov
 * Date: 01.08.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class DBTableModel extends AbstractTableModel {
    private CachedRowSet CachRS;
    private int columns;
    private int rows;
    private ResultSetMetaData metaData;
    private int col;

    public DBTableModel(CachedRowSet crs) {    // Конструктор инициализ.  модели осн. таблицы
        CachRS = crs; //crs инициализируется во время подключения к бд
        try {
            metaData = CachRS.getMetaData();
            columns = metaData.getColumnCount();
            rows = 0;
            crs.beforeFirst();
            while (crs.next()) {
                rows++;
            }
            crs.beforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }
    }

    public int getColumnCount() {
        return columns;
    }

    public int getRowCount() {
        return rows;
    }


    @Override
    public String getColumnName(int col) {
        String tmp = "";
        try {
            tmp = metaData.getColumnName(col + 1);
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }
        return tmp;
    }

    @Override
    public Class<?> getColumnClass(int col) {
        Class<?> clss = null;
        try {
            clss = Class.forName(metaData.getColumnClassName(col + 1));
            if (clss == java.sql.Timestamp.class | clss == java.sql.Date.class) {
                // return String.class;
                return java.util.Date.class;
            }
        } catch (ClassNotFoundException e) {
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Тут лог об ошибке
            System.err.println(ex.getClass().getName() + " : " + ex.getMessage());
            return null;
        }
        return clss;
    }

    public Object getValueAt(int row, int col) {
        Object obj = null;
        try {
            CachRS.absolute(row + 1);
            obj = CachRS.getObject(col + 1);
            //System.err.println("obj: " + obj + " : " + obj.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }
        return obj;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        //System.err.println("obj: " + value + " : " + value.getClass().getName());
        if (value != null && value.getClass() == java.util.Date.class) {
            Date tmp = (Date) value;
            java.sql.Date dt = new java.sql.Date(tmp.getTime());
            try {
                CachRS.absolute(row + 1);
                CachRS.updateObject(col + 1, dt);
                CachRS.updateRow();
            } catch (Exception e) {
                e.printStackTrace();
                //Тут лог об ошибке
                System.err.println(e.getClass().getName() + " : " + e.getMessage());
            }
            return;
        }
        try {
            CachRS.absolute(row + 1);
            CachRS.updateObject(col + 1, value);
            CachRS.updateRow();
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }
    }

    public void update() {
        fireTableDataChanged();
    }

    public CachedRowSet getCachRS() {
        return CachRS;
    }

}

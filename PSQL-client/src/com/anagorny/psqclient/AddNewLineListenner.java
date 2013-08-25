package com.anagorny.psqclient;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: sosnov
 * Date: 03.08.13
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */


public class AddNewLineListenner implements ActionListener {
    DBTable table;
    NewRecord newRecFrame;

    AddNewLineListenner(NewRecord newRecInst, DBTable tbl) {
        table = tbl;
        newRecFrame = newRecInst;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {


        try {
            DBTableModel tmpModel = (DBTableModel) table.getModel();
            tmpModel.getCachRS().moveToInsertRow();
            for (int i = 0; i < newRecFrame.getTblModel().getColumnCount(); i++) {
                Object obj;
                obj = newRecFrame.getTblModel().getValueAt(0, i);
                if (obj != null && obj.getClass() == java.util.Date.class) {
                    java.util.Date tmp = (java.util.Date) obj;
                    java.sql.Date dt = new java.sql.Date(tmp.getTime());
                    tmpModel.getCachRS().updateObject(i + 1, dt);
                } else {

                    System.out.println("ret = tmir.getValueAt(0, i)  " + obj);
                    tmpModel.getCachRS().updateObject(i + 1, obj);
                }
            }

            tmpModel.getCachRS().insertRow();
            tmpModel.getCachRS().moveToCurrentRow();
            tmpModel.getCachRS().beforeFirst();
            Connection connect=null;
            connect=CurrentBase.getBase().getNewConnect();
            connect.setAutoCommit(false);
            tmpModel.getCachRS().acceptChanges(connect);
            tmpModel.getCachRS().commit();
            connect.setAutoCommit(true);
        } catch (SQLException e) {
            /*try {
               // connect.setAutoCommit(false);
              //  connect.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            } */
            e.printStackTrace();
            UsersDialogs.Message("SQL Exception: " + e.getMessage() + "\n" + "Err code: " + e.getErrorCode());
            return;
        }

        try {
            CachedRowSet CachRS = null;
            CachRS = new CachedRowSetImpl();
            CurrentBase.getBase().selectToCachRow(table.getName(), "*", CachRS);
            table.setModel(new DBTableModel(CachRS));
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        newRecFrame.dispose();
    }
}

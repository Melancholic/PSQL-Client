package com.anagorny.psqclient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sosnov
 * Date: 21.07.13
 * Time: 18:27
 * To change this template use File | Settings | File Templates.
 */
public class MenuWindow {
    private JFrame frame;
    private JTabbedPane tabbedPane1;
    private JButton createTableButton;
    private JButton openTableButton;
    private JPanel Panel;
    private JButton removeTableButton;
    private JList TablesList;
    private int TabsCount;


    public MenuWindow() {
        TabsCount = 0;
        frame = new JFrame("Client for PostgreSQL");
        frame.setSize(1024, 700);
        frame.setVisible(true);
        frame.add(Panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new CloseListener());
        frame.setLocationRelativeTo(null);
        MakeLoginForm();
        makeTablesList();

        createTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<String> lines = new ArrayList();
                StringBuffer Name = new StringBuffer();
                System.out.print(Name.toString());
                new InputNewTableName(Name);
                System.out.println("Name:" + Name.toString());
                if (Name == null || Name.toString().equals("") || Name.toString().indexOf(" ") != -1) {
                    UsersDialogs.Error("Table not created", "Uncorrect Table`s name!");
                    return;
                }
                StringBuffer idRec = new StringBuffer();
                new crPrimKey(idRec);
                if (idRec == null || idRec.toString().equals("") || idRec.toString().indexOf(" ") != -1) {
                    UsersDialogs.Error("Table not created", "Uncorrect Primary Key`s name!");
                    return;
                }
                //System.out.print(("aa: " + idRec.toString()));
                lines.add(idRec.toString() + "  BIGSERIAL " + "  PRIMARY KEY ");
                new MkTbl(lines);
                System.out.println("There");
                for (String i : lines) {
                    System.err.println(i);
                }
                if (lines.size() == 0) {
                    UsersDialogs.Error("Table not created !");
                    return;
                }
                CurrentBase.getBase().createTable(Name.toString(), lines);
                makeTablesList();
            }
        });

        final MenuWindow MenuWindowForNewThread = this;
        openTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

             /*   if (TablesList.getSelectedValue() == null) {
                    UsersDialogs.Error("Select table in the tables list!");
                    return;
                }    */
                ArrayList<String> tmpList = (ArrayList<String>) TablesList.getSelectedValuesList();
                if (tmpList == null) {
                    UsersDialogs.Error("Select table in the tables list!");
                    return;
                }
                for (final String tmp : tmpList) {
                    Thread newThread = new Thread(new Thread(new Runnable() {
                        @Override
                        public void run() {

                            new OpenedTable(MenuWindowForNewThread, tmp);
                        }
                    }));
                    newThread.start();
                }

            }
        });
        removeTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // String tmp = TablesList.getSelectedValue().toString();
                ArrayList<String> tmpList = (ArrayList<String>) TablesList.getSelectedValuesList();
                if (tmpList == null) {
                    UsersDialogs.Error("Select table in the tables list!");
                    return;
                }
                System.err.println("www: " + MenuWindow.this.tabbedPane1.getComponentAt(0).getName());
                for (String tmp : tmpList) {
                    for (int i = 0; i < MenuWindow.this.tabbedPane1.getComponentCount(); ++i) {
                        System.err.println(MenuWindow.this.tabbedPane1.getComponentAt(i).getName());
                        if (("Open Table " + tmp).equals(tabbedPane1.getComponentAt(i).getName())) {
                            UsersDialogs.Error("Please, close this table!");
                            return;
                        }
                    }
                    CurrentBase.getBase().remTable(tmp);
                }
                makeTablesList();
            }
        });
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public void makeTablesList() {
        DefaultListModel model = new DefaultListModel();
        TablesList.setModel(model);
        for (String i : CurrentBase.getBase().getTableList()) {
            model.addElement(i);
        }
    }

    public String getSelectedTableName() {
        String name;
        name = TablesList.getSelectedValue().toString();
        return name;
    }

    public int getTabsCount() {
        return TabsCount;
    }

    public void incTabsCount() {
        TabsCount++;
    }

    private void MakeLoginForm() {
        Login loginForm = new Login(this);

    }

    public void makeCurBaseInst(String DBDriver, String DBServer, String DBName, String DBUsr, String DBPass) throws SQLException {
        CurrentBase.init(DBDriver, DBServer, DBName, DBUsr, DBPass);
    }

    public void closeThis() {
        frame.dispose();
        System.exit(0);
    }

    private class CloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent event) {
            CurrentBase.getBase().cleaner();
            CurrentBase.cleaner();
            super.windowClosing(event);
            System.exit(0);
        }
    }


}

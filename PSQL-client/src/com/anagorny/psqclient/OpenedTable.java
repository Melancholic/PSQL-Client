package com.anagorny.psqclient;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: sosnov
 * Date: 21.07.13
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */
public class OpenedTable {
    public JPanel panel1;
    private DBTable table1;
    private JButton saveButton;
    private JButton closeWithautSavingButton;
    private JButton addLineButton;
    private JButton saveAndCloseButton;
    private JButton removeLineButton;
    private JTextField textSearch;
    private JButton UPButton;
    private JButton DOWNButton;
    private JButton discardChangesButton;
    private JButton makeSelectionButton;
    private String tableName;
    private ArrayList<String> selectedLines;
    private ArrayList<String[]> tableArray;
    private DBTableModel tableModel;
    private ArrayList<String> NoNulled;
    private MenuWindow menuWin;
    private String PrimaryKey;

    public OpenedTable(MenuWindow mw) {
        this(mw, mw.getSelectedTableName());
    }

    public OpenedTable(MenuWindow mw, String TableName) {
        tableName = TableName;
        if (tableName.toString().equals("")) {
            System.err.println("ttt: ");
            return;
        }
        menuWin = mw;
        OpenTable();
        try {
            mw.getTabbedPane1().addTab("Open Table " + tableName, this.getPanel1());
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }

        addLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new NewRecord(new DBTableModelForNewRecord(tableModel), OpenedTable.this);
                RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table1.getModel());
                table1.setRowSorter(sorter);
            }
        });
        removeLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String FirstCol = table1.getModel().getColumnName(0);
                for (String i : selectedLines) {
                    try {
                        CurrentBase.getBase().delete(tableName, FirstCol, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //Тут лог об ошибке
                        System.err.println(e.getClass().getName() + " : " + e.getMessage());
                        System.exit(0);
                    }
                    try {
                        CachedRowSet CachRS = null;
                        CachRS = new CachedRowSetImpl();
                        CurrentBase.getBase().selectToCachRow(table1.getName(), "*", CachRS);
                        table1.setModel(new DBTableModel(CachRS));
                        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table1.getModel());
                        table1.setRowSorter(sorter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //Тут лог об ошибке
                        System.err.println(e.getClass().getName() + " : " + e.getMessage());
                        System.exit(0);
                    }

                }
            }

        });

        ListSelectionModel selModel = table1.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                selectedLines = new ArrayList<String>();
                String result = "";
                int[] selectedRows = table1.getSelectedRows();
                for (int i = 0; i < selectedRows.length; i++) {
                    int selIndex = selectedRows[i];
                    TableModel model = table1.getModel();
                    Object value = model.getValueAt(selIndex, 0);
                    selectedLines.add(value.toString());
                }
            }

        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                save();

            }
        });

        saveAndCloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                save();
                closeThisTab();
            }
        });
        closeWithautSavingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                closeThisTab();

            }
        });

        textSearch.getDocument().addDocumentListener(new DocumentListener() {
            private void search() {
                String strForSearch = textSearch.getText();
                table1.clearSelection(); // очищаем все выделения
                for (int i = 0; i < table1.getRowCount(); ++i) {
                    for (int j = 0; j < table1.getColumnCount(); ++j) {
                        if (table1.getValueAt(i, j) == null) {
                            continue;
                        }
                        String str_jtab = table1.getValueAt(i, j).toString();
                        if (str_jtab.startsWith(strForSearch)) { // сравним строки

                            table1.setRowSelectionInterval(i, i); // выделить заданную строку
                            table1.setColumnSelectionInterval(j, j);
                            table1.scrollRectToVisible(table1.getCellRect(i, j, true)); // прокручиваем до выделенной строки
                            return; // выходим из цикла
                        }
                    }
                }
            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                search();

            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                search();
            }
        });

        UPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String strForSearch = textSearch.getText();
                int rowSel = table1.getSelectedRow() + 1;
                table1.clearSelection();
                for (int i = rowSel; i < table1.getRowCount(); ++i) {
                    for (int j = 0; j < table1.getColumnCount(); ++j) {
                        if (table1.getValueAt(i, j) == null) {
                            continue;
                        }
                        String str_jtab = table1.getValueAt(i, j).toString();
                        if (str_jtab.startsWith(strForSearch)) {
                            table1.setRowSelectionInterval(i, i);
                            table1.scrollRectToVisible(table1.getCellRect(i, j, true));
                            return;
                        }
                    }
                }
            }
        });
        DOWNButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String strForSearch = textSearch.getText();

                int rowSel = table1.getSelectedRow() - 1;
                table1.clearSelection();
                for (int i = rowSel; i >= 0; --i) {
                    for (int j = 0; j < table1.getColumnCount(); ++j) {
                        if (table1.getValueAt(i, j) == null) {
                            continue;
                        }
                        String str_jtab = table1.getValueAt(i, j).toString();
                        if (str_jtab.startsWith(strForSearch)) {
                            table1.setRowSelectionInterval(i, i);
                            table1.scrollRectToVisible(table1.getCellRect(i, j, true));
                            return;
                        }
                    }
                }
            }

        });
        discardChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean User = false;
                NewQuestion Quest = new NewQuestion("<html>The table will be updated to the database state <br> " +
                        " and all unsaved changes will be lost. <br> " +
                        "</b>You agree to continue?</b></html>");
                User = Quest.getStatus();
                if (User) {
                    OpenTable();
                    table1.repaint();
                    UsersDialogs.Message("The table has  been updated!");
                } else {
                    UsersDialogs.Message("The table has not been updated!");
                    return;
                }

            }

        });
        makeSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StructValuesSelection.initMyStructValSel();
                new makeSelection(getColNames(), OpenedTable.this);
            }
        });
    }

    private void OpenTable() {
        CachedRowSet CachRS = null;
        try {
            CachRS = new CachedRowSetImpl();
            CurrentBase.getBase().selectToCachRow(tableName, "*", CachRS);
            System.err.println(CachRS == null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableModel = new DBTableModel(CachRS);
        table1.setModel(tableModel);
        table1.setName(tableName);
        tableUpd();
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table1.getModel());
        table1.setRowSorter(sorter);

    }

    private void closeThisTab() {
        menuWin.getTabbedPane1().remove(menuWin.getTabbedPane1().getSelectedIndex());
        Thread.currentThread().interrupt();
    }

    private void save() {
        try {
            Connection connect=null;
            connect=CurrentBase.getBase().getNewConnect();
            connect.setAutoCommit(false);
            tableModel.getCachRS().acceptChanges(connect);
            connect.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            //Тут лог об ошибке
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }
    }

    private void tableUpd() {
        tableArray = CurrentBase.getBase().select(tableName, "*");
        ArrayList<String> Nulled = CurrentBase.getBase().getNullsName(tableName);
        NoNulled = new ArrayList<String>();
        for (String str : tableArray.get(0)) {
            for (String strNul : Nulled) {
                if (!str.equals(strNul)) {
                    NoNulled.add(str);
                }
            }
        }
        PrimaryKey= CurrentBase.getBase().getPrimaryKey(tableName);
        System.err.println("PRIMARY: "+ PrimaryKey);

        for (String s : NoNulled) {
            System.out.println("!0: " + s);
        }
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public ArrayList<String> getNoNulled() {
        return NoNulled;
    }

    public DBTable getTable1() {
        return table1;
    }

    public String getTableName() {
        return tableName;
    }

    public String getPrimaryKey() {
        return PrimaryKey;
    }

    public ArrayList<String> getColNames() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < table1.getColumnCount(); ++i) {
            result.add(table1.getColumnName(i));
        }
        return result;
    }


    public MenuWindow getMenuWin() {
        return menuWin;
    }
}

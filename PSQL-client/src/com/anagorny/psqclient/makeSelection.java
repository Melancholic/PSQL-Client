package com.anagorny.psqclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sosnov
 * Date: 18.08.13
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
public class makeSelection extends JFrame {
    private JRadioButton allColumnsRadioButton;
    private JRadioButton сolumnsOfTheListRadioButton;
    private JList ColumnsList;
    private JRadioButton allValuesRadioButton;
    private JRadioButton createAPlantRadioButton;
    private JRadioButton aA9RadioButton;
    private JRadioButton a9AARadioButton;
    private JButton addButton;
    private JList ColSortedList;
    private JComboBox ComboBoxOperations;
    private JPanel panel;
    private JPanel OperationsPanel;
    private JComboBox ColNamesComboBox;
    private JButton OKButton;
    private JButton cancelButton;
    private OpenedTable OpenedTbl;
    private String newTblName;

    public makeSelection(ArrayList<String> ColumnNames, OpenedTable table) {
        OpenedTbl = table;
        makeJListColName(ColumnsList, ColumnNames);
        makeJListColName(ColSortedList, ColumnNames);
        mkColNmsCB(ColumnNames);
        add(panel);
        setSize(700, 700);
        setTitle(("Make Selection"));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        OperationsPanel.setLayout(new GridLayout(100, 0));
        while (true) {
            StringBuffer newTableName = new StringBuffer();
            new InputNewTableName(newTableName);
            newTblName = newTableName.toString();
            if (newTblName == null) {
                return;
            }
            if (newTblName.equals("") || newTblName.indexOf(" ") != -1) {
                UsersDialogs.Error("Uncorrect Table`s name!");
            } else {
                break;
            }
        }
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switch (ComboBoxOperations.getSelectedItem().toString()) {
                    case ">": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel(">"),
                                        new JTextField(),
                                        null,
                                        null,
                                        null,
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "<": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("<"),
                                        new JTextField(),
                                        null,
                                        null,
                                        null,
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "==": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("=="),
                                        new JTextField(),
                                        null,
                                        null,
                                        new JLabel(";"),
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "!=": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("!="),
                                        new JTextField(),
                                        null,
                                        null,
                                        null,
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;

                    case ">=": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel(">="),
                                        new JTextField(),
                                        null,
                                        null,
                                        null,
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "<=": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("<="),
                                        new JTextField(),
                                        null,
                                        null,
                                        null,
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "IS NULL": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("IS NULL"),
                                        null,
                                        null,
                                        null,
                                        null,
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "IS NOT NULL": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("IS NOT NULL"),
                                        null,
                                        null,
                                        null,
                                        null,
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "IN": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("IN ("),
                                        new JTextField(),
                                        null,
                                        null,
                                        new JLabel(")"),
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "NOT IN": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("NOT IN ("),
                                        new JTextField(),
                                        null,
                                        null,
                                        new JLabel(")"),
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "LIKE": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("LIKE ("),
                                        new JTextField(),
                                        null,
                                        null,
                                        new JLabel(")"),
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "NOT LIKE": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("NOT LIKE ("),
                                        new JTextField(),
                                        null,
                                        null,
                                        new JLabel(")"),
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                    case "BETWEEN": {
                        JPanel a = new JPanel();
                        OperationsPanel.add(a);
                        OperationsPanel.add(Box.createVerticalStrut(2));
                        StructValuesSelection.getMyStructValSel().add(
                                new StructValuesSelection(
                                        ColNamesComboBox.getSelectedItem().toString(),
                                        new JLabel("BETWEEN"),
                                        new JTextField(),
                                        new JLabel("AND"),
                                        new JTextField(),
                                        null,
                                        a));
                        OperationsPanel.revalidate();
                        OperationsPanel.repaint();
                    }
                    break;
                }
            }
        }

        );
        allColumnsRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ColumnsList.setEnabled(false);
            }
        });
        сolumnsOfTheListRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ColumnsList.setEnabled(true);
            }
        });
        allValuesRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ColNamesComboBox.setEnabled(false);
                ComboBoxOperations.setEnabled(false);
                addButton.setEnabled(false);
                OperationsPanel.setVisible(false);
            }
        });
        createAPlantRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ColNamesComboBox.setEnabled(true);
                ComboBoxOperations.setEnabled(true);
                addButton.setEnabled(true);
                OperationsPanel.setVisible(true);
            }
        });
        aA9RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ColSortedList.setEnabled(true);
            }
        });
        a9AARadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ColSortedList.setEnabled(true);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UsersDialogs.Message("Selection canceled!");
                makeSelection.this.dispose();

            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!CorrectInputData()) {
                    return;
                }
                if (createAPlantRadioButton.isSelected() && StructValuesSelection.getMyStructValSel().size() == 0) {
                    UsersDialogs.Error("Error:Method is selected \"Create a plant\", but did not create any rules! ", "Error!");
                    return;
                }
                String sql;
                sql = "SELECT";
                if (allColumnsRadioButton.isSelected()) {
                    sql += " * ";
                } else {
                    if (сolumnsOfTheListRadioButton.isSelected()) {
                        ArrayList<String> tmp = new ArrayList<String>(ColumnsList.getSelectedValuesList());
                        for (String i : tmp) {
                            sql += " " + i;
                            if (tmp.indexOf(i) != tmp.size() - 1) {
                                sql += ", ";
                            }
                        }
                    }
                }
                sql += " FROM " + OpenedTbl.getTableName();
                if (StructValuesSelection.getMyStructValSel().size() != 0) {
                    sql += " WHERE ";
                    for (int i = 0; i < StructValuesSelection.getMyStructValSel().size() - 1; ++i) {
                        sql += StructValuesSelection.getMyStructValSel().get(i).getStr() + " AND ";

                    }
                    sql += " " + StructValuesSelection.getMyStructValSel().get(StructValuesSelection.getMyStructValSel().size() - 1).getStr();
                }
                if (aA9RadioButton.isSelected()) {
                    sql += " ORDER BY " + ColSortedList.getSelectedValue().toString();
                } else {
                    if (a9AARadioButton.isSelected()) {
                        sql += " ORDER BY " + ColSortedList.getSelectedValue().toString() + " DESC";
                    }
                }
                try {
                    CurrentBase.getBase().newRequest("CREATE TABLE " + newTblName.toString()
                            + " AS ( " + sql + " );");
                } catch (Exception e) {
                    UsersDialogs.Error(e.getMessage());
                    return;
                }
                Thread newThread = new Thread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new OpenedTable(OpenedTbl.getMenuWin(), newTblName.toString());
                    }
                }));
                newThread.start();
                OpenedTbl.getMenuWin().makeTablesList();
                UsersDialogs.Message("Selection is complete!");
                makeSelection.this.dispose();
                System.err.println("SQL: " + sql);
            }

        }

        );
    }

    private boolean CorrectInputData() {
        if (allColumnsRadioButton.isSelected() == сolumnsOfTheListRadioButton.isSelected()) {
            UsersDialogs.Error("Internal error: unable to determine the input columns (Section 1).");
            return false;
        }
        if (сolumnsOfTheListRadioButton.isSelected() && ColumnsList.getSelectedIndex() == -1) {
            UsersDialogs.Error("Error: Please select at least one column for the sample (Section 1)," +
                    " or change the method of selecting the speakers to \"All columns\".");
            return false;
        }

        if (aA9RadioButton.isSelected() == a9AARadioButton.isSelected()) {
            UsersDialogs.Error(" Internal error: unable to determine sorting (Section 3).");
            return false;
        }
        if (ColSortedList.getSelectedIndex() == -1) {
            UsersDialogs.Error("Error: select a single column on which to sort (Section 3).");
            return false;
        }

        if (allValuesRadioButton.isSelected() == createAPlantRadioButton.isSelected()) {
            UsersDialogs.Error("Internal error: unable to determine the value for the sample (Ыection 2).");
            return false;
        }
        if (createAPlantRadioButton.isSelected() && StructValuesSelection.getMyStructValSel().size() == 0) {
            UsersDialogs.Error("Error: You must add at least one rule (Section 2).");
            return false;
        }
        if (!StructValuesSelection.testInputData()) {
            UsersDialogs.Error("Error: please fill all the fields (Section 2).");
            return false;
        }
        return true;
    }

    private void makeJListColName(JList colSortedList, ArrayList<String> columnNames) {
        DefaultListModel<String> model = new DefaultListModel<String>();
        for (String str : columnNames) {
            model.addElement(str);
        }
        colSortedList.setModel(model);
    }

    private void mkColNmsCB(ArrayList<String> columnNames) {
        for (String i : columnNames) {
            ColNamesComboBox.addItem(i);
        }

    }
}

class StructValuesSelection {
    static private ArrayList<StructValuesSelection> MyStructValSel;
    private JLabel Label1;
    private JTextField Text1;
    private JLabel Label2;
    private JTextField Text2;
    private JLabel Label3;
    private JButton ButtonDel;
    private String ColName;


    public StructValuesSelection(String colName,
                                 JLabel label1,
                                 JTextField text1,
                                 JLabel label2,
                                 JTextField text2,
                                 JLabel label3,
                                 final Container panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        ColName = colName;

        Label1 = label1;
        if (Label1 != null) {
            Label1.setText(ColName + "  " + Label1.getText());
            panel.add(Label1);
            panel.add(Box.createHorizontalStrut(10));
        }
        Text1 = text1;
        if (Text1 != null) {
            Text1.setEnabled(true);
            Text1.setMinimumSize(new Dimension(40, 40));
            Text1.setColumns(5);
            Text1.setVisible(true);
            text1.setToolTipText("Format: number: 123; \n real number: 12.3; \n text: \'text\'; \n date: 'M/D/Y';");
            panel.add(Text1);
            panel.add(Box.createHorizontalStrut(10));
        }
        Label2 = label2;
        if (Label2 != null) {
            panel.add(Label2);
            panel.add(Box.createHorizontalStrut(10));
        }
        Text2 = text2;
        if (Text2 != null) {
            Text2.setEnabled(true);
            Text2.setColumns(5);
            Text2.setVisible(true);
            text2.setToolTipText("Format: number: 123; \n real number: 12.3; \n text: \'text\'; \n date: 'M/D/Y';");
            panel.add(Text2);
            panel.add(Box.createHorizontalStrut(10));
        }
        Label3 = label3;
        if (Label3 != null) {
            panel.add(Label3);
            panel.add(Box.createHorizontalStrut(10));
        }
        ButtonDel = new JButton("✘");
        ButtonDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Label1 != null) {
                    ButtonDel.getParent().remove(Label1);
                }
                if (Text1 != null) {
                    ButtonDel.getParent().remove(Text1);
                }
                if (Label2 != null) {
                    ButtonDel.getParent().remove(Label2);
                }
                if (Text2 != null) {
                    ButtonDel.getParent().remove(Text2);
                }
                if (Label3 != null) {
                    ButtonDel.getParent().remove(Label3);
                }

                ButtonDel.getParent().remove(ButtonDel);
                JPanel parrent = (JPanel) panel.getParent();
                parrent.remove(panel);
                parrent.revalidate();
                parrent.repaint();
                parrent.updateUI();
                parrent.validate();
                System.err.println("1! ind: " + StructValuesSelection.getMyStructValSel().indexOf(StructValuesSelection.this) + "size: " + StructValuesSelection.getMyStructValSel().size());
                StructValuesSelection.getMyStructValSel().remove(StructValuesSelection.getMyStructValSel().indexOf(StructValuesSelection.this));
                System.err.println("2! ind: " + StructValuesSelection.getMyStructValSel().indexOf(StructValuesSelection.this) + "size: " + StructValuesSelection.getMyStructValSel().size());
            }
        }
        );

        panel.add(ButtonDel);
        panel.revalidate();
        panel.repaint();
    }

    static ArrayList<StructValuesSelection> getMyStructValSel() {
        return MyStructValSel;
    }

    public static void initMyStructValSel() {
        MyStructValSel = new ArrayList<>();

    }

    static public boolean testInputData() {
        for (StructValuesSelection i : MyStructValSel) {
            if (i.getText1() != null && i.getText1().getText().trim().length() == 0) {
                return false;
            }
            if (i.getText2() != null && i.getText2().getText().trim().length() == 0) {
                return false;
            }
        }
        return true;
    }

    JLabel getLabel1() {
        return Label1;
    }

    JTextField getText1() {
        return Text1;
    }

    JLabel getLabel2() {
        return Label2;
    }

    JTextField getText2() {
        return Text2;
    }

    JLabel getLabel3() {
        return Label3;
    }

    String getStr() {
        String result;
        result = " " +
                ((Label1 != null) ? Label1.getText() : "") + " " +
                ((Text1 != null) ? Text1.getText() : "") + " " +
                ((Label2 != null) ? Label2.getText() : "") + " " +
                ((Text2 != null) ? Text2.getText() : "") + " " +
                ((Label3 != null) ? Label3.getText() : "") + " ";
        return result;
    }
}


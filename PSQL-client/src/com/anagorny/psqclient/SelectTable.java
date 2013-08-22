package com.anagorny.psqclient;

import javax.swing.*;
import java.awt.event.*;

public class SelectTable extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList list1;
    private StringBuffer nameTable;

    public SelectTable(StringBuffer str, String name) {
        this.nameTable = str;
        this.setName(name);
        DefaultListModel model = (DefaultListModel) list1.getModel();
        for (String i : CurrentBase.getBase().getTableList()) {
            model.addElement(i);
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void onOK() {
        if (list1.getSelectedValue() != null) {
            nameTable.append(list1.getSelectedValue());
            dispose();
        } else {
            UsersDialogs.Error("Select table in the tables list!");
            return;
        }
    }

    private void onCancel() {
        UsersDialogs.Message("Table not opening...");
        nameTable = null;
        dispose();
    }
}

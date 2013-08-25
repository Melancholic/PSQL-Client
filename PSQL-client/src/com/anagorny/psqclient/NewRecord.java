package com.anagorny.psqclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NewRecord extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel CompPanel;
    private DBTable table1;
    private JPanel LabelsPanel;
    private DBTable bigTbl;
    private ArrayList<JLabel> Labels;

    NewRecord(DBTableModelForNewRecord tblModel,  OpenedTable OTable) {
        Labels= new ArrayList<>();
       // LabelsPanel= new JPanel();
        LabelsPanel.setLayout(new GridLayout(4,4));
        Labels.add(new JLabel("Primary Key: "+OTable.getPrimaryKey()));
        for(String i : OTable.getNoNulled()){
             Labels.add(new JLabel("Not Null: " + i));
        }
        for(JLabel label : Labels){
            LabelsPanel.add(label);
        }
        setPreferredSize(new Dimension(500, 220));
        setSize(700, 300);
        setResizable(false);
        bigTbl = OTable.getTable1();
        table1.setModel(tblModel);
        table1.setRowHeight(30);
        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);
        CompPanel.setLayout(new GridLayout(3, 2));
        buttonOK.addActionListener(new AddNewLineListenner(this, bigTbl));
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        this.pack();
        this.setVisible(true);
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
    }

    public DBTableModelForNewRecord getTblModel() {
        return (DBTableModelForNewRecord) table1.getModel();
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}

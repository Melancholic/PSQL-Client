package com.anagorny.psqclient;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.ArrayList;

public class MkTbl extends JDialog {
    private static boolean primary;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea enterColumnName;
    private JCheckBox PRIMARYKEYCheckBox;
    private JCheckBox NOTNULLCheckBox;
    private JList list1;
    private ArrayList<String> lines;
    private String Type;
    private String Primary;
    private String Name;
    private String Null;

    public MkTbl(ArrayList<String> lines) {
        this.lines = lines;
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
        setLocationRelativeTo(null);
        this.setVisible(true);

        ChangeListener chListener = new ChangeListener() {

            @Override

            public void stateChanged(ChangeEvent changeEvent) {
                AbstractButton absB = (AbstractButton) changeEvent.getSource();
                ButtonModel bMod = absB.getModel();
                boolean armed = bMod.isArmed();
                boolean pressed = bMod.isPressed();
                boolean selected = bMod.isSelected();
                if (bMod.isSelected() && !primary) {
                    primary = true;
                    Primary = "PRIMARY KEY";
                    System.out.println("OK!");
                } else {
                    Primary = "";
                    PRIMARYKEYCheckBox.setEnabled(false);
                }
                System.out.println("Changed: " + armed + "/" + pressed + "/" + selected);
            }

        };

        PRIMARYKEYCheckBox.addChangeListener(chListener);

        PRIMARYKEYCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Yello:");
            }
        });
    }

    private void onOK() {
        Name = enterColumnName.getText();
        if (Name == null) {
            UsersDialogs.Error("Error: Input name!");
            return;
        }
        System.out.println(Name.indexOf(" "));
        if (Name.indexOf(" ") != -1 | Name.indexOf("-") != -1 | Name.equals("Enter Column Name")) {
            UsersDialogs.Error("without space and upper case" +
                    "\nWARNING: not use special SQL key  in name your table!" +
                    "\n exmp: NAME", "Error: Input correct name!");
            return;
        }
        if (list1.getSelectedValue() == null) {
            UsersDialogs.Error("Error: Select type!");
            return;
        } else {
            Type = list1.getSelectedValue().toString();
        }
        Null = (NOTNULLCheckBox.isSelected()) ? "NOT NULL" : "NULL";
        if (!primary) {
            Primary = (PRIMARYKEYCheckBox.isSelected()) ? "PRIMARY KEY" : "";
            primary = true;
            Null = "";
        } else {
            Primary = "";
        }
        lines.add(Name + "  " + Type.toUpperCase() + "  " + Primary + "  " + Null);
        dispose();
        new MkTbl(lines);
    }

    private void onCancel() {
        dispose();
    }


}

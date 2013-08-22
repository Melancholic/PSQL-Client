package com.anagorny.psqclient;

import javax.swing.*;
import java.awt.event.*;

public class crPrimKey extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private StringBuffer idRec;

    public crPrimKey(StringBuffer Text) {
        idRec = Text;
        setContentPane(contentPane);
        setTitle("Create a Primary Key");
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
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK() {
        if (textField1.getText().equals("")) {
            UsersDialogs.Error("Input Primatry Key's name!");
            return;
        }
        if (textField1.getText().indexOf(" ") != -1 | textField1.getText().indexOf("-") != -1) {
            UsersDialogs.Error("Input correct Primatry Key's name!");
            return;
        }
        idRec.append(textField1.getText());
        dispose();
    }

    private void onCancel() {
        idRec = null;
        dispose();
    }

}

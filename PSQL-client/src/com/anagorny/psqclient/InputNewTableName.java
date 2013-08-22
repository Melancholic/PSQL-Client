package com.anagorny.psqclient;

import javax.swing.*;
import java.awt.event.*;

public class InputNewTableName extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private StringBuffer text;

    public InputNewTableName(StringBuffer Text) {
        setTitle("Input name for new tables");
        text = Text;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().equals("")) {
                    UsersDialogs.Error("Input table name!");
                    return;
                }
                if (textField1.getText().indexOf(" ") != -1 | textField1.getText().indexOf("-") != -1) {
                    UsersDialogs.Error("Input correct table name!");
                    return;
                }
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        this.pack();
        setLocationRelativeTo(null);
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
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    private void onOK() {
        text.append(textField1.getText());
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}

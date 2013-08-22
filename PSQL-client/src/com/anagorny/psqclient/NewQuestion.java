package com.anagorny.psqclient;

import javax.swing.*;
import java.awt.event.*;

public class NewQuestion extends JDialog {
    public JLabel TextMsg;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private boolean UserAnsw;

    public NewQuestion(String Str) {
        TextMsg.setText(Str);
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
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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

    private void onOK() {
        this.UserAnsw = true;
        dispose();
    }

    private void onCancel() {
        this.UserAnsw = false;
        dispose();
    }

    public boolean getStatus() {
        return UserAnsw;
    }
}

package com.anagorny.psqclient;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;

public class Login extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField UsrName;
    private JPasswordField Passwd;
    private JTextField DBName;
    private JTextField ServerName;
    private JList DBDrivers;
    private JCheckBox saveMeCheckBox;
    private MenuWindow WinMenu;

    public Login(MenuWindow mw) {
        WinMenu = mw;
        DBDrivers.setSelectedIndex(0);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addWindowListener(new CloseListener());
        isFileExist();
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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

        saveMeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //To change body of implemented methods use File | Settings | File Templates.


            }
        });
    }

    private void onOK() {
        final String pass = new String(Passwd.getPassword());
        if (DBDrivers.isSelectionEmpty()) {
            UsersDialogs.Error("Please, select driver!");
            return;
        }
        if (ServerName.getText().equals("")) {
            UsersDialogs.Error("Please, input server name/adress!");
            return;
        }
        if (DBName.getText().equals("")) {
            UsersDialogs.Error("Please, input database name!");
            return;
        }
        if (UsrName.getText().equals("")) {
            UsersDialogs.Error("Please, input user name!");
            return;
        }
        if (pass.equals("")) {
            UsersDialogs.Error("Please, input password!");
            return;
        }

        try {
            WinMenu.makeCurBaseInst(DBDrivers.getSelectedValue().toString(), ServerName.getText(), DBName.getText(), UsrName.getText(), pass);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            UsersDialogs.Error(e.getMessage() + "\nERR CODE: " + e.getErrorCode(), "Error from server: ");
            return;
        }
        File file = new File("./.settings");
        if (!saveMeCheckBox.isSelected()) {
            if (file.exists()) {
                file.delete();
            }

        } else {
            if (file.exists()) {
                file.delete();
            }
            try {
                DataOutputStream fileOut = new DataOutputStream(new FileOutputStream(file));
                ObjectOutputStream oos = new ObjectOutputStream(fileOut);
                LoginData LogData = new LoginData();

                LogData.setUrl(ServerName.getText());
                LogData.setDbDrivNum(DBDrivers.getSelectedIndex());
                LogData.setDBName(DBName.getText());
                LogData.setUsrName(UsrName.getText());
                LogData.setUsrPassw(new String(Passwd.getPassword()));
                LogData.setSaveStatus(saveMeCheckBox.isSelected());
                oos.writeObject(LogData);
                oos.flush();
                oos.close();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
                //Тут лог об ошибке
                System.err.println(e.getClass().getName() + " : " + e.getMessage());
                UsersDialogs.Error(e.getClass().getName() + " : " + e.getMessage());
            }
        }

        dispose();
    }

    private void onCancel() {
        dispose();
        WinMenu.closeThis();
    }

    public void isFileExist() {
        File file = new File("./.settings");
        if (file.exists()) {
            try {
                DataInputStream fis = new DataInputStream(new FileInputStream(file));
                ObjectInputStream oin = new ObjectInputStream(fis);
                LoginData LogData = (LoginData) oin.readObject();
                ServerName.setText(LogData.getUrl());
                DBDrivers.setSelectedIndex(LogData.getDbDrivNum());
                DBName.setText(LogData.getDBName());
                UsrName.setText(LogData.getUsrName());
                Passwd.setText(LogData.getUsrPassw());
                saveMeCheckBox.setSelected(LogData.isSaveStatus());
            } catch (Exception e) {
                e.printStackTrace();
                //Тут лог об ошибке
                System.err.println(e.getClass().getName() + " : " + e.getMessage());
                UsersDialogs.Error(e.getClass().getName() + " : " + e.getMessage());
            }
        }

    }

    private class CloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent event) {
            onCancel();
        }
    }

}

/*
 Задача на будущее: добавить шифрование
 */
class LoginData implements Serializable {

    private String url;
    private int DbDrivNum;
    private String DBName;
    private String usrName;
    private String usrPassw;
    private boolean saveStatus;

    private void writeObject(java.io.ObjectOutputStream stream)
            throws java.io.IOException {
        // "Encrypt"/obscure the sensitive data

        stream.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws java.io.IOException, ClassNotFoundException {
        stream.defaultReadObject();

        // "Decrypt"/de-obscure the sensitive data

    }

    String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    int getDbDrivNum() {
        return DbDrivNum;
    }

    void setDbDrivNum(int dbDrivNum) {
        DbDrivNum = dbDrivNum;
    }

    String getDBName() {
        return DBName;
    }

    void setDBName(String DBName) {
        this.DBName = DBName;
    }

    String getUsrName() {
        return usrName;
    }

    void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    String getUsrPassw() {
        return usrPassw;
    }

    void setUsrPassw(String usrPassw) {
        this.usrPassw = usrPassw;
    }

    boolean isSaveStatus() {
        return saveStatus;
    }

    void setSaveStatus(boolean saveStatus) {
        this.saveStatus = saveStatus;
    }
}


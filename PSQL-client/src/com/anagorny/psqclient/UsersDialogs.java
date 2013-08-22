package com.anagorny.psqclient;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: sosnov
 * Date: 23.07.13
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
public class UsersDialogs {
    public static void Error(String msg) {
        JOptionPane.showMessageDialog(new JFrame(),
                msg, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public static void Error(String name, String msg) {
        JOptionPane.showMessageDialog(new JFrame(), name, msg,
                JOptionPane.ERROR_MESSAGE);
    }

    public static void Warning(String msg) {
        JOptionPane.showMessageDialog(new JFrame(),
                msg, "Warning!", JOptionPane.WARNING_MESSAGE);
    }

    public static void Warning(String name, String msg) {
        JOptionPane.showMessageDialog(new JFrame(),
                msg, name, JOptionPane.WARNING_MESSAGE);
    }

    public static void Message(String msg) {
        JOptionPane.showMessageDialog(new JFrame(), msg);
    }
}

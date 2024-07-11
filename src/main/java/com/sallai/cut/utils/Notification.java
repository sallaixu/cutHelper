package com.sallai.cut.utils;

import java.awt.*;

/**
 * @ClassName Notification
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 11:01 PM 7/11/2024
 * @Version 1.0
 **/
public class Notification {
    public volatile static TrayIcon trayIcon = null;
    public static void showNotification(String title, String message) {
        if (!SystemTray.isSupported()) {
            System.out.println("System tray is not supported.");
            return;
        }
        if(trayIcon == null) {
            SystemTray tray = SystemTray.getSystemTray();
            Image icon = Toolkit.getDefaultToolkit().getImage(Notification.class.getResource("/img/cut.png"));
            trayIcon = new TrayIcon(icon, "CutHelper");
            trayIcon.setImageAutoSize(true);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("TrayIcon could not be added.");
            }
        }
        trayIcon.displayMessage(title, message, TrayIcon.MessageType.NONE);
    }
}

package com.sallai.cut.componet;

import com.sallai.cut.gui.MainGui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HeaderJPanel extends JPanel {

    public HeaderJPanel() {
        super();
        BoxLayout boxLayout = new BoxLayout(this,BoxLayout.X_AXIS);
        this.setLayout(boxLayout);
        JLabel authorName = new JLabel("author: sallai");
        JLabel authorEmail = new JLabel("email: sallai@aliyun.com");
        JCheckBox cbTop = new JCheckBox("置顶");
        cbTop.setSelected(true);
        cbTop.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                MainGui.cutHelper.setAlwaysOnTop(cbTop.isSelected());
            }
        });
        this.add(cbTop);
        this.add(authorName);
        this.add(authorEmail);
    }
}

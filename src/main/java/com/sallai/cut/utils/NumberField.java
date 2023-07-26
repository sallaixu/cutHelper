package com.sallai.cut.utils;

/**
 * @description: $
 * @author: sallai
 * @time: 2022年2月27日 0027 上午 11:09:54 秒
 */

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class NumberField extends PlainDocument {
    public NumberField() {
    }

    @Override
    public void insertString(int var1, String var2, AttributeSet var3) throws BadLocationException {
        if (this.isNumeric(var2)) {
            super.insertString(var1, var2, var3);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }

    }

    private boolean isNumeric(String var1) {
        try {
            Long.valueOf(var1);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }
}

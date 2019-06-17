package com.comntool.ext;

import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

public class ComnMenu {

	
	/**
	 * 深度设置输入框的鼠标右键菜单。复制，粘贴，剪切
	 * @param comps
	 */
	public static void addMenusDeep(Component ...comps) {
		for(Component comp : comps) {
			if(comp instanceof JPanel) {
				JPanel jp = (JPanel)comp;
				addMenusDeep(jp.getComponents());
			}else if(comp instanceof JScrollPane) {
				JScrollPane cont = (JScrollPane)comp;
				Component subComp = cont.getViewport().getView();
				if(subComp instanceof JTextComponent) {
					addMenu((JTextComponent)subComp);
				} else {
					addMenusDeep(subComp);
				}
			}else if(comp instanceof Box) {
				Box cont = (Box)comp;
				addMenusDeep(cont.getComponents());
			}else if(comp instanceof JTextComponent) {
				addMenu((JTextComponent)comp);
			}
		}
	}
	public static void addMenus(JTextComponent ...comps) {
		for(JTextComponent comp : comps) {
			addMenu(comp);
		}
	}
	
	// 参考： https://897457487.iteye.com/blog/1707014
	public static void addMenu(JTextComponent comp) {
		final JPopupMenu pop = new JPopupMenu(); // 弹出菜单  
	    final JMenuItem copy = new JMenuItem("复制"), paste = new JMenuItem("粘贴"), 
	    		cut = new JMenuItem("剪切"); // 三个功能菜单  
	    // final String copyStr= copy.getText(), pasteStr = paste.getText(), cutStr= cut.getText();
	    comp.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					pop.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
		        if (e.getButton() == MouseEvent.BUTTON3) {  
		            copy.setEnabled(isCanCopy(comp));  
		            paste.setEnabled(isClipboardString(comp));  
		            cut.setEnabled(isCanCopy(comp));  
		            pop.show(comp, e.getX(), e.getY());  
		        }  
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});  
	    
        pop.add(copy);  
        pop.add(paste);  
        pop.add(cut);  
        copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));  
        paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));  
        cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));  
        copy.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
            	comp.copy();
            }  
        });  
        paste.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
            	comp.paste(); 
            }  
        });  
        cut.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
            	comp.cut();
            }  
        });  
        comp.add(pop);  
	}
	
	 /** 
     * 菜单动作 
     *  
     * @param e 
     */  
    public static void action(JTextComponent comp, ActionEvent e, String copyStr, 
    		String pasteStr, String cutStr) {  
        String str = e.getActionCommand();  
        if (str.equals(copyStr)) { // 复制  
        	comp.copy();  
        } else if (str.equals(pasteStr)) { // 粘贴  
        	comp.paste();  
        } else if (str.equals(cutStr)) { // 剪切  
        	comp.cut();  
        }  
    }  
  
    /** 
     * 剪切板中是否有文本数据可供粘贴 
     *  
     * @return true为有文本数据 
     */  
    public static boolean isClipboardString(JTextComponent comp) {  
        boolean b = false;  
        Clipboard clipboard = comp.getToolkit().getSystemClipboard();  
        Transferable content = clipboard.getContents(comp);  
        try {  
            if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {  
                b = true;  
            }  
        } catch (Exception e) {  
        }  
        return b;  
    }  
  
    /** 
     * 文本组件中是否具备复制的条件 
     *  
     * @return true为具备 
     */  
    public static boolean isCanCopy(JTextComponent comp) {  
        boolean b = false;  
        int start = comp.getSelectionStart();  
        int end = comp.getSelectionEnd();  
        if (start != end)  
            b = true;  
        return b;  
    }  
}

package com.comntool.ext;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.JTextComponent;

import com.comntool.view.BottomView;

public class ComnViewSet {
	public static Properties prop = new Properties();

	/**
	 * 获取组件的最下方坐标
	 * @param comp 组件
	 * @param offset 偏移量
	 * @return
	 */
	public static int getBottomY(Component comp, Integer offset) {
		if(comp == null) {
			return 0;
		}
		return comp.getY() +comp.getHeight() + (offset== null ? 0 : offset);
	}
	
	/**
	 * JTextArea设置换行
	 * @param comps
	 */
	public static void taLineWrap(JTextArea ...comps) {
		for(JTextArea comp : comps) {
			comp.setLineWrap(true);        //激活自动换行功能 
			comp.setWrapStyleWord(true);            // 激活断行不断字功能
		}
		
	}
	
	public static void setBoxTFW(JTextField comp, int w, int h) {
		comp.setColumns(w);
		comp.setMaximumSize(new Dimension(w, h));// setColumns和setMaximumSize要同时设置
	}
	
	/**
	 * 批量设置字体
	 * @param font
	 * @param comps
	 */
	public static void setFont(Font font, JComponent ...comps) {
		for(JComponent comp : comps) {
			comp.setFont(font);
		}
	}
	

    static Font font=new Font("宋体", Font.BOLD, 16);
	public static void setFontDefault2(JComponent ...comps) {
		for(JComponent comp : comps) {
			comp.setFont(font);
		}
	}

	// 初始化字体
	public static void initFont() {
		Font font = new Font("Dialog", Font.PLAIN, 16);
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, font);
            }
        }
	}

	public static void setFontDefaultDeep(Component ...comps) {
		for(Component comp : comps) {
			if(comp instanceof JPanel) {
				JPanel jp = (JPanel)comp;
				setFontDefaultDeep(jp.getComponents());
			}
			//comp.setFont(font);
		}
	}
	
	public static void addComp(Container bottom, Component ...comps) {
		for(Component comp : comps) {
			bottom.add(comp);
		}
	}
	
	
	// 参考： https://897457487.iteye.com/blog/1707014
    private static void addPopupDefault(JComponent component) {  
    	final JPopupMenu popup = new JPopupMenu();
        component.addMouseListener(new MouseAdapter() {  
            public void mousePressed(MouseEvent e) {  
                if (e.isPopupTrigger()) {  
                    showMenu(e);  
                }  
            }  
  
            public void mouseReleased(MouseEvent e) {  
                if (e.isPopupTrigger()) {  
                    showMenu(e);  
                }  
            }  
            private void showMenu(MouseEvent e) {  
                popup.show(e.getComponent(), e.getX(), e.getY());  
            }  
        });  
    }

    /**
     * 
     * @param comps
     */
    public static void addPopupDefault(JComponent ...comps) {
    	for(JComponent comp : comps) {
    		addPopupDefault(comp);
		}
    }
    

    /**
     * 给输入框设置选择的文件或者文件夹
     * @param jPanel
     * @param fileChooser
     * @param mode 例： JFileChooser.FILES_ONLY
     * @param outText
     */
    public static void choseFile2text(JPanel jPanel, int mode, 
    		JTextComponent outText) {
    	 KFileChooser fileChooser = new KFileChooser();
    	 choseFile2text(jPanel, fileChooser, mode, outText);
    }
    /**
     * 给输入框设置选择的文件或者文件夹
     * @param jPanel
     * @param fileChooser
     * @param mode 例： JFileChooser.FILES_ONLY
     * @param outText
     */
    public static void choseFile2text(JPanel jPanel, KFileChooser fileChooser, int mode, 
    		JTextComponent outText) {
    	int result = 0;
    	String path = null;
    	FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
    	BottomView.setLogStatic(fsv.getHomeDirectory().getAbsolutePath()); //得到桌面路径
    	fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
    	fileChooser.setDialogTitle("请选择文件...");
    	fileChooser.setApproveButtonText("确定");
    	fileChooser.setFileSelectionMode(mode);
    	result = fileChooser.showOpenDialog(jPanel);
		if (JFileChooser.APPROVE_OPTION == result) {
			path = fileChooser.getSelectedFile().getPath();
			outText.setText(path);
			BottomView.setLogStatic("choose path=" + path);
		}
    }
}

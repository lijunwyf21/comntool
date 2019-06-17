package com.comntool.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.comntool.ext.ComnImg;
import com.comntool.ext.JClosableTabbedPane;

/**
 * 头部
 * 参考： https://blog.csdn.net/hwj528/article/details/53642588 
 * @author lijun
 *
 */
public class UpView extends JPanel {
	private static final long serialVersionUID = 1L;
    private JClosableTabbedPane jTabbedpane = new JClosableTabbedPane();// 存放选项卡的组件  
	JPanel funcPanel = new JPanel();
	
    public UpView() {
        // 参考： https://blog.csdn.net/qq_30917141/article/details/81988256 
    	try {
    		setPreferredSize(new Dimension(new Double(getSize().getWidth()).intValue(), 50));
    		initComponents();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    private void layoutComponents(JPanel funcPanel) {  
        int i = 0;  
        

        // 第一个标签下的JPanel  
        // jTabbedpane.addTab(tabNames[i++],icon,creatComponent(),"first");//加入第一个页面  
        JPanel logPanel = new JPanel();
        JScrollPane sp = new JScrollPane(funcPanel);
        logPanel.add(sp);
        Font font=new Font("宋体", Font.PLAIN, 18);
        jTabbedpane.addTab("日志", null, sp, "日志");// 加入第一个页面  
        jTabbedpane.setMnemonicAt(0, KeyEvent.VK_0);// 设置第一个位置的快捷键为0  
        

    }
    

    private void initComponents() {  
        JTextArea readme= new JTextArea();
        readme.setText("在菜单中选择功能");
        // 第一个标签下的JPanel  
        // jTabbedpane.addTab(tabNames[i++],icon,creatComponent(),"first");//加入第一个页面  
        JPanel logPanel = new JPanel();
        JScrollPane sp = new JScrollPane(readme);
        logPanel.add(sp);
        //Font font=new Font("宋体", Font.PLAIN, 18);
		Font font = new Font("宋体", Font.BOLD, 16);
        jTabbedpane.setFont(font);
        jTabbedpane.addTab("说明", sp);// 加入第一个页面  
        jTabbedpane.setMnemonicAt(0, KeyEvent.VK_0);// 设置第一个位置的快捷键为0
        setLayout(new GridLayout(1, 1));  
        add(jTabbedpane);

    }
    

    public void addTabPanel(JPanel panel, String tabName) { 
        jTabbedpane.addTab(tabName,  panel);// 加入一个页面  
        jTabbedpane.setSelectedComponent(panel);
        // jTabbedpane.setMnemonicAt(1, KeyEvent.VK_1);// 设置第一个位置的快捷键为0
        setLayout(new GridLayout(1, 1));  
        add(jTabbedpane);
    }

}
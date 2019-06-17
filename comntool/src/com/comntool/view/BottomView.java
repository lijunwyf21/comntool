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

/**
 * 底部
 * 参考： https://blog.csdn.net/hwj528/article/details/53642588 
 * @author lijun
 *
 */
public class BottomView extends JPanel {
	private static final long serialVersionUID = 1L;
	public static BottomView defaultBV= null;
    private JTabbedPane jTabbedpane = new JTabbedPane();// 存放选项卡的组件  
    public JTextArea logTA= new JTextArea();
    public JTextArea sysLogTA= new JTextArea();
    ImageIcon icon = null;// createImageIcon("com/comntool/resourse/demo.jpg");
    static int logLeng = 100000;
    
    public BottomView() {
        // 参考： https://blog.csdn.net/qq_30917141/article/details/81988256 
    	try {
    		setPreferredSize(new Dimension(new Double(getSize().getWidth()).intValue(), 50));
	        layoutComponents();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    private void layoutComponents() {  
        int i = 0;  
        // 第一个标签下的JPanel  
        // jTabbedpane.addTab(tabNames[i++],icon,creatComponent(),"first");//加入第一个页面  
        JPanel logPanel = new JPanel();
        JScrollPane sp = new JScrollPane(logTA);
        logPanel.add(sp);
        Font font=new Font("宋体", Font.PLAIN, 18);
        logTA.setSelectedTextColor(new Color(0xaa, 0x00, 0x00));
        logTA.setDisabledTextColor(new Color(0x00, 0x00, 0xaa));
        logTA.setCaretColor(new Color(0x00, 0xaa, 0x00));
        logTA.setSelectionColor(new Color(0x00, 0xaa, 0xaa));
        logTA.setFont(font);
        // sp.setViewportView(logTA);
        logTA.setText("1111111111111112222222222222222222222222"
        		+ "\n1234234324\n1234234324\n1234234324\n1234234324"
        		+ "\n1234234324\n\n\n\n1234234324\n12\n12\n12"
        		+ "\n12\n12\n12\n12\n1234234324");
        logTA.setText("");
        jTabbedpane.addTab("日志", icon, sp, "日志");// 加入第一个页面  
        jTabbedpane.setMnemonicAt(0, KeyEvent.VK_0);// 设置第一个位置的快捷键为0  
        
        // 第二个标签下的JPanel  
        // JPanel jpanelOutputParamsTemp = new JPanel();  
        JScrollPane sysLogSP = new JScrollPane(sysLogTA);
        jTabbedpane.addTab("系统日志", icon, sysLogSP, "系统日志");// 加入第一个页面  
        jTabbedpane.setMnemonicAt(1, KeyEvent.VK_1);// 设置快捷键为1  
        setLayout(new GridLayout(1, 1));  
        add(jTabbedpane);  


    }
    

    public static void setLogStatic(String log) {
    	if(defaultBV==null) {
    		return ;
    	}
    	JTextArea defaultLog = defaultBV.logTA;
    	String logAll = defaultLog.getText()+"\n"+log;
    	if(logAll.length() > logLeng) {
    		logAll = logAll.substring(logAll.length() - logLeng, logAll.length());
    	}
    	defaultLog.setText(logAll);
    }
    public static void setSysLogStatic(String log) {
    	if(defaultBV==null) {
    		return ;
    	}
    	JTextArea sysLog = defaultBV.sysLogTA;
    	String logAll = sysLog.getText()+"\n"+log;
    	if(logAll.length() > logLeng) {
    		logAll = logAll.substring(logAll.length() - logLeng, logAll.length());
    	}
    	sysLog.setText(logAll);
    }
    
    public void setLog(String log) {
    	String logAll = logTA.getText()+"\n"+log;
    	if(logAll.length() > logLeng) {
    		logAll = logAll.substring(logAll.length() - logLeng, logAll.length());
    	}
    	logTA.setText(logAll);
    }
    
    public ImageIcon createImageIcon(String path) { 
        URL url = BottomView.class.getClassLoader().getResource(path);  
        if (url == null) {  
            System.out.println("the image " + path + " is not exist!");  
            return null;  
        }  
        return new ImageIcon(url);  
    }  

    private JPanel createTab1Panel(){
        JPanel panel = new JPanel(false);
        panel.setLayout(null);  // new GridLayout()

        JLabel importDir = new JLabel("Import Folder:");

        JTextField textImportDir = new JTextField(15);

        JButton btnSubmit = new JButton("Submit");

        panel.add(importDir);
        panel.add(textImportDir);
        panel.add(btnSubmit);

        importDir.setBounds(100, 75, 100, 25);
        textImportDir.setBounds(200, 75, 200, 25);
        btnSubmit.setBounds(100, 125, 80, 25);

        return panel;
    }

}
package com.comntool.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.comntool.ext.ComnMenu;
import com.comntool.ext.ComnViewSet;
import com.comntool.ext.KFileChooser;
import com.comntool.util.utility.Comn;
import com.comntool.util.utility.JavaParseUtil;
import com.comntool.util.utility.bean.DisPair;

/**
 * 错误码java枚举转excel
 * @author justin6
 *
 */
public class Trans2SQLPanel extends JPanel{

	private static final long serialVersionUID = 23231L;
	private JScrollPane scrollPane = new JScrollPane();
    private final JPanel jPanel=new JPanel();

    private final JTextArea excelPathTA = new JTextArea();// 翻译的excel文件
    private JScrollPane excelPathSP = new JScrollPane(excelPathTA);
    private final JComboBox<DisPair> langMarkCB=new JComboBox<>();// 语言（中文，
    private final JComboBox<DisPair> dbSortCB=new JComboBox<>();// 数据库分类。oracle, mysql
    private final JComboBox<DisPair> transSortCB=new JComboBox<>();// 数据库分类。oracle, mysql
    private final JTextArea outputTA = new JTextArea();// 翻译的excel文件
    private JScrollPane outputSP = new JScrollPane(outputTA);
    
	public Trans2SQLPanel() {
		initPanel();
		initProp();
	}
	
	private void initPanel(){
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(jPanel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(scrollPane);

        jPanel.setLayout(null);
		jPanel.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
		int startY = 0;

		JLabel langMarkL = new JLabel("语言标识：");
		String signAlgo = "中文:zh;英文:en";
		for(String str : signAlgo.split(";")) {
			String[] strs = str.split(":");
			langMarkCB.addItem(new DisPair(strs[0], strs[1]));
		}
		langMarkCB.setMaximumSize(new Dimension(140,30)) ;
		
		JLabel dbSortL = new JLabel("语言标识：");
		String dbSortStr = "Oracle:oracle;Mysql:mysql";
		for(String str : dbSortStr.split(";")) {
			String[] strs = str.split(":");
			dbSortCB.addItem(new DisPair(strs[0], strs[1]));
		}
		dbSortCB.setMaximumSize(new Dimension(140,30)) ;


		JLabel transSortL = new JLabel("翻译分类：");
		String transSortStr = "返回码:errorCode;返回报文:returnMsg";
		for(String str : transSortStr.split(";")) {
			String[] strs = str.split(":");
			transSortCB.addItem(new DisPair(strs[0], strs[1]));
		}
		transSortCB.setMaximumSize(new Dimension(140,30)) ;
		
		excelPathSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		excelPathSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
		excelPathTA.setLineWrap(true);        //激活自动换行功能 
		excelPathTA.setWrapStyleWord(true);            // 激活断行不断字功能
		

		JButton transExcelFileBtn = new JButton("选择翻译excel文件");
		Box excelPathBox=Box.createHorizontalBox();
		excelPathBox.add(transExcelFileBtn);
		excelPathBox.add(Box.createHorizontalStrut(10));
		excelPathBox.add(excelPathSP);
		excelPathBox.setBounds(15, 10, 990, 95);
		jPanel.add(excelPathBox);

		Box selectBox=Box.createHorizontalBox();
		selectBox.add(langMarkL);// 选择旧翻译excel文件
		selectBox.add(Box.createHorizontalStrut(10));
		selectBox.add(langMarkCB);
		selectBox.add(Box.createHorizontalStrut(10));
		selectBox.add(dbSortL);
		selectBox.add(Box.createHorizontalStrut(10));
		selectBox.add(dbSortCB);
		selectBox.add(Box.createHorizontalStrut(10));
		selectBox.add(transSortL);
		selectBox.add(Box.createHorizontalStrut(10));
		selectBox.add(transSortCB);
		startY = 5 + excelPathBox.getY() +excelPathBox.getHeight();
		selectBox.setBounds(15, startY, 990, 35);
		jPanel.add(selectBox);


		Box operBox=Box.createHorizontalBox();
		JButton excel2sqlBtn = new JButton("excel文件转sql");
		operBox.add(outputSP);
		operBox.add(Box.createHorizontalStrut(10));
		operBox.add(excel2sqlBtn);
		startY = 5 + selectBox.getY() + selectBox.getHeight();
		operBox.setBounds(15, startY, 990, 255);
		jPanel.add(operBox);
		

		transExcelFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ComnViewSet.choseFile2text(jPanel, JFileChooser.FILES_ONLY, excelPathTA);
            }
        });

		
		excel2sqlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		String excelDir = excelPathTA.getText();
            		String langDis = ((DisPair)langMarkCB.getSelectedItem()).getDis(); 
            		String lang = ((DisPair)langMarkCB.getSelectedItem()).getKey(); 

            		String dbSort = ((DisPair)dbSortCB.getSelectedItem()).getKey();
            		String transSort = ((DisPair)transSortCB.getSelectedItem()).getKey();
            		BottomView.setLogStatic("dbSort="+dbSort);
        			String retn = JavaParseUtil.excel2transSql(excelDir, 0, 3, lang, langDis, transSort);
        			outputTA.setText(retn);
        			BottomView.setLogStatic("excel路径： "+retn);
        		}catch(Exception ex) {
        			BottomView.setLogStatic(ex.getMessage());
        		}
            }
        });
		
		ComnMenu.addMenusDeep(jPanel);
	}
	
	public void initProp() {
		if(ComnViewSet.prop ==null) {
			return ;
		}
		Properties prop = ComnViewSet.prop;
		if(StringUtils.isNotBlank(prop.getProperty("transExcel_path"))){
			excelPathTA.setText(prop.getProperty("transExcel_path"));
		}
		if(StringUtils.isNotBlank(prop.getProperty("langMarkCB_select"))){
			String str = prop.getProperty("langMarkCB_select");
			String[] strs = str.split(":");
			if(strs.length>1) {
				langMarkCB.setSelectedItem(new DisPair(strs[0], strs[1]));
			}
		}
		if(StringUtils.isNotBlank(prop.getProperty("dbSortCB_select"))){
			String str = prop.getProperty("dbSortCB_select");
			dbSortCB.setSelectedItem(new DisPair(str.split(":")[0], str.split(":")[1]));
		}
		if(StringUtils.isNotBlank(prop.getProperty("transSortCB_select"))){
			String str = prop.getProperty("transSortCB_select");
			transSortCB.setSelectedItem(new DisPair(str.split(":")[0], str.split(":")[1]));
		}
	}
}

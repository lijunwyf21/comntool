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

/**
 * 错误码java枚举转excel
 * @author justin6
 *
 */
public class TransErrorCodePanel extends JPanel{

	private static final long serialVersionUID = 23231L;
	private JScrollPane scrollPane = new JScrollPane();
    private final JPanel jPanel=new JPanel();
    
    private final JTextArea errorCodeTA = new JTextArea();// java错误码枚举文件路径显示
    private JScrollPane errorCodeSP = new JScrollPane(errorCodeTA);
    private final JTextField oldExcelPath = new JTextField();// 旧翻译的excel
    private final JTextField outExcelPath = new JTextField();// 输出excel路径
    //private final JTextField outExcelName = new JTextField("返回码_zh_中文.xlsx");// 输出的excel文件名
    private final JComboBox<String> langMarkCB=new JComboBox<>();
    private final JTextField endLine = new JTextField("65536");// 结束于多少行
    
	public TransErrorCodePanel() {
		initPanel();
		initProp();
	}
	
	private void initPanel(){
        // Font font=new Font("宋体", Font.BOLD, 16);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrollPane.setViewportView(jPanel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(scrollPane);

        jPanel.setLayout(null);
		jPanel.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
		int startY = 0;

		JButton selectJavaEnumFileBtn = new JButton("选择java返回码枚举文件");
		JButton selectOldExcelBtn = new JButton("选择旧翻译excel文件");
		JButton outExcelPathBtn = new JButton("选择输出excel文件夹");
		//JLabel outExcelFileNameL = new JLabel("输出excel文件名：");
		JLabel langMarkL = new JLabel("语言标识：");
		String signAlgo = "zh(中文),en(英文)";
		for(String algo : signAlgo.split(",")) {
			langMarkCB.addItem(algo.trim());
		}
		JLabel endLineL = new JLabel("结束行数：");
		
		errorCodeSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		errorCodeSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
		errorCodeTA.setLineWrap(true);        //激活自动换行功能 
		errorCodeTA.setWrapStyleWord(true);            // 激活断行不断字功能
		
		Box javaEnumFileBox=Box.createHorizontalBox();
		javaEnumFileBox.add(selectJavaEnumFileBtn);
		javaEnumFileBox.add(Box.createHorizontalStrut(10));
		javaEnumFileBox.add(errorCodeSP);
		javaEnumFileBox.setBounds(15, 10, 990, 115);
		jPanel.add(javaEnumFileBox);

		Box oldExcelBox=Box.createHorizontalBox();
		oldExcelBox.add(selectOldExcelBtn);// 选择旧翻译excel文件
		oldExcelBox.add(Box.createHorizontalStrut(20));
		oldExcelBox.add(oldExcelPath);
		startY = 5 + javaEnumFileBox.getY() +javaEnumFileBox.getHeight();
		oldExcelBox.setBounds(15, startY, 990, 35);
		jPanel.add(oldExcelBox);
		

		Box outExcelBox=Box.createHorizontalBox();
		outExcelBox.add(outExcelPathBtn); // 选择输出excel文件夹
		outExcelBox.add(Box.createHorizontalStrut(20));
		outExcelBox.add(outExcelPath);
		startY = 5 + oldExcelBox.getY() + oldExcelBox.getHeight();
		outExcelBox.setBounds(15, startY, 990, 30);
		//outExcelBox.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
		jPanel.add(outExcelBox);
		
		
		langMarkCB.setMaximumSize(new Dimension(140,30)) ;
		//outExcelName.setMaximumSize(new Dimension(350,30)) ;
		endLine.setMaximumSize(new Dimension(160,30)) ;
		Box outExcelFileNameBox=Box.createHorizontalBox();
		//outExcelFileNameBox.add(outExcelFileNameL); // 输出excel文件名
		//outExcelFileNameBox.add(Box.createHorizontalStrut(10));
		//outExcelFileNameBox.add(outExcelName);
		outExcelFileNameBox.add(Box.createHorizontalStrut(10));
		outExcelFileNameBox.add(langMarkL);
		outExcelFileNameBox.add(Box.createHorizontalStrut(10));
		outExcelFileNameBox.add(langMarkCB);
		outExcelFileNameBox.add(Box.createHorizontalStrut(10));
		outExcelFileNameBox.add(endLineL);
		outExcelFileNameBox.add(Box.createHorizontalStrut(10));
		outExcelFileNameBox.add(endLine);
		
		startY = 5 + outExcelBox.getY() + outExcelBox.getHeight();
		outExcelFileNameBox.setBounds(15, startY, 990, 35);
		//outExcelFileNameBox.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
		jPanel.add(outExcelFileNameBox);

		Box operBox=Box.createHorizontalBox();
		JButton enum2excelBtn = new JButton("java枚举文件转excel");
		operBox.add(enum2excelBtn);
		startY = 5 + outExcelFileNameBox.getY() + outExcelFileNameBox.getHeight();
		operBox.setBounds(15, startY, 990, 35);
		jPanel.add(operBox);

		//verifyBox.setBorder(BorderFactory.createLineBorder(Color.red, 3));
		//ComnViewSet.addPopupDefault(errorCodeTA, oldExcelPath, outExcelPath, langMarkCB, endLine);

		selectJavaEnumFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ComnViewSet.choseFile2text(jPanel, JFileChooser.DIRECTORIES_ONLY, errorCodeTA);
            }
        });
		

    	final KFileChooser fileChooser = new KFileChooser();
		selectOldExcelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ComnViewSet.choseFile2text(jPanel, fileChooser, JFileChooser.FILES_ONLY, oldExcelPath);
            }
        });

		final KFileChooser choseOutExcelPath = new KFileChooser();
		outExcelPathBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ComnViewSet.choseFile2text(jPanel, choseOutExcelPath, JFileChooser.DIRECTORIES_ONLY, outExcelPath);
            }
        });
		
		enum2excelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		String outDir = outExcelPath.getText();
            		outDir = outDir.endsWith(File.separator)? outDir : (outDir+File.separator) ;
            		String javaDir = errorCodeTA.getText();
            		String lang = langMarkCB.getSelectedItem().toString();
            		String[] langs = lang.split("\\(");
            		String langDis = langs[1].replaceAll("\\)", "");
        			List<String>  enumParse= JavaParseUtil.enumParse(Comn.file2String(javaDir), Integer.parseInt(endLine.getText()));
        			String excelPath = JavaParseUtil.enum2transExcel(oldExcelPath.getText(), outDir, enumParse, 
        					langs[0], langDis, "errorCode", null);
        			BottomView.setLogStatic("excel路径： "+excelPath);
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
		if(StringUtils.isNotBlank(prop.getProperty("errorCodeTA_path"))){
			errorCodeTA.setText(prop.getProperty("errorCodeTA_path"));
		}
		if(StringUtils.isNotBlank(prop.getProperty("oldExcelPath_path"))){
			oldExcelPath.setText(prop.getProperty("oldExcelPath_path"));
		}
		if(StringUtils.isNotBlank(prop.getProperty("outExcelPath_path"))){
			outExcelPath.setText(prop.getProperty("outExcelPath_path"));
		}
		if(StringUtils.isNotBlank(prop.getProperty("langMarkCB_select"))){
			langMarkCB.setSelectedItem(prop.getProperty("langMarkCB_select"));
		}
		if(StringUtils.isNotBlank(prop.getProperty("endLine_int"))){
			endLine.setText(prop.getProperty("endLine_int"));
		}
	}
}

package com.comntool.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang3.StringUtils;

import com.comntool.constcla.comn.ComnConst;
import com.comntool.ext.ComnMenu;
import com.comntool.ext.ComnViewSet;
import com.comntool.ext.KFileChooser;
import com.comntool.ext.LineNumberHeaderView;
import com.comntool.util.utility.CodeUtil;
import com.comntool.util.utility.Comn;
import com.comntool.util.utility.GraphqlUtil;
import com.comntool.util.utility.JavaParseUtil;

/**
 * java bean转graphql中的对象定义
 * @author justin6
 *
 */
public class TransGraphqlPanel extends JPanel{
	private static final long serialVersionUID = 23231L;
    private final JTextArea javaClaTA = new JTextArea();// java错误码枚举文件路径显示
    private JScrollPane javaClaSP = new JScrollPane(javaClaTA);

    private LineNumberHeaderView view = new LineNumberHeaderView(); // 行号
    private final JTextArea javaLoadTA = new JTextArea();// 加载的java类的文件内容
    private JScrollPane javaLoadSP = new JScrollPane(javaLoadTA);
    private final JTextArea graphqlObjTA = new JTextArea();// graphql对象定义
    private JScrollPane graphqlObjSP = new JScrollPane(graphqlObjTA);
    private final JTextArea ghDirTA = new JTextArea();// 输出的graphql对象定义文件路径
    private JScrollPane ghDirSP = new JScrollPane(ghDirTA);
    private final JTextField startLine = new JTextField("5");// 开始于多少行
    private final JTextField endLine = new JTextField("65536");// 结束于多少行
    private final JTextField objDesc = new JTextField("");// 对象描述（一般为中文）
    private final JTextField objName = new JTextField("");// 对象名（即类名，为英文）
    private final JTextField indent = new JTextField("    ");// 对象名（即类名，为英文）

    private final JPanel jPanel=new JPanel();
	private JScrollPane scrollPane = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	public TransGraphqlPanel() {
		initPanel();
		initProp();
	}
	
	private void initPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(scrollPane);

        jPanel.setLayout(null);
		jPanel.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
        jPanel.setPreferredSize(new Dimension(800,700));

		JLabel memoInfoLabel = new JLabel("说明：将Java文件中的成员变量转成graphql的对象定义");
		Box memoInfoBox=Box.createHorizontalBox();
		memoInfoBox.add(memoInfoLabel);
		memoInfoBox.setBounds(15, 10, 990, 35);
		jPanel.add(memoInfoBox);

		JButton javaClaBtn = new JButton("选择java bean类文件");
		Box javaClaBox=Box.createHorizontalBox();
		javaClaBox.add(javaClaBtn);
		javaClaBox.add(Box.createHorizontalStrut(10));
		javaClaBox.add(javaClaSP);
		javaClaBox.setBounds(15, ComnViewSet.getBottomY(memoInfoBox, 5), 990, 65);
		jPanel.add(javaClaBox);

		javaLoadSP.setRowHeaderView(view); // 设置行号
		Box javaLoadBox=Box.createHorizontalBox();
		javaLoadBox.add(javaLoadSP);// 加载的java文件内容
		javaLoadBox.setBounds(15, ComnViewSet.getBottomY(javaClaBox, 5), 990, 195);
		javaLoadBox.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
		jPanel.add(javaLoadBox);

		JButton selectOutGraphqlDirBtn = new JButton("选择输出的Graphql文件夹");
		Box selectOutGraphqlDirBox=Box.createHorizontalBox();
		ComnViewSet.addComp(selectOutGraphqlDirBox, selectOutGraphqlDirBtn,
				Box.createHorizontalStrut(20), ghDirSP);
		selectOutGraphqlDirBox.setBounds(15, ComnViewSet.getBottomY(javaLoadBox, 5), 990, 65);
		jPanel.add(selectOutGraphqlDirBox);

		// startLine.setColumns(5);
		// startLine.setMaximumSize(new Dimension(5, 35));// setColumns和setMaximumSize要同时设置
		ComnViewSet.setBoxTFW(startLine, 6, 35);
		ComnViewSet.setBoxTFW(endLine, 8, 35);
		ComnViewSet.setBoxTFW(objDesc, 18, 35);
		ComnViewSet.setBoxTFW(objName, 18, 35);
		JButton makeGraphqlBtn = new JButton("创建Graphql");
		Box operParamBox=Box.createHorizontalBox();
		ComnViewSet.addComp(operParamBox, new JLabel("开始行数："), startLine,
				Box.createHorizontalStrut(2), new JLabel("结束行数："), endLine,
				Box.createHorizontalStrut(2), new JLabel("文件描述："), objDesc,
				Box.createHorizontalStrut(2), new JLabel("对象名："), objName,
				Box.createHorizontalStrut(2), makeGraphqlBtn);
		operParamBox.setBounds(15, ComnViewSet.getBottomY(selectOutGraphqlDirBox, 5), 990, 35);
		jPanel.add(operParamBox);
		

		Box graphqlDisBox=Box.createHorizontalBox();
		ComnViewSet.addComp(graphqlDisBox, new JLabel("生成的graphql内容："), Box.createHorizontalStrut(20), graphqlObjSP);
		graphqlDisBox.setBounds(15, ComnViewSet.getBottomY(operParamBox, 5), 990, 195);
		jPanel.add(graphqlDisBox);
		
		javaClaTA.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent de) {
				javaLoadTA.setText(Comn.file2String(javaClaTA.getText()));
			}

			public void insertUpdate(DocumentEvent de) {
				javaLoadTA.setText(Comn.file2String(javaClaTA.getText()));
			}

			public void removeUpdate(DocumentEvent de) {
			}
		});
		
		javaClaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ComnViewSet.choseFile2text(jPanel, JFileChooser.FILES_ONLY, javaClaTA);
            }
        });
		
		selectOutGraphqlDirBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ComnViewSet.choseFile2text(jPanel, JFileChooser.DIRECTORIES_ONLY, ghDirTA);
            }
        });
		
		makeGraphqlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		String javaPath = javaClaTA.getText();
            		String ghDir = ghDirTA.getText();
            		String startLine_str = startLine.getText();
            		String endLine_str = endLine.getText();
            		String objDesc_str = objDesc.getText();
            		String objName_str = objName.getText();
            		String indent_str = StringUtils.isEmpty(indent.getText())? "    " : indent.getText();
            		String content = Comn.file2String(javaPath); // 
            		if(StringUtils.isBlank(content)) {
            			BottomView.setLogStatic("java类文件内容为空白，值："+content);
            			return ;
            		}
            		String ghStr = GraphqlUtil.java2graphql(objDesc_str, objName_str, indent_str, content,
            				Integer.valueOf(startLine_str), Integer.valueOf(endLine_str));
            		graphqlObjTA.setText(ghStr);
            		if(StringUtils.isNotBlank(ghDir)) {
            			ghDir = ghDir.endsWith(File.separator)? ghDir : (ghDir+File.separator) ;
            			String ghOutPath = ghDir + "mkgh_"+Comn.getDateStr(new Date(), Comn.dateFormat_dayTime)+".graphqls";
            			Comn.str2file(ghStr, new File(ghOutPath), ComnConst.CHARCODE);
            			BottomView.setLogStatic("make graphql, path="+ghOutPath);
            		}
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
		if(StringUtils.isNotBlank(prop.getProperty("javaClaTA_path"))){
			javaClaTA.setText(prop.getProperty("javaClaTA_path"));
		}
	}
}

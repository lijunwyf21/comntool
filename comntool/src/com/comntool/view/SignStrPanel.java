package com.comntool.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.alibaba.fastjson.JSONObject;
import com.comntool.ext.ComnMenu;
import com.comntool.ext.ComnViewSet;
import com.comntool.util.utility.CryptoTool;

/**
 * 拼装签名字符串。json字符串转签名的明文
 * @author justin6
 *
 */
public class SignStrPanel extends JPanel{

	private static final long serialVersionUID = 231L;

    private final JTextField signName = new JTextField();
    private final JTextArea jsonTA = new JTextArea();
    private JScrollPane jsonSP = new JScrollPane(jsonTA);
    private final JTextArea signPlaintextTA = new JTextArea();
    private JScrollPane signPlaintextSP = new JScrollPane(signPlaintextTA);
    private final JPanel jPanel=new JPanel();
	private JScrollPane scrollPane = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	public SignStrPanel() {
		initPanel();
	}
	
	private void initPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(scrollPane);

        jPanel.setLayout(null);
		jPanel.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
        jPanel.setPreferredSize(new Dimension(800,700));
		int startY = 0;
		

		JLabel memoInfoLabel = new JLabel("说明：必须是合法的Json字符串，本功能用fastjson来解析");
		Box memoInfoBox=Box.createHorizontalBox();
		memoInfoBox.add(memoInfoLabel);
		memoInfoBox.setBounds(15, 10, 990, 35);
		jPanel.add(memoInfoBox);

		JLabel jsonLabel = new JLabel("json字符串：");
		Box jsonBox=Box.createHorizontalBox();
		jsonBox.add(jsonLabel);
		jsonBox.add(jsonSP);
		startY = 2 + memoInfoBox.getY() + memoInfoBox.getHeight();
		jsonBox.setBounds(15, startY, 990, 105);
		jPanel.add(jsonBox);
		

		JLabel signPropLabel = new JLabel("签名字段（用于从签名的明文中删除）：");
		signName.setText("signStr16");
		JButton json2signPlaintext = new JButton("json转签名的明文");
		Box transferPlaintextBox=Box.createHorizontalBox();
		transferPlaintextBox.add(signPropLabel);
		transferPlaintextBox.add(Box.createHorizontalStrut(10));
		transferPlaintextBox.add(signName);
		transferPlaintextBox.add(Box.createHorizontalStrut(30));
		transferPlaintextBox.add(json2signPlaintext);
		startY = 2 + jsonBox.getY() + jsonBox.getHeight();
		transferPlaintextBox.setBounds(15, startY, 990, 35);
		jPanel.add(transferPlaintextBox);

		JLabel jsonTransResultLabel = new JLabel("json字符串转签名的转换结果：");
		Box plaintextBox=Box.createHorizontalBox();
		plaintextBox.add(jsonTransResultLabel);
		plaintextBox.add(Box.createHorizontalStrut(10));
		plaintextBox.add(signPlaintextSP);
		startY = 2 + transferPlaintextBox.getY() + transferPlaintextBox.getHeight();
		plaintextBox.setBounds(15, startY, 990, 105);
		jPanel.add(plaintextBox);

		ComnViewSet.taLineWrap(jsonTA, signPlaintextTA);
		ComnMenu.addMenusDeep(jPanel);
		
		json2signPlaintext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(Main.this, "You click button is " + name);
            	try {
                	@SuppressWarnings("unchecked")
					Map<String, String> jsonMap = (Map<String, String>)JSONObject.parseObject(jsonTA.getText(), Map.class);
            		String signPlaintext = CryptoTool.map2signStr(jsonMap, signName.getText());
            		signPlaintextTA.setText(signPlaintext);
            	}catch(Exception ex) {
            		ex.printStackTrace();
            	}
            }
        });

		

	}
}

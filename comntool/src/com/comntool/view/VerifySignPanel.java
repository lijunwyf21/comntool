package com.comntool.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.comntool.ext.ComnMenu;
import com.comntool.ext.ComnViewSet;
import com.comntool.util.utility.CryptoTool;

/**
 * 签名计算
 * @author justin6
 *
 */
public class VerifySignPanel extends JPanel{

	private static final long serialVersionUID = 23231L;
    private final JTextArea pubKeyTA = new JTextArea();
    private JScrollPane pubKeySP = new JScrollPane(pubKeyTA);
    private final JTextArea signStrTA = new JTextArea();// 签名前的明文字符串
    private JScrollPane signStrSP = new JScrollPane(signStrTA);
    private final JTextArea verifyStrTA = new JTextArea();// 用于验签的签名字符串
    private JScrollPane verifyStrSP = new JScrollPane(verifyStrTA);
    private final JTextField verifyResultTF = new JTextField();// 签名字符串
    private final JComboBox<String> comboBox=new JComboBox<>();
    private final JPanel jPanel=new JPanel();
	private JScrollPane scrollPane = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	public VerifySignPanel() {
		initPanel();
		initProp();
	}
	
	private void initPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// setLayout(null);
		add(scrollPane);

        jPanel.setLayout(null);
		jPanel.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
        jPanel.setPreferredSize(new Dimension(800,700));
		int startY = 0;
		
		JLabel memoInfoLabel = new JLabel("说明：公私钥支持带‘---’格式");
		Box memoInfoBox=Box.createHorizontalBox();
		memoInfoBox.add(memoInfoLabel);
		memoInfoBox.setBounds(15, 10, 990, 35);
		jPanel.add(memoInfoBox);
		
		pubKeyTA.setText("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCygj3FfqKnJ5Z2/+nV4lilj9Jz/rB"
				+ "H0jvnv/UptoVIWmhvG3WhNpZaic9JnZY9zqhAocxZ1S8tGScM7LQ05/7EpiYf9o6EDV2N5BiBN"
				+ "amsoTPOH830W30ZU6yi7wrFnNxX5PL9dv81bxnSNJkWGIu3XB6Wap/h0rdJLTuPtrFmUwIDAQAB");
		//pubKeySP.setSize(20, 150);
		JLabel pubKeyLabel = new JLabel("公钥：");
		Box pubKeyBox=Box.createHorizontalBox();
		pubKeyBox.setMaximumSize(new Dimension(800, 120));
		pubKeyBox.add(pubKeyLabel);
		pubKeyBox.add(Box.createHorizontalStrut(45));
		pubKeyBox.add(pubKeySP);
		startY = 2 + memoInfoBox.getY() + memoInfoBox.getHeight();
		pubKeyBox.setBounds(15, startY, 990, 105);
		jPanel.add(pubKeyBox);
		

		JLabel signStrLabel = new JLabel("签名明文：");
		Box signStrBox=Box.createHorizontalBox();
		signStrBox.setMaximumSize(new Dimension(800, 110));
		signStrBox.add(signStrLabel);
		signStrBox.add(Box.createHorizontalStrut(10));
		signStrBox.add(signStrSP);
		startY = 2 + pubKeyBox.getY() + pubKeyBox.getHeight();
		signStrBox.setBounds(15, startY, 990, 105);
		jPanel.add(signStrBox);

		JLabel verifyStrL = new JLabel("用于验签的签名结果字符串：");
		Box verifyStrBox=Box.createHorizontalBox();
		//verifyStrBox.setBorder(BorderFactory.createLineBorder(Color.red, 3));
		verifyStrBox.setMaximumSize(new Dimension(800, 35));
		verifyStrBox.add(verifyStrL);
		verifyStrBox.add(Box.createHorizontalStrut(20));
		verifyStrBox.add(verifyStrSP);
		startY = 2 + signStrBox.getY() + signStrBox.getHeight();
		verifyStrBox.setBounds(15, startY, 990, 105);
		jPanel.add(verifyStrBox);

		
		String signAlgo = "MD2withRSA,MD5withRSA,SHA1withRSA,SHA256withRSA,SHA384withRSA,SHA512withRSA";
		for(String algo : signAlgo.split(",")) {
			comboBox.addItem(algo.trim());
		}
		comboBox.setMaximumSize(new Dimension(180, 40));
		comboBox.setSelectedIndex(3);
		JButton signBtn = new JButton("验证签名");
		// 参考：  https://www.jianshu.com/p/4b9816449ac9
		Box verifyBox=Box.createHorizontalBox();
		verifyBox.setMaximumSize(new Dimension(800, 35));
		verifyBox.add(comboBox);
		verifyBox.add(Box.createHorizontalStrut(10));
		verifyBox.add(signBtn);
		startY = 2 + verifyStrBox.getY() + verifyStrBox.getHeight();
		verifyBox.setBounds(15, startY, 990, 35);
		jPanel.add(verifyBox);

		Box verifyResultBox=Box.createHorizontalBox();
		verifyResultBox.setMaximumSize(new Dimension(800, 35));
		JLabel verifyResultL = new JLabel("验签结果：");
		verifyResultBox.add(verifyResultL);
		verifyResultTF.setColumns(100);
		verifyResultTF.setMaximumSize(new Dimension (680, 30));
		verifyResultBox.add(verifyResultTF);
		startY = 2 + verifyBox.getY() + verifyBox.getHeight();
		verifyResultBox.setBounds(15, startY, 990, 35);
		jPanel.add(verifyResultBox);

		ComnViewSet.taLineWrap(pubKeyTA, signStrTA, verifyStrTA);
		ComnMenu.addMenusDeep(jPanel);

		signBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(Main.this, "You click button is " + name);
            	try {
            		String algo = comboBox.getSelectedItem().toString();
            		/**
            		 示例：
            		 -----BEGIN PRIVATE KEY-----
MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALKCPcV+oqcnlnb/
6dXiWKWP0nP+sEfSO+e/9Sm2hUhaaG8bdaE2llqJz0mdlj3OqEChzFnVLy0ZJwzs
tDTn/sSmJh/2joQNXY3kGIE1qayhM84fzfRbfRlTrKLvCsWc3Ffk8v12/zVvGdI0
mRYYi7dcHpZqn+HSt0ktO4+2sWZTAgMBAAECgYAg+3mIXR6WffUKJbzB3SF97bX3
4q4Vhx20cz5MJ8I2nSXAqbUxRVHAhsf7FRfmcaGa14pXatFdptEVTUL6p4bl5bnA
tkuarNe5/3Awq9meEOgMqDO9VaHB737OBBTv+TsQ+zS/tOv5qfqLYBd13tc1dpxd
5hCgA5yEOazU+PsGuQJBANvzIaIkVH++kWOdA4HLPfzAVne3OnZHV4N1GY0dEUJx
JEzPnktO/gN7kG0S0EFxxboBX3N2w6CSBh52XbmA+mUCQQDPxEnTBfoDlLx2HoJf
vdWGqRdKCzwua5z1bl3KpHLgAoFeDC59PLAjX7wRgFqjDL7K0JEU6p9ip1Og4Glc
rzZXAkBvLm+J7qNoD4SevffP3FTxNqh+y3gZ4eYy5TaNRmw7EcYvraNunfd4+zT+
bcwnyMMSSy9cxmQM/sNpEpw98RyhAkEAt/b42zYGixcuzHP1QqsHHyYnrfNGxV+Z
6iNgPubfpIPzYn/sxg1vdNL25pQ9LPRDF7gXTmr019iLDv7FrECkHwJBAKD/5Si8
0KyMluPo9E1LjT2Xm9ueZpk34UUrMx36lURYWd4MEQxysaFQeyZW9KYG9KoYE4c2
htLssci/BKZVJ90=
-----END PRIVATE KEY-----


            		 -----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCygj3FfqKnJ5Z2/+nV4lilj9Jz
/rBH0jvnv/UptoVIWmhvG3WhNpZaic9JnZY9zqhAocxZ1S8tGScM7LQ05/7EpiYf
9o6EDV2N5BiBNamsoTPOH830W30ZU6yi7wrFnNxX5PL9dv81bxnSNJkWGIu3XB6W
ap/h0rdJLTuPtrFmUwIDAQAB
-----END PUBLIC KEY-----

            		 */
            		String pubKey = pubKeyTA.getText();
            		String signStr = signStrTA.getText();
            		String signedStr16 = verifyStrTA.getText();
            		if(pubKey.contains("\n")) {
            			String[] strs = pubKey.split("\\\n");
            			String temp = "";
            			for(String st : strs) {
            				if(st.trim().startsWith("-")) {
            					continue;
            				}
            				temp+=st.trim();
            			}
            			pubKey= temp;
            		}
            		
            		boolean verifyResult = CryptoTool.verify(algo, signStr, signedStr16, pubKey);
            		verifyResultTF.setText(System.currentTimeMillis()+" "+verifyResult);
            	}catch(Exception ex) {
            		BottomView.setLogStatic(ex.getMessage());
            	}
            }
        });

	}
	
	public void initProp() {
		if(ComnViewSet.prop ==null) {
			return ;
		}
		Properties prop = ComnViewSet.prop;
		if(StringUtils.isNotBlank(prop.getProperty("pubKeyTA_path"))){
			pubKeyTA.setText(prop.getProperty("pubKeyTA_path"));
		}
		
	}
}

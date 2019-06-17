package com.comntool.view;

import java.awt.Color;
import java.awt.Dimension;
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
public class SignCalcPanel extends JPanel{

	private static final long serialVersionUID = 23323331L;
    private final JTextArea privKeyTA = new JTextArea();
    private JScrollPane privKeySP = new JScrollPane(privKeyTA);
    private final JTextArea signStrTA = new JTextArea();
    private JScrollPane signStrSP = new JScrollPane(signStrTA);
    private final JComboBox<String> comboBox=new JComboBox<>();
    private final JTextArea signResultTA = new JTextArea();
    private JScrollPane signResultSP = new JScrollPane(signResultTA);
    private final JPanel jPanel=new JPanel();
	private JScrollPane scrollPane = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	JButton signBtn = new JButton("明文签名");
	public SignCalcPanel() {
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

		JLabel memoInfoLabel = new JLabel("说明：私钥支持带‘---’格式");
		Box memoInfoBox=Box.createHorizontalBox();
		memoInfoBox.add(memoInfoLabel);
		memoInfoBox.setBounds(15, 10, 990, 35);
		jPanel.add(memoInfoBox);
		
		JLabel privKeyLabel = new JLabel("私钥：");
		privKeySP.setSize(300, 250);
		Box privKeyBox=Box.createHorizontalBox();
		privKeyBox.add(privKeyLabel);
		privKeyBox.add(Box.createHorizontalStrut(40));
		privKeyBox.add(privKeySP);
		startY = 2 + memoInfoBox.getY() + memoInfoBox.getHeight();
		privKeyBox.setBounds(15, startY, 990, 155);
		jPanel.add(privKeyBox);

		JLabel jsonLabel = new JLabel("签名明文：");
		Box plaintextBox=Box.createHorizontalBox();
		plaintextBox.add(jsonLabel);
		plaintextBox.add(Box.createHorizontalStrut(10));
		plaintextBox.add(signStrSP);
		startY = 2 + privKeyBox.getY() + privKeyBox.getHeight();
		plaintextBox.setBounds(15, startY, 990, 95);
		jPanel.add(plaintextBox);

		String signAlgo = "MD2withRSA,MD5withRSA,SHA1withRSA,SHA256withRSA,SHA384withRSA,SHA512withRSA";
		for(String algo : signAlgo.split(",")) {
			comboBox.addItem(algo.trim());
		}
		comboBox.setMaximumSize(new Dimension(180, 40));
		comboBox.setSelectedIndex(3);
		// 参考：  https://www.jianshu.com/p/4b9816449ac9
		Box operSignBox=Box.createHorizontalBox();
		operSignBox.add(comboBox);
		operSignBox.add(Box.createHorizontalStrut(20));
		operSignBox.add(signBtn);
		startY = 2 + plaintextBox.getY() +plaintextBox.getHeight();
		operSignBox.setBounds(15, startY, 990, 35);
		jPanel.add(operSignBox);
		

		JLabel signReusltLabel = new JLabel("签名结果：");
		Box signReusltBox=Box.createHorizontalBox();
		signReusltBox.add(signReusltLabel);
		signReusltBox.add(Box.createHorizontalStrut(10));
		signReusltBox.add(signResultSP);
		startY = 2 + operSignBox.getY() +operSignBox.getHeight();
		signReusltBox.setBounds(15, startY, 990, 95);
		jPanel.add(signReusltBox);
		
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
            		String privKey = privKeyTA.getText();
            		String signStr = signStrTA.getText();
            		if(privKey.contains("\n")) {
            			String[] strs = privKey.split("\\\n");
            			String temp = "";
            			for(String st : strs) {
            				if(st.trim().startsWith("-")) {
            					continue;
            				}
            				temp+=st.trim();
            			}
            			privKey= temp;
            		}
            		
            		String signResult = CryptoTool.sign(algo, signStr, privKey);
            		signResultTA.setText(signResult);
            	}catch(Exception ex) {
            		BottomView.setLogStatic(ex.getMessage());
            	}
            }
        });

		ComnViewSet.taLineWrap(privKeyTA, signStrTA, signResultTA);
		ComnMenu.addMenusDeep(jPanel);
	}
	

	public void initProp() {
		if(ComnViewSet.prop ==null) {
			return ;
		}
		Properties prop = ComnViewSet.prop;
		if(StringUtils.isNotBlank(prop.getProperty("privKeyTA_path"))){
			privKeyTA.setText(prop.getProperty("privKeyTA_path"));
		}
		
	}
}

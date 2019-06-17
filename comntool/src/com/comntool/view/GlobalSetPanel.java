package com.comntool.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.apache.commons.lang3.StringUtils;

import com.comntool.ext.ComnMenu;
import com.comntool.ext.ComnViewSet;
import com.comntool.util.utility.Comn;

/**
 * 全局设置，包括重新加载配置文件comntool.properties
 * @author justin6
 *
 */
public class GlobalSetPanel extends JPanel{

	private static final long serialVersionUID = 23231L;
    private final JTextArea loadConfTA = new JTextArea();// java错误码枚举文件路径显示
    private JScrollPane loadConfSP = new JScrollPane(loadConfTA);
    private final JTextArea resetConfTA = new JTextArea();// java错误码枚举文件路径显示
    private JScrollPane resetConfSP = new JScrollPane(resetConfTA);

    private final JPanel jPanel=new JPanel();
	private JScrollPane scrollPane = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	public GlobalSetPanel() {
		initPanel();
		initProp();
	}
	
	private void initPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(scrollPane);

        jPanel.setLayout(null);
		jPanel.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
        jPanel.setPreferredSize(new Dimension(800,700));

		JLabel memoInfoLabel = new JLabel("说明：修改全局设置时，旧的tab页不生效，新打开tab页才会生效");
		Box memoInfoBox=Box.createHorizontalBox();
		memoInfoBox.add(memoInfoLabel);
		memoInfoBox.setBounds(15, 10, 990, 35);
		jPanel.add(memoInfoBox);
		
		JButton selectConfFileBtn = new JButton("选择全局配置文件：");
		JButton resetConfBtn = new JButton("重置全局配置文件");
		
		Box loadConfBox=Box.createHorizontalBox();
		loadConfBox.add(selectConfFileBtn);
		loadConfBox.add(Box.createHorizontalStrut(10));
		loadConfBox.add(loadConfSP);
		loadConfBox.setBounds(15, ComnViewSet.getBottomY(memoInfoBox, 5), 990, 95);
		jPanel.add(loadConfBox);

		Box resetConfBox=Box.createHorizontalBox();
		resetConfBox.add(resetConfBtn);// 选择旧翻译excel文件
		resetConfBox.add(Box.createHorizontalStrut(20));
		resetConfBox.add(resetConfSP);
		resetConfBox.setBounds(15, ComnViewSet.getBottomY(loadConfBox, 5), 990, 95);
		jPanel.add(resetConfBox);
		
		JPanel lp=new JPanel() {
			private static final long serialVersionUID = 12323L;
			@Override
			public void paint(Graphics gh) {
				super.paint(gh);
				gh.setColor(new Color(0x55, 0x55, 0));
				// gh.drawLine(1, 1, lineW, 1); // 这样不会报告空指针异常。
				// 参考： https://blog.csdn.net/sunmc1204953974/article/details/25171537
				Graphics2D g2 = (Graphics2D)gh;  //g是Graphics对象
				g2.setStroke(new BasicStroke(2.0f));
				g2.drawLine(1, 1, 990, 1); 
			}
		};
		lp.setBounds(15, ComnViewSet.getBottomY(resetConfBox, 5), 990, 3);
		// lp.setBorder(BorderFactory.createLineBorder(Color.orange, 1));// 面板边框
		jPanel.add(lp);
		

		selectConfFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ComnViewSet.choseFile2text(jPanel, JFileChooser.FILES_ONLY, loadConfTA);
            }
        });
		
		resetConfBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        		ComnViewSet.prop = Comn.file2prop(loadConfTA.getText());
        		resetConfTA.setText(Comn.map2str(ComnViewSet.prop));
        		BottomView.setLogStatic("prop.size=" + ComnViewSet.prop.size());
        		BottomView.setSysLogStatic(ComnViewSet.prop.toString());
            }
        });
		ComnMenu.addMenusDeep(jPanel);
	}
	
	public void initProp() {
		if(ComnViewSet.prop ==null) {
			return ;
		}
		Properties prop = ComnViewSet.prop;
		if(StringUtils.isNotBlank(prop.getProperty("loadConfTA_path"))){
			loadConfTA.setText(prop.getProperty("loadConfTA_path"));
		}
	}
}

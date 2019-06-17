package com.comntool.entry;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.apache.commons.lang3.StringUtils;

import com.comntool.ext.ComnViewSet;
import com.comntool.util.utility.Comn;
import com.comntool.view.BottomView;
import com.comntool.view.GlobalSetPanel;
import com.comntool.view.SignCalcPanel;
import com.comntool.view.SignStrPanel;
import com.comntool.view.Trans2SQLPanel;
import com.comntool.view.TransErrorCodePanel;
import com.comntool.view.TransGraphqlPanel;
import com.comntool.view.TransReturnMsgPanel;
import com.comntool.view.UpView;
import com.comntool.view.VerifySignPanel;

public class FrameMain {
	static final JPanel jp = new JPanel();
	static final BottomView bv = new BottomView();
	static final UpView uv = new UpView();
	public static void initFrame() {
		ComnViewSet.initFont();
        loadConf();
        initTabMap();
        initTabView();
        
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame jf = new JFrame("Justin工具箱");
		
		createMenu(jf);		
		BottomView.defaultBV= bv ;
		// jf.add(jp, BorderLayout.SOUTH);
		// 添加 "Hello World" 标签
        JLabel label = new JLabel("Hello World");
        
        jp.setLayout(new BorderLayout());
        jp.add(label, BorderLayout.NORTH);
        jp.add(bv, BorderLayout.SOUTH);
        
        JSplitPane splitP = new JSplitPane(JSplitPane.VERTICAL_SPLIT, uv, bv);//创建拆分窗格
        splitP.setDividerLocation(500);	//设置拆分窗格分频器初始位置
        splitP.setDividerSize(6);			//设置分频器大小

        jf.getContentPane().add(splitP, BorderLayout.CENTER);
        

		// 显示窗口
        jf.pack();
		jf.setBounds(400, 300, 1200, 800);
		//传入参数null 即可让JFrame 位于屏幕中央, 这个函数若传入一个Component ,则JFrame位于该组件的中央
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        
        sysInfo();
	}
	
	private static void createMenu(JFrame jf) {
		// 下面添加菜单
		// 参考： https://blog.csdn.net/xietansheng/article/details/77151690 
		JMenuBar menuBar = new JMenuBar();
		jf.setJMenuBar(menuBar);
		JMenu toolSet = new JMenu("工具设置");
		JMenu str = new JMenu("字符串处理");
		JMenu file = new JMenu("文件转换");
        ComnViewSet.addComp(menuBar, toolSet, str, file);
        
		JMenu code = new JMenu("代码转换");
        ComnViewSet.addComp(str, code);
        

        final JMenuItem resetConf = new JMenuItem("全局设置");
        ComnViewSet.addComp(toolSet, resetConf);
        
		
        final JMenuItem json2signPlaintext = new JMenuItem("json转明文");
        final JMenuItem sign = new JMenuItem("签名");
        final JMenuItem verify = new JMenuItem("验签");
        ComnViewSet.addComp(code, json2signPlaintext, sign, verify);

        
        
        // 重置配置文件
        resetConf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uv.addTabPanel(new GlobalSetPanel(), resetConf.getText());
            }
        });
		json2signPlaintext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uv.addTabPanel(new SignStrPanel(), json2signPlaintext.getText());
            }
        });
		sign.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uv.addTabPanel(new SignCalcPanel(),sign.getText());
            }
        });

		verify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uv.addTabPanel(new VerifySignPanel(),verify.getText());
            }
        });
		

        final JMenuItem errorCodeEnum2excel = new JMenuItem("错误码java枚举转excel");
        final JMenuItem returnMsgEnum2excel = new JMenuItem("返回报文java枚举转excel");
        final JMenuItem excel2sql = new JMenuItem("转excel转sql");
        final JMenuItem javaBean2graphql = new JMenuItem("javaBean转graphql");
        ComnViewSet.addComp(file, errorCodeEnum2excel, returnMsgEnum2excel, excel2sql, javaBean2graphql);

        errorCodeEnum2excel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	uv.addTabPanel(new TransErrorCodePanel(), ((JMenuItem)e.getSource()).getText());
            }
        });
        returnMsgEnum2excel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	uv.addTabPanel(new TransReturnMsgPanel(), ((JMenuItem)e.getSource()).getText());
            }
        });
        excel2sql.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	uv.addTabPanel(new Trans2SQLPanel(), ((JMenuItem)e.getSource()).getText());
            }
        });
        javaBean2graphql.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	uv.addTabPanel(new TransGraphqlPanel(), ((JMenuItem)e.getSource()).getText());
            }
        });
        

    	//uv.addTabPanel(new SignCalcPanel(),sign.getText());
    	//uv.addTabPanel(new VerifySignPanel(),verify.getText());
    	//uv.addTabPanel(new SignStrPanel(), json2signPlaintext.getText());
    	//uv.addTabPanel(new TransGraphqlPanel(), javaBean2graphql.getText());
        //uv.addTabPanel(new GlobalSetPanel(), resetConf.getText());
        // uv.addTabPanel(new TransErrorCodePanel(),errorCodeEnum2excel.getText());
    	// uv.addTabPanel(new Trans2SQLPanel(), excel2sql.getText());
    	// uv.addTabPanel(new TransReturnMsgPanel(), returnMsgEnum2excel.getText());
	}

	public static void sysInfo() {
		// 参考： https://my.oschina.net/yongyi/blog/667617?p={{page}}
		Properties props=System.getProperties(); //系统属性
		StringBuilder sb = new StringBuilder();
		sb.append("\n").append("Java的运行环境版本："+props.getProperty("java.version"));
		sb.append("\n").append("Java的运行环境供应商："+props.getProperty("java.vendor"));
		sb.append("\n").append("Java供应商的URL："+props.getProperty("java.vendor.url"));
		sb.append("\n").append("Java的安装路径："+props.getProperty("java.home"));
		sb.append("\n").append("Java的虚拟机规范版本："+props.getProperty("java.vm.specification.version"));
		sb.append("\n").append("Java的虚拟机规范供应商："+props.getProperty("java.vm.specification.vendor"));
		sb.append("\n").append("Java的虚拟机规范名称："+props.getProperty("java.vm.specification.name"));
		sb.append("\n").append("Java的虚拟机实现版本："+props.getProperty("java.vm.version"));
		sb.append("\n").append("Java的虚拟机实现供应商："+props.getProperty("java.vm.vendor"));
		sb.append("\n").append("Java的虚拟机实现名称："+props.getProperty("java.vm.name"));
		sb.append("\n").append("Java运行时环境规范版本："+props.getProperty("java.specification.version"));
		sb.append("\n").append("Java运行时环境规范供应商："+props.getProperty("java.specification.vender"));
		sb.append("\n").append("Java运行时环境规范名称："+props.getProperty("java.specification.name"));
		sb.append("\n").append("Java的类格式版本号："+props.getProperty("java.class.version"));
		sb.append("\n").append("Java的类路径："+props.getProperty("java.class.path"));
		sb.append("\n").append("加载库时搜索的路径列表："+props.getProperty("java.library.path"));
		sb.append("\n").append("默认的临时文件路径："+props.getProperty("java.io.tmpdir"));
		sb.append("\n").append("一个或多个扩展目录的路径："+props.getProperty("java.ext.dirs"));
		sb.append("\n").append("操作系统的名称："+props.getProperty("os.name"));
		sb.append("\n").append("操作系统的构架："+props.getProperty("os.arch"));
		sb.append("\n").append("操作系统的版本："+props.getProperty("os.version"));
		sb.append("\n").append("文件分隔符："+props.getProperty("file.separator"));   //在 unix 系统中是＂／＂
		sb.append("\n").append("路径分隔符："+props.getProperty("path.separator"));   //在 unix 系统中是＂:＂
		sb.append("\n").append("行分隔符："+props.getProperty("line.separator"));   //在 unix 系统中是＂/n＂
		sb.append("\n").append("用户的账户名称："+props.getProperty("user.name"));
		sb.append("\n").append("用户的主目录："+props.getProperty("user.home"));
		sb.append("\n").append("用户的当前工作目录："+props.getProperty("user.dir"));
		
		BottomView.setSysLogStatic(sb.toString());
		/*
		System.out.println("Java的运行环境版本："+props.getProperty("java.version"));
		System.out.println("Java的运行环境供应商："+props.getProperty("java.vendor"));
		System.out.println("Java供应商的URL："+props.getProperty("java.vendor.url"));
		System.out.println("Java的安装路径："+props.getProperty("java.home"));
		System.out.println("Java的虚拟机规范版本："+props.getProperty("java.vm.specification.version"));
		System.out.println("Java的虚拟机规范供应商："+props.getProperty("java.vm.specification.vendor"));
		System.out.println("Java的虚拟机规范名称："+props.getProperty("java.vm.specification.name"));
		System.out.println("Java的虚拟机实现版本："+props.getProperty("java.vm.version"));
		System.out.println("Java的虚拟机实现供应商："+props.getProperty("java.vm.vendor"));
		System.out.println("Java的虚拟机实现名称："+props.getProperty("java.vm.name"));
		System.out.println("Java运行时环境规范版本："+props.getProperty("java.specification.version"));
		System.out.println("Java运行时环境规范供应商："+props.getProperty("java.specification.vender"));
		System.out.println("Java运行时环境规范名称："+props.getProperty("java.specification.name"));
		System.out.println("Java的类格式版本号："+props.getProperty("java.class.version"));
		System.out.println("Java的类路径："+props.getProperty("java.class.path"));
		System.out.println("加载库时搜索的路径列表："+props.getProperty("java.library.path"));
		System.out.println("默认的临时文件路径："+props.getProperty("java.io.tmpdir"));
		System.out.println("一个或多个扩展目录的路径："+props.getProperty("java.ext.dirs"));
		System.out.println("操作系统的名称："+props.getProperty("os.name"));
		System.out.println("操作系统的构架："+props.getProperty("os.arch"));
		System.out.println("操作系统的版本："+props.getProperty("os.version"));
		System.out.println("文件分隔符："+props.getProperty("file.separator"));   //在 unix 系统中是＂／＂
		System.out.println("路径分隔符："+props.getProperty("path.separator"));   //在 unix 系统中是＂:＂
		System.out.println("行分隔符："+props.getProperty("line.separator"));   //在 unix 系统中是＂/n＂
		System.out.println("用户的账户名称："+props.getProperty("user.name"));
		System.out.println("用户的主目录："+props.getProperty("user.home"));
		System.out.println("用户的当前工作目录："+props.getProperty("user.dir"));
		*/
	}
	
	public static Map<String, JPanel> deflt = new HashMap<>();
	public static void initTabMap() {
		deflt.put("json转明文", new SignStrPanel());
		deflt.put("签名", new SignCalcPanel());
		deflt.put("验签", new VerifySignPanel());
		
		deflt.put("全局设置", new GlobalSetPanel());
		
		deflt.put("错误码java枚举转excel", new TransErrorCodePanel());
		deflt.put("回报文java枚举转excel", new TransReturnMsgPanel());
		deflt.put("转excel转sql", new Trans2SQLPanel());
		deflt.put("javaBean转graphql", new TransGraphqlPanel());
		
	}

	public static void initTabView() {
		Properties prop = ComnViewSet.prop;
		if(prop == null || StringUtils.isNotBlank(prop.getProperty("defaultTab"))) {
			String defaultTab = prop.getProperty("defaultTab");
			for(String str : defaultTab.split(",")) {
				JPanel comp = deflt.get(str);
				if(comp!=null) {
					uv.addTabPanel(comp, str);
				}
			}
		}
		
	}
	
	public static void loadConf() {
		Properties props=System.getProperties(); //系统属性
		String userDir = props.getProperty("user.dir");
		ComnViewSet.prop = Comn.file2prop(userDir + File.separator+"comntool.properties");
		BottomView.setLogStatic("prop.size=" + ComnViewSet.prop.size());
		BottomView.setSysLogStatic(ComnViewSet.prop.toString());
	}
}

package com.comntool.util.utility;

import java.awt.Color;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comntool.constcla.comn.ComnConst;
import com.comntool.util.utility.bean.CodeParseObj;




public class CodeUtil {
	private static final Logger log = LoggerFactory.getLogger(CodeUtil.class);
	public static void main(String[] args) {
		// DataUtil.initLogback();
		
		// test_removeStartSerial();
		// conn2wordTable();
		// System.out.println(System.nanoTime());
		
		 // parseChar();
		// conn2excelTable4oracle();
	}
	public static void test_removeStartSerial(){
		String src = "2	用户编号	tcode	VARCHAR2(256)	否	用户编号";
		String indexStr = "	";
		indexStr = ComnConst.WORD_TABLE_SPLIT;
		String aim = removeStartSerial(src, indexStr);
		Comn.pl(aim);
	}

	public static boolean isNoConf(String conf){
		conf = Comn.trim(conf);
		return conf==null || StringUtils.equals(conf,"") || StringUtils.equals(conf,"-");
	}

	
	public static String gainGetMethodName(String originStr, boolean isGet){
		if(StringUtils.isNotBlank(originStr)){
			return (isGet?"get":"set")+originStr.substring(0,1).toUpperCase()+originStr.substring(1);
		}
		return originStr;
	}
	
	/**
	 * 根据属性名称，属性数据类型生成get,set方法
	 * @param prop
	 * @param dataType
	 * @return
	 */
	public static String gainGetSetMethod(String prop, String dataType){
		if(StringUtils.isBlank(dataType)){
			dataType= "String";
		}
		StringBuilder methodStr = new StringBuilder();
		String upperFirstField = Comn.upperFirst(prop);
		methodStr.append("\n	public ").append(dataType).append(" get").append(upperFirstField).append("() {");
		methodStr.append("\n		return ").append(prop).append(";\n	}\n");
		methodStr.append("\n	public void set").append(upperFirstField).append("(").append(dataType).append(" ").append(prop).append(") {");
		methodStr.append("\n		this.").append(prop).append(" = ").append(prop).append(";\n	}");
		return methodStr.toString();
	}

	/**
	 * 根据属性名称，属性数据类型生成get,set方法
	 * @param prop
	 * @param dataType
	 * @return
	 */
	public static String gainServiceAndSetMethod(List<String> beanNames){
		if(CollectionUtils.isEmpty(beanNames)){
			return "";
		}
		StringBuilder declareService = new StringBuilder();
		StringBuilder setService = new StringBuilder();
		for(String beanName : beanNames){
			if(StringUtils.isBlank(beanName)){
				continue;
			}

			String upperProp = Comn.upperFirst(beanName);
			declareService.append("\n	@Autowired private I"+upperProp+"Service "+beanName+"Service = null;");
			setService.append("\n\n	public void set"+upperProp+"Service(I"+upperProp+"Service "+beanName+"Service) {"
			 + "\n		this."+beanName+"Service = "+beanName+"Service;"
			 + "\n	}");

		}
		
		declareService.append(setService);
		
		return declareService.toString();
	}
	

	/**
	 * 创建描述方法的代码
	 * @param prop 属性名称
	 * @param dataType 属性的数据类型
	 * @return
	 */
	public static String makeDeclare(String prop, String dataType){
		String aim =  "\n	public I"+dataType+" "+prop+";";
		return aim;
	}
	
	/**
	 * 创建get方法的代码
	 * @param prop 属性名称
	 * @param dataType 属性的数据类型
	 * @return
	 */
	public static String makeSetMethod(String prop, String dataType){
		String firstUpper = Comn.upperFirst(prop);
		String aim =  "\n	public void set"+firstUpper+"(I"+dataType+" "+prop+") {"
				+ "\n		this."+prop+" = "+prop+";"
				+ "\n	}";
		return aim;
	}	
	
	/**
	 * 根据实体标识名称，生成service对象名称
	 * @param prop 属性名称
	 * @param dataType 属性的数据类型
	 * @return
	 */
	public static String makeServiceName(String mark){
		if(StringUtils.isBlank(mark)){
			return null;
		}
		String[] strs = mark.split("_");
		StringBuilder aim = new StringBuilder(strs[0]);
		for(int i=1,l=strs.length;i<l;++i){
			aim.append(Comn.upperFirst(strs[i]));
		}
		return aim.toString();
	}
	
	/**
	 * 将数据库中的列转成pojo的属性（成员变量）例user_name_first转成userNameFirst
	 * @param originStr
	 * @return
	 */
	public static String columnToField(String originStr){
		if(StringUtils.isNotBlank(originStr)){
			originStr = originStr.toLowerCase();
			String[] strs = originStr.split("_");
			StringBuilder sb = new StringBuilder(strs[0]);
			if(strs.length>1){
				for(int i=1,l=strs.length;i<l;++i){
					String str = strs[i];
					sb.append(Comn.upperFirst(str));
				}
			}
			return sb.toString();
		}
		return originStr;
	}
	
	/**
	 * 将标识转成pojo的属性（成员变量）例user_name_first转成userNameFirst
	 * @param originStr
	 * @return
	 */
	public static String getBeanName(String originStr){
		if(StringUtils.isNotBlank(originStr)){
			originStr = originStr.toLowerCase();
			String[] strs = originStr.split("_");
			StringBuilder sb = new StringBuilder(strs[0]);
			if(strs.length>1){
				for(int i=1,l=strs.length;i<l;++i){
					String str = strs[i];
					sb.append(Comn.upperFirst(str));
				}
			}
			return sb.toString();
		}
		return originStr;
	}

	// 显示长度
	public static String disLeng(String dataType, String leng){
		String dis = StringUtils.isBlank(leng)?"":"("+leng+")";
		return dis;
	}
	// 根据属性类型名称，获取属性类型
	public static String declDataType(String dataType){
		dataType = dataType== null? "String" : dataType;
		if(dataType.equals("int") || dataType.equals("long")){
			return dataType;
		}else{
			return Comn.upperFirst(dataType);
		}
	}
	// 根据属性类型获取默认值
	public static String propDefaultValue(String dataType){
		dataType = dataType== null? "null" : dataType.toLowerCase();
		if(StringUtils.equalsIgnoreCase(dataType, "int")){
			return "0";
		}else if(dataType.equalsIgnoreCase("long")){
			return "0L";			
		}
		/*
		else if(StringUtils.startsWith(dataType, "Integer")
				|| StringUtils.startsWith(dataType, "Long")){
			return "null";
		} */ 
		return "null";
	}
	

	// java数据类型中，有长度的数据类型
	public static Set<String> lengProp = new HashSet<String>(new ArrayList<String>(Arrays.asList(new String[]{
			"",
	})));
	// java数据类型与文档中默认的显示值之间的映射
	public static Set<String> ignoreProp = new HashSet<String>(new ArrayList<String>(Arrays.asList(new String[]{
			"",
	})));
	// java数据类型与文档中默认的显示值之间的映射
	public static Map<String, String> docDataTypeDefault = new HashMap<String, String>(){
		private static final long serialVersionUID = 123L;
		{
			this.put("byte","101");
			this.put("Byte","101");
			this.put("shot","101");
			this.put("int","101");
			this.put("Integer","101");
			this.put("long","101");
			this.put("Long","101");
			this.put("string","testStr");
			this.put("String","testStr");
			this.put("VARCHAR","testStr");
			this.put("varchar","testStr");
			this.put("Date","2021-03-12 15:12:34");
			this.put("Timestamp","2021-03-12 15:12:34");
			
		}
	};
	public static String getDocDefault(String key){
		return docDataTypeDefault.get(key);
	}
	
	// java数据类型与文档中json体的默认显示值之间的映射
	public static Map<String, String> jsonStrDataTypeDefault = new HashMap<String, String>(){
		private static final long serialVersionUID = 123L;
		{
			this.put("byte","101");
			this.put("Byte","101");
			this.put("shot","101");
			this.put("int","101");
			this.put("Integer","101");
			this.put("long","101");
			this.put("Long","101");
			this.put("string","\"testStr\"");
			this.put("String","\"testStr\"");
			this.put("VARCHAR","testStr");
			this.put("varchar","testStr");
			this.put("Date", "\"2021-03-12 15:12:34\"");
			this.put("Timestamp", "\"2021-03-12 15:12:34\"");
			
		}
	};

	public static String getJsonDefault(String key){
		return jsonStrDataTypeDefault.get(key);
	}
	
	// 根据属性类型设置文档中举例的默认值
	public static String propDocDefaultValue(String dataType){
		dataType = dataType== null? "null" : dataType.toLowerCase();
		if(StringUtils.equalsIgnoreCase(dataType, "int")){
			return "1001";
		}else if(dataType.equalsIgnoreCase("long")){
			return "1001";			
		}else if(dataType.equalsIgnoreCase("String")){
			return "testStr";			
		}else if(dataType.equalsIgnoreCase("Date")){
			return "2021-03-12 15:12:34";			
		}else if(dataType.equalsIgnoreCase("timestamp")){
			return "2021-03-12 15:12:34";			
		}
		
		/*
		else if(StringUtils.startsWith(dataType, "Integer")
				|| StringUtils.startsWith(dataType, "Long")){
			return "null";
		} */ 
		return "null";
	}
	
	/**
	 * 根据sql生成Mybatis中映射列的数据类型
	 * @param sqlDataType
	 * @return
	 */
	public static String getMybatisDataTypeFromSql(String sqlDataType){
		if(StringUtils.isBlank(sqlDataType)){
			return sqlDataType;
		}
		if(sqlDataType.startsWith("varchar") || sqlDataType.startsWith("text")
				|| sqlDataType.startsWith("char") || sqlDataType.startsWith("String")){
			return "VARCHAR";
		}else if(sqlDataType.startsWith("double") || sqlDataType.startsWith("bigint")
				|| sqlDataType.startsWith("int")){
			return "NUMERIC";
		}else if(sqlDataType.startsWith("timestamp") || sqlDataType.toLowerCase().startsWith("date")){
			return "TIMESTAMP";
		}
		return sqlDataType;
	}
	

	/**
	 * 根据sql生成类中的数据类型
	 * @param sqlDataType
	 * @return
	 */
	public static String getClassDataTypeFromSql(String sqlDataType){
		if(StringUtils.isBlank(sqlDataType)){
			return sqlDataType;
		}
		if(sqlDataType.startsWith("varchar") || sqlDataType.startsWith("text")
				|| sqlDataType.startsWith("char") || sqlDataType.startsWith("double")){
			return "String";
		}else if(sqlDataType.startsWith("int") || sqlDataType.startsWith("bigint")){
			return "Integer";
		}else if(sqlDataType.startsWith("timestamp") || sqlDataType.startsWith("date")){
			return "Date";
		}
		
		return sqlDataType;
	}

	/**
	 * 根据word中的表格的数据库表的数据类型生成mysql的SQL语句中列的数据类型
	 * @param sqlDataType
	 * @return
	 */
	public static String getMysqlDataTypeFromWord(String wordDataType){
		if(StringUtils.isBlank(wordDataType)){
			return "varchar";
		}

		wordDataType = wordDataType.toLowerCase();
		wordDataType = wordDataType.trim();
		if(wordDataType.startsWith("varchar") || wordDataType.startsWith("String")){
			String dataLen = RegexUtils.getMatchOne(wordDataType, "\\([\\d]+\\)");
			dataLen = StringUtils.isBlank(dataLen)?"":dataLen;
			// return "varchar"+dataLen+" CHARACTER SET utf8 COLLATE utf8_unicode_ci ";
			return "varchar"+dataLen;
		}else if(wordDataType.startsWith("int")){
			String dataLen = RegexUtils.getMatchOne(wordDataType, "\\([\\d]+\\)");
			dataLen = StringUtils.isBlank(dataLen)?"":dataLen;
			return "int"+dataLen;
		}else if(wordDataType.startsWith("double")){
			String dataLen = RegexUtils.getMatchOne(wordDataType, "\\([\\d]{1,},{0,1}[\\d]*\\)");
			dataLen = StringUtils.isBlank(dataLen)?"":dataLen;
			return "double"+dataLen;
		}else if(wordDataType.startsWith("timestamp")){
			return "Date";
		}
		return wordDataType;
	}

	/**
	 * 根据word设计中的数据类型，生成Mybatis中映射列的数据类型
	 * @param sqlDataType
	 * @return
	 */
	public static String getMybatisDataTypeFromWord(String wordDataType){
		if(StringUtils.isBlank(wordDataType)){
			return "VARCHAR";
		}
		String wordDataType_low = wordDataType.trim().toLowerCase();
		wordDataType_low = wordDataType_low.contains("(")? wordDataType_low.substring(0, wordDataType_low.indexOf("(")) : wordDataType_low;
		
		if(wordDataType_low.startsWith("varchar") || wordDataType.startsWith("String")){
			return "VARCHAR";
		}
		if(wordDataType.startsWith("timestamp")
				|| wordDataType.toLowerCase().startsWith("date")){
			return "TIMESTAMP";
		}
		
		Set<String> numericType = new HashSet<>(Arrays.asList(new String[]{"int","Integer","integer","long","Long","float","double","bigint","smalint",}));
		if(numericType.contains(wordDataType_low)){
			return "NUMERIC";
		}
		return wordDataType;
	}
	
	/**
	 * 根据word中的表格的数据类型转成JAVA类的数据类型
	 * @param dataType word中的数据类型
	 * @return
	 */
	public static String getClassDataTypeFromWord(String dataType){
		if(StringUtils.isBlank(dataType)){
			return dataType;
		}

		String dataType_low = dataType.trim().toLowerCase();
		if(dataType_low.startsWith("varchar") || dataType.startsWith("double")){
			return "String";
		}
		if(dataType_low.startsWith("int")){
			return "Integer";
		}
		if(dataType_low.startsWith("bigint")){
			return "Long";
		}
		if(dataType_low.startsWith("timestamp")){
			return "Date";
		}
		return dataType;
	}


	/**
	 * 根据word中的表格的数据类型转成JAVA类的数据类型
	 * @param dataType word中的数据类型
	 * @return
	 */
	public static String revision(String dataType, String dataLeng){
		if((StringUtils.equalsIgnoreCase(dataType, "int") || StringUtils.equalsIgnoreCase(dataType, "Integer"))
				&& StringUtils.isNotBlank(dataLeng) && Integer.parseInt(dataLeng)>32){
			return "Long";
		}
		return dataType;
	}
	
	/**
	 * 截去“/*"之前的逗号
	 * @param s
	 * @return
	 */
	public static String removeLastCommaFromSql(String s){
		if(StringUtils.isBlank(s)|| s.indexOf(",")==-1){
			return s;
		}
		if(s.endsWith(",")){
			return s.substring(0,s.length()-1);
		}
		String prefix = s.substring(0,s.lastIndexOf("/*"));
		int index = prefix.lastIndexOf(",");
		return s.substring(0,index)+s.substring(index+1);
	}
	
	/**
	 * 截去最后一个lastStr之前的removeStr
	 * @param s
	 * @param lastStr
	 * @return
	 */
	public static String removeLastCommaFromSql(String src, String removeStr, String lastStr){
		if(StringUtils.isBlank(src)|| src.indexOf(removeStr)==-1){
			return src;
		}
		if(src.endsWith(removeStr)){
			return src.substring(0,src.length()-1);
		}
		int index =0;
		if(lastStr == null || src.lastIndexOf(lastStr) <0){
			index = src.lastIndexOf(removeStr);
		}else{
			String prefix = src.substring(0,src.lastIndexOf(lastStr));
			index = prefix.lastIndexOf(removeStr);
		}
		return src.substring(0,index)+src.substring(index+removeStr.length());
	}
	
	/**
	 * 去掉开始的序号
	 * @param s
	 * @param lastStr
	 * @return
	 */
	public static String removeStartSerial(String src){
		return removeStartSerial(src, ComnConst.WORD_TABLE_SPLIT);
	}
	
	/**
	 * 去掉开始的序号
	 * @param s
	 * @param lastStr
	 * @return
	 */
	public static String removeStartSerial(String src, String indexTag){
		if(StringUtils.isBlank(src) || indexTag==null || src.indexOf(indexTag)==-1){
			return src;
		}
		int index = src.indexOf(indexTag);
		return src.substring(index + indexTag.length() , src.length());
	}

	public static String pretreatSqlField(String sqlLine){
		sqlLine = sqlLine.trim();
		sqlLine= sqlLine.replaceAll("  ", " ");
		sqlLine= sqlLine.replaceAll(" ,", ",");
		return sqlLine;
	}
	
	/**
	 * 根据列名生成成员变量的名称
	 * @param columnName
	 * @return
	 */
	public static String transVarFromColumn(String columnName){
		if(StringUtils.isBlank(columnName)){
			return null;
		}
		String[] words= columnName.split("_");
		StringBuilder sb = new StringBuilder(words[0]);
		for(int i=1,l = words.length; i<l; ++i){
			sb.append(Comn.upperFirst(words[i]));
		}
		return sb.toString();
	}
	
	public static int[] bitValue ={
		Integer.parseInt("1",2),
		Integer.parseInt("10",2),
		Integer.parseInt("100",2),
		Integer.parseInt("1000",2),
		Integer.parseInt("10000",2),
		Integer.parseInt("100000",2) ,
		Integer.parseInt("1000000",2) ,
		Integer.parseInt("10000000",2) ,
		Integer.parseInt("100000000",2) ,
		Integer.parseInt("1000000000",2) ,
		Integer.parseInt("10000000000",2) };

	
	


	/** 
     * 通过包名获取包内所有类 
     *  
     * @param pkg 
     * @return 
     */  
    public static List<Class<?>> getAllClassByPackageName(String packageName) {
    	if(StringUtils.isBlank(packageName)){
    		return null ;
    	}
        // 获取当前包下以及子包下所以的类  
        List<Class<?>> returnClassList = getClasses(packageName);  
        return returnClassList;  
    }
    
    
    /** 
     * 从包package中获取所有的Class 
     * @param packageName 
     * @return List
     */  
    private static List<Class<?>> getClasses(String packageName) {  
        List<Class<?>> classes = new ArrayList<Class<?>>();  
        boolean recursive = true;  
        String packageDirName = packageName.replace('.', '/');  
        Enumeration<URL> dirs;  
        try {  
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);  
            while (dirs.hasMoreElements()) {  
                URL url = dirs.nextElement();  
                String protocol = url.getProtocol();  
                if ("file".equals(protocol)) {  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);  
                } else if ("jar".equals(protocol)) { // 如果是jar包文件   定义一个JarFile  
                    JarFile jar;  
                    try {  
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();  
                        Enumeration<JarEntry> entries = jar.entries();  
                        while (entries.hasMoreElements()) {  
                            JarEntry entry = entries.nextElement();  
                            String name = entry.getName();  
                            if (name.charAt(0) == '/') {  
                                name = name.substring(1);  
                            }  
                            // 如果前半部分和定义的包名相同  
                            if (name.startsWith(packageDirName)) {  
                                int idx = name.lastIndexOf('/');  
                                if (idx != -1) {   // 如果以"/"结尾 是一个包   获取包名 把"/"替换成"."  
                                    packageName = name.substring(0, idx).replace('/', '.');  
                                }  
                                if ((idx != -1) || recursive) {  // 如果可以迭代下去 并且是一个包 如果是一个.class文件 而且不是目录  
                                    if (name.endsWith(".class") && !entry.isDirectory()) {  // 去掉后面的".class" 获取真正的类名  
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);  
                                        try {  
                                            classes.add(Class.forName(packageName + '.' + className)); // 添加到classes 
                                        } catch (ClassNotFoundException e) {  
                                            log.info(e.getMessage(), e);  
                                        }  
                                    }  
                                }  
                            }  
                        }  
                    } catch (IOException e) {  
                        log.info(e.getMessage(), e);  
                    }  
                }  
            }  
        } catch (IOException e) {
            log.info(e.getMessage(), e);  
        }  
  
        return classes;  
    }  
  
    
    /** 
     * 以文件的形式来获取包下的所有Class 
     * @param packageName 
     * @param packagePath 
     * @param recursive 
     * @param classes 
     */  
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {  
        File dir = new File(packagePath);  
        if (!dir.exists() || !dir.isDirectory()) {  // 如果不存在或者 也不是目录就直接返回  
            return;  
        }  
        
        File[] dirfiles = dir.listFiles(new FileFilter() {  // 如果存在 就获取包下的所有文件 包括目录  
            public boolean accept(File file) {  // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件) 
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));  
            }  
        });  
        for (File file : dirfiles) {  
            if (file.isDirectory()) { // 如果是目录 则继续扫描   
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);  
            } else {  
                String className = file.getName().substring(0, file.getName().length() - 6);  // 如果是java类文件 去掉后面的.class 只留下类名  
                try {  
                   
                    classes.add(Class.forName(packageName + '.' + className)); // 添加到集合中去    
                } catch (ClassNotFoundException e) {  
                    log.info(e.getMessage(), e);  
                }  
            }  
        }  
    }  
    
	

	/**
	 * 通过数据库链接将表结构导出成设计文档的结构
	 * @param conn
	 * @param excelPath
	 * @return
	 */
	public static String conn2excelTable_oracle(Connection conn, String[] schemas, String excelPath) {
		StringBuilder sb = new StringBuilder("");
		
		// excel
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("表结构");
		XSSFSheet sheet_tableName = wb.createSheet("表名");
		sheet.setZoom(80);
		sheet_tableName.setZoom(80);
		for(int i=0, leng = 10; i<leng; ++i) {
			sheet.setColumnWidth(i, 40 * 256);
			sheet_tableName.setColumnWidth(i, 40 * 256);
		}
		//生成一个字体
        XSSFFont font=wb.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// HSSFColor.WHITE.index HSSFColor.VIOLET.index //字体颜色
        font.setFontHeightInPoints((short)12);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT); // 创建一个居左格式
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(new XSSFColor(new Color(255, 255, 255)));
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN); // 下边框  
		style.setBorderLeft(BorderStyle.THIN);// 左边框  
		style.setBorderTop(BorderStyle.THIN);// 上边框  
		style.setBorderRight(BorderStyle.THIN);// 右边框 
		style.setWrapText(true);                    // 设置单元格内容是否自动换行 
		

		XSSFCellStyle styleHead = wb.createCellStyle();
		styleHead.setAlignment(HorizontalAlignment.LEFT); // 创建一个居左格式
		styleHead.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleHead.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		styleHead.setFont(font);
		styleHead.setBorderBottom(BorderStyle.THIN); // 下边框  
		styleHead.setBorderLeft(BorderStyle.THIN);// 左边框  
		styleHead.setBorderTop(BorderStyle.THIN);// 上边框  
		styleHead.setBorderRight(BorderStyle.THIN);// 右边框 
		styleHead.setWrapText(true);                    // 设置单元格内容是否自动换行 
		
		
		
		int j = 1; // 行
		int j2 = 1;
		XSSFRow row = null;
		XSSFCell cell = null;
		XSSFRow row_tableName = null;
		XSSFCell cell_tableName = null;
		OutputStream os = null;

		String mark_t = "	", mark_left= "(", mark_right= ")", yes = "是", no = "否";
		String columnName, columnType, remarks, abbrName ;
		int dataSize, nullable  ; // digits, 
		
		try {
			if(conn==null || conn.isClosed() ) {
				if(wb!=null) {
					wb.close();
				}
				return null;
			}

			DatabaseMetaData dbmd = conn.getMetaData();
			String[] default_schemas={"%"};
			schemas = schemas == null ? default_schemas : schemas;
			for(String schm : schemas) {
				ResultSet resultSet  = dbmd.getTables(null, schm.toUpperCase(), "%", new String[]{"TABLE"});
				if(StringUtils.isNotBlank(excelPath)) {
				}
				
	
				// resultSet.last();
				row = sheet.createRow(++j);
				cell = row.createCell(1);
				cell.setCellValue("表数目： " +  resultSet.getRow());
				cell.setCellStyle(style);
				//resultSet.beforeFirst();
				++j;
				while (resultSet.next()) {
			    	String tableName=resultSet.getString("TABLE_NAME");
			    	String tableSchema=resultSet.getString("TABLE_SCHEM");
			    	String tableType=resultSet.getString("TABLE_TYPE");
			    	log.info("export tableName="+tableName);
					// 原文：https://blog.csdn.net/u011637069/article/details/52046662 
					sb.append(" 表名： ").append(tableName).append("\r\n\r\n");
					
	
					row = sheet.createRow(++j);
					cell = row.createCell(1);
					cell.setCellValue(" 表名： " + tableName+",tableType:"+tableType);
					cell.setCellStyle(styleHead);
	
					cell = row.createCell(2);
					cell.setCellValue("表描述： " + resultSet.getString("REMARKS"));
					cell.setCellStyle(styleHead);
					
	
	
					row_tableName = sheet_tableName.createRow(++j2);
					cell_tableName = row_tableName.createCell(1);
					cell_tableName.setCellValue(resultSet.getString("REMARKS"));
					cell_tableName.setCellStyle(style);
					cell_tableName = row_tableName.createCell(2);
					cell_tableName.setCellValue(tableName);
					cell_tableName.setCellStyle(styleHead);
	
					ResultSet rs  = dbmd.getColumns(null, getSchema(conn), tableName.toUpperCase(), "%");
					// rs.last();
					cell = row.createCell(3);
					cell.setCellValue("列数目： " +  rs.getRow());
					cell.setCellStyle(styleHead);
					XSSFCell cell_colNum = cell;
					// rs.beforeFirst();
	
					cell = row.createCell(4);
					cell.setCellValue("是否为空 ");
					cell.setCellStyle(styleHead);
					
					Set<String> cols = new HashSet<>();
					int colNum = 0;
					whileCols: while(rs !=null && rs.next()) {
						row = sheet.createRow(++j);
						int i=0;
						columnName = rs.getString("COLUMN_NAME");
						if(cols.contains(columnName)) {
							break whileCols;
						}
						cols.add(columnName);
						
						++colNum;
						columnType = rs.getString("TYPE_NAME");
		    			remarks = rs.getString("REMARKS");
		    			abbrName = remarks==null? "no_read" : remarks.contains(mark_t) ? remarks.substring(0, remarks.indexOf(mark_t)) : remarks;
		    			if(abbrName.trim().matches("[\\d]+")){
		    				abbrName = remarks.substring(remarks.indexOf(mark_t)+1);
		    				abbrName = abbrName.contains(mark_t) ? abbrName.substring(0, abbrName.indexOf(mark_t)) : abbrName;
		    			}
						dataSize = rs.getInt("COLUMN_SIZE"); 
						// digits = rs.getInt("DECIMAL_DIGITS"); 
						nullable = rs.getInt("NULLABLE"); 
						
						sb.append(abbrName).append(mark_t).append(columnName).append(mark_t).append(columnType).append(mark_left)
								.append(dataSize).append(mark_right).append(mark_t).append(nullable == 1 ? yes : no).append("\r\n");
						
						if(StringUtils.isNotBlank(excelPath)) {
							cell = row.createCell(++i);
							cell.setCellValue(abbrName);
							cell.setCellStyle(style);
		
							cell = row.createCell(++i);
							cell.setCellValue(columnName);
							cell.setCellStyle(style);
		
							cell = row.createCell(++i);
							cell.setCellValue(columnType+mark_left+dataSize+mark_right);
							cell.setCellStyle(style);
		
							cell = row.createCell(++i);
							cell.setCellValue(nullable == 1 ? yes : no);
							cell.setCellStyle(style);
						}
					}
	
					cell_colNum.setCellValue("列数目： " +  colNum+",schema:"+tableSchema);
					sb.append("\r\n\r\n");
					++j;
				}
			}
			
		}catch(Exception e) {
			log.info(e.getMessage(), e);
		}

		if(StringUtils.isNotBlank(excelPath)) {
			File excelFile = new File(excelPath);
			try {
				Comn.createDir(excelFile.getParent());
				if(!excelFile.exists()){
					excelFile.createNewFile();
				}
				os = new FileOutputStream(excelFile);
				wb.write(os);
			}catch(Exception e) {
				log.info(e.getMessage(), e);
			}finally {
				try {
					if(os!=null) {
						os.flush();
						os.close();
					}
					if(wb!=null) {
						wb.close();
					}
				}catch(Exception e) {
					log.info(e.getMessage(), e);
				}
			}
		}
		
		
		return sb.toString();
	}


	/**
	 * 通过数据库链接将表结构导出成设计文档的结构
	 * @param conn
	 * @param excelPath
	 * @return
	 */
	public static String conn2excelTable(Connection conn, String excelPath) {
		StringBuilder sb = new StringBuilder("");
		
		// excel
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("表结构");
		XSSFSheet sheet_tableName = wb.createSheet("表名");
		sheet.setZoom(80);
		sheet_tableName.setZoom(80);
		for(int i=0, leng = 10; i<leng; ++i) {
			sheet.setColumnWidth(i, 40 * 256);
			sheet_tableName.setColumnWidth(i, 40 * 256);
		}
		//生成一个字体
        XSSFFont font=wb.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// HSSFColor.WHITE.index HSSFColor.VIOLET.index //字体颜色
        font.setFontHeightInPoints((short)12);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT); // 创建一个居左格式
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(new XSSFColor(new Color(255, 255, 255)));
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN); // 下边框  
		style.setBorderLeft(BorderStyle.THIN);// 左边框  
		style.setBorderTop(BorderStyle.THIN);// 上边框  
		style.setBorderRight(BorderStyle.THIN);// 右边框 
		style.setWrapText(true);                    // 设置单元格内容是否自动换行 
		

		XSSFCellStyle styleHead = wb.createCellStyle();
		styleHead.setAlignment(HorizontalAlignment.LEFT); // 创建一个居左格式
		styleHead.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleHead.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		styleHead.setFont(font);
		styleHead.setBorderBottom(BorderStyle.THIN); // 下边框  
		styleHead.setBorderLeft(BorderStyle.THIN);// 左边框  
		styleHead.setBorderTop(BorderStyle.THIN);// 上边框  
		styleHead.setBorderRight(BorderStyle.THIN);// 右边框 
		styleHead.setWrapText(true);                    // 设置单元格内容是否自动换行 
		
		
		
		int j = 1; // 行
		int j2 = 1;
		XSSFRow row = null;
		XSSFCell cell = null;
		XSSFRow row_tableName = null;
		XSSFCell cell_tableName = null;
		OutputStream os = null;
				

		String mark_t = "	", mark_left= "(", mark_right= ")", yes = "是", no = "否";
		String columnName, columnType, remarks, abbrName ;
		int dataSize, nullable  ; // digits, 
		
		try {
			if(conn==null || conn.isClosed() ) {
				if(wb!=null) {
					wb.close();
				}
				return null;
			}

			
			DatabaseMetaData dbmd = conn.getMetaData();
			
			ResultSet resultSet  = dbmd.getTables(null, "%","%", new String[]{"TABLE"});
			if(StringUtils.isNotBlank(excelPath)) {
			}
			

			// resultSet.last();
			row = sheet.createRow(++j);
			cell = row.createCell(1);
			cell.setCellValue("表数目： " +  resultSet.getRow());
			cell.setCellStyle(style);
			//resultSet.beforeFirst();
			++j;
			while (resultSet.next()) {
		    	String tableName=resultSet.getString("TABLE_NAME");
		    	log.info("export tableName="+tableName);
				// 原文：https://blog.csdn.net/u011637069/article/details/52046662 
				sb.append(" 表名： ").append(tableName).append("\r\n\r\n");
				

				row = sheet.createRow(++j);
				cell = row.createCell(1);
				cell.setCellValue(" 表名： " + tableName);
				cell.setCellStyle(styleHead);

				cell = row.createCell(2);
				cell.setCellValue("表描述： " + resultSet.getString("REMARKS"));
				cell.setCellStyle(styleHead);
				


				row_tableName = sheet_tableName.createRow(++j2);
				cell_tableName = row_tableName.createCell(1);
				cell_tableName.setCellValue(resultSet.getString("REMARKS"));
				cell_tableName.setCellStyle(style);
				cell_tableName = row_tableName.createCell(2);
				cell_tableName.setCellValue(tableName);
				cell_tableName.setCellStyle(styleHead);

				ResultSet rs  = dbmd.getColumns(null, getSchema(conn), tableName.toUpperCase(), "%");
				// rs.last();
				cell = row.createCell(3);
				cell.setCellValue("列数目： " +  rs.getRow());
				cell.setCellStyle(styleHead);
				XSSFCell cell_colNum = cell;
				// rs.beforeFirst();

				cell = row.createCell(4);
				cell.setCellValue("是否为空 ");
				cell.setCellStyle(styleHead);
				
				Set<String> cols = new HashSet<>();
				int colNum = 0;
				whileCols: while(rs !=null && rs.next()) {
					row = sheet.createRow(++j);
					int i=0;
					columnName = rs.getString("COLUMN_NAME");
					if(cols.contains(columnName)) {
						break whileCols;
					}
					cols.add(columnName);
					
					++colNum;
					columnType = rs.getString("TYPE_NAME");
	    			remarks = rs.getString("REMARKS");
	    			abbrName = remarks==null? "no_read" : remarks.contains(mark_t) ? remarks.substring(0, remarks.indexOf(mark_t)) : remarks;
	    			if(abbrName.trim().matches("[\\d]+")){
	    				abbrName = remarks.substring(remarks.indexOf(mark_t)+1);
	    				abbrName = abbrName.contains(mark_t) ? abbrName.substring(0, abbrName.indexOf(mark_t)) : abbrName;
	    			}
					dataSize = rs.getInt("COLUMN_SIZE"); 
					// digits = rs.getInt("DECIMAL_DIGITS"); 
					nullable = rs.getInt("NULLABLE"); 
					
					sb.append(abbrName).append(mark_t).append(columnName).append(mark_t).append(columnType).append(mark_left)
							.append(dataSize).append(mark_right).append(mark_t).append(nullable == 1 ? yes : no).append("\r\n");
					
					if(StringUtils.isNotBlank(excelPath)) {
						cell = row.createCell(++i);
						cell.setCellValue(abbrName);
						cell.setCellStyle(style);
	
						cell = row.createCell(++i);
						cell.setCellValue(columnName);
						cell.setCellStyle(style);
	
						cell = row.createCell(++i);
						cell.setCellValue(columnType+mark_left+dataSize+mark_right);
						cell.setCellStyle(style);
	
						cell = row.createCell(++i);
						cell.setCellValue(nullable == 1 ? yes : no);
						cell.setCellStyle(style);
					}
				}

				cell_colNum.setCellValue("列数目： " +  colNum+"schema:"+getSchema(conn));
				sb.append("\r\n\r\n");
				++j;
			}
			
		}catch(Exception e) {
			log.info(e.getMessage(), e);
		}

		if(StringUtils.isNotBlank(excelPath)) {
			File excelFile = new File(excelPath);
			try {
				Comn.createDir(excelFile.getParent());
				if(!excelFile.exists()){
					excelFile.createNewFile();
				}
				os = new FileOutputStream(excelFile);
				wb.write(os);
			}catch(Exception e) {
				log.info(e.getMessage(), e);
			}finally {
				try {
					if(os!=null) {
						os.flush();
						os.close();
					}
					if(wb!=null) {
						wb.close();
					}
				}catch(Exception e) {
					log.info(e.getMessage(), e);
				}
			}
		}
		
		
		return sb.toString();
	}
	
	//其他数据库不需要这个方法 oracle和db2需要
	private static String getSchema(Connection conn) throws Exception {
		String schema;
		schema = conn.getMetaData().getUserName();
		if ((schema == null) || (schema.length() == 0)) {
			return "no_schema";
			// throw new Exception("ORACLE数据库模式不允许为空");
		}
		return schema.toUpperCase().toString();
 
	}
	
	/**
	 * 本地通过数据库链接将表结构导出成设计文档的结构
	 */
	@SuppressWarnings("unused")
	public static void conn2excelTable() {
		// D:\datum\data\appdata\java\local
		String excelPath="D:\\datum\\data\\appdata\\java\\local\\aimiTable"+ Comn.getDateStr(new Date(), Comn.dateFormat_sqlDate) +".xlsx", 
										// db="db_test", user = "aimizhongchou", password = "Lwj1q2w3eLwj622300",// 米米测试
						// url="jdbc:mysql://rm-uf63lgya86zf9lp3co.mysql.rds.aliyuncs.com:3306/"+db+"?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false",
				msg="导出表结构到设计文档";
		String user, password, db, url;
		// user = "aimizhongchou"; password = "Lwj1q2w3eLwj622300";// 米米开发
		// db="db_test"; url="jdbc:mysql://rm-uf63lgya86zf9lp3co.mysql.rds.aliyuncs.com:3306/"+db+"?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false";

		user = "amlcdjsh_test"; password = "DPSnaxC2s0W9t6yG";// 爱米测试
		db="java_djsh_test"; url="jdbc:mysql://rm-bp12e6u89p1u1c16l5o.mysql.rds.aliyuncs.com:3306/java_djsh_test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false";

				
		Properties props =new Properties();
		props.setProperty("remarks", "true"); //设置可以获取remarks信息 
		props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
		props.setProperty("user", user);
		props.setProperty("password", password);
		
		Connection conn; 
		try {
			// Class.forName("com.mysql.jdbc.Driver"); //加载MYSQL JDBC驱动程序
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, props);
			conn2excelTable(conn, excelPath);
		} catch (Exception e) {
			log.info(msg+e.getMessage(), e);
		}
	}
	

	/**
	 * 本地通过数据库链接将表结构导出成设计文档的结构
	 */
	@SuppressWarnings("unused")
	public static void conn2excelTable4oracle() {
		// D:\datum\data\appdata\java\local
		String excelPath="D:\\datum\\data\\appdata\\java\\local\\fanfuTable"+ Comn.getDateStr(new Date(), Comn.dateFormat_sqlDate) +".xlsx", 
										// db="db_test", user = "aimizhongchou", password = "Lwj1q2w3eLwj622300",// 米米测试
						// url="jdbc:mysql://rm-uf63lgya86zf9lp3co.mysql.rds.aliyuncs.com:3306/"+db+"?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false",
				msg="导出表结构到设计文档";
		String user, password, db, url, jdbcDriver;
		// user = "aimizhongchou"; password = "Lwj1q2w3eLwj622300";// 米米开发
		// db="db_test"; url="jdbc:mysql://rm-uf63lgya86zf9lp3co.mysql.rds.aliyuncs.com:3306/"+db+"?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false";
		jdbcDriver="oracle.jdbc.driver.OracleDriver";
		// user = "ngdes"; password = "ngdes";
		user = "system"; password = "oracle";
		db="java_djsh_test"; url="jdbc:oracle:thin:@10.17.5.170:1521:orabiz";
		String[] schemas = {"exchangeadmin","currsettlement","historysettlement","sync","currsgoperation","currsgsnapshot","ngdes","ngdesuam","ngdesadmin","ngdesrisk","ngdesindex","ngdesaudit","spot","spothistory"};
				
		Properties props =new Properties();
		props.setProperty("remarks", "true"); //设置可以获取remarks信息 
		props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
		props.setProperty("user", user);
		props.setProperty("password", password);
		
		Connection conn; 
		try {
			// Class.forName("com.mysql.jdbc.Driver"); //加载MYSQL JDBC驱动程序
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url, props);
			conn2excelTable_oracle(conn, schemas, excelPath);
		} catch (Exception e) {
			log.info(msg+e.getMessage(), e);
		}
	}


	
	public static String parseChar(String filePath) {
		String str = Comn.file2String(filePath, "UTF-16");
		log.info("str=\n"+str);
		try{
			str = new String(str.getBytes(), "UTF-8");
		}catch(Exception e) {
			log.info(e.getMessage(), e);
		}
		return str;
	}


	


	/**
	 * excel数据格式转换
	 * @param conn
	 * @param excelPath
	 * @return
	 */
	public static String excelFormTrans(String excelPath, String origStr, long rowNum, long colNum) {
		try {
			XSSFWorkbook wb = new XSSFWorkbook(excelPath);
			XSSFSheet sheet = wb.getSheetAt(0);
		
		}catch(Exception e) {
			
		}
		return null;
	}
}

package com.comntool.constcla.comn;

public class ComnConst {
	// 符号
	public static final String BLANK = " "; // 空格
	public static final String SINGLE_QUOTE = "'"; // 单引号
	public static final String DOUBLE_QUOTE = "\""; // 双引号
	public static final String COMMA = ","; // 逗号
	public static final String CLN = ":"; // 冒号 COLON
	public static final String SEMICOLON = ";"; // 分号
	public static final String PERIOD = "."; // 英文句号
	public static final String HYPHEN = "-"; // 横杠（连字符）
	public static final String EQUAL_SIGN = "="; // 横杠（连字符）
	 
	public static final String UNDERSCORE = "_"; // 下划线
	public static final String DASH = "——"; // 破折号
	public static final String WORD_TABLE_SPLIT = "	"; // word中的table拷出来后的分隔符
	// 参考： https://zhidao.baidu.com/question/1992319152685108867.html
	public static final String OPEN_PAREN = "("; // 左圆括号
	public static final String CLOSE_PAREN = ")"; // 右圆括号
	public static final String OPEN_BRACKET = "["; // 左方括号
	public static final String CLOSE_BRACKET = "]"; // 右方括号
	public static final String OPEN_BRACE = "{"; // 左花括号
	public static final String CLOSE_BRACE = "}"; // 右花括号
	
	public static final String STR_ZERO = "0";
	public static final String STR_ONE = "1";
	public static final String STR_TWO = "2";
	public static final String STR_THREE = "3";
	public static final String STR_FOUR = "4";
	public static final String STR_FIVE = "5";
	
	public static Integer ZERO = 0;
	public static Integer ONE = 1;
	public static Integer TWO = 2;
	public static Integer THREE = 3;
	public static Integer FOUR = 4;
	public static Integer FIVE = 5;
	public static Integer SIX = 6;

	public static Integer TEXTAREA_LIMIT = 512;
	public static Integer QUERY_LIMIT = 2048;

	public static String SESN_USER = "SESN_USER";
	
	public static String comnDateFormatStr = "yyyy-MM-dd HH:mm:ss";
	

	public static String encode = "UTF-8";
	public static String CHARCODE = "UTF-8";
	  
	public static String commType_http = "http";
	public static String designSplit ="	";

	public static final String Y = "Y";
	public static final String N = "N";
	
	public static final String S = "S";
	public static final String F = "F";

	public static final String DESC = "DESC";
	public static final String ASC = "ASC";
	public static final String VARCHAR = "VARCHAR";
	public static final String CHAR = "CHAR";
	public static final String NUMERIC = "NUMERIC";
	public static final String TIMESTAMP = "TIMESTAMP";
	public static final String QUERY_EXACT = "queryExact";
	public static final String QUERY_BLUR = "queryBlur";
	

	public static final String MSG = "msg";
	
	// 常用字符串
	public static final String NEWLINE = "\n";
	

	public static final String REQUESTMAPPING = "RequestMapping";
	public static final String RESOURCEPAGEANNOTION = "ResourcePageAnnotion";
	public static final String RESOURCEANNOTION = "ResourceAnnotion";
	public static final String ANNOTIONMAPMETHODKEY = "method"; // 方法上的注解
	public static final String ANNOTIONMAPCLASSKEY = "class"; // 类上的注解
}

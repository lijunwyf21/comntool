package com.comntool.util.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comntool.util.utility.bean.CodeParseObj;

public class GraphqlUtil {
	private static final Logger log = LoggerFactory.getLogger(GraphqlUtil.class);

	public static void main(String[] args) {
		// java2graphql();
		java2graphqlRetnList();

	}


	public static void java2graphql() {
		String filePre = StringUtils.startsWith(System.getProperty("os.name").toLowerCase(), "win")?"D:\\datum\\code\\automake\\graphql\\"
				: "/data/app/conf/";
		String outputFile = filePre+"output.txt";
		String[] javaConf = {"订单返回信息,OrderDTO,  ,"+filePre+"input.txt"};
		StringBuilder sb = new StringBuilder();
		for(String str : javaConf) {
			String[] strs = str.split(",");
			try {
				String graphsql = java2graphql(strs[0], strs[1], strs[2], strs[3],null, null );
				sb.append(graphsql);
			}catch(Exception e) {
				log.info(e.getMessage(), e);
			}
		}
		Comn.str2file(sb.toString(), outputFile);
		Comn.pl(sb.toString());
	}



	public static void java2graphqlRetnList() {
		String filePre = StringUtils.startsWith(System.getProperty("os.name").toLowerCase(), "win")?"D:\\datum\\code\\automake\\graphql\\"
				: "/data/app/conf/";
		String outputFile = filePre+"output.txt";
		String[] javaConf = {"订单返回信息,OrderDTO,      ,"+filePre+"input.txt"};
		StringBuilder sb = new StringBuilder();
		for(String str : javaConf) {
			String[] strs = str.split(",");
			try {
				String content = Comn.file2String( strs[3]);
				String graphsql = java2graphqlRetnList(strs[0], strs[1], strs[2], content);
				sb.append(graphsql);
			}catch(Exception e) {
				log.info(e.getMessage(), e);
			}
		}
		Comn.str2file(sb.toString(), outputFile);
		Comn.pl(sb.toString());
	}
	
	/**
	 * 将java文件转成graphql文件的内容
	 * java文件，只能包含变量定义的部分
	 * @param entyName 实体名称
	 * @param indent 缩进，例：“  ”
	 * @param content 读取的java文件内容
	 * @param startLine 读取的java文件内容的开始行数。从0开始
	 * @param endLine 读取的java文件内容的结束行数。从0开始
	 * @return 转换的graphql的对象定义
	 */
	public static String java2graphql(String entyRemark,String entyName, String indent, String content, 
			Integer startLine,  Integer endLine) {
		String typeMapStr = "biginteger:BigInteger;bigdecimal:BigDecimal;byte:Byte;"
				+ "short:Short;int:Int;long:Long;float:Float;double:Float;"
				+ "string:String;boolean:Boolean";
		Map<String, String> typeMap = Comn.str2map(typeMapStr, ";",":");
		startLine = startLine==null? 2 : startLine;
		endLine = endLine == null? Integer.MAX_VALUE : endLine;
		
		// 例：entyDesc:订单查询请求;OrderReqDTO
		String firstRow= content.substring(0, content.indexOf("\n")).trim();
		if(StringUtils.startsWith(firstRow, "entyDesc")) {
			String fileDesc = firstRow.substring(firstRow.indexOf(":")+1);
			entyRemark = fileDesc.split(";")[0];
			entyName = fileDesc.split(";")[1];
			content = content.substring(content.indexOf("\n"));
		}
		List<CodeParseObj> codeParses = parseJavaVar(content, startLine, endLine );
		StringBuilder sb = new StringBuilder();
		sb.append("# ").append(entyRemark);
		sb.append("\ntype ").append(entyName).append("{");
		for(CodeParseObj codeParse : codeParses) {
			if(StringUtils.equals(codeParse.getSort(), CodeParseObj.SORT_REMARK)){
				if(StringUtils.isBlank(codeParse.getContent())) {
					continue;
				}
				sb.append("\n").append(indent).append("# ").append(codeParse.getContent());
			}else if(StringUtils.equals(codeParse.getSort(), CodeParseObj.SORT_VAR)) {
				String contentStr = codeParse.getContent().trim();
				int index = contentStr.lastIndexOf(" ");
				String typeCodeStr = contentStr.substring(0, index).trim();
		        String typeStr = typeMap.get(typeCodeStr);
		        typeStr = StringUtils.isBlank(typeStr)? typeCodeStr : typeStr;
				String varName = contentStr.substring(index+1).trim();;
				sb.append("\n").append(indent).append(varName).append(": ").append(typeStr).append("");

			}
		}
		sb.append("\n}\n\n");
		return sb.toString();
	}

	


	/**
	 * 将java文件转成graphql文件的内容
	 * java文件，只能包含变量定义的部分
	 * @param entyName 实体名称
	 * @param indent 缩进，例：“  ”
	 * @param javaFilePath
	 * @return
	 */
	public static String java2graphqlRetnList(String entyRemark,String entyName, String indent, String javaFilePath) {
		String typeMapStr = "biginteger:BigInteger;bigdecimal:BigDecimal;byte:Byte;short:Short;int:Int;long:Long;float:Float;double:Float;string:String;boolean:Boolean";
		Map<String, String> typeMap = Comn.str2map(typeMapStr, ";",":");
		String content = Comn.file2String(javaFilePath);
		// 例：entyDesc:订单查询请求;OrderReqDTO
		String firstRow= content.substring(0, content.indexOf("\n")).trim();
		if(StringUtils.startsWith(firstRow, "entyDesc")) {
			String fileDesc = firstRow.substring(firstRow.indexOf(":")+1);
			entyRemark = fileDesc.split(";")[0];
			entyName = fileDesc.split(";")[1];
			content = content.substring(content.indexOf("\n"));
		}
		List<CodeParseObj> codeParses = parseJavaVar(content);
		StringBuilder sb = new StringBuilder();
		sb.append("# ").append(entyRemark);
		sb.append("\ntype ").append(entyName).append("{");
		for(CodeParseObj codeParse : codeParses) {
			if(StringUtils.equals(codeParse.getSort(), CodeParseObj.SORT_REMARK)){
				if(StringUtils.isBlank(codeParse.getContent())) {
					continue;
				}
				sb.append("\n").append(indent).append("# ").append(codeParse.getContent());
			}else if(StringUtils.equals(codeParse.getSort(), CodeParseObj.SORT_VAR)) {
				String contentStr = codeParse.getContent().trim();
				int index = contentStr.lastIndexOf(" ");
				String typeCodeStr = contentStr.substring(0, index).trim();
				String typeStr = typeMap.get(typeCodeStr);
				typeStr = StringUtils.isBlank(typeStr)? typeCodeStr : typeStr;
				String varName = contentStr.substring(index+1).trim();;
				sb.append("\n").append(indent).append(varName);

			}
		}
		sb.append("\n}\n\n");
		return sb.toString();
	}

	
	/**
	 * 解析java中的变量及注释
	 * @param content
	 * @return
	 */
	public static List<CodeParseObj> parseJavaVar(String content){
		return parseJavaVar(content, 0, Integer.MAX_VALUE);
	}
	/**
	 * 解析java中的变量及注释
	 * @param content
	 * @return
	 */
	public static List<CodeParseObj> parseJavaVar(String content, Integer startLine,  Integer endLine) {
		List<String> codeSeg = Comn.str2list(content, "\\\n", startLine, endLine);
		Iterator<String> it = codeSeg.iterator();
		while(it.hasNext()){
		    String x = it.next();
		    if(StringUtils.isBlank(x)){
		        it.remove();
		    }
		}

		List<CodeParseObj> codeObjList = new ArrayList<>();
		for(String str : codeSeg) {
			CodeParseObj codeObj = new CodeParseObj();
			// 判断是不是注释
			if(str.startsWith("//")) {
				codeObj.setFormat(CodeParseObj.REMARK_START);
				codeObj.setSort(CodeParseObj.SORT_REMARK);

				String codeSegStr = str.replaceFirst("//", "");
				codeObj.setContent(codeSegStr.trim());
				codeObjList.add(codeObj);
				continue;
			}else if(str.startsWith("/*")) {
				codeObj.setFormat(CodeParseObj.REMARK_START);
				codeObj.setSort(CodeParseObj.SORT_REMARK);

				String codeSegStr = str;
				codeSegStr = codeSegStr.replaceFirst("/", "");
				codeSegStr = codeSegStr.replaceFirst("\\*", "");
				codeSegStr = codeSegStr.replaceFirst("\\*", "");
				codeSegStr = StringUtils.removeEnd(codeSegStr, "*/");
				if(StringUtils.isBlank(codeSegStr)) {
					continue;
				}
				codeObj.setContent(codeSegStr.trim());
				codeObjList.add(codeObj);
				continue;
			}else if(str.startsWith("*")&& !str.endsWith("*/")) {
				codeObj.setFormat(CodeParseObj.REMARK_MIDDLE);
				codeObj.setSort(CodeParseObj.SORT_REMARK);

				String codeSegStr = str.replaceFirst("\\*", "");
				codeSegStr = StringUtils.removeEnd(codeSegStr, "*/");
				codeObj.setContent(codeSegStr.trim());
				codeObjList.add(codeObj);
				continue;
			}else if(str.endsWith("*/")) {
				codeObj.setFormat(CodeParseObj.REMARK_END);
				codeObj.setSort(CodeParseObj.SORT_REMARK);

				String codeSegStr = StringUtils.removeEnd(str, "*/");
				codeObj.setContent(codeSegStr.trim());
				codeObjList.add(codeObj);
				continue;
			}
			
			// 判断是不是变量
			if(str.startsWith("public") || str.startsWith("protected") || str.startsWith("private") ) {
			    // 剥离结尾的注释
				String[] varCode = str.split("//");
				if(varCode.length>1) {
					// 有注释，先添加一个注释
					CodeParseObj codeRemark = new CodeParseObj();
					codeRemark.setSort(CodeParseObj.SORT_REMARK);
					codeRemark.setFormat(CodeParseObj.SINGLE_REMARK);
					codeRemark.setContent(varCode[1]);
					codeObjList.add(codeRemark);
				}
				
				str = varCode[0];// 留下代码
				str = str.split(";")[0].trim();// 去掉结束符号
				str = str.split("=")[0].trim();// 去掉默认值
				str = StringUtils.removeStart(str, "public").trim();// 去掉java关键字
				str = StringUtils.removeStart(str, "protected").trim();// 去掉java关键字
				str = StringUtils.removeStart(str, "private").trim();// 去掉java关键字
				str = StringUtils.removeStart(str, "static").trim();// 去掉java关键字
				str = StringUtils.removeStart(str, "transient").trim();// 去掉java关键字
				str = StringUtils.removeStart(str, "violate").trim();// 去掉java关键字
				// str = StringUtils.removeStart(str, "final").trim();// 去掉java关键字
				if(str.startsWith("final")) {
					// 忽略掉常量
					continue;
				}
				// 剩下变量及变量类型

				codeObj.setSort(CodeParseObj.SORT_VAR);
				codeObj.setContent(str);// 剩下的代码例如： String str
				codeObjList.add(codeObj);
				continue;
			}
		}
		return codeObjList;
	}
	
}

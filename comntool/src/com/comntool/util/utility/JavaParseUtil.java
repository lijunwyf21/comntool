package com.comntool.util.utility;


import com.comntool.constcla.comn.ComnConst;
import static com.comntool.constcla.comn.ComnConst.COMMA;
import static com.comntool.constcla.comn.ComnConst.SINGLE_QUOTE;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class JavaParseUtil {
	private static final Logger log = LoggerFactory.getLogger(CodeUtil.class);
	public static void main(String[] args) {
		// enum2transExcel();
		// enum2transExcel_returnMsg();
		excel2transSql();
	}

	public static void enum2transExcel() {
		String javaDir = "D:\\datum\\code\\git\\fanfu\\java\\ontrade-parent\\ontrade-common\\src\\main\\java\\com\\ontrade\\common\\constants\\BizCodeEnum.java";
		String outDir = "D:\\datum\\code\\git\\fanfu\\doc\\ontrade-doc\\交易所联盟\\翻译\\返回码翻译\\";
		String old = outDir+"返回码_zh_中文.xlsx";
		old = null;
		try {
			List<String>  enumParse= enumParse(Comn.file2String(javaDir), Integer.MAX_VALUE);
			enum2transExcel(old, outDir, enumParse, "en","英文", "errorCode", null);
		}catch(Exception e) {
			log.info(e.getMessage(), e);
		}
	}
	
	/**
	 * 返回报文翻译
	 * - 中文简体：zh
		- 英文（包含en-us、en-gb等情况）：en
	 */
	public static void enum2transExcel_returnMsg() {
		String javaDir = "D:\\datum\\code\\git\\fanfu\\java\\ontrade-parent\\ontrade-common\\src\\main\\java\\com\\ontrade\\common\\constants\\DictMsgEnum.java";
		String outDir = "D:\\\\datum\\\\code\\\\git\\\\fanfu\\\\doc\\\\ontrade-doc\\\\交易所联盟\\\\翻译\\\\返回报文翻译\\\\";
		String old = outDir+"返回报文_zh_中文.xlsx";
		old = null;
		String contsFilePath = "D:\\datum\\code\\git\\fanfu\\java\\ontrade-parent\\ontrade-common\\src\\main\\java\\com\\ontrade\\common\\constants\\DictTypeConstants.java";
		Map<String, String> strConvert = constParse(Comn.file2String(contsFilePath), 63);
		// Map<String, String> map = Comn.str2map(strConvert, ComnConst.SEMICOLON, ComnConst.CLN);
		try {
			List<String>  enumParse= enumParse(Comn.file2String(javaDir), 106);
			enum2transExcel(old, outDir, enumParse, "en","英文", "returnMsg", strConvert);
		}catch(Exception e) {
			log.info(e.getMessage(), e);
		}
	}
	

	public static void excel2transSql() {
		String excelDir = "D:\\datum\\data\\appdata\\java\\local\\";
		String excelFileName ="trans_en_英文_27153319.xlsx";
		String trans_sort = "errorCode";
		excelDir="D:\\datum\\code\\git\\fanfu\\doc\\ontrade-doc\\交易所联盟\\翻译\\返回码翻译\\";
		excelFileName="返回码_zh_中文.xlsx";
		String lang = "zh,中文";
		

		/* 
		excelDir="D:\\datum\\code\\git\\fanfu\\doc\\ontrade-doc\\交易所联盟\\翻译\\返回报文翻译\\";
		excelFileName="返回报文_zh_中文.xlsx";
		trans_sort = "returnMsg";
		
		
		excelDir="D:\\datum\\code\\git\\fanfu\\doc\\ontrade-doc\\交易所联盟\\翻译\\返回码翻译\\";
		excelFileName="返回码_en_英文.xlsx";
		trans_sort = "errorCode";
		lang = "en,英文";
		
		excelDir="D:\\datum\\code\\git\\fanfu\\doc\\ontrade-doc\\交易所联盟\\翻译\\返回报文翻译\\";
		excelFileName="返回报文_en_英文.xlsx";
		trans_sort = "returnMsg";
		lang = "en,英文";
		 */

		excelDir="D:\\datum\\code\\git\\fanfu\\doc\\ontrade-doc\\交易所联盟\\翻译\\返回码翻译\\";
		excelFileName="返回码_en_英文.xlsx";
		trans_sort = "errorCode";
		lang = "en,英文";
		// excel2transSql(excelDir+excelFileName, 0, 3, "zh","中文", trans_sort);
		excel2transSql(excelDir+excelFileName, 0, 3, lang.split(",")[0], lang.split(",")[1], trans_sort);
		
	}
	/**
	 * 解析java中的变量及注释。枚举参数只能在同一行
	 * @param content
	 * @return
	 */
	public static List<String> enumParse(String content, int endLine) {
		if(StringUtils.isBlank(content)) {
			return null;
		}
		List<String> codeSeg = Comn.str2list(content, "\\\n");
		codeSeg = removeRemark(codeSeg, endLine);
		Iterator<String> it = codeSeg.iterator();
		List<String> codeSegEnum = new ArrayList<>();
		int i=0;
		while(it.hasNext()){ // 解析枚举
			++i;
			if(i>endLine) {
				return codeSegEnum;
			}
		    String x = it.next().trim();
		    int open = x.indexOf(ComnConst.OPEN_PAREN);
		    int close = x.indexOf(ComnConst.CLOSE_PAREN);
		    if(open>-1&& close > -1 && close > open) {
		    	String enumParam = x.substring(open+1, close);
		    	if(StringUtils.isBlank(enumParam)) {
		    		continue;
		    	}
		    	List<String> vals = Comn.str2list(enumParam, ",");
		    	boolean ignore = false;
		    	filterMethod : for(String val : vals) {
		    		if(val.trim().contains(ComnConst.BLANK)) {
		    			ignore = true;
		    			break filterMethod;
		    		}
		    	}
		    	if(ignore== Boolean.FALSE) {
		    		codeSegEnum.add(enumParam.trim());
		    	}
		    }
		}
		return codeSegEnum;
	}

	/**
	 * 解析常量成属性及值的map
	 * @param content
	 * @return
	 */
	public static Map<String, String> constParse(String content, int endLine) {
		Map<String, String> map = new HashMap<>();
		if(StringUtils.isBlank(content)) {
			return map;
		}
		String keyStr = "String";
		List<String> codeSeg = Comn.str2list(content, "\\\n");
		codeSeg = removeRemark(codeSeg, endLine);
		Iterator<String> it = codeSeg.iterator();
		int i=0;
		while(it.hasNext()){ // 解析枚举
			++i;
			if(i>endLine) {
				return map;
			}
		    String x = it.next().trim();
		    
		    int open = x.indexOf(keyStr);
		    int close = x.indexOf(ComnConst.SEMICOLON);
		    if(open>-1 && close > -1 && close > open) {
		    	String constStr = x.substring(open+keyStr.length(), close);
		    	if(StringUtils.isBlank(constStr)) {
		    		continue;
		    	}
		    	List<String> vals = Comn.str2list(constStr, ComnConst.EQUAL_SIGN);
		    	if(vals.size()>1) {
		    		map.put(vals.get(0), peelDoubleQuote(vals.get(1)));
		    	}
		    }
		}
		return map;
	}
	
	/**
	 * 将枚举的值转成“用于填写翻译”的excel。
	 * excel的行列都是从0开始
	 * @param inPath 已经翻译好的excel
	 * @param outPath 新翻译填写excel
	 * @param enumStrs
	 * @param lang
	 * @param langDesc
	 * @param trans_sort
	 * @param sortConvert 枚举分类的转换
	 */
	public static String enum2transExcel(String inPath, String outDir, List<String> enumStrs, 
			String lang, String langDesc, String trans_sort, Map<String, String> sortConvert) {
		if(CollectionUtils.isEmpty(enumStrs)) {
			return null;
		}
		String outPath = outDir+"trans_"+lang+"_"+langDesc+"_"+Comn.getDateStr(new Date(), Comn.dateFormat_dayTime)+".xlsx";
		log.info("outPath={}", outPath);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(lang+"_"+langDesc);
		sheet.setZoom(80);

		XSSFCellStyle styleHead = defaultExcelHeadStyle(wb);
		XSSFCellStyle style = defaultExcelTextStyle(wb);

		sheet.setColumnWidth(1, 30 * 256);
		for(int i=2, leng = 10; i<leng; ++i) {
			sheet.setColumnWidth(i, 40 * 256);
		}
		Map<String, String> map = excel2map(inPath, 0, 3, 1, 3);

		int j = 0; // 行
		int c = 0; // 列
		XSSFRow row = null;

		row = sheet.createRow(++j);
		defautCell(row, ++c, styleHead, "数目： " + enumStrs.size());
		defautCell(row, ++c, styleHead, "语言： " + lang);
		defautCell(row, ++c, styleHead, "语言名称： " + langDesc);
		defautCell(row, ++c, styleHead, " ");
		defautCell(row, ++c, styleHead, " ");
		defautCell(row, ++c, styleHead, " ");
		

		row = sheet.createRow(++j);
		c = 0;
		defautCell(row, ++c, styleHead, "翻译标识");
		defautCell(row, ++c, styleHead, "中文描述");
		defautCell(row, ++c, styleHead, "翻译值");
		defautCell(row, ++c, styleHead, "参考翻译");
		defautCell(row, ++c, styleHead, "中文描述优化");
		
		
		for(String enumStr : enumStrs) {
			String[] strs = enumStr.split(",");
			if(strs.length<2) {
				log.info("lack val, enumStr={}", enumStr);
				continue;
			}
			row = sheet.createRow(++j);
			if(StringUtils.equals(trans_sort, "errorCode")) {
				// BizCodeEnum 10000, "客户端异常"
				String trans_mark = strs[0];
				trans_mark = replaceStr(sortConvert, trans_mark);
				defautCell(row, 1, style, trans_mark);
				String val =peelStr(strs[1], "\"");
				defautCell(row, 2, style, val);
				
				String oldVal = map!=null && StringUtils.isNotBlank(map.get(trans_mark))? map.get(trans_mark) : "";
				defautCell(row, 3, style, oldVal);
				defautCell(row, 4, style, " ");
				defautCell(row, 5, style, val);
			}else if(StringUtils.equals(trans_sort, "returnMsg")) {
				// BizMsgEnum "direction", "0","买", "买卖方向"
				if(strs.length<4) {
					continue;
				}
				try {
					String trans_mark = replaceStr(sortConvert, peelDoubleQuote(strs[0]));
					trans_mark = peelDoubleQuote(trans_mark+ComnConst.UNDERSCORE+ peelDoubleQuote(strs[1]));
					defautCell(row, 1, style, trans_mark);
					String val =peelDoubleQuote(strs[2]);
					defautCell(row, 2, style, val);
					
					String oldVal = map!=null && StringUtils.isNotBlank(map.get(trans_mark))? map.get(trans_mark) : "";
					defautCell(row, 3, style, oldVal);
					defautCell(row, 4, style, " ");
					defautCell(row, 5, style, val+ComnConst.UNDERSCORE+ peelDoubleQuote(strs[3]));
				}catch(Exception e) {
					log.info(e.getMessage()+","+enumStr, e);
				}
			}
			defautCell(row, 6, style, " ");
		}
		writeExcel(outPath, wb);
		
		return outPath;
	}

	/**
	 * 将“用于填写翻译”的excel转成插入表的sql
	 * @param excelPath
	 * @param sheetIndex
	 * @param startRowNum
	 * @param lang
	 * @param langDesc
	 * @param trans_sort
	 * @return 拼装好的，可执行的sql语句
	 */
	public static String excel2transSql(String excelPath, int sheetIndex, int startRowNum,
			String lang, String langDesc, String trans_sort) {
		String start = "insert into ngdesuam.t_ot_translate_conf (ID, trans_sort, trans_mark, lang_mark, zh_desc, trans_val, sequence_order, gmt_created) values (";
		String end = ");";
		String timestampStart = "to_timestamp('";
		String timestampEnd = "', 'yyyy-mm-dd hh24:mi:ss.ff3')";
		StringBuilder sb = new StringBuilder();
		
		if(StringUtils.isBlank(excelPath)) {
			return null;
		}
		XSSFRow row = null;
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(excelPath);
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			for(int i=startRowNum, leng = sheet.getLastRowNum()+1;i<leng; ++i) {
				row = sheet.getRow(i);
				int startColIndex = 0;
				String transMark = getCellStr(row, startColIndex+1);
				if(StringUtils.isBlank(transMark)) {
					break;
				}
				sb.append(start);
				sb.append(SINGLE_QUOTE).append(Comn.getId()).append(SINGLE_QUOTE).append(COMMA);
				sb.append(SINGLE_QUOTE).append(trans_sort).append(SINGLE_QUOTE).append(COMMA);
				sb.append(SINGLE_QUOTE).append(transMark).append(SINGLE_QUOTE).append(COMMA);
				sb.append(SINGLE_QUOTE).append(lang).append(SINGLE_QUOTE).append(COMMA);
				sb.append(SINGLE_QUOTE).append(row.getCell(startColIndex+2).getStringCellValue()).append(SINGLE_QUOTE).append(COMMA);
				sb.append(SINGLE_QUOTE).append(row.getCell(startColIndex+3).getStringCellValue()).append(SINGLE_QUOTE).append(COMMA);
				sb.append(i).append(COMMA);
				sb.append(timestampStart).append(Comn.getDateStrMilli()).append(timestampEnd).append(end);
				sb.append("\r\n");
			}
			log.info("inert sql=\n{}", sb.toString());
			return sb.toString();
		}catch(Exception e) {
			log.info(e.getMessage(), e);
		}finally {
			try {
				wb.close();
			}catch(Exception e) {
				log.info(e.getMessage(), e);
			}
		}
		
		return null;
	}

	public static List<String> removeRemark(List<String> codeSeg, int endLine) {
		Iterator<String> it = codeSeg.iterator();
		boolean remarkStart = false;
		int i=0;
		// 删除注释
		removeRemark : while(it.hasNext()){
			++i;
			if(i>endLine) {
				it.remove();;
				break removeRemark;
			}
		    String x = it.next().trim();
		    boolean hasRemove = false;
		    if(StringUtils.isBlank(x)|| StringUtils.startsWith(x, "//")){
		        it.remove();// 删除空行，注释
		    	hasRemove = true;
		    }
		    if(!hasRemove && StringUtils.startsWith(x, "/*")) {// 删除开头注释
		    	remarkStart = !StringUtils.endsWith(x, "*/");
		    	it.remove();
		    	hasRemove = true;
		    }
		    if(!hasRemove && StringUtils.endsWith(x, "*/")) {// 删除末尾注释
		    	remarkStart = false;
		    	it.remove();
		    	hasRemove = true;
		    }
		    if(!hasRemove && remarkStart) {// 删除中间注释
		    	it.remove();
		    	hasRemove = true;
		    }
		}
		return codeSeg;
	}
	/**
	 * 去掉首尾的双引号
	 * @param orig
	 * @param peel
	 * @return
	 */
	public static String replaceStr(Map<String,String> map, String orig) {
		if(map==null) {
			return orig;
		}
		String dist = map.get(orig);
		return dist==null? orig : dist;
	}
	
	/**
	 * 去掉首尾的双引号
	 * @param orig
	 * @param peel
	 * @return
	 */
	public static String peelDoubleQuote(String orig) {
		return peelStr(orig, ComnConst.DOUBLE_QUOTE);
	}

	/**
	 * 去掉首尾的字符串。比如双引号
	 * @param orig
	 * @param peel
	 * @return
	 */
	public static String peelStr(String orig, String peel) {
		if(orig== null) {
			return orig;
		}
		String val = orig.trim();
		val = StringUtils.removeStart(val, peel);
		val = StringUtils.removeEnd(val, peel);
		return val;
	}
	public static Map<String, String> excel2map(String inPath, int sheetIndex, int startRowNum, int keyCol, int valCol) {
		Map<String, String> map = new HashMap<>();
		if(StringUtils.isBlank(inPath)) {
			return map;
		}
		XSSFRow row = null;
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(inPath);
			if(new File(inPath).exists() == Boolean.FALSE) {
				return map;
			}
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			log.info("sheet name={}, row num={}", sheet.getSheetName(), sheet.getLastRowNum());
			for(int i=startRowNum, leng = sheet.getLastRowNum()+1; i<leng; ++i) {
				row = sheet.getRow(i);
				String mark = getCellStr(row, keyCol);
				String trans = getCellStr(row, valCol);
				if(StringUtils.isBlank(mark)) {
					break;
				}
				map.put(mark, trans);
			}
			log.info("read excel={}, size={}", inPath, map.size());
		}catch(Exception e) {
			log.info(e.getMessage(), e);
			return map;
		}finally {
			try {
				if(wb!=null) {
					wb.close();
				}
			}catch(Exception e) {
				log.info(e.getMessage(), e);
			}
		}
		return map;
	}
	public static void writeExcel(String outPath, XSSFWorkbook wb) {
		if(StringUtils.isNotBlank(outPath)) {
			OutputStream os = null;
			File excelFile = new File(outPath);
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
		log.info("write excel={}", outPath);
	}
	
	public static XSSFCell defautCell(XSSFRow row, int col, XSSFCellStyle style, String val) {
		XSSFCell cell = row.createCell(col);
		cell.setCellStyle(style);
		cell.setCellValue(val);
		cell.setCellType(CellType.STRING);
		return cell;
	}

	/**
	 * 默认头样式
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle defaultExcelHeadStyle(XSSFWorkbook wb) {
		//生成一个字体
        XSSFFont font=wb.createFont();
        font.setBold(true);
        // font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setColor(new XSSFColor(new Color(0x6f, 0x59, 0x9c)));
        font.setFontHeightInPoints((short)12);
        
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
		XSSFDataFormat format = wb.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		return style;
	}

	/**
	 * 获取单元格，并转成字符串格式
	 * @param row
	 * @param colNum
	 * @return
	 */
	public static String getCellStr(XSSFRow row, int colNum) {
		XSSFCell cell = row.getCell(colNum);
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}
	/**
	 * 获取单元格，并转成字符串格式
	 * @param row
	 * @param colNum
	 * @return
	 */
	public static XSSFCell getStrCell(XSSFRow row, int colNum) {
		XSSFCell cell = row.getCell(colNum);
		cell.setCellType(CellType.STRING);
		return cell;
	}
	/**
	 * 默认文本样式
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle defaultExcelTextStyle(XSSFWorkbook wb) {
		//生成一个字体
        XSSFFont font=wb.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// HSSFColor.WHITE.index HSSFColor.VIOLET.index //字体颜色
        font.setFontHeightInPoints((short)12);
        
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

		XSSFDataFormat format = wb.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		return style;
	}
}
package com.comntool.util.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	public static void main(String[] args) {
		Pattern p = Pattern.compile("(32)");
		Matcher c = p.matcher("([\\d])");
		c.matches();
		
	}
	
	/**
	 * 只要匹配一个，就返回
	 * @param src 被匹配的数据
	 * @param regex 需要匹配的正则表达式
	 * @return
	 */
	public static String getMatchOne(String src, String regex){
		Pattern p = Pattern.compile(regex);
		Matcher c = p.matcher(src);
		c.matches();
		while(c.find()) {
			String resultStr = c.group();
		/*if(resultStr != null && StringUtils.isNotBlank(resultStr)){
				try {
					resultStr = HttpUtils.chgCharset(resultStr, "GBK","UTF8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			return resultStr;
		}
		return null;
	}

}

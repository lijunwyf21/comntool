package com.comntool.constcla.comn;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.comntool.util.utility.Comn;

public class RegexObj {
	/**
	public static RegexObj num = new RegexObj("[\\d]*","必须为数字");
	public static RegexObj en_num_rod = new RegexObj("[\\w_\\-]*","必须为英文，数字，下划线或者横杠");
	public static RegexObj en_num_unde = new RegexObj("[\\w_]*","必须为英文，数字，下划线");
	public static RegexObj en_num = new RegexObj("[\\w]*","必须为英文，数字");
	*/
	public static RegexObj num = new RegexObj("[\\d]*", "必须为数字");
	public static RegexObj en_num_rod = new RegexObj("[\\w_\\-]*", "必须为英文，数字，下划线或者横杠");
	public static RegexObj en_num_unde = new RegexObj("[\\w_]*", "必须为英文，数字，下划线");
	public static RegexObj en_num = new RegexObj("[\\w]*", "必须为英文，数字");

	
	// 正则表达式标识
	private String remark ="";
	// 正则表达式
	private String regexFormat ="";
	// 匹配失败的提示
	private String mismatchTip= "";
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRegexFormat() {
		return regexFormat;
	}

	public void setRegexFormat(String regexFormat) {
		this.regexFormat = regexFormat;
	}

	public String getMismatchTip() {
		return mismatchTip;
	}

	public void setMismatchTip(String mismatchTip) {
		this.mismatchTip = mismatchTip;
	}

	private RegexObj(){};
	private RegexObj(String regexFormat, String mismatchTip) {
		super();
		this.regexFormat = regexFormat;
		this.mismatchTip = mismatchTip;
	}

	public static List<RegexObj> parseRegexList(String regexConf) {
		if(StringUtils.isBlank(regexConf)){
			return null;
		}
		List<RegexObj> list= new ArrayList<RegexObj>();
		regexConf = Comn.trim(regexConf);
		String[] strs= regexConf.split("\n");
		for(String str: strs){
			if(StringUtils.isBlank(str) || str.startsWith("标识")){
				continue;
			}
			RegexObj obj = parseRegex(str);
			if(obj!= null){
				list.add(obj);
			}
		}
		return list;
	}
	
	
	/**
	 * 解析一行正则表达式的配置
	 num	[\\d]*	必须为数字	默认为0
	 en_num_rod	[\\w_\\-]*	必须为英文，数字，下划线或者横杠	-
	 * @param regexConf
	 * @return
	 */
	public static RegexObj parseRegex(String str) {
		if(StringUtils.isBlank(str)){
			return null;
		}
		String[] strs2 = str.split("	");
		if(strs2.length>=3){
			RegexObj obj = new RegexObj();
			obj.setRemark(strs2[0]);
			obj.setRegexFormat(strs2[1]);
			obj.setMismatchTip(strs2[2]);
			return obj;
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package com.comntool.util.utility.bean;

import java.io.Serializable;
import java.util.List;

public class CodeParseObj implements Serializable{
	private static final long serialVersionUID = 899586773088995L;
	

	public static final String SORT_REMARK = "remark";
	public static final String SORT_VAR = "var";

	public static final String SINGLE_REMARK = "singleRemark";
	public static final String REMARK_START = "singleStart";
	public static final String REMARK_MIDDLE = "singleMiddle";
	public static final String REMARK_END = "singleEnd";

	
	private String content;// 内容
	private String sort;// 分类
	private String format; // 格式
	private List<CodeParseObj> remarks = null;
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public List<CodeParseObj> getRemarks() {
		return remarks;
	}
	public void setRemarks(List<CodeParseObj> remarks) {
		this.remarks = remarks;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	} 
	
	
}

package cn.com.mx.badword.entity;

import java.io.Serializable;

/**
 * @Description 传入需要校验信息body字段的类
 * @author yuemingyuan
 * @date 2016年11月16日 11:31:53
 */

public class BadwordDto implements Serializable {

	private static final long serialVersionUID = -6208778777996080068L;
	
	private String keywords = "";
	// 级别，数值越低级别越高 0：联合校验，返回所有级别的校验结果(默认为0) 1：严格校验 2：普通校验
	private int level = 0;
	
	public BadwordDto() {
		super();
	}
	public BadwordDto(String keywords, int level) {
		super();
		this.keywords = keywords;
		this.level = level;
	}
	@Override
	public String toString() {
		return "BadwordDto [keywords=" + keywords + ", level=" + level + "]";
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
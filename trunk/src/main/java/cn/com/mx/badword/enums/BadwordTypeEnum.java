/**
	 * @Description 校验类别枚举
	 * @author yuemingyuan
	 * @date 2016年11月14日 下午15:06:29
	 */
package cn.com.mx.badword.enums;

public enum BadwordTypeEnum {
	//组合校验 (1+2)
	COMBINED_VALIDATION(0),
	//严格校验
	STRICT_VALIDATION(1),
	//普通校验
	NORMAL_VALIDATION(2);
	
	
	public int value;
	
	BadwordTypeEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
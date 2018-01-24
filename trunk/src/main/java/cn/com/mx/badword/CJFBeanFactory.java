package cn.com.mx.badword;

/**
 * 接口的工厂
 * @author yuemingyuan
 * @lastEdit 2017年2月18日 17:14:54
 *
 */
public class CJFBeanFactory {
	
	private static ChineseJF chineseJF=new cn.com.mx.badword.impl.ChineseJFImpl();
	
	public static ChineseJF getChineseJF(){
		return chineseJF;
	}

}

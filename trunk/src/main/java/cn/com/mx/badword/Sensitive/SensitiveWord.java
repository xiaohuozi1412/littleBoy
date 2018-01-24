package cn.com.mx.badword.Sensitive;

import java.io.Serializable;
import java.util.Map;

import cn.com.mx.badword.enums.BadwordTypeEnum;

/**
 * @Description 单例加载词语
 * @author yuemingyuan
 * @date 2017年2月24日 下午6:16:37
 */
@SuppressWarnings("all")
public class SensitiveWord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7040374110291751685L;
	
	static Map sensitiveWordMap = null;
	static Map strictSensitiveWordMap = null;
	
	/**
	 * @Description 构造函数，初始化敏感词库 
	 * @author yuemingyuan
	 * @date 2017年2月24日 下午6:16:29
	 * @return
	 */
	public static SensitivewordFilter initMap(String variety){
		if(String.valueOf(BadwordTypeEnum.STRICT_VALIDATION.getValue()).equals(variety)){
			 if(strictSensitiveWordMap == null){
					synchronized(SensitiveWord.class){
						if(strictSensitiveWordMap == null){
							strictSensitiveWordMap = new SensitiveWordInit().initKeyWord(variety);
						}
					}
				}
			 }
		else {
			if(sensitiveWordMap == null){
			synchronized(SensitiveWord.class){
				if(sensitiveWordMap == null){
					sensitiveWordMap = new SensitiveWordInit().initKeyWord(variety);
				}
			}
		}
			}
		
		return new SensitivewordFilter();
	}
	
	/**
	 * @Description 二次加载敏感词 
	 * @author yuemingyuan
	 * @date 2017年2月24日 下午6:18:29
	 * @return
	 */
	public static boolean secondInitMap(String variety){
		sensitiveWordMap = null;
		sensitiveWordMap = new SensitiveWordInit().initKeyWord(variety);
		if(sensitiveWordMap!=null&&sensitiveWordMap.size()>0){
			return true;
		}else{
			return false;
		}
	}
}
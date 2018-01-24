package cn.com.mx.badword.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import cn.com.mx.badword.CJFBeanFactory;
import cn.com.mx.badword.ChineseJF;
import cn.com.mx.badword.Sensitive.SensitiveWord;
import cn.com.mx.badword.entity.BadwordDto;
import cn.com.mx.badword.enums.BadwordTypeEnum;
import cn.com.mx.badword.utils.SpecialCharacters;

/**
 * @Description badWord 
 * @author yuemingyuan
 * @date 2016年11月16日 10:00:00
 */
@SuppressWarnings("unused")
@WebServlet("/ExtBadWordServlet")
public class ExtBadWordServlet extends HttpServlet {

	private static final long serialVersionUID = -3462495797991308322L;

	/**
	 * @Description 获取请求中的json字符串 
	 * @author yuemingyuan
	 * @throws Exception 
	 * @date 2016年11月16日 10:30:00
	 */
	private String getRequestJsonStr(HttpServletRequest req) throws Exception {
        try {
            // 如果请求实体
            String contentType = req.getContentType();
            if (contentType != null && !contentType.contains("application/json")) {
                return null;
            }

            BufferedReader reader = req.getReader();
            String input;
            StringBuffer requstBody = new StringBuffer("");
            while ((input = reader.readLine()) != null) {
                requstBody.append(input);
            }
            return requstBody.toString();
        } catch (Exception e) {
        	throw e;
        }
    }
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置界面的乱码格式
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//获取的json转成json字符串
		try {
			 String jsonstr =getRequestJsonStr(request);
			 JSONObject jsonObject = JSONObject.fromObject(jsonstr); 
			 BadwordDto dto = (BadwordDto) JSONObject.toBean(jsonObject, BadwordDto.class);
			 String keywords = dto.getKeywords();
			 int level = dto.getLevel(); 
			 // 获得繁体-简体转换器
			 ChineseJF chinesdJF = CJFBeanFactory.getChineseJF();
			 String janText = chinesdJF.chineseFan2Jan(keywords);
			 String sens = SpecialCharacters.StringFilter(keywords);
			 Set<String> badwordsSet = new HashSet<String>();
			 if(level==BadwordTypeEnum.STRICT_VALIDATION.getValue()||level==BadwordTypeEnum.NORMAL_VALIDATION.getValue()){
				 badwordsSet = SensitiveWord.initMap(String.valueOf(level)).getSensitiveWord(sens + janText, 1,String.valueOf(level));
				 //返回json格式
				 JSONObject json = new JSONObject();
				 json.put("message", "");
				 JSONObject data = new JSONObject();
				 data.put("level_"+level, badwordsSet);
				 json.put("data", data);
				 response.getWriter().print(json);
			 	}
			 else{
				 	Set<String> level_1 = SensitiveWord.initMap(String.valueOf(BadwordTypeEnum.STRICT_VALIDATION.getValue())).getSensitiveWord(sens + janText, 1,String.valueOf(BadwordTypeEnum.STRICT_VALIDATION.getValue()));
					Set<String> level_2 = SensitiveWord.initMap(String.valueOf(BadwordTypeEnum.NORMAL_VALIDATION.getValue())).getSensitiveWord(sens + janText, 1,String.valueOf(BadwordTypeEnum.NORMAL_VALIDATION.getValue()));
						// 返回json格式结果
					JSONObject json = new JSONObject();
					json.put("message", "");
					JSONObject data = new JSONObject();
					data.put("level_1", level_1);
					data.put("level_2", level_2);
					json.put("data", data);
					response.getWriter().print(json);
			 }
		} 
			catch (Exception e) {
				e.printStackTrace();
			}	
		
	} 
	
		
}


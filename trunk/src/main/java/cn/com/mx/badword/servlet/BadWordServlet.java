package cn.com.mx.badword.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.com.mx.badword.CJFBeanFactory;
import cn.com.mx.badword.ChineseJF;
import cn.com.mx.badword.Sensitive.SensitiveWord;
import cn.com.mx.badword.enums.BadwordTypeEnum;
import cn.com.mx.badword.utils.SpecialCharacters;

/**
 * @Description badWord 
 * @author zhangyihang
 * @date 2016年2月17日 16:44:47
 */
/**
 * Servlet implementation class BadWordServlet
 */
@WebServlet("/BadWordServlet")
public class BadWordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置界面的乱码格式
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String sensitive = request.getParameter("sensitive");
		String type = request.getParameter("type");
		String level = request.getParameter("level");
		// 获得繁体-简体转换器
		ChineseJF chinesdJF = CJFBeanFactory.getChineseJF();
		String janText = chinesdJF.chineseFan2Jan(sensitive);
		String sens = SpecialCharacters.StringFilter(sensitive);
		if(String.valueOf(BadwordTypeEnum.COMBINED_VALIDATION.getValue()).equals(level)){
			Set<String> level_1 = SensitiveWord.initMap(String.valueOf(BadwordTypeEnum.STRICT_VALIDATION.getValue())).getSensitiveWord(sens + janText, 1,String.valueOf(BadwordTypeEnum.STRICT_VALIDATION.getValue()));
			Set<String> level_2 = SensitiveWord.initMap(String.valueOf(BadwordTypeEnum.NORMAL_VALIDATION.getValue())).getSensitiveWord(sens + janText, 1,String.valueOf(BadwordTypeEnum.NORMAL_VALIDATION.getValue()));
			if("json".equals(type)){	// 返回json格式结果
				JSONObject json = new JSONObject();
				json.put("message", "");
				JSONObject data = new JSONObject();
				data.put("level_1", level_1);
				data.put("level_2", level_2);
				data.put("result", (level_1!=null&&level_1.size()>0)||(level_2!=null&&level_2.size()>0));
				json.put("data", data);
				response.getWriter().print(json);
			} else {	// 返回标红文本结果
				Iterator<String> it = level_1.iterator();
				while (it.hasNext()) {
					String str = it.next();
					sensitive = sensitive.replaceAll(str, "<font color='red'>"+str+"</font>");
				}
				Iterator<String> it1 = level_2.iterator();
				while (it1.hasNext()) {
					String str = it1.next();
					sensitive = sensitive.replaceAll(str, "<font color='red'>"+str+"</font>");
				}
				response.getWriter().print(sensitive);
			}
		}
		else{
		Set<String> badwordsSet = SensitiveWord.initMap(level).getSensitiveWord(sens + janText, 1,level);
		if("json".equals(type)){	// 返回json格式结果
			JSONObject json = new JSONObject();
			json.put("message", "");
			JSONObject data = new JSONObject();
			data.put("badword", badwordsSet);
			data.put("result", badwordsSet!=null&&badwordsSet.size()>0);
			json.put("data", data);
			response.getWriter().print(json);
		} else {	// 返回标红文本结果
			Iterator<String> it = badwordsSet.iterator();
			while (it.hasNext()) {
				String str = it.next();
				sensitive = sensitive.replaceAll(str, "<font color='red'>"+str+"</font>");
			}
			response.getWriter().print(sensitive);
		}
	}
		}

}

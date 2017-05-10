/**
 * 
 */
package cn.touch.web.handler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on May 8,
 * 2017.
 */
public class TouchMappingExceptionResolver extends SimpleMappingExceptionResolver {
	private boolean debug;
	private Map<String, String> toplips = Collections.emptyMap();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.SimpleMappingExceptionResolver#
	 * doResolveException(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * java.lang.Exception)
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (isAjaxRequest(request)) {
			writeExcept2Response(response, ex);
			return new ModelAndView();
		}
		return super.doResolveException(request, response, handler, ex);
	}

	private void writeExcept2Response(HttpServletResponse response, Exception ex) {
		try {
			response.setCharacterEncoding("utf8");
			Writer writer = response.getWriter();
			JSONObject facade = new JSONObject();
			facade.put("err", true);
			// facade.put("", value)

			String clazz = ex.getClass().getName();
			facade.put("errCode", clazz);
			
			String detailMessage = ex.getMessage();
			if (StringUtils.isNotBlank(detailMessage)) {
				facade.put("errMsg", ex.getMessage());
			} else if (toplips.containsKey(clazz)) {
				facade.put("errMsg", toplips.get(clazz));
			}

			if (debug) {// 异常堆栈
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				PrintStream u = new PrintStream(bos);
				ex.printStackTrace(u);
				facade.put("errStack", new String(bos.toByteArray()));
			} 
			writer.write(JSON.toJSONString(facade));
			writer.flush();
		} catch (Exception e) {
			LoggerFactory.getLogger(TouchMappingExceptionResolver.class).error("", e);
		}
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}

	/**
	 * @param debug
	 *            the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @param toplips
	 *            the toplips to set
	 */
	public void setToplips(Map<String, String> toplips) {
		this.toplips = toplips;
	}

}

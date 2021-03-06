package poly.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.util.CmmUtil;


@Controller
public class MainController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="index")
	public String Index() {
		log.info(this.getClass().getName() + "index start");
		
		return "/index";
	}
	
	// 메인페이지 책 검색 결과 반환 JSP
	@RequestMapping(value="form_result")
	public String form_result() {
		
		log.info(this.getClass().getName() + "form_result start");
		
		return "/form_result";
	}
	
	@RequestMapping(value="header")
	public String header() {
		log.info(this.getClass().getName() + "header start");
		
		return "/header";
	}
	
	@RequestMapping(value = "CloudTest")
	public String CloudTest() {
		return "/cloudTest";
	}
	
	@RequestMapping(value = "CloudTest2")
	public String CloudTest2() {
		return "/cloudTest2";
	}
}

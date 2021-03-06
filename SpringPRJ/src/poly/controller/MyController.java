package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.dto.MailDTO;
import poly.dto.UserDTO;
import poly.service.IMailService;
import poly.service.impl.UserService;
import poly.util.CmmUtil;
import poly.util.EncryptUtil;

@Controller
@RequestMapping(value = "my/")
public class MyController {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "UserService")
	private UserService userService;
	
	@Resource(name = "MailService")
	private IMailService mailService;
	
	// 마이페이지 메인
	@RequestMapping(value = "MyMain")
	public String MyMain(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + " .MyMain start");
		
		String id = CmmUtil.nvl((String) session.getAttribute("id"));
		log.info("id : " + id);
		
		if (id.equals("")) {
			model.addAttribute("msg", "로그인이 필요한 서비스 입니다.");
			model.addAttribute("url", "/index.do");
			
			return "/redirect";
		}
		
		UserDTO uDTO = userService.getUserInfo(id);
		
		if(uDTO == null) {
			model.addAttribute("msg", "존재하지 않는 회원입니다.");
			model.addAttribute("url", "/index.do");
			return "/redirect";
		}
		
		model.addAttribute("uDTO", uDTO);
		
		return "/my/myMain";
	}
	
	// 회원정보 수정 화면 호출
	@RequestMapping(value = "UserEdit")
	public String UserEdit(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
		
		log.info(this.getClass().getName() + " .UserEdit start");
		
		String seq = request.getParameter("user_seq");
		int user_seq = Integer.parseInt(seq);
		
		UserDTO uDTO = new UserDTO();
		uDTO.setUser_seq(user_seq);
		
		uDTO = userService.getUserEditInfo(uDTO);
		model.addAttribute("uDTO", uDTO);
		
		return "/my/editUserInfo";
		
	}
	
	// 회원정보 수정 처리
	@RequestMapping(value = "DoUserEdit")
	public String DoUserEdit(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + " .DoUserEdit start");
		String user_name = request.getParameter("user_name");
		String email = request.getParameter("email");
		String id = request.getParameter("id");
		String user_tel = request.getParameter("user_tel");
		String regdate = request.getParameter("regdate");
		
		UserDTO uDTO = new UserDTO();
		uDTO.setUser_name(user_name);
		uDTO.setEmail(email);
		uDTO.setId(id);
		uDTO.setUser_tel(user_tel);
		uDTO.setRegdate(regdate);
		
		int res = 0;
		
		try {
			res = userService.updateUser(uDTO);
		} catch (Exception e) {
			log.info(e.toString());
		}
		
		String msg = "";
		String url = "/my/MyMain.do?id=" + id;
		
		if (res == 0) {
			msg = "회원정보 수정에 실패 하였습니다. 다시 시도해 주십시오.";
		} else {
			msg = "회원정보 수정에 성공 하였습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		log.info(this.getClass().getName() + " .DoUserEdit end");
		
		return "/redirect";
	}
	
	// 회원탈퇴 재확인 화면 호출
	@RequestMapping(value = "DeleteUserInfo")
	public String DeleteUserInfo() throws Exception {
		
		log.info(this.getClass().getName() + " .DeleteUserInfo start");
		
		return "/my/deleteUserInfo";
		
	}
	
	// 회원탈퇴 프로세스
	@RequestMapping(value = "DoDeleteUserInfo")
	public String DoDeleteUserInfo(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + " .DoDeleteUserInfo start");
		
		String id = CmmUtil.nvl(request.getParameter("id"));
		String pw = CmmUtil.nvl(request.getParameter("password"));
		
		log.info("id : " + id);
		log.info("pw : " + pw);
		
		pw = EncryptUtil.encHashSHA256(pw);
		log.info("암호화한 pw : " + pw);
		
		int res = 0;
		
		String msg = "";
		String url = "/index.do";
		
		res = userService.deleteUserInfo(id, pw);
		
		if (res > 0) {
			msg = "회원 탈퇴에 성공 하였습니다. Bookeeper를 이용해 주셔서 감사합니다.";
			session.invalidate();
		} else {
			msg = "회원 탈퇴에 실패 하였습니다. 잠시 후 다시 시도하여 주십시오.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		log.info(this.getClass().getName() + " .DoDeleteUserInfo end");
		
		return "/redirect";
	}
	
	@RequestMapping(value = "ChangePw")
	public String ChangePw(HttpServletRequest request, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + " .ChangePw start");
		return "/my/changePw";
	}
	
	@RequestMapping(value = "DoChangePw")
	public String DoChangePw(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
		log.info(this.getClass().getName() + " .DoChangePw start");
		String id = CmmUtil.nvl(request.getParameter("id"));
		String pw = CmmUtil.nvl(request.getParameter("pw"));
		pw = EncryptUtil.encHashSHA256(pw);
		UserDTO uDTO = new UserDTO();
		uDTO.setId(id);
		uDTO.setPassword(pw);
		UserDTO rDTO = userService.checkLogin(id, pw);
		if (rDTO == null) {
			log.info("아이디 또는 비밀번호 불일치");
			log.info(this.getClass().getName() + " .DoChangePw end");
			String msg = "아이디 또는 비밀번호가 일치하지 않습니다.";
			String url = "/my/MyMain.do";
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			return "/redirect";
		}
		model.addAttribute("id", id);
		rDTO = null;
		return "/my/changePwForm";
	}
	
	@RequestMapping(value = "DoChangePwForm")
	public String DoChangePwForm(HttpServletRequest request, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + " .DoChangePwForm start");
		String id = CmmUtil.nvl(request.getParameter("id"));
		String pw = CmmUtil.nvl(request.getParameter("pw"));
		pw = EncryptUtil.encHashSHA256(pw);
		int res = 0;
		String msg = "";
		String url = "";
		res = userService.updatePw(id, pw);
		log.info("updatePw 갔다온 res : " + res);
		if (res==0) {
			msg = "비밀번호 변경에 실패 하였습니다. 잠시 후 다시 시도해 주십시오.";
			url = "/my/MyMain.do";
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			return "/redirect";
		}
		msg = "비밀번호 변경에 성공 하였습니다.";
		url = "/my/MyMain.do";
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		return "/redirect";
	}
	
	@RequestMapping(value = "ChangeEmail")
	public String ChangeEmail(HttpServletRequest request, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + " .ChangeEmail start");
		return "/my/changeEmail";
	}
	
	@RequestMapping(value = "DoChangeEmail")
	public String DoChangeEmail(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
		log.info(this.getClass().getName() + " .DoChangeEmail start");
		String id = CmmUtil.nvl((String) session.getAttribute("id"));
		log.info("세션 id 제대로 들어왔나 확인 : " + id);
		String email = CmmUtil.nvl(request.getParameter("email"));
		log.info("email : " + email);
		int res = 0;
		try {
			res = userService.initEmail(id);
		} catch (Exception e) {
			log.info(e.toString());
		}
		String msg = "";
		String url = "/index.do";
		if (res == 0) {
			msg = "시스템 오류입니다. 잠시 후 다시 시도하여 주십시오.";
		} else {
			MailDTO mDTO = new MailDTO();
			mDTO.setTitle("Bookeeper 이메일 변경 인증 요청");
			mDTO.setToMail(email);
			StringBuilder content = new StringBuilder();
			content.append("아래 링크를 클릭하시면 이메일 인증이 완료됩니다.\n");
			content.append("http://localhost:8080/user/VerifyEmail.do?code=");
			String code = EncryptUtil.encAES128CBC(id + ",1");
			content.append(code);
			mDTO.setContents(content.toString());
			mailService.doSendMail(mDTO);
			msg = "이메일 재인증을 위해 로그아웃 합니다. 이메일 인증 메일을 확인해 주십시오.";
		}
		log.info(this.getClass().getName() + " .DoChangeEmail end");
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		session.invalidate();
		return "/redirect";
	}
}

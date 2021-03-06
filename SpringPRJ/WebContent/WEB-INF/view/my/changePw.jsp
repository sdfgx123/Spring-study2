<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
	.myContainer {
	margin: auto;
	width: 370px;
	height: 460px;
	box-shadow: 1px 1px 3px 1px #dadce0;
	padding: 10px;
}
</style>
<!-- session 첨부 -->
<%@ include file="../session.jsp"%>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<!-- header 영역 첨부 -->
<%@ include file="../header.jsp"%>
</head>
<body>
	<!-- 메인메뉴 영역 첨부 -->
	<%@ include file="../mainMenu.jsp"%>
	
	<!--히어로 영역 시작 -->
	<div class="slider-area2">
		<div
			class="slider-height3  hero-overly hero-bg4 d-flex align-items-center">
			<div class="container">
				<div class="row">
					<div class="col-xl-12">
						<div class="hero-cap2 pt-20 text-center">
							<h2>비밀번호 변경</h2>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- //히어로 영역 -->
	<br>
	<br>
	<!-- 아이디 & 비밀번호 검증 컨테이너 -->
	<div class="myContainer">
		<form action="DoChangePw.do" method="post" name="changePwForm">
			<p>비밀번호 변경 처리를 위해 아이디와 비밀번호를 입력해 주십시오.</p>
			<hr>
			<p>아이디</p>
			<input type="text" name="id" placeholder="아이디 입력" class="single-input-primary">
			<p>비밀번호</p>
			<input type="password" name="pw" placeholder="비밀번호 입력" class="single-input-primary">
			<br>
			<button type="submit" style="width: 100%;" class="genric-btn primary radius">제출</button>
		</form>
		<br>
		<a href="/my/MyMain.do" class="genric-btn primary radius" style="width: 100%;">취소</a>
	</div>
	<!-- //아이디 & 비밀번호 검증 컨테이너 -->
	
</body>
<!-- footer 영역 -->
<%@ include file="../footer.jsp"%>
</html>
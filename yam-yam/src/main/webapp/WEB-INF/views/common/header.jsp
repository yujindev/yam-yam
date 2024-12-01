<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header class="flex-box">
	<h1>
		<a class="m-0auto pb-1"
			href="${pageContext.request.contextPath}/main/main.do"><img
			src="${pageContext.request.contextPath}/images/logo.svg" alt="로고"></a>
	</h1>
	<nav class="ml-auto bg-gr100">
		<ul class="flex-box menu-box f-center">

			<li class="nav_menu">
				<button class="nav_menu_title">오늘의 메뉴</button>
				<ul class="nav_smenu">
					<li><a
						href="${pageContext.request.contextPath}/tmenu/roulette.do">룰렛</a></li>
					<li><a href="${pageContext.request.contextPath}/tmenu/draw.do">제비뽑기</a></li>
				</ul>
			</li>
			
			<li class="nav_menu">
				<button class="nav_menu_title">식당찾기</button>
				<ul class="nav_smenu">
					<li><a href="${pageContext.request.contextPath}/fplace/list.do">맛집랭킹</a></li>
					<li><a href="${pageContext.request.contextPath}/fplace/fplaceUserList.do">식당검색</a></li>
				</ul>
			</li>
			
			<li class="nav_menu">
				<button class="nav_menu_title">커뮤니티</button>
				<ul class="nav_smenu">
					<li><a href="${pageContext.request.contextPath}/main_board/list.do">전체 글 보기</a></li>
					<li><a href="${pageContext.request.contextPath}/ctalk_board/list.do">잡담</a></li>
					<li><a href="${pageContext.request.contextPath}/cmenu_board/list.do">맛집추천</a></li>
					<li><a href="${pageContext.request.contextPath}/cbob_board/list.do">밥친구 찾기</a></li>
					<li><a href="${pageContext.request.contextPath}/cmov_board/list.do">오늘뭐 보지?</a></li>
					<li><a href="${pageContext.request.contextPath}/czone_board/list.do">도시락존</a></li>
				</ul>
			</li>
			
			<li class="nav_menu">
				<button class="nav_menu_title">이게뭐지?</button>
				<ul class="nav_smenu">
					<li><a href="${pageContext.request.contextPath}/dopamine/fortuneCookie.do">포춘쿠키</a></li>
					<li><a href="${pageContext.request.contextPath}/dopamine/dpList.do?dp_category=2">건강정보</a></li>
					<li><a href="${pageContext.request.contextPath}/dopamine/dpList.do?dp_category=3">축제정보</a></li>
				</ul>
			</li>
			
			<li class="nav_menu">
				<button class="nav_menu_title">MyPage</button>
				<ul class="nav_smenu">
					<li><a
						href="${pageContext.request.contextPath}/member/myPage.do">마이페이지</a></li>
				</ul>
			</li>

			<c:if test="${user_auth==9}">
				<li class="nav_menu">
					<button class="nav_menu_title">관리자 메뉴</button>
					<ul class="nav_smenu">
						<li><a href="${pageContext.request.contextPath}/member/adminList.do">회원관리</a></li>
					    <li><a href="${pageContext.request.contextPath}/fplace/fplaceAdminList.do">식당관리</a></li>
						<li><a href="${pageContext.request.contextPath}/fplace/adminReviewsList.do">리뷰관리</a></li>
						<li><a href="${pageContext.request.contextPath}/tmenu/tmenuList.do">오늘의 메뉴 관리</a></li>
					</ul>
				</li>
			</c:if>

			<c:if test="${user_auth == 7}">
			<li class="nav_menu">
				<button class="nav_menu_title">식당관리자 메뉴</button>
				<ul class="nav_smenu">
					<li><a href="${pageContext.request.contextPath}">예약관리</a></li>
					<li><a href="${pageContext.request.contextPath}/fplace/writeForm.do">식당등록</a></li>
					<li><a href="${pageContext.request.contextPath}/fplace/fplaceManagerList.do">내식당</a></li>
					</ul>
				</li>
			</c:if>
			
			<li class="nav_menu">
				<button class="btn-nav">
					<img src="${pageContext.request.contextPath}/images/icon-arrow.png">
				</button>
			</li>
		</ul>
	</nav>
	<ul class="btn_area flex-box">
		<c:if test="${user_num == null}">
			<li><button type="button"
					onclick="location.href='${pageContext.request.contextPath}/member/loginForm.do'">
					<img src="${pageContext.request.contextPath}/images/icon-login.png"
						alt="">
				</button></li>
		</c:if>
		<li><button type="button">
				<img src="${pageContext.request.contextPath}/images/icon-search.png" alt="">
			</button></li>
		<c:if test="${user_num != null}">
			<li>
				<button type="button"
					onclick="location.href='${pageContext.request.contextPath}/member/logout.do'">
					<img
						src="${pageContext.request.contextPath}/images/icon-profile.png"
						alt="">
				</button>
			</li>
		</c:if>
	</ul>
	<script>
    //header 버튼클릭시 서브메뉴 보임
    const menu = document.querySelectorAll('.nav_menu')
    const subMenu = document.querySelectorAll('.nav_smenu');
    const btnNav = document.querySelector('.btn-nav');

    
	    //toggle형태
	    btnNav.addEventListener('click', () => {
	    subMenu.forEach(item => {
	      item.classList.toggle('open'); // 각 요소에 active 클래스 추가
	    });
	  });
	    
	 /* 헤더 메뉴 중 아무 메뉴를 클릭해도 모든 서브메뉴 열기/닫기
	    menu.forEach(item => {
	        const button = item.querySelector('.nav_menu_title');
	        button.addEventListener('click', () => {
	            subMenu.forEach(sub => {
	                sub.classList.toggle('open'); // 모든 서브메뉴에 open 클래스 토글
	            });
	        });
	    });
	 */
	  //헤더 메뉴에 마우스를 올리면 모든 서브메뉴 열기
	    menu.forEach(item => {
	        item.addEventListener('mouseenter', () => {
	            subMenu.forEach(sub => {
	                sub.classList.add('open'); // 모든 서브메뉴에 open 클래스 추가
	            });
	        });
	
        // 헤더 메뉴에서 마우스가 나가면 모든 서브메뉴 닫기
        item.addEventListener('mouseleave', () => {
            subMenu.forEach(sub => {
                sub.classList.remove('open'); // 모든 서브메뉴에서 open 클래스 제거
            });
        });
    });
</script>
</header>
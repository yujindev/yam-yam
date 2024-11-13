<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    window.onload = function(){
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

  }
</script>
<header class="flex-box">
    <h1><a class="m-0auto pb-1" href=""><img src="${pageContext.request.contextPath}/images/logo.svg" alt="로고"></a></h1>
    <nav class="ml-auto bg-gr100">
      <ul class="flex-box menu-box f-center">
        
        <li class="nav_menu">
          <button class="nav_menu_title">오늘의 메뉴</button>
          <ul class="nav_smenu">
            <li><a href="#">룰렛</a></li>
            <li><a href="#">제비뽑기</a></li>
          </ul>
        </li>
        <li class="nav_menu">
          <button class="nav_menu_title">식당찾기</button>
          <ul class="nav_smenu">
            <li><a href="#">맛집랭킹</a></li>
            <li><a href="#">식당정보</a></li>
          </ul>
        </li>
        <li class="nav_menu">
          <button class="nav_menu_title">커뮤니티</button>
          <ul class="nav_smenu">
            <li><a href="#">전체 글 보기</a></li>
            <li><a href="#">사용자 맛집추천</a></li>
            <li><a href="#">잡담</a></li>
            <li><a href="#">밥친구 찾기</a></li>
            <li><a href="#">오늘 뭐 보지?</a></li>
            <li><a href="#">도시락존</a></li>
          </ul>
        </li>
        <li class="nav_menu">
          <button class="nav_menu_title">이게뭐지?</button>
          <ul class="nav_smenu">
            <li><a href="#">날씨</a></li>
            <li><a href="#">포춘쿠키</a></li>
            <li><a href="#">건강정보</a></li>
            <li><a href="#">축제정보</a></li>
          </ul>
        </li>
        <li class="nav_menu">
          <button class="btn-nav"><img src="${pageContext.request.contextPath}/images/icon-arrow.png"></button>
        </li>
      </ul>
    </nav>
    <ul class="btn_area flex-box">
      <li><button type="button"><img src="${pageContext.request.contextPath}/images/icon-login.png"alt=""></button></li>
      <li><button type="button"><img src="${pageContext.request.contextPath}/images/icon-search.png"alt=""></button></li>
      <li>
        <button type="button"><img src="${pageContext.request.contextPath}/images/icon-profile.png"alt=""></button>
      </li>
    </ul>
  </header>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>키오스크 주문 화면</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"> <!-- 스타일 시트 링크 -->
</head>
<body>
<h1>키오스크 주문 화면</h1>

<!-- 카테고리 목록 -->
<div>
    <ul>
        <li><a href="?category=1">마른안주류</a></li>
        <li><a href="?category=2">과일류</a></li>
        <li><a href="?category=3">튀김류</a></li>
        <li><a href="?category=4">면류</a></li>
        <li><a href="?category=5">치킨류</a></li>
        <li><a href="?category=6">탕류</a></li>
        <li><a href="?category=7">생맥주</a></li>
        <li><a href="?category=8">흑맥주</a></li>
        <li><a href="?category=9">병맥주</a></li>
        <li><a href="?category=10">수입맥주</a></li>
        <li><a href="?category=11">소주</a></li>
        <li><a href="?category=12">집기류</a></li>
        <li><a href="?category=13">음료</a></li>
    </ul>
</div>

<!-- 선택된 카테고리의 메뉴 목록 -->
<div>
    <c:forEach var="menu" items="${menuList}">
        <div>
            <img src="${pageContext.request.contextPath}/images/category/m${menu.mno}_c${menu.categoryId}.jpg" alt="${menu.name}" />
            <p>${menu.name}</p>
            <p>${menu.price}원</p>
        </div>
    </c:forEach>
</div>

</body>
</html>

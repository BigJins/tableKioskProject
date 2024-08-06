<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>모든 메뉴 목록</title>
    <link rel="stylesheet" href="css/styles.css"> <!-- 스타일 시트 링크 -->
</head>
<body>
<h1>모든 메뉴 목록</h1>
<table>
    <thead>
    <tr>
        <th>메뉴 ID</th>
        <th>메뉴 이름</th>
        <th>가격</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="menu" items="${allMenus}">
        <tr>
            <td>${menu.mno}</td> <!-- 변경된 부분 -->
            <td>${menu.name}</td>
            <td>${menu.price}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="index.jsp">홈으로 돌아가기</a> <!-- 홈으로 돌아가는 링크 -->
</body>
</html>

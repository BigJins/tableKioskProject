<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../includes/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> <!-- 부트스트랩 CSS 링크 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"> <!-- 사용자 정의 스타일 시트 링크 -->
    <style>
        .menu-card {
            height: 300px; /* 카드의 고정 높이 설정 */
            display: flex;
            flex-direction: column;
            justify-content: space-between; /* 이미지와 텍스트 사이의 공간을 균등하게 배분 */
        }
        .card-img-top {
            height: 150px; /* 이미지의 고정 높이 설정 */
            object-fit: cover; /* 이미지 비율 유지 */
        }
    </style>
</head>
<body>


<div class="container mt-4">
    <div class="row">
        <div class="col-12">
            <ul class="list-inline text-center">
                <li class="list-inline-item">
                    <a href="?category=1" class="btn btn-outline-primary">마른안주류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=2" class="btn btn-outline-primary">과일류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=3" class="btn btn-outline-primary">튀김류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=4" class="btn btn-outline-primary">면류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=5" class="btn btn-outline-primary">치킨류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=6" class="btn btn-outline-primary">탕류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=7" class="btn btn-outline-primary">생맥주</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=8" class="btn btn-outline-primary">흑맥주</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=9" class="btn btn-outline-primary">병맥주</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=10" class="btn btn-outline-primary">수입맥주</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=11" class="btn btn-outline-primary">소주</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=12" class="btn btn-outline-primary">집기류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=13" class="btn btn-outline-primary">음료</a>
                </li>
            </ul>
        </div>
    </div>

    <div class="row mt-4">
        <c:forEach var="menu" items="${menuList}">
            <div class="col-md-3 mb-4">
                <div class="card menu-card">
                    <img src="${pageContext.request.contextPath}/images/category/m${menu.mno}_c${menu.categoryId}.jpg" class="card-img-top" alt="${menu.name}" />
                    <div class="card-body">
                        <h5 class="card-title">${menu.name}</h5>
                        <p class="card-text">${menu.price}원</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../includes/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> <!-- 부트스트랩 CSS 링크 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"> <!-- 사용자 정의 스타일 시트 링크 -->
    <style>
        .menu-card {
            height: 350px; /* 카드의 고정 높이 설정 */
            display: flex;
            flex-direction: column;
            justify-content: space-between; /* 이미지와 텍스트 사이의 공간을 균등하게 배분 */
        }
        .card-img-top {
            height: 150px; /* 이미지의 고정 높이 설정 */
            object-fit: cover; /* 이미지 비율 유지 */
        }
        .card-body {
            text-align: center; /* 텍스트를 중앙 정렬 */
        }
        .card-title {
            font-size: 1.2em;
            font-weight: bold;
        }
        .card-text {
            font-size: 1.1em;
            color: #555;
        }
        .description {
            font-size: 0.9em;
            color: #777;
            margin-top: 10px; /* 설명과 가격 사이에 간격 추가 */
        }
        .active-category {
            background-color: #007bff; /* 활성화된 카테고리 색상 */
            color: white; /* 글자 색상 */
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <div class="row">
        <div class="col-12">
            <ul class="list-inline text-center">
                <c:set var="selectedCategory" value="${param.category}" /> <!-- 선택된 카테고리 저장 -->
                <li class="list-inline-item">
                    <a href="?category=1" class="btn btn-outline-primary ${selectedCategory == '1' ? 'active-category' : ''}">마른안주류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=2" class="btn btn-outline-primary ${selectedCategory == '2' ? 'active-category' : ''}">과일류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=3" class="btn btn-outline-primary ${selectedCategory == '3' ? 'active-category' : ''}">튀김류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=4" class="btn btn-outline-primary ${selectedCategory == '4' ? 'active-category' : ''}">면류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=5" class="btn btn-outline-primary ${selectedCategory == '5' ? 'active-category' : ''}">치킨류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=6" class="btn btn-outline-primary ${selectedCategory == '6' ? 'active-category' : ''}">탕류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=7" class="btn btn-outline-primary ${selectedCategory == '7' ? 'active-category' : ''}">생맥주</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=8" class="btn btn-outline-primary ${selectedCategory == '8' ? 'active-category' : ''}">흑맥주</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=9" class="btn btn-outline-primary ${selectedCategory == '9' ? 'active-category' : ''}">병맥주</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=10" class="btn btn-outline-primary ${selectedCategory == '10' ? 'active-category' : ''}">수입맥주</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=11" class="btn btn-outline-primary ${selectedCategory == '11' ? 'active-category' : ''}">소주</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=12" class="btn btn-outline-primary ${selectedCategory == '12' ? 'active-category' : ''}">집기류</a>
                </li>
                <li class="list-inline-item">
                    <a href="?category=13" class="btn btn-outline-primary ${selectedCategory == '13' ? 'active-category' : ''}">음료</a>
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
                        <p class="card-text">${fn:substringBefore(menu.price, '.')}원</p> <!-- Format price -->
                        <p class="description">${menu.description}</p> <!-- 메뉴 설명 추가 -->
                        <button class="btn btn-primary" onclick="addToOrder(${menu.mno})">주문담기</button> <!-- Add to Order button -->
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    function addToOrder(menuId) {
        // This function would handle adding the item to the order.
        // You can implement this function to send an AJAX request or update the order summary on the page.
        alert('Menu item ' + menuId + ' added to order!');
    }
</script>
</body>
</html>

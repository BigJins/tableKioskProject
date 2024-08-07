<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .table td, .table th {
            vertical-align: middle;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <h3>주문 내역</h3>
    <c:if test="${not empty sessionScope.orders}">
        <table class="table">
            <thead>
            <tr>
                <th>메뉴 번호</th>
                <th>수량</th>
                <th>총 가격</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="detail" items="${sessionScope.orders}">
                <tr>
                    <td>${detail.mno}</td>
                    <td>${detail.quantity}</td>
                    <td>${detail.total_price}원</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty sessionScope.orders}">
        <p>주문 내역이 없습니다.</p>
    </c:if>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>

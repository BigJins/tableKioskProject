<%@ page import="org.example.tablekioskproject.vo.OrderDetailVO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 10px;
            font-size: 1.1em;
        }
        .detail {
            margin-bottom: 10px;
        }
        .total-price {
            font-weight: bold;
        }
    </style>
</head>
<body>

<h1>주문 상세</h1>
<% List<OrderDetailVO> orderDetails = (List<OrderDetailVO>) request.getAttribute("orderDetails"); %>
<% if (orderDetails != null && !orderDetails.isEmpty()) { %>
<% for (OrderDetailVO detail : orderDetails) { %>
    <div class="detail">
        <label>메뉴 이름: </label><span><%= detail.getMenuName() %></span>
    </div>
    <div class="detail">
        <label>가격: </label><span><%= detail.getMenuPrice() %></span>원
    </div>
    <div class="detail">
        <label>수량: </label><span><%= detail.getQuantity() %></span>
    </div>
    <div class="detail">
        <label>총 가격: </label><span><%= detail.getTotal_price() %></span>원
    </div>
    <form action="/remove" method="post">
        <button>삭제</button>
    </form>
    <hr/>
<% } %>
<% } else { %>
<p>주문 상세 정보가 없습니다.</p>
<% } %>

</body>
</html>

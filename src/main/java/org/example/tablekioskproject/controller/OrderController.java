package org.example.tablekioskproject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.tablekioskproject.dao.CustomerDAO;
import org.example.tablekioskproject.vo.DetailVO;
import org.example.tablekioskproject.vo.OrderDetailVO;
import org.example.tablekioskproject.vo.OrderVO;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/order")
@Log4j2
public class OrderController extends HttpServlet {
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = CustomerDAO.INSTANCE;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int tableNumber = Integer.parseInt(req.getParameter("table_number"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            int mno = Integer.parseInt(req.getParameter("mno"));

            BigDecimal price = customerDAO.getMenuPriceById(mno); // Assuming this method exists to get menu price
            BigDecimal totalPrice = price.multiply(new BigDecimal(quantity));

            // 주문 먼저
            OrderVO order = OrderVO.builder()
                    .table_number(tableNumber)
                    .o_sequence(1) // Sequence 설정 필요
                    .o_status("테스트용")
                    .o_date(LocalDate.now())
                    .o_time(LocalDateTime.now())
                    .build();
            int ono = customerDAO.insertOrder(order);

            // 상세 주문
            DetailVO detail = DetailVO.builder()
                    .ono(ono)
                    .mno(mno)
                    .quantity(quantity)
                    .total_price(totalPrice)
                    .build();
            customerDAO.insertOrderDetail(detail);

            // 모든 주문 상세 정보를 조회
            List<OrderDetailVO> orderDetails = customerDAO.getAllOrderDetails();

            req.setAttribute("orderDetails", orderDetails);
            req.getRequestDispatcher("/WEB-INF/kiosk/orderDetails.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error processing order", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing order");
        }
    }
}

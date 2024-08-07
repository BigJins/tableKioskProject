package org.example.tablekioskproject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.example.tablekioskproject.common.StringUtil;
import org.example.tablekioskproject.dao.CustomerDAO;
import org.example.tablekioskproject.vo.DetailVO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add")
@Log4j2
public class AddOrderController extends HttpServlet {
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = CustomerDAO.INSTANCE;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPost");

        int tableNumber = StringUtil.getInt(req.getParameter("tableNumber"), 1);
        int mno = StringUtil.getInt(req.getParameter("mno"), 1);
        int quantity = StringUtil.getInt(req.getParameter("quantity"), 1);
        BigDecimal price;

        try {
            price = customerDAO.getMenuPrice(mno);
        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/kiosk/kiosk.jsp?error=priceFetchError").forward(req, resp);
            return;
        }

        // 총 가격 계산
        BigDecimal totalPrice = price.multiply(new BigDecimal(quantity));

        // DetailVO 객체 생성
        DetailVO detail = DetailVO.builder()
                .mno(mno)
                .quantity(quantity)
                .total_price(totalPrice)
                .build();

        try {
            HttpSession session = req.getSession();
            List<DetailVO> detailList = (List<DetailVO>) session.getAttribute("orders");
            if (detailList == null) {
                detailList = new ArrayList<>();
            }
            detailList.add(detail); // 현재는 한 개의 상세 정보만 추가
            session.setAttribute("orders", detailList);

            // 페이지 리다이렉트
            req.getRequestDispatcher("/WEB-INF/kiosk/kiosk.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/kiosk/kiosk.jsp?error=priceFetchError").forward(req, resp);
        }

    }
}

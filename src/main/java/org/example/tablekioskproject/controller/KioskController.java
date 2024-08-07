package org.example.tablekioskproject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.tablekioskproject.common.StringUtil;
import org.example.tablekioskproject.dao.CustomerDAO;
import org.example.tablekioskproject.vo.MenuVO;
import org.example.tablekioskproject.vo.OrderDetailVO;

import java.io.IOException;
import java.util.List;

@WebServlet("/kiosk")
@Log4j2
public class KioskController extends HttpServlet {
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = CustomerDAO.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryParam = req.getParameter("category");
        int categoryId = StringUtil.getInt(categoryParam, 1); // StringUtil을 사용하여 categoryId 설정

        try {
            // 메뉴 목록을 조회
            List<MenuVO> menuList = customerDAO.getMenusByCategory(categoryId);
            req.setAttribute("menuList", menuList);

            // 모든 주문 상세 정보를 조회
            List<OrderDetailVO> orderDetails = customerDAO.getAllOrderDetails();
            req.setAttribute("orderDetails", orderDetails);

            // JSP 페이지로 포워딩
            req.getRequestDispatcher("/WEB-INF/kiosk/kiosk.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error processing request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "비상 서버오류!!");
        }
    }
}

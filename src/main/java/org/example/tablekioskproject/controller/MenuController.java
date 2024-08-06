package org.example.tablekioskproject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.tablekioskproject.dao.KioskDAO;
import org.example.tablekioskproject.vo.MenuVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/kiosk")
@Log4j2
public class MenuController extends HttpServlet {
    private KioskDAO kioskDAO;

    @Override
    public void init() throws ServletException {
        kioskDAO = KioskDAO.INSTANCE; // DAO 초기화
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("list".equals(action)) {
                listMenus(req, resp); // 모든 메뉴 조회
            } else if ("recommended".equals(action)) {
                listRecommendedMenus(req, resp); // 추천 메뉴 조회
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "올바른 요청이 아닙니다.");
            }
        } catch (Exception e) {
            log.error("Error processing request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
        }
    }

    private void listMenus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<MenuVO> allMenus = kioskDAO.getAllMenus();
            req.setAttribute("allMenus", allMenus);
            req.getRequestDispatcher("/WEB-INF/kiosk/list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listRecommendedMenus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<MenuVO> recommendedMenus = kioskDAO.getRecommendedMenus();
            req.setAttribute("recommendedMenus", recommendedMenus);
            req.getRequestDispatcher("/WEB-INF/kiosk/recommended.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

package org.example.tablekioskproject.dao;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.tablekioskproject.common.ConnectionUtil;
import org.example.tablekioskproject.vo.DetailVO;
import org.example.tablekioskproject.vo.MenuVO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public enum CustomerDAO {

    INSTANCE;

    CustomerDAO() {}

    public List<MenuVO> getAllMenus() throws Exception {
        log.info("getAllMenus called");
        List<MenuVO> menuList = new ArrayList<>();

        String sql = "SELECT * FROM tbl_k_menu WHERE is_sold_out = FALSE";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        @Cleanup ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            MenuVO menu = MenuVO.builder()
                    .mno(rs.getInt("mno"))
                    .categoryId(rs.getInt("category_id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .price(rs.getBigDecimal("price"))
                    .is_sold_out(rs.getBoolean("is_sold_out"))
                    .isRecommend(rs.getBoolean("is_recommend"))
                    .delflag(rs.getBoolean("delflag"))
                    .build();
            menuList.add(menu);
        }

        return menuList;
    }

    public List<MenuVO> getRecommendedMenus() throws Exception {
        log.info("getRecommendedMenus called");
        List<MenuVO> recommendedMenuList = new ArrayList<>();

        String sql = "SELECT * FROM tbl_k_menu WHERE is_recommend = TRUE AND is_sold_out = FALSE";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        @Cleanup ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            MenuVO menu = MenuVO.builder()
                    .mno(rs.getInt("mno"))
                    .categoryId(rs.getInt("category_id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .price(rs.getBigDecimal("price"))
                    .is_sold_out(rs.getBoolean("is_sold_out"))
                    .isRecommend(rs.getBoolean("is_recommend"))
                    .delflag(rs.getBoolean("delflag"))
                    .build();
            recommendedMenuList.add(menu);
        }

        return recommendedMenuList;
    }

    public List<MenuVO> getMenusByCategory(int categoryId) throws Exception {
        log.info("getMenusByCategory called");
        List<MenuVO> menuList = new ArrayList<>();

        String sql = "SELECT * FROM tbl_k_menu WHERE category_id = ? AND is_sold_out = FALSE";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, categoryId);
        @Cleanup ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            MenuVO menu = MenuVO.builder()
                    .mno(rs.getInt("mno"))
                    .categoryId(rs.getInt("category_id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .price(rs.getBigDecimal("price"))
                    .is_sold_out(rs.getBoolean("is_sold_out"))
                    .isRecommend(rs.getBoolean("is_recommend"))
                    .delflag(rs.getBoolean("delflag"))
                    .build();
            menuList.add(menu);
        }

        return menuList;
    }

    public void addOrder(int tableNumber, List<DetailVO> detailList) throws Exception {
        log.info("addOrder called");

        String orderSql = "INSERT INTO tbl_k_order (table_number, o_status, o_date, o_time) VALUES (?, ?, CURDATE(), CURTIME())";
        String detailSql = "INSERT INTO tbl_k_order_detail (ono, mno, quantity, total_price) VALUES (?, ?, ?, ?)";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement orderPs = con.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS);

        // 기본 주문 정보 삽입
        orderPs.setInt(1, tableNumber);
        orderPs.setString(2, "Pending"); // 초기 상태를 'Pending'으로 설정
        orderPs.executeUpdate();

        // 생성된 주문 번호 가져오기
        @Cleanup ResultSet generatedKeys = orderPs.getGeneratedKeys();
        if (generatedKeys.next()) {
            int ono = generatedKeys.getInt(1); // 주문 번호
            // 주문 세부 정보 삽입
            @Cleanup PreparedStatement detailPs = con.prepareStatement(detailSql);
                for (DetailVO detail : detailList) {
                    detailPs.setInt(1, ono);
                    detailPs.setInt(2, detail.getMno());
                    detailPs.setInt(3, detail.getQuantity());
                    detailPs.setBigDecimal(4, detail.getTotal_price());
                    detailPs.addBatch();
                }
                detailPs.executeBatch();

        }

        log.info("Order added successfully");
    }

    public BigDecimal getMenuPrice(int mno) throws Exception {
        log.info("getMenuPrice called for menu id: {}", mno);
        String sql = "SELECT price FROM tbl_k_menu WHERE mno = ? AND is_sold_out = FALSE";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, mno);

        @Cleanup ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getBigDecimal("price");
        }
        throw new Exception("Menu not found or sold out");
    }


}


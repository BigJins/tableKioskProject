package org.example.tablekioskproject.dao;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.tablekioskproject.common.ConnectionUtil;
import org.example.tablekioskproject.vo.MenuVO;

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
}

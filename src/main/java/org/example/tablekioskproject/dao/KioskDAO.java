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
public enum KioskDAO {

    INSTANCE;

    KioskDAO(){}

    //모든메뉴 조회
    public List<MenuVO> getAllMenus() throws Exception{
        log.info("getAllMenus called");
        List<MenuVO> menuList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM tbl_k_menu
                WHERE is_sold_out = FALSE;
                """;

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        @Cleanup ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            MenuVO menu = MenuVO.builder()
                    .mno(rs.getInt("mno")) // 메뉴 번호
                    .categoryId(rs.getInt("category_id")) // 카테고리 ID
                    .name(rs.getString("name")) // 메뉴 이름
                    .description(rs.getString("description")) // 메뉴 설명
                    .price(rs.getBigDecimal("price")) // 메뉴 가격
                    .is_sold_out(rs.getBoolean("is_sold_out")) // 판매 여부
                    .isRecommend(rs.getBoolean("is_recommend")) // 추천 여부
                    .delflag(rs.getBoolean("delflag")) // 삭제 여부
                    .build();
            menuList.add(menu); // 리스트에 추가
        }

        return menuList;
    }

    // 추천 메뉴 조회
    public List<MenuVO> getRecommendedMenus() throws Exception {
        log.info("getRecommendedMenus called");
        List<MenuVO> recommendedMenuList = new ArrayList<>();

        // SQL 쿼리 준비
        String sql = """
                     SELECT * 
                     FROM tbl_k_menu 
                     WHERE is_recommend = ? AND is_sold_out = ?;
                     """;
        // 데이터베이스 연결
        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setBoolean(1, true);
        ps.setBoolean(2, false);

        // 쿼리 실행
        @Cleanup ResultSet rs = ps.executeQuery();

        // 결과 처리
        while (rs.next()) {
            MenuVO menu = MenuVO.builder()
                    .mno(rs.getInt("mno")) // 메뉴 번호
                    .categoryId(rs.getInt("category_id")) // 카테고리 ID
                    .name(rs.getString("name")) // 메뉴 이름
                    .description(rs.getString("description")) // 메뉴 설명
                    .price(rs.getBigDecimal("price")) // 메뉴 가격
                    .is_sold_out(rs.getBoolean("is_sold_out")) // 판매 여부
                    .isRecommend(rs.getBoolean("is_recommend")) // 추천 여부
                    .delflag(rs.getBoolean("delflag")) // 삭제 여부
                    .build();
            recommendedMenuList.add(menu); // 추천 메뉴 리스트에 추가
        }

        return recommendedMenuList; // 추천 메뉴 리스트 반환
    }
}


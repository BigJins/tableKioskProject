package org.example.tablekioskproject.dao;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.tablekioskproject.common.ConnectionUtil;
import org.example.tablekioskproject.vo.DetailVO;
import org.example.tablekioskproject.vo.MenuVO;
import org.example.tablekioskproject.vo.OrderDetailVO;
import org.example.tablekioskproject.vo.OrderVO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public enum CustomerDAO {
    INSTANCE;

    CustomerDAO() {}

    public void deleteOrderDetail(int ono, int mno) throws Exception {
        String sql = "DELETE FROM tbl_k_detail WHERE ono = ? AND mno = ?";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, ono);
        ps.setInt(2, mno);

        ps.executeUpdate();
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
                    .build();
            menuList.add(menu);
        }

        return menuList;
    }

    public List<OrderDetailVO> getAllOrderDetails() throws Exception {
        log.info("getAllOrderDetails called");
        List<OrderDetailVO> detailsList = new ArrayList<>();

        String sql = """
            SELECT d.ono, d.mno, m.name AS menu_name, m.price AS menu_price, d.quantity, d.total_price 
            FROM tbl_k_menu m 
            INNER JOIN tbl_k_detail d ON m.mno = d.mno
            """;

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        @Cleanup ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            OrderDetailVO detail = OrderDetailVO.builder()
                    .ono(rs.getInt("ono"))   // Populate ono
                    .mno(rs.getInt("mno"))   // Populate mno
                    .menuName(rs.getString("menu_name"))
                    .menuPrice(rs.getBigDecimal("menu_price"))
                    .quantity(rs.getInt("quantity"))
                    .total_price(rs.getBigDecimal("total_price"))
                    .build();
            detailsList.add(detail);
        }

        return detailsList;
    }

    public BigDecimal getMenuPriceById(int mno) throws Exception {
        log.info("getMenuPriceById called");
        String sql = "SELECT price FROM tbl_k_menu WHERE mno = ?";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, mno);
        @Cleanup ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getBigDecimal("price");
        } else {
            throw new Exception("Menu item not found");
        }
    }

    public int insertOrder(OrderVO order) throws Exception {
        log.info("insertOrder called");
        String sql = "INSERT INTO tbl_k_order (table_number, o_sequence, o_status, o_date, o_time) VALUES (?, ?, ?, ?, ?)";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, order.getTable_number());
        ps.setInt(2, order.getO_sequence());
        ps.setString(3, order.getO_status());
        ps.setDate(4, java.sql.Date.valueOf(order.getO_date()));
        ps.setTimestamp(5, java.sql.Timestamp.valueOf(order.getO_time()));
        ps.executeUpdate();

        @Cleanup ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public void insertOrderDetail(DetailVO detail) throws Exception {
        log.info("insertOrderDetail called");
        String sql = "INSERT INTO tbl_k_detail (ono, mno, quantity, total_price) VALUES (?, ?, ?, ?)";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, detail.getOno());
        ps.setInt(2, detail.getMno());
        ps.setInt(3, detail.getQuantity());
        ps.setBigDecimal(4, detail.getTotal_price());
        ps.executeUpdate();
    }

    // New method to handle order creation and detail insertion
    public int createOrderWithDetail(int tableNumber, int mno, int quantity, BigDecimal totalPrice) throws Exception {
        // Create and insert order
        OrderVO order = OrderVO.builder()
                .table_number(tableNumber)
                .o_sequence(1) // Adjust the sequence logic as necessary
                .o_status("테스트용")
                .o_date(LocalDate.now())
                .o_time(LocalDateTime.now())
                .build();

        int ono = insertOrder(order);

        // Create and insert order detail
        DetailVO detail = DetailVO.builder()
                .ono(ono)
                .mno(mno)
                .quantity(quantity)
                .total_price(totalPrice)
                .build();

        insertOrderDetail(detail);

        return ono;
    }
}

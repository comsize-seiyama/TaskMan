package model.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.StatusBean;

public class StatusDAO {

    public List<StatusBean> selectAll() throws ClassNotFoundException, SQLException {

        List<StatusBean> statusBeanList = new ArrayList<>();

        String sql =
                "SELECT status_code, status_name " +
                "FROM m_status " +
                "ORDER BY status_code";

        try (
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
        ) {

            while (rs.next()) {

                StatusBean statusBean = new StatusBean();

                statusBean.setStatusCode(rs.getString("status_code"));
                statusBean.setStatusName(rs.getString("status_name"));
                

                statusBeanList.add(statusBean);
               
            }
           
        }

        return statusBeanList;
    }
}
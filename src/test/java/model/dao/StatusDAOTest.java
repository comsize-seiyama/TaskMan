package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.entity.StatusBean;


class StatusDAOTest extends StatusDAO {

	@Test
	void selectAll_取得成功() {
		StatusDAO dao = new StatusDAO();
	    List<StatusBean> 取得結果 = null;

	    try {
	        取得結果 = dao.selectAll();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }

	    StatusBean id1 = new StatusBean();
	    id1.setStatusCode("01");
	    id1.setStatusName("未着手");

	    StatusBean id2 = new StatusBean();
	    id2.setStatusCode("02");
	    id2.setStatusName("対応中");
	    
	    StatusBean id3 = new StatusBean();
	    id3.setStatusCode("03");
	    id3.setStatusName("完了");


	    List<StatusBean> status = new ArrayList<>();
	    status.add(id1);
	    status.add(id2);
	    status.add(id3);

	    assertIterableEquals(取得結果, status);
	}

	
	@Test
	void selectAll_取得失敗() {
		StatusDAO dao = new StatusDAO();
	    List<StatusBean> 取得結果 = null;

	    try {
	        取得結果 = dao.selectAll();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }

	    List<StatusBean> 空List = null;

	    assertIterableEquals(取得結果, 空List);
	}
}

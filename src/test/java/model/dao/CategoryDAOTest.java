package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.entity.CategoryBean;

class CategoryDAOTest extends CategoryDAO {
	

	@Test
	void selectAll_取得成功() {
	    CategoryDAO dao = new CategoryDAO();
	    List<CategoryBean> 取得結果 = null;

	    try {
	        取得結果 = dao.selectAll();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }

	    CategoryBean id1 = new CategoryBean();
	    id1.setCategoryId(1);
	    id1.setCategoryName("新商品A:開発プロジェクト");

	    CategoryBean id2 = new CategoryBean();
	    id2.setCategoryId(2);
	    id2.setCategoryName("既存商品B:改良プロジェクト");

	    List<CategoryBean> category = new ArrayList<>();
	    category.add(id1);
	    category.add(id2);

	    assertIterableEquals(取得結果, category);
	}

	
	@Test
	void selectAll_取得失敗() {
		CategoryDAO dao = new CategoryDAO();
	    List<CategoryBean> 取得結果 = null;

	    try {
	        取得結果 = dao.selectAll();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }

	    List<CategoryBean> 空List = null;

	    assertIterableEquals(取得結果, 空List);
	}
}

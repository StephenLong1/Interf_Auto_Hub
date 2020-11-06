package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import model.Tester;


public class TesterDao extends BaseDao{
	
	/**
	 * 测试人员登录
	 */
	public Tester login(Tester test) {
		String sql = "select * from s_tester where name=? and password=?";
		Tester tester = null;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
		    ps.setString(1, test.getName());
		    ps.setString(2, test.getPassword());
		    ResultSet rs = ps.executeQuery();
		    if(rs.next()) {
		    	tester = new Tester();
		    	tester.setId(rs.getInt("id"));
		    	tester.setName(rs.getString("name"));
		    	tester.setPassword(rs.getString("password"));
		    	tester.setCreateDate(rs.getString("createDate"));
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tester;
	}
}

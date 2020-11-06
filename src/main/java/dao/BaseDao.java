package dao;

import java.sql.Connection;

import util.DbUtil;

/**
 * 
 * @author stephen.long
   *    创建数据库连接对象, 整个项目于数据库打交道都用这一个对象
 */
public class BaseDao extends DbUtil {
	public Connection con = new DbUtil().getCon();

}

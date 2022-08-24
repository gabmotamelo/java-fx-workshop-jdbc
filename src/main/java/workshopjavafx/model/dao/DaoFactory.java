package workshopjavafx.model.dao;

import workshopjavafx.db.DB;
import workshopjavafx.model.dao.impl.DepartmentDaoJDBC;
import workshopjavafx.model.dao.impl.SellerDaoJDBC;

import java.sql.SQLException;

public class DaoFactory {

    public static SellerDao createSellerDao() throws SQLException {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() throws SQLException{
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}

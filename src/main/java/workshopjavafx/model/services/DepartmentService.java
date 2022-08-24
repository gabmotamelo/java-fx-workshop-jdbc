package workshopjavafx.model.services;

import workshopjavafx.model.dao.DaoFactory;
import workshopjavafx.model.dao.DepartmentDao;
import workshopjavafx.model.entities.Department;

import java.sql.SQLException;
import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public DepartmentService() throws SQLException {
    }

    public List<Department> findAll(){
        return dao.findAll();
    }

    public void saveOrUpdate(Department obj){
        if (obj.getId() == null){
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Department obj) {
        dao.deleteById(obj.getId());
    }
}

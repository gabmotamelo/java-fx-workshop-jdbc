package workshopjavafx.model.services;

import workshopjavafx.model.dao.DaoFactory;
import workshopjavafx.model.dao.SellerDao;
import workshopjavafx.model.entities.Seller;

import java.sql.SQLException;
import java.util.List;

public class SellerService {

    private SellerDao dao = DaoFactory.createSellerDao();

    public SellerService() throws SQLException {
    }

    public List<Seller> findAll(){
        return dao.findAll();
    }

    public void saveOrUpdate(Seller obj){
        if (obj.getId() == null){
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Seller obj) {
        dao.deleteById(obj.getId());
    }
}

package ua.lviv.lgs.service.impl;

import java.sql.SQLException;
import java.util.List;

import ua.lviv.lgs.dao.CustomerDao;
import ua.lviv.lgs.dao.impl.CustomerDaoImpl;
import ua.lviv.lgs.dommain.Customer;
import ua.lviv.lgs.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	
	private CustomerDao custumerDao;
	
	public  CustomerServiceImpl() {
		
		try {
			custumerDao = new CustomerDaoImpl();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public Customer create(Customer t) {
		
		return custumerDao.create(t);
	}

	@Override
	public Customer read(Integer id) {
		
		return custumerDao.read(id);
	}

	@Override
	public Customer update(Customer t) {
		
		return custumerDao.update(t);
	}

	@Override
	public void delete(Integer id) {
		custumerDao.delete(id);
		
	}

	@Override
	public List<Customer> raedAll() {
		
		return custumerDao.raedAll();
	}

}

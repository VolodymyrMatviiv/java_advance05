package ua.lviv.lgs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.lviv.lgs.dao.CustomerDao;
import ua.lviv.lgs.dommain.Customer;
import ua.lviv.lgs.utils.ConnectionUtile;

public class CustomerDaoImpl implements CustomerDao {

	private static String READ_ALL = "select * from customer";
	private static String CREATE = "insert into customer(`first_name`, `last_name`, `email`, `customer_role`) values (?,?,?,?)";
	private static String READ_BY_ID = "select * from customer where id =?";
	private static String UPDATE_BY_ID = "update customer set first_name=?, last_name = ?, email = ?, customer_role = ? where id = ?";
	private static String DELETE_BY_ID = "delete from customer where id=?";

	private Connection connection;
	private PreparedStatement preparedStatement;

	public CustomerDaoImpl()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		connection = ConnectionUtile.openConnection();
	}

	@Override
	public Customer create(Customer customer) {

		try {
			preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getEmail());
			preparedStatement.setString(4, customer.getCustomerRole());
			preparedStatement.executeUpdate();

			ResultSet result = preparedStatement.getGeneratedKeys();
			result.next();
			customer.setId(result.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customer;
	}

	@Override
	public Customer read(Integer id) {

		Customer customer = null;

		try {
			preparedStatement = connection.prepareStatement(READ_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();

			Integer customerId = result.getInt("id");
			String firstName = result.getString("first_name");
			String lastName = result.getString("last_name");
			String email = result.getString("email");
			String customerRole = result.getString("role");

			customer = new Customer(customerId, firstName, lastName, email, customerRole);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Customer update(Customer customer) {
		try {
			preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getEmail());
			preparedStatement.setString(4, customer.getCustomerRole());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public void delete(Integer id) {

		try {
			preparedStatement = connection.prepareStatement(DELETE_BY_ID);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Customer> raedAll() {
		List<Customer> customerRecords = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(READ_ALL);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				Integer customerId = result.getInt("id");
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String email = result.getString("email");
				String customerRole = result.getString("role");

				customerRecords.add(new Customer(customerId, firstName, lastName, email, customerRole));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerRecords;
	}

}

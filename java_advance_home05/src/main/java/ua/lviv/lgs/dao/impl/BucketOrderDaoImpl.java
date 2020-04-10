package ua.lviv.lgs.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.lviv.lgs.dao.BucketOrderDao;
import ua.lviv.lgs.dommain.BucketOrder;
import ua.lviv.lgs.utils.ConnectionUtile;

public class BucketOrderDaoImpl implements BucketOrderDao {
	
	private static String READ_ALL = "select * from bucket_order";
	private static String CREATE = "insert into bucket_order(`customer_id`, `product_id`, `purchase_date`) values (?,?,?)";
	private static String READ_BY_ID = "select * from bucket_order where id =?";
	private static String DELETE_BY_ID = "delete from bucket_order where id=?";

	private Connection connection;
	private PreparedStatement preparedStatement;

	 public BucketOrderDaoImpl() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		connection = ConnectionUtile.openConnection();
	}
	

	@Override
	public BucketOrder create(BucketOrder bucketOrder) {
		 try {
			preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, bucketOrder.getCustomerId());
			preparedStatement.setInt(2, bucketOrder.getProductId());
			preparedStatement.setDate(3, new Date(bucketOrder.getPurchaseDate().getTime()));
			preparedStatement.executeUpdate();
			
			ResultSet result = preparedStatement.getGeneratedKeys();
			result.next();
			bucketOrder.setId(result.getInt(1));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bucketOrder;
	}

	@Override
	public BucketOrder read(Integer id) {
		
		BucketOrder bucketOrder = null;
		
		try {
			preparedStatement = connection.prepareStatement(READ_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			
			Integer bucketOrderId = result.getInt("id");
			Integer customerId = result.getInt("customer_id");
			Integer productId = result.getInt("product_id");
			Date purchaseDate = result.getDate("purchase_date");
			
			bucketOrder = new BucketOrder(bucketOrderId, customerId, productId, purchaseDate);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return bucketOrder;
	}

	@Override
	public BucketOrder update(BucketOrder t) {
		throw new IllegalStateException("No update for bucket_order");
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
	public List<BucketOrder> raedAll() {

		List<BucketOrder> bucketOrderRecords = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(READ_ALL);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				Integer bucketOrderId = result.getInt("id");
				Integer customerId = result.getInt("customer_id");
				Integer bookId = result.getInt("product_id");
				Date purchaseDate = result.getDate("purchase_date");
				bucketOrderRecords.add(new BucketOrder(bucketOrderId, customerId, bookId, purchaseDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bucketOrderRecords;
		
	}
	
	
	

}

package ua.lviv.lgs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.lviv.lgs.dao.BookDao;
import ua.lviv.lgs.dommain.Book;
import ua.lviv.lgs.utils.ConnectionUtile;

public class BookDaoImpl implements BookDao {
	
	private static String READ_ALL = "select * from product";
	private static String CREATE = "isert into product('book_name' ,'book_description' ,'price' ,'isbn') value(?,?,?,?)";
	private static String READ_BY_ID = "select * from product where id =?";
	private static String UPDATE_BY_ID = "update product set book_name=?, book_description=?, price=?, isbn=? where id=?";
	private static String DELETE_BY_ID = "delete from product where id=?";
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	public  BookDaoImpl() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		connection = ConnectionUtile.openConnection();
	}

	@Override
	public Book create(Book book) {
		try {
			preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, book.getBookName());
			preparedStatement.setString(2, book.getBookDescription());
			preparedStatement.setDouble(3, book.getPrice());
			preparedStatement.setString(4, book.getIsbn());
			preparedStatement.executeUpdate();

			ResultSet result = preparedStatement.getGeneratedKeys();
			result.next();
			book.setId(result.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return book;
	}

	@Override
	public Book read(Integer id) {
		Book book = null;
		try {
			preparedStatement = connection.prepareStatement(READ_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();

			Integer bookId = result.getInt("id");
			String bookName = result.getString("book_name");
			String bookDescription = result.getString("book_description");
			Double price = result.getDouble("price");
			String isbn = result.getString("isbn");
			
			book = new Book(bookId, bookName, bookDescription, price, isbn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

	@Override
	public Book update(Book book) {
		try {
			preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
			preparedStatement.setString(1, book.getBookName());
			preparedStatement.setString(2, book.getBookDescription());
			preparedStatement.setDouble(3, book.getPrice());
			preparedStatement.setString(4, book.getIsbn());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
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
	public List<Book> raedAll() {
		List<Book> bookRecords = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(READ_ALL);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				Integer bookId = result.getInt("id");
				String bookName = result.getString("book_name");
				String bookDescription = result.getString("book_description");
				Double price = result.getDouble("price");
				String isbn = result.getString("isbn");
								
				bookRecords.add(new Book(bookId, bookName, bookDescription, price, isbn));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookRecords;
	}

}
 
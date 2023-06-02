package repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.User;

public class UserRepository extends DataManager implements Repository<User>{

	@Override
	public void add(User user) {
		String query="insert into users (username,name,surname,password) value(?,?,?,?)";
		
		try {
			connect();
			preparedStatement= connection.prepareStatement(query);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getSurname());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(User t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> find(Map<String, String> fieldList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
	String query="Select * from todolist_project.users";
	List<User> users= new ArrayList<>();
	
	try {
		connect();
		preparedStatement=connection.prepareStatement(query);
		resultSet=preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			users.add(new User(resultSet.getInt("users.id"), resultSet.getString("users.username"),resultSet.getString("users.name"), resultSet.getString("users.surname"),resultSet.getString("users.password")));
		}
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		try {
			disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	
	
	@Override
	public boolean remove(User t) {
		// TODO Auto-generated method stub
		return false;
	}

}

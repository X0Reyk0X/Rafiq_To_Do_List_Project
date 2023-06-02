package repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Category;

public class CategoryRepository extends DataManager implements Repository<Category> {

	@Override
	public void add(Category category) {
		String query="insert into todolist_project.categories (name) values(?)";
		
		try {
			connect();
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1,category.getName() );
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
	public void update(Category t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Category> find(Map<String, String> fieldList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findAll() {
		List<Category> categories = new ArrayList<>();
		
		String query="select * from todolist_project.categories";
		
		try {
			connect();
			preparedStatement=connection.prepareStatement(query);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Category category = new Category();
				category.setId(resultSet.getInt("categories.id"));
				category.setName(resultSet.getString("categories.name"));
				categories.add(category);
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
		return categories;
	}

	@Override
	public boolean remove(Category t) {
		// TODO Auto-generated method stub
		return false;
	}

}

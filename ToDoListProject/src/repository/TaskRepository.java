package repository;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Category;
import model.Task;
import model.User;

public class TaskRepository extends DataManager implements Repository<Task>{

	@Override
	public void add(Task t) {
		String query = "insert into todolist_project.tasks (task, start, days, category_id, user_id, status, active)"+
						"values( ?, NOW(), ?, ?, ?, 'Not Done', 1)";
		
		try {
			connect();
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, t.getTask());
			preparedStatement.setInt(2, t.getDays());
			preparedStatement.setInt(3, t.getCategory().getId());
			preparedStatement.setInt(4, t.getUser().getId());
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
	public void update(Task t) {
		String query = "update todolist_project.tasks set task = ?, days = ?, category_id = ? where id = ? and user_id = ?";
		
		
		try {
			connect();
			preparedStatement= connection.prepareStatement(query);
			preparedStatement.setString(1, t.getTask());
			preparedStatement.setInt(2, t.getDays());
			preparedStatement.setInt(3, t.getCategory().getId());
			preparedStatement.setInt(4, t.getId());
			preparedStatement.setInt(5, t.getUser().getId());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
			disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}

	public List<Task> find (Map<String, String> fieldList){
		String query ="select * from todolist_project.tasks "
				+"left join todolist_project.categories on tasks.category_id = categories.id "
				+"left join todolist_project.users on tasks.user_id = users.id "
				+"where tasks.task like '%" + fieldList.get("task") + "%' "
				+"and tasks.days like '%" + fieldList.get("days") + "%' "
				+"and categories.name like '%" + fieldList.get("category") + "%' "
				+ "and tasks.active=1";
		List<Task> tasks = new ArrayList<>();
		try {
			connect();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Task task = new Task();
				Category category = new Category();
				User user = new User();
				task.setId(resultSet.getInt("tasks.id"));
				task.setTask(resultSet.getString("tasks.task"));
				task.setDate(resultSet.getDate("tasks.start"));
				task.setDays(resultSet.getInt("tasks.days"));
				task.setStatus(resultSet.getString("tasks.status"));
				category.setId(resultSet.getInt("categories.id"));
				category.setName(resultSet.getString("categories.name"));
				user.setId(resultSet.getInt("users.id"));
				user.setUsername(resultSet.getString("users.name"));
				user.setName(resultSet.getString("users.name"));
				user.setSurname(resultSet.getString("users.surname"));
				user.setPassword(resultSet.getString("users.password"));
				task.setCategory(category);
				task.setUser(user);
				tasks.add(task);
			}
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}
	
	
	@Override
	public List<Task> findAll() {
		String query ="select * from todolist_project.tasks left join todolist_project.categories on tasks.category_id=categories.id left join todolist_project.users on tasks.user_id = users.id where tasks.active = 1";
		
		List<Task> tasks = new ArrayList<>();
		try {
			connect();
			preparedStatement=connection.prepareStatement(query);
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Task task= new Task();
				Category category= new Category();
				User user=new User();
				task.setId(resultSet.getInt("tasks.id"));
				task.setTask(resultSet.getString("tasks.task"));
				task.setDate(resultSet.getDate("tasks.start"));
				task.setDays(resultSet.getInt("tasks.days"));
				task.setStatus(resultSet.getString("tasks.status"));
				category.setId(resultSet.getInt("categories.id"));
				category.setName(resultSet.getString("categories.name"));
				user.setId(resultSet.getInt("users.id"));
				user.setName(resultSet.getString("users.name"));
				user.setSurname(resultSet.getString("users.surname"));
				user.setUsername(resultSet.getString("users.username"));
				user.setPassword(resultSet.getString("users.password"));
				task.setCategory(category);
				task.setUser(user);
				tasks.add(task);
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
		return tasks;
	}

	@Override
	public boolean remove(Task task) {
	boolean result = false;
	String query = "update todolist_project.tasks set active = 0 where id = ?";
	
	try {
		connect();
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, task.getId());
		preparedStatement.execute();
		result = true;
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
		return result;
	}
	
	public List<Task> findDone() {
		String query ="select * from todolist_project.tasks left join todolist_project.categories on tasks.category_id=categories.id left join todolist_project.users on tasks.user_id = users.id where tasks.active = 1 and tasks.status = 'Done'";
		
		List<Task> tasks = new ArrayList<>();
		try {
			connect();
			preparedStatement=connection.prepareStatement(query);
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Task task= new Task();
				Category category= new Category();
				User user=new User();
				task.setId(resultSet.getInt("tasks.id"));
				task.setTask(resultSet.getString("tasks.task"));
				task.setDate(resultSet.getDate("tasks.start"));
				task.setDays(resultSet.getInt("tasks.days"));
				task.setStatus(resultSet.getString("tasks.status"));
				category.setId(resultSet.getInt("categories.id"));
				category.setName(resultSet.getString("categories.name"));
				user.setId(resultSet.getInt("users.id"));
				user.setName(resultSet.getString("users.name"));
				user.setSurname(resultSet.getString("users.surname"));
				user.setUsername(resultSet.getString("users.username"));
				user.setPassword(resultSet.getString("users.password"));
				task.setCategory(category);
				task.setUser(user);
				tasks.add(task);
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
		return tasks;
	}
		public List<Task> findNotDone() {
		String query ="select * from todolist_project.tasks left join todolist_project.categories on tasks.category_id=categories.id left join todolist_project.users on tasks.user_id = users.id where tasks.active = 1 and tasks.status = 'Not Done'";
		
		List<Task> tasks = new ArrayList<>();
		try {
			connect();
			preparedStatement=connection.prepareStatement(query);
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Task task= new Task();
				Category category= new Category();
				User user=new User();
				task.setId(resultSet.getInt("tasks.id"));
				task.setTask(resultSet.getString("tasks.task"));
				task.setDate(resultSet.getDate("tasks.start"));
				task.setDays(resultSet.getInt("tasks.days"));
				task.setStatus(resultSet.getString("tasks.status"));
				category.setId(resultSet.getInt("categories.id"));
				category.setName(resultSet.getString("categories.name"));
				user.setId(resultSet.getInt("users.id"));
				user.setName(resultSet.getString("users.name"));
				user.setSurname(resultSet.getString("users.surname"));
				user.setUsername(resultSet.getString("users.username"));
				user.setPassword(resultSet.getString("users.password"));
				task.setCategory(category);
				task.setUser(user);
				tasks.add(task);
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
		return tasks;
	}
		
		public boolean setDone(Task task) {
			boolean result = false;
			String query = "update todolist_project.tasks set status = 'Done' where id = ?";
			
			try {
				connect();
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, task.getId());
				preparedStatement.execute();
				result = true;
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
				return result;
			}
		
		public boolean setAllDone(Task task) {
			boolean result = false;
			String query = "update todolist_project.tasks set status = 'Done' where status = 'Not Done'";
			
			try {
				connect();
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.execute();
				result = true;
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
				return result;
			}
}

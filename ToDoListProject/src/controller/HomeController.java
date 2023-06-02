package controller;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Category;
import model.Task;
import model.User;
import repository.CategoryRepository;
import repository.TaskRepository;

public class HomeController {

    @FXML
    private TableView<Task> taskTableView;
    
    @FXML
    private TableColumn<Task, Integer> daysColumn;
    
    @FXML
    private TableColumn<Task, DatePicker> startColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;

    @FXML
    private TableColumn<Task, String> taskColumn;
    
    @FXML
    private TableColumn<Task, Category> categoryColumn;
	
    @FXML
    private ComboBox<Category> categoryCMB;
    
    @FXML
    private TextField titleField;
    
    @FXML
    private RadioButton allButton;

    @FXML
    private TextField dayField;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Label categoryErrorLabel;

    @FXML
    private Label daysErrorLabel;

    @FXML
    private CheckBox findCB;

    @FXML
    private Label informationLB;

    @FXML
    private ToggleGroup radio;


    @FXML
    private Label taskErrorLabel;

    @FXML
    private Label taskLB;

    @FXML
    private Label userLabel;
    
    private TaskRepository taskRepository=new TaskRepository();
    
    private CategoryRepository categoryRepository= new CategoryRepository();
    
    private User user;
    
    private Parent parent;
    private Scene scene;
    private Stage stage;
    
    void login(User user) {
    	this.user=user;
    	userLabel.setText("User: "+ this.user.getUsername());
    	allButton.setSelected(true);
    	setValue();
    	updateTable(null);
    	writeMessage("Hello " +user.getName());
    	makeNumeric();
    }

    private void clearErrorLogs() {
    	taskErrorLabel.setText("");
    	daysErrorLabel.setText("");
    	categoryErrorLabel.setText("");
    }
    
	private void fillCategories() {
		List<Category> categories=new ArrayList<>();
		categories.add(new Category("All categories"));
		categories.addAll(categoryRepository.findAll());
		categoryCMB.getItems().setAll(categories);
		categoryCMB.getSelectionModel().selectFirst();
		}

	private void setAsDefault() {
			saveButton.setText("Add");
		}
		
	public void makeNumeric() {
		dayField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,5}(\\d{0,0})?")) {
					dayField.setText(oldValue);
				}
			}
		});
	}
	
	@FXML
    void addCategory(MouseEvent event) {
		List<Category> categories= categoryRepository.findAll();
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Category");
		dialog.setHeaderText("Add Category");
		dialog.setContentText("Input the new category name");
		Optional<String> value=dialog.showAndWait();
		boolean result=false;
		if(!value.get().trim().isEmpty()) {
			while(!result) {
				if(categories.isEmpty()) {
					result=true;
					break;
				}else {
					for(Category category : categories) {
					if(!category.getName().equalsIgnoreCase(value.get().trim())) {
						result=true;
					}else {
						Alert alert=new Alert (AlertType.ERROR);
						alert.setTitle("Category");
						alert.setHeaderText("category Error");
						alert.setContentText("This category alrady exist");
						alert.showAndWait();
						result = false;
						value=dialog.showAndWait();
						break;
					}
				}
				
				}
				
			}
			Category category = new Category();
			category.setName(value.get().trim());
			categoryRepository.add(category);
			fillCategories();
			updateTable(event);
		}else {
			Alert alert=new Alert (AlertType.ERROR);
			alert.setTitle("Category");
			alert.setHeaderText("category Error");
			alert.setContentText("Please, input the category name");
			alert.showAndWait();
		}
    }
	
	private void writeMessage(String text) {
		informationLB.setText(text);
		}

	@FXML
    void delete(MouseEvent event) {
		Task SelectedTask = taskTableView.getSelectionModel().getSelectedItem();
		taskRepository.remove(SelectedTask);
		updateTable(event);
    }

    @FXML
    void fillAll(ActionEvent event) {
    	fillTable();
    }

	@FXML
    void fillDoneTable(ActionEvent event) {
		List<Task> tasks=new ArrayList<>();
		List<Task> sortedTasks=new ArrayList<>();
		tasks.addAll(taskRepository.findDone());
		for(Task task : tasks) {
			System.out.println(task);
			if(task.getUser().equals(user)) {
				sortedTasks.add(task);
			}
		}
		System.out.println(tasks);
		System.out.println(sortedTasks);
		taskTableView.getItems().setAll(sortedTasks);
    }

    @FXML
    void fillNotDoneTable(ActionEvent event) {
    	List<Task> tasks=new ArrayList<>();
		List<Task> sortedTasks=new ArrayList<>();
		tasks.addAll(taskRepository.findNotDone());
		for(Task task : tasks) {
			System.out.println(task);
			if(task.getUser().equals(user)) {
				sortedTasks.add(task);
			}
		}
		System.out.println(tasks);
		System.out.println(sortedTasks);
		taskTableView.getItems().setAll(sortedTasks);
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
    	FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Login.fxml"));
		parent= loader.load();
		LoginController controller = loader.getController();
		controller.getData();
		stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
		scene=new Scene (parent);
		stage.setScene(scene);
		stage.setTitle("Login");
		stage.show();
    }

    @FXML
    void onTaskSelect(MouseEvent event) {
    	saveButton.setText("Change");
    Task task =taskTableView.getSelectionModel().getSelectedItem();
    titleField.setText(task.getTask());
    dayField.setText(task.getDays()+"");
    categoryCMB.getSelectionModel().selectFirst();
    }

    @FXML
    void save(MouseEvent event) {
    	Task selecteTask = taskTableView.getSelectionModel().getSelectedItem();
    		if(selecteTask == null) {
    			if(checkField()) {
    				if(checkTask()) {}
    				Task task = new Task();
    				task.setTask(titleField.getText());
    				task.setDays(Integer.parseInt(dayField.getText()));
    				task.setCategory(categoryCMB.getValue());
    				task.setUser(user);
    				taskRepository.add(task);
    				fillTable();
    				clearFields();
    				writeMessage("Task added successfully");
    			}else {
    					taskErrorLabel.setText("This task is already used");	
    				
    			}
    		}else {
    			if(checkField()) {
    				Task task = new Task();
    				task.setId(selecteTask.getId());
    				task.setTask(titleField.getText());
    				task.setDays(Integer.parseInt(dayField.getText()));
    				task.setCategory(categoryCMB.getValue());
    				task.setUser(user);
    				taskRepository.update(task);
    				fillTable();
    				clearFields();
    				writeMessage("Task changed successfully");
    				setAsDefault();
    				
    			}
    		}
    		}
    		
    			
    
    boolean checkField() {
    	clearErrorLogs();
    	boolean result=true;
    	if(titleField.getText().trim().isEmpty()) {
    		result = false;
    		taskErrorLabel.setText("Input the task name");
    	} else {
    		taskErrorLabel.setText("");
    	}
    	
    	if(dayField.getText().trim().isEmpty()) {
    		result=false;
    		daysErrorLabel.setText("Input the task days");
    	}	
    	else {
    		daysErrorLabel.setText("");
    	}
    	
    	if(categoryCMB.getValue().equals(new Category("All categories"))) {
    		result = false;
    		categoryErrorLabel.setText("input the task category");
    	}else {
    		categoryErrorLabel.setText("");
    	}
    	return result;
    }
    boolean checkTask() {
    	List<Task> allTasks = new ArrayList<>();
    	allTasks.addAll(taskRepository.findAll());
    	
    	List<Task> sortedTasks = getUserTasks(allTasks);
    	boolean result= true;
    	if(!sortedTasks.isEmpty()) {
    	for(Task task: sortedTasks) {
    		if(task.getTask().equals(titleField.getText())) {
    			result = false;
    			break;
    		} else {
    			result= true;
    		}
    	}
    	} else {
    		result = true;
    	}
    	return result;
    }

	private List<Task> getUserTasks(List<Task> allTasks) {
		List<Task> sortedTasks=new ArrayList<>();
		for(Task task : allTasks) {
			if(user.equals(task.getUser())) {
				sortedTasks.add(task);
				
			}
		}
		return sortedTasks;
	}

	@FXML
    void setAllDone(MouseEvent event) {
    	taskRepository.setAllDone(null);
    	writeMessage("all tasks have done Succesfully");
    	updateTable(event);
    }

    @FXML
    void setDone(MouseEvent event) {
    	Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
    	taskRepository.setDone(selectedTask);
    	writeMessage("Selected task has done Succesfully");
    	updateTable(null);
    }

    @FXML
    void updateTable(MouseEvent event) {
    	allButton.setSelected(true);
    	fillTable();
    	clearFields();
    	clearErrorLogs();
    	setAsDefault();
    	fillCategories();
    }	

    void setValue() {
    	taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
    	startColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    	daysColumn.setCellValueFactory(new PropertyValueFactory<>("days"));
    	statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    	categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    }
    
    private void clearFields() {
    	titleField.setText("");
    	dayField.setText("");
    	categoryCMB.getSelectionModel().selectFirst();
    }
    
    void fillTable() {
		List<Task> tasks=new ArrayList<>();
		List<Task> sortedTasks=new ArrayList<>();
		tasks.addAll(taskRepository.findAll());
		for(Task task : tasks) {
			System.out.println(task);
			if(task.getUser().equals(user)) {
				
				sortedTasks.add(task);
			}
		}
		taskLB.setText(sortedTasks.size()+ " Tasks.");
		taskTableView.getItems().setAll(sortedTasks);
	}
    
    @FXML
    void find() {
    	if(findCB.isSelected()) {
    		Category category = new Category();
    		if(categoryCMB.getValue().getName().equalsIgnoreCase("All categories")) {
    			category.setName("");
    		}else {
    			category.setName(categoryCMB.getValue().getName());
    		}
    		try{
    			Map<String, String> fields= new TreeMap<>();
    			fields.put("task", titleField.getText().trim());
    			fields.put("days", dayField.getText());
    			fields.put("category", category.getName());
    			
    			List<Task> tasks=taskRepository.find(fields);
    			taskTableView.getItems().setAll(tasks);
    			writeMessage("Task finded succesfuly");
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
}
	
	
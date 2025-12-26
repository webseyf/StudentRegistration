package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import manager.StudentManager;
import model.Course;
import model.Instructor;
import model.Student;
import javafx.beans.property.SimpleStringProperty;

import java.util.*;

public class MainView {

    private StudentManager manager;
    private ObservableList<Student> studentList;

    // --- Student fields ---
    private TextField idField, nameField, searchIdField, deleteIdField;
    private ComboBox<String> deptCombo, batchCombo;
    private TableView<Student> studentTable;
    private Label totalLabel;

    // --- Instructor & Course ---
    private List<Instructor> instructorList;
    private List<Course> courseList;

    // --- Student-Course Display ---
    private TableView<CourseAssignment> studentCoursesTable;

    public MainView(Stage stage) {
        manager = new StudentManager();
        studentList = FXCollections.observableArrayList();
        instructorList = new ArrayList<>();
        courseList = new ArrayList<>();
        buildUI(stage);
    }

    private void buildUI(Stage stage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // =================== STUDENT INPUT CARD ===================
        VBox studentCard = createCard("Add Student");
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        idField = new TextField();
        nameField = new TextField();

        deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("CS", "IT", "Math", "Physics", "Chemistry");
        deptCombo.setPromptText("Select Department");

        batchCombo = new ComboBox<>();
        batchCombo.getItems().addAll("Batch A", "Batch B", "Batch C");
        batchCombo.setPromptText("Select Batch");

        inputGrid.addRow(0, new Label("ID:"), idField);
        inputGrid.addRow(1, new Label("Name:"), nameField);
        inputGrid.addRow(2, new Label("Department:"), deptCombo);
        inputGrid.addRow(3, new Label("Batch:"), batchCombo);

        Button addBtn = createButton("Add Student", "#4CAF50");
        addBtn.setOnAction(e -> addStudent());
        inputGrid.add(addBtn, 1, 4);
        studentCard.getChildren().add(inputGrid);

        // =================== STUDENT ACTION CARD ===================
        VBox actionCard = createCard("Student Actions");
        HBox searchBox = new HBox(10);
        searchIdField = new TextField();
        searchIdField.setPromptText("Search by ID");
        Button searchBtn = createButton("Search", "#2196F3");
        searchBtn.setOnAction(e -> searchStudent());
        searchBox.getChildren().addAll(searchIdField, searchBtn);

        HBox deleteBox = new HBox(10);
        deleteIdField = new TextField();
        deleteIdField.setPromptText("Delete by ID");
        Button deleteBtn = createButton("Delete", "#f44336");
        deleteBtn.setOnAction(e -> deleteStudent());
        deleteBox.getChildren().addAll(deleteIdField, deleteBtn);

        HBox extraButtons = new HBox(10);
        Button displayBtn = createButton("Display All", "#FF9800");
        displayBtn.setOnAction(e -> displayAllStudents());

        Button countDeptBtn = createButton("Count Dept", "#9C27B0");
        countDeptBtn.setOnAction(e -> countStudentsByDepartment());

        Button clearAllBtn = createButton("Clear All", "#607D8B");
        clearAllBtn.setOnAction(e -> clearAllStudents());

        Button summaryBtn = createButton("Summary", "#795548");
        summaryBtn.setOnAction(e -> exportStudentSummary());

        Button randomBtn = createButton("Random Student", "#00BCD4");
        randomBtn.setOnAction(e -> generateRandomStudent());

        extraButtons.getChildren().addAll(displayBtn, countDeptBtn, clearAllBtn, summaryBtn, randomBtn);

        actionCard.getChildren().addAll(searchBox, deleteBox, extraButtons);

        // =================== STUDENT TABLE CARD ===================
        VBox tableCard = createCard("Student List");
        studentTable = new TableView<>();
        studentTable.setItems(studentList);
        studentTable.setPrefHeight(250);
        studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Student, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Student, String> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        TableColumn<Student, String> batchCol = new TableColumn<>("Batch");
        batchCol.setCellValueFactory(new PropertyValueFactory<>("batch"));
        TableColumn<Student, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEnrollmentStatus()));

        studentTable.getColumns().addAll(idCol, nameCol, deptCol, batchCol, statusCol);
        tableCard.getChildren().add(studentTable);

        totalLabel = new Label("Total Students: 0");
        totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        tableCard.getChildren().add(totalLabel);

        // =================== INSTRUCTOR CARD ===================
        VBox instrCard = createCard("Add Instructor");
        GridPane instrGrid = new GridPane();
        instrGrid.setHgap(10);
        instrGrid.setVgap(10);

        TextField instrIdField = new TextField();
        TextField instrNameField = new TextField();
        TextField instrDeptField = new TextField();

        instrGrid.addRow(0, new Label("ID:"), instrIdField);
        instrGrid.addRow(1, new Label("Name:"), instrNameField);
        instrGrid.addRow(2, new Label("Dept:"), instrDeptField);

        Button addInstrBtn = createButton("Add Instructor", "#3F51B5");
        instrGrid.add(addInstrBtn, 1, 3);
        instrCard.getChildren().add(instrGrid);

        // =================== COURSE CARD ===================
        VBox courseCard = createCard("Add Course");
        GridPane courseGrid = new GridPane();
        courseGrid.setHgap(10);
        courseGrid.setVgap(10);

        TextField courseIdField = new TextField();
        TextField courseNameField = new TextField();
        ComboBox<String> instructorCombo = new ComboBox<>();

        courseGrid.addRow(0, new Label("ID:"), courseIdField);
        courseGrid.addRow(1, new Label("Name:"), courseNameField);
        courseGrid.addRow(2, new Label("Instructor:"), instructorCombo);

        Button addCourseBtn = createButton("Add Course", "#009688");
        courseGrid.add(addCourseBtn, 1, 3);
        courseCard.getChildren().add(courseGrid);

        // =================== ASSIGN COURSE CARD ===================
        VBox assignCard = createCard("Assign Course to Student");
        GridPane assignGrid = new GridPane();
        assignGrid.setHgap(10);
        assignGrid.setVgap(10);

        TextField assignStudentIdField = new TextField();
        ComboBox<String> assignCourseCombo = new ComboBox<>();
        assignGrid.addRow(0, new Label("Student ID:"), assignStudentIdField);
        assignGrid.addRow(1, new Label("Course:"), assignCourseCombo);

        Button assignBtn = createButton("Assign", "#FF5722");
        assignGrid.add(assignBtn, 1, 2);
        assignCard.getChildren().add(assignGrid);

        // =================== STUDENT COURSES TABLE ===================
        VBox studentCoursesCard = createCard("Student Courses Table");
        studentCoursesTable = new TableView<>();
        studentCoursesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<CourseAssignment, String> scStudentCol = new TableColumn<>("Student");
        scStudentCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        TableColumn<CourseAssignment, String> scCourseCol = new TableColumn<>("Course");
        scCourseCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        TableColumn<CourseAssignment, String> scInstrCol = new TableColumn<>("Instructor");
        scInstrCol.setCellValueFactory(new PropertyValueFactory<>("instructorName"));

        studentCoursesTable.getColumns().addAll(scStudentCol, scCourseCol, scInstrCol);
        studentCoursesCard.getChildren().add(studentCoursesTable);

        // =================== ADD ALL CARDS ===================
        root.getChildren().addAll(studentCard, actionCard, tableCard, instrCard, courseCard, assignCard, studentCoursesCard);

        Scene scene = new Scene(scrollPane, 900, 1000);
        stage.setTitle("Student Registration System - Restored Version");
        stage.setScene(scene);
        stage.show();

        // =================== BUTTON LOGIC ===================
        addInstrBtn.setOnAction(e -> {
            String id = instrIdField.getText().trim();
            String name = instrNameField.getText().trim();
            String dept = instrDeptField.getText().trim();
            if (id.isEmpty() || name.isEmpty() || dept.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Fill all fields!");
                return;
            }
            for (Instructor instr : instructorList)
                if (instr.getId().equals(id)) { showAlert(Alert.AlertType.ERROR, "Duplicate", "ID exists"); return; }
            Instructor instr = new Instructor(id, name, dept);
            instructorList.add(instr);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Instructor added!");
            instrIdField.clear(); instrNameField.clear(); instrDeptField.clear();
            instructorCombo.getItems().clear();
            for (Instructor i : instructorList) instructorCombo.getItems().add(i.getId() + " - " + i.getName());
        });

        addCourseBtn.setOnAction(e -> {
            String id = courseIdField.getText().trim();
            String name = courseNameField.getText().trim();
            String selectedInstr = instructorCombo.getSelectionModel().getSelectedItem();
            if (id.isEmpty() || name.isEmpty()) { showAlert(Alert.AlertType.WARNING,"Fill fields!",""); return; }
            for (Course c : courseList) if (c.getId().equals(id)){ showAlert(Alert.AlertType.ERROR,"Duplicate",""); return; }
            Instructor instr = null;
            if (selectedInstr != null){
                String instrId = selectedInstr.split(" - ")[0];
                for (Instructor i : instructorList) if (i.getId().equals(instrId)) { instr=i; break; }
            }
            Course c = new Course(id,name,instr);
            courseList.add(c);
            showAlert(Alert.AlertType.INFORMATION,"Success","Course added!");
            courseIdField.clear(); courseNameField.clear(); instructorCombo.getSelectionModel().clearSelection();
            assignCourseCombo.getItems().clear();
            for (Course course: courseList) assignCourseCombo.getItems().add(course.getId() + " - " + course.getCourseName());
        });

        assignBtn.setOnAction(e -> {
            String studentId = assignStudentIdField.getText().trim();
            String selectedCourse = assignCourseCombo.getSelectionModel().getSelectedItem();
            if (studentId.isEmpty() || selectedCourse==null){ showAlert(Alert.AlertType.WARNING,"Select!",""); return; }
            Student s = manager.searchStudentById(studentId);
            if (s==null){ showAlert(Alert.AlertType.ERROR,"Not found",""); return; }
            String courseId = selectedCourse.split(" - ")[0];
            Course assignC=null;
            for (Course c: courseList) if (c.getId().equals(courseId)) { assignC=c; break; }
            if (assignC!=null){
                boolean added = s.addCourse(assignC);
                if (added) {
                    showAlert(Alert.AlertType.INFORMATION,"Success","Course assigned!");
                    updateStudentCoursesTable(s);
                    studentTable.refresh();
                } else showAlert(Alert.AlertType.WARNING,"Already assigned","Student has this course!");
            }
        });
    }

    // =================== HELPER METHODS ===================
    private VBox createCard(String title){
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 10;");
        card.setEffect(new DropShadow(3, Color.GRAY));
        Label lbl = new Label(title);
        lbl.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        card.getChildren().add(lbl);
        return card;
    }

    private Button createButton(String text, String color){
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: "+color+"; -fx-text-fill: white; -fx-background-radius: 5;");
        btn.setPrefWidth(120);
        return btn;
    }

    private void addStudent() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String dept = deptCombo.getSelectionModel().getSelectedItem();
        String batch = batchCombo.getSelectionModel().getSelectedItem();
        if (id.isEmpty() || name.isEmpty() || dept==null || batch==null){
            showAlert(Alert.AlertType.WARNING,"Validation Error","All fields must be filled");
            return;
        }
        Student s = new Student(id,name,dept,batch);
        boolean added = manager.addStudent(s);
        if (added){
            studentList.add(s);
            showAlert(Alert.AlertType.INFORMATION,"Added","Student added!");
            clearInputFields();
            updateTotalCount();
        } else showAlert(Alert.AlertType.ERROR,"Duplicate","ID exists!");
    }

    private void searchStudent(){
        String id = searchIdField.getText().trim();
        if(id.isEmpty()){ displayAllStudents(); return; }
        Student s = manager.searchStudentById(id);
        if(s!=null) studentList.setAll(s);
        else showAlert(Alert.AlertType.ERROR,"Not Found","Student not found!");
    }

    private void deleteStudent(){
        String id = deleteIdField.getText().trim();
        if(id.isEmpty()){ showAlert(Alert.AlertType.WARNING,"Enter ID",""); return; }
        Student s = manager.searchStudentById(id);
        if(s==null){ showAlert(Alert.AlertType.ERROR,"Not Found",""); return; }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,"Delete "+s.getName()+"?");
        confirm.showAndWait().ifPresent(r -> { if(r==ButtonType.OK){ manager.deleteStudentById(id); studentList.remove(s); updateTotalCount(); }}); 
    }

    private void displayAllStudents(){ studentList.setAll(manager.getAllStudents()); }

    private void countStudentsByDepartment(){
        if(manager.getAllStudents().isEmpty()){ showAlert(Alert.AlertType.INFORMATION,"Count Dept","No students"); return; }
        Map<String,Integer> map = new HashMap<>();
        for(Student s: manager.getAllStudents()) map.put(s.getDepartment(),map.getOrDefault(s.getDepartment(),0)+1);
        StringBuilder sb = new StringBuilder();
        sb.append("Student Count by Dept:\n");
        map.forEach((k,v)-> sb.append(k).append(": ").append(v).append("\n"));
        showAlert(Alert.AlertType.INFORMATION,"Count Dept",sb.toString());
    }

    private void clearAllStudents(){ manager.getAllStudents().clear(); studentList.clear(); updateTotalCount(); }

    private void exportStudentSummary(){
        if(manager.getAllStudents().isEmpty()){ showAlert(Alert.AlertType.INFORMATION,"Summary","No students"); return; }
        int total = manager.getStudentCount();
        Student first = manager.getAllStudents().get(0);
        Student last = manager.getAllStudents().get(total-1);
        Map<String,Integer> map = new HashMap<>();
        for(Student s: manager.getAllStudents()) map.put(s.getDepartment(),map.getOrDefault(s.getDepartment(),0)+1);
        StringBuilder sb = new StringBuilder();
        sb.append("Total: ").append(total).append("\nFirst: ").append(first).append("\nLast: ").append(last).append("\n\nDept Count:\n");
        map.forEach((k,v)-> sb.append(k).append(": ").append(v).append("\n"));
        showAlert(Alert.AlertType.INFORMATION,"Summary",sb.toString());
    }

    private void generateRandomStudent(){
        String[] names = {"Ali","Sara","John","Lily","Maya","Daniel"};
        String[] depts = {"CS","IT","Math","Physics","Chemistry"};
        String[] batches = {"Batch A","Batch B","Batch C"};
        int idNum = 1000+manager.getStudentCount()+1;
        String id = String.valueOf(idNum);
        String name = names[(int)(Math.random()*names.length)];
        String dept = depts[(int)(Math.random()*depts.length)];
        String batch = batches[(int)(Math.random()*batches.length)];
        Student s = new Student(id,name,dept,batch);
        manager.addStudent(s); studentList.add(s); updateTotalCount();
    }

    private void updateTotalCount(){ totalLabel.setText("Total Students: "+manager.getStudentCount()); }

    private void clearInputFields(){ idField.clear(); nameField.clear(); deptCombo.getSelectionModel().clearSelection(); batchCombo.getSelectionModel().clearSelection(); }

    private void showAlert(Alert.AlertType type,String title,String msg){
        Alert a = new Alert(type); a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }

    private void updateStudentCoursesTable(Student s){
        ObservableList<CourseAssignment> list = FXCollections.observableArrayList();
        for(Course c : s.getCourses()){
            list.add(new CourseAssignment(s.getName(), c.getCourseName(), c.getInstructor()!=null ? c.getInstructor().getName() : ""));
        }
        studentCoursesTable.setItems(list);
    }

    public static class CourseAssignment{
        private String studentName, courseName, instructorName;
        public CourseAssignment(String s, String c, String i){ studentName=s; courseName=c; instructorName=i; }
        public String getStudentName(){ return studentName; }
        public String getCourseName(){ return courseName; }
        public String getInstructorName(){ return instructorName; }
    }
}

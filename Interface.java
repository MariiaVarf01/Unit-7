package a;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Interface extends JFrame {
    private static final long serialVersionUID = 1L;
    private static HashMap<String, Boolean> studentValidationList = new HashMap<>();
    private static HashMap<String, Boolean> courseValidationList = new HashMap<>();

	private String defaultCourseString = "Sellect Course";
	private String defaultStudentString = "Sellect Student";
    private JPanel groupPanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        studentValidationList.put("idField", false);
        studentValidationList.put("nameField", false);
        courseValidationList.put("idField", false);
        courseValidationList.put("nameField", false);
        courseValidationList.put("capacityField", false);
        

        EventQueue.invokeLater(() -> {
            try {
                Interface frame = new Interface();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Interface() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        getContentPane().setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnStudent = new JMenu("Student");
        mnStudent.setForeground(Color.BLACK);
        mnStudent.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(mnStudent);

        JMenuItem addStudent = new JMenuItem("Add Student");
        mnStudent.add(addStudent);

        addStudent.addActionListener(e -> showAddStudent());

        JMenuItem updateStudent = new JMenuItem("Update Student");
        mnStudent.add(updateStudent);
        updateStudent.addActionListener(e -> {
        	setVisible(true);
        	showUpdateStudent();
        });
        
        JMenuItem viewStudent = new JMenuItem("View Student Details");
        mnStudent.add(viewStudent);
        viewStudent.addActionListener(e -> showStudent());

        JMenuItem enrollStudent = new JMenuItem("Enroll Student");
        mnStudent.add(enrollStudent);
        enrollStudent.addActionListener(e -> {
        	setVisible(true);
        	showEnrollStudent();
        });


        JMenu mnCourses = new JMenu("Courses");
        mnCourses.setFont(new Font("Segoe UI", Font.BOLD, 16));
        mnCourses.setForeground(Color.BLACK);
        menuBar.add(mnCourses);

        JMenuItem addCourse = new JMenuItem("Add Course");
        mnCourses.add(addCourse);
        
        addCourse.addActionListener(e -> {
        	hidePanel();
        	showAddCourse();
        });
        
        JMenu mnGrade = new JMenu("Grade");
        mnGrade.setForeground(Color.BLACK);
        mnGrade.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(mnGrade);

        JMenuItem assignGrade = new JMenuItem("Assign Grade");
        mnGrade.add(assignGrade);

        assignGrade.addActionListener(e -> showAssignGrade());
    }
    
    private void showStudent() { 
        if (groupPanel != null) {
            getContentPane().remove(groupPanel);
        }

        // Create a new panel
        groupPanel = new JPanel();
        groupPanel.setBounds(50, 50, 400, 300);
        groupPanel.setLayout(null);
        getContentPane().add(groupPanel);

        JLabel titleLabel = new JLabel("View Student Details");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 0, 350, 30);
        groupPanel.add(titleLabel);

        // Create a JComboBox
        JComboBox<String> studentBox = new JComboBox<>();
        studentBox.setBounds(100, 50, 200, 30);
        groupPanel.add(studentBox);

        JLabel idLabel = new JLabel("Student");
        idLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        idLabel.setBounds(0, 50, 150, 20);
        groupPanel.add(idLabel);

        // Get student list dynamically
        ArrayList<String> studentList = getStudentsList(); // Assume this returns an ArrayList of student names or IDs
        studentList.add(0, defaultStudentString);
        for (String item : studentList) {
            studentBox.addItem(item); // Dynamically add each item to the JComboBox
        }

        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        nameLabel.setBounds(0, 100, 350, 20);
        groupPanel.add(nameLabel);
        nameLabel.setVisible(false);

        JLabel studentNameLabel = new JLabel();
        studentNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        studentNameLabel.setBounds(100, 100, 350, 20);
        groupPanel.add(studentNameLabel);
        studentNameLabel.setVisible(false);

        JLabel coursesLabel = new JLabel("Courses: ");
        coursesLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        coursesLabel.setBounds(0, 150, 350, 20);
        groupPanel.add(coursesLabel);
        coursesLabel.setVisible(false);

        JLabel gradeLabel = new JLabel("Total Grade:");
        gradeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gradeLabel.setBounds(0, 200, 350, 20);
        groupPanel.add(gradeLabel);
        gradeLabel.setVisible(false);

        JLabel totalLabel = new JLabel();
        totalLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        totalLabel.setBounds(100, 200, 350, 20);
        groupPanel.add(totalLabel);
        totalLabel.setVisible(false);

        // Create a list panel to display enrolled courses
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBounds(100, 150, 200, 100); // Adjust bounds as needed
        groupPanel.add(listPanel);

        studentBox.addActionListener(e -> {
            String studentItem = (String) studentBox.getSelectedItem();
            if (!studentItem.isEmpty() && !studentItem.equals(defaultStudentString)) {
                String id = getId((String) studentBox.getSelectedItem());
                Student student = CourseManagement.findStudentById(id);

                nameLabel.setVisible(true);
                coursesLabel.setVisible(true);
                studentNameLabel.setText(student.getName());
                studentNameLabel.setVisible(true);
                gradeLabel.setVisible(true);
                totalLabel.setVisible(true);

                // Clear previous course list items
                listPanel.removeAll();
                List<Course> enrolledCourses = student.getEnrolledCourses();

                int totalGrade = 0;
                int number = 0;

                if (enrolledCourses.size() == 0) {
                    JLabel listItem = new JLabel("No enroled courses");
                    listItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listPanel.add(listItem);
                }
                for (int i = 0; i < enrolledCourses.size(); i++) {
                    Integer grade = student.getGrades().get(i);
                    double result = (grade != null) ? grade : 0.0; // Ensure result is a double
                    String formattedResult = String.format("%.2f", result); // Format as floating-point
                    Course course = enrolledCourses.get(i);

                    JLabel listItem = new JLabel(course.getCourseName() + ". Grade: " + formattedResult);
                    listItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listPanel.add(listItem);

                    number += (result > 0 ? 1 : 0);
                    totalGrade += result;
                }

                // Display the total grade or an appropriate message if no grades are available
                if (number > 0) {
                    totalLabel.setText(String.format("%.2f", (double) totalGrade / number));
                } else {
                    totalLabel.setText("-");
                }

                // Revalidate and repaint the list panel to refresh the UI
                listPanel.revalidate();
                listPanel.repaint();
            }
        });

        revalidate();
        repaint();
    }

    private void showAssignGrade() {
        if (groupPanel != null) {
            getContentPane().remove(groupPanel);
        }

        groupPanel = new JPanel();
        groupPanel.setBounds(50, 50, 400, 300);
        groupPanel.setLayout(null);
        getContentPane().add(groupPanel);

        JLabel titleLabel = new JLabel("Assign Grade");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 0, 400, 30);
        groupPanel.add(titleLabel);

        // Student Dropdown
        JLabel studentLabel = new JLabel("Select Student:");
        studentLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        studentLabel.setBounds(10, 50, 150, 20);
        groupPanel.add(studentLabel);

        JComboBox<String> studentBox = new JComboBox<>();
        studentBox.setBounds(150, 50, 200, 25);
        groupPanel.add(studentBox);

        // Populate student list
        ArrayList<String> studentList = getStudentsList(); // Assume this gets the list of student names/IDs
        studentBox.addItem("Select a Student"); // Default option
        for (String student : studentList) {
            studentBox.addItem(student);
        }

        // Course Dropdown
        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        courseLabel.setBounds(10, 100, 150, 20);
        groupPanel.add(courseLabel);

        JComboBox<String> courseBox = new JComboBox<>();
        courseBox.setBounds(150, 100, 200, 25);
        groupPanel.add(courseBox);

        // Grade Field
        JLabel gradeLabel = new JLabel("Enter Grade:");
        gradeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gradeLabel.setBounds(10, 150, 150, 20);
        groupPanel.add(gradeLabel);

        JTextField gradeField = new JTextField();
        gradeField.setBounds(150, 150, 200, 25);
        groupPanel.add(gradeField);

        JButton assignGradeButton = new JButton("Assign Grade");
        assignGradeButton.setBounds(150, 200, 150, 30);
        groupPanel.add(assignGradeButton);

        // Add an ActionListener to the student dropdown
        studentBox.addActionListener(e -> {
            String selectedStudent = (String) studentBox.getSelectedItem();
            if (selectedStudent != null && !selectedStudent.equals("Select a Student")) {
                String studentId = getId(selectedStudent); // Get the student ID
                Student student = CourseManagement.findStudentById(studentId);

                if (student != null) {
                    // Clear the courseBox before repopulating it
                    courseBox.removeAllItems();

                    // Add courses for the selected student
                    for (Course course : student.getEnrolledCourses()) {
                        courseBox.addItem(course.getCourseName());
                    }
                }
            }
        });

        assignGradeButton.addActionListener(e -> {
            String selectedStudent = (String) studentBox.getSelectedItem();
            String selectedCourse = (String) courseBox.getSelectedItem();
            String gradeText = gradeField.getText();

            if (selectedStudent == null || selectedStudent.equals("Select a Student")) {
                JOptionPane.showMessageDialog(groupPanel, "Please select a student.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedCourse == null) {
                JOptionPane.showMessageDialog(groupPanel, "Please select a course.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int grade = Integer.parseInt(gradeText);
                String studentId = getId(selectedStudent);
                Student student = CourseManagement.findStudentById(studentId);
                Course course = CourseManagement.findCourseByName(selectedCourse);

                if (grade < 0) {
                	JOptionPane.showMessageDialog(groupPanel, "Please enter a valid grade.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (student != null && course != null) {
                    CourseManagement.assignGradeToStudent(student, course, grade);
                    JOptionPane.showMessageDialog(groupPanel, "Grade assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    gradeField.setText(""); // Clear the grade field after assigning
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(groupPanel, "Please enter a valid grade.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        revalidate();
        repaint();
    }

    private void showUpdateStudent() {
        // Remove the existing panel if it exists
        if (groupPanel != null) {
            getContentPane().remove(groupPanel);
        }
        
        // Create a new panel
        groupPanel = new JPanel();
        groupPanel.setBounds(50, 50, 400, 300);
        groupPanel.setLayout(null);
        getContentPane().add(groupPanel);
        
        JLabel titleLabel = new JLabel("Update Student");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        titleLabel.setBounds(0, 0, 350, 25);
        groupPanel.add(titleLabel);
        

        JLabel idLabel = new JLabel("Student");
        idLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        idLabel.setBounds(0, 50, 150, 20);
        groupPanel.add(idLabel);

        // Create a JComboBox
        JComboBox<String> studentBox = new JComboBox<>();
        studentBox.setBounds(120, 50, 200, 30);
        groupPanel.add(studentBox);   
        
        // Get course codes dynamically
        ArrayList<String> studentList = getStudentsList(); // Assume this returns an ArrayList of course codes
        studentList.add(0, defaultStudentString);
        // Populate the JComboBox using the ArrayList
        for (String item : studentList) {
        	studentBox.addItem(item); // Dynamically add each item to the JComboBox
        }

        JLabel nameLabel = new JLabel("Student Name");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        nameLabel.setBounds(0, 100, 150, 20);
        groupPanel.add(nameLabel);
        nameLabel.setVisible(false);
        

        JTextField nameField = new JTextField();
        nameField.setBounds(120, 100, 200, 25);
        groupPanel.add(nameField);
        nameField.setVisible(false);
        
        JButton saveBtn = new JButton("Save Name");
        saveBtn.setBounds(120, 150, 200, 30);
        groupPanel.add(saveBtn);
        saveBtn.setVisible(false);
        
        studentBox.addActionListener(e -> {
            String studentItem = (String) studentBox.getSelectedItem();
            if (!studentItem.isEmpty() && studentItem != defaultStudentString) {
            	String id = getId((String) studentBox.getSelectedItem());
                nameLabel.setVisible(true);
                nameField.setVisible(true);
                nameField.setText(getName(studentItem)); 	
            }
        });
        
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                saveBtn.setVisible(!nameField.getText().isEmpty());
            }
        });
        
        saveBtn.addActionListener(e -> {
        	String id = getId((String) studentBox.getSelectedItem());
        	CourseManagement.updateStudent(id, nameField.getText().trim());
        	Student student = CourseManagement.findStudentById(id);
        	String msg = "Student Updated\n ID: " + id + ". New Name: " + student.getName();
        	JOptionPane.showMessageDialog(groupPanel, msg);
        });
        
        // Refresh the UI to reflect changes
        revalidate();
        repaint();        
    }

    private void showEnrollStudent() {
        // Remove the existing panel if it exists
        if (groupPanel != null) {
            getContentPane().remove(groupPanel);
        }
        
        // Create a new panel
        groupPanel = new JPanel();
        groupPanel.setBounds(50, 50, 400, 300);
        groupPanel.setLayout(null);
        getContentPane().add(groupPanel);
        
        JLabel titleLabel = new JLabel("Enroll Student");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        titleLabel.setBounds(0, 0, 350, 25);
        groupPanel.add(titleLabel);

        JLabel codeLabel = new JLabel("Course Code");
        codeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        codeLabel.setBounds(0, 50, 150, 20);
        groupPanel.add(codeLabel);
        
        // Create a JComboBox
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(100, 50, 200, 30);
        groupPanel.add(comboBox);

        // Get course codes dynamically
        ArrayList<String> optionList = getCoursesCodes(); // Assume this returns an ArrayList of course codes
        optionList.add(0, defaultCourseString);
        // Populate the JComboBox using the ArrayList
        for (String item : optionList) {
            comboBox.addItem(item); // Dynamically add each item to the JComboBox
        }



        JLabel idLabel = new JLabel("Student ID");
        idLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        idLabel.setBounds(0, 100, 150, 20);
        groupPanel.add(idLabel);
        
        // Create a JComboBox
        JComboBox<String> studentBox = new JComboBox<>();
        studentBox.setBounds(100, 100, 200, 30);
        groupPanel.add(studentBox);

        // Get course codes dynamically
        ArrayList<String> studentList = getStudentsList(); // Assume this returns an ArrayList of course codes
        studentList.add(0, defaultStudentString);
        // Populate the JComboBox using the ArrayList
        for (String item : studentList) {
        	studentBox.addItem(item); // Dynamically add each item to the JComboBox
        }

        JButton enrollStudent = new JButton("Enroll Student");
        enrollStudent.setEnabled(false);
        enrollStudent.setBounds(100, 150, 200, 30);
        groupPanel.add(enrollStudent);
        enrollStudent.setVisible(true);
        

        // Add an ActionListener to capture selected item
        comboBox.addActionListener(e -> {
            String courseItem = (String) comboBox.getSelectedItem();
            String studentItem = (String) studentBox.getSelectedItem();
        	enrollStudent.setEnabled(!courseItem.isEmpty() && !studentItem.isEmpty() && courseItem != defaultCourseString && studentItem != defaultStudentString);
        });
        studentBox.addActionListener(e -> {
            String courseItem = (String) comboBox.getSelectedItem();
            String studentItem = (String) studentBox.getSelectedItem();
        	enrollStudent.setEnabled(!courseItem.isEmpty() && !studentItem.isEmpty() && courseItem != defaultCourseString && studentItem != defaultStudentString);
        });
        
    	enrollStudent.addActionListener(e -> {
            String courseItem = (String) comboBox.getSelectedItem();
            String studentItem = (String) studentBox.getSelectedItem();
            if (courseItem.isEmpty() || studentItem.isEmpty() || courseItem == defaultCourseString || studentItem == defaultStudentString) {
            	enrollStudent.setEnabled(true);
            }
            else {
            	String id = getId((String) studentBox.getSelectedItem());
            	String code = (String) comboBox.getSelectedItem();
            	Student student = CourseManagement.findStudentById(id);
            	Course course = CourseManagement.findCourseByCode(code);
            	String msg = CourseManagement.enrollStudent(student, course);

            	JOptionPane.showMessageDialog(groupPanel, msg);
            }
        });


        // Refresh the UI to reflect changes
        revalidate();
        repaint();
    }
    
    private String getId (String value) {
        int idStart = value.indexOf("ID: ") + 4; // Add 4 to skip "ID: "

        // Search for the ending point of ", "
        int idEnd = value.indexOf(", ", idStart);

        // Extract the substring containing the ID
        if (idStart >= 4 && idEnd > idStart) { // Ensure valid indices
            return value.substring(idStart, idEnd);
        } else {
            return "";
        }
    }
    
    private String getName (String value) {
        int idStart = value.indexOf("Name: ") + 4; // Add 4 to skip "ID: "

        // Search for the ending point of ", "
        int idEnd = value.indexOf(value.length() - 1, idStart);

        // Extract the substring containing the ID
        if (idStart >= 4 && idEnd > idStart) { // Ensure valid indices
            return value.substring(idStart, idEnd);
        } else {
            return "";
        }
    }

    private ArrayList<String> getCoursesCodes () {
    	return CourseManagement.listCourses();
    }
    
    private ArrayList<String> getStudentsList () {
    	return CourseManagement.getStudentsList();
    }
 
    private void showAddCourse() {
        if (groupPanel != null) {
            getContentPane().remove(groupPanel);
        }

        groupPanel = new JPanel();
        groupPanel.setBounds(50, 50, 400, 300);
        groupPanel.setLayout(null);
        getContentPane().add(groupPanel);

        JLabel titleLabel = new JLabel("Add Course");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        titleLabel.setBounds(0, 0, 350, 25);
        groupPanel.add(titleLabel);

        // Course Code Label and Field
        JLabel codeLabel = new JLabel("Code");
        codeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        codeLabel.setBounds(10, 50, 80, 20);
        groupPanel.add(codeLabel);

        JTextField codeField = new JTextField();
        codeField.setBounds(100, 50, 200, 25);
        groupPanel.add(codeField);

        // Course Name Label and Field
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        nameLabel.setBounds(10, 100, 80, 20);
        groupPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(100, 100, 200, 25);
        groupPanel.add(nameField);

        // Course Capacity Label and Field
        JLabel capacityLabel = new JLabel("Capacity");
        capacityLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        capacityLabel.setBounds(10, 150, 80, 20);
        groupPanel.add(capacityLabel);

        JTextField capacityField = new JTextField("0");
        capacityField.setBounds(100, 150, 200, 25);
        groupPanel.add(capacityField);

        JLabel errorLabel = new JLabel();
        errorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(10, 180, 350, 20);
        errorLabel.setVisible(false);
        groupPanel.add(errorLabel);

        // Add Course Button
        JButton btnAddCourse = new JButton("Add New Course");
        btnAddCourse.setEnabled(false); // Initially disabled
        btnAddCourse.setBounds(100, 220, 200, 30);
        groupPanel.add(btnAddCourse);

        // Validation Logic
        KeyAdapter validationAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                boolean isCodeValid = !codeField.getText().trim().isEmpty();
                boolean isNameValid = !nameField.getText().trim().isEmpty();
                boolean isCapacityValid = false;

                try {
                    int capacity = Integer.parseInt(capacityField.getText().trim());
                    if (capacity < 0) {
                        errorLabel.setText("Capacity must be greater than or equal to 0.");
                        errorLabel.setVisible(true);
                        capacityField.setBackground(new Color(255, 200, 200)); // Light red
                    } else {
                        isCapacityValid = true;
                        errorLabel.setVisible(false);
                        capacityField.setBackground(Color.WHITE);
                    }
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Please enter a valid integer for capacity.");
                    errorLabel.setVisible(true);
                    capacityField.setBackground(new Color(255, 200, 200)); // Light red
                }

                btnAddCourse.setEnabled(isCodeValid && isNameValid && isCapacityValid);
            }
        };

        codeField.addKeyListener(validationAdapter);
        nameField.addKeyListener(validationAdapter);
        capacityField.addKeyListener(validationAdapter);

        // Add Course Action
        btnAddCourse.addActionListener(e -> {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            int capacity = Integer.parseInt(capacityField.getText().trim());

            // Check for unique course code
            if (CourseManagement.courseExists(code)) {
                errorLabel.setText("Course code already exists. Please use a unique code.");
                errorLabel.setVisible(true);
                return;
            }

            // Add the course
            Course course = new Course(code, name, capacity);
            CourseManagement.addCourse(course);

            // Display success message
            JOptionPane.showMessageDialog(
                this, 
                "Course added successfully:\nCode: " + code + "\nName: " + name + "\nCapacity: " + capacity,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );

            // Reset fields
            codeField.setText("");
            nameField.setText("");
            capacityField.setText("0");
            btnAddCourse.setEnabled(false);
        });

        revalidate();
        repaint();
    }

    
    /**
     * Show the Add Student Panel.
     */
    private void showAddStudent() {
        if (groupPanel != null) {
            getContentPane().remove(groupPanel);
        }

        groupPanel = new JPanel();
        groupPanel.setBounds(50, 50, 400, 300);
        groupPanel.setLayout(null);
        getContentPane().add(groupPanel);

        JLabel titleLabel = new JLabel("Add Student");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        titleLabel.setBounds(0, 0, 350, 25);
        groupPanel.add(titleLabel);

        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        idLabel.setBounds(10, 50, 80, 20);
        groupPanel.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(100, 50, 200, 25);
        groupPanel.add(idField);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        nameLabel.setBounds(10, 100, 80, 20);
        groupPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(100, 100, 200, 25);
        groupPanel.add(nameField);

        JLabel errorLabel = new JLabel();
        errorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(10, 130, 350, 20);
        errorLabel.setVisible(false);
        groupPanel.add(errorLabel);

        JButton btnAddStudent = new JButton("Add New Student");
        btnAddStudent.setEnabled(false);
        btnAddStudent.setBounds(100, 150, 200, 30);
        groupPanel.add(btnAddStudent);

        KeyAdapter validationAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                boolean isIdValid = !idField.getText().trim().isEmpty();
                boolean isNameValid = !nameField.getText().trim().isEmpty();

                btnAddStudent.setEnabled(isIdValid && isNameValid);
            }
        };

        idField.addKeyListener(validationAdapter);
        nameField.addKeyListener(validationAdapter);

        btnAddStudent.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();

            // Check if student with this ID already exists
            if (CourseManagement.findStudentById(id) != null) {
                JOptionPane.showMessageDialog(groupPanel, "Error: Student with this ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Student student = new Student(name, id);
                CourseManagement.getStudents().add(student);

                JOptionPane.showMessageDialog(groupPanel, "Student ID " + id + ": " + name + " added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Reset form fields and disable button
                idField.setText("");
                nameField.setText("");
                btnAddStudent.setEnabled(false);
            }
        });

        revalidate();
        repaint();
    }



    /**
     * Hide the Add Student Panel.
     */
    private void hidePanel() {
        if (groupPanel != null) {
            groupPanel.setVisible(false);
        }
    }

    /**
     * Check if all fields are valid.
     */
    private static boolean isBtnEnabled(HashMap<String, Boolean> studentValidationList) {
        return studentValidationList.values().stream().allMatch(Boolean::booleanValue);
    }
}

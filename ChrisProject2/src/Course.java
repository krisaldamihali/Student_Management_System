import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * Represents a course with properties such as course ID, course name, department, and instructor.
 * This class provides methods to interact with course data and fetch courses from a database.
 */
public class Course {

    private final SimpleStringProperty courseId;
    private final SimpleStringProperty courseName;
    private final SimpleStringProperty department;
    private final SimpleStringProperty instructor;

    /**
     * Constructs a Course object with the specified details.
     *
     * @param courseId   the unique identifier for the course
     * @param courseName the name of the course
     * @param department the department offering the course
     * @param instructor the instructor teaching the course
     */
    public Course(String courseId, String courseName, String department, String instructor) {
        this.courseId = new SimpleStringProperty(courseId);
        this.courseName = new SimpleStringProperty(courseName);
        this.department = new SimpleStringProperty(department);
        this.instructor = new SimpleStringProperty(instructor);
    }

    /**
     * Gets the course ID.
     *
     * @return the course ID
     */
    public String getCourseId() {
        return courseId.get();
    }

    /**
     * Gets the course ID property.
     *
     * @return the course ID property
     */
    public StringProperty courseIdProperty() {
        return courseId;
    }

    /**
     * Gets the course name.
     *
     * @return the course name
     */
    public String getCourseName() {
        return courseName.get();
    }

    /**
     * Gets the course name property.
     *
     * @return the course name property
     */
    public StringProperty courseNameProperty() {
        return courseName;
    }

    /**
     * Gets the department offering the course.
     *
     * @return the department
     */
    public String getDepartment() {
        return department.get();
    }

    /**
     * Gets the department property.
     *
     * @return the department property
     */
    public StringProperty departmentProperty() {
        return department;
    }

    /**
     * Gets the instructor teaching the course.
     *
     * @return the instructor
     */
    public String getInstructor() {
        return instructor.get();
    }

    /**
     * Gets the instructor property.
     *
     * @return the instructor property
     */
    public StringProperty instructorProperty() {
        return instructor;
    }

    /**
     * Fetches a list of courses from the database.
     *
     * @return an observable list of Course objects retrieved from the database
     */
    public static ObservableList<Course> fetchCoursesFromDatabase() {
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        String sql = "SELECT course_id, course_name, department, instructor FROM courses";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_management_system", "root", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Iterate through the result set and populate the course list
            while (resultSet.next()) {
                Course course = new Course(
                        resultSet.getString("course_id"),
                        resultSet.getString("course_name"),
                        resultSet.getString("department"),
                        resultSet.getString("instructor")
                );
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList;
    }
}

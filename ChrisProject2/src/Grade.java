import javafx.beans.property.*;

/**
 * Represents a grade record for a student, including details such as the student ID, 
 * course ID, grade, semester, and year. Uses JavaFX properties for binding and observability.
 */
public class Grade {

    private final SimpleStringProperty studentId;
    private final SimpleStringProperty courseId;
    private final SimpleDoubleProperty grade;
    private final SimpleStringProperty semester;
    private final SimpleIntegerProperty year;

    /**
     * Constructs a {@code Grade} object with the specified student ID, course ID, grade, semester, and year.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @param grade     the grade of the student in the course
     * @param semester  the semester in which the grade was earned
     * @param year      the year in which the grade was earned
     */
    public Grade(String studentId, String courseId, double grade, String semester, int year) {
        this.studentId = new SimpleStringProperty(studentId);
        this.courseId = new SimpleStringProperty(courseId);
        this.grade = new SimpleDoubleProperty(grade);
        this.semester = new SimpleStringProperty(semester);
        this.year = new SimpleIntegerProperty(year);
    }

    /**
     * Returns the student ID.
     *
     * @return the student ID as a {@code String}
     */
    public String getStudentId() {
        return studentId.get();
    }

    /**
     * Returns the student ID property.
     *
     * @return the student ID property as a {@code StringProperty}
     */
    public StringProperty studentIdProperty() {
        return studentId;
    }

    /**
     * Returns the course ID.
     *
     * @return the course ID as a {@code String}
     */
    public String getCourseId() {
        return courseId.get();
    }

    /**
     * Returns the course ID property.
     *
     * @return the course ID property as a {@code StringProperty}
     */
    public StringProperty courseIdProperty() {
        return courseId;
    }

    /**
     * Returns the grade.
     *
     * @return the grade as a {@code double}
     */
    public double getGrade() {
        return grade.get();
    }

    /**
     * Returns the grade property.
     *
     * @return the grade property as a {@code DoubleProperty}
     */
    public DoubleProperty gradeProperty() {
        return grade;
    }

    /**
     * Returns the semester.
     *
     * @return the semester as a {@code String}
     */
    public String getSemester() {
        return semester.get();
    }

    /**
     * Returns the semester property.
     *
     * @return the semester property as a {@code StringProperty}
     */
    public StringProperty semesterProperty() {
        return semester;
    }

    /**
     * Returns the year.
     *
     * @return the year as an {@code int}
     */
    public int getYear() {
        return year.get();
    }

    /**
     * Returns the year property.
     *
     * @return the year property as an {@code IntegerProperty}
     */
    public IntegerProperty yearProperty() {
        return year;
    }
}

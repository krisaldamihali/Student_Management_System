import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to handle database connections and operations for the
 * Student Management System. This class includes methods for user
 * authentication, registration, and retrieving student statistics.
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/student_management_system";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    /**
     * Establishes a connection to the database.
     *
     * @return a {@link Connection} object to interact with the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Authenticates a user based on their username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if the credentials are valid, false otherwise
     */
    public static boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Registers a new user in the system.
     *
     * @param username the username of the new user
     * @param email    the email address of the new user
     * @param password the password of the new user
     * @return true if registration is successful, false otherwise
     */
    public static boolean registerUser(String username, String email, String password) {
        try (Connection connection = getConnection()) {
            if (isUsernameTaken(username) || isEmailTaken(email)) {
                return false;
            }

            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if a given username is already taken.
     *
     * @param username the username to check
     * @return true if the username is taken, false otherwise
     */
    public static boolean isUsernameTaken(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        return checkExistence(sql, username);
    }

    /**
     * Checks if a given email address is already in use.
     *
     * @param email the email address to check
     * @return true if the email is in use, false otherwise
     */
    public static boolean isEmailTaken(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        return checkExistence(sql, email);
    }

    /**
     * Retrieves the total number of students in the system.
     *
     * @return the total number of students
     */
    public static int getTotalStudents() {
        return getCount("SELECT COUNT(*) FROM students");
    }

    /**
     * Retrieves the total number of female students in the system.
     *
     * @return the total number of female students
     */
    public static int getFemaleStudents() {
        return getCount("SELECT COUNT(*) FROM students WHERE gender = 'Female'");
    }

    /**
     * Retrieves the total number of male students in the system.
     *
     * @return the total number of male students
     */
    public static int getMaleStudents() {
        return getCount("SELECT COUNT(*) FROM students WHERE gender = 'Male'");
    }

    /**
     * Retrieves the total number of students enrolled on a specific date.
     *
     * @param date the enrollment date
     * @return the total number of students enrolled on the specified date
     */
    public static int getTotalStudentsOnDate(String date) {
        return getCount("SELECT COUNT(*) FROM students WHERE enrollment_date = ?", date);
    }

    /**
     * Retrieves the total number of female students enrolled on a specific date.
     *
     * @param date the enrollment date
     * @return the total number of female students enrolled on the specified date
     */
    public static int getFemaleStudentsOnDate(String date) {
        return getCount("SELECT COUNT(*) FROM students WHERE gender = 'Female' AND enrollment_date = ?", date);
    }

    /**
     * Retrieves the total number of male students enrolled on a specific date.
     *
     * @param date the enrollment date
     * @return the total number of male students enrolled on the specified date
     */
    public static int getMaleStudentsOnDate(String date) {
        return getCount("SELECT COUNT(*) FROM students WHERE gender = 'Male' AND enrollment_date = ?", date);
    }

    /**
     * Retrieves a list of distinct enrollment dates for chart visualization.
     *
     * @return an array of distinct enrollment dates
     */
    public static String[] getDatesForChart() {
        String sql = "SELECT DISTINCT enrollment_date FROM students ORDER BY enrollment_date";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<String> dates = new ArrayList<>();
            while (resultSet.next()) {
                dates.add(resultSet.getString("enrollment_date"));
            }
            return dates.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    /**
     * Retrieves the number of female students for each date in the given array.
     *
     * @param dates an array of dates to query
     * @return an array of counts of female students for each date
     */
    public static int[] getFemaleDataForChart(String[] dates) {
        return getDataForChart("SELECT COUNT(*) FROM students WHERE gender = 'Female' AND enrollment_date = ?", dates);
    }

    /**
     * Retrieves the number of male students for each date in the given array.
     *
     * @param dates an array of dates to query
     * @return an array of counts of male students for each date
     */
    public static int[] getMaleDataForChart(String[] dates) {
        return getDataForChart("SELECT COUNT(*) FROM students WHERE gender = 'Male' AND enrollment_date = ?", dates);
    }

    /**
     * Executes a query to count rows with optional parameters.
     *
     * @param sql    the SQL query to execute
     * @param params optional parameters for the query
     * @return the count result from the query
     */
    private static int getCount(String sql, String... params) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Retrieves data for a chart based on a query and an array of dates.
     *
     * @param query the SQL query to execute
     * @param dates an array of dates to query
     * @return an array of counts corresponding to the dates
     */
    private static int[] getDataForChart(String query, String[] dates) {
        int[] data = new int[dates.length];
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            for (int i = 0; i < dates.length; i++) {
                statement.setString(1, dates[i]);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    data[i] = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Checks the existence of a record in the database based on a query and a parameter.
     *
     * @param sql   the SQL query to execute
     * @param param the parameter to match in the query
     * @return true if the record exists, false otherwise
     */
    private static boolean checkExistence(String sql, String param) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, param);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

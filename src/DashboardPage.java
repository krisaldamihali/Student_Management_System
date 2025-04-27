import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The DashboardPage class represents the dashboard view of the Student Management System.
 * It provides a graphical representation of student data such as the total number of enrolled students,
 * the number of male and female students, and trends over time.
 */
public class DashboardPage extends BasePage {

    /**
     * Displays the dashboard page in the given application.
     * 
     * @param app The main application instance that contains the primary stage.
     */
    @Override
    public void showPage(StudentManagementSystem app) {
        // Create sidebar and dashboard header
        VBox sidebar = createSidebar(app, "home");
        HBox dashboard = createDashboardHeader();

        // Fetch student data from the database
        int totalStudents = DatabaseConnection.getTotalStudents();
        int femaleStudents = DatabaseConnection.getFemaleStudents();
        int maleStudents = DatabaseConnection.getMaleStudents();

        // Create the dashboard boxes to display total and gender-wise student counts
        VBox totalStudentsBox = createDashboardBox("Total Enrolled Students", String.valueOf(totalStudents), "\u1F464");
        VBox femaleStudentsBox = createDashboardBox("Enrolled Female Students", String.valueOf(femaleStudents), "\u2640");
        VBox maleStudentsBox = createDashboardBox("Enrolled Male Students", String.valueOf(maleStudents), "\u2642");

        // Add the boxes to the dashboard header
        dashboard.getChildren().addAll(totalStudentsBox, femaleStudentsBox, maleStudentsBox);

        // Fetch data for the charts
        String[] dates = DatabaseConnection.getDatesForChart();
        int[] femaleData = DatabaseConnection.getFemaleDataForChart(dates);
        int[] maleData = DatabaseConnection.getMaleDataForChart(dates);

        // Create line charts for female and male students over time
        LineChart<String, Number> femaleChart = createLineChart("Enrolled Female Chart", dates, femaleData);
        LineChart<String, Number> maleChart = createLineChart("Enrolled Male Chart", dates, maleData);
        femaleChart.setPrefHeight(120);
        maleChart.setPrefHeight(120);

        // Combine the gender charts into a VBox
        VBox genderCharts = new VBox(10);
        genderCharts.setStyle("-fx-padding: 10px;");
        genderCharts.getChildren().addAll(femaleChart, maleChart);

        // Create a bar chart showing total, female, and male students
        BorderPane chartsLayout = new BorderPane();
        chartsLayout.setStyle("-fx-padding: 10px;");
        chartsLayout.setPrefSize(650, 400);
        BarChart<String, Number> totalChart = createBarChart("Total Enrolled Chart", totalStudents, femaleStudents, maleStudents);
        totalChart.setPrefWidth(650);

        // Set the total chart in the center and gender charts on the right side
        chartsLayout.setCenter(totalChart);
        chartsLayout.setRight(genderCharts);

        // Create the main layout with the sidebar and charts
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(chartsLayout);

        // Create the right-side layout with the dashboard and charts
        BorderPane rightSideLayout = new BorderPane();
        rightSideLayout.setTop(dashboard);
        rightSideLayout.setCenter(chartsLayout);
        mainLayout.setRight(rightSideLayout);

        // Create the scene and display it
        Scene scene = new Scene(mainLayout, 900, 600);
        app.getPrimaryStage().setTitle("Student Management System");
        app.getPrimaryStage().setScene(scene);
        app.getPrimaryStage().setResizable(false);
        app.getPrimaryStage().show();
    }

    /**
     * Creates a dashboard box with a title, value, and icon.
     *
     * @param title       The title of the dashboard box.
     * @param value       The value to be displayed in the box.
     * @param iconSymbol  The symbol to be used as the icon in the box.
     * @return A VBox representing the dashboard box.
     */
    private VBox createDashboardBox(String title, String value, String iconSymbol) {
        VBox box = new VBox(5);
        box.setStyle("-fx-background-color: #005580; -fx-padding: 10px; -fx-alignment: center; -fx-pref-width: 150px; -fx-pref-height: 100px;");

        Label icon = new Label(iconSymbol);
        icon.setStyle("-fx-text-fill: white; -fx-font-size: 30px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        box.getChildren().addAll(icon, titleLabel, valueLabel);
        return box;
    }

    /**
     * Creates a bar chart displaying the total, female, and male students.
     *
     * @param title   The title of the bar chart.
     * @param total   The total number of students.
     * @param female  The number of female students.
     * @param male    The number of male students.
     * @return A BarChart displaying the data.
     */
    private BarChart<String, Number> createBarChart(String title, int total, int female, int male) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Category");
        yAxis.setLabel("Number");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);
        barChart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Total", total));
        series.getData().add(new XYChart.Data<>("Female", female));
        series.getData().add(new XYChart.Data<>("Male", male));

        barChart.getData().add(series);
        return barChart;
    }

    /**
     * Creates a line chart to display trends of data over time.
     *
     * @param title   The title of the line chart.
     * @param dates   The dates representing the time period.
     * @param data    The data to be displayed in the chart.
     * @return A LineChart displaying the data.
     */
    private LineChart<String, Number> createLineChart(String title, String[] dates, int[] data) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Count");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);
        lineChart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (int i = 0; i < dates.length; i++) {
            series.getData().add(new XYChart.Data<>(dates[i], data[i]));
        }

        lineChart.getData().add(series);
        return lineChart;
    }

    /**
     * Creates a header layout for the dashboard, typically including labels or titles.
     *
     * @return A HBox containing the dashboard header.
     */
    private HBox createDashboardHeader() {
        HBox dashboard = new HBox(20);
        dashboard.setStyle("-fx-padding: 10px; -fx-background-color: #003366; -fx-pref-height: 120px;");
        dashboard.setAlignment(javafx.geometry.Pos.CENTER);
        return dashboard;
    }
}

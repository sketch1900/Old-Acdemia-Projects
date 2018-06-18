/*
 *Contains the column layout for tables and gridpane layout.
 */
package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class TableMaker {

    TableView<TableData> table;
    TableView<Hall> hallTable;
    TableView<Lease> leaseTable;

    /**
     * Default constructor
     */
    public TableMaker() {
    }

    /**
     * @param table set TableData TableView
     */
    public void normalTable(TableView<TableData> table) {
        this.table = table;
    }

    /**
     * @param table set Hall TableView
     */
    public void hallTable(TableView<Hall> table) {
        this.hallTable = table;
    }

    /**
     * @param table set Lease TableView
     */
    public void leaseTable(TableView<Lease> table) {
        this.leaseTable = table;
    }

    /**
     * Creates the Manager/all/warden table layout
     *
     * @param tableId set unique table id for css
     * @param height
     * @param width
     */
    public void createTable(String tableId, double height, double width) {
        //tableview postion etc
        table.setId(tableId);
        table.setEditable(true);
        table.setMinWidth(width);
        table.setMaxHeight(height);
        table.setTranslateX(20);
        table.setTranslateY(10);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //set the column names and width 
        TableColumn leaseNumCol = new TableColumn("Lease\nNumber");
        leaseNumCol.setMinWidth(70);
        leaseNumCol.setCellValueFactory(
                new PropertyValueFactory<>("leaseNumber"));

        TableColumn hallCol = new TableColumn("Hall\nName");
        hallCol.setMinWidth(100);
        hallCol.setCellValueFactory(
                new PropertyValueFactory<>("hallName"));

        TableColumn hallNumCol = new TableColumn("Hall\nNumber");
        hallNumCol.setMinWidth(70);
        hallNumCol.setCellValueFactory(
                new PropertyValueFactory<>("hallNumber"));

        TableColumn roomNumCol = new TableColumn("Room\nNumber");
        roomNumCol.setMinWidth(70);
        roomNumCol.setCellValueFactory(
                new PropertyValueFactory<>("roomNumber"));

        TableColumn rentRateCol = new TableColumn("Monthly\nRent Rate");
        rentRateCol.setMinWidth(100);
        rentRateCol.setCellValueFactory(
                new PropertyValueFactory<>("rentRate"));

        TableColumn durationCol = new TableColumn("Duration");
        durationCol.setMinWidth(60);
        durationCol.setCellValueFactory(
                new PropertyValueFactory<>("duration"));

        TableColumn studentNumCol = new TableColumn("Student\nName");
        studentNumCol.setMinWidth(100);
        studentNumCol.setCellValueFactory(
                new PropertyValueFactory<>("studentName"));

        TableColumn occupancyCol = new TableColumn("Occupancy\nStatus");
        occupancyCol.setMinWidth(70);
        occupancyCol.setCellValueFactory(
                new PropertyValueFactory<>("occupancy"));

        TableColumn cleaningCol = new TableColumn("Cleaning\nStatus");
        cleaningCol.setMinWidth(60);
        cleaningCol.setCellValueFactory(
                new PropertyValueFactory<>("cleaningStatus"));

        table.getColumns().addAll(leaseNumCol, hallCol, hallNumCol, roomNumCol,
                rentRateCol, durationCol, studentNumCol, occupancyCol, cleaningCol);
    }

    /**
     * Create the Hall management table layout
     * @param height
     * @param width
     */
    public void createHallTable(double height, double width) {
        hallTable.setId("table2");
        hallTable.setEditable(true);
        hallTable.setMinWidth(width);
        hallTable.setMaxHeight(height);
        hallTable.setTranslateX(20);
        hallTable.setTranslateY(10);
        hallTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn hallNumCol = new TableColumn("Hall\nNumber");
        hallNumCol.setMinWidth(100);
        hallNumCol.setCellValueFactory(
                new PropertyValueFactory<>("hallNumber"));

        TableColumn hallNameCol = new TableColumn("Hall\nName");
        hallNameCol.setMinWidth(100);
        hallNameCol.setCellValueFactory(
                new PropertyValueFactory<>("hallName"));

        TableColumn hallAddressCol = new TableColumn("Hall\nAddress");
        hallAddressCol.setMinWidth(100);
        hallAddressCol.setCellValueFactory(
                new PropertyValueFactory<>("hallAddress"));

        TableColumn roomCountCol = new TableColumn("Room\nCount");
        roomCountCol.setMinWidth(100);
        roomCountCol.setCellValueFactory(
                new PropertyValueFactory<>("roomCount"));

        TableColumn managerCol = new TableColumn("Manager");
        managerCol.setMinWidth(100);
        managerCol.setCellValueFactory(
                new PropertyValueFactory<>("manager"));

        TableColumn wardenCol = new TableColumn("Warden");
        wardenCol.setMinWidth(100);
        wardenCol.setCellValueFactory(
                new PropertyValueFactory<>("warden"));

        hallTable.getColumns().addAll(hallNameCol, hallNumCol, hallAddressCol,
                roomCountCol, managerCol, wardenCol);
    }

    /**
     * Create Lease management table layout
     * @param height
     * @param width
     */
    public void createLeaseTable(double height, double width) {
        leaseTable.setId("table3");
        leaseTable.setEditable(true);
        leaseTable.setMinWidth(width);
        leaseTable.setMaxHeight(height);
        leaseTable.setTranslateX(20);
        leaseTable.setTranslateY(10);
        leaseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //set the column names and width 
        TableColumn leaseNumCol = new TableColumn("Lease\nNumber");
        leaseNumCol.setMinWidth(100);
        leaseNumCol.setCellValueFactory(
                new PropertyValueFactory<>("leaseNumber"));

        TableColumn studentNumCol = new TableColumn("Student\nName");
        studentNumCol.setMinWidth(100);
        studentNumCol.setCellValueFactory(
                new PropertyValueFactory<>("studentName"));

        TableColumn hallNumCol = new TableColumn("Hall\nNumber");
        hallNumCol.setMinWidth(100);
        hallNumCol.setCellValueFactory(
                new PropertyValueFactory<>("hallNumber"));

        TableColumn roomNumCol = new TableColumn("Room\nNumber");
        roomNumCol.setMinWidth(100);
        roomNumCol.setCellValueFactory(
                new PropertyValueFactory<>("roomNumber"));

        TableColumn rentRateCol = new TableColumn("Monthly\nRent Rate");
        rentRateCol.setMinWidth(100);
        rentRateCol.setCellValueFactory(
                new PropertyValueFactory<>("rentRate"));

        TableColumn durationCol = new TableColumn("Duration");
        durationCol.setMinWidth(100);
        durationCol.setCellValueFactory(
                new PropertyValueFactory<>("duration"));

        TableColumn studentIdCol = new TableColumn("Student\nId");
        studentIdCol.setMinWidth(100);
        studentIdCol.setCellValueFactory(
                new PropertyValueFactory<>("studentId"));

        leaseTable.getColumns().addAll(leaseNumCol, studentNumCol, hallNumCol,
                roomNumCol, rentRateCol, durationCol, studentIdCol);
    }

    /**
     * Creates the standard grid for the tab
     *
     * @param grid
     * @param tabName
     * @param width
     * @return New Tab containing the gridPane Layout
     *
     */
    public Tab createGrid(GridPane grid, String tabName, double width) {
        Tab tab = new Tab();
        tab.setText(tabName);
        tab.setId(tabName.toLowerCase());

        /* Settup the event listeners **/
        grid.setId("grid1");
        grid.setMinWidth(width);
        grid.setAlignment(Pos.BOTTOM_CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setTranslateX(20);
        grid.setVgap(10);
        grid.setHgap(10);

        return tab;
    }
}

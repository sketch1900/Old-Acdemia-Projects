/*
 *Contains the basic layout for the first three tabs: tabA, tabB and tabC 
 */
package app;

import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GeneralTable {

    Modal modal;
    TableMaker make;

    /**
     * @param modal takes modal as modal for the first 3 tables
     */
    public GeneralTable(Modal modal) {
        this.modal = modal;
        this.make = new TableMaker();
    }

    /**
     * Label creator
     *
     * @param labelName
     * @param width
     * @return the label
     */
    public Label label(String labelName, int width) {
        Label l = new Label(labelName);
        l.minWidth(width);
        return l;
    }

    /**
     * TextField Creator
     *
     * @param fieldId
     * @return a textfield
     */
    public TextField text(String fieldId) {
        TextField t = new TextField();
        t.setId(fieldId);
        return t;
    }
    
    /**
     * Validation function for Input.
     * 
     * @param duration
     * @param leaseNumText
     * @param stuName
     * @return 
     */
    public boolean validation(String duration, String leaseNumText,
            String stuName){

        boolean bool = false;

        boolean a = Pattern.matches("[0-9]{0,2}", duration);  
        boolean b = Pattern.matches("[0-9a-zA-Z]{8}", leaseNumText); 
        boolean c = Pattern.matches("[0-9a-zA-Z\\-\\s\\'\\.]+", stuName); 

        if(a && b && c){
            bool = true;
        }
        return bool;
    }

    /**
     * Layout for TabA Manager
     *
     * @param table
     * @param bp
     * @param grid
     * @param height
     * @param width
     * @return TabA
     */
    public Tab tabA(TableView<TableData> table, BorderPane bp, GridPane grid,
            double height, double width) {
        
        make.normalTable(table);
        make.createTable("table1",  height, width);
        Tab tabA = make.createGrid(grid, "Manager", width);

        /**
         * Buttons and comboBoxes
         */
        ComboBox<String> occupancy = new ComboBox();
        occupancy.getItems().addAll("Unoccupied", "Occupied");
        occupancy.setEditable(false);

        Button upButton = new Button("Update");
        Button delButton = new Button("Delete");

        /**
         * TextFields
         */
        TextField leaseNum1 = text("leaseNum1");
        TextField studentName1 = text("studentName1");
        TextField hallName1 = text("hallName1");
        TextField hallNum1 = text("hallNum1");
        TextField roomNum1 = text("roomNum1");
        TextField duration1 = text("duration");

        /**
         * Labels
         */
        Label clean = label("Hall Status ", 100);
        Label hallNum = label("Hall Number ", 40);
        Label hName = label("Hall Name ", 40);
        Label rNum = label("Room Number ", 60);
        Label lNum = label("Lease Number ", 60);
        Label sName = label("Student Name ", 60);
        Label occ = label("Occupancy ", 40);
        Label dura = label("Duration", 40);
        Label errorMess = label("",200);

        clean.setId("cleanText");
        errorMess.setId("error1");

        /*
         * Add to gridPane and set position
         */
        grid.add(clean, 0, 0);
        grid.add(errorMess, 1,0);

        grid.add(hName, 0, 1);
        grid.add(hallName1, 1, 1);

        grid.add(hallNum, 0, 2);
        grid.add(hallNum1, 1, 2);

        grid.add(rNum, 0, 3);
        grid.add(roomNum1, 1, 3);

        grid.add(dura, 0, 4);
        grid.add(duration1, 1, 4);

        grid.add(lNum, 7, 1);
        grid.add(leaseNum1, 8, 1);

        grid.add(sName, 7, 2);
        grid.add(studentName1, 8, 2);

        grid.add(occ, 7, 3);
        grid.add(occupancy, 8, 3);

        grid.add(delButton, 7, 4);
        grid.add(upButton, 8, 4);

        //Gets the row and updates the textfields with it using the tableRow events
        table.setRowFactory(tv -> {
            TableRow<TableData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    TableData clickedRow = row.getItem();

                    hallName1.setText(clickedRow.getHallName());
                    hallNum1.setText(clickedRow.getHallNumber());
                    roomNum1.setText(clickedRow.getRoomNumber());
                    leaseNum1.setText(clickedRow.getLeaseNumber());
                    studentName1.setText(clickedRow.getStudentName());
                    occupancy.setValue(clickedRow.getOccupancy());
                    duration1.setText(clickedRow.getDuration());
                }
            });
            return row;
        });

        //gets the modals items and then displays within the list as the observable
        table.setItems(FXCollections.observableArrayList(modal.getItems()));

        //update entries button
        upButton.setOnAction((ActionEvent event) -> {
             //validation on the user input
            String duration = duration1.getText();
            String leaseNumText = leaseNum1.getText();
            String stuName = studentName1.getText();
            
            boolean valid = validation(duration, leaseNumText, stuName);
            
            if(valid){
                //for each loop updating exsisting entrys
                for (TableData t : modal.getItems()) {
                    if (t.getCleaningStatus().equals("Clean")) {
                        if ((t.getRoomNumber().equals(roomNum1.getText()))
                                && (t.getHallNumber().equals(hallNum1.getText()))) {

                            t.setDuration(duration);
                            t.setOccupancy(occupancy.getValue());
                            t.setLeaseNumber(leaseNumText);
                            t.setStudentName(stuName);
                            break;
                        }
                    }
                }
            }else{
                errorMess.setText("Incorrect Entry");
            }
            table.setItems(FXCollections.observableArrayList(modal.getItems()));
            table.refresh();
        });

        //delete student Name and lease number button 
        delButton.setOnAction((ActionEvent e) -> {
            //for each loop
            for (TableData t : modal.getItems()) {
                if (t.getLeaseNumber().equals(leaseNum1.getText())) {
                    t.setLeaseNumber("");
                    t.setStudentName("");
                    t.setDuration("");
                    t.setOccupancy("Unoccupied");
                }
            }
            table.setItems(FXCollections.observableArrayList(modal.getItems()));
            table.refresh();
        });

        /* add table to the content of tab 1*/
        HBox hbox1 = new HBox(grid);
        HBox hbox2 = new HBox(table);

        bp.setBottom(hbox1);
        bp.setCenter(hbox2);
        tabA.setContent(bp);
        return tabA;
    }

    /**
     * Layout for tabB Warden
     *
     * @param table
     * @param bp2
     * @param grid2
     * @param height
     * @param width
     * @return TabB
     */
    public Tab tabB(TableView<TableData> table, BorderPane bp2, GridPane grid2,
            double height, double width) {

        make.normalTable(table);
        make.createTable("table2", height, width);
        Tab tabB = make.createGrid(grid2, "Warden", width);

        /**
         * Buttons and comboBoxes
         */
        ComboBox<String> status2 = new ComboBox<>();
        status2.getItems().addAll("Dirty", "Clean", "Offline");
        status2.setEditable(false);
        status2.setMinWidth(200);

        Button applyButton = new Button("Apply");

        /**
         * TextFields
         */
        TextField leaseNum2 = text("leaseNum2");
        TextField studentName2 = text("studentName2");
        TextField hallName2 = text("hallName2");
        TextField hallNum2 = text("hallNum2");
        TextField roomNum2 = text("roomNum2");
        TextField occupancy2 = text("occupancy2");

        /**
         * Labels
         */
        Label clean2 = label("Cleaning Status ", 100);
        Label hallNumber2 = label("Hall Number ", 40);
        Label hName2 = label("Hall Name ", 40);
        Label rNum2 = label("Room Number ", 60);
        Label lNum2 = label("Lease Number ", 60);
        Label sName2 = label("Student Name ", 60);
        Label occ2 = label("Occupancy ", 40);
        Label cleaningStat2 = label("Cleaning Status", 100);

        clean2.setId("cleanText2");

        /**
         * Add to gridPane and set position
         */
        grid2.add(clean2, 0, 0);

        grid2.add(lNum2, 0, 1);
        grid2.add(leaseNum2, 1, 1);

        grid2.add(hName2, 0, 2);
        grid2.add(hallName2, 1, 2);

        grid2.add(hallNum2, 1, 3);
        grid2.add(hallNumber2, 0, 3);

        grid2.add(rNum2, 0, 4);
        grid2.add(roomNum2, 1, 4);

        grid2.add(sName2, 7, 1);
        grid2.add(studentName2, 8, 1);

        grid2.add(occ2, 7, 2);
        grid2.add(occupancy2, 8, 2);

        grid2.add(cleaningStat2, 7, 3);
        grid2.add(status2, 8, 3);

        grid2.add(applyButton, 8, 4);

        //Gets the row and updates the textfields with it using the tableRow events
        table.setRowFactory(tv -> {
            TableRow<TableData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    TableData clickedRow = row.getItem();

                    hallName2.setText(clickedRow.getHallName());
                    hallNum2.setText(clickedRow.getHallNumber());
                    roomNum2.setText(clickedRow.getRoomNumber());
                    leaseNum2.setText(clickedRow.getLeaseNumber());
                    studentName2.setText(clickedRow.getStudentName());
                    occupancy2.setText(clickedRow.getOccupancy());
                    status2.setValue(clickedRow.getCleaningStatus());
                }
            });
            return row;
        });

        //gets the modals items and then displays within the list as the observable
        table.setItems(FXCollections.observableArrayList(modal.getItems()));

        //update entries button
        applyButton.setOnAction((ActionEvent event) -> {

            //update the cleaning status by checking the fields match
            for (TableData t : modal.getItems()) {
                // for (int i = 0; i < modal.getItems().size(); i++) {
                if ((t.getRoomNumber().equals(roomNum2.getText()))
                        && (t.getHallNumber().equals(hallNum2.getText()))) {
                    t.setCleaningStatus(status2.getValue());
                }
            }
            table.setItems(FXCollections.observableArrayList(modal.getItems()));
            table.refresh();
        });

        HBox wardenHBox1 = new HBox(grid2);
        HBox wardenHBox2 = new HBox(table);

        bp2.setBottom(wardenHBox1);
        bp2.setCenter(wardenHBox2);
        tabB.setContent(bp2);
        return tabB;
    }

    /**
     * Layout for tabC All
     *
     * @param table
     * @param bp3
     * @param grid3
     * @param height
     * @param width
     * @return TabC
     */
    public Tab tabC(TableView<TableData> table, BorderPane bp3, GridPane grid3,
            double height, double width) {
        
        make.normalTable(table);
        make.createTable("table3", height, width);
        Tab tabC = make.createGrid(grid3, "All", width);

        /**
         * Buttons and comboBoxes
         */
        ComboBox<String> status3 = new ComboBox<>();
        status3.getItems().addAll("Dirty", "Clean", "Offline");
        status3.setEditable(false);
        status3.setMinWidth(40);

        ComboBox<String> occupancy3 = new ComboBox();
        occupancy3.getItems().addAll("Unoccupied", "Occupied");
        occupancy3.setEditable(false);
        occupancy3.setMinWidth(40);

        Button upButton2 = new Button("Update");
        Button delButton2 = new Button("Delete");

        /**
         * TextFields
         */
        TextField leaseNum3 = text("leaseNum3");
        TextField studentName3 = text("studentName3");
        TextField hallName3 = text("hallName3");
        TextField hallNum3 = text("hallNum3");
        TextField roomNum3 = text("roomNum3");
        TextField duration2 = text("duration2");

        /**
         * Labels
         */
        Label clean3 = label("Status ", 100);
        Label hallNumber3 = label("Hall Number ", 40);
        Label hName3 = label("Hall Name ", 40);
        Label rNum3 = label("Room Number ", 60);
        Label lNum3 = label("Lease Number ", 60);
        Label sName3 = label("Student Name ", 60);
        Label occ3 = label("Occupancy ", 60);
        Label cleaningStat3 = label("Cleaning Status ", 100);
        Label dura2 = label("Duration", 40);
        Label errorMess2 = label("", 200);

        clean3.setId("cleanText3");
        errorMess2.setId("error2");

        /**
         * Add to gridPane and set position
         */
        grid3.add(clean3, 0, 0);
        grid3.add(errorMess2, 0,1);

        grid3.add(lNum3, 0, 1);
        grid3.add(leaseNum3, 1, 1);

        grid3.add(hName3, 0, 2);
        grid3.add(hallName3, 1, 2);

        grid3.add(hallNum3, 1, 3);
        grid3.add(hallNumber3, 0, 3);

        grid3.add(rNum3, 0, 4);
        grid3.add(roomNum3, 1, 4);

        grid3.add(dura2, 7, 0);
        grid3.add(duration2, 8, 0);

        grid3.add(sName3, 7, 1);
        grid3.add(studentName3, 8, 1);

        grid3.add(occ3, 7, 2);
        grid3.add(occupancy3, 8, 2);

        grid3.add(cleaningStat3, 7, 3);
        grid3.add(status3, 8, 3);

        grid3.add(delButton2, 7, 4);
        grid3.add(upButton2, 8, 4);

        //Gets the row and updates the textfields with it using the tableRow events
        table.setRowFactory(tv -> {
            TableRow<TableData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    TableData clickedRow = row.getItem();

                    hallName3.setText(clickedRow.getHallName());
                    hallNum3.setText(clickedRow.getHallNumber());
                    roomNum3.setText(clickedRow.getRoomNumber());
                    leaseNum3.setText(clickedRow.getLeaseNumber());
                    studentName3.setText(clickedRow.getStudentName());
                    occupancy3.setValue(clickedRow.getOccupancy());
                    status3.setValue(clickedRow.getCleaningStatus());
                    duration2.setText(clickedRow.getDuration());
                }
            });
            return row;
        });

        //gets the modals items and then displays within the list as the observable
        table.setItems(FXCollections.observableArrayList(modal.getItems()));

        //update entries button
        upButton2.setOnAction((ActionEvent event) -> {
             //validation on the user input
            String dura = duration2.getText();
            String leaseNumText2 = leaseNum3.getText();
            String stuName2 = studentName3.getText();
            
            boolean valid2 = validation(dura, leaseNumText2, stuName2);

            if(valid2){
                for (TableData t : modal.getItems()) {
                    if ((t.getRoomNumber().equals(roomNum3.getText()))
                            && (t.getHallNumber().equals(hallNum3.getText()))) {

                        t.setOccupancy(occupancy3.getValue());
                        t.setLeaseNumber(leaseNumText2);
                        t.setStudentName(stuName2);
                        t.setCleaningStatus(status3.getValue());
                        t.setDuration(dura);
                        break;
                    }
                }
            }else{
                errorMess2.setText("Incorrect entrys.");
            }
            table.setItems(FXCollections.observableArrayList(modal.getItems()));
            table.refresh();
        });

        //delete student Name and lease number button 
        delButton2.setOnAction((ActionEvent e) -> {
            for (TableData t : modal.getItems()) {
                if (t.getLeaseNumber().equals(leaseNum3.getText())) {
                    t.setLeaseNumber("");
                    t.setStudentName("");
                    t.setDuration("");
                    t.setOccupancy("Unoccupied");
                }
            }
            table.setItems(FXCollections.observableArrayList(modal.getItems()));
            table.refresh();
        });

        HBox allHBox1 = new HBox(grid3);
        HBox allHBox2 = new HBox(table);

        bp3.setBottom(allHBox1);
        bp3.setCenter(allHBox2);
        tabC.setContent(bp3);

        return tabC;
    }

    /**
     * TabD Lease Management
     *
     * @param table
     * @param bp
     * @param gp
     * @param acco
     * @param height
     * @param width
     * @return TabD
     */
    public Tab tabD(TableView<Lease> table, BorderPane bp, GridPane gp,
            AccomodationOffice acco, double height, double width) {

        TableMaker make1 = new TableMaker();

        make1.leaseTable(table);
        make1.createLeaseTable(height, width);

        Tab tabD = make1.createGrid(gp, "Lease", width);
        tabD.setId("leas");

        /**
         * Buttons and comboBoxes
         */
        Button upButton = new Button("Add");
        Button delButton = new Button("Remove");

        /**
         * TextFields
         */
        TextField leaseNum1 = text("leaseNum4");
        TextField studentName1 = text("studentName4");
        TextField hallNum1 = text("hallNumber4");
        TextField roomNum1 = text("roomNum4");
        TextField studentId = text("studentId");
        TextField duration1 = text("duration4");

        /**
         * Labels
         */
        Label clean = label("Lease Creation ", 150);
        Label hallNum = label("Hall Number ", 40);
        Label rNum = label("Room Number ", 60);
        Label lNum = label("Lease Number ", 100);
        Label sName = label("Student Name ", 60);
        Label studentId1 = label("Student Id Number ", 100);
        Label dura = label("Duration ", 40);

        clean.setId("cleanText4");

        /* Add to gridPane and set position */
        gp.add(clean, 0, 0);

        gp.add(studentId1, 0, 1);
        gp.add(studentId, 1, 1);

        gp.add(hallNum, 0, 2);
        gp.add(hallNum1, 1, 2);

        gp.add(rNum, 0, 3);
        gp.add(roomNum1, 1, 3);

        gp.add(dura, 0, 4);
        gp.add(duration1, 1, 4);

        gp.add(lNum, 7, 1);
        gp.add(leaseNum1, 8, 1);

        gp.add(sName, 7, 2);
        gp.add(studentName1, 8, 2);

        gp.add(delButton, 8, 4);
        gp.add(upButton, 9, 4);
        //Gets the row and updates the textfields with it using the tableRow events
        table.setRowFactory(tv -> {
            TableRow<Lease> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Lease clickedRow = row.getItem();

                    hallNum1.setText(clickedRow.getHallNumber());
                    roomNum1.setText(clickedRow.getRoomNumber());
                    leaseNum1.setText(clickedRow.getLeaseNumber());
                    studentName1.setText(clickedRow.getStudentName());
                    studentId.setText(clickedRow.getStudentId());
                    duration1.setText(clickedRow.getDuration());
                }
            });
            return row;
        });

        //gets the modals items and then displays within the list as the observable
        table.setItems(FXCollections.observableArrayList(modal.getLeaseItems()));

        //update entries button
        upButton.setOnAction((ActionEvent event) -> {
            boolean result = true;
            String rentR = "";

            //checks if the result is false else true
            for (Lease l : modal.getLeaseItems()) {
                result = l.getLeaseNumber().equals(leaseNum1.getText());
            }
            //checks if hall exsists and then gets the rent rate
            for (Hall hall4 : acco.getHalls()) {
                if (hall4.getHallNumber().equals(hallNum1.getText())) {
                    for (Room room : hall4.getRoom()) {
                        rentR = Integer.toString(room.getRentRate());
                    }
                }
            }
            //sets the new data into the modal
            if (!result) {
                //lease
                Lease lease2 = new Lease(leaseNum1.getText(),
                        studentName1.getText(), hallNum1.getText(), roomNum1.getText(),
                        duration1.getText(), rentR, studentId.getText());

                acco.getLease().add(lease2);
                modal.getLeaseItems().add(lease2);
            }

            table.setItems(FXCollections.observableArrayList(modal.getLeaseItems()));
            table.refresh();
        });

        //delete student Name and lease number button 
        delButton.setOnAction((ActionEvent e) -> {

            try {
                for (Lease l : modal.getLeaseItems()) {
                    if (l.getLeaseNumber().equals(leaseNum1.getText())) {
                        if (modal.getLeaseItems().size() == 1) {

                            l.setDuration("");
                            l.setHallNumber("");
                            l.setLeaseNumber("");
                            l.setRentRate("");
                            l.setStudentId("");
                            l.setStudentName("");
                            l.setRoomNumber("");
                            break;
                        } else {
                            acco.getLease().remove(l);
                            modal.getLeaseItems().remove(l);
                            break;
                        }

                    }
                }
            } catch (IndexOutOfBoundsException exception) {
                //drop the error
            }
            table.setItems(FXCollections.observableArrayList(modal.getLeaseItems()));
            table.refresh();
        });

        HBox leaseHBox1 = new HBox(gp);
        HBox leaseHBox2 = new HBox(table);

        bp.setCenter(leaseHBox2);
        bp.setBottom(leaseHBox1);
        tabD.setContent(bp);

        return tabD;
    }

    /**
     * TabE Hall Management
     *
     * @param table
     * @param bp
     * @param gp
     * @param acco
     * @param height
     * @param width
     * @return TabE
     */
    public Tab tabE(TableView<Hall> table, BorderPane bp, GridPane gp,
            AccomodationOffice acco, 
            double height, double width){

        make.hallTable(table);
        make.createHallTable(height, width);

        Tab tabE = make.createGrid(gp, "hall", width);
        tabE.setId("hall");

        tabE.setText("Hall Management");

        /**
         * Buttons and comboBoxes
         */
        Button upButton2 = new Button("Add");
        Button updateButton = new Button("Update");

        TextField hallName2 = text("hallName5");
        TextField hallAddress = text("hallAddress");
        TextField hallNum2 = text("hallNumber5");
        TextField roomCount = text("roomCount");
        TextField warden1 = text("warden1");
        TextField rentRate2 = text("rentRate2");
        TextField manager1 = text("manager1");

        /**
         * Labels
         */
        Label clean2 = label("Hall Management", 120);
        Label hallNum3 = label("Hall Number ", 60);
        Label hName2 = label("Hall Name ", 60);
        Label rCount = label("Room Count ", 60);
        Label warden2 = label("Warden ", 60);
        Label manager2 = label("Manager ", 60);
        Label rRate1 = label("Rent Rate ", 70);
        Label hAddress = label("Hall Address ", 40);
        Label errorMess3 = label("", 200);

        clean2.setId("cleanText5");
        errorMess3.setId("error3");

        /**
         * Add to gridPane and set position
         */
        gp.add(clean2, 0, 0);
        gp.add(errorMess3, 1,0);

        gp.add(hallNum3, 0, 1);
        gp.add(hallNum2, 1, 1);

        gp.add(hName2, 0, 2);
        gp.add(hallName2, 1, 2);

        gp.add(hAddress, 0, 3);
        gp.add(hallAddress, 1, 3);

        gp.add(rCount, 0, 4);
        gp.add(roomCount, 1, 4);

        gp.add(manager2, 7, 1);
        gp.add(manager1, 8, 1);

        gp.add(warden2, 7, 2);
        gp.add(warden1, 8, 2);

        gp.add(rRate1, 7, 3);
        gp.add(rentRate2, 8, 3);

        gp.add(updateButton, 7, 4);
        gp.add(upButton2, 8, 4);
        
        //gets the modals items and then displays within the list as the observable
        table.setItems(FXCollections.observableArrayList(modal.getHallItems()));

        //Gets the row and updates the textfields with it using the tableRow events
        table.setRowFactory(tv -> {
            TableRow<Hall> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Hall clickedRow = row.getItem();

                    hallNum2.setText(clickedRow.getHallNumber());
                    hallName2.setText(clickedRow.getHallName());
                    hallAddress.setText(clickedRow.getHallAddress());
                    roomCount.setText(clickedRow.getRoomCount());
                    warden1.setText(clickedRow.getWarden());
                    manager1.setText(clickedRow.getManager());

                    //loop through finding the acco.getHalls list rent rate for the
                    //textfields based on the ClickedRow.getRentRate() column
                    for (Hall h : acco.getHalls()) {
                        if (h.getHallNumber().equals(clickedRow.getHallNumber())) {
                            rentRate2.setText(Integer.toString(h.getRoom()
                                    .get(0).getRentRate()));
                        }
                    }
                }
            });
            return row;
        });

        /* Bugged and need to be fixed wont add the rooms to the others table and adds multiples of it.**/
        
        //update exisiting entries button
        upButton2.setOnAction((ActionEvent event) -> {
            boolean result = true;
            boolean secondCheck = false;
    
            boolean a = Pattern.matches("[a-zA-Z0-9]+", hallNum2.getText());  
            boolean b = Pattern.matches("[0-9a-zA-Z\\-\\s\\'\\.]+", hallName2.getText()); 
            boolean c = Pattern.matches("[0-9a-zA-Z\\-\\s\\'\\.]+", hallAddress.getText()); 
            boolean d = Pattern.matches("[0-9a-zA-Z\\-\\s\\'\\.]+", manager1.getText());  
            boolean e = Pattern.matches("[0-9a-zA-Z\\-\\s\\'\\.]+", warden1.getText());
            
            //validate first
            if(a && b && c && d && e){
                //loop
                for (Hall h : modal.getHallItems()) {
                    
                    //check if it exsits already
                    result = h.getHallNumber().equals(hallNum2.getText()) && 
                    h.getHallName().equals(hallName2.getText());
                    
                }
            }else{
                errorMess3.setText("Incorrect Entry information");
            }
            
            //if it doesnt already exsist
            if (!result) {
                //Halls
                Hall hall3 = new Hall(hallName2.getText(), hallNum2.getText(),
                        hallAddress.getText(), roomCount.getText(),
                        manager1.getText(), warden1.getText());

                //generate rooms
                hall3.generateRooms(Integer.parseInt(rentRate2.getText()));
                
                //add the hall and rooms to the modal
                modal.getHallItems().add(hall3);
                
                //refresh the modals
                table.setItems(FXCollections.observableArrayList(modal.getHallItems()));
                table.refresh();
                
                //check if it exsits already again
                for (Hall h : modal.getHallItems()) {
                    secondCheck = h.getHallNumber().equals(hallNum2.getText()) && 
                    h.getHallName().equals(hallName2.getText());
                }
                
                //if it does the rooms and hall information to manager/warden/all tables 
                if(secondCheck){
                    TableData tData1;
                    
                    for(int l = 0; l <= Integer.parseInt(hall3.getRoomCount()); l++){                  
                        tData1 = new TableData("", hallName2.getText(), hallNum2.getText(),
                                Integer.toString(hall3.getRoom().get(l).getRoomNumber()),
                                Integer.toString(hall3.getRoom().get(l).getRentRate()),
                                "", "", "Unoccupied", "Clean");
                        
                        modal.getItems().add(tData1);
                    }
                }else{
                    errorMess3.setText("Entry already Exsists!");
                }    
            }else{
                errorMess3.setText("Entry already Exsists!");
            }           
        });

        //update the halls rooms,
        updateButton.setOnAction((ActionEvent e) -> {
            TableData tData1;

            //update the manager, warde and room count
            for (Hall h : modal.getHallItems()) {
                if (h.getHallNumber().equals(hallNum2.getText()) && h.getHallName().equals(hallName2.getText())
                        && h.getHallAddress().equals(hallAddress.getText())) {
                    h.setManager(manager1.getText());
                    h.setRoomCount(roomCount.getText());
                    h.setWarden(warden1.getText());
                    break;
                }
            }

            //add the new rooms to the accommodation list and to TableData modal 
            for (Hall hall2 : acco.getHalls()) {
                int numberOfRooms = Integer.parseInt(hall2.getRoomCount());

                if (hall2.getHallName().equals(hallName2.getText())
                        && hall2.getHallAddress().equals(hallAddress.getText())
                        && hall2.getHallNumber().equals(hallNum2.getText())
                        && hall2.getManager().equals(manager1.getText())
                        && hall2.getWarden().equals(warden1.getText())) {

                    hall2.setManager(manager1.getText());
                    hall2.setWarden(warden1.getText());
                    hall2.setRoomCount(roomCount.getText());

                    //System.out.println(hall2.getWarden());
                    for (int m = 1 + numberOfRooms; m <= Integer.parseInt(roomCount.getText()); m++) {
                        Room r = new Room(m, hall2.getRoom().get(0).getRentRate());
                        hall2.getRoom().add(r);

                        tData1 = new TableData("", hallName2.getText(), hallNum2.getText(),
                                Integer.toString(hall2.getRoom().get(m).getRoomNumber()),
                                Integer.toString(hall2.getRoom().get(m).getRentRate()),
                                "", "", "Unoccupied", "Clean");

                        modal.getItems().add(tData1);
                    }
                }
            }

            table.setItems(FXCollections.observableArrayList(modal.getHallItems()));
            table.refresh();
        });

        HBox hallHBox1 = new HBox(gp);
        HBox hallHBox2 = new HBox(table);

        bp.setCenter(hallHBox2);
        bp.setBottom(hallHBox1);
        tabE.setContent(bp);

        return tabE;
    }
}

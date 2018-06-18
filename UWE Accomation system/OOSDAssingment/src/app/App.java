/** Main launch class of the app */
package app;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {
    
    //file writer
    FileWriter fileWriter = null;    
     
    /**
     * Sets up the stage and scene
     */
    @Override
    public void start(Stage primaryStage) {
        //make acco office
        AccomodationOffice acco = new AccomodationOffice();
        ArrayList<Lease> leaseList = new ArrayList<>();
        ArrayList<Hall> allHalls = new ArrayList<>();
        Modal modal = new Modal();
        
        //retrieve the size of the device screen and make the window  a size that fits,
        //This is due to the lack of resizeablitiy that java fx has
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        double tableHeight = screenSize.getHeight() - 500;
        double tableWidth = screenSize.getWidth() - 550;

        //Login information using double brace iniatlizer hashMap<String String>
        HashMap<String, String> entry = new HashMap<String, String>() {
            {
                put("p-alden", "Admin");
                put("max-power", "Warden");
                put("dragonborne", "Manager");
                put("harambe", "SuperUser");
            }
        };

        //access information using double brace iniatlizer hashMap<String String>
        HashMap<String, String> userInfo = new HashMap<String, String>() {
            {
                put("p-alden", "omg12345");
                put("max-power", "we123456");
                put("dragonborne", "fusrodah");
                put("harambe", "died2016");
            }
        };

        /**
         * Generate the first entry for the programme
         */
        Hall hall = new Hall("BroadQuay House", "BB01", "BS5 1DY",
                "2", "Danial Watkins", "Bob Alden");
        hall.generateRooms(Integer.parseInt("150"));

        allHalls.add(hall);
        acco.setHalls(allHalls);

        Hall hall1 = new Hall(
                hall.getHallName(), hall.getHallNumber(), hall.getHallAddress(),
                hall.getRoomCount(), hall.getManager(), hall.getWarden());

        modal.getHallItems().add(hall1);

        //lease
        Lease lease = new Lease("001", "Paul ALden", "BB01", "0",
                "12", "150", "FET53");

        leaseList.add(lease);
        acco.setLease(leaseList);

        Lease lease1 = new Lease(
                lease.getLeaseNumber(), lease.getStudentName(), lease.getHallNumber(),
                lease.getRoomNumber(), lease.getDuration(),
                lease.getRentRate(), lease.getStudentId());

        modal.getLeaseItems().add(lease1);

        TableData tData;
        //generate a table of rooms with no occupants and clean
        for (Hall hall2 : acco.getHalls()) {
            for (Room room : hall2.getRoom()) {
                tData = new TableData("",
                        hall2.getHallName(), hall2.getHallNumber(),
                        Integer.toString(room.getRoomNumber()),
                        Integer.toString(room.getRentRate()),
                        "", "", "Unoccupied", "Clean");

                modal.getItems().add(tData);
            }
        }
        /**
         * Stage
         */
        primaryStage.setTitle("UWE Bristol Accommodation System");
        TabPane tabPane = new TabPane();
        tabPane.setMinHeight(height - 400); 
        tabPane.setMinWidth(width - 200);   
        tabPane.setTranslateY(20);

        /* menu Bar for log and exit */
        MenuBar menuBar = new MenuBar();

        MenuItem menuItem1 = new MenuItem("Logout");
        MenuItem menuItem2 = new MenuItem("Exit");
        MenuItem menuItem3 = new MenuItem("Save");
        MenuItem menuItem4 = new MenuItem("Refresh");

        Menu menu = new Menu("Menu");
        menu.getItems().add(menuItem4);
        menu.getItems().add(new SeparatorMenuItem());
        menu.getItems().add(menuItem3);
        menu.getItems().add(new SeparatorMenuItem());
        menu.getItems().add(menuItem1);
        menu.getItems().add(new SeparatorMenuItem());
        menu.getItems().add(menuItem2);

        menuBar.getMenus().add(menu);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        /* tabA */
        BorderPane bp1 = new BorderPane();
        GridPane grid1 = new GridPane();

        TableView<TableData> table = new TableView<>();
        table.setPlaceholder(new Label(""));
        GeneralTable generalTable = new GeneralTable(modal);
        Tab tabA = generalTable.tabA(table, bp1, grid1, tableHeight, tableWidth);

        /* tabB */
        BorderPane bp2 = new BorderPane();
        GridPane grid2 = new GridPane();

        TableView<TableData> table2 = new TableView<>();
        table2.setPlaceholder(new Label(""));
        Tab tabB = generalTable.tabB(table2, bp2, grid2, tableHeight, tableWidth);

        /* tabC */
        BorderPane bp3 = new BorderPane();
        GridPane grid3 = new GridPane();

        TableView<TableData> table3 = new TableView<>();
        table3.setPlaceholder(new Label(""));
        Tab tabC = generalTable.tabC(table3, bp3, grid3, tableHeight, tableWidth);

        /*     TabD         */
        BorderPane bp4 = new BorderPane();
        GridPane grid4 = new GridPane();

        TableView<Lease> table4 = new TableView<>();
        table4.setPlaceholder(new Label(""));
        Tab tabD = generalTable.tabD(table4, bp4, grid4, acco, tableHeight, tableWidth);

        /*     TabE         */
        BorderPane bp5 = new BorderPane();
        GridPane grid5 = new GridPane();

        TableView<Hall> table5 = new TableView<>();
        table5.setPlaceholder(new Label(""));
        Tab tabE = generalTable.tabE(table5, bp5, grid5, acco, tableHeight, tableWidth);

        /* Tab 6 */
        BorderPane bp6 = new BorderPane();
        GridPane grid6 = new GridPane();

        Tab tabF = new Tab();

        tabF.setText("Login");
        tabF.setId("login");

        Label loginLabel = new Label("Login");
        Label userLabel = new Label("UserName");
        Label passLabel = new Label("Password");
        Label errorMessage = new Label("");

        userLabel.setMinWidth(100);
        passLabel.setMinWidth(100);
        loginLabel.setMinWidth(60);
        errorMessage.setMinWidth(200);

        TextField userName = new TextField();
        userName.setPromptText("Enter Your User Name");
        PasswordField passWord = new PasswordField();
        passWord.setPromptText("Enter Your Password");

        passWord.setId("password");
        loginLabel.setId("loginLabel");
        userLabel.setId("userLabel");
        passLabel.setId("passLabel");
        userName.setId("userName");
        errorMessage.setId("error");

        Button loginButton = new Button("Login");

        grid6.setHgap(10);
        grid6.setVgap(10);

        grid6.add(loginLabel, 0, 0);
        grid6.add(userLabel, 0, 1);
        grid6.add(passLabel, 0, 2);
        grid6.add(userName, 1, 1);
        grid6.add(passWord, 1, 2);
        grid6.add(loginButton, 3, 2);
        grid6.add(errorMessage, 4,0);

        //login event and validation.
        loginButton.setOnAction((ActionEvent e) -> {
            boolean userValid = false;
            boolean passValid = false;
            
            String username = userName.getText();
            String password = passWord.getText();
            
            boolean p1 = Pattern.matches("[A-Za-z0-9]{8,28}", password);  
            boolean u1 = Pattern.matches("[a-zA-z0-9\\-]+", username); 
                
            if (u1) {
                if (userInfo.containsKey(username)) {
                    userValid = true;
                }else{
                    errorMessage.setText("Error User Name does not Exist!");
                }
            }else{
                errorMessage.setText("Error User Name should only be letters and numbers");
            }
            
            if(p1){
                if (userInfo.containsValue(password)){
                    passValid = true;
                }else{
                    errorMessage.setText("Error Password does not match!");
                }
            }else{
                errorMessage.setText("Error Password must contain only numbers and letters only");
            }

            if(userValid && passValid){
                    String access = entry.get(username);

                switch (access) {
                    case "Manager":
                        tabA.setDisable(false);
                        tabD.setDisable(false);
                        tabPane.getSelectionModel().select(tabA);
                        menuBar.setDisable(false);
                        menuBar.setStyle(" visibility: inherit;");
                        tabF.setDisable(true);
                        tabF.setStyle(" visibility: hidden;");
                        break;
                    case "Warden":
                        tabB.setDisable(false);
                        tabPane.getSelectionModel().select(tabB);
                        menuBar.setDisable(false);
                        menuBar.setStyle(" visibility: inherit;");
                        tabF.setDisable(true);
                        tabF.setStyle(" visibility: hidden;");
                        break;
                    case "Admin":
                        tabD.setDisable(false);
                        tabE.setDisable(false);
                        tabPane.getSelectionModel().select(tabD);
                        menuBar.setDisable(false);
                        menuBar.setStyle(" visibility: inherit;");
                        tabF.setDisable(true);
                        tabF.setStyle(" visibility: hidden;");
                        break;
                    case "SuperUser":
                        tabPane.getSelectionModel().select(tabC);
                        menuBar.setDisable(false);
                        menuBar.setStyle(" visibility: inherit;");
                        tabF.setDisable(true);
                        tabF.setStyle(" visibility: hidden;");
                        tabA.setDisable(false);
                        tabB.setDisable(false);
                        tabC.setDisable(false);
                        tabD.setDisable(false);
                        tabE.setDisable(false);
                        break;
                    default:
                        break;
                }
            }
        });

        //add picture
        final ImageView imv = new ImageView();
        final Image image2
                = new Image(this.getClass().getResourceAsStream("acco-Img1.jpg"));
        imv.setImage(image2);

        final HBox pictureRegion = new HBox();

        pictureRegion.getChildren().add(imv);
        pictureRegion.setMaxWidth(200);
        pictureRegion.setMaxHeight(200);

        HBox loginHBox = new HBox(grid6);
        bp6.setCenter(loginHBox);
        bp6.setTop(pictureRegion);
        bp6.setTranslateX(50);
        bp6.setTranslateY(100);
        tabF.setContent(bp6);

        //add tabs to the group
        tabPane.getTabs().add(tabA);
        tabPane.getTabs().add(tabB);
        tabPane.getTabs().add(tabC);
        tabPane.getTabs().add(tabD);
        tabPane.getTabs().add(tabE);
        tabPane.getTabs().add(tabF);
        
        tabA.setDisable(true);
        tabB.setDisable(true);
        tabC.setDisable(true);
        tabD.setDisable(true);
        tabE.setDisable(true);
        menuBar.setDisable(true);
        menuBar.setStyle(" visibility: hidden;");
        
 /* lambdas for when the menu item is selected*/
        menuItem1.setOnAction((ActionEvent event) -> {
            tabPane.getSelectionModel().select(tabF);
            tabA.setDisable(true);
            tabB.setDisable(true);
            tabC.setDisable(true);
            tabD.setDisable(true);
            tabE.setDisable(true);
            tabF.setDisable(false);
            menuBar.setDisable(true);
            menuBar.setStyle(" visibility: hidden;");
        });
        
        //refresh the tabs and the save error if it occured
        menuItem4.setOnAction((ActionEvent event) -> {
            table.setItems(FXCollections.observableArrayList(modal.getItems()));
            table.refresh();
            table2.setItems(FXCollections.observableArrayList(modal.getItems()));
            table2.refresh();
            table3.setItems(FXCollections.observableArrayList(modal.getItems()));
            table3.refresh();
            table4.setItems(FXCollections.observableArrayList(modal.getLeaseItems()));
            table4.refresh();
            table5.setItems(FXCollections.observableArrayList(modal.getHallItems()));
            table5.refresh();
            menuItem3.setText("Save");
        });
        
        //write to save file and then close file
        menuItem2.setOnAction((ActionEvent event) -> {
            String text = ",", line ="\n";
            int i = 0;

            try{
                fileWriter = new FileWriter("report.csv");

                for(Hall mo: modal.getHallItems()){
                    i++;
                    fileWriter.append("Halls");
                    fileWriter.append(text);
                    fileWriter.append(Integer.toString(i));
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallAddress());
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallName());
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getManager());
                    fileWriter.append(text);
                    fileWriter.append(mo.getRoomCount());
                    fileWriter.append(text);
                    fileWriter.append(mo.getWarden());
                    fileWriter.append(line);
                }

                i = 0;

                for(TableData mo: modal.getItems()){
                    i++;
                    fileWriter.append("Rooms");
                    fileWriter.append(text);
                    fileWriter.append(Integer.toString(i));
                    fileWriter.append(text);
                    fileWriter.append(mo.getRoomNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallName());
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getDuration());
                    fileWriter.append(text);
                    fileWriter.append(mo.getCleaningStatus());
                    fileWriter.append(text);
                    fileWriter.append(mo.getOccupancy());
                    fileWriter.append(text);
                    fileWriter.append(mo.getRentRate());
                    fileWriter.append(text);
                    fileWriter.append(mo.getLeaseNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getStudentName());
                    fileWriter.append(line);
                }

                i = 0;

                for(Lease mo: modal.getLeaseItems()){
                    i++;
                    fileWriter.append("Leases");
                    fileWriter.append(text);
                    fileWriter.append(Integer.toString(i));
                    fileWriter.append(text);
                    fileWriter.append(mo.getRoomNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getDuration());
                    fileWriter.append(text);
                    fileWriter.append(mo.getRentRate());
                    fileWriter.append(text);
                    fileWriter.append(mo.getLeaseNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getStudentName());
                    fileWriter.append(mo.getStudentId());
                    fileWriter.append(line);
                }
            }catch(Exception e){
                menuItem3.setText("Error Failed to Save! Please close the open save file.");
            }finally {
	        try {
	            fileWriter.flush();
	            fileWriter.close();
	        } catch (IOException e) {
                    menuItem3.setText("Error Failed to Save! Please close the open save file.");
                }
	    }
            System.exit(0);
        });
        
        menuItem3.setOnAction((ActionEvent event) ->{
            //write to file save
            String text = ",", line ="\n";
            int i = 0;

            try{
                fileWriter = new FileWriter("report.csv");

                for(Hall mo: modal.getHallItems()){
                    i++;
                    fileWriter.append("Halls");
                    fileWriter.append(text);
                    fileWriter.append(Integer.toString(i));
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallAddress());
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallName());
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getManager());
                    fileWriter.append(text);
                    fileWriter.append(mo.getRoomCount());
                    fileWriter.append(text);
                    fileWriter.append(mo.getWarden());
                    fileWriter.append(line);
                }

                i = 0;

                for(TableData mo: modal.getItems()){
                    i++;
                    fileWriter.append("Rooms");
                    fileWriter.append(text);
                    fileWriter.append(Integer.toString(i));
                    fileWriter.append(text);
                    fileWriter.append(mo.getRoomNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallName());
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getDuration());
                    fileWriter.append(text);
                    fileWriter.append(mo.getCleaningStatus());
                    fileWriter.append(text);
                    fileWriter.append(mo.getOccupancy());
                    fileWriter.append(text);
                    fileWriter.append(mo.getRentRate());
                    fileWriter.append(text);
                    fileWriter.append(mo.getLeaseNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getStudentName());
                    fileWriter.append(line);
                }

                i = 0;

                for(Lease mo: modal.getLeaseItems()){
                    i++;
                    fileWriter.append("Leases");
                    fileWriter.append(text);
                    fileWriter.append(Integer.toString(i));
                    fileWriter.append(text);
                    fileWriter.append(mo.getRoomNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getHallNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getDuration());
                    fileWriter.append(text);
                    fileWriter.append(mo.getRentRate());
                    fileWriter.append(text);
                    fileWriter.append(mo.getLeaseNumber());
                    fileWriter.append(text);
                    fileWriter.append(mo.getStudentName());
                    fileWriter.append(mo.getStudentId());
                    fileWriter.append(line);
                }
            }catch(Exception e){
                menuItem3.setText("Error Failed to Save! Please close the open save file.");
            }finally {
	        try {
	            fileWriter.flush();
	            fileWriter.close();
	        } catch (IOException e) {
                    menuItem3.setText("Error Failed to Save! Please close the open save file.");
                }
	    }
        });

        //selects which tab to start on and allow closable or not
        tabPane.getSelectionModel().select(tabF);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        //refresh the table based on the tab change event listener
        tabPane.getSelectionModel().selectedItemProperty().addListener((
                ObservableValue<? extends Tab> ov, Tab tabA1, Tab tabB1) -> {

            table.setItems(FXCollections.observableArrayList(modal.getItems()));
            table.refresh();
            table2.setItems(FXCollections.observableArrayList(modal.getItems()));
            table2.refresh();
            table3.setItems(FXCollections.observableArrayList(modal.getItems()));
            table3.refresh();
            table4.setItems(FXCollections.observableArrayList(modal.getLeaseItems()));
            table4.refresh();
            table5.setItems(FXCollections.observableArrayList(modal.getHallItems()));
            table5.refresh();
            menuItem3.setText("Save");
        });

        Group root = new Group();
        Scene scene = new Scene(root, width - 500, height - 150);

        root.getChildren().add(tabPane);
        root.getChildren().add(menuBar);
        scene.getStylesheets().add(this.getClass().getResource("mainStyles.css")
                .toExternalForm());

        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

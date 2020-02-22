package application;


import application.tables.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Program {

    @FXML
    public TableView tvListofCustomers;
    public TableView tvListofHotels;
    public TextField txtLastName;
    public TextField txtFirstName;
    public Label lblStatus1;
    public DatePicker dpStartDate;
    public DatePicker dpEndDate;
    public CheckBox cbPool;
    public CheckBox cbChildActivity;
    public CheckBox cbEveningEvent;
    public TextField txtBeach;
    public TextField txtCenter;
    public ComboBox cbLocation;
    public TableView tvListofRooms;


    public void initializeGUI() {
        Database.connect();
        tv_Customers();
        fill_tv_Customers("", "");
        tv_Hotels();
        fill_tv_Hotels();
        tv_Rooms();
        tvListofRooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        fixDatePickers();
        fill_cb_Location();
    }



    private void fixDatePickers(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = "2020-06-01";
        String endDate = "2020-07-31";
        dpStartDate.valueProperty().addListener((ov, oldValue, newValue) -> {
            dpEndDate.setValue(newValue);
            restrictDatePicker(dpEndDate, newValue,LocalDate.parse(endDate));
        });
        restrictDatePicker(dpStartDate, LocalDate.parse(startDate),LocalDate.parse(endDate));
    }

    // region TabelViews
    //
    public void tv_Hotels() {
        tvListofHotels.getItems().clear();
        tvListofHotels.getColumns().clear();
        TableColumn<Hotel, String> column1 = new TableColumn<>("ID");
        column1.setPrefWidth(20f);
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Hotel, String> column2 = new TableColumn<>("Name");
        column2.setPrefWidth(100f);
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Hotel, String> column3 = new TableColumn<>("Address");
        column3.setPrefWidth(120f);
        column3.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Hotel, String> column4 = new TableColumn<>("Zip");
        column4.setPrefWidth(40f);
        column4.setCellValueFactory(new PropertyValueFactory<>("zip"));
        TableColumn<Hotel, String> column5 = new TableColumn<>("City");
        column5.setPrefWidth(80f);
        column5.setCellValueFactory(new PropertyValueFactory<>("city"));
        TableColumn<Hotel, String> column6 = new TableColumn<>("Country");
        column6.setPrefWidth(70f);
        column6.setCellValueFactory(new PropertyValueFactory<>("country"));
        TableColumn<Hotel, String> column7 = new TableColumn<>("Phone Number");
        column7.setPrefWidth(100f);
        column7.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumn<Hotel, String> column8 = new TableColumn<>("Stars");
        column8.setPrefWidth(35f);
        column8.setCellValueFactory(new PropertyValueFactory<>("stars"));
        TableColumn<Hotel, Boolean> column9 = new TableColumn<>("Pool");
        column9.setMaxWidth(50f);
        column9.setPrefWidth(50f);
        column9.setCellValueFactory(new PropertyValueFactory<>("pool"));
        column9.setCellFactory(col -> new TableCell<Hotel, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "YES" : "NO" );
            }
        });
        TableColumn<Hotel, Boolean> column10 = new TableColumn<>("Child Activity");
        column10.setPrefWidth(50f);
        column10.setMaxWidth(50f);
        column10.setCellValueFactory(new PropertyValueFactory<>("childActivity"));
        column10.setCellFactory(col -> new TableCell<Hotel, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "YES" : "NO" );
            }
        });
        TableColumn<Hotel, Boolean> column11 = new TableColumn<>("Evening Events");
        column11.setPrefWidth(50f);
        column11.setMaxWidth(50f);
        column11.setCellValueFactory(new PropertyValueFactory<>("eveningEvents"));
        column11.setCellFactory(col -> new TableCell<Hotel, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "YES" : "NO" );
            }
        });
        TableColumn<Hotel, Integer> column12 = new TableColumn<>("Beach");
        column12.setPrefWidth(50f);
        column12.setCellValueFactory(new PropertyValueFactory<>("distancetoBeach"));
        TableColumn<Hotel, Integer> column13 = new TableColumn<>("Center");
        column13.setPrefWidth(50f);
        column13.setCellValueFactory(new PropertyValueFactory<>("distancetoCenter"));
        tvListofHotels.getColumns().add(column1);
        tvListofHotels.getColumns().add(column2);
        tvListofHotels.getColumns().add(column3);
        tvListofHotels.getColumns().add(column4);
        tvListofHotels.getColumns().add(column5);
        tvListofHotels.getColumns().add(column6);
        tvListofHotels.getColumns().add(column7);
        tvListofHotels.getColumns().add(column8);
        tvListofHotels.getColumns().add(column9);
        tvListofHotels.getColumns().add(column10);
        tvListofHotels.getColumns().add(column11);
        tvListofHotels.getColumns().add(column12);
        tvListofHotels.getColumns().add(column13);
    }

    public void tv_Customers() {
        TableColumn<Customer, String> column1 = new TableColumn<>("ID");
        column1.setPrefWidth(30f);
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Customer, String> column2 = new TableColumn<>("First Name");
        column2.setPrefWidth(84f);
        column2.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        TableColumn<Customer, String> column3 = new TableColumn<>("Last Name");
        column3.setPrefWidth(84f);
        column3.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        tvListofCustomers.getColumns().add(column1);
        tvListofCustomers.getColumns().add(column2);
        tvListofCustomers.getColumns().add(column3);
    }

    public void tv_Rooms() {
        TableColumn<Room, Integer> column1 = new TableColumn<>("ID");
        column1.setPrefWidth(30f);
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Room, Integer> column2 = new TableColumn<>("Hotel ID");
        column2.setPrefWidth(30f);
        column2.setCellValueFactory(new PropertyValueFactory<>("hotel_Id"));
        TableColumn<Room, String> column3 = new TableColumn<>("Room Type");
        column3.setPrefWidth(84f);
        column3.setCellValueFactory(new PropertyValueFactory<>("room_Type"));
        TableColumn<Room, Double> column4 = new TableColumn<>("Prize / Night");
        column4.setPrefWidth(84f);
        column4.setCellValueFactory(new PropertyValueFactory<>("room_Prize"));
        TableColumn<Room, Double> column5 = new TableColumn<>("Extra bed");
        column5.setPrefWidth(84f);
        column5.setCellValueFactory(new PropertyValueFactory<>("extra_Bed"));
        TableColumn<Room, Double> column6 = new TableColumn<>("Breakfast");
        column6.setPrefWidth(84f);
        column6.setCellValueFactory(new PropertyValueFactory<>("breakfast"));
        TableColumn<Room, Double> column7 = new TableColumn<>("Half broad");
        column7.setPrefWidth(84f);
        column7.setCellValueFactory(new PropertyValueFactory<>("half_broad"));
        TableColumn<Room, Double> column8 = new TableColumn<>("Full broad");
        column8.setPrefWidth(84f);
        column8.setCellValueFactory(new PropertyValueFactory<>("full_broad"));
        tvListofRooms.getColumns().add(column1);
        tvListofRooms.getColumns().add(column2);
        tvListofRooms.getColumns().add(column3);
        tvListofRooms.getColumns().add(column4);
        tvListofRooms.getColumns().add(column5);
        tvListofRooms.getColumns().add(column6);
        tvListofRooms.getColumns().add(column7);
        tvListofRooms.getColumns().add(column8);
    }
    // endregion

    // region Fill TableViews
    private void fill_tv_Customers(String firstName, String lastName) {
        tvListofCustomers.getItems().clear();
        ArrayList<Customer> customers = Database.getCustomerList(firstName, lastName);
        if (customers.size() > 0) {
            for (Customer customer : customers) {
                tvListofCustomers.getItems().add(customer);
            }
        }
        tvListofCustomers.getSelectionModel().selectFirst();
    }

    private void fill_tv_Hotels() {

    }

    private void fill_cb_Location(){

        ObservableList<String> locations = Database.getAllHotelLocations();
        cbLocation.setItems(locations);
        cbLocation.getSelectionModel().selectFirst();
    }



    private void setStatus(String strStatus){
        lblStatus1.setText("Status: " + strStatus);
    }

    // endregion

    // region Buttons
    // Buttons & Click
    //
    public void btnAddCustomer(ActionEvent actionEvent) {
        if(checkNameFields()) {
            String work = Database.addNewCustomer(txtFirstName.getText(), txtLastName.getText());
            clearNameFields();
            setStatus(work);
        }
    }

    public void btnSearchCustomer(ActionEvent actionEvent) {
        fill_tv_Customers(txtFirstName.getText(), txtLastName.getText());
    }

    public void btnFindHotels(ActionEvent actionEvent) {
        if(validate(txtBeach.getText()) && validate(txtCenter.getText())) {
            tvListofHotels.getItems().clear();
            ArrayList<Hotel> hotels = Database.getHotelbyRequest(cbLocation.getSelectionModel().getSelectedItem().toString(), cbPool.isSelected(), cbChildActivity.isSelected(), cbEveningEvent.isSelected(), Integer.parseInt(txtBeach.getText()), Integer.parseInt(txtCenter.getText()));
            if (hotels.size() > 0) {
                for (Hotel hotel : hotels) {
                    tvListofHotels.getItems().add(hotel);
                }
            }
        }
    }

    // Was a extra feature on my mind that I had no time to fix. - Display how many rooms hotel had in total in another tabel.
    public void onListofHotels_Click(MouseEvent mouseEvent) {
     /*   tvListofRooms.getItems().clear();
        ArrayList<Room> rooms = Database.getRoomsfromHotel();
        if (rooms.size() > 0) {
            for (Room room : rooms) {
                tvListofRooms.getItems().add(room);
            }
        } */
    }


    public void btnFindRooms(ActionEvent actionEvent) {
        if(tvListofHotels.getSelectionModel().getSelectedItem() != null) {
            Hotel selectedhotel = (Hotel) tvListofHotels.getSelectionModel().getSelectedItem();
            tvListofRooms.getItems().clear();
            ArrayList<Room> rooms = Database.getRoombyRequest(selectedhotel.getId(), dpStartDate.getValue(), dpEndDate.getValue());
            if (rooms.size() > 0) {
                for (Room room : rooms) {
                    tvListofRooms.getItems().add(room);
                }
            }
        }
    }

    // endregion

    private boolean checkNameFields(){
        if(txtFirstName.getText().length() < 1 || txtLastName.getText().length() < 1) {
            setStatus("One of the textfields is missing something");
            return false;
        }
        else {
            return true;
        }
    }

    private void clearNameFields(){
        txtFirstName.setText("");
        txtLastName.setText("");
    }

    private boolean validate(String text)
    {
        return text.matches("[0-9]");
    }

    public void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }else if (item.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
        datePicker.setValue(minDate);
    }


}

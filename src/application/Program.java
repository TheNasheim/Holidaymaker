package application;


import application.tables.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Program {

    @FXML
    public TableView tvListofCustomers;
    public TextField txtLastName;
    public TextField txtFirstName;
    public Label lblStatus;
    public DatePicker dpStartDate;
    public DatePicker dpEndDate;
    public TableView tvListofRooms;
    public CheckBox cbPool;
    public CheckBox cbChildActivity;
    public CheckBox cbEveningEvent;
    public TextField txtBeach;
    public TextField txtCenter;
    public ComboBox cbHotelNames;


    public void initializeGUI() {
        Database.connect();
        tv_Customers();
        fill_tv_Customers("", "");
        fixDatePickers();
    }

    private void fixDatePickers(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = "2020-06-01";
        String endDate = "2020-07-31";
        restrictDatePicker(dpStartDate, LocalDate.parse(startDate),LocalDate.parse(endDate));
        restrictDatePicker(dpEndDate, LocalDate.parse(startDate),LocalDate.parse(endDate));
    }
    // region UI
    // UI Settings and changes
    //
    public void tv_Customers() {
        tvListofCustomers.getItems().clear();
        tvListofCustomers.getColumns().clear();
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

    private void setStatus(String strStatus){
        lblStatus.setText("Status: " + strStatus);
    }
    // endregion

    // region Buttons
    // Buttons
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

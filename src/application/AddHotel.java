package application;

import application.tables.Customer;
import application.tables.Hotel;
import application.tables.Price;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddHotel {


    public TableView tvListofHotels;
    public TextField txtHotelName;
    public TextField txtHotelAddress;
    public TextField txtHotelZip;
    public TextField txtHotelCity;
    public TextField txtHotelCountry;
    public TextField txtHotelPhone;
    public TextField txtHotelSingle;
    public TextField txtHotelDouble;
    public TextField txtHotelQuad;
    public TextField txtHotelQueen;
    public TextField txtHotelKing;
    public CheckBox cbPool;
    public CheckBox cbChildActivity;
    public CheckBox cbEveningEvent;
    public TextField txtBeach;
    public TextField txtCenter;
    public TextField txtSingle_Price;
    public DatePicker dpStartDate;
    public DatePicker dpEndDate;
    public TextField txtKing_Price;
    public TextField txtQueen_Price;
    public TextField txtQuad_Price;
    public TextField txtDouble_Price;
    public TextField txtExtra_Bed_Price;
    public TextField txtBreakfast_Price;
    public TextField txtHalf_Broad_Price;
    public TextField txtFull_Broad_Price;

    public void btnAddHotel(ActionEvent actionEvent) {
        if(checkHotelAddressFields() && validate(txtBeach.getText()) && validate(txtCenter.getText())) {
            Hotel hotel = new Hotel(0, txtHotelName.getText(),
                    txtHotelAddress.getText(),
                    txtHotelZip.getText(),
                    txtHotelCity.getText(),
                    txtHotelCountry.getText(),
                    txtHotelPhone.getText(),
                    0,
                    cbPool.isSelected(),
                    cbChildActivity.isSelected(),
                    cbEveningEvent.isSelected(),
                    Integer.parseInt(txtBeach.getText()),
                    Integer.parseInt(txtCenter.getText()));

            Database.addNewHotel(hotel);
            int id = Database.getHotelId(txtHotelName.getText());
            if(id != 0){
                if(validate(txtHotelSingle.getText()))
                    addRoomtoHotel(id, Integer.parseInt(txtHotelSingle.getText()), "Single");
                if(validate(txtHotelDouble.getText()))
                    addRoomtoHotel(id, Integer.parseInt(txtHotelDouble.getText()), "Double");
                if(validate(txtHotelQuad.getText()))
                    addRoomtoHotel(id, Integer.parseInt(txtHotelQuad.getText()), "Quad");
                if(validate(txtHotelQueen.getText()))
                    addRoomtoHotel(id, Integer.parseInt(txtHotelQueen.getText()), "Queen");
                if(validate(txtHotelKing.getText()))
                    addRoomtoHotel(id, Integer.parseInt(txtHotelKing.getText()), "King");
            }
            resetHotelFields();
        }
    }

    private void addRoomtoHotel(int id, int amount, String room_type){
        for(int i = 0; i < amount; i++){
            Database.addNewRoom(id, room_type);
        }
    }

    private void resetHotelFields() {
        txtHotelName.setText("");
        txtHotelAddress.setText("");
        txtHotelZip.setText("");
        txtHotelCity.setText("");
        txtHotelCountry.setText("");
        txtHotelPhone.setText("");
        cbPool.setSelected(false);
        cbChildActivity.setSelected(false);
        cbEveningEvent.setSelected(false);
        txtHotelSingle.setText("");
        txtHotelDouble.setText("");
        txtHotelQuad.setText("");
        txtHotelQueen.setText("");
        txtHotelKing.setText("");
    }

    private void resetPriceFields() {
        txtSingle_Price.setText("0");
        txtDouble_Price.setText("0");
        txtQuad_Price.setText("0");
        txtQueen_Price.setText("0");
        txtKing_Price.setText("0");
        txtExtra_Bed_Price.setText("0");
        txtBreakfast_Price.setText("0");
        txtHalf_Broad_Price.setText("0");
        txtFull_Broad_Price.setText("0");
    }

    public void btnUpdateHotelTV(ActionEvent actionEvent) {
        tv_Hotels();
        fillHotelTV();
        fixDatePickers();
        dpEndDate.setValue(LocalDate.parse("2020-08-01"));
    }

    private void fixDatePickers(){
        String startDate = "2020-06-01";
        String endDate = "2020-08-01";
        dpStartDate.valueProperty().addListener((ov, oldValue, newValue) -> {
            dpEndDate.setValue(newValue);
            restrictDatePicker(dpEndDate, newValue, LocalDate.parse(endDate));
        });
        restrictDatePicker(dpStartDate, LocalDate.parse(startDate),LocalDate.parse(endDate));

    }

    private void tv_Hotels() {
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
        TableColumn<Hotel, Boolean> column8 = new TableColumn<>("Pool");
        column8.setMaxWidth(50f);
        column8.setPrefWidth(50f);
        column8.setCellValueFactory(new PropertyValueFactory<>("pool"));
        column8.setCellFactory(col -> new TableCell<Hotel, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "YES" : "NO" );
            }
        });
        TableColumn<Hotel, Boolean> column9 = new TableColumn<>("Child Activity");
        column9.setPrefWidth(50f);
        column9.setMaxWidth(50f);
        column9.setCellValueFactory(new PropertyValueFactory<>("childActivity"));
        column9.setCellFactory(col -> new TableCell<Hotel, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "YES" : "NO" );
            }
        });
        TableColumn<Hotel, Boolean> column10 = new TableColumn<>("Evening Events");
        column10.setPrefWidth(70f);
        column10.setMaxWidth(70f);
        column10.setCellValueFactory(new PropertyValueFactory<>("eveningEvents"));
        column10.setCellFactory(col -> new TableCell<Hotel, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "YES" : "NO" );
            }
        });
        TableColumn<Hotel, Integer> column11 = new TableColumn<>("Beach");
        column11.setPrefWidth(50f);
        column11.setCellValueFactory(new PropertyValueFactory<>("distancetoBeach"));
        TableColumn<Hotel, Integer> column12 = new TableColumn<>("Center");
        column12.setPrefWidth(50f);
        column12.setCellValueFactory(new PropertyValueFactory<>("distancetoCenter"));
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
    }


    private void fillHotelTV(){
        ArrayList<Hotel> hotels = Database.getHotelList("");
        if (hotels.size() > 0) {
            for (Hotel hotel : hotels) {
                tvListofHotels.getItems().add(hotel);
            }
        }
        tvListofHotels.getSelectionModel().selectFirst();
    }


    private boolean checkHotelAddressFields(){
        if(txtHotelName.getText().isEmpty() || txtHotelAddress.getText().isEmpty() || txtHotelZip.getText().isEmpty() || txtHotelCity.getText().isEmpty() || txtHotelCountry.getText().isEmpty()){
            return false;
        }
        return true;
    }

    private boolean validate(String text)
    {
        return text.matches("[0-9]*");
    }

    private void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
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

    public void btnAddPricetoHotel(ActionEvent actionEvent) {
        if (checkHotelPriceFields() && tvListofHotels.getSelectionModel().getSelectedItem() != null) {
            int hotel_id = ((Hotel)tvListofHotels.getSelectionModel().getSelectedItem()).getId();
            Price price = new Price(hotel_id,
                    (LocalDate)dpStartDate.getValue(),
                    (LocalDate)dpEndDate.getValue(),
                    Double.parseDouble(txtSingle_Price.getText()),
                    Double.parseDouble(txtDouble_Price.getText()),
                    Double.parseDouble(txtQuad_Price.getText()),
                    Double.parseDouble(txtQueen_Price.getText()),
                    Double.parseDouble(txtKing_Price.getText()),
                    Double.parseDouble(txtExtra_Bed_Price.getText()),
                    Double.parseDouble(txtBreakfast_Price.getText()),
                    Double.parseDouble(txtHalf_Broad_Price.getText()),
                    Double.parseDouble(txtFull_Broad_Price.getText()));
            Database.addPricetoHotel(price);
            resetPriceFields();
        }

    }

    private boolean checkHotelPriceFields(){
        if(validate(txtHotelSingle.getText()) || validate(txtHotelDouble.getText()) || validate(txtHotelQuad.getText()) || validate(txtHotelQueen.getText()) || validate(txtHotelKing.getText()) || validate(txtExtra_Bed_Price.getText()) || validate(txtBreakfast_Price.getText())|| validate(txtHalf_Broad_Price.getText())|| validate(txtFull_Broad_Price.getText())){
            return true;
        }
        return false;
    }


}

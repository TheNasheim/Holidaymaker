package application;

import application.tables.Hotel;
import javafx.event.ActionEvent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class AddRating {
    public TextField txtRating;
    public TableView tvListofRatingHotels;

    public void btnUpdateHotelRatingTV(ActionEvent actionEvent) {
        tv_RatingHotels();
        fillHotelRatingTV();
    }


    private void tv_RatingHotels() {
        tvListofRatingHotels.getItems().clear();
        tvListofRatingHotels.getColumns().clear();
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
        column8.setPrefWidth(50f);
        column8.setCellValueFactory(new PropertyValueFactory<>("stars"));
        tvListofRatingHotels.getColumns().add(column1);
        tvListofRatingHotels.getColumns().add(column2);
        tvListofRatingHotels.getColumns().add(column3);
        tvListofRatingHotels.getColumns().add(column4);
        tvListofRatingHotels.getColumns().add(column5);
        tvListofRatingHotels.getColumns().add(column6);
        tvListofRatingHotels.getColumns().add(column7);
        tvListofRatingHotels.getColumns().add(column8);
    }

    private void fillHotelRatingTV() {
            tvListofRatingHotels.getItems().clear();
            ArrayList<Hotel> hotels = Database.getHotelbyRequest("Show All", false, false, false, 0, 0);
            if (hotels.size() > 0) {
                for (Hotel hotel : hotels) {
                    tvListofRatingHotels.getItems().add(hotel);
                }
                tvListofRatingHotels.getSelectionModel().selectFirst();
            }

        tvListofRatingHotels.getSelectionModel().selectFirst();
    }

    public void btnAddRating(ActionEvent actionEvent) {
        if(validate(txtRating.getText())) {
            int id = ((Hotel) tvListofRatingHotels.getSelectionModel().getSelectedItem()).getId();
            Database.addHotelRating(id, Integer.parseInt(txtRating.getText()));
            txtRating.setText("0");
            fillHotelRatingTV();
        }
    }


    private boolean validate(String text)
    {
        return text.matches("[0-5]");
    }
}

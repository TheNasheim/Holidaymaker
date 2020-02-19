package application;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    public void btnAddHotel(ActionEvent actionEvent) {

    }

    private boolean chekHotelAddressFields(){
        if(txtHotelName.getText().isEmpty() || txtHotelAddress.getText().isEmpty() || txtHotelZip.getText().isEmpty() || txtHotelCity.getText().isEmpty() || txtHotelCountry.getText().isEmpty()){
            return false;
        }
        return true;
    }


    public void btnUpdateHotelTV(ActionEvent actionEvent) {
    }
}

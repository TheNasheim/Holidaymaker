package application;
import application.tables.Customer;
import application.tables.*;

import java.sql.*;
import java.util.ArrayList;


public class Database {

    private static Connection conn = null;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    public static void connect(){
        try{

            conn = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=holidaymaker&password=mysql&serverTimezone=UTC");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }





    //region Customer
    public static String addNewCustomer(String firstName, String lastName){
        try {
            statement = conn.prepareStatement("INSERT INTO customer SET first_name = ?, last_name = ?");
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.executeUpdate();
            return firstName + " " + lastName + " was added successfully.";
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "Something is wrong! Customer was not added.";
        }
    }

    public static ArrayList<Customer> getCustomerList(String firstName, String lastName){
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            if (firstName.isEmpty() && lastName.isEmpty()) {
                statement = conn.prepareStatement("SELECT * FROM customer");
                resultSet = statement.executeQuery();
            }
            else if (lastName.isEmpty()) {
                statement = conn.prepareStatement("SELECT * FROM customer WHERE first_name LIKE ?");
                statement.setString(1, "%" + firstName + "%");
                resultSet = statement.executeQuery();
            }
            else if (firstName.isEmpty()) {
                statement = conn.prepareStatement("SELECT * FROM customer WHERE last_name LIKE ?");
                statement.setString(1, "%" + lastName + "%");
                resultSet = statement.executeQuery();
            }
            else {
                statement = conn.prepareStatement("SELECT * FROM customer WHERE first_name LIKE ? AND last_name LIKE ?");
                statement.setString(1, "%" + firstName + "%");
                statement.setString(2, "%" + lastName + "%");
                resultSet = statement.executeQuery();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String first_Name = resultSet.getString("first_name");
                String last_Name = resultSet.getString("last_name");
                customers.add(new Customer(id, first_Name, last_Name));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return customers;
    }
    // endregion

    // region Hotel
    public static ArrayList<Hotel> getAllHotelList(){
        ArrayList<Hotel> hotels = new ArrayList<>();
        try {
                statement = conn.prepareStatement("SELECT * FROM hotel");
                resultSet = statement.executeQuery();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String zip = resultSet.getString("zip");
                String town = resultSet.getString("city");
                String country = resultSet.getString("country");
                String phone = resultSet.getString("phone");
                Boolean pool = resultSet.getBoolean("pool");
                Boolean child = resultSet.getBoolean("childactivity");
                Boolean evening = resultSet.getBoolean("eveningevents");
                Integer distobeach = resultSet.getInt("distancetobeach");
                Integer distocent = resultSet.getInt("distancetocenter");

                hotels.add(new Hotel(id, name, address, zip, town, country, phone, pool, child, evening, distobeach, distocent));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return hotels;
    }

    public static String addNewHotel(Hotel hotel){
        try {
            statement = conn.prepareStatement("INSERT INTO hotel SET name = ?, address = ?,zip = ?,city = ?,country = ?,phone = ?,pool = ?,childactivity = ?,eveningevents = ?,distancetobeach = ?,distancetocenter = ?");
            statement.setString(1,hotel.getName());
            statement.setString(2,hotel.getAddress());
            statement.setString(3,hotel.getZip());
            statement.setString(4,hotel.getCity());
            statement.setString(5,hotel.getCountry());
            statement.setString(6,hotel.getPhone());
            statement.setString(7,String.valueOf(hotel.getPool()));
            statement.setString(8,String.valueOf(hotel.getChildActivity()));
            statement.setString(9,String.valueOf(hotel.getEveningEvents()));
            statement.setDouble(10,hotel.getDistancetoBeach());
            statement.setDouble(11,hotel.getDistancetoCenter());
            statement.executeUpdate();
            return " was added successfully.";
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "Something is wrong! Customer was not added.";
        }
    }
/*
// this is a expansion for some other time.
    public static String editHotel(){
        try {
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static String deleteHotel(){
        try {
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
*/
    public static int getHotelId(String hotelname) {
        int index = 0;
        try {
            statement = conn.prepareStatement("SELECT id FROM hotel WHERE name = ?");
            statement.setString(1, hotelname);
            resultSet = statement.executeQuery();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                index = resultSet.getInt("id");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return index;
    }
    // endregion

    //  region Rooms
    public static void addNewRoom(int id, String room_type) {
        try {
            statement = conn.prepareStatement("INSERT INTO room SET hotel_id = ?, room_type = ?");
            statement.setInt(1, id);
            statement.setString(2, room_type);
            statement.executeUpdate();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
    // endregion


    public static void getrooms(){

    }
}

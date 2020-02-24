package application;
import application.tables.Customer;
import application.tables.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Database {

    private static Connection conn = null;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    public static void connect() {
        try {
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //region Customer
    public static String addNewCustomer(String firstName, String lastName) {
        try {
            statement = conn.prepareStatement("INSERT INTO customer SET first_name = ?, last_name = ?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.executeUpdate();
            return firstName + " " + lastName + " was added successfully.";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Something is wrong! Customer was not added.";
        }
    }

    public static ArrayList<Customer> getCustomerList(String firstName, String lastName) {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            if (firstName.isEmpty() && lastName.isEmpty()) {
                statement = conn.prepareStatement("SELECT * FROM customer");
                resultSet = statement.executeQuery();
            } else if (lastName.isEmpty()) {
                statement = conn.prepareStatement("SELECT * FROM customer WHERE first_name LIKE ?");
                statement.setString(1, "%" + firstName + "%");
                resultSet = statement.executeQuery();
            } else if (firstName.isEmpty()) {
                statement = conn.prepareStatement("SELECT * FROM customer WHERE last_name LIKE ?");
                statement.setString(1, "%" + lastName + "%");
                resultSet = statement.executeQuery();
            } else {
                statement = conn.prepareStatement("SELECT * FROM customer WHERE first_name LIKE ? AND last_name LIKE ?");
                statement.setString(1, "%" + firstName + "%");
                statement.setString(2, "%" + lastName + "%");
                resultSet = statement.executeQuery();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String first_Name = resultSet.getString("first_name");
                String last_Name = resultSet.getString("last_name");
                customers.add(new Customer(id, first_Name, last_Name));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customers;
    }
    // endregion

    // region Hotel
    public static ArrayList<Hotel> getHotelList(String location) {
        ArrayList<Hotel> hotels = new ArrayList<>();
        try {
            if (location.isBlank()) {
                statement = conn.prepareStatement("SELECT * FROM hotel");
            } else {
                statement = conn.prepareStatement("SELECT * FROM hotel WHERE city = ?");
                statement.setString(1, location);
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("hotel_name");
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

                hotels.add(new Hotel(id, name, address, zip, town, country, phone, 0, pool, child, evening, distobeach, distocent));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hotels;
    }



    public static String addNewHotel(Hotel hotel) {
        try {
            statement = conn.prepareStatement("INSERT INTO hotel SET hotel_name = ?, address = ?,zip = ?,city = ?,country = ?,phone = ?,pool = ?,childactivity = ?,eveningevents = ?,distancetobeach = ?,distancetocenter = ?");
            statement.setString(1, hotel.getName());
            statement.setString(2, hotel.getAddress());
            statement.setString(3, hotel.getZip());
            statement.setString(4, hotel.getCity());
            statement.setString(5, hotel.getCountry());
            statement.setString(6, hotel.getPhone());
            statement.setString(7, String.valueOf(hotel.getPool()));
            statement.setString(8, String.valueOf(hotel.getChildActivity()));
            statement.setString(9, String.valueOf(hotel.getEveningEvents()));
            statement.setDouble(10, hotel.getDistancetoBeach());
            statement.setDouble(11, hotel.getDistancetoCenter());
            statement.executeUpdate();

            int index = getHotelId(hotel.getName());
            statement = conn.prepareStatement("INSERT INTO rating SET hotel_id = ?, stars = ?");
            statement.setInt(1, index);
            statement.setInt(2, 2);
            statement.executeUpdate();
            statement = conn.prepareStatement("INSERT INTO rating SET hotel_id = ?, stars = ?");
            statement.setInt(1, index);
            statement.setInt(2, 3);
            statement.executeUpdate();

            return " was added successfully.";
        } catch (Exception ex) {
            ex.printStackTrace();

            return "Something is wrong! Hotel was not added.";
        }
    }

    public static int getHotelId(String hotelname) {
        int index = 0;
        try {
            statement = conn.prepareStatement("SELECT id FROM hotel WHERE hotel_name = ?");
            statement.setString(1, hotelname);
            resultSet = statement.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                index = resultSet.getInt("id");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return index;
    }

    public static ArrayList<Hotel> getHotelbyRequest(String location, Boolean poolIn, Boolean childIn, Boolean eveningIn, int beachIn, int centerIn) {
        ArrayList<Hotel> hotels = new ArrayList<>();
        try {
            String query = "";
            if(location.contains("Show All")) {
                query = "SELECT * FROM hotels_with_rating WHERE 1=1 ";}
             else {
                query = "SELECT * FROM hotels_with_rating WHERE city = ? ";}

                if (poolIn)
                    query += "AND pool = 'true' ";
                if (childIn)
                    query += "AND childactivity = 'true' ";
                if (eveningIn)
                    query += "AND eveningevents = 'true' ";
                if (beachIn > 0)
                    query += "AND distancetobeach <= ? ";
                if (centerIn > 0)
                    query += "AND distancetocenter <= ? ";

            statement = conn.prepareStatement(query);
            if(!location.contains("Show All")) {
                statement.setString(1, location);
                if (beachIn > 0)
                    statement.setInt(2, beachIn);
                if (centerIn > 0 && beachIn > 0)
                    statement.setInt(3, centerIn);
                else if (centerIn > 0)
                    statement.setInt(2, centerIn);
            }
            else {
                if (beachIn > 0)
                    statement.setInt(1, beachIn);
                if (centerIn > 0 && beachIn > 0)
                    statement.setInt(2, centerIn);
                else if (centerIn > 0)
                    statement.setInt(1, centerIn);
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("hotel_name");
                String address = resultSet.getString("address");
                String zip = resultSet.getString("zip");
                String town = resultSet.getString("city");
                String country = resultSet.getString("country");
                String phone = resultSet.getString("phone");
                double stars = resultSet.getDouble("stars");
                Boolean pool = resultSet.getBoolean("pool");
                Boolean child = resultSet.getBoolean("childactivity");
                Boolean evening = resultSet.getBoolean("eveningevents");
                Integer distobeach = resultSet.getInt("distancetobeach");
                Integer distocent = resultSet.getInt("distancetocenter");

                hotels.add(new Hotel(id, name, address, zip, town, country, phone, stars, pool, child, evening, distobeach, distocent));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hotels;
    }


    public static ObservableList<String> getAllHotelLocations() {
        ObservableList<String> citys = FXCollections.observableArrayList();
        try {
            citys.add("Show All");
            statement = conn.prepareStatement("SELECT city FROM hotel GROUP BY city");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                citys.add(resultSet.getString("city")); //add the String to the list
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return citys;
    }
    // endregion

    //  region Rooms
    public static void addNewRoom(int id, String room_type) {
        try {
            statement = conn.prepareStatement("INSERT INTO room SET hotel_id = ?, room_type = ?");
            statement.setInt(1, id);
            statement.setString(2, room_type);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static ArrayList<Room> getRoombyRequest(int hotelId, LocalDate startDate, LocalDate endDate) {
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            String query = "SELECT room.id, room.hotel_id, room.room_type, reservation.checkin_date, reservation.checkout_date, " +
            "(CASE WHEN room_type = 'Single' THEN single_room " +
            "WHEN room_type = 'Double' THEN double_room " +
            "WHEN room_type = 'Quad' THEN quad_room " +
            "WHEN room_type = 'Queen' THEN queen_room " +
            "WHEN room_type = 'King' THEN king_room " +
            "END) AS room_price, price.extra_bed, price.breakfast, half_board, full_board " +
            "FROM hotel " +
            "JOIN room " +
            "ON hotel.id = room.hotel_id " +
            "LEFT JOIN reservation " +
            "ON room.id = reservation.room_id " +
            "LEFT JOIN price " +
            "ON price.hotel_id = hotel.id " +
            "WHERE room.id NOT IN (SELECT room_id " +
                    "FROM reservation " +
                    "WHERE ? BETWEEN reservation.checkin_date AND reservation.checkout_date " +
                    "OR ? BETWEEN reservation.checkin_date AND reservation.checkout_date	) " +
            "AND hotel.id = ? " +
            "AND ? BETWEEN price.start_date AND price.end_date " +
            "GROUP BY id " +
            "ORDER BY id";
            statement = conn.prepareStatement(query);
            statement.setString(1, startDate.toString());
            statement.setString(2, endDate.toString());
            statement.setInt(3, hotelId);
            statement.setString(4, startDate.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int hotel_id = resultSet.getInt("hotel_id");
                String room_type = resultSet.getString("room_type");
                String checkin_date = resultSet.getString("checkin_date");
                String checkout_date = resultSet.getString("checkout_date");
                double room_price = resultSet.getDouble("room_price");
                double extra_bed = resultSet.getDouble("extra_bed");
                double breakfast = resultSet.getDouble("breakfast");
                double half_board = resultSet.getDouble("half_board");
                double full_board = resultSet.getDouble("full_board");
                rooms.add(new Room(id, room_type, hotel_id, room_price,extra_bed, breakfast, half_board, full_board));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rooms;
    }


    public static void addReservation(int customerId, int guests, ArrayList<Reservation> reservations){
        String query1 = "INSERT INTO booking SET customer_id = ?, guests = ?";
        String query2 = "SELECT id FROM booking ORDER BY id DESC LIMIT 1";
        String query3 = "INSERT INTO reservation SET booking_id = ?, room_id = ?, checkin_date = ?, checkout_date = ?, extra_bed = ?, board = ?, price = ?";
        int bookingId = 0;
        try {
            statement = conn.prepareStatement(query1);
            statement.setInt(1, customerId);
            statement.setInt(2, guests);
            statement.executeUpdate();

            statement = conn.prepareStatement(query2);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
               bookingId = resultSet.getInt("id");
            }
            for(Reservation reservation : reservations) {
                statement = conn.prepareStatement(query3);
                statement.setInt(1, bookingId);
                statement.setInt(2, reservation.getRoom_Id());
                statement.setString(3, reservation.getCheckin_date().toString());
                statement.setString(4, reservation.getCheckout_date().toString());
                statement.setString(5, reservation.getExtra_bed());
                statement.setString(6, reservation.getBoard());
                statement.setDouble(7, reservation.getPrice());
                statement.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static  ArrayList<Reservation> getReservation(int customerId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            if(customerId == 0) {
                statement = conn.prepareStatement("SELECT * FROM reservation ORDER BY booking_id");
            }
            else{
                statement = conn.prepareStatement("SELECT reservation.id AS id, reservation.room_id, reservation.booking_id, reservation.checkin_date, reservation.checkout_date, reservation.extra_bed, reservation.board, reservation.price " +
                        "FROM reservation " +
                        "JOIN booking " +
                        "ON booking.id = reservation.booking_id " +
                        "LEFT JOIN customer " +
                        "ON customer.id = booking.customer_id " +
                        "WHERE customer.id = ? " +
                        "ORDER BY reservation.booking_id");
                statement.setInt(1, customerId);
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int booking_id = resultSet.getInt("booking_id");
                int hotel_id = resultSet.getInt("room_id");
                String checkin_date = resultSet.getString("checkin_date");
                String checkout_date = resultSet.getString("checkout_date");
                String extra_bed = resultSet.getString("extra_bed");
                String board = resultSet.getString("board");
                double room_price = resultSet.getDouble("price");
                reservations.add(new Reservation(id, booking_id, hotel_id, LocalDate.parse(checkin_date, formatter), LocalDate.parse(checkout_date, formatter), extra_bed, board, room_price));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return reservations;
    }



    public static void test() {
        try {
            String query = "SELECT hotel.id, hotel.hotel_name, hotel.city, " +
                    "COUNT(IF(room.room_type = 'Single',1 ,NULL)) AS single_room, " +
                    "COUNT(IF(room.room_type = 'Double',1, NULL)) AS double_room, " +
                    "COUNT(IF(room.room_type = 'Quad',1, NULL)) AS quad_room, " +
                    "COUNT(IF(room.room_type = 'Queen',1, NULL)) AS queen_room, " +
                    "COUNT(IF(room.room_type = 'King',1, NULL)) AS king_room " +
                    "FROM hotel, room " +
                    "WHERE hotel.id = room.hotel_id " +
                    "AND hotel.city = ?" +
                    "ORDER BY hotel.id";
            statement = conn.prepareStatement(query);
            statement.setString(1, "Las Palmas");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.print(resultSet.getString("id") + " ");
                System.out.print(resultSet.getString("hotel_name") + " ");
                System.out.print(resultSet.getString("city") + " ");
                System.out.print(resultSet.getString("single_room") + " ");
                System.out.print(resultSet.getString("double_room") + " ");
                System.out.print(resultSet.getString("quad_room") + " ");
                System.out.print(resultSet.getString("queen_room") + " ");
                System.out.println(resultSet.getString("king_room") + " ");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void addHotelRating(int id, int stars) {
        try {
            statement = conn.prepareStatement("INSERT INTO rating SET hotel_id = ?, stars = ?");
            statement.setInt(1, id);
            statement.setInt(2, stars);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void addPricetoHotel(Price price) {
        try {
            statement = conn.prepareStatement("INSERT INTO price SET hotel_id = ?, start_date = ?, end_date = ?, single_room = ?, double_room = ?, quad_room = ?, queen_room = ?, king_room = ?, extra_bed = ?, breakfast = ?, half_board = ?, full_board = ?");
            statement.setInt(1, price.getHotel_id());
            statement.setString(2, price.getStart_date().toString());
            statement.setString(3, price.getEnd_date().toString());
            statement.setDouble(4, price.getSingle_room());
            statement.setDouble(5, price.getDouble_room());
            statement.setDouble(6, price.getQuad_room());
            statement.setDouble(7, price.getQueen_room());
            statement.setDouble(8, price.getKing_room());
            statement.setDouble(9, price.getExtra_bed());
            statement.setDouble(10, price.getBreakfast());
            statement.setDouble(11, price.getHalf_board());
            statement.setDouble(12, price.getFull_board());
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


/*
    public static void getrooms(){
        try {
            String query = "SELECT rooms.id, city, hotel_name, rating, distance_centre, distance_beach, room_type, price_per_night, reservation.checkin_date, reservation.checkout_date " +
                    "FROM hotel " +
                    "JOIN rooms " +
                    "ON hotel.id = rooms.hotel_id " +
                    "LEFT JOIN reservation " +
                    "ON rooms.id = reservation.room_id " +
                    "WHERE city = 'Las Palmas' " +
                    "AND '2020-06-02' NOT BETWEEN reservation.checkin_date AND reservation.checkout_date " +
                    "AND '2020-06-04' NOT BETWEEN reservation.checkin_date AND reservation.checkout_date " +
                    "AND '2020-06-02' BETWEEN '2020-05-31' AND '2020-07-30' " +
                    "AND '2020-06-04' BETWEEN '2020-06-02' AND '2020-07-31' ";


                statement = conn.prepareStatement("SELECT * FROM customer");
                resultSet = statement.executeQuery();

        }
        catch (Exception ex){
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
        }*/

    // endregion


    /*
CREATE VIEW hotel_rooms AS
SELECT room.id, room_type, hotel_name, city,
IFNULL(reservation.checkin_date, '2020-05-31') AS checkin_date,
IFNULL(reservation.checkout_date, '2020-05-31') AS checkout_date
FROM room
JOIN hotel
ON room.hotel_id = hotel.id
LEFT JOIN reservation
ON room.id = reservation.room_id



SELECT room.id, room_type, hotel_name, city, pool, childactivity, eveningevents, distancetobeach, distancetocenter,
		(CASE WHEN room_type = 'Single' THEN single_room
             WHEN room_type = 'Double' THEN double_room
             WHEN room_type = 'Quad' THEN quad_room
             WHEN room_type = 'King' THEN queen_room
       		END) AS room_price,
IFNULL(reservation.checkin_date, '2020-05-31') AS checkin_date,
IFNULL(reservation.checkout_date, '2020-05-31') AS checkout_date
FROM room
JOIN hotel
ON room.hotel_id = hotel.id
LEFT JOIN reservation
ON room.id = reservation.room_id
LEFT JOIN price
ON price.hotel_id = hotel.id



SELECT id, room_type, hotel_name, city FROM hotel_rooms WHERE id NOT IN (
SELECT id
FROM hotel_rooms
WHERE '2020-06-06' BETWEEN checkin_date AND checkout_date)
GROUP BY id


     */



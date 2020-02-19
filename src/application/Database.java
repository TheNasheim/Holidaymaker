package application;
import application.tables.Customer;

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
                int rowid = Integer.parseInt(resultSet.getString("id"));
                        String first_Name = resultSet.getString("first_name");
                        String last_Name = resultSet.getString("last_name");
                customers.add(new Customer(rowid, first_Name, last_Name));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return customers;
    }

}

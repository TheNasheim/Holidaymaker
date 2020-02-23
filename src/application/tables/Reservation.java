package application.tables;

import java.time.LocalDate;

public class Reservation {


    private int id;
    private int booking_Id;
    private int room_Id;
    private LocalDate checkin_date;
    private LocalDate checkout_date;
    private String extra_bed;
    private String board;
    private double price;


    public Reservation(int id, int booking_Id, int room_Id, LocalDate checkin_date, LocalDate checkout_date, String extra_bed, String board, double price) {
        this.id = id;
        this.booking_Id = booking_Id;
        this.room_Id = room_Id;
        this.checkin_date = checkin_date;
        this.checkout_date = checkout_date;
        this.extra_bed = extra_bed;
        this.board = board;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getBooking_Id() {
        return booking_Id;
    }

    public int getRoom_Id() {
        return room_Id;
    }

    public LocalDate getCheckin_date() {
        return checkin_date;
    }

    public LocalDate getCheckout_date() {
        return checkout_date;
    }

    public String getExtra_bed() {
        return extra_bed;
    }

    public String getBoard() {
        return board;
    }

    public double getPrice() {
        return price;
    }
}

package application.tables;

public class Room {

    private int id;
    private String room_Type;
    private int hotel_Id;
    private double room_Price;
    private double extra_Bed;
    private double breakfast;
    private double half_board;
    private double full_board;

    public Room(int id, String room_Type, int hotel_Id, double room_Price, double extra_Bed, double breakfast, double half_board, double full_board) {
        this.id = id;
        this.room_Type = room_Type;
        this.hotel_Id = hotel_Id;
        this.room_Price = room_Price;
        this.extra_Bed = extra_Bed;
        this.breakfast = breakfast;
        this.half_board = half_board;
        this.full_board = full_board;
    }

    public int getId() {
        return id;
    }

    public String getRoom_Type() {
        return room_Type;
    }

    public int getHotel_Id() {
        return hotel_Id;
    }

    public double getRoom_Price() {
        return room_Price;
    }

    public double getExtra_Bed() {
        return extra_Bed;
    }

    public double getBreakfast() {
        return breakfast;
    }

    public double getHalf_board() {
        return half_board;
    }

    public double getFull_board() {
        return full_board;
    }

}

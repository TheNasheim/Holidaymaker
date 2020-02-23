package application.tables;

import java.time.LocalDate;

public class Price {
    private int hotel_id;
    private LocalDate start_date;
    private LocalDate end_date;
    private double single_room;
    private double double_room;
    private double quad_room;
    private double queen_room;
    private double king_room;
    private double extra_bed;
    private double breakfast;
    private double half_broad;
    private double full_broad;

    public Price(int hotel_id, LocalDate start_date, LocalDate end_date, double single_room, double double_room, double quad_room, double queen_room, double king_room, double extra_bed, double breakfast, double half_broad, double full_broad) {
        this.hotel_id = hotel_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.single_room = single_room;
        this.double_room = double_room;
        this.quad_room = quad_room;
        this.queen_room = queen_room;
        this.king_room = king_room;
        this.extra_bed = extra_bed;
        this.breakfast = breakfast;
        this.half_broad = half_broad;
        this.full_broad = full_broad;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public double getSingle_room() {
        return single_room;
    }

    public double getDouble_room() {
        return double_room;
    }

    public double getQuad_room() {
        return quad_room;
    }

    public double getQueen_room() {
        return queen_room;
    }

    public double getKing_room() {
        return king_room;
    }

    public double getExtra_bed() {
        return extra_bed;
    }

    public double getBreakfast() {
        return breakfast;
    }

    public double getHalf_broad() {
        return half_broad;
    }

    public double getFull_broad() {
        return full_broad;
    }
}

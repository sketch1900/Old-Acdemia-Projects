package app;

public class Room {

    private int roomNumber;
    private int rentRate;

    /**
     * @param roomNumber
     * @param rentRate
     */
    public Room(int roomNumber, int rentRate) {
        this.roomNumber = roomNumber;
        this.rentRate = rentRate;
    }

    /**
     * @return get RoomNumber Integer
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber set roomNumber Integer
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return get Integer rentRate
     */
    public int getRentRate() {
        return rentRate;
    }

    /**
     * @param rentRate set Integer rentRate
     */
    public void setRentRate(int rentRate) {
        this.rentRate = rentRate;
    }

    /**
     * @return string of the class information
     */
    @Override
    public String toString() {
        return "\nRoom Number: " + roomNumber
                + "\nMonthly Rent Rate: " + rentRate;
    }
}

package app;

import java.util.ArrayList;

public class Hall {

    private ArrayList<Room> Room;
    private String manager;
    private String warden;
    private String hallAddress;
    private String hallName;
    private String hallNumber;
    private String roomCount;

    /**
     * Constructor
     *
     * @param hallName String
     * @param hallNumber String
     * @param roomCount String
     * @param hallAddress String
     * @param manager String
     * @param warden String
     */
    public Hall(String hallName, String hallNumber, String hallAddress,
            String roomCount, String manager, String warden) {
        this.manager = manager;
        this.warden = warden;
        this.hallAddress = hallAddress;
        this.hallName = hallName;
        this.hallNumber = hallNumber;
        this.roomCount = roomCount;
    }

    /**
     * @return ArrayList of room Room type
     */
    public ArrayList<Room> getRoom() {
        return Room;
    }

    /**
     * @param Room set ArrayList of Room room
     */
    public void setRoom(ArrayList<Room> Room) {
        this.Room = Room;
    }

    /**
     * @return warden name string
     */
    public String getWarden() {
        return warden;
    }

    /**
     * @param warden set the warden name string
     */
    public void setWarden(String warden) {
        this.warden = warden;
    }

    /**
     * @return hall address string
     */
    public String getHallAddress() {
        return hallAddress;
    }

    /**
     * @param hallAddress set the Hall address string
     */
    public void setHallAddress(String hallAddress) {
        this.hallAddress = hallAddress;
    }

    /**
     * @return Hall Name String
     */
    public String getHallName() {
        return hallName;
    }

    /**
     * @param hallName set Hall Name
     */
    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    /**
     * @return return Hall Number String
     */
    public String getHallNumber() {
        return hallNumber;
    }

    /**
     * @param hallNumber set hall Number 
     */
    public void setHallNumber(String hallNumber) {
        this.hallNumber = hallNumber;
    }

    /**
     * @return Room Count String
     */
    public String getRoomCount() {
        return roomCount;
    }

    /**
     * @param roomCount set Room Count 
     */
    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
    }

    /**
     * @return manager String
     */
    public String getManager() {
        return manager;
    }

    /**
     * @param manager set Manager name
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * @param monthlyRate integer
     */
    public void generateRooms(int monthlyRate) {
        Room = new ArrayList<>();
        //generate rooms
        for (int i = 0; i <= Integer.parseInt(roomCount); i++) {
            Room r = new Room(i, monthlyRate);
            Room.add(r);
        }
    }
}

/*
 * Manager/all/warden TableView Data Class
 */
package app;

public class TableData {
    
    private String leaseNumber;
    private String hallName;
    private String hallNumber;
    private String roomNumber;
    private String rentRate;
    private String duration;
    private String studentName;
    private String occupancy;
    private String cleaningStatus;
    
    /**
     * @param leaseNumber
     * @param hallName
     * @param hallNumber
     * @param roomNumber
     * @param duration
     * @param rentRate
     * @param studentName
     * @param occupancy
     * @param cleaningStatus*/
    public TableData(String leaseNumber, String hallName, String hallNumber,
            String roomNumber, String rentRate, String duration,
            String studentName, String occupancy, String cleaningStatus){ 
        this.leaseNumber = leaseNumber;     
        this.hallName =  hallName;
        this.hallNumber = hallNumber;
        this.studentName =  studentName;
        this.roomNumber = roomNumber;
        this.cleaningStatus = cleaningStatus;
        this.occupancy = occupancy;
        this.duration = duration;
        this.rentRate = rentRate;
    }
    
    /**
     * @return get String leaseNumber
     */
    public String getLeaseNumber(){
        return leaseNumber;
    }
    
    /**
     * @param leaseNumber set String leaseNumber
     */
    public void setLeaseNumber(String leaseNumber){
        this.leaseNumber = leaseNumber;
    }

    /**
     * @return get String studentName
     */
    public String getStudentName() {
        return studentName;
    }
    
    /**
     * @param studentName set String StudentName
     */
    public void setStudentName(String studentName){
        this.studentName = studentName;
    }
    
    /**
     * @return String occupancy
     */
    public String getOccupancy() {
        return occupancy;
    }
   
    /**
     * @param occupancy set string occupancy, Occupied or Unoccupied
     */
    public void setOccupancy(String occupancy){
        this.occupancy = occupancy;
    }

    /**
     * @return String cleaning Status
     */
    public String getCleaningStatus() {
        return cleaningStatus;
    }
    
    /**
     * @param cleaningStatus set cleaningStatus String 
     */
    public void setCleaningStatus(String cleaningStatus){
        this.cleaningStatus = cleaningStatus;
    }

    /**
     * @return String of the hallNumber
     */
    public String getHallNumber() {
        return hallNumber;
    }
    
    /**
     * @param hallNumber set String of the Halls Number
     */
    public void setHallNumber(String hallNumber){
        this.hallNumber = hallNumber;
    }

    /**
     * @return String of the roomNumber
     */
    public String getRoomNumber() {
        return roomNumber;
    }
   
    /**
     * @param roomNumber set RoomNumber String
     */
    public void setRoomNumber(String roomNumber){
        this.roomNumber = roomNumber;
    }

    /**
     * @return String of the Hall Name
     */
    public String getHallName() {
        return hallName;
    }
    
    /**
     * @param hallName set String hallName
     */
    public void setHallName(String hallName){
        this.hallName = hallName;
    }
    
    /**
     * @return String RentRate
     */
    public String getRentRate() {
        return rentRate;
    }

    /**
     * @param rentRate set String RentRate
     */
    public void setRentRate(String rentRate) {
        this.rentRate = rentRate;
    }

    /**
     * @return String Duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration set String duration in Months
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }
  
    /**
     * @return as String
     */
    @Override
    public String toString(){
        return "Lease Number: " + leaseNumber + "\nStudent Name: " + studentName +
        "\nLease Number: " + leaseNumber + "\nRoom Number: " + roomNumber + 
        "\nHall Name: " + hallName + "\nHall Number: " + hallNumber +        
        "\nOccupancy: " + occupancy + "\nCleaning status: " + cleaningStatus;
    }
}

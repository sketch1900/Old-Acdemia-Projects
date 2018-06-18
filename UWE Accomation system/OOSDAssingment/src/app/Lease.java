package app;

public class Lease {

    private String studentName;
    private String studentId;
    private String leaseNumber;
    private String hallNumber;
    private String rentRate;
    private String duration;
    private String roomNumber;

    /**
     * @param leaseNumber
     * @param studentName
     * @param hallNumber
     * @param roomNumber
     * @param duration
     * @param rentRate
     * @param studentId
     */
    public Lease(String leaseNumber, String studentName,
            String hallNumber, String roomNumber,
            String duration, String rentRate, String studentId) {
        this.studentName = studentName;
        this.leaseNumber = leaseNumber;
        this.hallNumber = hallNumber;
        this.roomNumber = roomNumber;
        this.duration = duration;
        this.studentId = studentId;
        this.rentRate = rentRate;
    }

    /**
     * @return get studentName String
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * @param studentName set studentName String
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * @return get studentId String
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * @param studentId set StudentId string
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * @return get hallNumber String
     */
    public String getHallNumber() {
        return hallNumber;
    }

    /**
     * @param hallNumber set hallNumber String
     */
    public void setHallNumber(String hallNumber) {
        this.hallNumber = hallNumber;
    }

    /**
     * @return leaseNumber String
     */
    public String getLeaseNumber() {
        return leaseNumber;
    }

    /**
     * @param leaseNumber set leaseNumber String
     */
    public void setLeaseNumber(String leaseNumber) {
        this.leaseNumber = leaseNumber;
    }

    /**
     * @return get String duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration set String duration in months
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return get String roomNumber
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber set String roomNumber
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return get String rentRate
     */
    public String getRentRate() {
        return rentRate;
    }

    /**
     * @param rentRate set String rentRate
     */
    public void setRentRate(String rentRate) {
        this.rentRate = rentRate;
    }
}

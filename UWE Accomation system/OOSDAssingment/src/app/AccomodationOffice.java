package app;

import java.util.ArrayList;

public class AccomodationOffice {

    private ArrayList<Lease> Lease;
    private ArrayList<Hall> Halls;

    /**
     * Default Constructor
     */
    public AccomodationOffice() {
    }

    /**
     * @return get ArrayList Lease
     */
    public ArrayList<Lease> getLease() {
        return Lease;
    }

    /**
     * @param Lease set ArrayList
     */
    public void setLease(ArrayList<Lease> Lease) {
        this.Lease = Lease;
    }

    /**
     * @return get ArrayList Halls
     */
    public ArrayList<Hall> getHalls() {
        return Halls;
    }

    /**
     * @param Halls set ArrayList
     */
    public void setHalls(ArrayList<Hall> Halls) {
        this.Halls = Halls;
    }
}

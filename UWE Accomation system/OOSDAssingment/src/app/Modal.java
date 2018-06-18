/** Go between class for the observable list */
package app;

import java.util.ArrayList;
import java.util.List;

public class Modal {

    private List<TableData> items;
    private List<Hall> hallItems;
    private List<Lease> leaseItems;

    /**
     * Constructor
     */
    public Modal() {
        this.items = new ArrayList<>();
        this.hallItems = new ArrayList<>();
        this.leaseItems = new ArrayList<>();
    }

    /**
     * @return TableData Type List
     */
    public List<TableData> getItems() {
        return items;
    }

    /**
     * @return Hall Type List
     */
    public List<Hall> getHallItems() {
        return hallItems;
    }

    /**
     * @return Lease Type List
     */
    public List<Lease> getLeaseItems() {
        return leaseItems;
    }

}

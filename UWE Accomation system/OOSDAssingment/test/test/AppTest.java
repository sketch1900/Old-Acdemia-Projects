/*
 * AppTest Class used to test several functions within the application.
 */
package test;

import app.Hall;
import app.Lease;
import app.Modal;
import app.TableData;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    
    Modal modal;
    
    /**
     * Before prep setting up the initial class Modal class.
     */
    @Before
    public void setUp() {
        System.out.println("@Before");
        
        modal = new Modal();
         
        Hall hall = new Hall("BroadQuay House", "BB01", "BS5 1DY",
                "2", "Danial Watkins", "Bob Alden");
        hall.generateRooms(Integer.parseInt("150"));

        modal.getHallItems().add(hall);
        
        Lease lease = new Lease("001", "Paul ALden", "BB01", "0",
                "12", "150", "FET53");
        
        modal.getLeaseItems().add(lease);
        
        TableData tData = new TableData("",
                hall.getHallName(), hall.getHallNumber(),
                Integer.toString(0),lease.getRentRate(),
                "", "", "Unoccupied", "Clean");

        modal.getItems().add(tData);
    }
    
    /**
     * AppTest of test_method_1 method to check it works.
     */
    @Test
    public void test_method_1() {
        System.out.println("test_method_1 Checking test with a nuetral test 4 equals 4");
        assertEquals(4, 4);
    }
    
    /**
     *  AppTest of test_Modal method, Testing the hall modal list
     */
    @Test
    public void test_Modal(){
        System.out.println("test_Modal  AssertEquals modal.getHallItems to '2' ");
        assertEquals(modal.getHallItems().get(0).getRoomCount(), "2");
    }
    
    /**
     * AppTest of test_Modal2 method, Testing the Table data modal (getItems)
     */
    @Test
    public void test_Modal2(){
        System.out.println("test_Modal2 AssertEquals modal.getItems to 'BB01'");
        assertEquals(modal.getItems().get(0).getHallNumber(), "BB01");
    }
    
    /**
     * AppTest of test_Modal3 method, Testing the lease modal list
     */
    @Test
    public void test_Modal3(){
        System.out.println("test_Modal3 AssertEquals modal.getLeaseItems to '150'");
        assertEquals(modal.getLeaseItems().get(0).getRentRate(), "150");
    }
}

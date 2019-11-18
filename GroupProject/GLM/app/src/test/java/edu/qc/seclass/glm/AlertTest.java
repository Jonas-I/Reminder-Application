package edu.qc.seclass.glm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class AlertTest {

    private Alert alert;
    private Alert alertRepeat;
    private Date date;
    private String repeat;

    @Before
    public void setUp() throws Exception {
        date = new Date();
        repeat = "Never";
        alert = new Alert(date);
        alertRepeat = new Alert(date, repeat);
    }

    @After
    public void tearDown() throws Exception {
        date = null;
        alert = null;
        repeat = null;
        alertRepeat = null;
    }

    @Test
    public void getAlertTime() {
        assertEquals(date, alert.getAlertTime());
    }

    @Test
    public void getAlertTime2() {
        assertEquals(date, alertRepeat.getAlertTime());
    }

    @Test
    public void setAlertTime() {
        Date newDate = new Date();
        alert.setAlertTime(newDate);
        assertEquals(newDate, alert.getAlertTime());
    }

    @Test
    public void setAlertTime2() {
        Date newDate = new Date();
        alertRepeat.setAlertTime(newDate);
        assertEquals(newDate, alertRepeat.getAlertTime());
    }

    @Test
    public void getRepeat() {
        assertEquals(repeat, alert.getRepeat());
    }

    @Test
    public void getRepeat2() {
        assertEquals(repeat, alertRepeat.getRepeat());
    }

    @Test
    public void setRepeat() {
        repeat = "Weekly";
        alert.setRepeat(repeat);
        assertEquals(repeat, alert.getRepeat());
    }

    @Test
    public void setRepeat2() {
        repeat = "Weekly";
        alertRepeat.setRepeat(repeat);
        assertEquals(repeat, alertRepeat.getRepeat());
    }
}
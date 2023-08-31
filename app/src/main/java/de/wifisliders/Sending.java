package de.wifisliders;

import java.sql.Time;
import java.util.Date;

public class Sending {
    public final byte toSlider;
    public final byte value;
    public final Date time;

    public Sending(byte toSlider, byte value) {
        this.toSlider = toSlider;
        this.value = value;
        time = new Date();
    }

    @Override
    public String toString() {
        return toSlider + " " + value + " " + time;
    }
}

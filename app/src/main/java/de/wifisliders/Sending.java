package de.wifisliders;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.util.Date;

public class Sending {
    public final byte toSlider;
    public final byte value;
    public final Date time;
    public int tries = 0;

    public Sending(byte toSlider, byte value) {
        this.toSlider = toSlider;
        this.value = value;
        time = new Date();
    }

    @NonNull
    @Override
    public String toString() {
        return toSlider + ":" + value;
    }
}

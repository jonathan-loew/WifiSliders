package de.wifisliders;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final int MAXVAL = 100;

    SeekBar[] seekbars;
    TextView[] textViews;

    UDPBackend udpBackend = new UDPBackend("192.168.240.221", 365);

    public static MainActivity Instance;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Instance = this;

        setContentView(R.layout.activity_main);

        seekbars = new SeekBar[11];
        seekbars[0] = findViewById(R.id.slider);
        seekbars[1] = findViewById(R.id.slider2);
        seekbars[2] = findViewById(R.id.slider3);
        seekbars[3] = findViewById(R.id.slider4);
        seekbars[4] = findViewById(R.id.slider5);
        seekbars[5] = findViewById(R.id.slider6);
        seekbars[6] = findViewById(R.id.slider7);
        seekbars[7] = findViewById(R.id.slider8);
        seekbars[8] = findViewById(R.id.slider9);
        seekbars[9] = findViewById(R.id.slider10);
        seekbars[10] = findViewById(R.id.slider11);

        textViews = new TextView[11];
        textViews[0] = findViewById(R.id.sliderValue);
        textViews[1] = findViewById(R.id.sliderValue2);
        textViews[2] = findViewById(R.id.sliderValue3);
        textViews[3] = findViewById(R.id.sliderValue4);
        textViews[4] = findViewById(R.id.sliderValue5);
        textViews[5] = findViewById(R.id.sliderValue6);
        textViews[6] = findViewById(R.id.sliderValue7);
        textViews[7] = findViewById(R.id.sliderValue8);
        textViews[8] = findViewById(R.id.sliderValue9);
        textViews[9] = findViewById(R.id.sliderValue10);
        textViews[10] = findViewById(R.id.sliderValue11);

        for (int i = 0; i < seekbars.length; i++) {
            seekbars[i].setMax(MAXVAL);
            seekbars[i].setMin(0);
            seekbars[i].setProgress((int)(MAXVAL * 0.5f));
            udpBackend.sendPacket(i, seekbars[i].getProgress());
        }

        findViewById(R.id.resendAllButton).setOnClickListener((View a) -> ResendAll());

        for(int j = 0; j < seekbars.length && j < textViews.length; j++) {
            ChangeSliderValueCallback(j);
            final int k = j;
            seekbars[j].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    ChangeSliderValueCallback(k);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    SendOne(k);
                }
            });
        }
    }

    void ChangeSliderValueCallback(int sliderId) {
        int val = seekbars[sliderId].getProgress();
        //float progress = val / (float) MAXVAL;
        //progress = ((int)(progress * 100)) / 100f;
        textViews[sliderId].setText(String.format("%s", val));
    }

    public static void MakeStaticToast(String text) {
        Instance.MakeToast(text);
    }

    public void MakeToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void SendOne(int k) {
        udpBackend.sendPacket(k, seekbars[k].getProgress());
    }

    public void ResendAll() {
        for(int i = 0; i < seekbars.length; i++)
            SendOne(i);
    }
}
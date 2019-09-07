package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ImageButton start,fast_forward,fast_rewind;
    SeekBar seekBar;
    TextView song_currenttime,song_duration;
    int startTime = 0;
    int finalTime = 0;
    int prog=0;
    int flag=0;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        fast_forward = findViewById(R.id.stop);
        fast_rewind = findViewById(R.id.pause);
        seekBar = findViewById(R.id.seek_bar);

        song_currenttime = findViewById(R.id.current_time);
        song_duration = findViewById(R.id.duration);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.musci);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mediaPlayer.setVolume(100,200);
               if(flag==0)
               {
                   start.setImageResource(R.drawable.pause);
                   flag=1;
                   mediaPlayer.start();
                   startTime = mediaPlayer.getCurrentPosition();
                   finalTime = mediaPlayer.getDuration();


                   song_duration.setText(String.format("%d min, %d sec",
                           TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                           TimeUnit.MILLISECONDS.toSeconds((long) finalTime)
                                   -
                                   TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                           finalTime))));

                   seekBar.setProgress((int)startTime);
                   seekBar.setMax((int) finalTime);
                   seekBar.setProgress((int)startTime);
                   handler.postDelayed(runnable,100);
                   Toast.makeText(getApplicationContext(),"Music Start",Toast.LENGTH_SHORT).show();

               }
               else
               {
                   start.setImageResource(R.drawable.play);
                   flag=0;
                   Toast.makeText(getApplicationContext(),"Music Pause",Toast.LENGTH_SHORT).show();
                   mediaPlayer.pause();

               }

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                prog=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


                mediaPlayer.seekTo(prog);
                Toast.makeText(getApplicationContext(),String.valueOf(TimeUnit.MICROSECONDS.toSeconds(prog)),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(prog);

            }
        });

        fast_rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    mediaPlayer.seekTo(startTime + 5000);

            }
        });

        fast_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startTime>=5000)
                    mediaPlayer.seekTo(startTime - 5000);
            }
        });

    }



    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            startTime = mediaPlayer.getCurrentPosition();
            song_currenttime.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBar.setProgress(startTime);

            handler.postDelayed(this,100);



        }
    };

    public void onBackPressed()
    {
        mediaPlayer.release();
    }
}

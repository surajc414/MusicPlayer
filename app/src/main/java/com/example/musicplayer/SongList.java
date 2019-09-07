package com.example.musicplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SongList extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        listView = findViewById(R.id.song_list);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("The permission is needed for this and that")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ActivityCompat.requestPermissions(SongList.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        }).create().show();

            } else {
                ActivityCompat.requestPermissions(SongList.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);


            }
        }else{

                String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
                String TrackInfo[] = {

                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DATE_ADDED
                };

                Cursor cursor = this.managedQuery(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        TrackInfo,
                        selection,
                        null,
                        null


                );
                int c = 0;
                arrayList = new ArrayList<String>();
                String s;

                while (cursor.moveToNext()) {
                    c++;

//                s = "" + cursor.getString(0) + "||"
//                          + cursor.getString(1) + "||"
                    arrayList.add(cursor.getString(2));

//                          + cursor.getString(3) + "||"
//                          + cursor.getString(4) + "||"
//                          + cursor.getString(5) + "||"
//                          + cursor.getString(6);




                }
        Log.e("Song List",arrayList.toString());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                }
            });

            Toast.makeText(getApplicationContext(),"Total Song "+c,Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListInterface {

    //First line gives list of pattern names which can be found in res/values/strings.xml
    ArrayList<Pattern_List_Item> Pattern_Model = new ArrayList<>();

    //This gives an array of images for each name, each list needs an accompanying pattern
    //so if you add a name in res/values/strings/.xml you have to add an image here
    //Images are added in res/drawable

    //To add new image right click res/drawable click new click vector asset
    int[] Pattern_Images = {R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_outline_arrow_forward_ios_24,
            R.drawable.ic_baseline_color_lens_24,
            R.drawable.ic_baseline_timer_24,
            R.drawable.ic_baseline_music_note_24,
            R.drawable.ic_baseline_bluetooth_24};

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setup_Pattern_Models();

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, Pattern_Model, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    private void setup_Pattern_Models(){
        String[] Pattern_List_Names = getResources().getStringArray(R.array.PatternList);

        for(int i=0; i<Pattern_List_Names.length; i++){
            Pattern_Model.add(new Pattern_List_Item(Pattern_List_Names[i], Pattern_Images[i]));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onItemClick(int position) {
        changeActivity(position);
    }

    private void changeActivity(int position){
        MyBluetoothService write(5);
        Intent intent = new Intent(this, Activity2.class);

        if(position == 15){
            intent = new Intent(this, Activity3.class);
        }
        else if(position == 16){
            intent = new Intent(this, Activity4.class);
        }
        else if(position == 17){
            intent = new Intent(this, Activity5.class);
        }
        else if(position == 18){
            intent = new Intent(this, BlueToothActivity.class);
        }

        startActivity(intent);
    }
}
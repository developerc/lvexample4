package com.taxiandroid.ru.lvexample4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by saperov on 25.10.15.
 */
public class TwoActivity extends AppCompatActivity {
    ArrayList<Two> arrayTwo;
    ListView lvMain;
    CustomTwoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        populateTwoList();
        lvMain.setOnItemClickListener(itemClickListener);
    }

    private void populateTwoList() {
        arrayTwo = Two.getTwoItem();
        adapter = new CustomTwoAdapter(this,arrayTwo);
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(adapter);
    }

    protected AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
            Two itemLV = (Two) parent.getItemAtPosition(position);
           // String itemName = itemLV.hometown;
            if (position == 1) {
                Toast.makeText(getApplicationContext(), "Отправляем СМС", Toast.LENGTH_SHORT).show();
            }
            if (position == 2) {
                Toast.makeText(getApplicationContext(), "Вызываем звонилку", Toast.LENGTH_SHORT).show();
            }
            if (position == 3) {
                startActivity(new Intent(getApplicationContext(),ThreeActivity.class));
                Toast.makeText(getApplicationContext(), "Вызываем окно таксометра", Toast.LENGTH_SHORT).show();
            }

            //
            //startActivity(new Intent(getApplicationContext(),TwoActivity.class));

        }


        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }


        public void onNothingSelected(AdapterView<?> parent) {

        }


    };
}

package com.taxiandroid.ru.lvexample4;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    ArrayList<User> arrayOfUsers;
    ListView listView;
    CustomUsersAdapter adapter;
    private static final String TAG = "myLogs";
   // final String textSource = "http://developer.alexanderklimov.ru/android/apk/realcat.txt";
   final String textSource = "http://pchelka.teleknot.ru/api/user1/x11unkde/orders";
    boolean ZakazEmpty = true;
    final Handler myHandler = new Handler();
    Runnable runnable;


    ArrayList<Integer> zakaz = new ArrayList<Integer>();
    ArrayList<String> telefon = new ArrayList<String>();
    ArrayList<String> kode = new ArrayList<String>();
    ArrayList<String> dat = new ArrayList<String>();
    ArrayList<String> tim = new ArrayList<String>();
    public static ArrayList<String> adres = new ArrayList<String>();
    ArrayList<String> car = new ArrayList<String>();
    ArrayList<String> predvar = new ArrayList<String>();


    private static final String RESULT = "result";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

       // adres.set(0,"Нет заказов");
       // new GetAsincTask().execute(textSource);
        populateUsersList();

        listView.setOnItemClickListener(itemClickListener);

        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdateGUI();
            }
        }, 0, 15000);
    }

    private void UpdateGUI() {

        myHandler.post(myRunnable);
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            Log.d(TAG, "Сработала Runnable!" );
           // updateUsersList();
            new GetAsincTask().execute(textSource);
            if (ZakazEmpty == false) {
                updateUsersList();
            }
        }
    };

    public class GetAsincTask extends AsyncTask<String, Void, Void> {

        String textResult;

        @Override
        protected Void doInBackground(String... params) {

            try {
                Log.d(TAG, "*******************    Open Connection    *****************************");
                URL url = new URL(params[0]);
                Log.d(TAG, "Received URL:  " + url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(TAG, "The response is: " + response);
                InputStream in = conn.getInputStream();
                Log.d(TAG, "GetInputStream:  " + in);

                Log.d(TAG, "*******************    String Builder     *****************************");
                String line = null;

                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String StringBuffer;
                String stringText = "";
                while ((StringBuffer = bufferReader.readLine()) != null) {
                    stringText += StringBuffer;
                }
                bufferReader.close();

                textResult = stringText;

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                textResult = e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                textResult = e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            //получили JSON строку с сервера
            Log.d(TAG, textResult);
            //обрабатываем JSON строку
            try {
                ZakazJson(textResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(result);
        }
    }  //Закончился GetAsincTask


    //обработка JSON строки
    public void ZakazJson(String jsonString) throws JSONException {
        Log.d(TAG, "*******************    обрабатываем JSON строку     *****************************");
        if (jsonString.contains("ERROR: zakazi not found")) {
            ZakazEmpty = true;
            Log.d(TAG, "Заказов нет");
        }
        else {
            Log.d(TAG, "Заказы есть");
            ZakazEmpty = false;
            jsonString = "{\"myjsonarray\"="+jsonString+"}";
            Log.d(TAG, jsonString);
            JSONObject jo =  new JSONObject(jsonString);
            JSONArray jsonMainArr = jo.getJSONArray("myjsonarray");

            //Очищаем ArrayList
            zakaz.clear();
            telefon.clear();
            kode.clear();
            dat.clear();
            tim.clear();
            adres.clear();
            car.clear();
            predvar.clear();

            for(int i=0; i<jsonMainArr.length(); i++) {
                JSONObject json_data = jsonMainArr.getJSONObject(i);
                zakaz.add(json_data.getInt("zakaz"));
                telefon.add(json_data.getString("telefon"));
                kode.add(json_data.getString("kode"));
                dat.add(json_data.getString("dat"));
                tim.add(json_data.getString("tim"));
                adres.add(json_data.getString("adres"));
                car.add(json_data.getString("car"));
                predvar.add(json_data.getString("predvar"));
                // Log.d(TAG, "Заказ=" + zakaz.get(i) + "  Адрес:" + adres.get(i) + "  Предварительный:" + predvar.get(i));

            }
            for(int i=0; i<zakaz.size(); i++) {
                Log.d(TAG, "Заказ=" + zakaz.get(i) + "  Адрес:" + adres.get(i) + "  Предварительный:" + predvar.get(i));
            }

        }


    }

    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
            User itemLV = (User) parent.getItemAtPosition(position);
            String itemName = itemLV.hometown;

            Toast.makeText(getApplicationContext(), "Вы выбрали " + itemName + " \n Для отказа нажмите на заказ", Toast.LENGTH_SHORT).show();
        //
       // startActivity(new Intent(getApplicationContext(),TwoActivity.class));

            Log.d(TAG, "Нажатие на List View");
            //new FileReadTask().execute();
          //  new GetTask().execute(textSource);
        }

        class FileReadTask extends AsyncTask<Void, Void, Void> {

            String textResult;

            @Override
            protected Void doInBackground(Void... params) {

                URL textUrl;

                try {
                    textUrl = new URL(textSource);

                    BufferedReader bufferReader = new BufferedReader(
                            new InputStreamReader(textUrl.openStream()));

                    String StringBuffer;
                    String stringText = "";
                    while ((StringBuffer = bufferReader.readLine()) != null) {
                        stringText += StringBuffer;
                    }
                    bufferReader.close();

                    textResult = stringText;
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    textResult = e.toString();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    textResult = e.toString();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                /*tvText.setText(textResult);
                tvPrompt.setText("Готово!");*/
                Log.d(TAG, textResult);

                super.onPostExecute(result);
            }
        }

        //Делаем GET запрос получаем ответ

        class GetTask extends AsyncTask<String, Void, Void> {
            String textResult;

            @Override
            protected Void doInBackground(String... params) {

                try {
                    Log.d(TAG, "*******************    Open Connection    *****************************");
                    URL url = new URL(params[0]);
                    Log.d(TAG, "Received URL:  " + url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream in = conn.getInputStream();
                    Log.d(TAG, "GetInputStream:  " + in);

                    Log.d(TAG, "*******************    String Builder     *****************************");
                    String line = null;

                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String StringBuffer;
                    String stringText = "";
                    while ((StringBuffer = bufferReader.readLine()) != null) {
                        stringText += StringBuffer;
                    }
                    bufferReader.close();

                    textResult = stringText;

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    textResult = e.toString();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    textResult = e.toString();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                //получили JSON строку с сервера
                Log.d(TAG, textResult);
                //обрабатываем JSON строку
                try {
                    ZakazJson(textResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPostExecute(result);
            }
        } //закончился GetTask

        //обработка JSON строки
        public void ZakazJson(String jsonString) throws JSONException {
            Log.d(TAG, "*******************    обрабатываем JSON строку     *****************************");
            if (jsonString.contains("ERROR: zakazi not found")) {
                ZakazEmpty = true;
                Log.d(TAG, "Заказов нет");
            }
            else {
                Log.d(TAG, "Заказы есть");
                ZakazEmpty = false;
                jsonString = "{\"myjsonarray\"="+jsonString+"}";
                Log.d(TAG, jsonString);
                JSONObject jo =  new JSONObject(jsonString);
                JSONArray jsonMainArr = jo.getJSONArray("myjsonarray");

                for(int i=0; i<jsonMainArr.length(); i++) {
                    JSONObject json_data = jsonMainArr.getJSONObject(i);
                    zakaz.add(json_data.getInt("zakaz"));
                    telefon.add(json_data.getString("telefon"));
                    kode.add(json_data.getString("kode"));
                    dat.add(json_data.getString("dat"));
                    tim.add(json_data.getString("tim"));
                    adres.add(json_data.getString("adres"));
                    car.add(json_data.getString("car"));
                    predvar.add(json_data.getString("predvar"));
                   // Log.d(TAG, "Заказ=" + zakaz.get(i) + "  Адрес:" + adres.get(i) + "  Предварительный:" + predvar.get(i));

                }
                for(int i=0; i<zakaz.size(); i++) {
                    Log.d(TAG, "Заказ=" + zakaz.get(i) + "  Адрес:" + adres.get(i) + "  Предварительный:" + predvar.get(i));
                }

            }


        }

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }


        public void onNothingSelected(AdapterView<?> parent) {

        }


    };

    private void populateUsersList() {
        // Construct the data source
        // ArrayList<User> arrayOfUsers = User.getUsers();
        arrayOfUsers = User.getUsers();
        // Create the adapter to convert the array to views
        adapter = new CustomUsersAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.lvUsers);
        listView.setAdapter(adapter);
    }

    private void updateUsersList() {
        // Construct the data source
        // ArrayList<User> arrayOfUsers = User.getUsers();
        arrayOfUsers = User.UpdateUsers();
        // Create the adapter to convert the array to views
        adapter = new CustomUsersAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.lvUsers);
        listView.setAdapter(adapter);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
               // mTitle = getString(R.string.title_section3);
                Toast.makeText(this, "Нажато Выбор4.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           // Toast.makeText(getApplicationContext(), "Здесь нажали на меню", Toast.LENGTH_SHORT).show();
           


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}

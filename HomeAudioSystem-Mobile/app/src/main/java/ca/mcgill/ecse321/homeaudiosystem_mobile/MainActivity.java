package ca.mcgill.ecse321.homeaudiosystem_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.Date;

    import ca.mcgill.ecse321.HomeAudioSystem.controller.HomeAudioSystemController;
    import ca.mcgill.ecse321.HomeAudioSystem.controller.InvalidInputException;
    import ca.mcgill.ecse321.HomeAudioSystem.persistence.PersistenceHomeAudioSystem;

    public class MainActivity extends AppCompatActivity {

        private String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Context context = this;
        //set the xml file name and location
        PersistenceHomeAudioSystem.setFileName(context.getFilesDir().getAbsolutePath() +
                "homeaudiosystem.xml");
        // load model
        PersistenceHomeAudioSystem.loadHomeAudioSystemModel();

        refreshData();
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

    private void refreshData(){
        TextView tv = (TextView) findViewById(R.id.newalbumtitle_name);
        tv.setText("");
        tv = (TextView) findViewById(R.id.newalbumgenre_name);
        tv.setText("");

        // HAS
    }

    //Method to add events
    public void addAlbum(View v) {
        TextView tvName = (TextView) findViewById(R.id.newalbumtitle_name);
        TextView tvName2 = (TextView) findViewById(R.id.newalbumgenre_name);

        TextView tvDate = (TextView) findViewById(R.id.newalbum_date);
        String strDate = tvDate.getText().toString();
        // format of strDate is dd-mm-yyyy, need to convert it to yyyy-mm-dd to create Date Object
        String strDateFormatted = strDate.substring(6) + "-" + strDate.substring(3, 5) + "-" +
                strDate.substring(0,2);
        System.out.println(strDateFormatted);
        Date date = java.sql.Date.valueOf(strDateFormatted);


        HomeAudioSystemController pc = new HomeAudioSystemController();
        error = null;
        //calling create event method
        try {
            System.out.println(tvName.getText().toString() +"   " + tvName2.getText().toString());
            pc.add_Album(tvName.getText().toString(), tvName2.getText().toString(), date);
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }

        refreshData();
    }


    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager() , "datePicker");
    }

    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if(comps.length ==3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month - 1);
        rtn.putInt("year", year);

        return rtn;
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m+1, y));
    }

}

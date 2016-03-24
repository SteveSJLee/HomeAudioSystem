package ca.mcgill.ecse321.homeaudiosystem_mobile;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.HomeAudioSystem.controller.Genre;
import ca.mcgill.ecse321.HomeAudioSystem.controller.HomeAudioSystemController;
    import ca.mcgill.ecse321.HomeAudioSystem.controller.InvalidInputException;
import ca.mcgill.ecse321.HomeAudioSystem.model.Album;
import ca.mcgill.ecse321.HomeAudioSystem.model.Artist;
import ca.mcgill.ecse321.HomeAudioSystem.model.HAS;
import ca.mcgill.ecse321.HomeAudioSystem.model.Playlist;
import ca.mcgill.ecse321.HomeAudioSystem.model.Song;
import ca.mcgill.ecse321.HomeAudioSystem.persistence.PersistenceHomeAudioSystem;

    public class MainActivity extends AppCompatActivity {

        private HashMap<Integer, Album> albums;
        private HashMap<Integer, Artist> artists;
        private HashMap<Integer, Song> songs;
        private HashMap<Integer, Playlist> playlists;
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
        // Erase all the text fields
        TextView albumTitle = (TextView) findViewById(R.id.newalbumtitle_name);
        albumTitle.setText("");
        TextView artistName = (TextView) findViewById(R.id.newartistname_name);
        artistName.setText("");
        TextView songTitle = (TextView) findViewById(R.id.newsongtitle_name);
        songTitle.setText("");
        TextView songDuration = (TextView) findViewById(R.id.newsongduration_name);
        songDuration.setText("");
        TextView playlistName = (TextView) findViewById(R.id.newplaylistname_name);
        playlistName.setText("");

        HAS has = HAS.getInstance();

        // Initialize the data in the album spinner
        Spinner albumSpinner = (Spinner) findViewById(R.id.albumspinner);
        ArrayAdapter<CharSequence> albumAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        albumAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        this.albums = new HashMap<Integer, Album>();

        int i = 0;
        for(Iterator<Album> albums = has.getAlbums().iterator();
            albums.hasNext(); i++){
            Album p = albums.next();
            if(p.getSongs().isEmpty())
                albumAdapter.add(p.getTitle() + " (Empty!)");
            else
                albumAdapter.add(p.getTitle());
            this.albums.put(i, p);
        }
        albumSpinner.setAdapter(albumAdapter);


        // Initialize the data in the artist spinner
        Spinner artistSpinner = (Spinner) findViewById(R.id.artistspinner);
        ArrayAdapter<CharSequence> artistAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        artistAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        this.artists = new HashMap<Integer, Artist>();

        i = 0;
        for(Iterator<Artist> artists = has.getArtists().iterator();
            artists.hasNext(); i++){
            Artist p = artists.next();
            if(p.getSongs().isEmpty())
                artistAdapter.add(p.getName() + " (Empty!)");
            else
                artistAdapter.add(p.getName());
            this.artists.put(i, p);
        }
        artistSpinner.setAdapter(artistAdapter);

        // Initialize the data in the song spinner
        Spinner songSpinner = (Spinner) findViewById(R.id.songspinner);
        ArrayAdapter<CharSequence> songAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        songAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        this.songs = new HashMap<Integer, Song>();

        i = 0;
        for(Iterator<Song> songs = has.getSongs().iterator();
            songs.hasNext(); i++){
            Song p = songs.next();
            songAdapter.add(p.getTitle());
            this.songs.put(i, p);
        }
        songSpinner.setAdapter(songAdapter);

        // Initialize the data in the playlist spinner
        Spinner playlistSpinner = (Spinner) findViewById(R.id.playlistspinner);
        ArrayAdapter<CharSequence> playlistAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        playlistAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        this.playlists = new HashMap<Integer, Playlist>();

        i = 0;
        for(Iterator<Playlist> playlists = has.getPlaylists().iterator();
            playlists.hasNext(); i++){
            Playlist p = playlists.next();
            if(p.getSongs().isEmpty())
                playlistAdapter.add(p.getName() + " (Empty!)");
            else
                playlistAdapter.add(p.getName());
            this.playlists.put(i, p);
        }
        playlistSpinner.setAdapter(playlistAdapter);

        // Initialize the data in the genre album spinner
        Spinner genreSpinner = (Spinner) findViewById(R.id.genrespinner);
        genreSpinner.setAdapter(new ArrayAdapter<Genre>(this, android.R.layout.simple_list_item_1, Genre.values()));

        // show errors if any
        TextView errorDisplay = (TextView)findViewById(R.id.errortodisplay);
        errorDisplay.setTextColor(Color.RED);
        errorDisplay.setText(error);
        // HAS
    }

    //Method to add albums
    public void addAlbum(View v) {
        TextView tvAlbumTitle = (TextView) findViewById(R.id.newalbumtitle_name);

        //extract value of genrespinner
        Spinner genreSpinner=(Spinner) findViewById(R.id.genrespinner);

        //Extract date and convert to appropriate Date format
        TextView tvDate = (TextView) findViewById(R.id.newalbum_date);
        String strDate = tvDate.getText().toString();
        // format of strDate is dd-mm-yyyy, need to convert it to yyyy-mm-dd to create Date Object
        String strDateFormatted = strDate.substring(6) + "-" + strDate.substring(3, 5) + "-" +
                strDate.substring(0,2);
        Date date = java.sql.Date.valueOf(strDateFormatted);

        HomeAudioSystemController pc = new HomeAudioSystemController();
        error = null;
        //calling create event method
        try {
            pc.addAlbum(tvAlbumTitle.getText().toString(), genreSpinner.getSelectedItem().toString(), date);
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }

        refreshData();
        }


    //Method to add artists
    public void addArtist(View v) {
        TextView tvArtistName = (TextView) findViewById(R.id.newartistname_name);

        HomeAudioSystemController pc = new HomeAudioSystemController();
        error = null;
        //calling create artist method
        try {
            pc.addArtist(tvArtistName.getText().toString());
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }

        refreshData();
    }

    //Method to add songs
    public void addSong(View v) {
        TextView tvSongTitle = (TextView) findViewById(R.id.newsongtitle_name);
        TextView tvSongDuration = (TextView) findViewById(R.id.newsongduration_name);

        //extract value of participantspinner
        Spinner albumSpinner=(Spinner) findViewById(R.id.albumspinner);
        int albumSelected = albumSpinner.getSelectedItemPosition();

        //extract value of eventspinner
        Spinner artistSpinner = (Spinner) findViewById(R.id.artistspinner);
        int artistSelected = artistSpinner.getSelectedItemPosition();

        HomeAudioSystemController pc = new HomeAudioSystemController();
        error = null;
        //calling create song method
        try {

            pc.addSong(tvSongTitle.getText().toString(), tvSongDuration.getText().toString(),
                    albums.get(albumSelected), artists.get(artistSelected));
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }

        refreshData();
    }

    //Method to add playlists
    public void addPlaylist(View v) {
        TextView tvPlaylistName = (TextView) findViewById(R.id.newplaylistname_name);

        HomeAudioSystemController pc = new HomeAudioSystemController();
        error = null;
        //calling create playlist method
        try {
            pc.addPlaylist(tvPlaylistName.getText().toString());
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

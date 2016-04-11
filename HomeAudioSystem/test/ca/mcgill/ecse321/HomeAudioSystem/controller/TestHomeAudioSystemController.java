package ca.mcgill.ecse321.HomeAudioSystem.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.HomeAudioSystem.persistence.PersistenceXStream;
import ca.mcgill.ecse321.HomeAudioSystem.model.Album;
import ca.mcgill.ecse321.HomeAudioSystem.model.Artist;
import ca.mcgill.ecse321.HomeAudioSystem.model.HAS;
import ca.mcgill.ecse321.HomeAudioSystem.model.Location;
import ca.mcgill.ecse321.HomeAudioSystem.model.Playlist;
import ca.mcgill.ecse321.HomeAudioSystem.model.Song;

public class TestHomeAudioSystemController {

	Album album;
	Artist artist;
	Song song;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.setFilename("test"+File.separator+"ca"+File.separator+"mcgill"+File.separator+"ecse321"+File.separator+"HomeAudioSystem"+
				File.separator+"controller"+File.separator+"data.xml");
		PersistenceXStream.setAlias("location", Location.class);
		PersistenceXStream.setAlias("album", Album.class);
		PersistenceXStream.setAlias("song", Song.class);
		PersistenceXStream.setAlias("artist", Artist.class);
		PersistenceXStream.setAlias("playlist", Playlist.class);
	}

	@After
	public void tearDown() throws Exception {
		HAS has = HAS.getInstance();
		has.delete();
	}

	@Test
	public void testAddArtists() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getArtists().size());

		String name = "J.S.Bach";

		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addArtist(name);
		} catch (InvalidInputException e) {
			// check that no error occurred
			fail();
		}

		checkResultArtist(name, has);

		HAS has2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();

		// check file contents
		checkResultArtist(name, has2);
	}

	@Test
	public void testAddArtistNull() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getArtists().size());

		String name = null;

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addArtist(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Artist name cannot be empty!", error);

		// check no change in memory
		assertEquals(0, has.getArtists().size());
	}

	@Test
	public void testAddArtistEmpty() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getArtists().size());

		String name = "";

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addArtist(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Artist name cannot be empty!", error);

		//check no change in memory
		assertEquals(0, has.getArtists().size());
	}
	
	@Test
	public void testAddArtistSpaces() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getArtists().size());

		String name = " ";

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addArtist(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Artist name cannot be empty!", error);

		//check no change in memory
		assertEquals(0, has.getArtists().size());
	}

	@Test
	public void testAddAlbum() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getAlbums().size());

		String title = "Goldberg Variations BWV 988";
		String genre = "Classic";
		Calendar c = Calendar.getInstance();
		c.set(2015, Calendar.FEBRUARY,17);
		Date date = new Date(c.getTimeInMillis());

		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addAlbum(title, genre, date);
		} catch (InvalidInputException e) {
			// check that no error occurred
			fail();
		}

		// check model in memory
		checkResultAlbum(title, genre, date, has);

		HAS has2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();

		// check file contents
		checkResultAlbum(title, genre, date, has2);
	}

	@Test
	public void testAddAlbumNull() {
		HAS rm = HAS.getInstance();
		assertEquals(0, rm.getAlbums().size());

		String title = null;
		String genre = null;
		Date date = null;

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addAlbum(title, genre, date);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Album title cannot be empty! Album genre cannot be empty! Album release date cannot be empty!", error);

		// check model in memory
		assertEquals(0, rm.getAlbums().size());
	}

	@Test
	public void testAddAlbumEmpty() {
		HAS rm = HAS.getInstance();
		assertEquals(0, rm.getAlbums().size());

		String title = "";
		String genre = "";
		Calendar c = Calendar.getInstance();
		c.set(2015, Calendar.FEBRUARY,17);
		Date date = new Date(c.getTimeInMillis());

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addAlbum(title, genre, date);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Album title cannot be empty! Album genre cannot be empty!", error);

		// check model in memory
		assertEquals(0, rm.getAlbums().size());

	}

	@Test
	public void testAddAlbumSpaces() {
		HAS rm = HAS.getInstance();
		assertEquals(0, rm.getAlbums().size());

		String title = " ";
		String genre = " ";
		Calendar c = Calendar.getInstance();
		c.set(2015, Calendar.FEBRUARY,17);
		Date date = new Date(c.getTimeInMillis());

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addAlbum(title, genre, date);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Album title cannot be empty! Album genre cannot be empty!", error);

		// check model in memory
		assertEquals(0, rm.getAlbums().size());
	}

	@Test
	public void testAddPlaylist(){
		HAS has = HAS.getInstance();
		assertEquals(0, has.getPlaylists().size());

		String name = "If you read this it's too late";

		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addPlaylist(name);
		} catch (InvalidInputException e) {
			// check that no error occurred
			fail();
		}

		checkResultPlaylist(name, has);

		HAS has2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();

		// check file contents
		checkResultPlaylist(name, has2);
	}
	
	@Test
	public void testAddPlaylistNull() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getPlaylists().size());

		String name = null;

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addPlaylist(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Playlist name cannot be empty!", error);

		// check no change in memory
		assertEquals(0, has.getPlaylists().size());
	}
	
	@Test
	public void testAddPlaylistEmpty() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getPlaylists().size());

		String name = "";

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addPlaylist(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Playlist name cannot be empty!", error);

		//check no change in memory
		assertEquals(0, has.getArtists().size());
	}
	
	@Test
	public void testAddPlaylistSpaces() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getArtists().size());

		String name = " ";

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addPlaylist(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Playlist name cannot be empty!", error);

		//check no change in memory
		assertEquals(0, has.getArtists().size());
	}
	
	@Test
	public void testAddLocation() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getLocations().size());

		String name = "Kitchen";
		int volume = 20;

		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addLocation(name, volume);;
		} catch (InvalidInputException e) {
			// check that no error occurred
			fail();
		}

		checkResultLocation(name, volume, has);

		HAS has2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();

		// check file contents
		checkResultLocation(name, volume, has2);
	}
	
	@Test
	public void testAddLocationNull() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getLocations().size());

		String name = null;
		int volume = 20;

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addLocation(name, volume);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Location name cannot be empty!", error);

		//check no change in memory
		assertEquals(0, has.getLocations().size());
	}
	
	@Test
	public void testAddLocationEmpty() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getLocations().size());

		String name = "";
		int volume = 20;

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addLocation(name, volume);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Location name cannot be empty!", error);

		//check no change in memory
		assertEquals(0, has.getLocations().size());
	}
	
	@Test
	public void testAddLocationSpaces() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getLocations().size());

		String name = " ";
		int volume = 20;

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addLocation(name, volume);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Location name cannot be empty!", error);

		//check no change in memory
		assertEquals(0, has.getLocations().size());
	}
	
	@Test
	public void testAddLocationMinVolume() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getLocations().size());

		String name = "Kitchen";
		int volume = -1;

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addLocation(name, volume);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		//assertEquals("Location name cannot be empty!", error);
		assertEquals(null, error);

		//check no change in memory
		//assertEquals(0, has.getLocations().size());
		assertEquals(1, has.getLocations().size());
	}
	
	@Test
	public void testAddLocationMaxVolume() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getLocations().size());

		String name = "Kitchen";
		int volume = 31;

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addLocation(name, volume);
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		//assertEquals("Location name cannot be empty!", error);
		assertEquals(null, error);

		//check no change in memory
		//assertEquals(0, has.getLocations().size());
		assertEquals(1, has.getLocations().size());
	}
	
	@BeforeClass
	public static void setUp() {
		
	}
	
	@Test
	public void testAddSong() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getSongs().size());

		String songTitle= "Jumpman";
		String duration = "02:30";
		
		String albumTitle = "If you're reading this it's too late";
		String genre = "Hip Hop";
		Calendar c = Calendar.getInstance();
		c.set(2015, Calendar.FEBRUARY,17);
		Date date = new Date(c.getTimeInMillis());
	
		String artistName = "Drake";

		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addAlbum(albumTitle, genre, date);
			hasc.addArtist(artistName);
			hasc.addSong(songTitle, duration, has.getAlbum(0), has.getArtist(0));;
		} catch (InvalidInputException e) {
			// check that no error occurred
			fail();
		}

		// check model in memory
		checkResultSong(songTitle, duration, has.getAlbum(0), has.getArtist(0), has);

		HAS has2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();

		// check file contents
		checkResultSong(songTitle, duration, has.getAlbum(0), has.getArtist(0), has2);
	}
	
	@Test
	public void testAddSongNull() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getSongs().size());

		String songTitle= null;
		String duration = null;
		
		String albumTitle = "If you're reading this it's too late";
		String genre = "Hip Hop";
		Calendar c = Calendar.getInstance();
		c.set(2015, Calendar.FEBRUARY,17);
		Date date = new Date(c.getTimeInMillis());
	
		String artistName = "Drake";

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addAlbum(albumTitle, genre, date);
			hasc.addArtist(artistName);
			hasc.addSong(songTitle, duration, has.getAlbum(0), has.getArtist(0));;
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Song title cannot be empty! Song duration cannot be empty!", error);

		//check no change in memory
		assertEquals(0, has.getSongs().size());
	}
	
	public void testAddSongEmpty() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getSongs().size());

		String songTitle= "";
		String duration = "";
		
		String albumTitle = "If you're reading this it's too late";
		String genre = "Hip Hop";
		Calendar c = Calendar.getInstance();
		c.set(2015, Calendar.FEBRUARY,17);
		Date date = new Date(c.getTimeInMillis());
	
		String artistName = "Drake";

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addAlbum(albumTitle, genre, date);
			hasc.addArtist(artistName);
			hasc.addSong(songTitle, duration, has.getAlbum(0), has.getArtist(0));;
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Song title cannot be empty! Song duration cannot be empty!", error);

		//check no change in memory
		assertEquals(0, has.getSongs().size());
	}
	
	public void testAddSongSpaces() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getSongs().size());

		String songTitle= " ";
		String duration = " ";
		
		String albumTitle = "If you're reading this it's too late";
		String genre = "Hip Hop";
		Calendar c = Calendar.getInstance();
		c.set(2015, Calendar.FEBRUARY,17);
		Date date = new Date(c.getTimeInMillis());
	
		String artistName = "Drake";

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addAlbum(albumTitle, genre, date);
			hasc.addArtist(artistName);
			hasc.addSong(songTitle, duration, has.getAlbum(0), has.getArtist(0));;
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Song title cannot be empty! Song duration cannot be empty!", error);

		//check no change in memory
		assertEquals(0, has.getSongs().size());
	}
	
	public void testAddSongWrongFormatDuration() {
		HAS has = HAS.getInstance();
		assertEquals(0, has.getSongs().size());

		String songTitle= "Jumpman";
		String duration = "1";
		
		String albumTitle = "If you're reading this it's too late";
		String genre = "Hip Hop";
		Calendar c = Calendar.getInstance();
		c.set(2015, Calendar.FEBRUARY,17);
		Date date = new Date(c.getTimeInMillis());
	
		String artistName = "Drake";

		String error = null;
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addAlbum(albumTitle, genre, date);
			hasc.addArtist(artistName);
			hasc.addSong(songTitle, duration, has.getAlbum(0), has.getArtist(0));;
		} catch (InvalidInputException e) {
			error = e.getMessage();	
		}

		// check error
		assertEquals("Duration format is not correct!", error);

		//check no change in memory
		assertEquals(0, has.getSongs().size());
	}
	
	private void checkResultArtist(String name, HAS has2) {
		assertEquals(1, has2.getArtists().size());
		assertEquals(name, has2.getArtist(0).getName());
		assertEquals(0, has2.getAlbums().size());
		assertEquals(0, has2.getPlaylists().size());
		assertEquals(0, has2.getSongs().size());
		assertEquals(0, has2.getLocations().size());
	}

	private void checkResultAlbum(String title, String genre, Date date, HAS has2) {
		assertEquals(1, has2.getAlbums().size());
		assertEquals(title, has2.getAlbum(0).getTitle());
		assertEquals(date.toString(), has2.getAlbum(0).getReleaseDate().toString());
		assertEquals(0, has2.getSongs().size());
		assertEquals(0, has2.getArtists().size());
		assertEquals(0, has2.getPlaylists().size());
		assertEquals(0, has2.getLocations().size());
	}

	private void checkResultPlaylist(String name, HAS has2) {
		assertEquals(1, has2.getPlaylists().size());
		assertEquals(name, has2.getPlaylist(0).getName());
		assertEquals(0, has2.getSongs().size());
		assertEquals(0, has2.getArtists().size());
		assertEquals(0, has2.getAlbums().size());
		assertEquals(0, has2.getLocations().size());
	}
	
	private void checkResultLocation(String name, int volume, HAS has2) {
		assertEquals(1, has2.getLocations().size());
		assertEquals(name, has2.getLocation(0).getName());
		assertEquals(volume, has2.getLocation(0).getVolume());
		assertEquals(0, has2.getSongs().size());
		assertEquals(0, has2.getArtists().size());
		assertEquals(0, has2.getPlaylists().size());
		assertEquals(0, has2.getAlbums().size());
	}
	
	private void checkResultSong(String title, String duration, Album album, Artist artist, HAS has2) {
		assertEquals(1, has2.getSongs().size());
		assertEquals(title, has2.getSong(0).getTitle());
		assertEquals(duration, has2.getSong(0).getDuration());
		assertEquals(album.getTitle(), has2.getSong(0).getAlbum().getTitle());
		assertEquals(artist.getName(), has2.getSong(0).getArtist().getName());	
		assertEquals(1, has2.getSong(0).getPositionInAlbum());
		assertEquals(0, has2.getLocations().size());
		assertEquals(1, has2.getArtists().size());
		assertEquals(0, has2.getPlaylists().size());
		assertEquals(1, has2.getAlbums().size());
	}
}

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
}
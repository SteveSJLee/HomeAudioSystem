package ca.mcgill.ecse321.HomeAudioSystem.controller;

import ca.mcgill.ecse321.HomeAudioSystem.persistence.PersistenceXStream;

import java.sql.Date;

import ca.mcgill.ecse321.HomeAudioSystem.model.Album;
import ca.mcgill.ecse321.HomeAudioSystem.model.Artist;
import ca.mcgill.ecse321.HomeAudioSystem.model.HAS;
import ca.mcgill.ecse321.HomeAudioSystem.model.Song;

public class HomeAudioSystemController {
	
	public HomeAudioSystemController ()
	{
	}
	
	public void addAlbum(String title, String genre, Date date) throws InvalidInputException
	{
		String error = "";
		if (title == null || title.trim().length() == 0)
			error = error + "Album title cannot be empty! ";
		if (genre == null)
			error = error + "Album genre cannot be empty! ";
		if (date == null)
			error = error + "Album release date cannot be empty! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);
		
		Album ab = new Album(title, genre, date);
		HAS has = HAS.getInstance();
		has.addAlbum(ab);
		PersistenceXStream.saveToXMLwithXStream(has);
	}
	
	public void addArtist(String name) throws InvalidInputException
	{
		if (name == null || name.trim().length() ==0)
			throw new InvalidInputException("Artist name cannot be empty!");
		
		Artist a  = new Artist(name);
		HAS has = HAS.getInstance();
		has.addArtist(a);
		PersistenceXStream.saveToXMLwithXStream(has);
	}
	
	public void addSong(String title, String duration, Album album, Artist artist) throws InvalidInputException
	{
		String error = "";
		if (title == null || title.trim().length() == 0)
			error = error +"Song title cannot be empty! ";
		if (duration == null || duration.trim().length() == 0)
			error = error + "Song duration cannot be empty! ";
//		if (positionInAlbum < 1)
//			error = error + "Song position in album cannot be empty! ";
		if (album == null || album.toString().trim().equals(""))
			error = error + "Song album cannot be empty! ";
		if (artist == null || artist.toString().trim().equals(""))
			error = error + "Song artist cannot be empty! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);
		
		Song s = new Song(title, duration, album, artist);
		HAS has = HAS.getInstance();
		has.addSong(s);
		album.addSong(s);
		artist.addSong(s);
		s.setPositionInAlbum(album.getSongs().indexOf(s) + 1);
		PersistenceXStream.saveToXMLwithXStream(has);
	}
	
}

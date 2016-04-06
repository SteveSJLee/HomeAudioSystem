package ca.mcgill.ecse321.HomeAudioSystem.controller;

import ca.mcgill.ecse321.HomeAudioSystem.persistence.PersistenceXStream;
import ca.mcgill.ecse321.HomeAudioSystem.controller.InvalidInputException;

import java.sql.Date;

import ca.mcgill.ecse321.HomeAudioSystem.model.Album;
import ca.mcgill.ecse321.HomeAudioSystem.model.Artist;
import ca.mcgill.ecse321.HomeAudioSystem.model.HAS;
import ca.mcgill.ecse321.HomeAudioSystem.model.Location;
import ca.mcgill.ecse321.HomeAudioSystem.model.Playlist;
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
		String error = "";
		if (name == null || name.trim().length() ==0)
			error = error + "Artist name cannot be empty! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);

		Artist a  = new Artist(name);
		HAS has = HAS.getInstance();
		has.addArtist(a);
		PersistenceXStream.saveToXMLwithXStream(has);
	}

	public void addSong(String title, String duration, Album album, Artist artist) throws InvalidInputException
	{
		HAS has = HAS.getInstance();

		String error = "";
		if (album == null)
			error = error + "Album needs to be selected for adding a Song! ";
		else if (!has.getAlbums().contains(album))
			error = error + "Album does not exist! ";
		if (artist == null)
			error = error + "Artist needs to be selected for adding a Song! ";
		else if (!has.getArtists().contains(artist))
			error = error + "Artist does not exist! ";
		if (title == null || title.trim().length() == 0)
			error = error +"Song title cannot be empty! ";
		if (duration == null || duration.trim().length() == 0)
			error = error + "Song duration cannot be empty! ";
		if (duration.length() !=5)
			error = error + "Duration format is not correct! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);

		try {
			Integer.parseInt(duration.substring(0,2));
			int a = Integer.parseInt(duration.substring(3,5));

			if (a>59) {
				error = error + "Seconds cannot pass 60s! ";
			}
			else {
				Song s = new Song(title, duration, album, artist);
				has.addSong(s);
				album.addSong(s);
				artist.addSong(s);
				s.setPositionInAlbum(album.getSongs().indexOf(s) + 1);
				PersistenceXStream.saveToXMLwithXStream(has);
			}
		} catch (NumberFormatException n){
			error = error + "This is not a number! ";
			error = error.trim();
			if (error.length() > 0)
				throw new InvalidInputException(error);
		}
	}

	public void addPlaylist(String name) throws InvalidInputException 
	{
		HAS has = HAS.getInstance();

		String error = "";
		if (name == null || name.trim().length() == 0)
			error = error +"Playlist name cannot be empty! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);

		Playlist pl  = new Playlist(name);
		has.addPlaylist(pl);
		PersistenceXStream.saveToXMLwithXStream(has);
	}

	public void addLocation(String name, int volume) throws InvalidInputException
	{
		HAS has =  HAS.getInstance();

		String error = "";
		if (name == null || name.trim().length() == 0)
			error = error +"Location name cannot be empty! ";
		if (String.valueOf(volume) == null) 
			error = error + "Volume is not set! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);

		Location l = new Location(name);
		l.setVolume(volume);
		l.setIsPlaying(false);
		has.addLocation(l);
		PersistenceXStream.saveToXMLwithXStream(has);
	}

	public void changeVolumeLocation(Location location, int volume, int beforeMuted) throws InvalidInputException
	{
		HAS has =  HAS.getInstance();

		String error = "";
		if (location == null)
			error = error + "Location needs to be selected to mute Location ";
		else if (!has.getLocations().contains(location))
			error = error + "Location does not exist! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);

		if (volume != beforeMuted) {
			location.setVolume(volume);
			location.setBeforeMuted(beforeMuted);
			PersistenceXStream.saveToXMLwithXStream(has);
		}
	}

	public void addSongToPlaylist(Song song, Playlist playlist) throws InvalidInputException
	{
		HAS has =  HAS.getInstance();

		String error = "";
		if (song == null)
			error = error + "Song needs to be selected for adding Song to Playlist! ";
		else if (!has.getSongs().contains(song))
			error = error + "Song does not exist! ";
		if (playlist == null)
			error = error + "Playlist needs to be selected for adding Song to Playlist! ";
		else if (!has.getPlaylists().contains(playlist))
			error = error + "Playlist does not exist! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);

		playlist.addSong(song);
		PersistenceXStream.saveToXMLwithXStream(has);
	}

	public void assignSongToLocation(Song song, Location location) throws InvalidInputException
	{
		HAS has = HAS.getInstance();

		String error = "";
		if (song == null)
			error = error + "Song needs to be selected for assigning Song to Location! ";
		else if (!has.getSongs().contains(song))
			error = error + "Song does not exist! ";
		if (location == null)
			error = error + "Location needs to be selected for assigning Song to Location! ";
		else if (!has.getLocations().contains(location))
			error = error + "Location does not exist! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);

		// activate timer 

		location.delete();
		location.setSong(song);		
		location.setIsPlaying(false);

		int timer = 0;
		timer =  Integer.parseInt(location.getSong().getDuration().substring(0, 2))*60 
				+ Integer.parseInt(location.getSong().getDuration().substring(3)); 

		location.setTime(timer);

		PersistenceXStream.saveToXMLwithXStream(has);		
	}

	public void assignAlbumToLocation(Album album, Location location) throws InvalidInputException
	{
		HAS has = HAS.getInstance();

		String error = "";
		if (album == null)
			error = error + "Album needs to be selected for assigning album to Location! ";
		else if (album.getSongs().isEmpty())
			error = error + "Album is empty! ";
		else if (!has.getAlbums().contains(album))
			error = error + "Album does not exist! ";
		if (location == null)
			error = error + "Location needs to be selected for assigning album to Location! ";
		else if (!has.getLocations().contains(location))
			error = error + "Location does not exist! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);

		location.delete();
		location.setAlbum(album);
		location.setIsPlaying(false);
		int timer = 0;
		for (int i = 0; i < location.getAlbum().numberOfSongs(); i ++) {
			timer += Integer.parseInt(location.getAlbum().getSong(i).getDuration().substring(0, 2))*60 
					+ Integer.parseInt(location.getAlbum().getSong(i).getDuration().substring(3)); 
		}
		location.setTime(timer);

		PersistenceXStream.saveToXMLwithXStream(has);	
	}

	public void assignPlaylistToLocation(Playlist playlist, Location location) throws InvalidInputException
	{
		HAS has = HAS.getInstance();

		String error = "";
		if (playlist == null)
			error = error + "Playlist needs to be selected for assigning playlist to Location! ";
		else if (playlist.getSongs().isEmpty())
			error = error + "Playlist is Empty! ";
		else if (!has.getPlaylists().contains(playlist))
			error = error + "Playlist does not exist! ";
		if (location == null)
			error = error + "Location needs to be selected for assigning playlist to Location! ";
		else if (!has.getLocations().contains(location))
			error = error + "Location does not exist! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);

		location.delete();
		location.setPlaylist(playlist);
		location.setIsPlaying(false);
		int timer = 0;
		for (int i = 0; i < location.getPlaylist().getSongs().size(); i ++) {
			timer += Integer.parseInt(location.getPlaylist().getSong(i).getDuration().substring(0, 2))*60 
					+ Integer.parseInt(location.getPlaylist().getSong(i).getDuration().substring(3)); 
		}
		location.setTime(timer);
		PersistenceXStream.saveToXMLwithXStream(has);
	}

	public void playPauseAll() 
	{
		HAS has = HAS.getInstance();
		for (int i = 0; i < has.getLocations().size(); i++) {
			if (has.getLocation(i).getIsPlaying() == false) {
				// song
				if (has.getLocation(i).getSong() != null) {
					has.getLocation(i).setIsPlaying(true);

					PersistenceXStream.saveToXMLwithXStream(has);
				}
				// album
				else if (has.getLocation(i).getAlbum() != null) {
					has.getLocation(i).setIsPlaying(true);
					// activate timer 
					PersistenceXStream.saveToXMLwithXStream(has);
				}
				// playlist
				else if (has.getLocation(i).getPlaylist() != null) {
					has.getLocation(i).setIsPlaying(true);
					// activate timer 
					PersistenceXStream.saveToXMLwithXStream(has);
				}
			}

			else if (has.getLocation(i).getIsPlaying() == true) {
				// song
				if (has.getLocation(i).getSong() != null) {
					has.getLocation(i).setIsPlaying(false);

					PersistenceXStream.saveToXMLwithXStream(has);
				}
				// album
				else if (has.getLocation(i).getAlbum() != null) {
					has.getLocation(i).setIsPlaying(false);
					// activate timer 
					PersistenceXStream.saveToXMLwithXStream(has);
				}
				// playlist
				else if (has.getLocation(i).getPlaylist() != null) {

					has.getLocation(i).setIsPlaying(false);
					// activate timer 
					PersistenceXStream.saveToXMLwithXStream(has);

				}
			}
		}
	}

	public void playPause(Location location, boolean playing) throws InvalidInputException
	{
		HAS has = HAS.getInstance();
		String error = "";
		if (location == null)
			error = error + "Location needs to be selected to pause! ";
		else if (!has.getLocations().contains(location))
			error = error + "Location does not exist! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);

		location.setIsPlaying(playing);
		PersistenceXStream.saveToXMLwithXStream(has);

	}
	
	public void clearLocation(Location location) throws InvalidInputException 
	{
		HAS has = HAS.getInstance();
		String error = "";
		
		if (location == null)
			error = error + "Location needs to be selected for assigning Song to Location! ";
		else if (!has.getLocations().contains(location))
			error = error + "Location does not exist! ";
		error = error.trim();
		if (error.length() > 0)
			throw new InvalidInputException(error);
		
		location.delete();
		
		PersistenceXStream.saveToXMLwithXStream(has);
	}

	public void clearAllLocation() {
		HAS has = HAS.getInstance();
		for (int i = 0; i < has.getLocations().size(); i++) {
			has.getLocation(i).delete();
		}

		PersistenceXStream.saveToXMLwithXStream(has);
	}

}

package ca.mcgill.ecse321.HomeAudioSystem.persistence;

import java.util.Iterator;

import ca.mcgill.ecse321.HomeAudioSystem.model.Album;
import ca.mcgill.ecse321.HomeAudioSystem.model.Artist;
import ca.mcgill.ecse321.HomeAudioSystem.model.HAS;

public class PersistenceHomeAudioSystem {

	private static String filename = "homeaudiosystem.xml";

	private static void initializeXStream() {
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.setAlias("album", Album.class);
		PersistenceXStream.setAlias("artist", Artist.class);
		PersistenceXStream.setAlias("audiosystem", HAS.class);
	}
	
	public static void loadHomeAudioSystemModel() {
		HAS has = HAS.getInstance();
		PersistenceHomeAudioSystem.initializeXStream();
		HAS has2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		if (has2 != null) {
			Iterator<Album> abIt = has2.getAlbums().iterator();
			while (abIt.hasNext())
				has.addAlbum(abIt.next());
			Iterator<Artist> aIt = has2.getArtists().iterator();
			while (aIt.hasNext())
				has.addArtist(aIt.next());
		}
	}

	public static void setFileName (String name){
		filename = name;
	}
}

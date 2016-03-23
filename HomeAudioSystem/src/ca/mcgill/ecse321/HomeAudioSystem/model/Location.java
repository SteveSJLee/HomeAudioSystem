/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.HomeAudioSystem.model;
import java.sql.Date;

// line 32 "../../../../../HomeAudioSystem.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private String name;
  private int volume;
  private boolean isMuted;

  //Location Associations
  private Song songs;
  private Album albums;
  private Playlist playlists;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(String aName, int aVolume, boolean aIsMuted, Song aSongs, Album aAlbums, Playlist aPlaylists)
  {
    name = aName;
    volume = aVolume;
    isMuted = aIsMuted;
    if (!setSongs(aSongs))
    {
      throw new RuntimeException("Unable to create Location due to aSongs");
    }
    if (!setAlbums(aAlbums))
    {
      throw new RuntimeException("Unable to create Location due to aAlbums");
    }
    if (!setPlaylists(aPlaylists))
    {
      throw new RuntimeException("Unable to create Location due to aPlaylists");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setVolume(int aVolume)
  {
    boolean wasSet = false;
    volume = aVolume;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsMuted(boolean aIsMuted)
  {
    boolean wasSet = false;
    isMuted = aIsMuted;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getVolume()
  {
    return volume;
  }

  public boolean getIsMuted()
  {
    return isMuted;
  }

  public Song getSongs()
  {
    return songs;
  }

  public Album getAlbums()
  {
    return albums;
  }

  public Playlist getPlaylists()
  {
    return playlists;
  }

  public boolean setSongs(Song aNewSongs)
  {
    boolean wasSet = false;
    if (aNewSongs != null)
    {
      songs = aNewSongs;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setAlbums(Album aNewAlbums)
  {
    boolean wasSet = false;
    if (aNewAlbums != null)
    {
      albums = aNewAlbums;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setPlaylists(Playlist aNewPlaylists)
  {
    boolean wasSet = false;
    if (aNewPlaylists != null)
    {
      playlists = aNewPlaylists;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    songs = null;
    albums = null;
    playlists = null;
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "volume" + ":" + getVolume()+ "," +
            "isMuted" + ":" + getIsMuted()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "songs = "+(getSongs()!=null?Integer.toHexString(System.identityHashCode(getSongs())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "albums = "+(getAlbums()!=null?Integer.toHexString(System.identityHashCode(getAlbums())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "playlists = "+(getPlaylists()!=null?Integer.toHexString(System.identityHashCode(getPlaylists())):"null")
     + outputString;
  }
}
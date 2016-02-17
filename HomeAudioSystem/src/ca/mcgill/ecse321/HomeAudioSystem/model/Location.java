/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.HomeAudioSystem.model;

// line 30 "../../../../../HomeAudioSystem.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private String name;
  private int volume;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(String aName, int aVolume)
  {
    name = aName;
    volume = aVolume;
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

  public String getName()
  {
    return name;
  }

  public int getVolume()
  {
    return volume;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "volume" + ":" + getVolume()+ "]"
     + outputString;
  }
}
<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-f2a13e6 modeling language!*/

class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private $name;
  private $volume;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aVolume)
  {
    $this->name = $aName;
    $this->volume = $aVolume;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setName($aName)
  {
    $wasSet = false;
    $this->name = $aName;
    $wasSet = true;
    return $wasSet;
  }

  public function setVolume($aVolume)
  {
    $wasSet = false;
    $this->volume = $aVolume;
    $wasSet = true;
    return $wasSet;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getVolume()
  {
    return $this->volume;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {}

}
?>
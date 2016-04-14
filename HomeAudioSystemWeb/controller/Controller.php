<?php

 /*
require_once 'persistence/PersistenceAudioSystem.php';
require_once 'model/HAS.php';
require_once 'model/Album.php';
require_once 'model/Artist.php';
require_once 'model/Playlist.php';
require_once 'model/Location.php';
require_once 'model/Song.php';
require_once 'controller/InputValidator.php';
 */

// /*
require_once 'C:\Users\ArnoldK\Desktop\Group02\HomeAudioSystemWeb\persistence\PersistenceAudioSystem.php';
require_once 'C:\Users\ArnoldK\Desktop\Group02\HomeAudioSystemWeb\model\HAS.php';
require_once 'C:\Users\ArnoldK\Desktop\Group02\HomeAudioSystemWeb\model\Album.php';
require_once 'C:\Users\ArnoldK\Desktop\Group02\HomeAudioSystemWeb\model\Artist.php';
require_once 'C:\Users\ArnoldK\Desktop\Group02\HomeAudioSystemWeb\model\Playlist.php';
require_once 'C:\Users\ArnoldK\Desktop\Group02\HomeAudioSystemWeb\model\Location.php';
require_once 'C:\Users\ArnoldK\Desktop\Group02\HomeAudioSystemWeb\model\Song.php';
require_once 'C:\Users\ArnoldK\Desktop\Group02\HomeAudioSystemWeb\controller\InputValidator.php';
// */

//2/19/2016

class Controller
{
	public function __construct()
	{	
	}
	public function createAlbum($album_title, $album_genre, $album_releasedate){
		//Validate Input
		$title = InputValidator::validate_input($album_title);
		$genre = InputValidator::validate_input($album_genre);
		$releasedate = InputValidator::validate_input($album_releasedate);
		
		$error = "";
		
		if ($title == null || strlen($title) == 0){
			$error .= "Album name cannot be empty! ";
		}
		if ($genre == null || strlen($genre) == 0){
			$error .= "Album genre cannot be empty! ";
		}
		if ($releasedate == null || strlen($releasedate) == 0){
			$error .= "Album must have a release date! ";
		}
		elseif (strtotime($album_releasedate) == false){
			$error .= "Album must have a release date specified correctly (YYYY-MM-DD)! ";
		} else {
			$album_releasedate = date('Y-m-d', strtotime($album_releasedate));
		}
		
		if($error == ""){
			// 2. Load all data
			$pm = new PersistenceAudioSystem();
			$has = $pm->loadDataFromStore();
			
			// 3. Add new Album
			$album = new Album($title, $genre, $releasedate);
			$has->addAlbum($album);
			
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
	public function createArtist($artist_name){
		//Validate Input
		$name = InputValidator::validate_input($artist_name);
	
		$error = "";
	
		if ($name == null || strlen($name) == 0){
			$error .= "Artist name cannot be empty! ";
		}
	
		if($error == ""){
			// 2. Load all data
			$pm = new PersistenceAudioSystem();
			$has = $pm->loadDataFromStore();
				
			// 3. Add new Album
			$artist = new Artist($name);
			$has->addArtist($artist);
				
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
	public function createSong($song_title, $song_duration, $song_album_index, $song_artist_index){
		//Validate Input
		$title = InputValidator::validate_input($song_title);
		$duration = InputValidator::validate_input($song_duration);
		$album = InputValidator::validate_input($song_album_index);
		$artist = InputValidator::validate_input($song_artist_index);
	
		$error = "";
	
		if ($title == null || strlen($title) == 0){
			$error .= "Song title cannot be empty! ";
		}
		if ($duration == null || strlen($duration) == 0){
			$error .= "Song duration cannot be empty! ";
		}
		elseif (strlen($duration)!=5){
			$error .= "Song duration must follow the format ##:## ! ";
		}
		if ($album == null || strlen($album) == 0){
			$error .= "Song album must be selected! ";
		}
		if ($artist == null || strlen($artist) == 0){
			$error .= "Song artist must be selected! ";
		}
	
		if($error == ""){
			// 2. Load all data
			$pm = new PersistenceAudioSystem();
			$has = $pm->loadDataFromStore();
	
			// 3. Add new Song
			$song = new Song($title, $duration, $has->getAlbum_index($song_album_index), $has->getArtist_index($song_artist_index));
			$has->addSong($song);
	
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
	public function createPlaylist($playlist_name){
		//Validate Input
		$name = InputValidator::validate_input($playlist_name);
	
		$error = "";
	
		if ($name == null || strlen($name) == 0){
			$error .= "Playlist name cannot be empty! ";
		}
	
		if($error == ""){
			// 2. Load all data
			$pm = new PersistenceAudioSystem();
			$has = $pm->loadDataFromStore();
	
			// 3. Add new Album
			$playlist = new Playlist($name);
			$has->addPlaylist($playlist);
	
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
	public function createLocation($location_name, $location_volume){
		//Validate Input
		$name = InputValidator::validate_input($location_name);
		$volume = InputValidator::validate_input($location_volume);
		
		$error = "";
		
	
		if ($name == null || strlen($name) == 0){
			$error .= "Location name cannot be empty! ";
		}
		if ($volume == null || strlen($volume) == 0){
			$error .= "Location volume cannot be empty! ";
		}
		elseif (!ctype_digit($location_volume)){
			$error .= "Location volument must be an Integer! ";
		}
	
		if($error == ""){
			// 2. Load all data
			$pm = new PersistenceAudioSystem();
			$has = $pm->loadDataFromStore();
	
			// TODO: fix null inputs not allowed
			
			$location = new Location($name, null, null, null);
			$location->setVolume($volume);
			$has->addLocation($location);
	
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
	public function muteLocation($location, $location_volume, $location_beforeMuted){
		//Validate Input
		$volume = InputValidator::validate_input($location_volume);
		$beforeMuted = InputValidator::validate_input($location_beforeMuted);
		
		$pm = new PersistenceAudioSystem();
		$has = $pm->loadDataFromStore();
	
		$error = "";
		if($location == null){
			$error .= "Location must be selected! ";
		}
		elseif(in_array($location, $has->getLocations()));{
			$error .= "Location does not exist! ";
		}
		if (strval($volume) == null){
			$error .= "Location volume cannot be empty! ";
		}
		
	
		if($error == ""){
			$location->setVolume($volume);
			$location->setBeforeMuted($beforeMuted);
	
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
	public function addSongToPlaylist($song_index, $playlist_index){
		//Validate Input
		$pm = new PersistenceAudioSystem();
		$has = $pm->loadDataFromStore();
		
		$song=$has->getSong_index($song_index);
		$playlist=$has->getPlaylist_index($playlist_index);
	
		$error = "";
	
	
		if($song == null){
			$error .= "Song must be selected! ";
		}/*
		elseif(!in_array($song, $has->getSongs()));{
			$error .= "Song does not exist! ";
		}*/
		if($playlist == null){
			$error .= "Playlist must be selected! ";
		}/*
		elseif(!in_array($playlist, $has->getPlaylists()));{
			$error .= "Playlist does not exist! ";
		}*/
	
		if($error == ""){
			$playlist->addSong($song);
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
	public function assignSongToLocation($song_index, $location_index){
		//Validate Input
		$pm = new PersistenceAudioSystem();
		$has = $pm->loadDataFromStore();
		
		$song=$has->getSong_index($song_index);
		$location=$has->getLocation_index($location_index);
	
		$error = "";
		$songs = $has->getSongs();
	
		if($song == null){
			$error .= "Song must be selected! ";
		}
		elseif(!in_array($song, $has->getSongs())){
			$error .= "Song does not exist! ";
		}
		if($location == null){
			$error .= "Location must be selected! ";
		}
		elseif(!in_array($location, $has->getLocations())){
			$error .= "Location does not exist! ";
		}
	
		if($error == ""){
			$location->delete();
			$location->setSong($song);
			
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
	public function assignAlbumToLocation($album_index, $location_index){
		//Validate Input
		$pm = new PersistenceAudioSystem();
		$has = $pm->loadDataFromStore();
	
		$error = "";
		
		$album=$has->getAlbum_index($album_index);
		$location=$has->getLocation_index($location_index);
	
		if($album == null){
			$error .= "Album must be selected! ";
		}
		elseif(!in_array($album, $has->getAlbums())){
			$error .= "Album does not exist! ";
		}
		if($location == null){
			$error .= "Location must be selected! ";
		}
		elseif(!in_array($location, $has->getLocations())){
			$error .= "Location does not exist! ";
		}
	
		if($error == ""){
			$location->delete();
			$location->setAlbum($album);
				
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
	public function assignPlaylistToLocation($playlist_index, $location_index){
		//Validate Input
		$pm = new PersistenceAudioSystem();
		$has = $pm->loadDataFromStore();
	
		$error = "";
		
		$playlist=$has->getPlaylist_index($playlist_index);
		$location=$has->getLocation_index($location_index);
	
		if($playlist == null){
			$error .= "Playlist must be selected! ";
		}
		elseif(!in_array($playlist, $has->getPlaylists())){
			$error .= "Playlist does not exist! ";
		}
		if($location == null){
			$error .= "Location must be selected! ";
		}
		elseif(!in_array($location, $has->getLocations())){
			$error .= "Location does not exist! ";
		}
	
		if($error == ""){
			$location->delete();
			$location->setPlaylist($playlist);
				
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
	public function play(){
		$pm = new PersistenceAudioSystem();
		$has = $pm->loadDataFromStore();
		for($i = 0;$i<count($has->getLocations());$i++){
			if($has->getLocation_index($i)->getAlbum() != null){
				if(!($has->getLocation(i)->getIsPlaying())){
					$has->getLocation_index(i)->setIsPlaying(true);
				}
			}
			if($has->getLocation_index($i)->getSong() != null){
				if(!($has->getLocation(i)->getIsPlaying())){
					$has->getLocation_index(i)->setIsPlaying(true);
				}
			}
			if($has->getLocation_index($i)->getPLaylust != null){
				if(!($has->getLocation(i)->getIsPlaying())){
					$has->getLocation_index(i)->setIsPlaying(true);
				}
			}
		}
		
	}
	
}



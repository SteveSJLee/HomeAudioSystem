<?php

require_once 'C:\xampp\htdocs\HomeAudioSystemWeb\persistence\PersistenceAudioSystem.php';
require_once 'C:\xampp\htdocs\HomeAudioSystemWeb\model\HAS.php';
require_once 'C:\xampp\htdocs\HomeAudioSystemWeb\model\Album.php';
require_once 'C:\xampp\htdocs\HomeAudioSystemWeb\controller\InputValidator.php';

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
			$error .= "@1Album name cannot be empty! ";
		}
		if ($genre == null || strlen($genre) == 0){
			$error .= "@2Album genre cannot be empty! ";
		}
		if ($releasedate == null || strlen($releasedate) == 0){
			$error .= "@3Album must have a release date!1";
		}
		
		if($error == ""){
			// 2. Load all data
			$pm = new PersistenceAudioSystem();
			$has = $pm->loadDataFromStore();
			
			// 3. Add new participant
			$album = new Album($title, $genre, $releasedate);
			$has->addAlbum($album);
			
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
	}
}

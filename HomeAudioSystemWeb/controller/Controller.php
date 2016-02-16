<?php
require_once '\..\controller\InputValidator.php';
require_once '\..\model\Album.php';
require_once '\..\model\HAS.php';
require_once '\..\persistence\PersistenceAudioSystem.php';


class Controller
{
	public function __construct(){	
	}
	public function createAlbum($album_title, $album_genre, $album_releasedate){
		//Validate Input
		$title = InputValidator::validate_input($album_title);
		$genre = InputValidator::validate_input($album_genre);
		$releasedate = InputValidator::validate_input($album_releasedate);
		
		$error = "";
		
		if ($title == null || strlen($title) == 0){
			$error .= "@1Album name cannot be empty!";
		}
		if ($genre == null || strlen($genre) == 0){
			$error .= "2@Album genre cannot be empty!";
		}
		if ($releasedate == null || strlen($releasedate) == 0){
			$error .= "3@Album must have a release date!";
		}
		
		if($error = ""){
			// 2. Load all data
			$pm = new PersistenceAudioSystem();
			$has = $pm->loadDataFromStore();
			
			// 3. Add new participant
			$album = new Album($album_title, $album_genre, $album_releasedate);
			$has->addAlbum($album);
			
			// 4. Write all of the data
			$pm->writeDataToStore($has);
		} else {
			throw new Exception(trim($error));
		}
		}
}

<?php
require_once 'controller\Controller.php';


session_start();

$_SESSION["errorAlbumTitle"] = "";
$_SESSION["errorAlbumGenre"] = "";
$_SESSION["errorAlbumReleaseDate"] = "";

$c = new Controller();

try {
	$c->createAlbum($_POST['album_title'], $_POST['album_genre'], $_POST['album_releasedate']);

} catch (Exception $e) {

	$errors = explode("@", $e->getMessage());
	foreach ($errors as $error) {
		if (substr($error, 0, 1) == "1") {
			$_SESSION["errorAlbumTitle"] = substr($error, 1);
		}
		if (substr($error, 0, 1) == "2") {
			$_SESSION["errorAlbumGenre"] = substr($error, 1);
		}
		if (substr($error, 0, 1) == "3") {
			$_SESSION["errorAlbumReleaseDate"] = substr($error, 1);
		}
	}
}
?>

<!DOCTYPE html> 
<html> 
  	<head> 
    	<meta http-equiv="refresh" content="0; url=/HomeAudioSystemWeb/" /> 
  	</head> 
</html> 
<?php
require_once 'controller/Controller.php';


session_start();

$_SESSION["errorAlbumTitle"] = "";
$_SESSION["errorAlbumGenre"] = "";
$_SESSION["errorAlbumReleaseDate"] = "";

$c = new Controller();

try {
	$c->createAlbum($_POST['album_title'], $_POST['album_genre'], $_POST['album_releasedate']);

} catch (Exception $e) {
		$_SESSION["error"] = $e->getMessage();
	
}
?>

<!DOCTYPE html> 
<html> 
  	<head> 
    	<meta http-equiv="refresh" content="0; url=/HomeAudioSystemWeb/" /> 
  	</head> 
</html> 
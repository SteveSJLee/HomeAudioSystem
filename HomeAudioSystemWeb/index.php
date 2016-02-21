<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home Audio System</title>
		<style>
			.error {color: #FF0000;}
		</style>
	</head>
	<body>
		<?php 
		
		require_once 'persistence\PersistenceAudioSystem.php';
		require_once 'model\HAS.php';
		require_once 'model\Album.php';
		
		
		//retrieve the data from the model
		$pm = new PersistenceAudioSystem();
		$has = $pm->loadDataFromStore();
		
		session_start();
		
		?>
		<form action="addalbum.php" method="post">
			<p>Title? <input type="text" name="album_title"/>
			<span class="error">
			<?php 
			if (isset($_SESSION['errorAlbumTitle']) && !empty($_SESSION['errorAlbumTitle'])){
				echo " * " . $_SESSION["errorAlbumTitle"];
			}
			?>
			</span></p>
			<p>Genre? <input type="text" name="album_genre"/>
			<span class="error">
			<?php 
			if (isset($_SESSION['errorAlbumGenre']) && !empty($_SESSION['errorAlbumGenre'])){
				echo " * " . $_SESSION["errorAlbumGenre"];
			}
			?>
			</span>
			<p>Release Date? <input type="date" name="album_releasedate" value="<?php echo date('Y-m-d');?>"/>
			<span class="error">
			<?php 
			if (isset($_SESSION['errorAlbumReleaseDate']) && !empty($_SESSION['errorAlbumReleaseDate'])){
				echo " * " . $_SESSION["errorAlbumReleaseDate"];
			}
			?>
			</span>
			<p><input type="submit" value="Add Album"/></p>
		</form>
	</body>
</html>
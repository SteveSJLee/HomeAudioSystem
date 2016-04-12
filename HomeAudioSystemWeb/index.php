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
		
		require_once 'persistence/PersistenceAudioSystem.php';
		require_once 'model/HAS.php';
		require_once 'model/Album.php';
		require_once 'model/Artist.php';
		require_once 'model/Playlist.php';
		require_once 'model/Location.php';
		require_once 'model/Song.php';
		
		//retrieve the data from the model
		$pm = new PersistenceAudioSystem();
		$has = $pm->loadDataFromStore();
		
		session_start();
		
		
		?>
		<p><span class="error">
			<?php 
				echo $_SESSION["error"];
			?>
		</span></p>
		
		
		<div style="float:left; min-width:500; height:180px; background:grey; margin:10px;padding-left:10px; padding-right:10px;"> 
		<form action="addalbum.php" method="post">
			<p>Title: <input type="text" name="album_title"/></p>
			<p>Genre: <input type="text" name="album_genre"/></p>
			<p>Release Date: <input type="date" name="album_releasedate" value="<?php echo date('Y-m-d');?>"/></p>
			<p><input type="submit" value="Add Album"/></p>
		</form>
		</div>
		
		<div style="width:150; float:left; height:180px; background:grey; margin:10px;padding-left:10px; padding-right:10px;"> 
		<form action="addsong.php" method="post">
  			<p>Album: <select name="song_album">
  			<?php
  			for($i=0;$i<$has->numberOfAlbums();$i++){
  				$name=$has->getAlbum_index($i)->getTitle();	
  				echo "<option value='$i'>$name</option>";
  			}
  			?>
  			
  			</select></p>
  			<p>Artist: <select name="song_artist">
    		<?php 
  			for($i=0; $i<$has->numberOfArtists();$i++){
  				$name=$has->getArtist_index($i)->getName();
  				echo "<option value='$i'>$name</option>";
  			}
  			?>
  			</select></p>
  			<p>Duration: <input type="text" name="song_duration"/></p>
  			<p>Title: <input type="text" name="song_title"/></p>
 		 	<p><input type="submit" value="Add Song"/></p>
		</form>
		</div>
		
		<div style="float:left; width:150; height:180px; background:grey; margin:10px;padding-left:10px; padding-right:10px;"> 
		<form action="addlocation.php" method="post">
			<p>Title: <input type="text" name="location_name"/></p>
			<p>Volume: <input type="text" name="location_volume"/></p>
			<p><input type="submit" value="Add Location"/></p>
		</form>
		</div>
		
		<div style="float:left; width:150; height:180px; background:grey; margin:10px;padding-left:10px; padding-right:10px;"> 
		<form action="addartist.php" method="post">
			<p>Title: <input type="text" name="artist_name"/></p>
			<p><input type="submit" value="Add Artist"/></p>
		</form>
		</div>
		
		<div style="float:left; width:150; height:180px; background:grey; margin:10px;padding-left:10px; padding-right:10px;"> 
		<form action="addplaylist.php" method="post">
			<p>Name: <input type="text" name="playlist_name"/></p>
			<p><input type="submit" value="Add Playlist"/></p>
		</form>
		<form action="addsongtoplaylist.php" method="post">
			<p>Song: <select name="song_index">
  				<?php
  				for($i=0;$i<$has->numberOfSongs();$i++){
  					$name=$has->getSong_index($i)->getTitle();	
  					echo "<option value='$i'>$name</option>";
  				}
  				?>
  			</select></p>
  			<p>Playlist: <select name="playlist_index">
  				<?php
  				for($i=0;$i<$has->numberOfPlaylists();$i++){
  					$name=$has->getPlaylist_index($i)->getName();	
  					echo "<option value='$i'>$name</option>";
  				}
  				?>
  			</select></p>
  			<p><input type="submit" value="Add Song to Playlist"/></p>
  		</form>
		</div>
		
		<div style="float:left; width:150; height:150px; background:grey; margin:10px;padding-left:10px; padding-right:10px;"> 
		<form action="assignsongtolocation.php" method="post">
			<p>Location: <select name="location_index">
  				<?php
  				for($i=0;$i<$has->numberOfLocations();$i++){
  					$name=$has->getLocation_index($i)->getName();	
  					echo "<option value='$i'>$name</option>";
  				}
  				?>
  			</select></p>
			<p>Song: <select name="song_index">
  				<?php
  				for($i=0;$i<$has->numberOfSongs();$i++){
  					$name=$has->getSong_index($i)->getTitle();	
  					echo "<option value='$i'>$name</option>";
  				}
  				?>
  			</select></p>
  			<p><input type="submit" value="Assign Song to Location"/></p>
		</form>
		</div>	
			<div style="float:left; width:150; height:150px; background:grey; margin:10px;padding-left:10px; padding-right:10px;"> 
		<form action="assignalbumtolocation.php" method="post">
			<p>Location: <select name="location_index">
  				<?php
  				for($i=0;$i<$has->numberOfLocations();$i++){
  					$name=$has->getLocation_index($i)->getName();	
  					echo "<option value='$i'>$name</option>";
  				}
  				?>
  			</select></p>
			<p>Album: <select name="album_index">
  				<?php
  				for($i=0;$i<$has->numberOfAlbums();$i++){
  					$name=$has->getAlbum_index($i)->getTitle();	
  					echo "<option value='$i'>$name</option>";
  				}
  				?>
  			</select></p>
  			<p><input type="submit" value="Assign Album to Location"/></p>
		</form>
		</div>	
			<div style="float:left; width:150; height:150px; background:grey; margin:10px;padding-left:10px; padding-right:10px;"> 
		<form action="assignplaylisttolocation.php" method="post">
			<p>Location: <select name="location_index">
  				<?php
  				for($i=0;$i<$has->numberOfLocations();$i++){
  					$name=$has->getLocation_index($i)->getName();	
  					echo "<option value='$i'>$name</option>";
  				}
  				?>
  			</select></p>
			<p>Playlist: <select name="playlist_index">
  				<?php
  				for($i=0;$i<$has->numberOfPlaylists();$i++){
  					$name=$has->getPlaylist_index($i)->getName();	
  					echo "<option value='$i'>$name</option>";
  				}
  				?>
  			</select></p>
  			<p><input type="submit" value="Assign Playlist to Location"/></p>
		</form>
		</div>	
	</body>
</html>
package ca.mcgill.ecse321.HomeAudioSystem.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.ComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.HomeAudioSystem.controller.HomeAudioSystemController;
import ca.mcgill.ecse321.HomeAudioSystem.controller.InvalidInputException;
import ca.mcgill.ecse321.HomeAudioSystem.model.Genre;
import ca.mcgill.ecse321.HomeAudioSystem.model.Album;
import ca.mcgill.ecse321.HomeAudioSystem.model.Artist;
import ca.mcgill.ecse321.HomeAudioSystem.model.HAS;
import ca.mcgill.ecse321.HomeAudioSystem.model.Location;
import ca.mcgill.ecse321.HomeAudioSystem.model.Playlist;
import ca.mcgill.ecse321.HomeAudioSystem.model.Song;

public final class HomeAudioSystemPage extends JFrame {
	private static final long serialVersionUID = -8062635784771606869L;

	// UI elements
	private JLabel errorMessage;
	private JComboBox<String> albumList;
	private JLabel albumLabel;
	
	private JComboBox<Genre> albumGenreTextField;
	private JLabel albumGenreLabel;

	private JTextField albumTitleTextField;
	private JLabel albumTitleLabel;
	private JDatePickerImpl albumDatePicker;
	private JLabel albumDateLabel;
	private JButton addAlbumButton;
	
	private JComboBox<String> artistList;
	private JLabel artistLabel;
	private JTextField artistNameTextField;
	private JLabel artistNameLabel;
	private JButton addArtistButton;
	
	private JComboBox<String> songList;
	private JLabel songLabel;
	private JTextField songTitleTextField;
	private JLabel songTitleLabel;
	private JTextField songDurationTextField;
	private JLabel songDurationLabel;
//	private JTextField songPositionTextField;
//	private JLabel songPositionLabel;
	private JButton addSongButton;
	
	private JComboBox<String> playlistList;
	private JLabel playlistLabel;
	private JTextField playlistNameTextField;
	private JLabel playlistNameLabel;
	private JButton addPlaylistButton;
	private JButton addSongToPlaylistButton;
	
	private JComboBox<String> locationList;
	private JLabel locationLabel;
	private JTextField locationNameTextField;
	private JLabel locationNameLabel;
	private JSlider locationVolumeLabel;
	private JButton muteButton;
	private JButton unMuteButton;
	private JButton addLocationButton;
	static final int VOL_MIN = 0;
	static final int VOL_MAX = 30;
	static final int VOL_INIT = 15;
	
	// data elements
	private String error = null;
	private Integer selectedAlbum = -1;
	private HashMap<Integer, Album> albums;
	private Integer selectedArtist = -1;
	private HashMap<Integer, Artist> artists;
	private Integer selectedSong = -1;
	private HashMap<Integer, Song> songs;
	private Integer selectedPlaylist = -1;
	private HashMap<Integer, Playlist> playlists;
	private Integer selectedLocation = -1;
	private HashMap<Integer, Location> locations;

	/** Creates new form HomeAudioSystemPage */
	public HomeAudioSystemPage() {
		initComponents();
		refreshData();
	}

	/** This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		// elements for creating album
		albumList = new JComboBox<String>(new String[0]);
		albumList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedAlbum = cb.getSelectedIndex();
			}
		});
		albumLabel = new JLabel();

		// elements for creating artist
		artistList = new JComboBox<String>(new String[0]);
		artistList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedArtist = cb.getSelectedIndex();
			}
		});
		artistLabel = new JLabel();
		
		// elements for creating song
		songList = new JComboBox<String>(new String[0]);
		songList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedSong = cb.getSelectedIndex();
			}
		});
		songLabel = new JLabel();

		// elements for creating playlist
		playlistList = new JComboBox<String>(new String[0]);
		playlistList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedPlaylist = cb.getSelectedIndex();
			}
		});
		playlistLabel = new JLabel();
		
		// elements for creating location
		locationList = new JComboBox<String>(new String[0]);
		locationList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedLocation = cb.getSelectedIndex();
			}
		});
		locationLabel = new JLabel();
		
		
		// elements for album
		albumTitleTextField = new JTextField();
		albumTitleLabel = new JLabel();
		
		albumGenreTextField = new JComboBox(Genre.values());
		albumGenreLabel = new JLabel();

		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		albumDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		addAlbumButton = new JButton();
		albumDateLabel = new JLabel();

		// elements for artist
		artistNameTextField = new JTextField();
		artistNameLabel = new JLabel();
		addArtistButton = new JButton();
		
		// elements for song
		songTitleTextField = new JTextField();
		songTitleLabel = new JLabel();
		songDurationTextField = new JTextField();
		songDurationLabel = new JLabel();
		addSongButton = new JButton();
		
		// elements for playlist
		playlistNameTextField = new JTextField();
		playlistNameLabel = new JLabel();
		addPlaylistButton = new JButton();
		addSongToPlaylistButton = new JButton();
		
		// elements for location
		locationNameTextField = new JTextField();
		locationNameLabel = new JLabel();
		locationVolumeLabel = new JSlider(JSlider.HORIZONTAL, VOL_MIN, VOL_MAX, VOL_INIT);
		locationVolumeLabel.setMajorTickSpacing(10);
		locationVolumeLabel.setMinorTickSpacing(1);
		locationVolumeLabel.setPaintTicks(true);
		locationVolumeLabel.setPaintLabels(true);
		muteButton = new JButton();
		unMuteButton = new JButton();
		addLocationButton = new JButton();
		
		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("HAS - Home Audio System");

		albumLabel.setText("Select Album:");
		albumTitleLabel.setText("Title:");
		albumGenreLabel.setText("Genre:");
		albumDateLabel.setText("Release Date:");
		addAlbumButton.setText("Add Album");
		addAlbumButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addAlbumButtonActionPerformed(evt);
			}
		});
		
		artistLabel.setText("Select Artist:");
		artistNameLabel.setText("Name:");
		addArtistButton.setText("Add Artist");
		addArtistButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addArtistButtonActionPerformed(evt);
			}
		});
		
		songLabel.setText("Select Song:");
		songTitleLabel.setText("Title:");
		songDurationLabel.setText("Duration:");
		addSongButton.setText("Add Song");
		addSongButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addSongButtonActionPerformed(evt);
			}
		});

		playlistLabel.setText("Select Playlist:");
		playlistNameLabel.setText("Name:");
		addPlaylistButton.setText("Add Playlist");
		addPlaylistButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addPlaylistButtonActionPerformed(evt);
			}
		});
		addSongToPlaylistButton.setText("Add Song to Playlist");
		addSongToPlaylistButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addSongToPlaylistButtonActionPerformed(evt);
			}
		});
		
		locationLabel.setText("Select Location:");
		locationNameLabel.setText("Name:");
		Font font = new Font("Volume", Font.ITALIC, 10);
		locationVolumeLabel.setFont(font);
		muteButton.setText("Mute");
		muteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				muteButtonActionPerformed(evt);
			}
		});	
		unMuteButton.setText("Unmute");
		unMuteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				unMuteButtonActionPerformed(evt);
			}
		});	
		addLocationButton.setText("Add Location");
		addLocationButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addLocationButtonActionPerformed(evt);
			}
		});
		
		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(albumLabel)
								.addComponent(albumTitleLabel)
								.addComponent(albumGenreLabel)
								.addComponent(albumDateLabel)
								.addComponent(playlistLabel)
								.addComponent(playlistNameLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(albumList)
								.addComponent(albumTitleTextField, 200, 200, 400)
								.addComponent(albumGenreTextField, 200, 200, 400)
								.addComponent(albumDatePicker)
								.addComponent(addAlbumButton)
								.addComponent(playlistList)
								.addComponent(playlistNameTextField, 200, 200, 400)
								.addComponent(addPlaylistButton)
								.addComponent(addSongToPlaylistButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(artistLabel)
								.addComponent(artistNameLabel)
								.addComponent(locationLabel)
								.addComponent(locationNameLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(artistList)
								.addComponent(artistNameTextField, 200, 200, 400)
								.addComponent(addArtistButton)
								.addComponent(locationList)
								.addComponent(locationNameTextField, 200, 200, 400)
								.addComponent(locationVolumeLabel)
								.addComponent(muteButton)
								.addComponent(unMuteButton)
								.addComponent(addLocationButton))					
						.addGroup(layout.createParallelGroup()		
								.addComponent(songLabel)
								.addComponent(songTitleLabel)
								.addComponent(songDurationLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(songList)
								.addComponent(songTitleTextField, 200, 200, 400)
								.addComponent(songDurationTextField, 200, 200, 400)
								.addComponent(addSongButton))
						
				));

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {albumLabel, artistLabel, songLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {albumTitleTextField, albumGenreTextField, addAlbumButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {artistNameTextField, addArtistButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {songTitleTextField, songDurationTextField, addSongButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {playlistLabel, locationLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {playlistNameTextField, addPlaylistButton, addSongToPlaylistButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {locationNameTextField, locationVolumeLabel, muteButton, unMuteButton, addLocationButton});
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addComponent(albumLabel)
						.addComponent(albumList)
						.addComponent(artistLabel)
						.addComponent(artistList)
						.addComponent(songLabel)
						.addComponent(songList))
				.addGroup(layout.createParallelGroup()
						.addComponent(albumTitleLabel)
						.addComponent(albumTitleTextField)
						.addComponent(artistNameLabel)
						.addComponent(artistNameTextField)
						.addComponent(songTitleLabel)
						.addComponent(songTitleTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(songDurationLabel)
						.addComponent(songDurationTextField))
				.addGroup(layout.createParallelGroup()		
						.addComponent(albumGenreLabel)
						.addComponent(albumGenreTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(albumDateLabel)
						.addComponent(albumDatePicker))
				.addGroup(layout.createParallelGroup()
						.addComponent(addAlbumButton)
						.addComponent(addArtistButton)
						.addComponent(addSongButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(playlistLabel)
						.addComponent(playlistList)
						.addComponent(locationLabel)
						.addComponent(locationList))
				.addGroup(layout.createParallelGroup()
						.addComponent(playlistNameLabel)
						.addComponent(playlistNameTextField)
						.addComponent(locationNameLabel)
						.addComponent(locationNameTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(addPlaylistButton)
						.addComponent(locationVolumeLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(addSongToPlaylistButton)
						.addComponent(muteButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(unMuteButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(addLocationButton))
				);
		pack();
	}

	private void refreshData() {
		HAS has = HAS.getInstance();
		// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// Album list
			albums = new HashMap<Integer, Album>();
			albumList.removeAllItems();
			Iterator<Album> abIt = has.getAlbums().iterator();
			Integer index = 0;
			while (abIt.hasNext()) {
				Album ab = abIt.next();
				albums.put(index, ab);
				if (ab.getSongs().isEmpty())
					albumList.addItem(ab.getTitle() + " (Empty!)");
				else 
					albumList.addItem(ab.getTitle());
				index++;
			}
			selectedAlbum = -1;
			albumList.setSelectedIndex(selectedAlbum);
			
			// artist list
			artists = new HashMap<Integer, Artist>();
			artistList.removeAllItems();
			Iterator<Artist> aIt = has.getArtists().iterator();
			index = 0;
			while (aIt.hasNext()) {
				Artist a = aIt.next();
				artists.put(index, a);
				if (a.getSongs().isEmpty())
					artistList.addItem(a.getName() + " (Empty!)");
				else
					artistList.addItem(a.getName());
				index++;
			}
			selectedArtist = -1;
			artistList.setSelectedIndex(selectedArtist);
			
			// song list
			songs = new HashMap<Integer, Song>();
			songList.removeAllItems();
			Iterator<Song> sIt = has.getSongs().iterator();
			index = 0;
			while (sIt.hasNext()) {
				Song s = sIt.next();
				songs.put(index, s);
				songList.addItem(s.getTitle());
				index++;
			}
			selectedSong = -1;
			songList.setSelectedIndex(selectedSong);		
			
			// playlist list
			playlists = new HashMap<Integer, Playlist>();
			playlistList.removeAllItems();
			Iterator<Playlist> plIt = has.getPlaylists().iterator();
			index = 0;
			while (plIt.hasNext()) {
				Playlist pl = plIt.next();
				playlists.put(index, pl);
				if (pl.getSongs().isEmpty())
					playlistList.addItem(pl.getName() + " (Empty!)");
				else
					playlistList.addItem(pl.getName());
				index++;
			}
			selectedPlaylist = -1;
			playlistList.setSelectedIndex(selectedPlaylist);

			// location list
			locations = new HashMap<Integer, Location>();
			locationList.removeAllItems();
			Iterator<Location> lIt = has.getLocations().iterator();
			index = 0;
			while (lIt.hasNext()) {
				Location l = lIt.next();
				locations.put(index, l);
				if (l.getSongs().isEmpty())
					locationList.addItem(l.getName() + " (Empty!)");
				else
					locationList.addItem(l.getName());
				index++;
			}
			selectedLocation = -1;
			locationList.setSelectedIndex(selectedLocation);

			// album
			albumTitleTextField.setText("");
			albumDatePicker.getModel().setValue(null);
			// artist
			artistNameTextField.setText("");
			// song
			songTitleTextField.setText("");
			songDurationTextField.setText("");
			songDurationTextField.setToolTipText("mm:ss");
			// playlist
			playlistNameTextField.setText("");
			// location
			locationNameTextField.setText("");

		}

		// this is needed because the size of the window change depending on whether an error message is shown or not
		pack();
	}
	
	private void addAlbumButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// call the controller
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		error = null;
		try {
			hasc.addAlbum(albumTitleTextField.getText(), String.valueOf(albumGenreTextField.getSelectedItem()), (java.sql.Date) albumDatePicker.getModel().getValue());
		} catch (InvalidInputException e) {
			error = e.getMessage();
		} 
		// update visuals
		refreshData();
	}
	private void addArtistButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// call the controller
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		error = null;
		try {
			hasc.addArtist(artistNameTextField.getText());
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		// update visuals
		refreshData();
	}
	private void addSongButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// call the controller
		HAS has = HAS.getInstance();
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		error = null;
		try {
			hasc.addSong(songTitleTextField.getText(), songDurationTextField.getText(), has.getAlbum(albumList.getSelectedIndex()), has.getArtist(artistList.getSelectedIndex()));
		} catch (InvalidInputException e) {
			error = e.getMessage();
		} catch (ArrayIndexOutOfBoundsException exception) {
			if (albumList.getSelectedIndex() == -1) {
				error = "Song album cannot be empty! ";
			}
			if (artistList.getSelectedIndex() == -1) {
				error = error + "Song artist cannot be empty! ";
			}
		}
		
		// update visuals
		refreshData();
	}
	
	private void addPlaylistButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// call the controller
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		error = null;
		try {
			hasc.addPlaylist(playlistNameTextField.getText());
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		// update visuals
		refreshData();
	}

	private void muteButtonActionPerformed(java.awt.event.ActionEvent evt) {

		HAS has = HAS.getInstance();
		HomeAudioSystemController hasc = new HomeAudioSystemController();

		int volume = has.getLocation(locationList.getSelectedIndex()).getVolume();

		try {
			hasc.muteLocation(has.getLocation(locationList.getSelectedIndex()), 0, volume);
		} catch (ArrayIndexOutOfBoundsException exception) {
			if (locationList.getSelectedIndex() == -1) {
				error = "location cannot be empty! ";
			}
		}

		refreshData();
	}

	private void unMuteButtonActionPerformed(java.awt.event.ActionEvent evt) {

		HAS has = HAS.getInstance();
		HomeAudioSystemController hasc = new HomeAudioSystemController();

		int volume = has.getLocation(locationList.getSelectedIndex()).getBeforeMuted();

		try {
			hasc.muteLocation(has.getLocation(locationList.getSelectedIndex()), volume, 0);
		} catch (ArrayIndexOutOfBoundsException exception) {
			if (locationList.getSelectedIndex() == -1) {
				error = "location cannot be empty! ";
			}
		}

		refreshData();
	}
	private void addLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// call the controller
		
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		error = null;
		
		try {
			hasc.addLocation(locationNameTextField.getText(), locationVolumeLabel.getValue());
		} catch (InvalidInputException e) {
			error = e.getMessage();
		} catch (NumberFormatException e){
			error = "This is not number";
		}
		//update visuals
		refreshData();
	}
	
	private void addSongToPlaylistButtonActionPerformed(java.awt.event.ActionEvent evt) {
		HAS has = HAS.getInstance();
		HomeAudioSystemController hasc = new HomeAudioSystemController();
		try {
			hasc.addSongToPlaylist(has.getSong(songList.getSelectedIndex()), has.getPlaylist(playlistList.getSelectedIndex()));
		} catch (ArrayIndexOutOfBoundsException exception) {
			if (playlistList.getSelectedIndex() == -1) {
				error = "Playlist cannot be empty! ";
			}
			if (songList.getSelectedIndex() == -1) {
				error = error + "Song cannot be empty! ";
			}
		}
		
		refreshData();
	}

}
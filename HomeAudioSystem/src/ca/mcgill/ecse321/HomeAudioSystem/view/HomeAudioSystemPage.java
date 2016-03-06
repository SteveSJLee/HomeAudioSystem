package ca.mcgill.ecse321.HomeAudioSystem.view;

import java.awt.Color;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.HomeAudioSystem.controller.HomeAudioSystemController;
import ca.mcgill.ecse321.HomeAudioSystem.controller.InvalidInputException;
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
//	private JTextField albumGenreTextField;
//	private JLabel albumGenreLabel;
	private JDatePickerImpl albumDatePicker;
	private JLabel albumDateLabel;
	private JButton addAlbumButton;
	
	private JComboBox<String> artistList;
	private JLabel artistLabel;
	private JTextField artistNameTextField;
	private JLabel artistNameLabel;
	private JButton addArtistButton;
	
	// data elements
	private String error = null;
	private Integer selectedAlbum = -1;
	private HashMap<Integer, Album> albums;
	private Integer selectedArtist = -1;
	private HashMap<Integer, Artist> artists;

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
				selectedAlbum = cb.getSelectedIndex();
			}
		});
		artistLabel = new JLabel();
		
		// elements for album
		albumTitleTextField = new JTextField();
		albumTitleLabel = new JLabel();
		
		albumGenreTextField = new JComboBox(Genre.values());
//		albumGenreTextField = new JTextField();
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
		
		artistLabel.setText("Artist:");
		artistNameLabel.setText("Name:");
		addArtistButton.setText("Add Artist");
		addArtistButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addArtistButtonActionPerformed(evt);
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
								.addComponent(albumDateLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(albumList)
								.addComponent(albumTitleTextField, 200, 200, 400)
								.addComponent(albumGenreTextField, 200, 200, 400)
								.addComponent(albumDatePicker)
								.addComponent(addAlbumButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(artistLabel)
								.addComponent(artistNameLabel))			
						.addGroup(layout.createParallelGroup()
								.addComponent(artistList)
								.addComponent(artistNameTextField, 200, 200, 400)
								.addComponent(addArtistButton)))
				);

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {albumLabel, artistLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {albumTitleTextField, albumGenreTextField, addAlbumButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {artistNameTextField, addArtistButton});
				
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addComponent(albumLabel)
						.addComponent(albumList)
						.addComponent(artistLabel)
						.addComponent(artistList))
				.addGroup(layout.createParallelGroup()
						.addComponent(albumTitleLabel)
						.addComponent(albumTitleTextField)
						.addComponent(artistNameLabel)
						.addComponent(artistNameTextField))
				.addGroup(layout.createParallelGroup()		
						.addComponent(albumGenreLabel)
						.addComponent(albumGenreTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(albumDateLabel)
						.addComponent(albumDatePicker))
				.addGroup(layout.createParallelGroup()
						.addComponent(addAlbumButton)
						.addComponent(addArtistButton))
						
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
				artistList.addItem(a.getName());
				index++;
			}
			selectedArtist = -1;
			artistList.setSelectedIndex(selectedArtist);		
			// album
			albumTitleTextField.setText("");
//			albumGenreTextField.setText("");
			albumDatePicker.getModel().setValue(null);
			// artist
			artistNameTextField.setText("");
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



}
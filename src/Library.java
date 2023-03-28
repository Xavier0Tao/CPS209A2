/**
 * Name: Tao Wang
 * Student Id:501152907
 */

import java.util.ArrayList;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists;
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{
		switch (content.getType()) {
			case Song.TYPENAME -> {
				if (songs.contains((Song) content)) {
					throw new DownloadFailureException("Song already downloaded");
				}
				songs.add((Song) content);
			}
			case AudioBook.TYPENAME -> {
				if (audiobooks.contains((AudioBook) content)) {
					throw new DownloadFailureException("Audiobook already downloaded");
				}
				audiobooks.add((AudioBook) content);
			}
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{

		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();
		}


	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{

	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++) {
			int index = i + 1;
			System.out.println(index + ". " + playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> artists = new ArrayList<>();
		for (Song song : songs) {
			if (artists.contains(song.getArtist())){ continue;}
			artists.add(song.getArtist());
		}

		for (int index = 0; index < artists.size(); index++) {
			System.out.println((index + 1) + ". " + artists.get(index));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public boolean deleteSong(int index)
	{
		int location = index - 1;
		Song song = songs.get(location);

		for (Playlist playlist : playlists) {
			ArrayList<AudioContent> content = playlist.getContent();
			for (int j = 0; j < content.size(); j++) {
				AudioContent audioContent = content.get(j);
				if (audioContent instanceof Song && song.equals(audioContent)) {
					content.remove(j);
					break;
				}
			}
		}

		songs.remove(location);

		return true;
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		songs.sort(new Comparator<Song>() {
			@Override
			public int compare(Song o1, Song o2) {
				return o1.getYear() - o2.getYear();
			}
		});
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator 
	{
		
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort()
		songs.sort(new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		@Override
		public int compare(Song o1, Song o2) {
			return o1.getLength() - o2.getLength();
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		songs.sort(new Comparator<Song>() {
			@Override
			public int compare(Song o1, Song o2) {
				return o1.compareTo(o2);
			}
		});
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new SongNotFoundException("Song Not Found");
		}
		songs.get(index - 1).play();
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		if (index-1 >= audiobooks.size()) {
			throw new AudioBookNotFoundException("Book not found.");
		}

		AudioBook audioBook = audiobooks.get(index-1);
		audioBook.selectChapter(chapter-1);
		audioBook.play();

	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if (audiobooks.size() <= index-1) {
			throw new AudioBookNotFoundException("Book not found.");
		}

		AudioBook audioBook = audiobooks.get(index-1);
		audioBook.printTOC();
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		for (Playlist playlist : playlists) {
			if (playlist.getTitle().equals(title)) {
				throw new PlaylistNotFoundException("Playlist already exists.");
			}
		}

		playlists.add(new Playlist(title));
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		Playlist playlist = null;
		for (Playlist list : playlists) {
			if (list.getTitle().equals(title)) {
				playlist = list;
			}
		}

		if (playlist == null) {
			throw new PlaylistNotFoundException("Playlist not found.");
		}
		playlist.printContents();
	}

	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		Playlist playlist = null;
		for (Playlist list : playlists) {
			if (list.getTitle().equals(playlistTitle)) {
				playlist = list;
			}
		}
		if (playlist == null) {
			throw new PlaylistNotFoundException("Playlist not found.");
		}

		ArrayList<AudioContent> content = playlist.getContent();

		for (int index = 0; index < content.size(); index++) {
			System.out.print((index + 1) + ". ");
			content.get(index).play();
			System.out.println("");
		}
	}

	public Playlist getPlaylistByTitle(String title) {
		Playlist playlist = null;
		for (Playlist list : playlists) {
			if (list.getTitle().equals(title)) {
				playlist = list;
			}
		}
		if (playlist == null) {
			errorMsg = "Playlist not found.";
			return null;
		}

		return playlist;
	}

	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		Playlist playlist = getPlaylistByTitle(playlistTitle);

		if (playlist == null) throw new PlaylistNotFoundException("Playlist not found.");

		playlist.getContent().get(indexInPL - 1).play();
	}

	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle) {
		Playlist playlist = null;
		for (Playlist listItem : playlists) {
			if (listItem.getTitle().equals(playlistTitle)) {
				playlist = listItem;
				break;
			}
		}

		if (playlist == null) throw new PlaylistNotFoundException("Playlist not found.");

		switch (type.toUpperCase()) {
			case Song.TYPENAME -> {
				playlist.addContent(songs.get(index-1));
			}
			case AudioBook.TYPENAME -> playlist.addContent(audiobooks.get(index-1));
		}

	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		Playlist playlist = getPlaylistByTitle(title);
		ArrayList<AudioContent> content = playlist.getContent();

		if (index-1 > 0 && index-1 < content.size()) {
			content.remove(index - 1);
		}
	}
	
}

//DONE Make custom exception classes
//DONE Change the return type of the functions in Library class to void
class AudioContentNotFoundException extends RuntimeException {
	public AudioContentNotFoundException(String error) {
		super(error);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}

class DownloadFailureException extends RuntimeException {
	public DownloadFailureException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}

class SongNotFoundException extends RuntimeException {
	public SongNotFoundException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}

class AudioBookNotFoundException extends RuntimeException {
	public AudioBookNotFoundException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}

class PlaylistNotFoundException extends RuntimeException {
	public PlaylistNotFoundException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}


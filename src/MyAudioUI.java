/**
 * Name: Tao Wang
 * Student Id:501152907
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)
//DONE try catch the errors thrown by the Library, use a single try to surround all if statements except for the download( it needs separate try catch)

public class MyAudioUI {
	public static void main(String[] args) {
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();

		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine()) {
			String action = scanner.nextLine();
			try {
				if (action != null) action = action.trim();
				if (action == null || action.equals("")) {
					System.out.print("\n>");
					continue;
				} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;
					//TODO SEARCHA input artist or author string, return index and info of all audio content
					//TODO SEARCHG input genre string, print all indices and info of all songs with the genre
					//TODO DOWNLOADA input artist or author string,download all songs or audiobooks with the artists to mylobrary
					//TODO DOWNLOADG download all songs with the input genre
				else if (action.equalsIgnoreCase("SEARCH")) {
					System.out.print("Title ");
					//TODO return with the index and the info
				} else if (action.equalsIgnoreCase("STORE"))    // List all songs
				{
					store.listAll();
				} else if (action.equalsIgnoreCase("SONGS"))    // List all songs
				{
					mylibrary.listAllSongs();
				} else if (action.equalsIgnoreCase("BOOKS"))    // List all songs
				{
					mylibrary.listAllAudioBooks();
				} else if (action.equalsIgnoreCase("PODCASTS"))    // List all songs
				{
					mylibrary.listAllPodcasts();
				} else if (action.equalsIgnoreCase("ARTISTS"))    // List all songs
				{
					mylibrary.listAllArtists();
				} else if (action.equalsIgnoreCase("PLAYLISTS"))    // List all play lists
				{
					mylibrary.listAllPlaylists();
				}
				// Download audiocontent (song/audiobook/podcast) from the store
				// Specify the index of the content
				else if (action.equalsIgnoreCase("DOWNLOAD")) {
					int index = 0;

					System.out.print("Store Content #: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					try {
						mylibrary.download(store.getContent(index));
					} catch (NullPointerException exception) {
						System.out.println("Index out of bound of Store!!");
					}

				}
				// Get the *library* index (index of a song based on the songs list)
				// of a song from the keyboard and play the song
				else if (action.equalsIgnoreCase("PLAYSONG")) {
					// Print error message if the song doesn't exist in the library
					System.out.print("Song number:");
					if (scanner.hasNextInt()) {
						mylibrary.playSong(scanner.nextInt());
					}
				}
				// Print the table of contents (TOC) of an audiobook that
				// has been downloaded to the library. Get the desired book index
				// from the keyboard - the index is based on the list of books in the library
				else if (action.equalsIgnoreCase("BOOKTOC")) {
					// Print error message if the book doesn't exist in the library
					System.out.print("Audio Book Number:");
					if (scanner.hasNextInt()) {
						int index = scanner.nextInt();
						mylibrary.printAudioBookTOC(index);
					}
				}
				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) {
					System.out.print("Audio Book Number: ");
					int bookIndex = 0;
					if (scanner.hasNextInt()) {
						bookIndex = scanner.nextInt();
					}
					System.out.print("Chapter: ");
					int chapterIndex = 0;
					if (scanner.hasNextInt()) chapterIndex = scanner.nextInt();
					mylibrary.playAudioBook(bookIndex, chapterIndex);
				}
				// Print the episode titles for the given season of the given podcast
				// In addition to the podcast index from the list of podcasts,
				// read the season number from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PODTOC")) {

				}
				// Similar to playsong above except for podcast
				// In addition to the podcast index from the list of podcasts,
				// read the season number and the episode number from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPOD")) {

				}
				// Specify a playlist title (string)
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) {
					String title = null;
					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine()) {
						mylibrary.playPlaylist(scanner.nextLine());
					}
				}
				// Specify a playlist title (string)
				// Read the index of a song/audiobook/podcast in the playist from the keyboard
				// Play all the audio content
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPL")) {
					String title = null;
					int contentNumber = 0;

					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine()) {
						title = scanner.nextLine();
					}

					System.out.print("Content Number: ");
					if (scanner.hasNextLine()) {
						mylibrary.playPlaylist(title, scanner.nextInt());
					}
				}

				// Delete a song from the list of songs in mylibrary and any play lists it belongs to
				// Read a song index from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELSONG")) {
					System.out.print("Library Song: ");
					int songNumber = 0;

					if (scanner.hasNextInt()) {
						songNumber = scanner.nextInt();
					}

					if (!mylibrary.deleteSong(songNumber)) System.out.println(mylibrary.getErrorMessage());
				}
				// Read a title string from the keyboard and make a playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("MAKEPL")) {
					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine()) {
						mylibrary.makePlaylist(scanner.nextLine());
					}
				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PRINTPL"))    // print playlist content
				{
					System.out.print("Playlist Title: ");
					String title = null;
					if (scanner.hasNextLine()) {
						mylibrary.printPlaylist(scanner.nextLine());
					}

				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
				// Read the playlist title, the type of content ("song" "audiobook" "podcast")
				// and the index of the content (based on song list, audiobook list etc) from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("ADDTOPL")) {
					String playlistTitle = null, contentType = null;
					int libraryContent = 0;

					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine()) {
						playlistTitle = scanner.nextLine();
					}

					System.out.println("Content Type [SONG,PODCAST,AUDIOBOOK]: ");
					if (scanner.hasNextLine()) {
						contentType = scanner.nextLine();
					}

					System.out.println("Library Content #: ");
					if (scanner.hasNextInt()) {
						mylibrary.addContentToPlaylist(contentType, scanner.nextInt(), playlistTitle);
					}

				}
				// Delete content from play list based on index from the playlist
				// Read the playlist title string and the playlist index
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELFROMPL")) {
					String title = null;
					int contentNumber = 0;

					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine()) {
						title = scanner.nextLine();
					}

					System.out.print("Content Number: ");
					if (scanner.hasNextLine()) {
						contentNumber = scanner.nextInt();
					}
					mylibrary.delContentFromPlaylist(contentNumber, title);

				} else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				} else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				} else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				} else {
					System.out.println("Invalid instruction!");
				}
			} catch (DownloadFailureException e) {
				System.out.println(e.getMessage());
			} catch (SongNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (AudioBookNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (PlaylistNotFoundException e) {
				System.out.println(e.getMessage());
			}

			System.out.print("\n>");
		}
	}
}

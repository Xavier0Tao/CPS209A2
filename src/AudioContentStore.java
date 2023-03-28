/**
 * Name: Tao Wang
 * Student Id:501152907
 */

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore {
	private ArrayList<AudioContent> contents;
	private HashMap<String, Integer> titleMap = new HashMap<>();
	private HashMap<String, ArrayList<Integer>> artistMap = new HashMap<>();
	private HashMap<String, ArrayList<Integer>> genreMap = new HashMap<>();

	public AudioContentStore() {
		/*try {
			this.contents = initializationFromFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/

		//TODO Replace this file with code that reads the information form store.txt based on the pattern provided in the doc,
		// make a private method dedicated to reading data, and return the arraylist.
		// Call the private method in constructor and wrap it using try catch(IOException e) print the error msg and System.exit(1)

		this.contents = initialization();
		// Create a podcast object if you are doing the bonus see the makeSeasons() method below
		// It is currently commented out. It makes use of a class Season you may want to also create
		// or change it to something else

		// First Map
		for (int i = 0; i < contents.size(); i++) {
			String type = contents.get(i).getType();
			switch (contents.get(i).getType()) {
				case Song.TYPENAME,AudioBook.TYPENAME -> {
					titleMap.put(contents.get(i).getTitle(), i);
				}
			}
		}

		// Test the Map
		/*for (Map.Entry<String, Integer> stringIntegerEntry : titleMap.entrySet()) {
			System.out.println(stringIntegerEntry.getKey() + "   " + stringIntegerEntry.getValue());
		}*/


		//TODO Create three maps in the constructor. Used to search for audio content titles.
		// The first one: Title(String) -> Index(int) map the title of a song/book to the index into the contents list.
		// Second : Artist/Authore(Stirng)-> Index Arraylist
		// Third : Genre(String) -> Index Arraylist

		// Second Map
		for (int i = 0; i < contents.size(); i++) {
			AudioContent content = contents.get(i);
			switch (content.getType()) {
				case Song.TYPENAME -> {
					Song song = (Song) content;
					ArrayList<Integer> artistIndices = artistMap.getOrDefault(song.getArtist(), new ArrayList<Integer>());
					artistIndices.add(i);
					artistMap.put(song.getArtist(), artistIndices);
				}
				case AudioBook.TYPENAME -> {
					AudioBook audioBook = (AudioBook) content;
					ArrayList<Integer> artistIndices = artistMap.getOrDefault(audioBook.getAuthor(), new ArrayList<Integer>());
					artistIndices.add(i);
					artistMap.put(audioBook.getAuthor(), artistIndices);
				}
			}
		}

		//Third Map
		for (int i = 0; i < contents.size(); i++) {
			if (contents.get(i).getType().equals(Song.TYPENAME)) {
				Song song = (Song) contents.get(i);
				ArrayList<Integer> genreIndices = genreMap.getOrDefault(song.getGenre().name(), new ArrayList<Integer>());
				genreIndices.add(i);
				genreMap.put(song.getGenre().name(), genreIndices);
			}
		}

		// Test the Map
		/*for (Map.Entry<String, ArrayList<Integer>> stringArrayListEntry : genreMap.entrySet()) {
			System.out.println(stringArrayListEntry.getKey() + "  " + stringArrayListEntry.getValue());
		}*/

	}

	private ArrayList<AudioContent> initialization() {
		ArrayList<AudioContent> audioContents = new ArrayList<>();

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("store.txt")));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				switch (line) {
					case "SONG"->{
						String id = bufferedReader.readLine();
						String title = bufferedReader.readLine();
						int year = Integer.parseInt(bufferedReader.readLine());
						int length = Integer.parseInt(bufferedReader.readLine());
						String artist = bufferedReader.readLine();
						String composer = bufferedReader.readLine();
						String genre = bufferedReader.readLine();
						int numberOfLyrics = Integer.parseInt(bufferedReader.readLine());
						StringBuilder lyrics = new StringBuilder();
						for (int i = 0; i < numberOfLyrics; i++) {
							lyrics.append(bufferedReader.readLine());
						}
						String audioFile = lyrics.toString();
						Song song = new Song(title, year, id, Song.TYPENAME, audioFile, length, artist, composer, Song.Genre.valueOf(genre), audioFile);
						audioContents.add(song);
					}

					case "AUDIOBOOK" -> {
						String id = bufferedReader.readLine();
						String title = bufferedReader.readLine();
						int year = Integer.parseInt(bufferedReader.readLine());
						int length = Integer.parseInt(bufferedReader.readLine());
						String author = bufferedReader.readLine();
						String narrator = bufferedReader.readLine();
						int numberOfChapters = Integer.parseInt(bufferedReader.readLine());

						ArrayList<String> chapterTitles = new ArrayList<>();
						ArrayList<String> chapterContents = new ArrayList<>();
						for (int i = 0; i < numberOfChapters; i++) {
							chapterTitles.add(bufferedReader.readLine());
						}

						for (int i = 0; i < numberOfChapters; i++) {
							int numberOfContents = Integer.parseInt(bufferedReader.readLine());
							StringBuilder content = new StringBuilder();

							for (int j = 0; j < numberOfContents; j++) {
								content.append(bufferedReader.readLine());
							}
							chapterContents.add(content.toString());
						}
						audioContents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapterTitles, chapterContents));
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return audioContents;
	}

	private ArrayList<AudioContent> initializationFromFile() throws FileNotFoundException {


		Scanner scanner = new Scanner(new File("D:\\Desktop_backup\\CPS209\\Assignment1\\src\\store.txt"));
		ArrayList<AudioContent> audioContents = new ArrayList<>();

		while (scanner.hasNextLine()) {
			String line1 = scanner.nextLine();
			try {
				switch (line1) {
					case "SONG" -> {
						String id = scanner.nextLine();
						String title = scanner.nextLine();
						int year = Integer.parseInt(scanner.nextLine());
						int length = Integer.parseInt(scanner.nextLine());
						String artist = scanner.nextLine();
						String composer = scanner.nextLine();
						String genre = scanner.nextLine();
						System.out.println(genre);
						int numberOfLyrics = scanner.hasNextLine() ? Integer.parseInt(scanner.nextLine()) : scanner.nextInt();
						StringBuilder lyrics = new StringBuilder();
						for (int i = 0; i < numberOfLyrics; i++) lyrics.append(scanner.nextLine());
						String audioFile = lyrics.toString();
						Song song = new Song(title, year, id, Song.TYPENAME, audioFile, length, artist, composer, Song.Genre.valueOf(genre), audioFile);
						audioContents.add(song);
					}
					case "AUDIOBOOK" -> {
						String id = scanner.nextLine();
						String title = scanner.nextLine();
						int year = Integer.parseInt(scanner.nextLine());
						int length = Integer.parseInt(scanner.nextLine());
						String author = scanner.nextLine();
						String narrator = scanner.nextLine();
						int numberOfChapters = Integer.parseInt(scanner.nextLine());

						ArrayList<String> chapterTitles = new ArrayList<>();
						ArrayList<String> chapterContents = new ArrayList<>();
						for (int i = 0; i < numberOfChapters; i++) {
							chapterTitles.add(scanner.nextLine());
						}

						for (int i = 0; i < numberOfChapters; i++) {
							int numberOfContents = Integer.parseInt(scanner.nextLine());
							StringBuilder content = new StringBuilder();

							for (int j = 0; j < numberOfContents; j++) {
								content.append(scanner.nextLine());
							}
							chapterContents.add(content.toString());
						}
						audioContents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapterTitles, chapterContents));
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return audioContents;
	}

	public AudioContent getContent(int index) {
		if (index < 1 || index > contents.size()) {
			return null;
		}
		return contents.get(index - 1);
	}

	public void listAll() {

		for (int i = 0; i < contents.size(); i++) {
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	private ArrayList<String> makeHPChapterTitles() {
		ArrayList<String> titles = new ArrayList<String>();
		titles.add("The Riddle House");
		titles.add("The Scar");
		titles.add("The Invitation");
		titles.add("Back to The Burrow");
		return titles;
	}

	private ArrayList<String> makeHPChapters() {
		ArrayList<String> chapters = new ArrayList<String>();
		chapters.add("In which we learn of the mysterious murders\r\n"
				+ " in the Riddle House fifty years ago, \r\n"
				+ "how Frank Bryce was accused but released for lack of evidence, \r\n"
				+ "and how the Riddle House fell into disrepair. ");
		chapters.add("In which Harry awakens from a bad dream, \r\n"
				+ "his scar burning, we recap Harry's previous adventures, \r\n"
				+ "and he writes a letter to his godfather.");
		chapters.add("In which Dudley and the rest of the Dursleys are on a diet,\r\n"
				+ " and the Dursleys get letter from Mrs. Weasley inviting Harry to stay\r\n"
				+ " with her family and attend the World Quidditch Cup finals.");
		chapters.add("In which Harry awaits the arrival of the Weasleys, \r\n"
				+ "who come by Floo Powder and get trapped in the blocked-off fireplace\r\n"
				+ ", blast it open, send Fred and George after Harry's trunk,\r\n"
				+ " then Floo back to the Burrow. Just as Harry is about to leave, \r\n"
				+ "Dudley eats a magical toffee dropped by Fred and grows a huge purple tongue. ");
		return chapters;
	}

	private ArrayList<String> makeMDChapterTitles() {
		ArrayList<String> titles = new ArrayList<String>();
		titles.add("Loomings.");
		titles.add("The Carpet-Bag.");
		titles.add("The Spouter-Inn.");
		return titles;
	}

	private ArrayList<String> makeMDChapters() {
		ArrayList<String> chapters = new ArrayList<String>();
		chapters.add("Call me Ishmael. Some years ago never mind how long precisely having little\r\n"
				+ " or no money in my purse, and nothing particular to interest me on shore,\r\n"
				+ " I thought I would sail about a little and see the watery part of the world.");
		chapters.add("stuffed a shirt or two into my old carpet-bag, tucked it under my arm, \r\n"
				+ "and started for Cape Horn and the Pacific. Quitting the good city of old Manhatto, \r\n"
				+ "I duly arrived in New Bedford. It was a Saturday night in December.");
		chapters.add("Entering that gable-ended Spouter-Inn, you found yourself in a wide, \r\n"
				+ "low, straggling entry with old-fashioned wainscots, \r\n"
				+ "reminding one of the bulwarks of some condemned old craft.");
		return chapters;
	}

	private ArrayList<String> makeSHChapterTitles() {
		ArrayList<String> titles = new ArrayList<String>();
		titles.add("Prologue");
		titles.add("Chapter 1");
		titles.add("Chapter 2");
		titles.add("Chapter 3");
		return titles;
	}

	private ArrayList<String> makeSHChapters() {
		ArrayList<String> chapters = new ArrayList<String>();
		chapters.add("The gale tore at him and he felt its bite deep within\r\n"
				+ "and he knew that if they did not make landfall in three days they would all be dead");
		chapters.add("Blackthorne was suddenly awake. For a moment he thought he was dreaming\r\n"
				+ "because he was ashore and the room unbelieveable");
		chapters.add("The daimyo, Kasigi Yabu, Lord of Izu, wants to know who you are,\r\n"
				+ "where you come from, how ou got here, and what acts of piracy you have committed.");
		chapters.add("Yabu lay in the hot bath, more content, more confident than he had ever been in his life.");
		return chapters;
	}

	// Podcast Seasons
		/*
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ " smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ " very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ " In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ " and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		}
		*/
}

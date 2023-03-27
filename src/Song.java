import java.util.Objects;

/**
 * Name: Tao Wang
 * Student Id:501152907
 */

/*
 * A Song is a type of AudioContent. A Song has extra fields such as Artist (person(s) singing the song) and composer 
 */
public class Song extends AudioContent // implement the Comparable interface
{
	public static final String TYPENAME =	"SONG";
	
	public static enum Genre {POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL}; 
	private String artist; 		// Can be multiple names separated by commas
	private String composer; 	// Can be multiple names separated by commas
	private Genre  genre; 
	private String lyrics;
	
	
	
	public Song(String title, int year, String id, String type, String audioFile, int length, String artist,
			String composer, Song.Genre genre, String lyrics)
	{
		// Make use of the constructor in the super class AudioContent. 
		// Initialize additional Song instance variables.
		super(title, year, id, type, audioFile, length);
		this.artist = artist;
		this.composer = composer;
		this.genre = genre;
		this.lyrics = lyrics;
	}
	
	public String getType()
	{
		return TYPENAME;
	}
	
	// Print information about the song. First print the basic information of the AudioContent 
	// by making use of the printInfo() method in superclass AudioContent and then print artist, composer, genre 
	public void printInfo()
	{
		super.printInfo();
		System.out.println("Artist: " + this.artist + " Composer: " + this.composer + " Genre: " + this.genre);
	}
	
	// Play the song by setting the audioFile to the lyrics string and then calling the play() method of the superclass
	public void play()
	{
		super.setAudioFile(lyrics);
		super.play();
	}
	
	public String getComposer()
	{
		return composer;
	}
	public void setComposer(String composer)
	{
		this.composer = composer;
	}
	
	public String getArtist()
	{
		return artist;
	}
	public void setArtist(String artist)
	{
		this.artist = artist;
	}
	
	public String getLyrics()
	{
		return lyrics;
	}
	public void setLyrics(String lyrics)
	{
		this.lyrics = lyrics;
	}

	public Genre getGenre()
	{
		return genre;
	}

	public void setGenre(Genre genre)
	{
		this.genre = genre;
	}	
	
	// Two songs are equal if their AudioContent information is equal and both the composer and artists are the same
	// Make use of the superclass equals() method
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Song song = (Song) o;
		return Objects.equals(artist, song.artist) && Objects.equals(composer, song.composer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(artist, composer, genre, lyrics);
	}

	// Implement the Comparable interface
	// Compare two songs based on their title
	// This method will allow songs to be sorted alphabetically
	public int compareTo(Song other)
	{
		String thisTitle = this.getTitle();
		String otherTitle = other.getTitle();

		for (int index = 0; index < thisTitle.length() && index < otherTitle.length(); index++) {
			char charAtThis = thisTitle.charAt(index);
			char charAtOther = otherTitle.charAt(index);
			if (charAtThis > charAtOther) return 1;
			else if (charAtThis < charAtOther) return -1;
		}

		return Integer.compare(thisTitle.length(), otherTitle.length());
	}
}

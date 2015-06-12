/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grooveshark2spotify;

/**
 *
 * @author Kevin Lagacé <kevlag100@hotmail.com>
 */
public class Song
{
    private String songName;
    private String artistName;
    private String albumName;
    private String spotifyURL;
    
    /* Constructor */
    public Song(String songName, String artistName, String albumName)
    {
        this.songName = songName;
        this.artistName = artistName;
        this.albumName = albumName;
    }

    public void setSongName(String songName)
    {
        this.songName = songName;
    }

    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
    }

    public void setAlbumName(String albumName)
    {
        this.albumName = albumName;
    }
    
    public String getSongName()
    {
        return songName;
    }

    public String getArtistName()
    {
        return artistName;
    }

    public String getAlbumName()
    {
        return albumName;
    }

    public void setSpotifyURL(String url) {
        this.spotifyURL = url;
    }
    
    public String getSpotifyURL() {
        if (spotifyURL != null) {
            return spotifyURL;
        } else {
            return "";
        }
    }
    
    
    
    
}

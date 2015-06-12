/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grooveshark2spotify;

import java.util.ArrayList;

/**
 *
 * @author Kevin Lagacé <kevlag100@hotmail.com>
 */
public class Playlist
{
    private String playListName;
    ArrayList<Song> songList;
    
    public Playlist(String playListName)
    {
        this.playListName = playListName;
        songList = new ArrayList<>();
    }
    
    public boolean addSong(Song song) {
        return songList.add(song);
    }
    
    public String getName() {
        return playListName;
    }
    
    public ArrayList<Song> getSongList() {
        return new ArrayList<>(songList);
    }
    
    
}

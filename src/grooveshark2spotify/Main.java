/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grooveshark2spotify;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin Lagacé <kevlag100@hotmail.com>
 */
public class Main
{
    static ArrayList<Playlist> playlistList = new ArrayList<>();
    static String spotifyURL = "https://api.spotify.com/v1/search";
    static ArrayList spotifyTrackList = new ArrayList();

    public static void main(String[] args)
    {
        File playlistDir = new File("GroovesharkPlaylists");
        File[] playlistDirList = playlistDir.listFiles();
        if (playlistDirList != null) {
            // Iterate over every playlist File
            for (File playlistFile : playlistDirList) {
                try
                {
                    Playlist pList = new Playlist(playlistFile.getName());
                    playlistList.add(pList);
                    
                    BufferedReader br = new BufferedReader(new FileReader(playlistFile));
                    String line;
                    br.readLine(); // Gets rid of initial header line
                    
                    /* 
                     * Iterate through each line (song) in the playlist text file,
                     * gets spotify URL and save song in playlist
                     */
                    while ((line = br.readLine()) != null) {
                        Song song = txtToSong(line);
                        System.out.println("Song: " + song.getSongName());
                        System.out.println("Artist: " + song.getArtistName());
                        System.out.println("Album: " + song.getAlbumName());

                        //Sends a request with song info as query to Spotify API
                        String query = song.getSongName();
                        String result = requestTrackURL(URLEncoder.encode(query, "utf-8"));
                        
                        //Sends a request with song info as query to Spotify API
                        System.out.println("Spotify track URL: " + result);
                        
                        // Set the spotify URL to the Song and add it to the playlist
                        song.setSpotifyURL(result);
                        pList.addSong(song);
                    }
                } catch (FileNotFoundException ex)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
        
        }
        saveToFile();
    }
    
    /**
     * Converts line of song in text form from Grooveshark playlist dump file
     * 
     * @param line line of song in text form
     * @return Song object containing the song info (Song name, Artist name, Album name)
     */
    public static Song txtToSong(String line) {
        String[] songSplit = line.split("\"");
        return (new Song(songSplit[1], songSplit[3], songSplit[5]));
    }
    
    /**
     * Sends a Spotify request with a query for a single track match
     * 
     * @param query query parameters in string form
     * @see https://developer.spotify.com/web-api/search-item/ for query examples
     * @return Spotify track URL
     */
    public static String requestTrackURL(String query) {
        String result = "";
        try
        {
            URL url = new URL(spotifyURL + "?q=" + query + "&type=track&limit=1");
            System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            StringBuilder outputSB = new StringBuilder();
            String output;
            while ((output = reader.readLine()) != null) {
                outputSB.append(output);
            }
            
            String jsonString = outputSB.toString();
            
            // Split strings at every brace and look for spotify url for
            // parts with type: track
            String[] splitJSON = jsonString.split("\\{|\\}");
            for (String jsonPart : splitJSON) {
                if (jsonPart.contains("\"type\" : \"track\"")) {
                    String[] splitResult = jsonPart.split("\"uri\" : \"", 2);
                    String secondPart = splitResult[1].split("\"")[0];
                    result = secondPart;
                }
            }
        } catch (IOException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Save Spotify playlists in separate files (Additional file for songs that weren't found)
     */
    public static void saveToFile() {
        for (Playlist pList : playlistList) {
            try
            {
                BufferedWriter writer = new BufferedWriter(new FileWriter("GroovesharkPlaylists/" + pList.getName()));
                BufferedWriter writerNotFound = new BufferedWriter(new FileWriter("GroovesharkPlaylists/" + pList.getName().split(".txt")[0] + " (not found) .txt"));
                for (Song song : pList.getSongList()) {
                    String spotifyUrl = song.getSpotifyURL();
                    if (!spotifyUrl.isEmpty()) {
                        writer.write(spotifyUrl + "\n");
                    } else {
                        writerNotFound.write(song + "\n");
                    }
                }
                writer.close();
            } catch (IOException ex)
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}

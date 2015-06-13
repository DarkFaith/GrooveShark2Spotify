# GrooveShark2Spotify

1. Get your grooveshark playlist files from: http://googleglass.my/groovebackup/

2. Save all files to the GroovesharkPlaylists folder

3. Run Grooveshark2Spotify.jar (Requires Java 1.7)
    - Playlist files will be saved separately in SpotifyPlaylists folder
    - There is nothing to show progress, if you have a lot of playlists or really long playlists it may take a while since spotify has a limit on the number of requests you can make within several seconds. You can open task manager and watch until the java.exe *32 disappears, watch your spotify folder (playlists only show up once done) or run the bot from command line from within the same directory as the jar file using "java -jar Grooveshark2Spotify.jar" and watch the progress in the command prompt.
4. Create playlist with name of the desired playlist in Spotify and make sure it is selected.
    - Open the playlist file in SpotifyPlaylists in notepad
    - Copy the entire file contents
    - Set focus to spotify (click in the app) and paste (CTRL-V)

5. You should now have a populated playlist of all the songs Spotify could find from your old grooveshark playlists!

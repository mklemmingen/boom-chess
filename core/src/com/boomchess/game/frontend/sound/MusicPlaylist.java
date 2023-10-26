package com.boomchess.game.frontend.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.boomchess.game.BoomChess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicPlaylist {
    /*
     * MusicPlaylist.java is the object for the music playlist in the game Boom Chess.
     * It holds the playlist of music and the methods to play, pause, resume, and dispose of the music.
     */
    private final List<Music> songs;
    private int currentIndex = 0;
    private boolean isLooping = false;

    public MusicPlaylist() {
        /*
        * Constructor for MusicPlaylist.java
        * does not take any arguments.
        */
        songs = new ArrayList<>();
    }

    public void addSong(String fileName) {
        /*
        * addSong adds a song, given a fileName/Direction to a Music Object, to the playlist.
        * Adds a listener to go to the next song when the current song is finished.
         */
        Music song = Gdx.audio.newMusic(Gdx.files.internal(fileName));
        song.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                nextSong();
            }
        });
        songs.add(song);
    }

    public void play() {
        if (songs.size() > 0) {
            Music playSong = songs.get(currentIndex);
            playSong.play();
            playSong.setVolume(BoomChess.volume);
        }
    }

    public void pause() {
        if (songs.size() > 0) {
            songs.get(currentIndex).pause();
        }
    }

    public void resume() {
        if (songs.size() > 0) {
            songs.get(currentIndex).play();
        }
    }



    public void nextSong() {
        /*
         * nextSong goes to the next song in the playlist.
         * Responds to the isLooping variable
         */
        if (songs.size() > 0) {
            songs.get(currentIndex).stop();
            int previousIndex = currentIndex;

            // Generate a random index different from the previous one
            do {
                currentIndex = new Random().nextInt(songs.size());
            } while (currentIndex == previousIndex);

            play();
        }
    }

    public void stop() {
        if (songs.size() > 0) {
            songs.get(currentIndex).stop();
        }
    }

    public void setVolume(float volume){
        // can take but must not take a int value, if there is one, it is taken as the volume for setVolume
        if (songs.size() > 0) {
            songs.get(currentIndex).setVolume(volume);
        }
    }

    public void setLooping(boolean isLooping){
        /*
        * setLooping never lets the playlist end. It loops the playlist.
         */
        this.isLooping = isLooping;
    }

    public boolean isPlaying() {
    /*
    * isPlaying returns a boolean value of whether a song is playing.
    */
        if (songs.size() > 0) {
            return songs.get(currentIndex).isPlaying();
        }
        return false;
    }

    public void dispose() {
        for (Music song : songs) {
            song.dispose();
        }
    }
}

package com.boomchess.game.frontend.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.boomchess.game.BoomChess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playlist {
    private List<Song> songs;
    private int currentIndex;
    private boolean isLooping;

    public Playlist() {
        songs = new ArrayList<>();
        currentIndex = 0;
        isLooping = false;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }

    public void play() {
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
}

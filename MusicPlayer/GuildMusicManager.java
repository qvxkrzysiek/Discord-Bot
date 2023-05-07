package me.qvx.MusicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {

    public final AudioPlayer audioPlayer;
    public final TrackScheduler scheduler;
    public final AudioPlayerSendHandler sendHandler;
    public final MusicChatController musicChatController;

    public GuildMusicManager(AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.musicChatController = new MusicChatController();
        this.scheduler = new TrackScheduler(this.audioPlayer, this.musicChatController);
        this.audioPlayer.addListener(this.scheduler);
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);
    }

    public AudioPlayerSendHandler getSendHandler(){
        return this.sendHandler;
    }
}

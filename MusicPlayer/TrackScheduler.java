package me.qvx.MusicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.qvx.DTOs.MusicQueueElement;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {

    public final AudioPlayer audioPlayer;
    public final MusicChatController musicChatController;
    public MusicQueueElement currentTrackElement;
    public BlockingQueue<MusicQueueElement> queue;
    public boolean repeat = false;

    public TrackScheduler(AudioPlayer audioPlayer, MusicChatController musicChatController){
        this.audioPlayer = audioPlayer;
        this.queue = new LinkedBlockingQueue<>();
        this.musicChatController = musicChatController;
    }


    public void queue(AudioTrack track, MessageReceivedEvent e){
        if(this.audioPlayer.startTrack(track,true)){
            currentTrackElement = new MusicQueueElement(audioPlayer.getPlayingTrack(), e);
            musicChatController.updatePlayer(audioPlayer, e, repeat);
        } else {
            this.queue.offer(new MusicQueueElement(track,e));
            musicChatController.updateQueue(new LinkedBlockingQueue<>(queue));
        }
    }


    public void nextTrack(){
        if(queue.isEmpty()) {
            new Thread(() -> {
                try {
                    Thread.sleep(30000);
                    if(audioPlayer.getPlayingTrack() == null){
                        MusicChatController.MUSIC_TEXT_CHANNEL.getGuild().getAudioManager().closeAudioConnection();
                        MusicChatController.reinit();
                        PlayerManager.destroyINSTANCE();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            return;
        }
        currentTrackElement = queue.poll();
        System.out.println(currentTrackElement.getAudioTrack().getInfo().title);
        audioPlayer.startTrack(currentTrackElement.getAudioTrack(),false);
        musicChatController.updatePlayer(audioPlayer, currentTrackElement.getMessageEvent(), repeat);
        musicChatController.updateQueue(new LinkedBlockingQueue<>(queue));
    }

    public void pauseOrResume(){
        audioPlayer.setPaused(!audioPlayer.isPaused());
    }

    public void cycleRepeat(){
        if(repeat){
            repeat = false;
            musicChatController.updatePlayer(audioPlayer, currentTrackElement.getMessageEvent(), repeat);
        } else {
            repeat = true;
            musicChatController.updatePlayer(audioPlayer, currentTrackElement.getMessageEvent(), repeat);
        }
    }

    public void shuffle(){
        if(queue.isEmpty()) return;
        ArrayList<MusicQueueElement> list = new ArrayList<>(new LinkedBlockingQueue<>(queue));
        Collections.shuffle(list);
        queue = new LinkedBlockingQueue<>(list);
        musicChatController.updateQueue(new LinkedBlockingQueue<>(queue));
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(endReason.mayStartNext){
            if(repeat){
                audioPlayer.startTrack(track.makeClone(),false);
                return;
            }
            nextTrack();
        }
    }
    
}

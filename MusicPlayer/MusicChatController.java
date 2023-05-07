package me.qvx.MusicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.qvx.DTOs.MusicQueueElement;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;

import java.awt.*;
import java.util.concurrent.BlockingQueue;

public class MusicChatController {

    public static TextChannel MUSIC_TEXT_CHANNEL;
    private static Message EMBED_MESSAGE;
    private static Message QUEUE_MESSAGE;

    public static void init(TextChannel musicTextChannel) {
        MUSIC_TEXT_CHANNEL = musicTextChannel;
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                clearMusicChannel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Test");
        RestAction<Message> restAction = MUSIC_TEXT_CHANNEL.sendMessageEmbeds(embedBuilder.build());
        EMBED_MESSAGE = restAction.complete();
        EMBED_MESSAGE.addReaction(Emoji.fromUnicode("U+25b6")).queue();
        EMBED_MESSAGE.addReaction(Emoji.fromUnicode("U+23e9")).queue();
        EMBED_MESSAGE.addReaction(Emoji.fromUnicode("U+1f501")).queue();
        EMBED_MESSAGE.addReaction(Emoji.fromUnicode("U+1f500")).queue();
        EMBED_MESSAGE.addReaction(Emoji.fromUnicode("U+23f9")).queue();


        RestAction<Message> restAction1 = MUSIC_TEXT_CHANNEL.sendMessage("Kolejka jest aktualnie pusta");
        QUEUE_MESSAGE = restAction1.complete();
    }

    public static void reinit(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Test");
        EMBED_MESSAGE.editMessageEmbeds(embedBuilder.build()).queue();
        QUEUE_MESSAGE.editMessage("Kolejka jest aktualnie pusta").queue();
    }

    public MusicChatController() {
    }

    public void updateQueue(BlockingQueue<MusicQueueElement> queue){
        if(queue.isEmpty()){
            QUEUE_MESSAGE.editMessage("Kolejka jest aktualnie pusta").queue();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;

        stringBuilder.append("Kolejka: ").append("\n");
        while (!queue.isEmpty()) {
            MusicQueueElement element = queue.poll();
            stringBuilder.append(i).append(".) ").append(element.getTitle()).append(" ").append(element.getMessageAuthor()).append("\n");
            i++;
            if(i==11) {
                if(queue.size() != 0) stringBuilder.append("I wiecej ... ").append(queue.size());
                break;
            }
        }

        QUEUE_MESSAGE.editMessage(stringBuilder.toString()).queue();
    }

    public void updatePlayer(AudioPlayer audioPlayer, MessageReceivedEvent e, boolean repeat){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor("Odtwarzam");
        embedBuilder.setTitle(audioPlayer.getPlayingTrack().getInfo().title);
        embedBuilder.setDescription(audioPlayer.getPlayingTrack().getInfo().author);
        embedBuilder.setImage(getImage(audioPlayer.getPlayingTrack().getInfo().uri));
        if(repeat){
            embedBuilder.setFooter("Dodane przez: " + e.getAuthor().getName() + " Powtarzanie wlaczone");
        } else {
            embedBuilder.setFooter("Dodane przez: " + e.getAuthor().getName());
        }
        embedBuilder.setColor(new Color(255,0,255));
        EMBED_MESSAGE.editMessageEmbeds(embedBuilder.build()).queue();
    }

    private static void clearMusicChannel() {
        MUSIC_TEXT_CHANNEL.getHistory().retrievePast(10).queue(messages -> {
            for (Message message : messages) {
                if(!message.equals(EMBED_MESSAGE) && !message.equals(QUEUE_MESSAGE)){
                    message.delete().queue();
                }
            }
        });
    }
    private String getImage(String link){
        String token_link = link.split("=")[1];
        return "https://img.youtube.com/vi/" + token_link + "/0.jpg";
    }

    public static TextChannel getMusicTextChannel() {
        return MUSIC_TEXT_CHANNEL;
    }

    public static Message getEmbedMessage() {
        return EMBED_MESSAGE;
    }
}
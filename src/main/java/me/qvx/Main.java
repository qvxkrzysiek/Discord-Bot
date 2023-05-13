package me.qvx;

import me.qvx.ChatGPT.ChatGPTController;
import me.qvx.ChatGPT.ChatGPTEvents;
import me.qvx.MusicPlayer.Events.MusicReactionClick;
import me.qvx.MusicPlayer.Handlers.MusicChatHandler;
import me.qvx.MusicPlayer.Events.MusicEvents;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;


public class Main {

    public static void main(String[] args) throws Exception{

        ConfigLoader config = new ConfigLoader();

        System.out.println(config.getDISCORD_API_TOKEN());

        JDA jda = JDABuilder.createDefault(config.getDISCORD_API_TOKEN())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .enableCache(CacheFlag.VOICE_STATE)
                .build().awaitReady();

        ChatGPTController chat = new ChatGPTController(config.getCHATGPT_API_TOKEN());
        ChatGPTEvents ChatGPTEvents = new ChatGPTEvents(config.getCHATGPT_CHAT_ID(), chat);
        jda.addEventListener(ChatGPTEvents);

        TextChannel musicTextChannel = jda.getTextChannelById(config.getMUSIC_CHAT_ID());
        MusicChatHandler.init(musicTextChannel);
        jda.addEventListener(new MusicEvents());
        jda.addEventListener(new MusicReactionClick());

        Guild guild = jda.getGuildById("694891408569401435");
        if(guild != null){
            guild.upsertCommand("skip","Skips audio").queue();
            guild.deleteCommandById("1100218679821602939");
        }



    }
}
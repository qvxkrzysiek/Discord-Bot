package me.qvx.MusicPlayer.Commands;

import me.qvx.MusicPlayer.PlayerManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ConfigureMusicChat extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e){
        if(e.getName().equals("skip")){
            PlayerManager.getINSTANCE().getMusicManager(Objects.requireNonNull(e.getGuild())).scheduler.nextTrack();
        }
    }
}

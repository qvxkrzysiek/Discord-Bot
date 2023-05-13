package me.qvx.ChatGPT;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatGPTEvents extends ListenerAdapter {

    private final String CHAT_ID;
    private final ChatGPTController chatGPTController;

    public ChatGPTEvents(String CHAT_ID, ChatGPTController chatGPTController){
        this.CHAT_ID = CHAT_ID;
        this.chatGPTController = chatGPTController;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(!e.getAuthor().isBot()) {
            if (e.getChannel().getId().equals(CHAT_ID)) {
                String result;
                try {
                    result = chatGPTController.chatGPT(e.getMessage().getContentRaw());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(result);
                e.getChannel().sendMessage(result).queue();
            }
        }
    }
}

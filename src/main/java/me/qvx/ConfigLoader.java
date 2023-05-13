package me.qvx;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {

    private String DISCORD_API_TOKEN;
    private String CHATGPT_API_TOKEN;
    private String CHATGPT_CHAT_ID;
    private String MUSIC_CHAT_ID;

    public ConfigLoader() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("config.yml");
        Map<String, String> map = yaml.load(inputStream);
        System.out.println(map);

        DISCORD_API_TOKEN = map.get("DISCORD_API_TOKEN");
        CHATGPT_API_TOKEN = map.get("CHATGPT_API_TOKEN");
        CHATGPT_CHAT_ID = map.get("CHATGPT_CHAT_ID");
        MUSIC_CHAT_ID = map.get("MUSIC_CHAT_ID");
    }

    public String getDISCORD_API_TOKEN() {
        return DISCORD_API_TOKEN;
    }

    public String getCHATGPT_API_TOKEN() {
        return CHATGPT_API_TOKEN;
    }

    public String getCHATGPT_CHAT_ID() {
        return CHATGPT_CHAT_ID;
    }

    public String getMUSIC_CHAT_ID() {
        return MUSIC_CHAT_ID;
    }
}

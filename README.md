# Discord Bot 1.0
Made by: [qvxkrzysiek](https://github.com/qvxkrzysiek)

A bot that you can add to your Discord server and enjoy listening to music with your friends without ads and the ability to ask a quick question to the bot thanks to [ChatGPT 3.0](https://openai.com/)

## Features in current version

- Listening to music on discord server
- Preview of the currently played song, including: title, author, thumbnail
- Song queue creation and preview
- Quick and easy music controls, currently: pause, skip, repeat, shuffle
- Asking questions to bot through a special channel and getting answers from [ChatGPT 3.0](https://openai.com/)
- And more!

## Gallery

### GUI
![GUI INTERFACE](https://raw.githubusercontent.com/qvxkrzysiek/Discord-Bot/main/docs/Discord1.png)

## Installation
To install the bot you will need to create a token for it via [Discord Developer Portal](https://discord.com/developers/docs/intro) On this page you can configure the name of the bot and its icon.
The second important element will be getting a token from [OpenAI API](https://platform.openai.com/overview).
Once these things are done you can proceed to configuration.

### 1 Configuration
Path: Discord-Bot/src/main/resources/config.yml
```shell
DISCORD_API_TOKEN: ""
CHATGPT_API_TOKEN: ""
CHATGPT_CHAT_ID: ""
MUSIC_CHAT_ID: ""
```
In this file you put your Discord API token and OpenAI API token.
In place of CHATGPT CHAT ID, MUSIC_CHAT ID, enter the numbers of discord channels on which you want the bot to operate.
  
### 2 RUNNING
To run the program, I recommend opening the repository in [IntelliJ IDEA](https://www.jetbrains.com/idea/)

You can use a different environment, but this may result in the pom.xml dependencies not working

## Future plans

- Adding the ability to add a song to your favorites
- Adding a faster and easier installation method
- Adding more customization on the user's side
- Multi-language support

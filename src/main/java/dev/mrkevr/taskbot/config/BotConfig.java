package dev.mrkevr.taskbot.config;

import dev.mrkevr.taskbot.bot.TaskBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@ConfigurationProperties(prefix = "telegram-bot")
@Getter
@Setter
public class BotConfig {

    private String name;
    private String token;

    @Bean
    CommandLineRunner commandLineRunner(TaskBot taskBot) {
        return args -> {
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(taskBot);
            } catch(TelegramApiException e) {
                e.printStackTrace();
            }
        };
    }
}

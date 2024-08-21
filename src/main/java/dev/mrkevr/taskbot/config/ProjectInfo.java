package dev.mrkevr.taskbot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ProjectInfo {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.version}")
    private String apppVersion;

    @Value("${spring.application.description}")
    private String appDescription;
}

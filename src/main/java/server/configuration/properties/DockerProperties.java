package server.configuration.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @GitHub : https://github.com/zacscoding
 */
@Getter
@Setter
public class DockerProperties {

    private Server server;

    @Getter
    @Setter
    public static class Server {

        private String username;
        private String password;
        private String host;
    }
}

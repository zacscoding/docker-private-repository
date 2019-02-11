package server.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class DockerClientFactory {

    public DockerClient createDockerClient(String username, String password, String host) {
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerTlsVerify(false)
            .withRegistryUsername(username)
            .withRegistryPassword(password)
            .withDockerHost(host)
            .build();

        return DockerClientBuilder.getInstance(config).build();
    }
}

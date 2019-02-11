package server;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DockerPrivateRepositoryApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DockerPrivateRepositoryApplication.class, args);
        try {
            DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerTlsVerify(false)
                .withDockerHost("unix:///var/run/docker.sock")
                .build();

            DockerClient client = DockerClientBuilder.getInstance(config).build();
            System.out.println(">> Docker client :: " + client);
            Info exec = client.infoCmd().exec();
            System.out.println(">> info cmd :: " + exec);
        } catch (Exception e) {
            logger.warn("Exception occur while getting docker client", e);
            throw new RuntimeException(e);
        }
    }

}


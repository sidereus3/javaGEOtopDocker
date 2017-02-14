import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import org.junit.Test;

import java.util.List;

/**
 * Created by sidereus on 2/14/17.
 */
public class ContainerList {

    @Test
    public void containerList() throws DockerCertificateException, DockerException, InterruptedException {
        System.out.println("-- List each and every container --");
        final DockerClient docker = DefaultDockerClient.fromEnv().build();
        final List<Container> containers = docker.listContainers(DockerClient.ListContainersParam.allContainers());

        containers.forEach((container -> {
            System.out.println(container.image());
        }));

    }
}

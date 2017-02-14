import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Image;
import org.junit.Test;

import java.util.List;

/**
 * Created by sidereus on 2/14/17.
 */
public class ImageList {

    @Test
    public void imageList() throws DockerCertificateException, DockerException, InterruptedException {
        System.out.println("-- List each and every image --");
        final DockerClient docker = DefaultDockerClient.fromEnv().build();
        final List<Image> quxImages = docker.listImages(DockerClient.ListImagesParam.allImages());

        quxImages.forEach((image -> {
            System.out.println(image.repoTags());
        }));
    }
}

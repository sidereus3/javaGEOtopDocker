package org.altervista.growworkinghard;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;

/**
 * Created by sidereus on 2/14/17.
 */
public class dockerBinding {

    public static void main(String[] args) throws DockerException, InterruptedException, DockerCertificateException {
        // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
        final DockerClient docker = DefaultDockerClient.fromEnv().build();

        docker.pull("omslab/geotop:2.1");

        final HostConfig hostConfig = HostConfig.builder().appendBinds("/home/sidereus/vcs/git/geotopmodel/geotop_examples/3D/small_example:/work").build();
        final ContainerConfig volumeConfig = ContainerConfig.builder()
                .image("omslab/geotop:2.1")
                .hostConfig(hostConfig)
                .build();

        final ContainerCreation creation = docker.createContainer(volumeConfig);
        final String id = creation.id();

        // Start container
        docker.startContainer(id);

        // Exec command inside running container with attached STDOUT and STDERR
        final String[] command = {"geotop", "."};
        final ExecCreation execCreation = docker.execCreate(
                id, command, DockerClient.ExecCreateParam.attachStdout(),
                DockerClient.ExecCreateParam.attachStderr());
        final LogStream output = docker.execStart(execCreation.id());
        //final String execOutput = output.readFully();

        docker.waitContainer(id);

        // Remove container
        docker.removeContainer(id);

        // Close the docker client
        docker.close();
    }
}

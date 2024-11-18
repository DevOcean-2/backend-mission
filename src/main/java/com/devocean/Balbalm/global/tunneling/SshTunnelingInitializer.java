package com.devocean.Balbalm.global.tunneling;

import static java.lang.System.*;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import jakarta.annotation.PreDestroy;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("dev")
@Component
@ConfigurationProperties(prefix = "ssh")
@Validated
@Setter
public class SshTunnelingInitializer {

	@NotNull
	private String remoteJumpHost;
	@NotNull
	private String user;
	@NotNull
	private int sshPort;
	@NotNull
	private String privateKey;
	@NotNull
	private String databaseUrl;
	@NotNull
	private int databasePort;

	private Session session;

	@PreDestroy
	public void closeSSH() {
		if (session.isConnected())
			session.disconnect();
	}

	public Integer buildSshConnection() {

		Integer forwardedPort = null;

		try {
			JSch jSch = new JSch();

			jSch.addIdentity(privateKey);  // 개인키
			session = jSch.getSession(user, remoteJumpHost, sshPort);  // 세션 설정
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			session.connect();  // ssh 연결

			forwardedPort = session.setPortForwardingL(0, databaseUrl, databasePort);

		} catch (Exception e){
			this.closeSSH();
			e.printStackTrace();
			exit(1);
		}

		return forwardedPort;
	}
}
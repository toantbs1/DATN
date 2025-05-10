package com.users.app.config;

import io.github.jhipster.config.JHipsterProperties;
import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Logger;

@Component
@Log4j2
public class DynamicMailBaseUrlConfigurer {

    private final JHipsterProperties jHipsterProperties;

    public DynamicMailBaseUrlConfigurer(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @PostConstruct
    public void updateMailBaseUrl() {
        try {
            String ip = getLocalIpAddress();
            String url = "http://" + ip + ":8080";
            jHipsterProperties.getMail().setBaseUrl(url);
            log.info("✔ Dynamic base URL for mail set to: {}", url);
        } catch (Exception e) {
            log.warn("⚠ Failed to dynamically set mail base URL", e);
        }
    }

    private String getLocalIpAddress() throws Exception {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
                    return addr.getHostAddress();
                }
            }
        }
        throw new RuntimeException("No LAN IP address found.");
    }
}

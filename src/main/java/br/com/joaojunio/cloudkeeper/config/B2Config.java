package br.com.joaojunio.cloudkeeper.config;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(B2Properties.class)
public class B2Config {

    @Bean
    public B2StorageClient b2StorageClient(B2Properties props) {
        return B2StorageClientFactory
            .createDefaultFactory()
            .create(props.getKeyId(), props.getApplicationKey(), props.getUserAgent());
    }
}

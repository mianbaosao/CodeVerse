package com.bread.oss.config;



import com.bread.oss.adapter.StorageAdapter;
import com.bread.oss.adapter.impl.AliCloldAdapterImpl;
import com.bread.oss.adapter.impl.MinioAdapterImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存储config
 *
 * @author: bread
 * @date: 2024/09/11
 */
@Slf4j
@Configuration
@RefreshScope
public class StorageConfig {
    @Value("${storage.service.type}")
    private String storageType;

    @Bean
    @RefreshScope
    public StorageAdapter storageService() {
        log.info("[storageType]"+storageType);
        if ("minio".equals(storageType)) {
            return new MinioAdapterImpl();
        } else if ("aliyun".equals(storageType)) {
            return new AliCloldAdapterImpl();
        } else {
            throw new IllegalArgumentException("未找到对应的文件存储处理器");
        }
    }

}

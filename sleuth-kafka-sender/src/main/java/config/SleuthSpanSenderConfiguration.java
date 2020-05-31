package config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.zipkin2.ZipkinAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sender.KafkaSpanSender;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;

@Configuration
@EnableConfigurationProperties(KafkaSenderProperties.class)
public class SleuthSpanSenderConfiguration {

  @Bean(ZipkinAutoConfiguration.REPORTER_BEAN_NAME)
  Reporter<Span> myReporter(KafkaSenderProperties properties) {
    return AsyncReporter.create(sender(properties));
  }

  @Bean(ZipkinAutoConfiguration.SENDER_BEAN_NAME)
  KafkaSpanSender sender(KafkaSenderProperties properties) {
    return new KafkaSpanSender(properties);
  }
}

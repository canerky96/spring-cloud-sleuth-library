package config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import zipkin2.codec.Encoding;

@ConfigurationProperties("kaya.kafka")
public class KafkaSenderProperties {
    private String brokers;
    private String topic;
    private int messageMaxBytes = Integer.MAX_VALUE;
    private Encoding encoding = Encoding.JSON;
    private int messageTimeout = 1;

    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getMessageMaxBytes() {
        return messageMaxBytes;
    }

    public void setMessageMaxBytes(int messageMaxBytes) {
        this.messageMaxBytes = messageMaxBytes;
    }

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    public int getMessageTimeout() {
        return messageTimeout;
    }

    public void setMessageTimeout(int messageTimeout) {
        this.messageTimeout = messageTimeout;
    }
}
package sender;

import config.KafkaSenderProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import zipkin2.Call;
import zipkin2.codec.Encoding;
import zipkin2.reporter.BytesMessageEncoder;
import zipkin2.reporter.Sender;

import java.util.List;
import java.util.Properties;

public class KafkaSpanSender extends Sender {
  private KafkaSenderProperties properties;
  private BytesMessageEncoder encoder;
  private KafkaProducer<Long, String> kafkaProducer;

  public KafkaSpanSender(KafkaSenderProperties properties) {
    this.properties = properties;
    this.encoder = BytesMessageEncoder.forEncoding(properties.getEncoding());
  }

  public Encoding encoding() {
    return properties.getEncoding();
  }

  public int messageMaxBytes() {
    return properties.getMessageMaxBytes();
  }

  public int messageSizeInBytes(List<byte[]> list) {
    return encoding().listSizeInBytes(list);
  }

  public Call<Void> sendSpans(List<byte[]> list) {
    byte[] encodedMessage = encoder.encode(list);
    getProducer().send(new ProducerRecord(properties.getTopic(), encodedMessage));
    return null;
  }

  private KafkaProducer<Long, String> getProducer() {
    if (null == kafkaProducer) {
      synchronized (this) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.properties.getBrokers());
        properties.put(
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        properties.put(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        this.kafkaProducer = new KafkaProducer<Long, String>(properties);
      }
    }
    return kafkaProducer;
  }
}

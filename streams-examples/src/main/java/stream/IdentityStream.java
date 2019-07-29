package stream;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Identity stream, sends messages back into the input topic (creating an infinite loop).
 *
 * <p>Created by wilmol on 2019-04-17.
 */
public final class IdentityStream {

  private IdentityStream() {}

  private static final Logger LOG = LogManager.getLogger();

  /** Run the streams. */
  public static void main(final String... args) {
    if (args.length < 1) {
      LOG.error("Enter topic name");
      System.exit(1);
    }
    String topic = args[0];
    LOG.info("Creating identity stream for topic: {}", topic);

    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

    StreamsBuilder builder = new StreamsBuilder();
    builder.stream(topic).to(topic);
    KafkaStreams streams = new KafkaStreams(builder.build(), props);

    CountDownLatch latch = new CountDownLatch(1);
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread("streams-shutdown-hook") {
              @Override
              public void run() {
                streams.close();
                latch.countDown();
              }
            });
    try {
      streams.start();
      latch.await();
    } catch (Throwable e) {
      System.exit(1);
    }
    System.exit(0);
  }
}

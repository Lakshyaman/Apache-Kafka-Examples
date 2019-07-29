package service;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.Service;
import org.apache.kafka.streams.KafkaStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapts a {@link KafkaStreams} to a {@link Service} so it can be run as a service.
 *
 * <p>Since {@link KafkaStreams} manages its own threading {@link AbstractIdleService} is used.
 *
 * <p>Created by wilmol on 2019-07-29.
 */
public class StreamsService extends AbstractIdleService {

  private final Logger log = LoggerFactory.getLogger(ConsumerService.class);

  private final KafkaStreams kafkaStreams;

  public StreamsService(KafkaStreams kafkaStreams) {
    this.kafkaStreams = checkNotNull(kafkaStreams);
  }

  @Override
  protected void startUp() {
    log.info("Starting Kafka Streams");
    kafkaStreams.start();
    log.info("Started Kafka Streams");
  }

  @Override
  protected void shutDown() {
    kafkaStreams.close();
    log.info("Closed Kafka Streams");
  }
}

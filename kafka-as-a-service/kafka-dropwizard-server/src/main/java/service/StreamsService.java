package service;

import static com.google.common.base.Preconditions.checkNotNull;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.util.concurrent.AbstractIdleService;
import org.apache.kafka.streams.KafkaStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kafka {@link KafkaStreams} example service.
 *
 * <p>Since {@link KafkaStreams} manages its own threading {@link AbstractIdleService} is used.
 *
 * <p>Created by wilmol on 2019-07-29.
 */
public class StreamsService extends AbstractIdleService implements DropWizardKafkaService {

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

  /**
   * Override the default implementation because {@link KafkaStreams} manages its own threading.
   *
   * @return {@link KafkaStreams} as a {@link HealthCheck}
   */
  @Override
  public HealthCheck healthCheck() {
    return new HealthCheck() {
      @Override
      protected Result check() {
        String state = kafkaStreams.state().toString();
        return kafkaStreams.state().isRunning() ? Result.healthy(state) : Result.unhealthy(state);
      }
    };
  }

  @Override
  public String name() {
    return serviceName();
  }
}

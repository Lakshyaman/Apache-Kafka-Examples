package service;

import static com.google.common.base.Preconditions.checkNotNull;

import adapter.ManagedService;
import com.codahale.metrics.health.HealthCheck;
import com.google.common.util.concurrent.AbstractIdleService;
import io.dropwizard.lifecycle.Managed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Example {@link KafkaStreams} service.
 *
 * <p>Since {@code KafkaStreams} manages its own threading {@link AbstractIdleService} is used.
 *
 * <p>Created by wilmol on 2019-07-29.
 */
public class StreamsService extends AbstractIdleService implements DropWizardService {

  private final Logger log = LogManager.getLogger();

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

  @Override
  public Managed managed() {
    return new ManagedService(this, name());
  }

  /**
   * Note {@link KafkaStreams.State#isRunning()} method is used because {@code KafkaStreams} manages
   * its own threading.
   *
   * @return {@link KafkaStreams} {@link HealthCheck}
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

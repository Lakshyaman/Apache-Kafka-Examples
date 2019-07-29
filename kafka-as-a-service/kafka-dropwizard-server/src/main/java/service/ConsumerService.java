package service;

import static com.google.common.base.Preconditions.checkNotNull;

import adapter.ManagedService;
import adapter.ServiceHealthCheck;
import com.codahale.metrics.health.HealthCheck;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.AbstractScheduledService;
import io.dropwizard.lifecycle.Managed;
import java.time.Duration;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Example {@link Consumer} service.
 *
 * <p>In this case, the {@code Consumer} is always polling, hence the service type is {@link
 * AbstractExecutionThreadService}. Another option is {@link AbstractScheduledService}.
 *
 * <p>Created by wilmol on 2019-07-29.
 */
public class ConsumerService extends AbstractExecutionThreadService implements DropWizardService {

  private static final Duration TIMEOUT = Duration.ofSeconds(1);

  private final Logger log = LogManager.getLogger();

  private final Consumer<?, ?> kafkaConsumer;

  private final String topic;

  public ConsumerService(Consumer<?, ?> kafkaConsumer, String topic) {
    this.kafkaConsumer = checkNotNull(kafkaConsumer);
    this.topic = topic;
  }

  @Override
  protected void startUp() {
    log.info("Starting Kafka Consumer");
    kafkaConsumer.subscribe(ImmutableSet.of(topic));
    log.info("Started Kafka Consumer, subscribed to topic [{}]", topic);
  }

  @Override
  protected void run() {
    while (isRunning()) {
      ConsumerRecords<?, ?> records = kafkaConsumer.poll(TIMEOUT);
      Streams.stream(records.iterator()).forEach(record -> log.info(record.toString()));
    }
  }

  @Override
  protected void shutDown() {
    kafkaConsumer.close();
    log.info("Closed Kafka Consumer");
  }

  @Override
  public Managed managed() {
    return new ManagedService(this, name());
  }

  @Override
  public HealthCheck healthCheck() {
    return new ServiceHealthCheck(this);
  }

  @Override
  public String name() {
    return serviceName();
  }
}

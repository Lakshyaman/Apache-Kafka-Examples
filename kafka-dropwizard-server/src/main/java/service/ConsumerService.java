package service;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.common.util.concurrent.Service;
import java.time.Duration;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapts a {@link Consumer} to a {@link Service} so it can be run as a service.
 *
 * <p>In this case, the consumer is always polling, hence the service type is {@link
 * AbstractExecutionThreadService}. Another option could be {@link AbstractScheduledService}.
 *
 * <p>Created by wilmol on 2019-07-29.
 */
public class ConsumerService extends AbstractExecutionThreadService {

  private static final Duration TIMEOUT = Duration.ofSeconds(1);

  private final Logger log = LoggerFactory.getLogger(ConsumerService.class);

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
}

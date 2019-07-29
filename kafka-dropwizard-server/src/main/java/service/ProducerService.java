package service;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.common.util.concurrent.Service;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapts a {@link Producer} to a {@link Service} so it can be run as a service.
 *
 * <p>In this case, the producer is always sending, hence the service type is {@link
 * AbstractExecutionThreadService}. Another option could be {@link AbstractScheduledService}.
 *
 * <p>Created by wilmol on 2019-07-29.
 */
public class ProducerService extends AbstractExecutionThreadService {

  private final Logger log = LoggerFactory.getLogger(ConsumerService.class);

  private final Producer<String, String> kafkaProducer;

  private final String topic;

  public ProducerService(Producer<String, String> kafkaProducer, String topic) {
    this.kafkaProducer = checkNotNull(kafkaProducer);
    this.topic = topic;
  }

  @Override
  protected void run() {
    while (isRunning()) {
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key", "value");
      kafkaProducer.send(record);
    }
  }

  @Override
  protected void shutDown() {
    kafkaProducer.close();
    log.info("Closed Kafka Producer");
  }
}

package service;

import static com.google.common.base.Preconditions.checkNotNull;

import adapter.ManagedService;
import adapter.ServiceHealthCheck;
import com.codahale.metrics.health.HealthCheck;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.AbstractScheduledService;
import io.dropwizard.lifecycle.Managed;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Example {@link Producer} service.
 *
 * <p>In this case, the {@code Producer} is always sending, hence the service type is {@link
 * AbstractExecutionThreadService}. Another option is {@link AbstractScheduledService}.
 *
 * <p>Created by wilmol on 2019-07-29.
 */
public class ProducerService extends AbstractExecutionThreadService implements DropWizardService {

  private final Logger log = LogManager.getLogger();

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

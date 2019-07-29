package service;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.util.concurrent.Service;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

/**
 * A Kafka (Producer/Consumer/Streams) service which may be run within a DropWizard Application.
 *
 * <p>Created by wilmol on 2019-07-29.
 *
 * @see org.apache.kafka.clients.producer.KafkaProducer
 * @see org.apache.kafka.clients.producer.KafkaProducer
 * @see org.apache.kafka.streams.KafkaStreams
 * @see io.dropwizard.Application
 * @see Service
 */
public interface DropWizardKafkaService extends Service {

  /**
   * Register this Kafka service within the DropWizard {@link Environment}.
   *
   * @param environment DropWizard app environment.
   */
  default void registerWithin(Environment environment) {
    environment.lifecycle().manage(managed());
    environment.healthChecks().register(name(), healthCheck());
  }

  /**
   * Adapt this {@code DropWizardKafkaService} to a {@link Managed} so it can be run as a background
   * service by DropWizard.
   *
   * <p>By default, this implementation adapts a {@link Service} to a {@link Managed}. Subclasses
   * may override this behaviour.
   *
   * @return this instance as a {@link Managed}
   */
  default Managed managed() {
    return new Managed() {
      @Override
      public void start() {
        startAsync().awaitRunning();
      }

      @Override
      public void stop() {
        stopAsync().awaitTerminated();
      }
    };
  }

  /**
   * Adapt this {@code DropWizardKafkaService} to a {@link HealthCheck} so it can be registered by
   * DropWizard.
   *
   * <p>By default, this implementation adapts a {@link Service} to a {@link HealthCheck}.
   * Subclasses may override this behaviour.
   *
   * @return this instance as a {@link HealthCheck}
   */
  default HealthCheck healthCheck() {
    return new HealthCheck() {
      @Override
      protected Result check() {
        String state = state().toString();
        return isRunning()
            ? HealthCheck.Result.healthy(state)
            : HealthCheck.Result.unhealthy(state);
      }
    };
  }

  String name();
}

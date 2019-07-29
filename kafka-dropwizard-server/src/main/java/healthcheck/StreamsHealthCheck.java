package healthcheck;

import static com.google.common.base.Preconditions.checkNotNull;

import com.codahale.metrics.health.HealthCheck;
import org.apache.kafka.streams.KafkaStreams;

/**
 * Adapts a {@link KafkaStreams} to a {@link HealthCheck} so it can be registered by DropWizard.
 *
 * <p>The reason {@link ServiceHealthCheck} is not used for {@link KafkaStreams} is because {@link
 * KafkaStreams} manages its own threading.
 *
 * <p>Created by wilmol on 2019-07-29.
 */
public class StreamsHealthCheck extends HealthCheck {

  private final KafkaStreams kafkaStreams;

  public StreamsHealthCheck(KafkaStreams kafkaStreams) {
    this.kafkaStreams = checkNotNull(kafkaStreams, "service");
  }

  @Override
  protected HealthCheck.Result check() {
    String state = kafkaStreams.state().toString();
    return kafkaStreams.state().isRunning() ? Result.healthy(state) : Result.unhealthy(state);
  }
}

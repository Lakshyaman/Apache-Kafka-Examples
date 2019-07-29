import static service.DropWizardService.register;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ConsumerService;
import service.DropWizardService;
import service.ProducerService;
import service.StreamsService;

/** Created by wilmol on 2019-07-29. */
public class DropWizardApp extends Application {

  private final Logger log = LogManager.getLogger();

  @SuppressFBWarnings(
      value = "NP_NULL_PARAM_DEREF_ALL_TARGETS_DANGEROUS",
      justification = "This is an example, in real world we would not be passing null :)")
  public static void main(String[] args) {
    new DropWizardApp().run(null, null);
  }

  @Override
  public void run(Configuration configuration, Environment environment) {
    log.info("Configuration: [{}]", configuration);

    // Create producers, consumers, streams etc.
    Producer<String, String> producer = new KafkaProducer<>(new Properties());
    Consumer<String, String> consumer = new KafkaConsumer<>(new Properties());
    KafkaStreams streams = new KafkaStreams(null, new Properties());

    // Create services
    DropWizardService producerService = new ProducerService(producer, "producerTopic");
    DropWizardService consumerService = new ConsumerService(consumer, "consumerTopic");
    DropWizardService streamsService = new StreamsService(streams);

    // Register services
    register(producerService, environment);
    register(consumerService, environment);
    register(streamsService, environment);
  }
}

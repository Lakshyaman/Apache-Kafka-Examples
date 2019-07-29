import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by wilmol on 2019-07-29. */
public class DropWizardApp extends Application {

  private final Logger log = LoggerFactory.getLogger(DropWizardApp.class);

  public static void main(String[] args) {
    new DropWizardApp().run(null, null);
  }

  @Override
  public void run(Configuration configuration, Environment environment) {
    //    // Create your producers, consumers, streams etc.
    //    Producer<String, String> producer = new KafkaProducer<>(new Properties());
    //    Consumer<String, String> consumer = new KafkaConsumer<>(new Properties());
    //    KafkaStreams streams = new KafkaStreams(null, new Properties());

    // Consumers/Producers should run in AbstractExecutionThreadService

    // Streams should run in
  }
}

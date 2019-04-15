package consumer.stdout;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

/**
 * Consumer which subscribes to several topics of type [String -> String] and pipes input to standard output.
 * <p>
 * Created by wilmol on 2019-04-15.
 */
public class StdOutStringConsumer
{

    // currently subscribes to all available topics
    private static final List<String> TOPICS = List.of("stdin", "primes");

    private static final Duration POLLING_DURATION = Duration.ofSeconds(1);

    public static void main(final String... args)
    {
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, StdOutStringConsumer.class.getName());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(TOPICS);

        while (true)
        {
            ConsumerRecords<String, String> records = consumer.poll(POLLING_DURATION);
            records.forEach(record ->
                    System.out.println(String.format("Received:(%s, %s, %,d, %,d)",
                            record.key(), record.value(),
                            record.partition(), record.offset())));
            consumer.commitAsync();
        }
    }

}

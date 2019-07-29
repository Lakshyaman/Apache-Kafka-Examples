package producer.stdin;

import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import producer.StringProducer;
import util.KeyValuePair;

/**
 * Producer which sends command line input to the 'stdin' topic.
 *
 * <p>Created by wilmol on 2019-04-15.
 */
public class StdInProducer implements StringProducer {

  private final Scanner scanner = new Scanner(System.in, Charset.defaultCharset().name());

  private final AtomicLong count = new AtomicLong();

  public static void main(final String... args) {
    new StdInProducer().run();
  }

  @Override
  public String topicName() {
    return "stdin";
  }

  @Override
  public KeyValuePair<String, String> next() {
    return KeyValuePair.of(String.format("stdin_%d", count.incrementAndGet()), scanner.nextLine());
  }
}

package producer.stdin;

import producer.StringProducer;
import util.KeyValuePair;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Producer which sends command line input to the 'stdin' topic.
 * <p>
 * Created by wilmol on 2019-04-15.
 */
public class StdInProducer implements StringProducer
{

    private final Scanner scanner = new Scanner(System.in);

    private final AtomicLong count = new AtomicLong();

    public static void main(final String... args)
    {
        new StdInProducer().run();
    }

    @Override
    public String topicName()
    {
        return "stdin";
    }

    @Override
    public KeyValuePair<String, String> next()
    {
        return KeyValuePair.of(String.format("stdin_%d", count.incrementAndGet()), scanner.nextLine());
    }

}

package producer.primes;

import com.google.common.math.LongMath;
import producer.StringProducer;
import util.KeyValuePair;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

/**
 * Producer which sends the prime numbers to the 'primes' topic.
 * <p>
 * Created by wilmol on 2019-04-15.
 */
public class PrimesProducer implements StringProducer
{

    private static final Duration TIME_BETWEEN_PRIMES = Duration.ofMillis(500);

    private final AtomicLong count = new AtomicLong();

    private long mostRecentPrime = -1;

    public static void main(final String... args)
    {
        new PrimesProducer().run();
    }

    @Override
    public String topicName()
    {
        return "primes";
    }

    @Override
    public KeyValuePair<String, String> next()
    {
        try
        {
            Thread.sleep(TIME_BETWEEN_PRIMES.toMillis());
        }
        catch (InterruptedException ignored)
        {
        }

        long startFrom = mostRecentPrime == -1 ? 2 : mostRecentPrime + 1;
        long nextPrime = LongStream.iterate(startFrom, n -> n + 1)
                .sequential()
                .filter(LongMath::isPrime)
                .findFirst()
                .getAsLong();
        mostRecentPrime = nextPrime;
        return KeyValuePair.of(String.format("prime_%s", formatLong(count.incrementAndGet())), formatLong(nextPrime));
    }

    private String formatLong(long n)
    {
        return String.format("%,d", n);
    }

}

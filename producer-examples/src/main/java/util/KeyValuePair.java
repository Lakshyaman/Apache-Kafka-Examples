package util;

import lombok.Data;

/**
 * Created by wilmol on 2019-04-15.
 */
@Data(staticConstructor = "of")
public class KeyValuePair<K, V>
{
    private final K key;

    private final V value;

    @Override
    public String toString()
    {
        return "Pair(" + key + ", " + value + ")";
    }

}

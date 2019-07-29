package util;

/** Created by wilmol on 2019-04-15. */
public final class KeyValuePair<K, V> {

  private final K key;

  private final V value;

  private KeyValuePair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public static <K, V> KeyValuePair<K, V> of(K key, V value) {
    return new KeyValuePair<>(key, value);
  }

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Pair(" + key + ", " + value + ")";
  }
}

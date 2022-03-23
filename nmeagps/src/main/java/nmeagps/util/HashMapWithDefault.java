package nmeagps.util;

import java.util.HashMap;

public class HashMapWithDefault<K, V> extends HashMap<K, V> {
  private V defaultValue;

  public HashMapWithDefault(V defaultValue) {
    this.defaultValue = defaultValue;
  }

  public V get(Object key) {
    return super.getOrDefault(key, defaultValue);
  }
}

package org.codeformatter.collections;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor(staticName = "of")
@Value
public class Pair<K, V> {

    K key;

    V value;

}

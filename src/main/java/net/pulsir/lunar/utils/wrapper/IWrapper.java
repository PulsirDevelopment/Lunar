package net.pulsir.lunar.utils.wrapper;

public interface IWrapper<K,V> {

    K wrap(V v);
    V unwrap(K k);
}

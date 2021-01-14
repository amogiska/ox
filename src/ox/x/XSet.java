package ox.x;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Sets;

import ox.util.Utils;

public class XSet<T> extends ForwardingSet<T> {

  private final Set<T> delegate;

  private XSet(Set<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  protected Set<T> delegate() {
    return delegate;
  }

  /**
   * Unlike map(), which calls the function one time per element, the given function will only be called once. It is
   * passed this entire list as an argument.
   */
  public <V> V mapBulk(Function<? super XSet<T>, V> function) {
    return function.apply(this);
  }

  public XSet<T> ifContains(T element, Runnable callback) {
    if (this.contains(element)) {
      callback.run();
    }
    return this;
  }

  public XSet<T> removeNull() {
    remove(null);
    return this;
  }

  public Optional<T> only() {
    return Optional.ofNullable(Utils.only(this));
  }

  public static <T> XSet<T> create(Set<T> set) {
    return new XSet<T>(set);
  }

  public static <T> XSet<T> create(Iterable<T> iter) {
    return new XSet<T>(Sets.newHashSet(iter));
  }

}
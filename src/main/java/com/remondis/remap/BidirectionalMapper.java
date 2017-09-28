package com.remondis.remap;

import static com.remondis.remap.Lang.denyNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BidirectionalMapper<S, D> {

  Mapper<S, D> to;
  Mapper<D, S> from;

  private BidirectionalMapper(Mapper<S, D> to, Mapper<D, S> from) {
    super();
    this.to = to;
    this.from = from;
  }

  /**
   * Creates a {@link BidirectionalMapper} for the specified {@link Mapper} objects that represent the two
   * uni-directional mappings..
   *
   * @param to Valid {@link Mapper} for mapping source to destination type.
   * @param from Valid {@link Mapper} for mapping destination back to source type.
   * @return Returns a {@link BidirectionalMapper}.
   */
  public static <S, D> BidirectionalMapper<S, D> of(Mapper<S, D> to, Mapper<D, S> from) {
    denyNull("to", to);
    denyNull("from", from);
    return new BidirectionalMapper<>(to, from);
  }

  /**
   * Performs the mapping from the source to destination type.
   *
   * @param source The source object to map to a new destination object.
   * @return Returns a newly created destination object.
   */
  public D map(S source) {
    return to.map(source);
  }

  /**
   * Performs the mapping from the source to destination type for the specified {@link Collection}.
   *
   * @param source The source collection to map to a new collection of destination objects.
   * @return Returns a newly created collection of destination objects. The type of the resulting collection is either
   *         {@link List} or {@link Set} depending on the specified type.
   */
  public Collection<D> map(Collection<S> source) {
    return to.map(source);
  }

  /**
   * Performs the mapping from the source to destination type for the specified {@link List}.
   *
   * @param source The source collection to map to a new collection of destination objects.
   * @return Returns a newly created list of destination objects.
   */
  public List<D> map(List<S> source) {
    return to.map(source);
  }

  /**
   * Performs the mapping from the source to destination type for the specified {@link Set}.
   *
   * @param source The source collection to map to a new collection of destination objects.
   * @return Returns a newly created set of destination objects.
   */
  public Set<D> map(Set<S> source) {
    return to.map(source);
  }

  /**
   * Performs the mapping from the source to destination type for the elements provided by the specified
   * {@link Iterable} .
   *
   * @param iterable The source iterable to be mapped to a new {@link List} of destination objects.
   * @return Returns a newly created list of destination objects.
   */
  public List<D> map(Iterable<S> iterable) {
    return to.map(iterable);
  }

  /**
   * Performs the mapping from the destination to source type.
   *
   * @param destination The destination object to map to a new source object.
   * @return Returns a newly created source object.
   */
  public S mapFrom(D destination) {
    return from.map(destination);
  }

  /**
   * Performs the mapping from the destination to source type for the specified {@link Collection}.
   *
   * @param destination The destination collection to map to a new collection of source objects.
   * @return Returns a newly created collection of source objects. The type of the resulting collection is either
   *         {@link List} or {@link Set} depending on the specified type.
   */
  public Collection<S> mapFrom(Collection<D> destination) {
    return from.map(destination);
  }

  /**
   * Performs the mapping from the destination to source type for the specified {@link List}.
   *
   * @param destination The destination collection to map to a new collection of source objects.
   * @return Returns a newly created list of source objects.
   */
  public List<S> mapFrom(List<D> destination) {
    return from.map(destination);
  }

  /**
   * Performs the mapping from the destination to source type for the specified {@link Set}.
   *
   * @param destination The destination collection to map to a new collection of source objects.
   * @return Returns a newly created set of source objects.
   */
  public Set<S> mapFrom(Set<D> destination) {
    return from.map(destination);
  }

  /**
   * Performs the mapping from the destination to source type for the elements provided by the specified
   * {@link Iterable} .
   *
   * @param iterable The destination iterable to be mapped to a new {@link List} of source objects.
   * @return Returns a newly created list of source objects.
   */
  public List<S> mapFrom(Iterable<D> iterable) {
    return from.map(iterable);
  }

  /**
   * @return Returns the {@link Mapper} object for mapping source to destination type.
   */
  public Mapper<S, D> getMapper() {
    return to;
  }

  /**
   * @return Returns the {@link Mapper} object for mapping destination to source type.
   */
  public Mapper<D, S> getFromMapper() {
    return from;
  }

  @Override
  public String toString() {
    return "BidirectionalMapper [to=" + to + ", from=" + from + "]";
  }

}

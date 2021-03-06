// Generated by delombok at Thu Jun 14 14:59:06 CEST 2018
package com.remondis.remap.flatCollectionMapping;

import java.util.List;

public class Source {
  private List<Long> ids;


  @java.lang.SuppressWarnings("all")
  public static class SourceBuilder {
    @java.lang.SuppressWarnings("all")
    private List<Long> ids;

    @java.lang.SuppressWarnings("all")
    SourceBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public SourceBuilder ids(final List<Long> ids) {
      this.ids = ids;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Source build() {
      return new Source(ids);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Source.SourceBuilder(ids=" + this.ids + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  public static SourceBuilder builder() {
    return new SourceBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public List<Long> getIds() {
    return this.ids;
  }

  @java.lang.SuppressWarnings("all")
  public void setIds(final List<Long> ids) {
    this.ids = ids;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Source)) return false;
    final Source other = (Source) o;
    if (!other.canEqual((java.lang.Object) this)) return false;
    final java.lang.Object this$ids = this.getIds();
    final java.lang.Object other$ids = other.getIds();
    if (this$ids == null ? other$ids != null : !this$ids.equals(other$ids)) return false;
    return true;
  }

  @java.lang.SuppressWarnings("all")
  protected boolean canEqual(final java.lang.Object other) {
    return other instanceof Source;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $ids = this.getIds();
    result = result * PRIME + ($ids == null ? 43 : $ids.hashCode());
    return result;
  }

  @java.lang.SuppressWarnings("all")
  public Source() {
  }

  @java.lang.SuppressWarnings("all")
  public Source(final List<Long> ids) {
    this.ids = ids;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Source(ids=" + this.getIds() + ")";
  }
}

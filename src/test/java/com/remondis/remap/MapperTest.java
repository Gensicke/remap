package com.remondis.remap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.remondis.remap.inheritance.Child;
import com.remondis.remap.inheritance.ChildResource;

public class MapperTest {

  private static final String MORE_IN_A = "moreInA";
  private static final Long ZAHL_IN_A = -88L;
  private static final Integer B_INTEGER = -999;
  private static final int B_NUMBER = 222;
  private static final String B_STRING = "b string";
  private static final Integer INTEGER = 310;
  private static final int NUMBER = 210;
  private static final String STRING = "a string";

  @Test(expected = MappingException.class)
  public void shouldDenyMapNull() {
    Mapper<A, AResource> mapper = Mapping.from(A.class)
                                         .to(AResource.class)
                                         .reassign(A::getMoreInA)
                                         .to(AResource::getMoreInAResource)
                                         .reassign(A::getZahlInA)
                                         .to(AResource::getZahlInAResource)
                                         .useMapper(Mapping.from(B.class)
                                                           .to(BResource.class)
                                                           .mapper())
                                         .mapper();
    mapper.map((A) null);
  }

  /**
   * Ensures that the mapper maps inherited field correctly.
   */
  @Test
  public void shouldMapInheritedFields() {
    Mapper<Child, ChildResource> map = Mapping.from(Child.class)
                                              .to(ChildResource.class)
                                              .omitInSource(Child::getMoreInParent)
                                              .omitInDestination(ChildResource::getMoreInParentResource)
                                              .useMapper(Mapping.from(B.class)
                                                                .to(BResource.class)
                                                                .mapper())
                                              .mapper();

    B b = new B(B_STRING, B_NUMBER, B_INTEGER);
    Object shouldNotMap = new Object();
    Object object = new Object();
    int zahl = 11;
    Child child = new Child(shouldNotMap, STRING, b, object, zahl);
    ChildResource cr = map.map(child);

    assertNull(cr.getMoreInParentResource());
    assertEquals(STRING, child.getString());
    assertEquals(STRING, cr.getString());
    assertEquals(object, child.getObject());
    assertEquals(object, cr.getObject());
    assertEquals(zahl, child.getZahl());
    assertEquals(zahl, cr.getZahl());

    BResource br = cr.getB();
    assertEquals(B_STRING, b.getString());
    assertEquals(B_STRING, br.getString());
    assertEquals(B_NUMBER, b.getNumber());
    assertEquals(B_NUMBER, br.getNumber());
    assertEquals(B_INTEGER, b.getInteger());
    assertEquals(B_INTEGER, br.getInteger());

  }

  /**
   * This is the happy-path test for mapping {@link A} to {@link AResource} with a nested mapping. This test does not
   * check the inherited fields.
   */
  @SuppressWarnings("rawtypes")
  @Test
  public void shouldMapCorrectly() {
    Mapper<A, AResource> mapper = Mapping.from(A.class)
                                         .to(AResource.class)
                                         .omitInSource(A::getMoreInA)
                                         .omitInDestination(AResource::getMoreInAResource)
                                         .reassign(A::getZahlInA)
                                         .to(AResource::getZahlInAResource)
                                         .useMapper(Mapping.from(B.class)
                                                           .to(BResource.class)
                                                           .mapper())
                                         .mapper();

    B b = new B(B_STRING, B_NUMBER, B_INTEGER);
    A a = new A(MORE_IN_A, STRING, NUMBER, INTEGER, ZAHL_IN_A, b);
    a.setZahlInA(ZAHL_IN_A);

    A[] aarr = new A[] {
        a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a
    };

    List<A> aList = Arrays.asList(aarr);
    List<AResource> arCollection = mapper.map(aList);

    // Make sure this is a new collection
    assertFalse((List) aList != (List) arCollection);

    for (AResource ar : arCollection) {
      assertNull(ar.getMoreInAResource());
      assertEquals(STRING, a.getString());
      assertEquals(STRING, ar.getString());
      assertEquals(NUMBER, a.getNumber());
      assertEquals(NUMBER, ar.getNumber());
      assertEquals(INTEGER, a.getInteger());
      assertEquals(INTEGER, ar.getInteger());
      assertEquals(ZAHL_IN_A, a.getZahlInA());
      assertEquals(ZAHL_IN_A, ar.getZahlInAResource());

      BResource br = ar.getB();
      assertEquals(B_STRING, b.getString());
      assertEquals(B_STRING, br.getString());
      assertEquals(B_NUMBER, b.getNumber());
      assertEquals(B_NUMBER, br.getNumber());
      assertEquals(B_INTEGER, b.getInteger());
      assertEquals(B_INTEGER, br.getInteger());

    }
  }

  /**
   * Ensures that the {@link Mapper} detects one more property in the source object that is not omitted by the mapping
   * configuration. The {@link Mapper} is expected to throw a {@link MappingException}.
   */
  @Test(expected = MappingException.class)
  public void oneMoreSourceFieldInA() {
    Mapping.from(AWithOneMoreSourceField.class)
           .to(AResourceWithOneMoreSourceField.class)
           .mapper();
  }

  /**
   * Ensures that an unmatched source field is omitted.
   */
  @Test
  public void oneMoreSourceFieldInAButItIsOmitted() {
    Mapper<AWithOneMoreSourceField, AResourceWithOneMoreSourceField> mapper = Mapping.from(AWithOneMoreSourceField.class)
                                                                                     .to(AResourceWithOneMoreSourceField.class)
                                                                                     .omitInSource(a -> a.getOnlyInA())
                                                                                     .mapper();

    AWithOneMoreSourceField aWithOneMoreSourceField = new AWithOneMoreSourceField(1, 10, "text");
    AResourceWithOneMoreSourceField map = mapper.map(aWithOneMoreSourceField);

    assertEquals(aWithOneMoreSourceField.getText(), map.getText());
    assertEquals(aWithOneMoreSourceField.getZahl(), map.getZahl());
  }

  /**
   * Ensures that the {@link Mapper} detects one more property in the destination object that is not omitted by the
   * mapping
   * configuration. The {@link Mapper} is expected to throw a {@link MappingException}.
   */
  @Test(expected = MappingException.class)
  public void oneMoreDestinationFieldInAResource() {
    Mapping.from(AWithOneMoreDestinationField.class)
           .to(AResourceWithOneMoreDestinationField.class)
           .mapper();
  }

  /**
   * Ensures that an unmatched destination field is omitted.
   */
  @Test
  public void oneMoreDestinationFieldInAResourceButItsOmmited() {
    Mapper<AWithOneMoreDestinationField, AResourceWithOneMoreDestinationField> mapper = Mapping.from(AWithOneMoreDestinationField.class)
                                                                                               .to(AResourceWithOneMoreDestinationField.class)
                                                                                               .omitInDestination(ar -> ar.getOnlyInAResource())
                                                                                               .mapper();

    AWithOneMoreDestinationField aWithOneMoreDestinationField = new AWithOneMoreDestinationField(10, "text");
    AResourceWithOneMoreDestinationField map = mapper.map(aWithOneMoreDestinationField);

    assertEquals(aWithOneMoreDestinationField.getText(), map.getText());
    assertEquals(aWithOneMoreDestinationField.getZahl(), map.getZahl());
  }

  /**
   * Ensures that the mapper performs a correct reassigment of fields.
   */
  @Test
  public void reassign() {
    Mapper<AReassign, AResourceReassign> mapper = Mapping.from(AReassign.class)
                                                         .to(AResourceReassign.class)
                                                         .reassign(AReassign::getFirstNumberInA)
                                                         .to(AResourceReassign::getFirstNumberInAResource)
                                                         .reassign(AReassign::getSecondNumberInA)
                                                         .to(AResourceReassign::getSecondNumberInAResource)
                                                         .mapper();

    AReassign aReassgin = new AReassign(1, 2, 3);
    AResourceReassign map = mapper.map(aReassgin);

    assertEquals(aReassgin.getZahl(), map.getZahl());
    assertEquals(aReassgin.getFirstNumberInA(), map.getFirstNumberInAResource());
    assertEquals(aReassgin.getSecondNumberInA(), map.getSecondNumberInAResource());
  }

  /**
   * Ensures that the mapper does not allow an omitted field in the source to be reassigned.
   */
  @Test(expected = MappingException.class)
  public void reassignAnOmmitedFieldInSource() {
    Mapping.from(AReassign.class)
           .to(AResourceReassign.class)
           .omitInSource(AReassign::getFirstNumberInA)
           .reassign(AReassign::getFirstNumberInA)
           .to(AResourceReassign::getFirstNumberInAResource)
           .reassign(AReassign::getSecondNumberInA)
           .to(AResourceReassign::getSecondNumberInAResource)
           .mapper();
  }

  /**
   * Ensures that the mapper does not allow an omitted field in the destination to be reassigned.
   */
  @Test(expected = MappingException.class)
  public void reassignToAnOmmitedFieldInDestination() {
    Mapping.from(AReassign.class)
           .to(AResourceReassign.class)
           .omitInDestination(ar -> ar.getFirstNumberInAResource())
           .reassign(AReassign::getFirstNumberInA)
           .to(AResourceReassign::getFirstNumberInAResource)
           .reassign(AReassign::getSecondNumberInA)
           .to(AResourceReassign::getSecondNumberInAResource)
           .mapper();
  }

  /**
   * Ensures that the mapper detects an unmapped field in the destination while the all source fields are mapped.
   */
  @Test(expected = MappingException.class)
  public void reassignAndOneDestinationFieldIsUnmapped() {
    Mapping.from(AReassign.class)
           .to(AResourceReassign.class)
           .reassign(AReassign::getFirstNumberInA)
           .to(AResourceReassign::getSecondNumberInAResource)
           .omitInSource(AReassign::getSecondNumberInA)
           .mapper();
  }

}

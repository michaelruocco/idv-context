package uk.co.idv.identity.adapter.eligibility.external.data;

public interface StubDataFactory<T> {

    String getName();

    T getPopulatedData();

    T getEmptyData();

}

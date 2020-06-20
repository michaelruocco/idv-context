package uk.co.idv.context.adapter.eligibility.external.data;

public interface StubDataFactory<T> {

    String getName();

    T getPopulatedData();

    T getEmptyData();

}

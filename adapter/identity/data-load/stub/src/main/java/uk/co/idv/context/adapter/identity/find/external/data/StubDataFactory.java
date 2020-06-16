package uk.co.idv.context.adapter.identity.find.external.data;

public interface StubDataFactory<T> {

    String getName();

    T getPopulatedData();

    T getEmptyData();

}

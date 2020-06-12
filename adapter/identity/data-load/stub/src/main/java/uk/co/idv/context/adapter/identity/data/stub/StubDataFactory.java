package uk.co.idv.context.adapter.identity.data.stub;

public interface StubDataFactory<T> {

    String getName();

    T getPopulatedData();

    T getEmptyData();

}

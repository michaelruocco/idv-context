package uk.co.idv.context.adapter.stub.identity.data;

public interface StubDataFactory<T> {

    String getName();

    T getPopulatedData();

    T getEmptyData();

}

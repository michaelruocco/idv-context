package uk.co.idv.context.adapter.stub.identity.find.data;

public interface StubDataFactory<T> {

    String getName();

    T getPopulatedData();

    T getEmptyData();

}

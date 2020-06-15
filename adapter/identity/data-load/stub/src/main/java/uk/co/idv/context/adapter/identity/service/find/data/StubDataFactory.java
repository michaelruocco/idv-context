package uk.co.idv.context.adapter.identity.service.find.data;

public interface StubDataFactory<T> {

    String getName();

    T getPopulatedData();

    T getEmptyData();

}

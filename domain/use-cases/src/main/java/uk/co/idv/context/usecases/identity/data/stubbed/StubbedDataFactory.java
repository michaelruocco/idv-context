package uk.co.idv.context.usecases.identity.data.stubbed;

public interface StubbedDataFactory<T> {

    String getName();

    T getPopulatedData();

    T getEmptyData();

}

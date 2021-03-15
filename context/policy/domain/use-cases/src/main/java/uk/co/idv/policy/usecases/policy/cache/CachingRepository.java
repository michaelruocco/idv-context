package uk.co.idv.policy.usecases.policy.cache;

public interface CachingRepository extends Runnable {

    default void run() {
        refresh();
    }

    void refresh();

}

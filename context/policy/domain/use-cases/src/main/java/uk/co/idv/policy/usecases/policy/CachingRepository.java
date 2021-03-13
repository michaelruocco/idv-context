package uk.co.idv.policy.usecases.policy;

public interface CachingRepository extends Runnable {

    default void run() {
        refresh();
    }

    void refresh();

}

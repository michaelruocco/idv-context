package uk.co.idv.context.entities.context.method.otp.delivery;

import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;

public class DeliveryMethodComparator implements Comparator<DeliveryMethod> {

    @Override
    public int compare(DeliveryMethod method1, DeliveryMethod method2) {
        int lastUpdatedResult = byLastUpdated(method1, method2);
        if (lastUpdatedResult == 0) {
            return method1.getValue().compareTo(method2.getValue());
        }
        return lastUpdatedResult;
    }

    private int byLastUpdated(DeliveryMethod method1, DeliveryMethod method2) {
        Optional<Instant> updated1 = method1.getLastUpdated();
        Optional<Instant> updated2 = method2.getLastUpdated();
        if (updated1.isPresent() && updated2.isPresent()) {
            return updated2.get().compareTo(updated1.get());
        }
        if (updated2.isPresent()) {
            return 1;
        }
        if (updated1.isPresent()) {
            return -1;
        }
        return 0;
    }

}

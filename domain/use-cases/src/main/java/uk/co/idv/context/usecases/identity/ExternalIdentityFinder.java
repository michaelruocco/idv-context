package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.data.DataFutures;
import uk.co.idv.context.usecases.identity.data.DataSupplierFactory;

import java.util.function.Supplier;

@Builder
@RequiredArgsConstructor
@Slf4j
public class ExternalIdentityFinder implements IdentityFinder {

    private final DataSupplierFactory factory;
    private final AsyncDataLoader dataLoader;

    @Override
    public Identity find(FindIdentityRequest request) {
        Aliases aliases = loadAliases(request);
        FindIdentityRequest updatedRequest = request.setAliases(aliases);
        DataFutures futures = dataLoader.loadData(updatedRequest);
        return Identity.builder()
                .aliases(updatedRequest.getAliases())
                .country(updatedRequest.getCountry())
                .phoneNumbers(futures.getPhoneNumbersNow())
                .emailAddresses(futures.getEmailAddressesNow())
                .build();
    }

    private Aliases loadAliases(FindIdentityRequest request) {
        Supplier<Aliases> supplier = factory.aliasesSupplier(request);
        Aliases loadedAliases = supplier.get();
        return loadedAliases.add(request.getProvidedAlias());
    }

}

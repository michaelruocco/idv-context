package uk.co.idv.app.spring.config.repository.caching;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.policy.usecases.policy.CachingRepository;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public class CachingPolicyRepositoryRefreshTask implements Runnable {

    private final Collection<CachingRepository> cachingRepositories;

    public void run() {
        log.info("refreshing {} caching policy repositories", cachingRepositories.size());
        cachingRepositories.forEach(CachingRepository::refresh);
    }

}

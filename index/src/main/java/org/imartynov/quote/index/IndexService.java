package org.imartynov.quote.index;

import org.springframework.stereotype.Service;

@Service
public class IndexService {

    public long [] findSubscribedUsers(String quote) {
        // search in index ...
        return new long [] {1L, 2L, 3L, 5L, 15L};
    }
}

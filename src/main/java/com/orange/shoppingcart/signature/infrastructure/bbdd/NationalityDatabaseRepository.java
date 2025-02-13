package com.orange.shoppingcart.signature.infrastructure.bbdd;

import com.orange.shoppingcart.signature.domain.ports.output.NationalityPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class NationalityDatabaseRepository implements NationalityPort {

    private final NationalityJPARepository nationalityJPARepository;

    @Override
    @Cacheable(value = "nationalityCache", key = "#nationality")
    public Boolean existNationality(String nationality) {
        return nationalityJPARepository.findByNationality(nationality)
                .isPresent() ? Boolean.TRUE : Boolean.FALSE;
    }
}

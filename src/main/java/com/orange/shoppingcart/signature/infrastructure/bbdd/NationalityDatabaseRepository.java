package com.orange.shoppingcart.signature.infrastructure.bbdd;

import com.orange.shoppingcart.signature.domain.ports.output.NationalityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NationalityDatabaseRepository implements NationalityPort {

    private final NationalityJPARepository nationalityJPARepository;

    @Override
    public Boolean existNationality(String nationality) {
        return nationalityJPARepository.findByNationality(nationality)
                .isPresent() ? Boolean.TRUE : Boolean.FALSE;
    }
}

/*
 * InmemoryBeerService.java
 *
 * Created on 2019-02-09, 7:30
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-09.
 */
@Service
public class InmemoryBeerService implements BeerService {

  private final BeerRepository beerRepository;

  public InmemoryBeerService(BeerRepository beerRepository) {
    this.beerRepository = beerRepository;
  }

  @Override
  public List<Beer> getBeers() {
    return StreamSupport.stream(beerRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public Beer insertBear(Beer beerToInsert) {
    return beerRepository.save(beerToInsert);
  }
}

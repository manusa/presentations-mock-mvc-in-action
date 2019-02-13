/*
 * InmemoryBeerService.java
 *
 * Created on 2019-02-09, 7:30
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import com.marcnuri.demo.mockmvcinaction.web.exception.BusinessException;
import com.marcnuri.demo.mockmvcinaction.web.exception.NonUniqueException;
import com.marcnuri.demo.mockmvcinaction.web.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
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
    return streamAll().collect(Collectors.toList());
  }

  @Override
  public Beer getBeer(String externalId) {
    return Optional.ofNullable(beerRepository.findBeerByExternalId(externalId))
        .orElseThrow(() -> new NotFoundException(String.format("Beer %s was not found", externalId)));
  }

  @Override
  public Beer insertBeer(Beer beerToInsert) {
    return save(beerToInsert);
  }

  @Override
  public Beer updateBeer(@NonNull String externalId, @NonNull Beer beerToUpdate) {
    if (!externalId.equals(beerToUpdate.getExternalId())) {
      throw new BusinessException("ExternalId mismatch", HttpStatus.BAD_REQUEST);
    }
    final Beer existingBeer = getBeer(externalId);
    beerToUpdate.setId(existingBeer.getId());
    return save(beerToUpdate);
  }

  @Override
  public void removeBeer(String externalId) {
    beerRepository.delete(getBeer(externalId));
  }

  /**
   * Checks data integrity for the beer to save.
   *
   * <p> Saves a beer and adds audit information.
   *
   * @param beerToSave the beer to save
   * @return the saved beer
   */
  private Beer save(Beer beerToSave) {
    if (streamAll()
        .filter(b -> b.getExternalId().equals(beerToSave.getExternalId()))
        .anyMatch(b-> beerToSave.getId() == null || !b.getId().equals(beerToSave.getId()))) {
      throw new NonUniqueException(String.format("Beer with externalId %s already exists", beerToSave.getExternalId()));
    }
    beerToSave.setLastModified(LocalDateTime.now());
    return beerRepository.save(beerToSave);
  }

  /**
   * Returns a {@link Stream} with all the registered {@link Beer}s in the System
   *
   * @return a stream of Beers ;)
   */
  private Stream<Beer> streamAll() {
    return StreamSupport.stream(beerRepository.findAll().spliterator(), false);
  }
}

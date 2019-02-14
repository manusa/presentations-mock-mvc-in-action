/*
 * BeerService.java
 *
 * Created on 2019-02-09, 7:29
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import com.marcnuri.demo.mockmvcinaction.web.exception.NotFoundException;
import java.util.List;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-09.
 */
public interface BeerService {

  /**
   * Retrieves a list of registered {@link Beer}s
   *
   * @return the list of beers
   */
  List<Beer> getBeers();

  /**
   * Gets the {@link Beer} for the provided externalId or throws {@link NotFoundException} if the
   * beer doesn't exist.
   *
   * @param externalId for the Beer to find
   * @return the Beer found for the provided externalId
   */
  Beer getBeer(String externalId);

  /**
   * Registers a new {@link Beer} in the system
   *
   * @return the newly inserted beer
   */
  Beer insertBeer(Beer beerToInsert);

  /**
   * Updates an already registered {@link Beer} in the system.
   *
   * @param externalId of the registered beer
   * @param beerToUpdate the beer to update
   * @return the updated beer
   */
  Beer updateBeer(String externalId, Beer beerToUpdate);

  /**
   * Removes an already registered {@link Beer} with the provided externalId from the system.
   */
  void removeBeer(String externalId);
}

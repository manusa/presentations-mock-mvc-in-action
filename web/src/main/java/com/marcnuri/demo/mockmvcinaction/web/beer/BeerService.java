/*
 * BeerService.java
 *
 * Created on 2019-02-09, 7:29
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import java.util.List;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-09.
 */
interface BeerService {

  /**
   * Retrieves a list of registered beers
   *
   * @return the list of beers
   */
  List<Beer> getBeers();

  /**
   * Registers a new Beer in the system
   *
   * @return the newly inserted beer
   */
  Beer insertBear(Beer beerToInsert);

}

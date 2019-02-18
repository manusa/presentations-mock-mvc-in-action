/*
 * BeerResourceStrictUnitTest.java
 *
 * Created on 2019-02-16, 20:03
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-16.
 */
public class BeerResourceStrictUnitTest {

  private BeerService mockBeerService;
  private BeerResource beerResource;

  @Before
  public void setUp() {
    mockBeerService = Mockito.mock(BeerService.class);
    beerResource = new BeerResource(mockBeerService);
  }

  @After
  public void tearDown() {
    beerResource = null;
    mockBeerService = null;
  }

  @Test
  public void updateBeerAsXML_validBeer_shouldReturnUpdatedBeer(){
    // When
    beerResource.updateBeerAsXML("OST.01", new Beer());

    // Then
    verify(mockBeerService, times(1))
        .updateBeer(Mockito.eq("OST.01"), Mockito.any(Beer.class));
  }
}

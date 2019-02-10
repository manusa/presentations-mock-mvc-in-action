/*
 * BeerResource.java
 *
 * Created on 2019-02-09, 7:18
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-09.
 */
@RestController
@RequestMapping("/beers")
public class BeerResource {

  private BeerService beerService;

  @Autowired
  public BeerResource(BeerService beerService) {
    this.beerService = beerService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<Beer> getBeersAsJson() {
    return getBeers();
  }

  @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
  public List<Beer> getBeersAsXML() {
    return getBeers();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Beer insertBeer(@Validated  @RequestBody Beer beer) {
    return beerService.insertBeer(beer);
  }

  @PutMapping(
      path = "/{externalId}",
      headers = {"Special-Header=XML Babel"},
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Beer updateBeerAsXML(
      @PathVariable("externalId") String externalId, @Validated @RequestBody Beer beer) {

    return beerService.insertBeer(beer);
  }

  private List<Beer> getBeers() {
    return beerService.getBeers();
  }
}

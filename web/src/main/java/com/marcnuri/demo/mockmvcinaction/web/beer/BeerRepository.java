/*
 * BeerRepository.java
 *
 * Created on 2019-02-09, 7:27
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-09.
 */
public interface BeerRepository extends CrudRepository<Beer, String> {
}

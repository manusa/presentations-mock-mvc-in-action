/*
 * Beer.java
 *
 * Created on 2019-02-09, 7:19
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-09.
 */
@KeySpace("beers")
public class Beer {
  @Id
  private String id;
  private String name;
  private BeerType type;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BeerType getType() {
    return type;
  }

  public void setType(BeerType type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Beer beer = (Beer) o;
    return Objects.equals(id, beer.id) &&
        Objects.equals(name, beer.name) &&
        type == beer.type;
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, type);
  }
}

/*
 * Beer.java
 *
 * Created on 2019-02-09, 7:19
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.keyvalue.annotation.KeySpace;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-09.
 */
@KeySpace("beers")
public class Beer {
  @Id
  @JsonIgnore
  private String id;
  @NotNull
  private String externalId;
  @NotNull
  @Size(min = 1)
  private String name;
  @NotNull
  private BeerType type;
  @LastModifiedDate
  private LocalDateTime lastModified;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
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

  public LocalDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(LocalDateTime lastModified) {
    this.lastModified = lastModified;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Beer)) return false;

    Beer beer = (Beer) o;

    if (getId() != null ? !getId().equals(beer.getId()) : beer.getId() != null) return false;
    if (getExternalId() != null ? !getExternalId().equals(beer.getExternalId()) : beer.getExternalId() != null)
      return false;
    if (getName() != null ? !getName().equals(beer.getName()) : beer.getName() != null) return false;
    if (getType() != beer.getType()) return false;
    return getLastModified() != null ? getLastModified().equals(beer.getLastModified()) : beer.getLastModified() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getExternalId() != null ? getExternalId().hashCode() : 0);
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getType() != null ? getType().hashCode() : 0);
    result = 31 * result + (getLastModified() != null ? getLastModified().hashCode() : 0);
    return result;
  }
}

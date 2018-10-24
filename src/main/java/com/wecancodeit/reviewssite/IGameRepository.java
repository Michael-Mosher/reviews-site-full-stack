package com.wecancodeit.reviewssite;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

interface IGameRepository extends CrudRepository<Game, Long>
{
  Collection<Game> findByTagsContains(Tag tag);
  Collection<Game> findByTagsId(long lFirstTag);
}

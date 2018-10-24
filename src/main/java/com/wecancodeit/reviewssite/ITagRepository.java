package com.wecancodeit.reviewssite;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface ITagRepository extends CrudRepository<Tag, Long>
{

	Collection<Tag> findByGamesContains(Game game);

}

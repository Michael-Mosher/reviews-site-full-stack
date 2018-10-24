package com.wecancodeit.reviewssite;

import org.springframework.data.repository.CrudRepository;

public interface IReviewRepository extends CrudRepository<Review, Long>
{
  
}

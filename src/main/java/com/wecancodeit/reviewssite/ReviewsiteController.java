package com.wecancodeit.reviewssite;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class ReviewsiteController {
  
  @Resource
  IReviewRepository oReviewRepository;
  @Resource
  IGameRepository oGameRepository;
  @Resource
  ITagRepository oTagRepository;
//  @RequestMapping("/reviews")
//  public String findAllReviews(Model model)
//  {
//    model.addAttribute("reviews", oReviewRepository.getAllEntries());
//	return "reviews";
//  }
//  
//  @RequestMapping("/review")
//  public String findAReview(@RequestParam(value="reviewId", required=false, 
//	      defaultValue="")String reviewId, Model model)
//  {
//	  model.addAttribute("review", oReviewRepository.getEntry(new Long(reviewId)));
//	  return "review";
//  }
  @RequestMapping("/game")
	public String findOneGame(@RequestParam(value="gameId") long lGameId, Model model) throws GameNotFoundException
	{
	  Optional<Game> game = oGameRepository.findById(lGameId);
	  if(game.isPresent()){
		model.addAttribute("gameQueried", game.get());
		Collection<Tag> oAssociatedTags = oTagRepository.findByGamesContains(game.get());
		Collection<Review> oAssociatedReview = game.get().getReviews();
		model.addAttribute("associatedTags", oAssociatedTags);
		model.addAttribute("associatedReviews", oAssociatedReview);
		return "game";
	  } else {
		  throw new GameNotFoundException();
	  }
	}

	@RequestMapping("/games")
	public String findAllGames(
			Model model/*,
			@RequestParam(value="tagName", defaultValue="", required=false) String sTagName,
			@RequestParam(value="tagId", defaultValue="", required=false) long lTagId*/
	)
	{
	  Collection<Game> oQueryResult;
	  /*if(sTagName.length()>0) {
		oQueryResult = oGameRepository.findByTagsContains(new Tag(sTagName));
	  } else if(new Long(lTagId).longValue() > 0L) {
		oQueryResult = oGameRepository.findByTagsId(new Long(lTagId));
	  } else */{
		oQueryResult = (Collection<Game>) oGameRepository.findAll();
	  }
	  model.addAttribute("gamesQueried", oQueryResult);
	  return "games";
	}
	
  @RequestMapping("/tag")
  public String findOneTag(@RequestParam(value="tagId") long lTagId, Model model) throws TagNotFoundException
  {
	  Optional<Tag> tag = oTagRepository.findById(lTagId);
	  if(tag.isPresent()){
	  model.addAttribute("tagQueried", tag.get());
	  return "tag";
	} else {
	  throw new TagNotFoundException();
	}
  }

  @RequestMapping("/tags")
  public String findAllTags(Model model)
  {
	Collection<Tag> oQueryResult = (Collection<Tag>) oTagRepository.findAll();
	model.addAttribute("tagsQueried", oQueryResult);
	return "tags";
  }
}

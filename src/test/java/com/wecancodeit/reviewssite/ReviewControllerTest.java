package com.wecancodeit.reviewssite;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static java.util.Arrays.asList;

import javax.annotation.Resource;

public class ReviewControllerTest {
  @InjectMocks
  private ReviewsiteController oControllerTested;
  @Mock
  private Game oGameMockOne;
  @Mock
  private Game oGameMockTwo;
  @Mock
  private Review oReviewMockOne;
  @Mock
  private Tag oTagMockOne;
  @Mock
  private Tag oTagMockTwo;
  @Mock
  private Review oReviewMockTwo;
  @Mock
  private IGameRepository gameRepo;
  @Mock
  private ITagRepository tagRepo;
  @Mock
  private IReviewRepository reviewRepo;
  @Mock
  private Model oMockModel;
  
  @Before
  public void setUp()
  {
	MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void shouldAddSingleGameToModel() throws GameNotFoundException
  {
	long lGameId = 1;
	when(gameRepo.findById(lGameId)).thenReturn(
			Optional.of(oGameMockOne));
//	when(gameRepo.findById(lGameId)).thenReturn(
//			Arrays.asList(oTagMockOne, oTagMockTwo));
	oControllerTested.findOneGame(lGameId, oMockModel);
	verify(oMockModel).addAttribute("gameQueried", oGameMockOne);
  }
  
  @Test
  public void shouldAddAllGamesToModel()
  {
	Collection<Game> oExpectedCollection = Arrays.asList(new Game[] { oGameMockOne, oGameMockTwo});
	when(gameRepo.findAll()).thenReturn(oExpectedCollection);
	oControllerTested.findAllGames(oMockModel/*,"", 0*/);
	verify(oMockModel).addAttribute("gamesQueried", oExpectedCollection);
  }
  
  @Test
  public void shouldAddSingleTagToModel() throws TagNotFoundException
  {
	long lTagId = 1;
	when(tagRepo.findById(lTagId)).thenReturn(
			Optional.of(oTagMockOne));
	oControllerTested.findOneTag(lTagId, oMockModel);
	verify(oMockModel).addAttribute("tagQueried", oTagMockOne);
  }
  
  @Test
  public void shouldAddAllTagsToModel()
  {
	Collection<Tag> oExpectedCollection = Arrays.asList(new Tag[] { oTagMockOne, oTagMockTwo});
	when(tagRepo.findAll()).thenReturn(oExpectedCollection);
	oControllerTested.findAllTags(oMockModel);
	verify(oMockModel).addAttribute("tagsQueried", oExpectedCollection);
  }
  
/*  @Test
  public void shouldBeOkForAllReviews() throws Exception
  {
	mvc.perform(get("/reviews")).andExpect(status().isOk());
  }
  
  @Test
  public void shouldPutAllReviewsIntoModel() throws Exception
  {
	Collection<Review> oReviewCollection = asList(firstReview, secondReview);
	when(reviewRepo.getAllEntries()).thenReturn(oReviewCollection);
	mvc.perform(get("/reviews")).andExpect(model().attribute("reviewsQueried", is(oReviewCollection)));
  }
  
//  @Test
//  public void shouldPutReviewId100IntoModel() throws Exception
//  {
//	mvc.perform(get("/review?reviewId=100")).andExpect(status().isOk());
//  }
  
  @Test
  public void shouldRouteToSingleReviewView() throws Exception
  {
	when(reviewRepo.getEntry(100)).thenReturn(firstReview);
	mvc.perform(get("/review?reviewId=100")).andExpect(view().name(is("review")));
  }
  
  @Test
  public void shouldPutSingleReviewIntoModel() throws Exception
  {
	when(reviewRepo.getEntry(101)).thenReturn(secondReview);
	mvc.perform(get("/review?reviewId=101")).andExpect(model().attribute("review", is(secondReview)));
  }
  
  @Test
  public void shouldNotPutSingleReviewIntoModel() throws Exception
  {
	when(reviewRepo.getEntry(500L)).thenReturn(secondReview);
    mvc.perform(get("/review?reviewId=500")).andExpect(model().attribute("reivew", is(nullValue())));
  }*/
}

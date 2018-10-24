package com.wecancodeit.reviewssite;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;

import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewsiteController.class)
public class MvcTest {
	@Resource
	private MockMvc mvc;
	@MockBean
	private IGameRepository gameRepo;
	@MockBean
	private ITagRepository tagRepo;
	@MockBean
	private IReviewRepository reviewRepo;
	@Mock
	private Game oMockGame;
	@Mock
	private Game oAnotherMockGame;
  @Mock
  private Tag oMockTag;
  @Mock
  private Tag oAnotherMockTag;
  @Mock
  private Review oMockReview;
  @Mock
  private Review oAnotherMockReview;
  
  @Test
  public void shouldBeOkForAllGames() throws Exception
  {
	Collection<Game> oExpectedCollection = Arrays.asList(new Game[] {oMockGame, oAnotherMockGame});
	when(gameRepo.findAll()).thenReturn(oExpectedCollection);
	mvc.perform(get("/games?tagName=&tagId=")).andExpect(status().isOk());
  }
  
  @Test(expected = GameNotFoundException.class)
  public void assertGamesStatusNotFound() throws Exception
  {
    mvc.perform(get("/games")).andExpect(status().isNotFound());
  }
  
  @Test
  public void shouldRouteToAllGameView() throws Exception
  {
    Collection<Game> oExpectedCollection = Arrays.asList(new Game[] {oMockGame, oAnotherMockGame});
    when(gameRepo.findAll()).thenReturn(oExpectedCollection);
    mvc.perform(get("/games")).andExpect(view().name(is("games")));
  }
  
  @Test
  public void shouldBeOkForGame() throws Exception
  {
	when(gameRepo.findById(1L)).thenReturn(Optional.of(oMockGame));
	mvc.perform(get("/game?gameId=1")).andExpect(status().isOk());
  }
  
  @Test
  public void assertRouteToGameView() throws Exception
  {
	  when(gameRepo.findById(1L)).thenReturn(Optional.of( oMockGame));
    mvc.perform(get("/game?gameId=1")).andExpect(view().name(is("game")));
  }
  
  @Test(expected = GameNotFoundException.class)
  public void shouldNotBeOkForSingleGame() throws Exception
  {
    mvc.perform(get("/game?gameId=1")).andExpect(status().isNotFound());	
  }
  
  @Test
  public void assertSingleGameAddedToModel() throws Exception
  {
    long lGameId = 1;
    when(gameRepo.findById(new Long(lGameId))).thenReturn(Optional.of(oMockGame));
    mvc.perform(get("/game?gameId=1")).andExpect(model().attribute("gameQueried", is(oMockGame)));
  }
  
  @Test
  public void shouldBeOkForAllTags() throws Exception
  {
	mvc.perform(get("/tags")).andExpect(status().isOk());
  }
  
  @Test
  public void assertRouteToTagsView() throws Exception
  {
    mvc.perform(get("/tags")).andExpect(view().name(is("tags")));
  }
  
  @Test
  public void shouldRouteToAllTagsView() throws Exception
  {
    Collection<Tag> oExpectedCollection = Arrays.asList(new Tag[] {oMockTag, oAnotherMockTag});
    when(tagRepo.findAll()).thenReturn(oExpectedCollection);
    mvc.perform(get("/tags")).andExpect(view().name(is("tags")));
  }
  
  @Test
  public void shouldBeOkForTag() throws Exception
  {
	when(tagRepo.findById(1L)).thenReturn(Optional.of(oMockTag));
	mvc.perform(get("/tag?tagId=1")).andExpect(status().isOk());
  }
  
  @Test
  public void assertRouteToTagView() throws Exception
  {
	when(tagRepo.findById(1L)).thenReturn(Optional.of(oMockTag));
    mvc.perform(get("/tag?tagId=1")).andExpect(view().name(is("tag")));
  }
  
  @Test(expected = TagNotFoundException.class)
  public void shouldNotBeOkForSingleTag() throws Exception
  {
    mvc.perform(get("/tag?tagId=1")).andExpect(status().isNotFound());	
  }
  
  @Test
  public void assertSingleTagAddedToModel() throws Exception
  {
    long lTagId = 1;
    when(tagRepo.findById(new Long(lTagId))).thenReturn(Optional.of(oMockTag));
    mvc.perform(get("/tag?tagId=1")).andExpect(model().attribute("tagQueried", is(oMockTag)));
  }
  
//  @Test
//  public void shouldNotBeOkForSingleCourse() throws Exception
//  {
//	mvc.perform(get("/course?id=1")).andExpect(status().isNotFound());	
//  }
//  
//  @Test
//  public void shouldRouteToAllCourseView() throws Exception
//  {
//	Collection<Course> oExpectedCollection = Arrays.asList(new Course[] {oMockCourse, oAnotherMockCourse});
//	when(courseRepo.findAll()).thenReturn(oExpectedCollection);
//	mvc.perform(get("/courses")).andExpect(view().name(is("courses")));
//  }
//  
//  @Test
//  public void assertSingleCourseAddedToModel() throws Exception
//  {
//	long lCourseId = 1;
//	when(courseRepo.findById(new Long(lCourseId))).thenReturn(Optional.of(oMockCourse));
//	mvc.perform(get("/course?id=1")).andExpect(model().attribute("courseModel", is(oMockCourse)));
//  }
//  
//  @Test
//  public void assertRouteToAllCourseView() throws Exception
//  {
//	mvc.perform(get("/courses")).andExpect(view().name(is("courses")));
//  }
//  
//  @Test
//  public void shouldBeOkForAllCourse() throws Exception
//  {
//	mvc.perform(get("/courses")).andExpect(status().isOk());
//  }
//  
//  /* org.springframework.web.util.NestedServletException: Request processing failed; nested exception is java.lang.IllegalStateException: Optional long parameter 'lTopicId' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type.
//	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:982)
//	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:866)
//	at javax.servlet.http.HttpServlet.service(HttpServlet.java:635)
//	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:851)
//	at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:71)
//	at javax.servlet.http.HttpServlet.service(HttpServlet.java:742)
//	at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:166)
//	at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:133)
//	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
//	at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:133)
//	at org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:109)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
//	at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:133)
//	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
//	at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:133)
//	at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:165)
//	at org.wecancodeit.coursetopicstextbooksjpa.MockMvcTest.shouldRouteToSingleTopicView(MockMvcTest.java:101)
//	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
//	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
//	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
//	at java.lang.reflect.Method.invoke(Method.java:498)
//	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
//	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
//	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
//	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
//	at org.springframework.test.context.junit4.statements.RunBeforeTestExecutionCallbacks.evaluate(RunBeforeTestExecutionCallbacks.java:73)
//	at org.springframework.test.context.junit4.statements.RunAfterTestExecutionCallbacks.evaluate(RunAfterTestExecutionCallbacks.java:83)
//	at org.springframework.test.context.junit4.statements.RunBeforeTestMethodCallbacks.evaluate(RunBeforeTestMethodCallbacks.java:75)
//	at org.springframework.test.context.junit4.statements.RunAfterTestMethodCallbacks.evaluate(RunAfterTestMethodCallbacks.java:86)
//	at org.springframework.test.context.junit4.statements.SpringRepeat.evaluate(SpringRepeat.java:84)
//	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
//	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:251)
//	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:97)
//	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
//	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
//	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
//	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
//	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
//	at org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks.evaluate(RunBeforeTestClassCallbacks.java:61)
//	at org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks.evaluate(RunAfterTestClassCallbacks.java:70)
//	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
//	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.run(SpringJUnit4ClassRunner.java:190)
//	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86)
//	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
//	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:538)
//	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:760)
//	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:460)
//	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:206)
//Caused by: java.lang.IllegalStateException: Optional long parameter 'lTopicId' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type.
//	at org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver.handleNullValue(AbstractNamedValueMethodArgumentResolver.java:244)
//	at org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver.resolveArgument(AbstractNamedValueMethodArgumentResolver.java:114)
//	at org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:124)
//	at org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:161)
//	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:131)
//	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102)
//	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:891)
//	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797)
//	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
//	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:991)
//	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925)
//	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974)
//  @Test
//  public void shouldRouteToSingleTopicView() throws Exception
//  {
//	long lTopicId = 1;
//	when(topicRepo.findById(new Long(lTopicId))).thenReturn(Optional.of(oMockTopic));
//	mvc.perform(get("/topic?id="+lTopicId)).andExpect(view().name(is("topic")));
//  }*/
//  
//  @Test
//  public void shouldRouteToSingleTopicView() throws Exception
//  {
//    Long arbitraryTopicId = new Long(42L);
//    when(topicRepo.findById(arbitraryTopicId)).thenReturn(Optional.of(oMockTopic));
//    mvc.perform(get("/topic?id=42")).andExpect(view().name("topic"));
//  }
//  
//  @Test
//  public void shouldBeOkForSingleTopic() throws Exception
//  {
//	long lTopicId = 1;
//	when(topicRepo.findById(lTopicId)).thenReturn(Optional.of(oMockTopic));
//	mvc.perform(get("/topic?id=1")).andExpect(status().isOk());
//  }
//  
//  @Test
//  public void shouldNotBeOkForSingleTopic() throws Exception
//  {
//	mvc.perform(get("/topic?id=0")).andExpect(status().isNotFound());	
//  }
//  
//  @Test
//  public void shouldRouteToAllTopicView() throws Exception
//  {
//	Collection<Topic> oExpectedCollection = Arrays.asList(new Topic[] {oMockTopic, oAnotherMockTopic});
//	when(topicRepo.findAll()).thenReturn(oExpectedCollection);
//	mvc.perform(get("/topics")).andExpect(view().name(is("topics")));
//  }
//  
//  @Test
//  public void assertSingleTopicAddedToModel() throws Exception
//  {
//	long lTopicId = 1L;
//	when(topicRepo.findById(lTopicId)).thenReturn(Optional.of(oMockTopic));
//	mvc.perform(get("/topic?id=1")).andExpect(model().attribute("topicModel", is(oMockTopic)));
//  }
//  
//  @Test
//  public void assertRouteToAllTopicView() throws Exception
//  {
//	  mvc.perform(get("/topics")).andExpect(view().name(is("topics")));
//  }
//  
//  @Test
//  public void shouldBeOkForAllTopic() throws Exception
//  {
//	mvc.perform(get("/topics")).andExpect(status().isOk());
//  }
//  
//  @Test
//  public void shouldRouteToSingleTextbookView() throws Exception
//  {
//	long lTbId = 1;
//	when(tbRepo.findById(lTbId)).thenReturn(Optional.of(oMockTb));
//	mvc.perform(get("/textbook?id=1")).andExpect(view().name(is("textbook")));
//  }
//  
//  @Test
//  public void shouldBeOkForSingleTextbook() throws Exception
//  {
//	long lTbId = 1;
//	when(tbRepo.findById(lTbId)).thenReturn(Optional.of(oMockTb));
//	mvc.perform(get("/textbook?id=1")).andExpect(status().isOk());
//  }
//  
//  @Test
//  public void shouldNotBeOkForSingleTextbook() throws Exception
//  {
//	mvc.perform(get("/textbook?id=1")).andExpect(status().isNotFound());	
//  }
//  
//  @Test
//  public void shouldRouteToAllTextbookView() throws Exception
//  {
//	Collection<Textbook> oExpectedCollection = Arrays.asList(new Textbook[] {oMockTb, oAnotherMockTb});
//	when(tbRepo.findAll()).thenReturn(oExpectedCollection);
//	mvc.perform(get("/textbooks")).andExpect(view().name(is("textbooks")));
//  }
//  
//  @Test
//  public void assertSingleTextbookAddedToModel() throws Exception
//  {
//	long lTbId = 1;
//	when(tbRepo.findById(lTbId)).thenReturn(Optional.of(oMockTb));
//	mvc.perform(get("/textbook?id=1")).andExpect(model().attribute("textbookModel", is(oMockTb)));
//  }
//  
//  @Test
//  public void assertRouteToAllTextbookView() throws Exception
//  {
//	  mvc.perform(get("/textbooks")).andExpect(view().name(is("textbooks")));
//  }
//  
//  @Test
//  public void shouldBeOkForAllTextbook() throws Exception
//  {
//	mvc.perform(get("/textbooks")).andExpect(status().isOk());
//  }
}

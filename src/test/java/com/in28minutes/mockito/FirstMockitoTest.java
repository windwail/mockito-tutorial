package com.in28minutes.mockito;

import static com.sun.activation.registries.LogSupport.log;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.in28minutes.business.TodoBusinessImpl;
import com.in28minutes.data.api.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


@Slf4j
public class FirstMockitoTest {

	@Test
	public void test() {

		List<String> arr = Arrays.asList("Spring new","Spring","Spring old");

		TodoService ts = mock(TodoService.class);
		when(ts.retrieveTodos("myuser")).thenReturn(arr);
		log.info(String.valueOf(ts.retrieveTodos("myuser")));

		TodoBusinessImpl todoBusiness = new TodoBusinessImpl(ts);

		List<String> rez = todoBusiness.retrieveTodosRelatedToSpring("myuser");

		Assert.assertArrayEquals(arr.toArray(), rez.toArray());

		assertTrue(true);
	}

	@Test
	public void test2() {

		TodoService ts = mock(TodoService.class);

		given(ts.retrieveTodos("Dummy"))
				.willReturn(
						Arrays.asList(
								"Spring is great!",
								"Not containing s*ring"
						));

		TodoBusinessImpl tb = new TodoBusinessImpl(ts);

		tb.deleteTodosNotRelatedToSpring("Dummy");

		verify(ts).deleteTodo(anyString()); // verify that deleteTodo was called.

		verify(ts, never()).deleteTodo("Spring is great!"); // verify never called.

		verify(ts, times(1)).deleteTodo(anyString()); // how many times

		verify(ts, atLeast(1)).deleteTodo(anyString()); // atleas 1 time
	}

}

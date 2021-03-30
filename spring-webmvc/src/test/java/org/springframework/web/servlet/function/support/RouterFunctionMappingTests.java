/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.servlet.function.support;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.testfixture.servlet.MockHttpServletRequest;
import org.springframework.web.util.ServletRequestPathUtils;
import org.springframework.web.util.pattern.PathPatternParser;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Arjen Poutsma
 */
class RouterFunctionMappingTests {

	private final MockHttpServletRequest request = new MockHttpServletRequest("GET", "/match");

	private List<HttpMessageConverter<?>> messageConverters = Collections.emptyList();

	@Test
	public void normal() throws Exception {
		HandlerFunction<ServerResponse> handlerFunction = request -> ServerResponse.ok().build();
		RouterFunction<ServerResponse> routerFunction = request -> Optional.of(handlerFunction);

		RouterFunctionMapping mapping = new RouterFunctionMapping(routerFunction);
		mapping.setMessageConverters(this.messageConverters);
		ServletRequestPathUtils.parseAndCache(this.request);

		HandlerExecutionChain result = mapping.getHandler(this.request);

		assertThat(result).isNotNull();
		assertThat(result.getHandler()).isSameAs(handlerFunction);
	}

	@Test
	public void noMatch() throws Exception {
		RouterFunction<ServerResponse> routerFunction = request -> Optional.empty();

		RouterFunctionMapping mapping = new RouterFunctionMapping(routerFunction);
		mapping.setMessageConverters(this.messageConverters);
		ServletRequestPathUtils.parseAndCache(this.request);

		HandlerExecutionChain result = mapping.getHandler(this.request);

		assertThat(result).isNull();
	}

	@Test
	public void changeParser() throws Exception {
		HandlerFunction<ServerResponse> handlerFunction = request -> ServerResponse.ok().build();
		RouterFunction<ServerResponse> routerFunction = RouterFunctions.route()
				.GET("/foo", handlerFunction)
				.POST("/bar", handlerFunction)
				.build();

		RouterFunctionMapping mapping = new RouterFunctionMapping(routerFunction);
		mapping.setMessageConverters(this.messageConverters);
		PathPatternParser patternParser = new PathPatternParser();
		patternParser.setCaseSensitive(false);
		mapping.setPatternParser(patternParser);
		mapping.afterPropertiesSet();

		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/FOO");
		ServletRequestPathUtils.parseAndCache(request);

		HandlerExecutionChain result = mapping.getHandler(request);

		assertThat(result).isNotNull();
		assertThat(result.getHandler()).isSameAs(handlerFunction);
	}

}

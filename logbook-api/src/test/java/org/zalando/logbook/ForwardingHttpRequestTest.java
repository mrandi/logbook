package org.zalando.logbook;

/*
 * #%L
 * Logbook: Core
 * %%
 * Copyright (C) 2015 Zalando SE
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.zalando.logbook.Origin.REMOTE;

public final class ForwardingHttpRequestTest {

    private final HttpRequest unit = new ForwardingHttpRequest() {
        @Override
        protected HttpRequest delegate() {
            return mockHttpRequest();
        }
    };

    @Test
    public void shouldDelegate() throws IOException {
        assertThat(unit.getOrigin(), is(REMOTE));
        assertThat(unit.getRemote(), is("127.0.0.1"));
        assertThat(unit.getMethod(), is("GET"));
        assertThat(unit.getRequestUri(), is("http://localhost/"));
        assertThat(unit.getScheme(), is("http"));
        assertThat(unit.getHost(), is("localhost"));
        assertThat(unit.getPort(), is(Optional.of(8080)));
        assertThat(unit.getPath(), is("/"));
        assertThat(unit.getQuery(), is(emptyString()));
        assertThat(unit.getProtocolVersion(), is("HTTP/1.1"));
        assertThat(unit.getHeaders().values(), is(empty()));
        assertThat(unit.getContentType(), is(""));
        assertThat(unit.getCharset(), is(UTF_8));
        assertThat(unit.getBody(), is("".getBytes(UTF_8)));
        assertThat(unit.getBodyAsString(), is(emptyString()));
    }

    static HttpRequest mockHttpRequest() {
        final HttpRequest request = mock(HttpRequest.class);

        when(request.getOrigin()).thenReturn(REMOTE);
        when(request.getRemote()).thenReturn("127.0.0.1");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestUri()).thenReturn("http://localhost/");
        when(request.getScheme()).thenReturn("http");
        when(request.getHost()).thenReturn("localhost");
        when(request.getPort()).thenReturn(Optional.of(8080));
        when(request.getPath()).thenReturn("/");
        when(request.getQuery()).thenReturn("");
        when(request.getProtocolVersion()).thenReturn("HTTP/1.1");
        when(request.getHeaders()).thenReturn(emptyMap());
        when(request.getContentType()).thenReturn("");
        when(request.getCharset()).thenReturn(UTF_8);

        try {
            when(request.getBody()).thenReturn("".getBytes(UTF_8));
            when(request.getBodyAsString()).thenReturn("");
        } catch (final IOException e) {
            throw new AssertionError(e);
        }

        return request;
    }

}
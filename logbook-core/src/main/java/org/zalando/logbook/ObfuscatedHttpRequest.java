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

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class ObfuscatedHttpRequest extends ForwardingHttpRequest {

    private final HttpRequest request;
    private final QueryObfuscator queryObfuscator;
    private final BodyObfuscator bodyObfuscator;
    private final Map<String, List<String>> headers;

    ObfuscatedHttpRequest(final HttpRequest request,
            final QueryObfuscator queryObfuscator,
            final HeaderObfuscator headerObfuscator,
            final BodyObfuscator bodyObfuscator) {
        this.request = request;
        this.queryObfuscator = queryObfuscator;
        this.bodyObfuscator = bodyObfuscator;
        this.headers = Obfuscators.obfuscateHeaders(request.getHeaders(), headerObfuscator::obfuscate);
    }

    @Override
    protected HttpRequest delegate() {
        return request;
    }

    @Override
    public String getRequestUri() {
        return RequestURI.reconstruct(this);
    }

    @Override
    public String getQuery() {
        final String query = super.getQuery();
        return query.isEmpty() ? query : queryObfuscator.obfuscate(query);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public byte[] getBody() throws IOException {
        return getBodyAsString().getBytes(getCharset());
    }

    @Override
    public String getBodyAsString() throws IOException {
        return bodyObfuscator.obfuscate(getContentType(), request.getBodyAsString());
    }

}

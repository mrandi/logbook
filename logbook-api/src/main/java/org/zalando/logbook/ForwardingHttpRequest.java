package org.zalando.logbook;

/*
 * #%L
 * Logbook: Core
 * %%
 * Copyright (C) 2015 - 2016 Zalando SE
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

import java.util.Optional;

public abstract class ForwardingHttpRequest extends ForwardingHttpMessage implements HttpRequest {

    @Override
    protected abstract HttpRequest delegate();

    @Override
    public String getRemote() {
        return delegate().getRemote();
    }

    @Override
    public String getMethod() {
        return delegate().getMethod();
    }

    @Override
    public String getRequestUri() {
        return delegate().getRequestUri();
    }

    @Override
    public String getHost() {
        return delegate().getHost();
    }

    @Override
    public String getScheme() {
        return delegate().getScheme();
    }

    @Override
    public Optional<Integer> getPort() {
        return delegate().getPort();
    }

    @Override
    public String getPath() {
        return delegate().getPath();
    }

    @Override
    public String getQuery() {
        return delegate().getQuery();
    }

}

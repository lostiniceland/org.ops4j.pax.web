/* Copyright 2007 Alin Dreghiciu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.web.service.internal;

import org.ops4j.lang.NullArgumentException;
import org.osgi.framework.Bundle;
import org.osgi.service.http.HttpContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * A simple wrapper to enclose custom HttpContexts to a WebContainerContext
 */
class WebContainerContextWrapper extends DefaultHttpContext {

    private HttpContext httpContext;


    WebContainerContextWrapper(final Bundle bundle, final HttpContext httpContext) {
        super(bundle, "wrapped");
        NullArgumentException.validateNotNull(httpContext, "HttpContext");
        this.httpContext = httpContext;
    }


    @Override
    public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return httpContext.handleSecurity(request, response);
    }

    @Override
    public URL getResource(String name) {
        return httpContext.getResource(name);
    }

    @Override
    public String getMimeType(String name) {
        return httpContext.getMimeType(name);
    }
}

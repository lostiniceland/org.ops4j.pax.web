/*
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
 package org.ops4j.pax.web.itest.tomcat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.web.itest.base.VersionUtil;
import org.ops4j.pax.web.itest.base.client.HttpTestClientFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;


/**
 * @author Sergey Beryozkin
 */
@RunWith(PaxExam.class)
public class WarDispatchTCIntegrationTest extends ITestBase {

	private Bundle installWarBundle;

	@Configuration
	public Option[] configure() {
		return configureTomcat();
	}


	@Before
	public void setUp() throws BundleException, InterruptedException {
		LOG.info("Setting up test");
		
		initWebListener();
		
		String bundlePath = WEB_BUNDLE
				+ "mvn:org.ops4j.pax.web.samples/war-dispatch-jsp/"
				+ VersionUtil.getProjectVersion() + "/war?"
				+ WEB_CONTEXT_PATH + "=/war-dispatch-jsp";

		installWarBundle = bundleContext.installBundle(bundlePath);
		installWarBundle.start();

		waitForWebListener();
	}

	@After
	public void tearDown() throws BundleException {
		if (installWarBundle != null) {
			installWarBundle.stop();
			installWarBundle.uninstall();
		}
	}


	@Test
	public void testDispatchJsp() throws Exception {
		HttpTestClientFactory.createDefaultTestClient()
				.withResponseAssertion("Response must contain '<h2>Hello World!</h2>'",
						resp -> resp.contains("<h2>Hello World!</h2>"))
				.doGETandExecuteTest("http://127.0.0.1:8282/war-dispatch-jsp/wc/dispatch/jsp");

//		testClient.testWebPath("http://127.0.0.1:8282/war-dispatch-jsp/wc/dispatch/jsp", "<h2>Hello World!</h2>");
	}



}

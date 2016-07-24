/*
 * Copyright 2016 Fendler Consulting cc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jensfendler.ninjacamunda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import ninja.lifecycle.Dispose;

/**
 * Shutdown hook for the Camunda process engine.
 * 
 * @author Jens Fendler <jf@jensfendler.com>
 *
 */
@Singleton
public class CamundaShutdown {

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NinjaCamundaModule.class);

	/**
	 * Provider for the running process engine
	 */
	@Inject
	protected Provider<Camunda> camundaProvider;

	/**
	 * Shutdown hook.
	 */
	@Dispose(order = 15)
	public void shutdownCamunda() {
		LOG.info("Shutting down Camunda BPMN process engine.");
		camundaProvider.get().getProcessEngine().close();
	}

}

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

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Ninja Module to provide integration with the Camunda BPMN process engine.
 * 
 * To use it, <code>install()</code> a new instance of this module in your
 * application's <code>conf.Module</code> class.
 * 
 * @author Jens Fendler
 *
 */
public class NinjaCamundaModule extends AbstractModule {

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NinjaCamundaModule.class);

	/**
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		LOG.info("Initialising Ninja Camunda module.");
		bind(Camunda.class).toProvider(CamundaProvider.class).in(Singleton.class);
		bind(CamundaShutdown.class);
	}

}

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

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ninja.utils.NinjaProperties;

/**
 * Default implementation of a {@link Provider} for the injectable
 * {@link Camunda} interface.
 * 
 * This class creates a Camunda {@link ProcessEngineConfiguration} which is then
 * used to construct the singleton {@link ProcessEngine} instance which is kept
 * within a {@link Camunda} implementation to provide access to all parts of the
 * process engine.
 * 
 * @author Jens Fendler
 *
 */
public class CamundaProvider implements Provider<Camunda> {

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NinjaCamundaModule.class);

	/**
	 * JDBC URL to use for the process engine database (required property)
	 */
	private static final String PROPERTY_JDBC_URL = "camunda.jdbc.url";

	/**
	 * JDBC user name to use when connecting to the process engine database
	 * (required property)
	 */
	private static final String PROPERTY_JDBC_USERNAME = "camunda.jdbc.username";

	/**
	 * JDBC password to use when connecting to the process engine database
	 * (required property)
	 */
	private static final String PROPERTY_JDBC_PASSWORD = "camunda.jdbc.password";

	/**
	 * Class name of the JDBC driver to use for connecting to the process engine
	 * database (required property)
	 */
	private static final String PROPERTY_JDBC_DRIVER = "camunda.jdbc.driver";

	/**
	 * Boolean property, indicating if the camunda job executor should be aware
	 * of process deployments (default: true)
	 */
	private static final String PROPERTY_JOBEXECUTOR_DEPLOYMENTAWARE = "camunda.jobexecutor.deploymentAware";

	/**
	 * Boolean property, indicating if the camunda job executor should be
	 * activated immediately. (default: true)
	 */
	private static final String PROPERTY_JOBEXECUTOR_ACTIVATE = "camunda.jobexecutor.activate";

	/**
	 * Injected (via constructor) {@link NinjaProperties} to read module
	 * configuration from
	 */
	protected NinjaProperties ninjaProperties;

	/**
	 * Constructor with injected {@link NinjaProperties}
	 * 
	 * @param ninjaProperties
	 */
	@Inject
	public CamundaProvider(NinjaProperties ninjaProperties) {
		this.ninjaProperties = ninjaProperties;
	}

	/**
	 * Provides a new {@link Camunda} instance.
	 * 
	 * @see com.google.inject.Provider#get()
	 */
	public Camunda get() {
		// read required properties
		String jdbcUrl = ninjaProperties.getOrDie(PROPERTY_JDBC_URL);
		String jdbcUsername = ninjaProperties.getOrDie(PROPERTY_JDBC_USERNAME);
		String jdbcPassword = ninjaProperties.getOrDie(PROPERTY_JDBC_PASSWORD);
		String jdbcDriver = ninjaProperties.getOrDie(PROPERTY_JDBC_DRIVER);

		// read optional properties (having default values)
		boolean jobExecutorDeploymentAware = ninjaProperties.getBooleanWithDefault(PROPERTY_JOBEXECUTOR_DEPLOYMENTAWARE, true);
		boolean jobExecutorActivate = ninjaProperties.getBooleanWithDefault(PROPERTY_JOBEXECUTOR_ACTIVATE, true);

		// build the configuration
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE).setJdbcUrl(jdbcUrl).setJdbcUsername(jdbcUsername)
				.setJdbcPassword(jdbcPassword).setJdbcDriver(jdbcDriver).setJobExecutorDeploymentAware(jobExecutorDeploymentAware)
				.setJobExecutorActivate(jobExecutorActivate);

		// start the engine
		LOG.info("Building Camunda BPMN Process Engine...");
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

		// wrap the ProcessEngine in a Camunda object and return it
		Camunda camunda = new DefaultCamundaImpl(processEngine);
		LOG.info("Camunda BPMN Process Engine ready.");
		return camunda;
	}

}

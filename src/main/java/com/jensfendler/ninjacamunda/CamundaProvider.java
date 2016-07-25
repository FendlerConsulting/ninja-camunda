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

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ninja.postoffice.PostofficeConstant;
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
	 * Boolean property to control if the Camunda BPM should work with an
	 * in-memory database (e.g. for testing), or through the configured JDBC
	 * connection (default: false).
	 */
	private static final String PROPERTY_USE_IN_MEMORY_DB = "camunda.db.inMemory";

	/**
	 * String property to control Camunda DB schema drop/create/update. Possible
	 * values are <code>"true"</true>
	 * ({@link ProcessEngineConfiguration#DB_SCHEMA_UPDATE_TRUE}),
	 * <code>"false"</true>
	 * ({@link ProcessEngineConfiguration#DB_SCHEMA_UPDATE_FALSE}), or
	 * <code>"create-drop"</true>
	 * ({@link ProcessEngineConfiguration#DB_SCHEMA_UPDATE_CREATE_DROP}).
	 * 
	 * Default: <code>"true"</true>
	 */
	private static final String PROPERTY_UPDATE_SCHEMA = "camunda.db.schemaUpdate";

	/**
	 * Boolean property to control user authorization.
	 */
	private static final String PROPERTY_AUTHORIZATION_ENABLED = "camunda.useAuthorization";

	/**
	 * String property for the Sender name of outgoing emails
	 * 
	 * (No default value. Camunda will not send mails unless this property is
	 * set.)
	 */
	private static final String PROPERTY_SMTP_FROM = "camunda.smtp.from";

	/**
	 * String property for the SMTP host name (defaults to Ninja Postoffice
	 * configuration as per the config values defined in
	 * {@link PostofficeConstant}).
	 */
	private static final String PROPERTY_SMTP_HOST = "camunda.smtp.host";

	/**
	 * Integer property for the SMTP port number (defaults to Ninja Postoffice
	 * configuration as per the config values defined in
	 * {@link PostofficeConstant}).
	 */
	private static final String PROPERTY_SMTP_PORT = "camunda.smtp.port";

	/**
	 * String property for the SMTP username (if any) (defaults to Ninja
	 * Postoffice configuration as per the config values defined in
	 * {@link PostofficeConstant}).
	 */
	private static final String PROPERTY_SMTP_USERNAME = "camunda.smtp.username";

	/**
	 * String property for the SMTP password (if any) (defaults to Ninja
	 * Postoffice configuration as per the config values defined in
	 * {@link PostofficeConstant}).
	 */
	private static final String PROPERTY_SMTP_PASSWORD = "camunda.smtp.password";

	/**
	 * Boolean property to control the use of TLS when camunda sends SMTP mails
	 * (defaults to Ninja Postoffice configuration as per the config values
	 * defined in {@link PostofficeConstant}).
	 * 
	 * If no default value is provided by {@link PostofficeConstant#smtpSsl},
	 * this defaults to false.
	 */
	private static final String PROPERTY_SMTP_USE_TLS = "camunda.smtp.tls";

	/**
	 * Injected (via constructor) {@link NinjaProperties} to read module
	 * configuration from
	 */
	protected NinjaProperties ninjaProperties;

	/**
	 * Constructor with injected {@link NinjaProperties}
	 * 
	 * @param ninjaProperties
	 *            injected {@link NinjaProperties}
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
	@Override
	public Camunda get() {
		ProcessEngineConfiguration processEngineConfiguration = null;

		// generic camunda configuration properties
		boolean useInMemoryDatabase = ninjaProperties.getBooleanWithDefault(PROPERTY_USE_IN_MEMORY_DB, false);
		String updateSchema = ninjaProperties.getWithDefault(PROPERTY_UPDATE_SCHEMA, ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		boolean jobExecutorDeploymentAware = ninjaProperties.getBooleanWithDefault(PROPERTY_JOBEXECUTOR_DEPLOYMENTAWARE, true);
		boolean jobExecutorActivate = ninjaProperties.getBooleanWithDefault(PROPERTY_JOBEXECUTOR_ACTIVATE, true);
		boolean authorizationEnabled = ninjaProperties.getBooleanWithDefault(PROPERTY_AUTHORIZATION_ENABLED, false);

		// SMTP configuration (defaults to Ninja Postoffice settings)
		String mailFrom = ninjaProperties.get(PROPERTY_SMTP_FROM);
		String mailHost = ninjaProperties.getWithDefault(PROPERTY_SMTP_HOST, ninjaProperties.get(PostofficeConstant.smtpHost));
		int mailPort = ninjaProperties.getIntegerWithDefault(PROPERTY_SMTP_PORT,
				ninjaProperties.getIntegerWithDefault(PostofficeConstant.smtpPort, 25));
		String mailUser = ninjaProperties.getWithDefault(PROPERTY_SMTP_USERNAME, ninjaProperties.get(PostofficeConstant.smtpUser));
		String mailPass = ninjaProperties.getWithDefault(PROPERTY_SMTP_PASSWORD, ninjaProperties.get(PostofficeConstant.smtpPassword));
		boolean mailUseTLS = ninjaProperties.getBooleanWithDefault(PROPERTY_SMTP_USE_TLS,
				ninjaProperties.getBooleanWithDefault(PostofficeConstant.smtpSsl, false));

		// create specific configurations for JDBC/in-memory scenarios
		if (useInMemoryDatabase) {
			LOG.debug("Preparing Camunda configuration for In-Memory database");
			processEngineConfiguration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();

		} else {
			LOG.debug("Preparing Camunda configuration with JDBC connection to database");
			String jdbcUrl = ninjaProperties.getOrDie(PROPERTY_JDBC_URL);
			String jdbcUsername = ninjaProperties.getOrDie(PROPERTY_JDBC_USERNAME);
			String jdbcPassword = ninjaProperties.getOrDie(PROPERTY_JDBC_PASSWORD);
			String jdbcDriver = ninjaProperties.getOrDie(PROPERTY_JDBC_DRIVER);

			processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration().setJdbcUrl(jdbcUrl)
					.setJdbcUsername(jdbcUsername).setJdbcPassword(jdbcPassword).setJdbcDriver(jdbcDriver);
		}

		// setup general configuration aspects
		processEngineConfiguration = processEngineConfiguration.setDatabaseSchemaUpdate(updateSchema)
				.setJobExecutorDeploymentAware(jobExecutorDeploymentAware).setJobExecutorActivate(jobExecutorActivate)
				.setAuthorizationEnabled(authorizationEnabled);

		// only configure SMTP setup if explicitly provided
		if (StringUtils.isNotBlank(mailFrom)) {
			processEngineConfiguration = processEngineConfiguration.setMailServerDefaultFrom(mailFrom).setMailServerHost(mailHost)
					.setMailServerPort(mailPort).setMailServerUseTLS(mailUseTLS);

			// only configure SMTP user if explicitly provided
			if (StringUtils.isNotBlank(mailUser)) {
				LOG.debug("Camunda will use usename/password authentication for SMTP requests");
				processEngineConfiguration = processEngineConfiguration.setMailServerUsername(mailUser);

				// only configure SMTP password when explicitly provided
				// together with user name
				if (StringUtils.isNotBlank(mailPass)) {
					processEngineConfiguration = processEngineConfiguration.setMailServerPassword(mailPass);
				}
			} else {
				LOG.debug("Camunda will issue un-authenticated for SMTP requests");
			}
		}

		// start the engine
		LOG.info("Building Camunda BPMN Process Engine...");
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

		// wrap the ProcessEngine in a Camunda object and return it
		Camunda camunda = new DefaultCamundaImpl(processEngine);
		LOG.info("Camunda BPMN Process Engine ready: {}", processEngine.getName());

		return camunda;
	}

}

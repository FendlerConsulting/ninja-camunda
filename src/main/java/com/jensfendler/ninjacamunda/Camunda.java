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

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ExternalTaskService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;

/**
 * Interface for the Camunda BPMN process engine.
 * 
 * @author Jens Fendler
 *
 */
public interface Camunda {

	/**
	 * @return the camunda {@link ProcessEngine}
	 */
	public ProcessEngine getProcessEngine();

	/**
	 * @return the camunda {@link AuthorizationService}
	 */
	public AuthorizationService getAuthorizationService();

	/**
	 * @return the camunda {@link CaseService}
	 */
	public CaseService getCaseService();

	/**
	 * @return the camunda {@link DecisionService}
	 */
	public DecisionService getDecisionService();

	/**
	 * @return the camunda {@link ExternalTaskService}
	 */
	public ExternalTaskService getExternalTaskService();

	/**
	 * @return the camunda {@link FilterService}
	 */
	public FilterService getFilterService();

	/**
	 * @return the camunda {@link FormService}
	 */
	public FormService getFormService();

	/**
	 * @return the camunda {@link HistoryService}
	 */
	public HistoryService getHistoryService();

	/**
	 * @return the camunda {@link IdentityService}
	 */
	public IdentityService getIdentityService();

	/**
	 * @return the camunda {@link ManagementService}
	 */
	public ManagementService getManagementService();

	/**
	 * @return the {@link ProcessEngineConfiguration} used to construct the
	 *         underlying {@link ProcessEngine}
	 */
	public ProcessEngineConfiguration getConfiguration();

	/**
	 * @return the camunda {@link RepositoryService}
	 */
	public RepositoryService getRepositoryService();

	/**
	 * @return the camunda {@link RuntimeService}
	 */
	public RuntimeService getRuntimeService();

	/**
	 * @return the camunda {@link TaskService}
	 */
	public TaskService getTaskService();

}

/*
 * Copyright 2016 Fendler Consulting cc.
 *
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
 * Abstract base class for implementations of the {@link Camunda} interface.
 * 
 * Since all Camunda components are available through the {@link ProcessEngine}
 * object, this class provides some convenience methods.
 * 
 * @author Jens Fendler
 *
 */
public abstract class AbstractCamunda implements Camunda {

	/**
	 * Public Constructor
	 */
	public AbstractCamunda() {
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getProcessEngine()
	 */
	public abstract ProcessEngine getProcessEngine();

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getAuthorizationService()
	 */
	public AuthorizationService getAuthorizationService() {
		return getProcessEngine().getAuthorizationService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getCaseService()
	 */
	public CaseService getCaseService() {
		return getProcessEngine().getCaseService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getDecisionService()
	 */
	public DecisionService getDecisionService() {
		return getProcessEngine().getDecisionService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getExternalTaskService()
	 */
	public ExternalTaskService getExternalTaskService() {
		return getProcessEngine().getExternalTaskService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getFilterService()
	 */
	public FilterService getFilterService() {
		return getProcessEngine().getFilterService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getFormService()
	 */
	public FormService getFormService() {
		return getProcessEngine().getFormService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getHistoryService()
	 */
	public HistoryService getHistoryService() {
		return getProcessEngine().getHistoryService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getIdentityService()
	 */
	public IdentityService getIdentityService() {
		return getProcessEngine().getIdentityService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getManagementService()
	 */
	public ManagementService getManagementService() {
		return getProcessEngine().getManagementService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getConfiguration()
	 */
	public ProcessEngineConfiguration getConfiguration() {
		return getProcessEngine().getProcessEngineConfiguration();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getRepositoryService()
	 */
	public RepositoryService getRepositoryService() {
		return getProcessEngine().getRepositoryService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getRuntimeService()
	 */
	public RuntimeService getRuntimeService() {
		return getProcessEngine().getRuntimeService();
	}

	/**
	 * @see com.jensfendler.ninjacamunda.Camunda#getTaskService()
	 */
	public TaskService getTaskService() {
		return getProcessEngine().getTaskService();
	}

}

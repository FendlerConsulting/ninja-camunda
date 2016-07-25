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
package controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.camunda.bpm.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.jensfendler.ninjacamunda.Camunda;

import ninja.Result;
import ninja.Results;

/**
 * @author Jens Fendler
 *
 */
@Singleton
public class Application {

	public static final Logger LOG = LoggerFactory.getLogger(Application.class);

	@Inject
	protected Provider<Camunda> camundaProvider;

	/**
	 * @return
	 */
	public Result index() {
		LOG.info("index() controller running.");
		return Results.ok().text().renderRaw("Index page".getBytes());
	}

	/**
	 * @return
	 */
	public Result deploymentList() {
		LOG.info("deploymentList() controller running.");
		List<Deployment> deployments = camundaProvider.get().getRepositoryService().createDeploymentQuery().list();
		return Results.ok().json().render("deployments", deployments);
	}

	/**
	 * @return
	 */
	public Result deploymentNew() {
		LOG.info("deploymentNew() controller running.");

		String bpmnModel;
		try {
			bpmnModel = loadModel("bpmn/test1.bpmn");
		} catch (IOException e) {
			return Results.internalServerError().json();
		}
		Deployment deployment = camundaProvider.get().getRepositoryService().createDeployment().addString("test1", bpmnModel).deploy();
		return Results.ok().json().render("deployment", deployment);
	}

	/**
	 * @param string
	 * @return
	 * @throws IOException
	 */
	private String loadModel(String filename) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteStreams.copy(getClass().getClassLoader().getResourceAsStream(filename), baos);
		return new String(baos.toByteArray());
	}

}

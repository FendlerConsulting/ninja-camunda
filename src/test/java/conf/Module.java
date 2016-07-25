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
package conf;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jensfendler.ninjacamunda.NinjaCamundaModule;

import controllers.Application;
import ninja.conf.NinjaClassicModule;
import ninja.utils.NinjaProperties;

/**
 * @author Jens Fendler
 *
 */
@Singleton
public class Module extends NinjaClassicModule {

	/**
	 * @param ninjaProperties
	 * @param defaultEnabled
	 */
	@Inject
	public Module(NinjaProperties ninjaProperties) {
		super(ninjaProperties, true);
	}

	/**
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	public void configure() {
		install(new NinjaCamundaModule());
		Application.LOG.info("NinjaCamundaModule() has been installed.");
	}

}

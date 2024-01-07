/**
 *    Copyright (C) 2024 ACE Ninjas <aceninjas.org@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.aceninjas.udn;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogManager {

	public static synchronized void log(Properties props,String infor,String error){		
		PropertyConfigurator.configure(props);
		Logger logger = Logger.getLogger("file");

		if (!StringUtils.isEmpty(infor)) {
			logger.info(infor);
		}
		
		if (!StringUtils.isEmpty(error)) {
			logger.error(error);
		}
	}

}

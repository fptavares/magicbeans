/*
 * Copyright 2005 Filipe Tavares
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devyant.magicbeans.conf;

/**
 * InvalidConfigurationException is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 11/Jun/2005 2:53:12
 */
public class InvalidConfigurationException extends ConfigurationException {
    /**
     * Creates a new <code>InvalidConfigurationException</code> instance.
     * @param key The property's key
     * @param value The property's value
     */
    public InvalidConfigurationException(final String key, final String value) {
        super("The value '" + value + "' for the '"
                + MagicConfiguration.KEY_PREFIX + key
                + "' property is not valid.");
    }
}

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
 * ConfigurationException is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 11/Jun/2005 2:35:04
 */
public class ConfigurationException extends Exception {

    /**
     * Creates a new <code>ConfigurationException</code> instance.
     */
    public ConfigurationException() {
        super();
    }

    /**
     * Creates a new <code>ConfigurationException</code> instance.
     * @param message The message
     * @param e The root exception
     */
    public ConfigurationException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Creates a new <code>ConfigurationException</code> instance.
     * @param message The message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new <code>ConfigurationException</code> instance.
     * @param e The root exception
     */
    public ConfigurationException(Throwable e) {
        super(e);
    }
    
}

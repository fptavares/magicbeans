/*
 * Magic Beans: a library for GUI generation and component-bean mapping.
 * Copyright (C) 2005  Filipe Tavares
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * devYant, devyant@devyant.org
 * Rua Simao Bolivar 203 6C, 4470-214 Maia, Portugal.
 *
 */
package org.devyant.magicbeans.conf;

import org.devyant.magicbeans.exceptions.MagicException;

/**
 * This exception is thrown if there is any problem while loading
 * the configuration files and it's properties.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 11/Jun/2005 2:35:04
 */
public class ConfigurationException extends MagicException {

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
    
}

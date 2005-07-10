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

import org.apache.commons.collections.FastHashMap;
import org.apache.commons.configuration.Configuration;

/**
 * ConfigurationCache is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 10, 2005 1:54:46 AM
 */
public class ConfigurationCache extends FastHashMap {
    /**
     * Creates a new <code>ConfigurationCache</code> instance.
     */
    public ConfigurationCache() {
        this.setFast(true);
    }
    
    /**
     * @param className The super bean's class name
     * @param beanPath The bean path
     * @return The cached object
     */
    public Configuration get(final String className, final String beanPath) {
        return (Configuration) get(className + "." + beanPath);
    }
    
    /**
     * @param className The super bean's class name
     * @param beanPath The bean path
     * @return The cached object
     */
    public void put(final String className, final String beanPath,
            final Configuration configuration) {
        put(className + "." + beanPath, configuration);
    }
}

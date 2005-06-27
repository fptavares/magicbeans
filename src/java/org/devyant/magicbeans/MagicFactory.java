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
package org.devyant.magicbeans;

import org.devyant.magicbeans.conf.ConfigurationException;
import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.conf.UnavailableConfigurationException;
import org.devyant.magicbeans.swing.SwingComponentFactory;

/**
 * MagicFactory is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 11/Jun/2005 2:04:14
 */
public abstract class MagicFactory {
    private static final String SWING = "swing";
    private static final String AWT = "awt";

    /**
     * Returns, according to the configuration, an apropriate
     * <code>MagicContainer</code> instance.
     * @param beanClass The bean's class
     * @return A <code>MagicContainer</code> instance
     * @throws ConfigurationException When the configuration
     * returns an invalid value
     */
    protected static final MagicView getContainerInstanceFor(
            final Class beanClass, final MagicConfiguration configuration)
            throws ConfigurationException {
        if (configuration.get(MagicConfiguration.GUI_TYPE_KEY)
                .equalsIgnoreCase(SWING)) {
            // Swing
            //return new SwingContainer();
            final MagicComponent component = SwingComponentFactory
                .getBinderInstanceFor(beanClass, configuration, false);
            
            if (component instanceof MagicView) {
                return (MagicView) component;
            } else {
                return null;
            }
            
        } else if (configuration.get(MagicConfiguration.GUI_TYPE_KEY)
                .equalsIgnoreCase(AWT)) {
            // AWT
            throw new UnavailableConfigurationException(
                    MagicConfiguration.GUI_TYPE_KEY,
                    configuration.get(MagicConfiguration.GUI_TYPE_KEY));
        } else {
            throw new InvalidConfigurationException(
                    MagicConfiguration.GUI_TYPE_KEY,
                    configuration.get(MagicConfiguration.GUI_TYPE_KEY));
        }
    }
}

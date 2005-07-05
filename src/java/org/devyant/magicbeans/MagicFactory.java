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

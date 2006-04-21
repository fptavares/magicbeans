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

import javax.swing.JComponent;

import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.conf.UnavailableConfigurationException;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.ui.UIFactory;
import org.devyant.magicbeans.ui.swing.SwingFactory;

/**
 * MagicFactory is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 11/Jun/2005 2:04:14
 */
public abstract class MagicFactory {
    private static UIFactory<JComponent> swingFactory;
    
    public static final UIFactory<JComponent> swing() {
        if (swingFactory == null) {
            swingFactory = new SwingFactory();
        }
        return swingFactory;
    }
    
    /**
     * @param property The property
     * @return The factory instance
     * @throws UnavailableConfigurationException
     * @throws InvalidConfigurationException
     * @FIXME must use factory configuration
     */
    private static final UIFactory<?> factoryFor(final MagicProperty property)
            throws UnavailableConfigurationException,
                InvalidConfigurationException {
        
        final String type = property.getConfiguration().get(
                MagicConfiguration.GUI_TYPE_KEY);
        
        if (type.equalsIgnoreCase(MagicConfiguration.SWING_VALUE)) {
            // Swing
            return swing();
            
        } else if (type.equalsIgnoreCase(MagicConfiguration.AWT_VALUE)) {
            // AWT_VALUE
            throw new UnavailableConfigurationException(
                    MagicConfiguration.GUI_TYPE_KEY, type);
            
        } else {
            throw new InvalidConfigurationException(
                    MagicConfiguration.GUI_TYPE_KEY, type);
        }
    }
    
    /**
     * Returns, according to the configuration, an apropriate
     * <code>MagicContainer</code> instance.
     * <p>
     * If the {@link #getComponentInstanceFor(MagicProperty)} returns
     * an object that is not an instance of <code>MagicContainer</code>,
     * this method will return <code>null</code>.
     * </p>
     * @param property The property
     * @return A <code>MagicContainer</code> instance
     * @throws MagicException When the configuration returns an invalid value
     *  or when that layout could not be instantiated
     */
    protected static final MagicContainer<?> getContainerInstanceFor(
            final MagicProperty property) throws MagicException {
        
        final MagicComponent<?> component = getComponentInstanceFor(property);
        
        if (component instanceof MagicContainer) {
            // return the component
            return (MagicContainer<?>) component;
        } else {
            // not a container, so return null
            return null;
        }
    }

    /**
     * Returns, according to the configuration, an apropriate
     * <code>MagicComponent</code> instance.
     * @param beanClass The bean's class
     * @return A <code>MagicComponent</code> instance
     * @throws MagicException When the configuration returns an invalid value
     *  or when that layout could not be instantiated
     */
    public static final MagicComponent<?> getComponentInstanceFor(
            final MagicProperty property) throws MagicException {
        return factoryFor(property).getBaseComponentInstanceFor(property);
    }
}

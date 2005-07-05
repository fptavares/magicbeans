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
package org.devyant.magicbeans.utils.containers;

import java.awt.Component;
import java.awt.Container;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.exceptions.MagicException;

/**
 * DefaultContainerBehaviour is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 17/Jun/2005 1:28:17
 */
public abstract class DefaultContainerBehaviour {
    /**
     * This is a utility class, therefor it should not be instatiated.
     */
    protected DefaultContainerBehaviour() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Common implementation for all <code>MagicContainer</code>s
     * {@link org.devyant.magicbeans.MagicComponent#update()} method.
     * @param container
     * @throws MagicException An error occured during the update sequence
     * @see org.devyant.magicbeans.MagicComponent
     */
    public static final void doUpdateForContainer(Container container) throws MagicException {
        final Component [] components = container.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof MagicComponent) {
                // do layer-by-layer update
                ((MagicComponent) components[i]).update();
                
                // debug
                MagicUtils.debug(
                        ((MagicComponent) components[i]).getProperty().getName()
                        + ": "
                        + ((MagicComponent) components[i]).getProperty().get()
                );
            }
        }
    }
}

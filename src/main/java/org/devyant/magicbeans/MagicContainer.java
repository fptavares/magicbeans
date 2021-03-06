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

import java.util.Collection;

import org.devyant.magicbeans.ui.listeners.UpdateButtonActionHandler;

/**
 * MagicContainer is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Apr 3, 2006 5:57:52 PM
 * @param <T> The most basic type of this toolkit's components
 */
public interface MagicContainer<T> extends MagicComplexComponent<T>,
        UpdateButtonActionHandler {
    /**
     * If set to true, a MagicComplexComponent will be able
     * to stand alone (i.e. containing the apropriate "controllers").
     * @param standalone The boolean value
     */
    public void setStandalone(final boolean standalone);
    /**
     * Get all child components from this container.
     * @return A <code>Collection</code> of components
     */
    public Collection<MagicComponent<? extends T>> getComponents();
}

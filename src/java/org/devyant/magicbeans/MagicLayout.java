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

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;

/**
 * MagicLayout is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jun 24, 2005 9:52:07 PM
 */
public interface MagicLayout {
    /**
     * Add a pair of components (label + MagicComponent)
     * that represent a property.
     * @param container The container
     * @param label The label component
     * @param component The magic component
     */
    public void addLabeledComponent(final Container container,
            final Component label, final Component component);
    /**
     * Add a pair of components (label + IsolatedComponent)
     * that represent a property.
     * @param container The container
     * @param label The label component
     * @param component The isolated magic component
     */
    public void addLabeledIsolatedComponent(final Container container,
            final Component label, final Component component);
    /**
     * Add a single magic component that represent a property.
     * @param container The container
     * @param component The magic component
     */
    public void addUnlabeledComponent(final Container container,
            final Component component);
    /**
     * Add a component which is controlled by a series of other components.
     * <p>
     * As the controllers may be many, they could force the main component
     * to expand in height. This method does not allow the component to expand
     * if the expand parameter is set to <code>true</code>.
     * </p>
     * @param container The container
     * @param component The magic component
     * @param controllers The control components
     * @param expand 
     */
    public void addControledComponent(final Container container,
            final Component component, final Component[] controllers, boolean expand);
    /**
     * Add the update button.
     * @param container The container
     * @param button The button component
     */
    public void addButton(final Container container, final Component button);
    /**
     * Add the status bar.
     * @param container The container
     * @param status The status bar
     */
    public void addStatus(final Container container, final Component status);
}

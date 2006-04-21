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

import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.layout.LayoutIsolatedBehaviour;


/**
 * MagicLayout is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @param <T> The most basic type of this toolkit's components
 * @since Jun 24, 2005 9:52:07 PM
 */
public interface MagicLayout<T> {
    /**
     * Add a pair of components (label + MagicComponent)
     * that represent a property.
     * @param container The container
     * @param label The label component
     * @param component The magic component
     * @throws MagicException When a problem occurs while rendering the component
     */
    public void addLabeledComponent(final T container, final T label,
            final MagicComponent<? extends T> component) throws MagicException;
    /**
     * Add a pair of components (label + IsolatedComponent)
     * that represent a property.
     * @param container The container
     * @param label The label component
     * @param component The isolated magic component
     * @throws MagicException When a problem occurs while rendering the component
     */
    public void addLabeledIsolatedComponent(final T container,
            final T label, final MagicComponent<? extends T> component)
            throws MagicException;
    /**
     * Add a single magic component that represents a property.
     * @param container The container
     * @param component The magic component
     * @throws MagicException When a problem occurs while rendering the component
     */
    public void addUnlabeledComponent(final T container,
            final MagicComponent<? extends T> component) throws MagicException;
    /**
     * Add a component which is controlled by a series of other components.
     * <p>
     * As the controllers may be many, they could force the main component
     * to expand in height. This method does not allow the component to expand
     * if the expand parameter is set to <code>true</code>.
     * </p>
     * @param container The container
     * @param component The magic component
     * @param expand Expand the component vertically
     * @param controllers The control components
     * @throws MagicException When a problem occurs while rendering the component
     */
    public void addControledComponent(final T container, final T component,
            boolean expand, final T... controllers) throws MagicException;
    /**
     * Add a simple toolkit component.
     * @param container The container
     * @param component The component
     */
    public void addSimpleComponent(final T container,
            final T component);
    /**
     * Add the OK button.
     * @param container The container
     * @param button The button component
     */
    public void addButton(final T container, final T button);
    /**
     * Add the status bar.
     * @param container The container
     * @param status The status bar
     */
    public void addStatus(final T container, final T status);
    /**
     * Add an auxiliar panel to one of the windows edges.
     * @param container The container
     * @param component The component to add
     * @param orientation The panel's orientation
     *  (i.e. witch of the edges to append to)
     */
    /*public void addAuxiliarPanel(final Container container,
            final Component component, final int orientation);
    
    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 4;*/
    
    /**
     * Set the isolated behaviour.
     * @param behaviour The isolated behaviour
     */
    public void setIsolatedBehaviour(final LayoutIsolatedBehaviour<T> behaviour);
    
}

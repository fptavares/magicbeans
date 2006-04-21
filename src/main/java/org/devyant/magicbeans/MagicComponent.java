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

import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.utils.Previewable;

/**
 * MagicComponent is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @param <C> The component to use for the binding
 * @since 18/Abr/2005 20:56:13
 */
public interface MagicComponent<C> extends Previewable {
    /**
     * Updates the original data container.
     * @throws MagicException Something went wrong
     */
    public void update() throws MagicException;
    /**
     * Binds the component with the object's property.
     * @param property The property
     * @throws MagicException Something went wrong
     */
    public void bindTo(final MagicProperty property) throws MagicException;
    /**
     * Getter method for the property.
     * @return The <code>MagicProperty</code> instance binded to this component
     */
    public MagicProperty getProperty();
    /**
     * Render the actual graphical component for the corresponding toolkit.
     * @return A GUI component
     * @throws MagicException Something went wrong
     */
    public C render() throws MagicException;
    /**
     * Whether this is a labeled component or not.
     * @return A boolean
     */
    public boolean isLabeled();
    /**
     * Is it a nested or isolated container?.
     * @return The answer
     */
    public boolean isNested();
    /**
     * The getter method for the property's name.
     * <p>This <code>String</code> will
     * be used for the component's label/title.</p>
     * @return A <code>String</code> that contains the name to be displayed
     */
    public String getName();
    /**
     * The setter method for this component's parent container.
     * @param parent The parent container
     */
    public void setParent(MagicContainer<?> parent);
    /**
     * The getter method for this component's parent container.
     * @return The parent container
     */
    public MagicContainer<?> getParent();
}

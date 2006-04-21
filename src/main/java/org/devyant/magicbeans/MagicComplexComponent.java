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
import org.devyant.magicbeans.ui.UIFactory;
import org.devyant.magicbeans.ui.listeners.WindowListener;


/**
 * MagicComplexComponent is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @param <T> The most basic type of this toolkit's components
 * @since 17/Jun/2005 3:35:04
 */
public interface MagicComplexComponent<T> extends MagicComponent<T> {
    /**
     * Tells the complex component which layout manager to use.
     * @param layout The <code>MagicLayout</code> instance
     */
    public void setMagicLayout(final MagicLayout<T> layout);
    /**
     * Tells the complex component which UI factory to use.
     * @param factory The apropriate factory, considering which
     * toolkit you're using.
     * <p>This factory may easilly be fetched using, for example,
     * <code>MagicFactory.swing()</code> (for the Swing toolkit).</p>
     */
    public void setUIFactory(UIFactory<T> factory);
    /**
     * Creates and displays a window showing this component. 
     * @param parent The parent component
     * @param listener The listener for window-related events
     * @return The window
     * @throws MagicException {@link #render()}
     */
    public Object createAndShowWindow(T parent, final WindowListener listener)
        throws MagicException;
}

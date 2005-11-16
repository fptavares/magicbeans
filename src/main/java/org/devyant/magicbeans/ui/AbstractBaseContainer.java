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
package org.devyant.magicbeans.ui;

import org.devyant.magicbeans.exceptions.MagicException;

/**
 * AbstractBaseContainer is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Oct 28, 2005 7:13:05 PM
 */
public abstract class AbstractBaseContainer extends AbstractMagicContainer {

    /**
     * The nested <code>boolean</code>.
     */
    private final boolean nested;
    
    /**
     * Creates a new <code>AbstractBaseContainer</code> instance.
     * @param component The component to bind the property to
     */
    public AbstractBaseContainer(final Object container, final boolean nested) {
        super(container);
        this.nested = nested;
        // nested -> !standalone
        this.setStandalone(!this.nested);
    }

    /**
     * Calls {@link #massUpdate()}
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#update()
     */
    public final void update() throws MagicException {
        massUpdate();
    }

    /**
     * The update routine for updating all child components.
     */
    protected abstract void massUpdate();

}

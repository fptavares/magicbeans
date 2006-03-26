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
package org.devyant.magicbeans.ui.swing.layout.gridbag;

import java.awt.GridBagConstraints;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.layout.AbstractIsolatedBehaviour;
import org.devyant.magicbeans.ui.awt.layout.gridbag.GridBagMagicLayout;

/**
 * GridBagChildBehaviour is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Mar 25, 2006 3:02:12 PM
 */
public class GridBagChildBehaviour
        extends AbstractIsolatedBehaviour<SwingGridBagMagicLayout> {

    /**
     * Creates a new <code>GridBagChildBehaviour</code> instance.
     * @param layout The layout instance
     */
    public GridBagChildBehaviour(SwingGridBagMagicLayout layout) {
        super(layout);
    }

    /**
     * @see org.devyant.magicbeans.layout.LayoutIsolatedBehaviour#addLabeledIsolatedComponent(java.lang.Object, java.lang.Object, org.devyant.magicbeans.MagicComponent)
     */
    public void addLabeledIsolatedComponent(Object container, Object label,
            MagicComponent<?> component) throws MagicException {
        // add label
        this.layout.addComponent(container, label, 0, GridBagMagicLayout.NO_WEIGHTX);
        // add magic component
        this.layout.addComponent(container, component.render(), 1,
                GridBagMagicLayout.NO_WEIGHTX,
                GridBagConstraints.EAST, GridBagConstraints.VERTICAL);
        this.layout.nextRow();
    }

}

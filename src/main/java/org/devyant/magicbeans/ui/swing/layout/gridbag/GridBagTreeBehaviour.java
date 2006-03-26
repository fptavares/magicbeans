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

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.layout.AbstractIsolatedBehaviour;

/**
 * GridBagTreeBehaviour is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Mar 25, 2006 4:09:04 PM
 */
public class GridBagTreeBehaviour
        extends AbstractIsolatedBehaviour<SwingGridBagMagicLayout> {

    /**
     * Creates a new <code>GridBagTreeBehaviour</code> instance.
     * @param layout The layout instance
     */
    public GridBagTreeBehaviour(SwingGridBagMagicLayout layout) {
        super(layout);
    }

    /**
     * @see org.devyant.magicbeans.layout.LayoutIsolatedBehaviour#addLabeledIsolatedComponent(java.lang.Object, java.lang.Object, org.devyant.magicbeans.MagicComponent)
     */
    public void addLabeledIsolatedComponent(Object container, Object label,
            MagicComponent<?> component) throws MagicException {
        // @todo Auto-generated method stub
        
    }

}

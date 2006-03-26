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

import org.devyant.magicbeans.layout.LayoutIsolatedBehaviour;
import org.devyant.magicbeans.ui.awt.layout.gridbag.GridBagMagicLayout;


/**
 * SwingGridBagMagicLayout is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Mar 25, 2006 2:50:17 PM
 */
public class SwingGridBagMagicLayout extends GridBagMagicLayout {
    
    /**
     * @see org.devyant.magicbeans.ui.awt.layout.gridbag.GridBagMagicLayout#GridBagMagicLayout(IsolatedBehaviourType)
     */
    public SwingGridBagMagicLayout(IsolatedBehaviourType type) {
        super(type);
    }

    /**
     * @see org.devyant.magicbeans.layout.AbstractMagicLayout#createChildBehaviour()
     */
    @Override
    protected LayoutIsolatedBehaviour createChildBehaviour() {
        return new GridBagChildBehaviour(this);
    }

    /**
     * @see org.devyant.magicbeans.layout.AbstractMagicLayout#createTabbedBehaviour()
     */
    @Override
    protected LayoutIsolatedBehaviour createTabbedBehaviour() {
        return new GridBagTabbedBehaviour(this);
    }

    /**
     * @see org.devyant.magicbeans.layout.AbstractMagicLayout#createTreeBehaviour()
     * @todo
     */
    @Override
    protected LayoutIsolatedBehaviour createTreeBehaviour()
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

}

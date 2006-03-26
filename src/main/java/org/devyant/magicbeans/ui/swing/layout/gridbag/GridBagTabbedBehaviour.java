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

import java.awt.Component;

import javax.swing.JTabbedPane;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.layout.AbstractIsolatedBehaviour;

/**
 * GridBagTabbedBehaviour is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Mar 23, 2006 3:34:22 PM
 */
public class GridBagTabbedBehaviour
        extends AbstractIsolatedBehaviour<SwingGridBagMagicLayout> {

    /**
     * The tabbedPane <code>JTabbedPane</code>.
     */
    private JTabbedPane tabbedPane;
    
    /**
     * Creates a new <code>GridBagTabbedBehaviour</code> instance.
     * @param layout The layout instance
     */
    public GridBagTabbedBehaviour(SwingGridBagMagicLayout layout) {
        super(layout);
    }

    /**
     * This method is only called if the component may be isolated.
     * @see org.devyant.magicbeans.layout.LayoutIsolatedBehaviour#addLabeledIsolatedComponent(java.lang.Object, java.lang.Object, org.devyant.magicbeans.MagicComponent)
     * @see org.devyant.magicbeans.MagicUtils#mayBeIsolated(MagicComponent)
     * @throws MagicException 
     */
    public void addLabeledIsolatedComponent(Object container,
            @SuppressWarnings("unused") Object label,
            MagicComponent<?> component) throws MagicException {
        if (this.tabbedPane == null) {
            // create the tabbed container
            this.tabbedPane = new JTabbedPane();
            this.layout.addSimpleComponent(container, this.tabbedPane);
        }
        // add component as a new tab
        this.tabbedPane.addTab(
                component.getName(), (Component) component.render());
    }

}

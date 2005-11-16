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
package org.devyant.magicbeans.ui.swing.isolated;

import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JTabbedPane;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicFactory;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.ui.AbstractMagicContainer;
import org.devyant.magicbeans.ui.UIFactory;

/**
 * SwingTabbedComponent is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Aug 23, 2005 12:58:33 PM
 * @todo ...
 */
public class SwingTabbedComponent extends AbstractMagicContainer {
    /*
     * The properties <code>Collection</code>.
     */
    //private final Collection properties = new LinkedList();

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicContainer#initMagicContainerAction(java.lang.Object)
     */
    protected void initMagicContainerAction(Object button) {
        // @todo Auto-generated method stub
        
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicContainer#getFactory()
     */
    protected UIFactory getFactory() {
        return MagicFactory.swing();
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#createComponent()
     */
    protected Object createComponent() {
        return new JTabbedPane();
    }

    /*
     * @see org.devyant.magicbeans.MagicComponent#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     * @todo take care of property's gui here or at layout?
     *  either layout only adds this once or layout already
     *  receives the rendered component and adds it to this
     */
    /*public void bindTo(MagicProperty property) throws MagicException {
        properties.add(property); 
    }*/

    /*
     * @see org.devyant.magicbeans.MagicComponent#getProperty()
     * @todo perhaps remove this from MagicComponent
     */
    /*public MagicProperty getProperty() {
        return null;
    }*/

}

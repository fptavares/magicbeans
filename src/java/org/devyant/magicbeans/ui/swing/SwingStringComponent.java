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
package org.devyant.magicbeans.ui.swing;

import javax.swing.JTextField;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.exceptions.MagicException;

/**
 * SwingStringComponent is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @since 18/Abr/2005 19:36:41
 */
public class SwingStringComponent extends JTextField implements MagicComponent {
    /**
     * The property to bind to.
     */
    private MagicProperty property;
    
    /**
     * @see org.devyant.magicbeans.MagicComponent#update()
     */
    public void update() throws MagicException {
        this.property.set(getText());
    }
    /**
     * @see org.devyant.magicbeans.MagicComponent#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    public void bindTo(MagicProperty property) throws MagicException {
        this.property = property;
        // fill with property's value
        final Object value = this.property.get();
        if (value != null) {
            setText(value.toString());
        }
    }
    /**
     * @see org.devyant.magicbeans.MagicComponent#getProperty()
     */
    public final MagicProperty getProperty() {
        return property;
    }
}

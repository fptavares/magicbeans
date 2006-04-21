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

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import org.devyant.magicbeans.ui.AbstractMagicComponent;
import org.devyant.magicbeans.utils.Previewable;

/**
 * SwingBooleanComponent is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 10, 2005 4:05:20 AM
 */
public class SwingBooleanComponent extends AbstractMagicComponent<JCheckBox>
        implements Previewable {

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#createComponent()
     */
    @Override
    protected JCheckBox createComponent() {
        return new JCheckBox();
    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#initializeComponent()
     */
    @Override
    protected void initializeComponent() {
        this.component.setHorizontalAlignment(SwingConstants.LEFT);
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#getValue()
     */
    @Override
    protected Object getValue() {
        return this.component.isSelected();
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#setValue(java.lang.Object)
     */
    @Override
    protected void setValue(Object value) {
        this.component.setSelected((Boolean) value);
    }

}

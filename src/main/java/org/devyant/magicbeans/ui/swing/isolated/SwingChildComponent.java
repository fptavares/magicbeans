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

import javax.swing.JButton;

import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.ui.AbstractMagicComponent;
import org.devyant.magicbeans.utils.InternalMagicBean;

/**
 * A link to an isolated (not nested) property.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 6, 2005 2:11:33 AM
 */
public class SwingChildComponent extends AbstractMagicComponent<JButton> {
    
    /**
     * The <code>InternalMagicBean</code>
     * that will be used to generate the container.
     */
    private InternalMagicBean internalBean;

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#setValue(java.lang.Object)
     */
    @Override
    protected void setValue(Object value) throws MagicException {
        // create the magic bean
        this.internalBean = new InternalMagicBean(getProperty());
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#getValue()
     */
    @Override
    protected Object getValue() throws MagicException {
        // call container's update() method 
        this.internalBean.update();
        // update property's value
        return this.internalBean.getRealValue();
    }

    /**
     * GUI initialization.
     */
    @Override
    protected void initializeComponent() {
        this.component.setText(MagicConfiguration.resources
                .get(MagicResources.STRING_ISOLATEDBUTTON));
        this.component.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    SwingChildComponent.this.internalBean.showInternalFrame(
                            SwingChildComponent.this.component,
                            MagicConfiguration.resources.getNameFor(getProperty()),
                            null);
                } catch (MagicException e) {
                    MagicUtils.error(e);
                }
            }
        });
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#createComponent()
     */
    @Override
    protected JButton createComponent() {
        return new JButton();
    }

}

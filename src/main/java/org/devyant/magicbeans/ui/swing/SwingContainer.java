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

import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.devyant.magicbeans.MagicFactory;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.ui.AbstractBaseContainer;

/**
 * SwingContainer is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @since 18/Abr/2005 20:01:28
 * @todo maybe remove status bar....
 * replace by a simple -> title + "*" -> only at nested containers...:(
 */
public class SwingContainer extends AbstractBaseContainer {

    /**
     * @see AbstractBaseContainer#AbstractBaseContainer(boolean)
     */
    public SwingContainer(final boolean nested) {
        super(MagicFactory.swing(), nested);
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#createComponent()
     */
    protected Object createComponent() {
        return new JPanel();
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#initialize()
     */
    public void initialize() {
        super.initialize();
        ((Container) this.component).setLayout((LayoutManager) this.layout);
    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicContainer#initMagicContainerAction(java.lang.Object)
     */
    protected final void initMagicContainerAction(Object button) {
        ((JButton) button).addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    update();
                    fireUpdateButtonAction();
                    showMessage(MagicResources.MESSAGE_UPDATED);
                } catch (MagicException e) {
                    MagicUtils.debug(e);
                    showErrorMessage(e.toString());
                }
            }
        });
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicContainer#finalizeNestedComponent()
     */
    protected void finalizeNestedComponent() {
        ((JComponent) this.component).setBorder(
                new TitledBorder(null,  " " + this.getName() + " ",
                        TitledBorder.LEFT, TitledBorder.TOP));
    }

    /**
     * Show a message in the status bar.
     * @param name The message's name
     */
    protected void showMessage(final String name) {
        ((JLabel) this.status).setText(
                MagicConfiguration.resources.getMessage(name));
    }
    
    /**
     * Show an error message in the status bar.
     * @param string The message
     * @todo use icon for error
     */
    protected void showErrorMessage(final String string) {
        showMessage("[ERROR] " + string);
    }
    
}
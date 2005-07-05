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
package org.devyant.magicbeans.swing.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JDialog;

/**
 * AbstractDialog is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Jun/2005 3:49:34
 */
public abstract class AbstractDialog extends JDialog {

    /**
     * Creates a new <code>AbstractDialog</code> instance.
     * @param bean
     * @param parent
     * @param title
     * @param modal
     */
    public AbstractDialog(final Container container, final Dialog parent,
            final String title, boolean modal) {
        super(parent, title, modal);
        init(container, parent, modal);
    }

    /**
     * Creates a new <code>AbstractDialog</code> instance.
     * @param bean
     * @param parent
     * @param title
     * @param modal
     */
    public AbstractDialog(final Container container, final Frame parent,
            final String title, boolean modal) {
        super(parent, title, modal);
        init(container, parent, modal);
    }

    /**
     * @param bean
     * @param parent
     * @param modal
     */
    private void init(final Container container, final Component parent,
            boolean modal) {
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeWindowEvent();
            }
        });
        setContentPane(container);
        pack();
        setLocationRelativeTo(parent);
        setModal(modal);
    }
    
    public abstract void closeWindowEvent();
}

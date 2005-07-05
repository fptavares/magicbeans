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

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;

/**
 * BasicDialog is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Jun/2005 4:02:55
 */
public class BasicDialog extends AbstractDialog {

    /**
     * Creates a new <code>BasicDialog</code> instance.
     * @param bean
     * @param parent
     * @param title
     * @param modal
     */
    public BasicDialog(Container container, Dialog parent, String title,
            boolean modal) {
        super(container, parent, title, modal);
    }

    /**
     * Creates a new <code>BasicDialog</code> instance.
     * @param bean
     * @param parent
     * @param title
     * @param modal
     */
    public BasicDialog(Container container, Frame parent, String title,
            boolean modal) {
        super(container, parent, title, modal);
    }

    /**
     * Does nothing.
     * @see org.devyant.magicbeans.swing.utils.AbstractDialog#closeWindowEvent()
     */
    public void closeWindowEvent() {
    }

}

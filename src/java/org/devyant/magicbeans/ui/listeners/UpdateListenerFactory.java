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
package org.devyant.magicbeans.ui.listeners;

import java.awt.Frame;

import org.devyant.magicbeans.conf.MagicConfiguration;

/**
 * UpdateListenerFactory is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 9, 2005 12:20:36 AM
 */
public abstract class UpdateListenerFactory {
    private static final Object HIDE_ON_UPDATE = "hide";
    
    public static final UpdateButtonActionListener createListenerInstance(
            final Frame frame) {
        return getInstance(frame);
    }
    

    /**
     * @param frame
     */
    private static final UpdateButtonActionListener getInstance(
            final Frame frame) {
        // get configuration
        final String onUpdate = MagicConfiguration.getFromDefault(
                MagicConfiguration.GUI_AUX_UPDATE_ACTION);
        // 
        if (onUpdate.equals(HIDE_ON_UPDATE)) {
            return new UpdateButtonActionListener() {
                public void updateButtonActionPerformed() {
                    frame.setVisible(false);
                    frame.dispose();
                }
            };
        } else  {
            return null;
        }
        
    }
}
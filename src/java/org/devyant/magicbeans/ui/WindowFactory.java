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
package org.devyant.magicbeans.ui;

import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.conf.MagicConfiguration;

/**
 * WindowFactory is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 9, 2005 3:26:45 PM
 */
public abstract class WindowFactory {
    /**
     * Create an instance of a Frame.
     * @return A Frame
     * @throws InvalidConfigurationException
     *  When the gui configuration returns an invalid value
     */
    public static final Frame createFrame()
            throws InvalidConfigurationException {
        final String type =
            MagicConfiguration.getFromDefault(MagicConfiguration.GUI_TYPE_KEY);
        
        if (type.equalsIgnoreCase(MagicConfiguration.SWING_VALUE)) {
            // Swing
            return new JFrame();
            
        } else if (type.equalsIgnoreCase(MagicConfiguration.AWT_VALUE)) {
            // AWT_VALUE
            return new Frame();
            
        } else {
            throw new InvalidConfigurationException(
                    MagicConfiguration.GUI_TYPE_KEY, type);
        }
    }
    
    /**
     * Create an instance of a Dialog.
     * @param parent The parent window
     * @return A Dialog
     * @throws InvalidConfigurationException
     *  When the gui configuration returns an invalid value
     */
    public static final Dialog createDialog(Frame parent)
            throws InvalidConfigurationException {
        final String type =
            MagicConfiguration.getFromDefault(MagicConfiguration.GUI_TYPE_KEY);
        
        if (type.equalsIgnoreCase(MagicConfiguration.SWING_VALUE)) {
            // Swing
            return new JDialog(parent);
            
        } else if (type.equalsIgnoreCase(MagicConfiguration.AWT_VALUE)) {
            // AWT_VALUE
            return new Dialog(parent);
            
        } else {
            throw new InvalidConfigurationException(
                    MagicConfiguration.GUI_TYPE_KEY, type);
        }
    }
    /**
     * Create an instance of a Dialog.
     * @param parent The parent window
     * @return A Dialog
     * @throws InvalidConfigurationException
     *  When the gui configuration returns an invalid value
     */
    public static final Dialog createDialog(Dialog parent)
            throws InvalidConfigurationException {
        final String type =
            MagicConfiguration.getFromDefault(MagicConfiguration.GUI_TYPE_KEY);
        
        if (type.equalsIgnoreCase(MagicConfiguration.SWING_VALUE)) {
            // Swing
            return new JDialog(parent);
            
        } else if (type.equalsIgnoreCase(MagicConfiguration.AWT_VALUE)) {
            // AWT_VALUE
            return new Dialog(parent);
            
        } else {
            throw new InvalidConfigurationException(
                    MagicConfiguration.GUI_TYPE_KEY, type);
        }
    }
}

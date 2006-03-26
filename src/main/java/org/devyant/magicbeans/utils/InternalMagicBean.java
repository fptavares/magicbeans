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
package org.devyant.magicbeans.utils;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowListener;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicFactory;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.exceptions.PropertyException;

/**
 * InternalMagicBean is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 29, 2005 1:45:58 AM
 */
public class InternalMagicBean extends AbstractMagicBean {
    /**
     * Creates a new <code>InternalMagicBean</code> instance.
     * @param object The object to map
     */
    public InternalMagicBean(Object object) {
        super(object, object.getClass().getName(), null, false);
    }
    
    /**
     * Creates a new <code>InternalMagicBean</code> instance.
     * @param property The property who's object we want to map
     * @throws PropertyException {@link MagicProperty#get()}
     */
    public InternalMagicBean(final MagicProperty property) throws PropertyException {
        super(property.get(),
                property.getSuperBeanClassName(), property.getBeanPath(), false);
    }

    /**
     * @see org.devyant.magicbeans.utils.AbstractMagicBean#getMagicComponentFor(org.devyant.magicbeans.beans.MagicProperty)
     */
    protected MagicComponent getMagicComponentFor(final MagicProperty property)
            throws MagicException {
        return MagicFactory.getComponentInstanceFor(property);
    }
    
    /**
     * Call container's update() method if it has been rendered.
     * @throws MagicException
     */
    public final void update() throws MagicException {
        if (componentExists()) {
            component.update();
        }
    }
    
    /**
     * Show a frame containing the resulting interface.
     * @param parent Parent component
     * @param title Title for the window
     * @param listener Window listener
     * @throws MagicException {@link #render()}
     */
    public final void showInternalFrame(final Component parent,
            final String title, WindowListener listener)
            throws MagicException {
        // create frame
        final Frame frame = createFrame(parent, title, null);
        // render the container
        final Component c = (Component) this.render();
        // add user's listener
        if (listener != null) {
            frame.addWindowListener(listener);
        }
        // show frame
        showFrame(parent, c, frame);
    }
    
    private final boolean componentExists() {
        return component != null;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        if (componentExists() && component instanceof Previewable) {
            try {
                return String.valueOf( ((Previewable) component).preview() );
            } catch (MagicException e) {
                MagicUtils.debug(e);
            }
        }
        return String.valueOf(this.object);
    }
}

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
package org.devyant.magicbeans;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.WindowListener;

import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.beans.SinglePropertyWrapper;
import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.ui.listeners.UpdateButtonActionListener;
import org.devyant.magicbeans.utils.AbstractMagicBean;


/**
 * MagicBean is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @since 18/Abr/2005 19:44:06
 */
public class MagicBean extends AbstractMagicBean {
    /**
     * Creates a new <code>MagicBean</code> instance.
     * @param object The object to map
     */
    public MagicBean(Object object) {
        super(object, object.getClass().getName(), null);
    }
    
    /**
     * Creates a new <code>MagicBean</code> instance.
     * @param object The object to map
     * @param beanPath The bean path to use
     */
    public MagicBean(Object object, String beanPath) {
        super(object, object.getClass().getName(), beanPath);
    }

    /**
     * @return
     * @throws MagicException
     */
    protected MagicComponent createMagicComponent() throws MagicException {
        // create the magic property object
        final MagicProperty property = createMagicProperty();
        
        // get the base container
        MagicUtils.info("Generating the base container.");
        MagicContainer mContainer =
            MagicFactory.getContainerInstanceFor(property);
        
        if (MagicUtils.cannotStandalone(mContainer)) {
            MagicUtils.info("The object cannot be mapped to a standalone container."
                    + " Using a wrapper...");
            // no container could be found for this bean
            // it is a simple property
            // we have to tranform it into a bean
            this.object = new SinglePropertyWrapper(this.object);
            
            // now we should be able to retrive the correct container
            mContainer = MagicFactory.getContainerInstanceFor(property);
        }
        
        // bind container to this MagicBean's bean
        MagicUtils.info("Binding the base container to a new MagicProperty"
                + " containing the bean.");
        mContainer.bindTo(property);
   
        MagicUtils.info("Rendering the base container.");
        mContainer.renderStandalone();
        return mContainer;
    }
    
    /**
     * Useful for Swing and AWT configurations.
     * @return A java.awt.Container
     * @throws MagicException Thrown if something goes wrong
     *  during the GUI generation process.
     */
    public final Container renderContainer() throws MagicException {
        return (Container) this.render();
    }
    
    /**
     * @see #showFrame(Component, String, UpdateButtonActionListener, WindowListener)
     */
    public final void showFrame(final Component parent) throws MagicException {
        showFrame(parent,
                MagicConfiguration.resources.get(MagicResources.STRING_TITLE),
                null, null);
    }
    
    /**
     * @see #showFrame(Component, String, UpdateButtonActionListener, WindowListener)
     */
    public final void showFrame(final Component parent, final String title)
            throws MagicException {
        showFrame(parent, title, null, null);
    }
    
    /**
     * @see #showFrame(Component, String, UpdateButtonActionListener, WindowListener)
     */
    public final void showFrame(final Component parent,
            final WindowListener listener) throws MagicException {
        showFrame(parent,
                MagicConfiguration.resources.get(MagicResources.STRING_TITLE),
                null, listener);
    }
    
    /**
     * Show a frame containing the resulting interface.
     * @param parent Parent component
     * @param title Title for the window
     * @param listener Listener for the update button
     * @param windowListener Window listener
     *  (you could listen to the window close action, for example)
     * @throws MagicException {@link #render()}
     */
    public final void showFrame(final Component parent, final String title,
            final UpdateButtonActionListener listener,
            final WindowListener windowListener)
            throws MagicException {
        // render the container
        final Container container = this.renderContainer();
        if (listener != null) {
            ((MagicContainer) container).addUpdateButtonActionListener(listener);
        }
        // create and show frame
        createAndShowFrame(parent, container, title, windowListener);
    }
    
    /**
     * @param parent Parent component
     * @param container The frame's content
     * @param title Title for the window
     * @param listener Listener for the update button
     * @param windowListener Window listener
     * @throws InvalidConfigurationException
     *  {@link #createFrame(Component, String, WindowListener)}
     */
    private void createAndShowFrame(final Component parent,
            final Container container, final String title,
            final WindowListener windowListener) throws InvalidConfigurationException {
        final Frame frame = createFrame(parent, title, windowListener);
        showFrame(parent, container, frame);
    }
}

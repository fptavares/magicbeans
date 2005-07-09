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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.beans.SinglePropertyWrapper;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.exceptions.PropertyException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.observer.Observer;
import org.devyant.magicbeans.observer.Subject;
import org.devyant.magicbeans.ui.listeners.UpdateButtonActionListener;
import org.devyant.magicbeans.ui.listeners.UpdateListenerFactory;


/**
 * MagicBean is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @since 18/Abr/2005 19:44:06
 */
public class MagicBean extends Observable implements Observer {
    /**
     * The object <code>Object</code>.
     */
    private Object object;
    
    /**
     * The beanPath <code>String</code>.
     */
    private final String beanPath;
    /**
     * The superBeanClassName <code>String</code>.
     */
    private String superBeanClassName;
    
    /**
     * Creates a new <code>MagicBean</code> instance.
     * @param object The object to map
     */
    public MagicBean(Object object) {
        this(object, object.getClass().getName(), null);
    }
    
    /**
     * Creates a new <code>MagicBean</code> instance.
     * @param property The property who's object we want to map
     * @throws PropertyException {@link MagicProperty#get()}
     */
    public MagicBean(MagicProperty property) throws PropertyException {
        this(property.get(),
                property.getSuperBeanClassName(), property.getBeanPath());
    }
    
    /**
     * Creates a new <code>MagicBean</code> instance.
     * @param object The object to map
     * @param beanPath The bean path to use
     */
    public MagicBean(Object object, String beanPath) {
        this(object, object.getClass().getName(), beanPath);
    }
    
    /**
     * Creates a new <code>MagicBean</code> instance.
     * @param object The object to map
     * @param beanPath The bean path to use
     * @param superBeanClassName The base bean's class name
     */
    public MagicBean(Object object,
            String superBeanClassName, String beanPath) {
        super();
        this.object = object;
        this.beanPath = beanPath;
        this.superBeanClassName = superBeanClassName;
    }
    
    /**
     * @throws MagicException Thrown if something goes wrong
     *  during the GUI generation process.
     */
    public Container render() throws MagicException {
        // create the magic property object
        final MagicProperty property = new MagicProperty(this.superBeanClassName,
                this.beanPath, this, "object", true, false);
        
        // get the base container
        MagicUtils.info("Generating the base container.");
        MagicContainer container =
            MagicFactory.getContainerInstanceFor(property);
        
        if (MagicUtils.cannotStandalone(container)) {
            MagicUtils.info("The object cannot be mapped to a standalone container."
                    + " Using a wrapper...");
            // no container could be found for this bean
            // it is a simple property
            // we have to tranform it into a bean
            this.object = new SinglePropertyWrapper(this.object);
            
            // now we should be able to retrive the correct container
            container = MagicFactory.getContainerInstanceFor(property);
        }
        
        // bind container to this MagicBean's bean
        MagicUtils.info("Binding the base container to a new MagicProperty"
                + " containing the bean.");
        container.bindTo(property);

        MagicUtils.info("Rendering the base container.");
        return container.render();
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
            final WindowAdapter adapter) throws MagicException {
        showFrame(parent,
                MagicConfiguration.resources.get(MagicResources.STRING_TITLE),
                null, adapter);
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
        final Container container = this.render();
        if (listener != null) {
            ((MagicContainer) container).addUpdateButtonActionListener(listener);
        }
        // create and show frame
        createAndShowFrame(parent, container, title, windowListener);
    }
    
    /**
     * Show a frame containing the resulting interface.
     * @param parent Parent component
     * @param title Title for the window
     * @param listener Listener for the update button
     * @throws MagicException {@link #render()}
     */
    public final void showInternalFrame(final Component parent,
            final String title, UpdateButtonActionListener listener)
            throws MagicException {
        // create frame
        final Frame frame = createFrame(parent, title, null);
        // render the container
        final Container container = this.render();
        // add user's listener
        if (listener != null) {
            ((MagicContainer) container).addUpdateButtonActionListener(listener);
        }
        // add update listener
        ((MagicContainer) container).addUpdateButtonActionListener(
                UpdateListenerFactory.createListenerInstance(frame));
        // show frame
        showFrame(parent, container, frame);
    }

    /**
     * @param parent Parent component
     * @param title Title for the window
     * @param listener Listener for the update button
     * @param windowListener Window listener
     */
    private Frame createFrame(final Component parent, final String title,
            final WindowListener windowListener) {
        final Frame frame = new JFrame(title);
        if (windowListener != null) {
            frame.addWindowListener(windowListener);
        }
        return frame;
    }

    /**
     * @param parent Parent component
     * @param container The frame's content
     * @param frame The frame instance
     */
    private void showFrame(final Component parent, final Container container, final Frame frame) {
        frame.add(container);
        frame.pack();
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }
    
    /**
     * @param parent Parent component
     * @param container The frame's content
     * @param title Title for the window
     * @param listener Listener for the update button
     * @param windowListener Window listener
     */
    private void createAndShowFrame(final Component parent,
            final Container container, final String title,
            final WindowListener windowListener) {
        final Frame frame = createFrame(parent, title, windowListener);
        showFrame(parent, container, frame);
    }

    /**
     * @param resources The resource bundle to use
     * @todo can include at runtime?
     */
    public void includeResources(ResourceBundle resources) {
        //this.resources = new MagicResources(resources);
    }

    /**
     * @param baseName The base name of the resource bundle to use
     */
    public void includeResources(String baseName) {
        //includeResources(ResourceBundle.getBundle(baseName));
    }

    /**
     * The getter method for the object property.
     * @return The property's <code>MagicBean</code> value
     */
    public Object getObject() {
        return object;
    }

    /**
     * The setter method for the object property.
     * @param object The <code>Object</code> to set
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * Returns the real value of this wrapped by this class. An object might
     * have been wrapped by a <code>SinglePropertyWrapper</code>
     * if it wasn't a bean.
     * @return An object.
     * @see org.devyant.magicbeans.beans.SinglePropertyWrapper
     */
    public final Object getRealValue() {
        if (object instanceof SinglePropertyWrapper) {
            return ((SinglePropertyWrapper) object).get();
        } else {
            return object;
        }
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        return String.valueOf(object);
    }

    /**
     * @see org.devyant.magicbeans.observer.Observer#update(org.devyant.magicbeans.observer.Subject)
     */
    public void update(Subject o) {
        this.notifyObservers();
    }
}

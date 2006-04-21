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
import org.devyant.magicbeans.MagicContainer;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.beans.AuxiliarBean;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.beans.SinglePropertyWrapper;
import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.ui.WindowFactory;

/**
 * AbstractMagicBean is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Aug 1, 2005 5:32:45 AM
 */
public abstract class AbstractMagicBean<T> implements AuxiliarBean {
    /**
     * The object <code>Object</code>.
     */
    protected Object object;
    
    /**
     * The beanPath <code>String</code>.
     */
    private final String beanPath;
    /**
     * The superBeanClassName <code>String</code>.
     */
    private String superBeanClassName;
    
    /**
     * The singleton <code>MagicComponent</code> to return on {@link #render()}.
     */
    protected MagicComponent<? extends T> component;
    
    /**
     * The standalone <code>boolean</code>.
     */
    protected final boolean standalone;
    
    /**
     * Creates a new <code>AbstractMagicBean</code> instance.
     * @param object The object to map
     * @param beanPath The bean path to use
     * @param superBeanClassName The base bean's class name
     * @param standalone Is it a standalone container?
     */
    public AbstractMagicBean(Object object, final String superBeanClassName,
            final String beanPath, final boolean standalone) {
        super();
        this.object = object;
        this.beanPath = beanPath;
        this.superBeanClassName = superBeanClassName;
        this.standalone = standalone;
    }

    /**
     * @throws MagicException Thrown if something goes wrong
     *  during the GUI generation process.
     */
    public T render() throws MagicException {
        if (this.component == null) {
            this.component = createMagicComponent();
        }
        
        return this.component.render();
    }

    /**
     * @param parent Parent component
     * @param title Title for the window
     * @param listener Listener for the update button
     * @param windowListener Window listener
     * @throws InvalidConfigurationException {@link WindowFactory#createFrame()}
     */
    protected Frame createFrame(final Component parent, final String title,
            final WindowListener windowListener)
            throws InvalidConfigurationException {
        final Frame frame = WindowFactory.createFrame();
        frame.setTitle(title);
        if (windowListener != null) {
            frame.addWindowListener(windowListener);
        }
        return frame;
    }

    /**
     * @param parent Parent component
     * @param component The frame's content
     * @param frame The frame instance
     */
    protected void showFrame(final Component parent, final Component component,
            final Frame frame) {
        frame.add(component);
        frame.pack();
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
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
     * Returns the real value of the bean. The object might have been
     * wrapped by a <code>SinglePropertyWrapper</code> if it wasn't a bean.
     * @return An object.
     * @see org.devyant.magicbeans.beans.SinglePropertyWrapper
     */
    public final Object getValue() {
        if (object instanceof SinglePropertyWrapper) {
            return ((SinglePropertyWrapper) object).getProperty();
        } else {
            return object;
        }
    }
    
    /**
     * @return
     * @throws MagicException
     */
    protected MagicComponent<? extends T> createMagicComponent() throws MagicException {
        // create the magic property object
        final MagicProperty property = createMagicProperty();
        
        // get the component
        MagicUtils.info("Generating the component.");
        final MagicComponent<? extends T> mComponent =
            getMagicComponentFor(property);
        
        // bind container to this MagicBean's bean
        MagicUtils.info("Binding the component to a new MagicProperty"
                + " containing the bean.");
        mComponent.bindTo(property);
   
        if (mComponent instanceof MagicContainer<?>) {
            ((MagicContainer<?>) mComponent).setStandalone(this.standalone);
        }
        
        return mComponent;
    }

    private MagicProperty createMagicProperty() {
        return new MagicProperty(this.superBeanClassName,
                this.beanPath, this, "object");
    }
    protected abstract MagicComponent<? extends T> getMagicComponentFor(
            final MagicProperty property) throws MagicException;
}

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
import java.util.Observable;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.observer.Observer;
import org.devyant.magicbeans.observer.Subject;
import org.devyant.magicbeans.swing.utils.BasicDialog;
import org.devyant.magicbeans.utils.beans.SinglePropertyWrapper;
import org.devyant.magicbeans.utils.containers.NonStandaloneContainer;


/**
 * MagicBean is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @since 18/Abr/2005 19:44:06
 */
public class MagicBean extends Observable implements Observer {
    /**
     * The <code>boolean</code> that tells whether to validate or not.
     * <p>Defaults to <code>true</code>.</p>
     */
    private boolean validate = true;
    /**
     * The object <code>Object</code>.
     */
    private Object object;
    /**
     * The configuration.
     */
    private MagicConfiguration configuration;
    
    private final String beanPath;
    
    /**
     * Creates a new <code>MagicBean</code> instance.
     * @param object The object to map
     */
    public MagicBean(Object object) {
        this(object, null);
    }
    
    /**
     * Creates a new <code>MagicBean</code> instance.
     * @param object The object to map
     */
    public MagicBean(Object object, String beanPath) {
        super();
        MagicUtils.debug(object.getClass().getName());
        this.object = object;
        this.beanPath = beanPath;
        this.configuration =
            new MagicConfiguration(object.getClass().getName(), beanPath);
    }
    
    /**
     * @throws MagicException Thrown if something goes wrong
     *  during the GUI generation process.
     */
    public Container render() throws MagicException {
        // get the base container
        MagicUtils.info("Generating the base container.");
        MagicView container =
            MagicFactory.getContainerInstanceFor(object.getClass(),
                    configuration);
        
        if (container == null
                || container instanceof NonStandaloneContainer) {
            MagicUtils.info("The object cannot be mapped to a standalone container."
                    + " Using a wrapper...");
            // no container could be found for this bean
            // it is a simple property
            // we have to tranform it into a bean
            object = new SinglePropertyWrapper(object);
            
            // now we should be able to retrive the correct container
            container = MagicFactory.getContainerInstanceFor(object.getClass(),
                    configuration);
        }
        
        // bind container to this MagicBean's bean
        MagicUtils.info("Binding the base container to a new MagicProperty"
                + " containing the bean.");
        container.bindTo(new MagicProperty(
                this.getRealValue().getClass().getName(), beanPath, this, "object", false));

        MagicUtils.info("Rendering the base container.");
        return container.render();
    }
    
    /**
     * @param parent Parent container
     * @throws MagicException {@link #render()}
     */
    public final void showDialog(Frame parent) throws MagicException {
        showDialog(parent, false);
    }
    /**
     * @param parent Parent container
     * @param modal Whether it is modal
     * @throws MagicException {@link #render()}
     */
    public final void showDialog(Frame parent, boolean modal) throws MagicException {
        new BasicDialog(this.render(), parent,
                MagicConfiguration.resources.getString(MagicResources.STRING_TITLE),
                modal).setVisible(true);
    }
    
    /**
     * Show a frame containing the resulting interface.
     * @param parent Parent container
     * @throws MagicException {@link #render()}
     */
    public final void showFrame(Component parent, WindowAdapter adapter)
            throws MagicException {
        JFrame frame =
            new JFrame(MagicConfiguration.resources.getString(MagicResources.STRING_TITLE));
        frame.addWindowListener(adapter);
        frame.setContentPane(this.render());
        frame.pack();
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
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
     * @return Returns whether to validate or not.
     */
    public final boolean isValidate() {
        return validate;
    }
    /**
     * @param validate The validate <code>boolean</code> to set.
     */
    public final void setValidate(boolean validate) {
        this.validate = validate;
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
        MagicUtils.debug("After set: " + this.object);
    }

    /**
     * Returns the real value of this wrapped by this class. An object might
     * have been wrapped by a <code>SinglePropertyWrapper</code>
     * if it wasn't a bean.
     * @return An object.
     * @see org.devyant.magicbeans.utils.beans.SinglePropertyWrapper
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

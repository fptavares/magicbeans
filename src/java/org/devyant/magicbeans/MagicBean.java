/*
 * Copyright 2005 Filipe Tavares
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devyant.magicbeans;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.observer.Observer;
import org.devyant.magicbeans.observer.Subject;
import org.devyant.magicbeans.swing.utils.BasicDialog;
import org.devyant.magicbeans.utils.beans.SinglePropertyWrapper;


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
     * The message resources.
     */
    private MagicResources resources;
    
    /**
     * Creates a new <code>MagicBean</code> instance.
     * @param object The object to map
     */
    public MagicBean(Object object) {
        super();
        this.object = object;
    }
    
    /**
     * @throws Exception
     * @todo finish...
     * @todo replace Exception with something like MagicException
     */
    public Container render() throws Exception {
        // get the base container
        MagicView container =
            MagicFactory.getContainerInstanceFor(object.getClass());
        
        if (container == null) {
            // no container could be found for this bean
            // it is a simple property
            // we have to tranform it into a bean
            object = new SinglePropertyWrapper(object);
            
            // now we should be able to retrive the correct container
            container = MagicFactory.getContainerInstanceFor(object.getClass());
        }
        
        // i18n resources
        if (resources != null) {
            container.setResources(resources);
        }
        
        // bind container to this MagicBean's bean
        container.bindTo(new MagicProperty(this, "object", true));
        
        return container.render();
    }
    
    /**
     * @param parent Parent container
     * @throws Exception
     */
    public final void showDialog(Frame parent) throws Exception {
        showDialog(parent, false);
    }
    /**
     * @param parent Parent container
     * @param modal Whether it is modal
     * @throws Exception
     */
    public final void showDialog(Frame parent, boolean modal) throws Exception {
        new BasicDialog(this.render(), parent,
                resources.getString(MagicResources.STRING_TITLE),
                modal).setVisible(true);
    }
    
    /**
     * @param parent Parent container
     * @throws Exception
     */
    public final void showFrame(Component parent) throws Exception {
        JFrame frame =
            new JFrame(resources.getString(MagicResources.STRING_TITLE));
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                MagicUtils.debug(object.toString());
                System.exit(0);
            }
        });
        frame.setContentPane(render());
        frame.pack();
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }

    /**
     * @param resources The resource bundle to use
     */
    public void includeResources(ResourceBundle resources) {
        this.resources = new MagicResources(resources);
    }

    /**
     * @param baseName The base name of the resource bundle to use
     */
    public void includeResources(String baseName) {
        includeResources(ResourceBundle.getBundle(baseName));
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
    public final void setResources(MagicResources resources) {
        this.resources = resources;
    }

    /**
     * @return Returns the object.
     */
    public final Object getObject() {
        return object;
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

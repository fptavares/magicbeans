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

import java.awt.Component;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicContainer;
import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.ui.listeners.UpdateButtonActionListener;
import org.devyant.magicbeans.utils.components.UnlabeledComponent;

/**
 * AbstractMagicContainer is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Oct 6, 2005 12:29:43 PM
 */
public abstract class AbstractMagicContainer extends AbstractMagicComponent
        implements MagicContainer {
    
    /**
     * The standalone <code>boolean</code>.
     */
    private boolean standalone = false;
    
    /**
     * The <code>MagicLayout</code>.
     */
    protected MagicLayout layout;
    
    /**
     * The status label component.
     */
    protected Object status;
    
    /**
     * Creates a new <b>labeled</b> <code>AbstractMagicContainer</code> instance.
     */
    public AbstractMagicContainer() {
        super(true);
    }

    /**
     * Creates a new <code>AbstractMagicContainer</code> instance.
     * @param labeled Whether this is a labeled container
     */
    public AbstractMagicContainer(final boolean labeled) {
        super(labeled);
    }
    
    /*
     * The method override checks wether this object is a bean container
     * or simply a component acting as a container.
     * If it is in fact a container for a bean, then the {@link #massUpdate()}
     * method is called.
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#update()
     */
    /*public void update() throws MagicException {
        if (this.beanContainer) {
            massUpdate();
        } else {
            super.update();
        }
    }*/
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#finalize()
     */
    protected void finalize() {
        if (!isStandalone()) {
            return;
        }

        // initialize status
        this.status = getFactory().getStatusComponent();
        // initialize button
        final Object button = getFactory().getButtonComponent(MagicConfiguration
                .resources.get(MagicResources.STRING_OKBUTTON));
        initMagicContainerAction(button);
        // add components
        layout.addButton(this.component, button);
        layout.addStatus(this.component, this.status);
    }

    /**
     * Initializes the handler for the MagicContainerAction.
     * @param button The OK button
     * @todo implementation
     */
    protected abstract void initMagicContainerAction(Object button);

    /**
     * @see org.devyant.magicbeans.MagicContainer#setStandalone(boolean)
     */
    public void setStandalone(final boolean standalone) {
        this.standalone = standalone;
    }

    /**
     * @see org.devyant.magicbeans.MagicContainer#setMagicLayout(org.devyant.magicbeans.MagicLayout)
     */
    public void setMagicLayout(final MagicLayout layout) {
        this.layout = layout;
    }

    /**
     * @see org.devyant.magicbeans.MagicContainer#addMagicComponent(org.devyant.magicbeans.MagicComponent)
     */
    public void addMagicComponent(MagicComponent component) {
        if (component.isLabeled()) {
            addUnlabeledComponent(component);
        } else {
            addLabeledComponent(component, component.getName());
        }
    }

    /**
     * @see org.devyant.magicbeans.ui.listeners.UpdateButtonActionHandler#addUpdateButtonActionListener(org.devyant.magicbeans.ui.listeners.UpdateButtonActionListener)
     */
    public void addUpdateButtonActionListener(final UpdateButtonActionListener l) {
        // @todo Auto-generated method stub

    }

    /**
     * @see org.devyant.magicbeans.ui.listeners.UpdateButtonActionHandler#fireUpdateButtonAction()
     */
    public void fireUpdateButtonAction() {
        // @todo Auto-generated method stub

    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#setValue(java.lang.Object)
     */
    protected void setValue(Object value) throws MagicException {
        // fill with property's value
        final Object object = getProperty().get();
        if (object == null) {
            return;
        }
        
        final Collection properties =
            MagicUtils.getProperties(object, getProperty());
        
        for (Iterator i = properties.iterator(); i.hasNext(); ) {
            final MagicProperty prop = (MagicProperty) i.next();
            final MagicComponent component;
            
            // verify if is visible
            if (!prop.getConfiguration()
                    .getSpecialBoolean(MagicConfiguration.XML_VISIBLE)) {
                continue; // not visible, so continue to the next property
            }
            
            // verify if is nested
            if (prop.getConfiguration()
                    .getSpecialBoolean(MagicConfiguration.XML_NESTED)) {
                // basic nested component
                component = getFactory().getNestedComponentInstanceFor(prop);
            } else {
                // isolated component
                component = getFactory().getIsolatedComponentFor(prop);
            }
            
            MagicUtils.info("Binding the generated component to the property.");
            component.bindTo(prop);
            
            addMagicComponent(component);
        }
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#getValue()
     * @return <code>null</code>
     */
    protected Object getValue() throws MagicException {
        return null;
    }

    /**
     * This method should return the apropriate factory, considering which
     * toolkit we're using.
     * <p>This factory may easilly be fetched using, for example,
     * <code>MagicFactory.swing()</code> (for the Swing toolkit).</p>
     * @return An <code>UIFactory</code> instance
     */
    protected abstract UIFactory getFactory();

    /**
     * The getter method for the standalone property.
     * @return The property's <code>boolean</code> value
     */
    protected boolean isStandalone() {
        return this.standalone;
    }

}

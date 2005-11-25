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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;

/**
 * AbstractBaseContainer is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Oct 28, 2005 7:13:05 PM
 */
public abstract class AbstractBaseContainer extends AbstractMagicContainer {
    
    /**
     * A <code>Collection</code> of this container's child components.
     */
    private final Collection components = new LinkedList();
    
    /**
     * Creates a new <code>AbstractBaseContainer</code> instance.
     */
    public AbstractBaseContainer(final UIFactory factory, final boolean nested) {
        super(factory, nested);
    }
    
    /**
     * Mass layer update.
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#update()
     */
    public final void update() throws MagicException {
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            final MagicComponent child = (MagicComponent) i.next();
            // do layer-by-layer update
            child.update();
            
            // debug
            MagicUtils.debug(child.getProperty().getName()
                    + " : " + child.getProperty().get());
        }
    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#setValue(java.lang.Object)
     */
    protected final void setValue(Object value) throws MagicException {
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
            final boolean nested = prop.getConfiguration()
            .getSpecialBoolean(MagicConfiguration.XML_NESTED);
            if (nested) {
                // basic nested component
                component = getFactory().getNestedComponentInstanceFor(prop);
            } else {
                // isolated component
                component = getFactory().getIsolatedComponentFor(prop);
            }
            
            MagicUtils.info("Binding the generated component to the property.");
            component.bindTo(prop);
            
            addMagicComponent(component, nested);
        }
    }
    
    /**
     * @param mComponent
     * @param nested
     * @throws MagicException
     */
    private final void addMagicComponent(MagicComponent mComponent,
            final boolean nested) throws MagicException {
        if (mComponent.isLabeled()) {
            Object label = getFactory().createLabel(mComponent.getName());
            // add label + magic component
            if (nested) {
                this.layout.addLabeledComponent(
                        this.component, label, mComponent);
            } else {
                this.layout.addLabeledIsolatedComponent(
                        this.component, label, mComponent);
            }
        } else {
            // add magic component
            this.layout.addUnlabeledComponent(this.component, mComponent);
        }
    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#getValue()
     * @return <code>null</code>
     */
    protected final Object getValue() throws MagicException {
        return null;
    }
    
}

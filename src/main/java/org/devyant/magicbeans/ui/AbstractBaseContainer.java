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
 * @see AbstractMagicContainer
 * @since Oct 28, 2005 7:13:05 PM
 */
public abstract class AbstractBaseContainer<C>
        extends AbstractMagicContainer<C> {
    
    /**
     * A <code>Collection</code> of this container's child components.
     */
    private Collection<MagicComponent<?>> components;
    
    /**
     * Creates a new <code>AbstractBaseContainer</code> instance.
     * @see AbstractMagicContainer#AbstractMagicContainer(UIFactory)
     */
    public AbstractBaseContainer(final UIFactory factory) {
        super(factory);
    }
    
    /**
     * Mass layer update.
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#update()
     */
    @Override
    public final void update() throws MagicException {
        for (MagicComponent<?> child : this.components) {
            // do layer-by-layer update
            child.update();
            
            // debug
            MagicUtils.debug(child.getProperty().getName()
                    + " : " + child.getProperty().get()); //$NON-NLS-1$
        }
    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    @Override
    public final void bindTo(final MagicProperty property) throws MagicException {
        super.bindTo(property);
        
        // fill with property's value
        final Object value = getProperty().get();
        if (value == null) {
            return;
        }
        
        this.components = new LinkedList<MagicComponent<?>>();
        
        final Collection<MagicProperty> properties =
            MagicUtils.getProperties(value, getProperty());
        
        for (MagicProperty prop : properties) {
            final MagicComponent<?> component;
            
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
                component = getFactory().getComponentForIsolated(prop);
            }
            
            MagicUtils.debug("Binding the generated component to the property."); //$NON-NLS-1$
            component.bindTo(prop);
            
            this.components.add(component);
        }
    }
    
    /**
     * Add the actual components to the actual container 
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#initializeComponent()
     */
    @Override
    protected void initializeComponent() throws MagicException {
        super.initializeComponent();
        
        for (MagicComponent<?> mComponent : this.components) {
            addMagicComponent(mComponent);
        }
    }
    
    /**
     * @param mComponent
     * @throws MagicException
     */
    private final void addMagicComponent(MagicComponent<?> mComponent)
            throws MagicException {
        if (mComponent.isLabeled()) {
            final Object label = getFactory().createLabel(mComponent.getName());
            // add label + magic component
            if (isNested()) {
                this.layout.addLabeledComponent(
                        this.component, label, mComponent);
            } else {
                this.layout.addLabeledIsolatedComponent(
                        this, label, mComponent);
            }
        } else {
            // add magic component
            this.layout.addUnlabeledComponent(this.component, mComponent);
        }
    }
    
    /**
     * @todo this doesn't seem very correct...
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#setValue(java.lang.Object)
     */
    @Override
    protected final void setValue(Object value) {
        // do nothing
    }
    
    /**
     * @todo this doesn't seem very correct...
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#getValue()
     * @return <code>null</code>
     */
    @Override
    protected final Object getValue() {
        return null;
    }
    
}

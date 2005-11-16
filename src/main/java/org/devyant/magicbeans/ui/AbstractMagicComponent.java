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

import org.apache.commons.lang.StringUtils;
import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;

/**
 * AbstractMagicComponent is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Oct 4, 2005 4:43:07 AM
 */
public abstract class AbstractMagicComponent implements MagicComponent {
    /**
     * The property to bind to.
     */
    private MagicProperty property;
    /**
     * The component to be binded to a property.
     */
    protected Object component;
    
    /**
     * The property's name. This <code>String</code> will
     * be used for the component's label/title.
     */
    private String name;
    
    /**
     * Whether this is a labeled component or not.
     */
    private boolean labeled;
    
    /**
     * Creates a new <b>labeled</b> <code>AbstractMagicComponent</code> instance.
     */
    protected AbstractMagicComponent() {
        this(true);
    }

    /**
     * Creates a new <code>AbstractMagicComponent</code> instance.
     * @param labeled Whether this is a labeled component
     */
    public AbstractMagicComponent(final boolean labeled) {
        this.labeled = labeled;
    }


    /**
     * @see org.devyant.magicbeans.MagicComponent#update()
     */
    public void update() throws MagicException {
        this.property.set(getValue());
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    public void bindTo(final MagicProperty property) {
        this.property = property;
        // get the property's name
        this.name = MagicConfiguration.resources.getNameFor(this.property);
        this.labeled = this.labeled && !StringUtils.isBlank(this.name);
        /*
         * TODO: maybe move the code bellow to avoid
         * recursive initialization of non-rendered components
         */
    }

    /**
     * This is an empty implementation for the component initialization.
     * A component that requires the layout and property to be set before
     * the initialization of certain parts of it's implementation
     * may override this method to achieve this.
     */
    protected void initialize() {
    }

    /**
     * This is an empty implementation for the component finalization.
     */
    protected void finalize() {
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#getProperty()
     */
    public final MagicProperty getProperty() {
        return this.property;
    }
    
    /**
     * @see org.devyant.magicbeans.MagicComponent#render()
     */
    public Object render() throws MagicException {
        this.component = createComponent();
        // initialize the component
        initialize();
        // fill with property's value
        final Object value = this.property.get();
        if (value != null) {
            setValue(value);
        }
        // finalize the component
        finalize();
        return this.component;
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#isLabeled()
     */
    public boolean isLabeled() {
        return this.labeled;
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#getName()
     */
    public String getName() {
        return this.name;
    }

    /**
     * @see org.devyant.magicbeans.utils.Previewable#preview()
     */
    public Object preview() throws MagicException {
        return getValue();
    }
    
    /**
     * Create and return the instance of the component to bind to the property.
     * @return The actual toolkit-specific component
     */
    protected abstract Object createComponent();

    /**
     * This method returns the current value that this component has
     * for it's encapsulated property.
     * @return The current value for the property
     * @throws MagicException Something went wrong
     */
    protected abstract Object getValue() throws MagicException;

    /**
     * This method sets the current value that this component has.
     * @param value The current value for the property
     * @throws MagicException Something went wrong
     */
    protected abstract void setValue(final Object value) throws MagicException;
    
}

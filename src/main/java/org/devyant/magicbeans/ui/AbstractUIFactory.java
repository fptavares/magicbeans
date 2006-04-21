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

import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicComplexComponent;
import org.devyant.magicbeans.MagicContainer;
import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.ConfigurationException;
import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.layout.LayoutIsolatedBehaviour;
import org.devyant.magicbeans.ui.listeners.WindowListener;

/**
 * AbstractUIFactory is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @param <T> The most basic type of this toolkit's components
 * @since Sep 15, 2005 1:40:50 AM
 */
public abstract class AbstractUIFactory<T> implements UIFactory<T> {
    /**
     * The COLLECTION_STYLE_COMBO <code>String</code>.
     */
    protected static final String COLLECTION_STYLE_COMBO = "combo";
    /**
     * The COLLECTION_STYLE_LIST <code>String</code>.
     */
    protected static final String COLLECTION_STYLE_LIST = "list";
    /**
     * The keyword <code>String</code>.
     */
    public final String keyword;


    /**
     * Creates a new <code>AbstractUIFactory</code> instance.
     * @param keyword The keyword that represents this toolkit
     */
    public AbstractUIFactory(final String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * @see org.devyant.magicbeans.ui.UIFactory#getNestedComponentInstanceFor(org.devyant.magicbeans.beans.MagicProperty)
     */
    public final MagicComponent<? extends T> getNestedComponentInstanceFor(
            final MagicProperty property) throws MagicException {
        return getComponentInstanceFor(property);
    }

    /**
     * @see org.devyant.magicbeans.ui.UIFactory#getBaseComponentInstanceFor(org.devyant.magicbeans.beans.MagicProperty)
     */
    public final MagicComponent<? extends T> getBaseComponentInstanceFor(
            final MagicProperty property) throws MagicException {
        return getComponentInstanceFor(property);
    }
    
    /**
     * This method returns a magic component for the specified property.
     * @param property The property
     * @return A renderer for the property
     * @throws MagicException
     *  {@link #getBaseComponentInstanceFor(MagicProperty)}
     * @FIXME remove this
     */
    private final MagicComponent<? extends T> getComponentInstanceFor(
            final MagicProperty property)
            throws MagicException {
        // create the component
        final MagicComponent<? extends T> component = getBinderInstanceFor(
                property.getType(), property.getConfiguration());
        MagicUtils.debug("Generated a new component: " + component);
        
        // initialize layout TODO: initialize later?
        
        if (component instanceof MagicComplexComponent) {
            final MagicComplexComponent<T> complex =
                (MagicComplexComponent<T>) component;
            complex.setMagicLayout(createLayoutFor(property));
            complex.setUIFactory(this);
        }
        
        // return the component
        return component;
        
    }

    /**
     * @param type The class of the object to bind to
     * @param configuration This property's configuration
     * @return A <code>MagicComponent</code> instance for the property
     * @throws InvalidConfigurationException The configuration specified
     *  an invalid value.
     * @todo complete...
     * numbers, files, colors, ... timelines (a Collection of dates)...
     */
    private final MagicComponent<? extends T> getBinderInstanceFor(
            final Class<?> type, final MagicConfiguration configuration)
            throws InvalidConfigurationException {
        MagicUtils.debug("Type for component: " + type.getName());
        // java.lang.String
        if (String.class.isAssignableFrom(type)) {
            return getComponentForString();

            // java.lang.Number
        } else if (Number.class.isAssignableFrom(type)) {
            return getComponentForNumber();

            // java.lang.Boolean
        } else if (Boolean.class.isAssignableFrom(type)) {
            return getComponentForBoolean();

            // java.util.Date
        } else if (Date.class.isAssignableFrom(type)) {
            return getComponentForDate();

            // java.util.Calendar
        } else if (Calendar.class.isAssignableFrom(type)) {
            return getComponentForCalendar();

            // java.sql.Timestamp
        } else if (Timestamp.class.isAssignableFrom(type)) {
            return getComponentForTimestamp();

            // java.sql.Date
        } else if (java.sql.Date.class.isAssignableFrom(type)) {
            return getComponentForSqlDate();

            // java.util.Collection
        } else if (Collection.class.isAssignableFrom(type)) {
            final String style =
                configuration.get(MagicConfiguration.GUI_COLLECTIONS_STYLE_KEY);
            return getComponentForCollection(style);
            
            // java.io.File
        } else if (File.class.isAssignableFrom(type)) {
            return getComponentForFile();
            
            // complex data
            // -> magic panel
            // -> auto-magic-bean -> nested magic panel
        } else {
            return getContainerForBean();
        }
    }

    /**
     * Create and return a new container.
     * @return The container instance
     */
    protected abstract MagicContainer<? extends T> getContainerForBean();
    
    /**
     * Create a component instance to bind to a <code>java.lang.String</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent<? extends T> getComponentForString();
    /**
     * Create a component instance to bind to a <code>java.lang.Number</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent<? extends T> getComponentForNumber();
    /**
     * Create a component instance to bind to a <code>java.lang.Boolean</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent<? extends T> getComponentForBoolean();
    /**
     * Create a component instance to bind to a <code>java.util.Date</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent<? extends T> getComponentForDate();
    /**
     * Create a component instance to bind to a <code>java.util.Calendar</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent<? extends T> getComponentForCalendar();
    /**
     * Create a component instance to bind to a <code>java.sql.Timestamp</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent<? extends T> getComponentForTimestamp();
    /**
     * Create a component instance to bind to a <code>java.sql.Date</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent<? extends T> getComponentForSqlDate();
    /**
     * Create a component instance to bind
     * to a <code>java.util.Collection</code>.
     * @param style The component's style
     * @return The appropriate component
     * @throws InvalidConfigurationException
     */
    protected abstract MagicComponent<? extends T> getComponentForCollection(
            final String style) throws InvalidConfigurationException;
    /**
     * Create a component instance to bind to a <code>java.io.File</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent<? extends T> getComponentForFile();
    

    /**
     * @see org.devyant.magicbeans.ui.UIFactory#createContainerFor(org.devyant.magicbeans.beans.MagicProperty, boolean)
     */
    public T createContainerFor(final MagicProperty property,
            final boolean hasIsolatedComponent) throws ConfigurationException {
        // TODO: needed?
        /*if (hasIsolatedComponent) {
            final String type = property.getConfiguration().get(
                    MagicConfiguration.GUI_ISOLATED_TYPE_KEY);
            if (MagicConfiguration.ISOLATED_TABBED_VALUE.equals(type)) {
                return createTabbedContainer();
            } else if (MagicConfiguration.ISOLATED_TREE_VALUE.equals(type)) {
                return createTreeContainer();
            } else if (MagicConfiguration.ISOLATED_CHILD_VALUE.equals(type)) {
                return createChildContainer();
            } else  {
                
                // invalid type
                throw new InvalidConfigurationException(
                        MagicConfiguration.GUI_ISOLATED_TYPE_KEY, type);
                
            }
        } else {*/
            return createContainer();
        /*}*/
    }
    
    /**
     * Create and return a layout instance.
     * @param property The behaviour type to use
     * @return The layout
     * @throws MagicException On the layout instantiation
     */
    private MagicLayout<T> createLayoutFor(final MagicProperty property)
            throws MagicException {
        final IsolatedBehaviourType type = IsolatedBehaviourType.valueOf(
                property.getConfiguration().get(
                        MagicConfiguration.GUI_ISOLATED_TYPE_KEY)
                        .toUpperCase());
        
        final MagicLayout<T> layout = (MagicLayout<T>) property
            .getConfiguration().getClassInstance(
                    MagicConfiguration.GUI_LAYOUT_PREFIX + keyword
                    + MagicConfiguration.GUI_LAYOUT_SUFIX);
        
        final LayoutIsolatedBehaviour<T> behaviour;
        switch(type) {
            case CHILD:
            default:
                behaviour = createChildBehaviour(layout);
                break;
            case TABBED:
                behaviour = createTabbedBehaviour(layout);
                break;
            case TREE:
                behaviour = createTreeBehaviour(layout);
                break;
        }
        layout.setIsolatedBehaviour(behaviour);
        return layout;
    }
    
    /**
     * @see org.devyant.magicbeans.ui.UIFactory#createAndShowWindow(java.lang.Object, org.devyant.magicbeans.MagicComponent)
     */
    public Object createAndShowWindow(T parent,
            MagicComponent<? extends T> component) throws MagicException {
        return createAndShowWindow(parent, component, null);
    }
    
    /**
     * @see org.devyant.magicbeans.ui.UIFactory#createAndShowWindow(java.lang.Object, org.devyant.magicbeans.MagicComponent, org.devyant.magicbeans.ui.listeners.WindowListener)
     */
    public Object createAndShowWindow(T parent,
            MagicComponent<? extends T> component,
            final WindowListener listener) throws MagicException {
        return createAndShowWindow(
                parent, component.getName(), component.render(), listener);
    }

    /**
     * Create and return the child behaviour.
     * @param layout The layout for which this behaviour is intended for
     * @return The behaviour instance
     * @throws UnsupportedOperationException Toolkit doesn't support this
     */
    protected abstract LayoutIsolatedBehaviour<T> createChildBehaviour(
            MagicLayout<T> layout) throws UnsupportedOperationException;
    /**
     * Create and return the tabbed behaviour.
     * @param layout The layout for which this behaviour is intended for
     * @return The behaviour instance
     * @throws UnsupportedOperationException Toolkit doesn't support this
     */
    protected abstract LayoutIsolatedBehaviour<T> createTabbedBehaviour(
            MagicLayout<T> layout) throws UnsupportedOperationException;
    /**
     * Create and return the tree behaviour.
     * @param layout The layout for which this behaviour is intended for
     * @return The behaviour instance
     * @throws UnsupportedOperationException Toolkit doesn't support this
     */
    protected abstract LayoutIsolatedBehaviour<T> createTreeBehaviour(
            MagicLayout<T> layout) throws UnsupportedOperationException;
    
}

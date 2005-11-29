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
import org.devyant.magicbeans.MagicContainer;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.conf.UnavailableConfigurationException;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.layouts.LayoutFactory;
import org.devyant.magicbeans.ui.swing.SwingContainer;
import org.devyant.magicbeans.ui.swing.SwingNestedContainer;
import org.devyant.magicbeans.ui.swing.isolated.SwingChildComponent;

/**
 * AbstractUIFactory is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Sep 15, 2005 1:40:50 AM
 */
public abstract class AbstractUIFactory implements UIFactory {
    /**
     * The COLLECTION_STYLE_COMBO <code>String</code>.
     */
    protected static final String COLLECTION_STYLE_COMBO = "combo";
    /**
     * The COLLECTION_STYLE_LIST <code>String</code>.
     */
    protected static final String COLLECTION_STYLE_LIST = "list";


    /**
     * @see org.devyant.magicbeans.ui.UIFactory#getNestedComponentInstanceFor(org.devyant.magicbeans.beans.MagicProperty)
     */
    public final MagicComponent getNestedComponentInstanceFor(
            final MagicProperty property) throws MagicException {
        return getComponentInstanceFor(property, true);
    }

    /**
     * @see org.devyant.magicbeans.ui.UIFactory#getBaseComponentInstanceFor(org.devyant.magicbeans.beans.MagicProperty)
     */
    public final MagicComponent getBaseComponentInstanceFor(
            final MagicProperty property) throws MagicException {
        return getComponentInstanceFor(property, false);
    }
    
    /**
     * This method returns a magic component for the specified property.
     * @param property The property
     * @param nested Whether it is a nested component or not
     * @return A renderer for the property
     * @throws MagicException
     *  {@link #getBaseComponentInstanceFor(MagicProperty)}
     *  {@link LayoutFactory#createLayout(MagicConfiguration)}
     */
    private final MagicComponent getComponentInstanceFor(
            final MagicProperty property, final boolean nested)
            throws MagicException {
        // create the component
        final MagicComponent component = getBinderInstanceFor(property.getType(),
                property.getConfiguration(), nested);
        MagicUtils.debug("Generated a new component: " + component);
        
        // Magic Layout
        if (component instanceof MagicContainer) {
            configureContainer((MagicContainer) component, property);
        }
        
        // return the component
        return component;
        
    }

    /**
     * @param container
     * @param property
     * @throws MagicException
     */
    protected void configureContainer(MagicContainer container,
            final MagicProperty property) throws MagicException {
        // instantiate the layout manager
        container.setMagicLayout(
                LayoutFactory.createLayout(property.getConfiguration()));
    }

    /**
     * @param type The class of the object to bind to
     * @param nested Whether it is a nested component or not
     * @return A <code>MagicComponent</code> instance for the property
     * @throws InvalidConfigurationException The configuration specified
     *  an invalid value.
     * @todo complete...
     * numbers, files, colors, ... timelines (a Collection of dates)...
     */
    private final MagicComponent getBinderInstanceFor(final Class type,
            final MagicConfiguration configuration, final boolean nested)
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
            /*if (nested) { TODO: delete?
                return new SwingNestedContainer();
            } else {*/
                return new SwingContainer();
            /*}*/
        }
    }

    /**
     * @todo all wrong
     * @see org.devyant.magicbeans.ui.UIFactory#getComponentForIsolated(org.devyant.magicbeans.beans.MagicProperty)
     */
    public final MagicComponent getComponentForIsolated(
            final MagicProperty property) throws MagicException {
        final MagicComponent component = getNestedComponentInstanceFor(property);
        
        if (!MagicUtils.mayBeIsolated(component)) {
            return component;
        }
        
        
        final String type = property.getConfiguration()
                .get(MagicConfiguration.GUI_ISOLATED_TYPE_KEY);
        
        if (MagicConfiguration.ISOLATED_TREE_VALUE.equals(type)) {
            
            // @todo tree
            throw new UnavailableConfigurationException(
                    MagicConfiguration.GUI_ISOLATED_TYPE_KEY, type);
            
        } else if (MagicConfiguration.ISOLATED_TABBED_VALUE.equals(type)) {
            
            // tabbed
            /*
             * in tabbed, all components have to be created
             * that's why we always return the component
             * the actual isolated component is created at TabbedLayoutDecorator
             * it will contain all the components returned by this method
             */
            return component;
            
        } else if (MagicConfiguration.ISOLATED_CHILD_VALUE.equals(type)) {
            
            // child
            return new SwingChildComponent();
            
        } else  {
            
            // invalid type
            throw new InvalidConfigurationException(
                    MagicConfiguration.GUI_ISOLATED_TYPE_KEY, type);
            
        }
    }

    
    /**
     * Create a component instance to bind to a <code>java.lang.String</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent getComponentForString();
    /**
     * Create a component instance to bind to a <code>java.lang.Number</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent getComponentForNumber();
    /**
     * Create a component instance to bind to a <code>java.lang.Boolean</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent getComponentForBoolean();
    /**
     * Create a component instance to bind to a <code>java.util.Date</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent getComponentForDate();
    /**
     * Create a component instance to bind to a <code>java.util.Calendar</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent getComponentForCalendar();
    /**
     * Create a component instance to bind to a <code>java.sql.Timestamp</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent getComponentForTimestamp();
    /**
     * Create a component instance to bind to a <code>java.sql.Date</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent getComponentForSqlDate();
    /**
     * Create a component instance to bind
     * to a <code>java.util.Collection</code>.
     * @param style The component's style
     * @return The appropriate component
     * @throws InvalidConfigurationException
     */
    protected abstract MagicComponent getComponentForCollection(
            final String style) throws InvalidConfigurationException;
    /**
     * Create a component instance to bind to a <code>java.io.File</code>.
     * @return The appropriate component
     */
    protected abstract MagicComponent getComponentForFile();
    
}

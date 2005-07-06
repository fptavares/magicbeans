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
package org.devyant.magicbeans.swing;

import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.MagicView;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.generalizers.date.CalendarGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.DateGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.NoNeedForGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.TimestampGeneralizerImpl;
import org.devyant.magicbeans.swing.generalizers.list.ComboBoxGeneralizerImpl;
import org.devyant.magicbeans.swing.generalizers.list.ListGeneralizerImpl;
import org.devyant.magicbeans.utils.containers.NestedBeanContainer;

/**
 * Factory for Swing components. This is used to get the right component
 * for each property class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Abr/2005 2:38:36
 */
public abstract class SwingComponentFactory {
    /**
     * The COLLECTION_STYLE_COMBO <code>String</code>.
     */
    private static final String COLLECTION_STYLE_COMBO = "combo";
    /**
     * The COLLECTION_STYLE_LIST <code>String</code>.
     */
    private static final String COLLECTION_STYLE_LIST = "list";


    /**
     * Nested defaults to true.
     * @param property The property
     * @return A renderer for the property
     * @throws InvalidConfigurationException The configuration specified
     *  an invalid value.
     */
    protected static final MagicComponent getComponentInstanceFor(
            final MagicProperty property) throws InvalidConfigurationException {
        MagicComponent c = getBinderInstanceFor(property.getType(),
                property.getConfiguration(), true);
        MagicUtils.debug("Generated a new SwingComponent: " + c);
        return c;
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
    public static MagicComponent getBinderInstanceFor(final Class type,
            final MagicConfiguration configuration, final boolean nested)
            throws InvalidConfigurationException {
        MagicUtils.debug("Type for SwingComponent: " + type.getName());
        // java.lang.String
        if (String.class.isAssignableFrom(type)) {
            return new SwingStringComponent();

            // java.lang.Number
        } else if (Number.class.isAssignableFrom(type)) {
            return new SwingNumberComponent();

            // java.util.Date
        } else if (Date.class.isAssignableFrom(type)) {
            return new SwingDateComponent(new NoNeedForGeneralizerImpl());

            // java.util.Calendar
        } else if (Calendar.class.isAssignableFrom(type)) {
            return new SwingDateComponent(new CalendarGeneralizerImpl());

            // java.sql.Timestamp
        } else if (Timestamp.class.isAssignableFrom(type)) {
            return new SwingDateComponent(new TimestampGeneralizerImpl());

            // java.sql.Date
        } else if (java.sql.Date.class.isAssignableFrom(type)) {
            return new SwingDateComponent(new DateGeneralizerImpl());

            // java.util.Collection
        } else if (Collection.class.isAssignableFrom(type)) {
            final String style =
                configuration.get(MagicConfiguration.GUI_COLLECTIONS_STYLE_KEY);
            if (style.equals(COLLECTION_STYLE_LIST)) {
                // JList
                return new SwingCollectionComponent(new ListGeneralizerImpl());
            } else if (style.equals(COLLECTION_STYLE_COMBO)) {
                // JComboBox
                return new SwingCollectionComponent(new ComboBoxGeneralizerImpl());
            } else {
                // invalid style
                throw new InvalidConfigurationException(
                        MagicConfiguration.GUI_COLLECTIONS_STYLE_KEY, style);
            }
            
            // java.io.File
        } else if (File.class.isAssignableFrom(type)) {
            return new SwingFileComponent();
            
            
            // complex data
            // -> magic panel
            // -> auto-magic-bean -> nested magic panel
        } else {
            if (nested) {
                return new SwingNestedContainer();
            } else {
                return new SwingContainer();
            }
        }
    }


    /**
     * @return An isolated component if the property is mapped to an
     *  instance of a MagicView. If not, the normal component is returned.
     * @throws InvalidConfigurationException The configuration specified
     *  an invalid value.
     */
    public static final MagicComponent getIsolatedComponent(
            final MagicProperty property) throws InvalidConfigurationException {
        final MagicComponent component = getComponentInstanceFor(property);
        if (component instanceof NestedBeanContainer) {
            return new SwingIsolatedComponent();
        } else {
            return component;
        }
    }
}

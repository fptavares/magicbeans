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
package org.devyant.magicbeans.swing;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.generalizers.date.CalendarGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.DateGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.NoNeedForGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.TimestampGeneralizerImpl;
import org.devyant.magicbeans.swing.generalizers.list.ComboBoxGeneralizerImpl;
import org.devyant.magicbeans.swing.generalizers.list.ListGeneralizerImpl;

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
        
        return getBinderInstanceFor(property.getType(),
                property.getConfiguration(), true);
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
        // java.lang.String
        if (type.isAssignableFrom(String.class)) {
            return new SwingStringComponent();

            // java.util.Date
        } else if (type.isAssignableFrom(Date.class)) {
            return new SwingDateComponent(new NoNeedForGeneralizerImpl());

            // java.util.Calendar
        } else if (type.isAssignableFrom(Calendar.class)) {
            return new SwingDateComponent(new CalendarGeneralizerImpl());

            // java.sql.Timestamp
        } else if (type.isAssignableFrom(Timestamp.class)) {
            return new SwingDateComponent(new TimestampGeneralizerImpl());

            // java.sql.Date
        } else if (type.isAssignableFrom(java.sql.Date.class)) {
            return new SwingDateComponent(new DateGeneralizerImpl());

            // java.util.Collection
        } else if (type.isAssignableFrom(Collection.class)) {
            if (configuration.get(MagicConfiguration.GUI_COLLECTIONS_STYLE_KEY)
                    .equals(COLLECTION_STYLE_LIST)) {
                // JList
                return new SwingCollectionContainer(new ListGeneralizerImpl());
            } else if (configuration.get(MagicConfiguration.GUI_COLLECTIONS_STYLE_KEY)
                    .equals(COLLECTION_STYLE_COMBO)) {
                // JComboBox
                return new SwingCollectionContainer(new ComboBoxGeneralizerImpl());
            } else {
                // invalid style
                throw new InvalidConfigurationException(
                        MagicConfiguration.GUI_COLLECTIONS_STYLE_KEY,
                        configuration.get(MagicConfiguration.GUI_COLLECTIONS_STYLE_KEY));
            }
            
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
}

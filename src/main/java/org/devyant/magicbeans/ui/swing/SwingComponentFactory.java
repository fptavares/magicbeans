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
package org.devyant.magicbeans.ui.swing;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.conf.UnavailableConfigurationException;
import org.devyant.magicbeans.generalizers.date.CalendarGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.DateGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.NoNeedForGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.TimestampGeneralizerImpl;
import org.devyant.magicbeans.ui.AbstractUIFactory;
import org.devyant.magicbeans.ui.swing.generalizers.list.ComboBoxGeneralizerImpl;
import org.devyant.magicbeans.ui.swing.generalizers.list.ListGeneralizerImpl;

/**
 * Factory for Swing components. This is used to get the right component
 * for each property class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Abr/2005 2:38:36
 */
public class SwingComponentFactory extends AbstractUIFactory<JComponent> {

    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForString()
     */
    protected MagicComponent getComponentForString() {
        return new SwingStringComponent();
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForNumber()
     */
    protected MagicComponent getComponentForNumber() {
        return new SwingNumberComponent();
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForBoolean()
     */
    protected MagicComponent getComponentForBoolean() {
        return new SwingBooleanComponent();
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForDate()
     */
    protected MagicComponent getComponentForDate() {
        return new SwingDateComponent(new NoNeedForGeneralizerImpl());
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForCalendar()
     */
    protected MagicComponent getComponentForCalendar() {
        return new SwingDateComponent(new CalendarGeneralizerImpl());
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForTimestamp()
     */
    protected MagicComponent getComponentForTimestamp() {
        return new SwingDateComponent(new TimestampGeneralizerImpl());
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForSqlDate()
     */
    protected MagicComponent getComponentForSqlDate() {
        return new SwingDateComponent(new DateGeneralizerImpl());
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForCollection(java.lang.String)
     */
    protected MagicComponent getComponentForCollection(final String style) throws InvalidConfigurationException {
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
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForFile()
     */
    protected MagicComponent getComponentForFile() {
        return new SwingFileComponent();
    }

    /**
     * @see org.devyant.magicbeans.ui.UIFactory#createButton(java.lang.String)
     */
    public JComponent createButton(final String text) {
        return new JButton(text);
    }
    /**
     * @see org.devyant.magicbeans.ui.UIFactory#createStatus()
     */
    public JComponent createStatus() {
        return createLabel(" "); //$NON-NLS-1$
    }
    /**
     * @see org.devyant.magicbeans.ui.UIFactory#createLabel(java.lang.String)
     */
    public JComponent createLabel(final String string) {
        return new JLabel(string);
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#createContainer()
     */
    @Override
    protected JComponent createContainer() {
        return new JPanel();
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#createTabbedContainer()
     */
    @Override
    protected JComponent createTabbedContainer() {
        return new JTabbedPane();
    }
    
}

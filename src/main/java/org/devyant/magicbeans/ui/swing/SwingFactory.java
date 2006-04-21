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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicContainer;
import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.conf.InvalidConfigurationException;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.generalizers.date.CalendarGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.DateGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.NoNeedForGeneralizerImpl;
import org.devyant.magicbeans.generalizers.date.TimestampGeneralizerImpl;
import org.devyant.magicbeans.layout.LayoutIsolatedBehaviour;
import org.devyant.magicbeans.ui.AbstractUIFactory;
import org.devyant.magicbeans.ui.listeners.WindowListener;
import org.devyant.magicbeans.ui.swing.generalizers.list.ComboBoxGeneralizerImpl;
import org.devyant.magicbeans.ui.swing.generalizers.list.ListGeneralizerImpl;
import org.devyant.magicbeans.ui.swing.layout.isolated.SwingChildBehaviour;
import org.devyant.magicbeans.ui.swing.layout.isolated.SwingTabbedBehaviour;
import org.devyant.magicbeans.ui.swing.layout.isolated.SwingTreeBehaviour;
import org.devyant.magicbeans.utils.ActionWrapper;

/**
 * Factory for Swing components. This is used to get the right component
 * for each property class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Abr/2005 2:38:36
 */
public class SwingFactory extends AbstractUIFactory<JComponent> {

    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#AbstractUIFactory(String)
     */
    public SwingFactory() {
        super(MagicConfiguration.SWING_VALUE);
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForString()
     */
    @Override
    protected MagicComponent<? extends JComponent> getComponentForString() {
        return new SwingStringComponent();
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForNumber()
     */
    @Override
    protected MagicComponent<? extends JComponent> getComponentForNumber() {
        return new SwingNumberComponent();
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForBoolean()
     */
    @Override
    protected MagicComponent<? extends JComponent> getComponentForBoolean() {
        return new SwingBooleanComponent();
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForDate()
     */
    @Override
    protected MagicComponent<? extends JComponent> getComponentForDate() {
        return new SwingDateComponent(new NoNeedForGeneralizerImpl());
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForCalendar()
     */
    @Override
    protected MagicComponent<? extends JComponent> getComponentForCalendar() {
        return new SwingDateComponent(new CalendarGeneralizerImpl());
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForTimestamp()
     */
    @Override
    protected MagicComponent<? extends JComponent> getComponentForTimestamp() {
        return new SwingDateComponent(new TimestampGeneralizerImpl());
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForSqlDate()
     */
    @Override
    protected MagicComponent<? extends JComponent> getComponentForSqlDate() {
        return new SwingDateComponent(new DateGeneralizerImpl());
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getComponentForCollection(java.lang.String)
     */
    @Override
    protected MagicComponent<? extends JComponent> getComponentForCollection(final String style) throws InvalidConfigurationException {
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
    @Override
    protected MagicComponent<? extends JComponent> getComponentForFile() {
        return new SwingFileComponent();
    }

    /**
     * @see org.devyant.magicbeans.ui.UIFactory#createButton(java.lang.String, org.devyant.magicbeans.utils.ActionWrapper)
     */
    public JComponent createButton(final String text, final ActionWrapper action) {
        final JButton button = new JButton(text);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(
                    @SuppressWarnings("unused") java.awt.event.ActionEvent evt) {
                action.doAction();
            }
        });
        return button;
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
     * @see org.devyant.magicbeans.ui.UIFactory#createContainer()
     */
    public JComponent createContainer() {
        return new JPanel();
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#createChildBehaviour(org.devyant.magicbeans.MagicLayout)
     */
    @Override
    protected LayoutIsolatedBehaviour<JComponent>
            createChildBehaviour(MagicLayout<JComponent> layout) {
        return new SwingChildBehaviour(layout);
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#createTabbedBehaviour(org.devyant.magicbeans.MagicLayout)
     */
    @Override
    protected LayoutIsolatedBehaviour<JComponent>
            createTabbedBehaviour(MagicLayout<JComponent> layout) {
        return new SwingTabbedBehaviour(layout);
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#createTreeBehaviour(org.devyant.magicbeans.MagicLayout)
     */
    @Override
    protected LayoutIsolatedBehaviour<JComponent>
            createTreeBehaviour(MagicLayout<JComponent> layout) {
        return new SwingTreeBehaviour(layout);
    }
    /**
     * @see org.devyant.magicbeans.ui.AbstractUIFactory#getContainerForBean()
     */
    @Override
    protected MagicContainer<? extends JComponent> getContainerForBean() {
        return new SwingContainer();
    }
    /**
     * @see org.devyant.magicbeans.ui.UIFactory#createAndShowWindow(java.lang.Object, java.lang.String, java.lang.Object, org.devyant.magicbeans.ui.listeners.WindowListener)
     */
    public Object createAndShowWindow(JComponent parent, final String title,
            JComponent content, final WindowListener listener) {
        final JFrame frame = new JFrame(title);
        frame.add(content);
        frame.pack();
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
        if (listener != null) {
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(
                        @SuppressWarnings("unused") WindowEvent e) {
                    listener.closeWindowActionPerformed();
                }
            });
        }
        return frame;
    }
    
}

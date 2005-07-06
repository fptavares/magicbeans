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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;
import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.MagicView;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.layouts.GridBagMagicLayout;
import org.devyant.magicbeans.swing.listeners.UpdateButtonActionListener;
import org.devyant.magicbeans.utils.components.UnlabeledComponent;
import org.devyant.magicbeans.utils.containers.DefaultContainerBehaviour;

/**
 * SwingContainer is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @since 18/Abr/2005 20:01:28
 * @todo maybe remove status bar....
 * replace by a simple -> title + "*" -> only at nested containers...:(
 */
public class SwingContainer extends JPanel implements MagicView {
    /**
     * The status <code>JLabel</code>.
     */
    private JLabel status;
    /**
     * The okButton <code>JButton</code>.
     */
    private JButton okButton;
    
    /**
     * The <code>MagicLayout</code>.
     */
    protected final MagicLayout layout = new GridBagMagicLayout();
    
    /**
     * The property to bind to.
     */
    protected MagicProperty property;
    
    
    /**
     * Creates a new <code>SwingContainer</code> instance.
     */
    public SwingContainer() {
        super();
    }
    
    /**
     * Initializes the gui.
     */
    protected void init() {
        // gui initialization
        setLayout(layout);
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#update()
     */
    public void update() throws MagicException {
        // do common update
        DefaultContainerBehaviour.doUpdateForContainer(this);
    }
    
    /**
     * @see org.devyant.magicbeans.MagicComponent#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    public void bindTo(MagicProperty property) throws MagicException {
        this.property = property;

        init(); // init gui
        
        // fill with property's value
        final Object object = this.property.get();
        if (object == null) {
            return;
        }
        
        final Collection properties = MagicUtils.getProperties(object, this.property);
        
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
                component = SwingComponentFactory.getComponentInstanceFor(prop);
            } else {
                // isolated component
                component = SwingComponentFactory.getIsolatedComponent();
            }
            
            
            
            ((Component) component).setMinimumSize(new Dimension(
                            property.getConfiguration().getInt(
                                    MagicConfiguration
                                        .GUI_COMPONENT_MINIMUM_WIDTH_KEY),
                            property.getConfiguration().getInt(
                                    MagicConfiguration
                                        .GUI_COMPONENT_MINIMUM_HEIGHT_KEY))
                    );
            
            MagicUtils.info("Binding the generated component to the property.");
            component.bindTo(prop);
            
            addMagicComponent(component);
        }
    }

    /**
     * Show a message in the status bar.
     * @param name The message's name
     */
    protected void showMessage(String name) {
        status.setText(MagicConfiguration.resources.getMessage(name));
    }
    /**
     * Show an error message in the status bar.
     * @param string The message
     * @todo use icon for error
     */
    protected void showErrorMessage(String string) {
        status.setText("[ERROR] " + string);
    }

    /**
     * Add a magic component to the panel.
     * @param component The component to add
     */
    public final void addMagicComponent(final MagicComponent component) {
        // get the property's name
        final String string =
            MagicConfiguration.resources.getNameFor(component.getProperty());
        
        if (StringUtils.isBlank(string)
                        || component instanceof UnlabeledComponent) {
            addUnlabeledComponent(component, string);
        } else {
            addLabeledComponent(component, string);
        }
    }
    /**
     * @param component The component to add
     * @param string The property's name
     */
    private void addUnlabeledComponent(final MagicComponent component,
            final String string) {
        if (component instanceof UnlabeledComponent) {
            // set the components title
            ((UnlabeledComponent) component).setTitle(string);
        }
        // add magic component
        layout.addUnlabeledComponent(this, (Component) component);
    }
    /**
     * @param component The component to add
     * @param string The property's name
     */
    private void addLabeledComponent(final MagicComponent component,
            final String string) {
        // add label + magic component
        if (component instanceof SwingIsolatedComponent) {
            MagicUtils.debug("----------------" + component.getClass());
            layout.addLabeledIsolatedComponent(this, new JLabel(string),
                    (Component) component);
        } else {
            layout.addLabeledComponent(this, new JLabel(string),
                    (Component) component);
        }
    }

    /**
     * Add the OK button.
     */
    private final void addOKButton() {
        layout.addButton(this, okButton);
    }
    /**
     * Add the status bar.
     */
    private final void addStatusBar() {
        layout.addStatus(this, status);
    }
    
    /**
     * @see org.devyant.magicbeans.MagicView#render()
     */
    public final Container render() {
        status = new JLabel(" ");
        okButton = new JButton();
        okButton.setText(MagicConfiguration.resources.get(MagicResources.STRING_OKBUTTON));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    update();
                    fireUpdateButtonAction();
                    showMessage(MagicResources.MESSAGE_UPDATED);
                } catch (MagicException e) {
                    MagicUtils.debug(e);
                    showErrorMessage(e.toString());
                }
            }
        });
        
        // add final components
        addOKButton();
        addStatusBar();
        
        return this;
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#getProperty()
     */
    public final MagicProperty getProperty() {
        return property;
    }
    
    /**
     * @see org.devyant.magicbeans.swing.listeners.UpdateButtonActionHandler#addUpdateButtonActionListener(org.devyant.magicbeans.swing.listeners.UpdateButtonActionListener)
     */
    public void addUpdateButtonActionListener(final UpdateButtonActionListener l) {
        listenerList.add(UpdateButtonActionListener.class, l);
    }
    /**
     * @see org.devyant.magicbeans.swing.listeners.UpdateButtonActionHandler#fireUpdateButtonAction()
     */
    public void fireUpdateButtonAction() {
        final UpdateButtonActionListener [] listeners =
            (UpdateButtonActionListener[])
            this.listenerList.getListeners(UpdateButtonActionListener.class);
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].updateButtonActionPerformed();
        }
    }
}

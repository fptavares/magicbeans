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
    private final JLabel status = new JLabel(" ");
    /**
     * The okButton <code>JButton</code>.
     */
    private final JButton okButton = new JButton();
    
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
        
        okButton.setText(MagicConfiguration.resources.getString(MagicResources.STRING_OKBUTTON));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    update();
                    fireUpdateButtonAction();
                    showMessage(MagicResources.MESSAGE_UPDATED);
                } catch (Exception e) {
                    MagicUtils.debug(e);
                    showMessage(e.toString());
                }
            }
        });
        
        // object properties
        //@todo properties?...
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#update()
     */
    public void update() throws Exception {
        // do common update
        DefaultContainerBehaviour.doUpdateForContainer(this);
    }
    
    /**
     * @see org.devyant.magicbeans.MagicComponent#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    public void bindTo(MagicProperty property) throws Exception {
        init(); // init gui
        
        this.property = property;
        
        // fill with property's value
        final Object object = this.property.get();
        if (object == null) {
            return;
        }
        
        final Collection properties = MagicUtils.getProperties(object, this.property);
        
        for (Iterator i = properties.iterator(); i.hasNext(); ) {
            final MagicProperty prop = (MagicProperty) i.next();
            final MagicComponent component = SwingComponentFactory
                .getComponentInstanceFor(prop);
            
            ((Component) component).setMinimumSize(
                    new Dimension(
                            property.getConfiguration().getInt(
                            MagicConfiguration.GUI_COMPONENT_MINIMUM_WIDTH_KEY),
                            property.getConfiguration()
                                .getInt(MagicConfiguration.GUI_COMPONENT_MINIMUM_HEIGHT_KEY))
                    );
            
            component.bindTo(prop);
            
            addMagicComponent(component);
        }
    }

    /**
     * Show a message in the status bar.
     * @param name The message's name
     */
    private void showMessage(String name) {
        status.setText(MagicConfiguration.resources.getMessage(name));
    }

    /**
     * Add a magic component to the panel.
     * @param component The component to add
     */
    public final void addMagicComponent(final MagicComponent component) {
        // get the property's name
        final String string =
            MagicConfiguration.resources.getProperty(component.getProperty().getName());
        
        if ((StringUtils.isBlank(string)) || (component instanceof UnlabeledComponent)) {
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
        layout.addLabeledComponent(this, new JLabel(string),
                (Component) component);
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

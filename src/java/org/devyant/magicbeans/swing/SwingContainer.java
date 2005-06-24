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
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;
import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicContainer;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.MagicView;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.observer.BasicSubjectBehaviour;
import org.devyant.magicbeans.observer.Observer;
import org.devyant.magicbeans.swing.listeners.UpdateButtonActionListener;
import org.devyant.magicbeans.utils.components.UnlabeledComponent;
import org.devyant.magicbeans.utils.containers.MagicContainers;

/**
 * SwingContainer is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @since 18/Abr/2005 20:01:28
 * @todo maybe remove status bar....
 * replace by a simple -> title + "*" -> only at nested containers...:(
 */
public class SwingContainer extends JPanel
        implements MagicView {
    /**
     * The status <code>JLabel</code>.
     */
    private final JLabel status = new JLabel(" ");
    /**
     * The okButton <code>JButton</code>.
     */
    private final JButton okButton = new JButton();
    /**
     * The <code>GridBagConstraints</code>.
     */
    private GridBagConstraints gridBagConstraints;
    /**
     * The insets for the components.
     */
    protected final Insets insets = new Insets(5, 5, 5, 5);
    /**
     * The current line being filled at the <code>GridBagLayout</code>.
     */
    protected int line = 0;
    
    /**
     * The default message resources.
     * @todo return property if null
     */
    protected MagicResources resources;
    
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
        setLayout(new java.awt.GridBagLayout());
        
        okButton.setText(resources.getString(MagicResources.STRING_OKBUTTON));
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
        MagicContainers.doUpdateForContainer(this);
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
        
        final Collection properties = MagicUtils.getProperties(object);
        
        for (Iterator i = properties.iterator(); i.hasNext(); ) {
            final MagicProperty prop = (MagicProperty) i.next();
            final MagicComponent component = SwingComponentFactory
                .getComponentInstanceFor(prop);
            
            if (component instanceof MagicContainer) {
                ((MagicContainer) component).setResources(resources);
            }
            
            component.bindTo(prop);
            
            addMagicComponent(component);
        }
    }

    /**
     * Show a message in the status bar.
     * @param name The message's name
     */
    private void showMessage(String name) {
        status.setText(resources.getMessage(name));
    }

    /**
     * Add a magic component to the panel.
     * @param component The component to add
     */
    public final void addMagicComponent(final MagicComponent component) {
        // get the property's name
        final String string =
            resources.getProperty(component.getProperty().getName());
        
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
        addComponent((Component) component, 0, line++,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, 2, 1, insets);
    }
    /**
     * @param component The component to add
     * @param string The property's name
     */
    private void addLabeledComponent(final MagicComponent component,
            final String string) {
        // add label
        addComponent(new JLabel(string), 0, line);
        // add magic component
        addComponent((Component) component, 1, line++);
    }
    
    /**
     * Add a component to the panel.
     * @param component The component to add
     * @param x The x coordinate
     * @param y The y coordinate
     */
    private final void addComponent(final Component component, int x, int y) {
        addComponent(component, x, y,
                GridBagConstraints.WEST, GridBagConstraints.BOTH);
    }
    
    /**
     * @param component The component to add
     * @param x The x coordinate
     * @param y The y coordinate
     * @param anchor The <code>GridBagConstraints</code>'s anchor to use
     */
    private final void addComponent(final Component component,
            int x, int y, int anchor, int fill) {
        addComponent(component, x, y, anchor, fill, 1, 1, insets);
    }
    
    /**
     * @param component The component to add
     * @param x The x coordinate
     * @param y The y coordinate
     * @param anchor The <code>GridBagConstraints</code>'s anchor to use
     */
    protected final void addComponent(final Component component, int x, int y,
            int anchor, int fill, int gridwidth, int gridheight, Insets insets) {
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = anchor;
        gridBagConstraints.fill = fill;
        gridBagConstraints.gridwidth = gridwidth;
        gridBagConstraints.gridheight = gridheight;
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        gridBagConstraints.insets = insets;
        add(component, gridBagConstraints);
    }

    /**
     * Add the OK button.
     */
    private final void addOKButton() {
        addComponent(okButton, 1, line++,
                GridBagConstraints.EAST, GridBagConstraints.NONE);
    }
    /**
     * Add the status bar.
     */
    private final void addStatusBar() {
        addComponent(status, 0, line++, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, GridBagConstraints.REMAINDER, 1,
                new Insets(0, 5, 0, 5));
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
     * @see org.devyant.magicbeans.MagicContainer#setResources(org.devyant.magicbeans.i18n.MagicResources)
     */
    public void setResources(MagicResources resources) {
        this.resources = resources;
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

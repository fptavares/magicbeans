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

import java.util.Collection;
import java.util.LinkedList;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.ui.listeners.UpdateButtonActionListener;

/**
 * AbstractBaseContainer is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @param <C> The container to use for the binding
 * @see AbstractMagicContainer
 * @since Oct 28, 2005 7:13:05 PM
 */
public abstract class AbstractBaseContainer<C>
        extends AbstractMagicContainer<C> {
    
    /**
     * The standalone <code>boolean</code>.
     */
    private boolean standalone = false;
    
    /**
     * States whether this container has isolated components.
     */
    private boolean isolatedComponentContainer = false;
    
    /**
     * The status label component.
     */
    protected Object status;
    
    /**
     * A <code>Collection</code> of this container's child components.
     */
    private Collection<MagicComponent<?>> components;
    
    /**
     * Creates a new <code>AbstractBaseContainer</code> instance.
     * @see AbstractMagicContainer#AbstractMagicContainer(UIFactory)
     */
    public AbstractBaseContainer(final UIFactory factory) {
        super(factory);
    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#createComponent()
     */
    @Override
    protected final C createComponent() throws MagicException {
        return (C) this.getFactory().createContainerFor(this.getProperty(),
                this.isIsolatedComponentContainer());
    }
    
    /**
     * Mass layer update.
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#update()
     */
    @Override
    public final void update() throws MagicException {
        for (MagicComponent<?> child : this.components) {
            // do layer-by-layer update
            child.update();
            
            // debug
            MagicUtils.debug(child.getProperty().getName()
                    + " : " + child.getProperty().get()); //$NON-NLS-1$
        }
    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    @Override
    public final void bindTo(final MagicProperty property) throws MagicException {
        super.bindTo(property);
        
        // fill with property's value
        final Object value = getProperty().get();
        if (value == null) {
            return;
        }
        
        this.components = new LinkedList<MagicComponent<?>>();
        
        final Collection<MagicProperty> properties =
            MagicUtils.getProperties(value, getProperty());
        
        for (MagicProperty prop : properties) {
            final MagicComponent<?> component;
            
            // verify if is visible
            if (!prop.getConfiguration()
                    .getSpecialBoolean(MagicConfiguration.XML_VISIBLE)) {
                continue; // not visible, so continue to the next property
            }
            
            // verify if is nested
            final boolean nested = prop.getConfiguration()
                .getSpecialBoolean(MagicConfiguration.XML_NESTED);
            if (nested) {
                // basic nested component
                component = getFactory().getNestedComponentInstanceFor(prop);
            } else {
                // isolated component
                if (!this.isolatedComponentContainer) {
                    this.isolatedComponentContainer = true;
                }
                component = getFactory().getComponentForIsolated(prop);
            }
            
            MagicUtils.debug("Binding the generated component to the property."); //$NON-NLS-1$
            component.bindTo(prop);
            
            this.components.add(component);
        }
    }
    
    /**
     * Add the actual components to the actual container.
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#initializeComponent()
     */
    @Override
    protected void initializeComponent() throws MagicException {
        super.initializeComponent();
        
        for (MagicComponent<?> mComponent : this.components) {
            addMagicComponent(mComponent);
        }
    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#finalizeComponent()
     */
    @Override
    protected void finalizeComponent() {
        if (!isStandalone()) {
            return;
        }

        // initialize status
        this.status = getFactory().createStatus();
        // initialize button
        final Object button = getFactory().createButton(MagicConfiguration
                .resources.get(MagicResources.STRING_OKBUTTON));
        bindButtonToAction(button, updateAction);
        // add components
        layout.addButton(this.component, button);
        layout.addStatus(this.component, this.status);
        
        if (this.isNested()) { // TODO: is this only needed on standalone?
            finalizeNestedComponent();
        }
    }
    
    /**
     * @param mComponent
     * @throws MagicException
     */
    private final void addMagicComponent(MagicComponent<?> mComponent)
            throws MagicException {
        if (mComponent.isLabeled()) {
            final Object label = getFactory().createLabel(mComponent.getName());
            // add label + magic component
            if (isNested()) {
                this.layout.addLabeledComponent(
                        this.component, label, mComponent);
            } else {
                this.layout.addLabeledIsolatedComponent(
                        this.component, label, mComponent);
            }
        } else {
            // add magic component
            this.layout.addUnlabeledComponent(this.component, mComponent);
        }
    }

    /**
     * Initializes the handler for the MagicContainerAction.
     * @param button The button
     * @param action The action to be exectuted
     * @todo implementation
     */
    protected abstract void bindButtonToAction(Object button, ActionWrapper action);

    /**
     * @see org.devyant.magicbeans.MagicContainer#setStandalone(boolean)
     */
    public void setStandalone(final boolean standalone) {
        this.standalone = standalone;
    }

    /**
     * The getter method for the standalone property.
     * @return The property's <code>boolean</code> value
     */
    protected boolean isStandalone() {
        return this.standalone;
    }

    /**
     * @see org.devyant.magicbeans.ui.listeners.UpdateButtonActionHandler#addUpdateButtonActionListener(org.devyant.magicbeans.ui.listeners.UpdateButtonActionListener)
     * @todo
     */
    public void addUpdateButtonActionListener(final UpdateButtonActionListener l) {
        //listenerList.add(UpdateButtonActionListener.class, l);
    }

    /**
     * @see org.devyant.magicbeans.ui.listeners.UpdateButtonActionHandler#fireUpdateButtonAction()
     */
    public void fireUpdateButtonAction() {
        // @todo Auto-generated method stub

    }
    
    /**
     * The getter method for the isolatedComponentContainer property.
     * @return The property's <code>AbstractBaseContainer</code> value
     */
    public final boolean isIsolatedComponentContainer() {
        return this.isolatedComponentContainer;
    }

    /**
     * The setter method for the isolatedComponentContainer property.
     * @param isolatedComponentContainer The <code>boolean</code> to set
     */
    public final void setIsolatedComponentContainer(
            final boolean isolatedComponentContainer) {
        this.isolatedComponentContainer = isolatedComponentContainer;
    }

    /**
     * @todo this doesn't seem very correct...
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#setValue(java.lang.Object)
     */
    @Override
    protected final void setValue(@SuppressWarnings("unused") Object value) {
        // do nothing
    }
    
    /**
     * @todo this doesn't seem very correct...
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#getValue()
     * @return <code>null</code>
     */
    @Override
    protected final Object getValue() {
        return null;
    }
    
    /**
     * Show a message in the status bar.
     * @param name The message's name
     */
    protected abstract void showMessage(final String name);
    
    /**
     * Show an error message in the status bar.
     * @param string The message
     * @todo use icon for error
     */
    public void showErrorMessage(final String string) {
        showMessage("[ERROR] " + string); //$NON-NLS-1$
    }
    
    /**
     * Wraps an action.
     */
    public static abstract class ActionWrapper {
        /**
         * Executes the wrapped action.
         */
        public abstract void doAction();
    }
    
    /**
     * The updateAction <code>ActionWrapper</code>.
     */
    private final ActionWrapper updateAction = new ActionWrapper() {
        /**
         * @see org.devyant.magicbeans.ui.AbstractBaseContainer.ActionWrapper#doAction()
         */
        @Override
        public void doAction() {
            try {
                update();
                fireUpdateButtonAction();
                showMessage(MagicResources.MESSAGE_UPDATED);
            } catch (MagicException e) {
                MagicUtils.debug(e);
                showErrorMessage(e.toString());
            }
        }
    };
    
}

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
package org.devyant.magicbeans.layouts.decorators;


import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.ui.isolated.TabbedContainer;

/**
 * TabbedLayoutDecorator is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Aug 23, 2005 12:07:24 AM
 */
public class TabbedLayoutDecorator implements MagicLayout {
    /**
     * The layout <code>MagicLayout</code>.
     */
    private final MagicLayout layout;
     
     /**
     * Creates a new <code>TabbedLayoutDecorator</code> instance.
     * @param layout The layout to be decorated
     */
    public TabbedLayoutDecorator(final MagicLayout layout) {
        this.layout = layout;
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addLabeledComponent(Object, Object, MagicComponent)
     */
    public void addLabeledComponent(final Object container, final Object label,
            final MagicComponent<?> component) throws MagicException {
        this.layout.addLabeledComponent(container, label, component);
    }

    /**
     * The method who's behaviour is altered.
     * @see org.devyant.magicbeans.MagicLayout#addLabeledIsolatedComponent(Object, Object, MagicComponent)
     */
    public void addLabeledIsolatedComponent(final Object container,
            final Object label, final MagicComponent<?> component)
            throws MagicException {
        /*
         * TODO: either add TabbedInterface and call getMainPanel()
         * or override addComponent on TabbedContainerImpl to
         * check the type of component and add it to the right place
         */
        if (MagicUtils.mayBeIsolated(component)) {
            this.layout.addLabeledComponent(
                    ((TabbedContainer) container).getMainPanel(),
                    label, component);
        } else {
            ((TabbedContainer) container).addSecondaryPanel(component.getName(),
                    component.render());
        }
        /*
         * TODO: nested/isolated problem. apply on component or container?
         * child  -> component
         * tabbed -> container
         * tree   -> ?
         * it must be the same for all
         * container works for all, but component is the right option
         * 
         * -----> actually, it's applied to a component which, in fact,
         *       is a container. this is the same as with the child behaviour :)
         */
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addUnlabeledComponent(Object, MagicComponent)
     */
    public void addUnlabeledComponent(final Object container,
            final MagicComponent<?> component) throws MagicException {
        this.layout.addUnlabeledComponent(container, component);
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addControledComponent(Object, MagicComponent, Object[], boolean)
     */
    public void addControledComponent(final Object container,
            final Object component, final Object[] controllers,
            final boolean expand) throws MagicException {
        this.layout.addControledComponent(
                container, component, controllers, expand);
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addButton(Object, Object)
     */
    public void addButton(final Object container, final Object button) {
        this.layout.addButton(container, button);
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addStatus(Object, Object)
     */
    public void addStatus(final Object container, final Object status) {
        this.layout.addStatus(container, status);
    }

}

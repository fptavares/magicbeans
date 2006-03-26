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
package org.devyant.magicbeans.layout;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.exceptions.MagicException;

/**
 * AbstractMagicLayout is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Mar 23, 2006 3:07:49 PM
 */
public abstract class AbstractMagicLayout implements MagicLayout {
    /**
     * The behaviour type.
     */
    public enum IsolatedBehaviourType { CHILD, TABBED, TREE; }
    
    /**
     * The behaviour to use for the handling of isolated components.
     */
    private final LayoutIsolatedBehaviour ibehaviour;

    /**
     * Creates a new <code>AbstractMagicLayout</code> instance.
     * @param type The behaviour type
     */
    public AbstractMagicLayout(final IsolatedBehaviourType type) {
        switch(type) {
            case CHILD:
            default:
                this.ibehaviour = createChildBehaviour();
                break;
            case TABBED:
                this.ibehaviour = createTabbedBehaviour();
                break;
            case TREE:
                this.ibehaviour = createTreeBehaviour();
                break;
        }
    }

    /**
     * Create and return the child behaviour.
     * @return The behaviour instance
     * @throws UnsupportedOperationException Toolkit doesn't support this
     */
    protected abstract LayoutIsolatedBehaviour
            createChildBehaviour() throws UnsupportedOperationException;
    /**
     * Create and return the tabbed behaviour.
     * @return The behaviour instance
     * @throws UnsupportedOperationException Toolkit doesn't support this
     */
    protected abstract LayoutIsolatedBehaviour
            createTabbedBehaviour() throws UnsupportedOperationException;
    /**
     * Create and return the tree behaviour.
     * @return The behaviour instance
     * @throws UnsupportedOperationException Toolkit doesn't support this
     */
    protected abstract LayoutIsolatedBehaviour
            createTreeBehaviour() throws UnsupportedOperationException;

    /**
     * @see org.devyant.magicbeans.MagicLayout#addLabeledIsolatedComponent(java.lang.Object, java.lang.Object, org.devyant.magicbeans.MagicComponent)
     */
    public final void addLabeledIsolatedComponent(Object container, Object label,
            MagicComponent<?> component) throws MagicException {
        if (MagicUtils.mayBeIsolated(component)) {
            addLabeledComponent(container, label, component);
        } else {
            this.ibehaviour.addLabeledIsolatedComponent(container, label,
                    component);
        }
    }
    
}

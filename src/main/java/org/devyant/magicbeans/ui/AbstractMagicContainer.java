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

import org.devyant.magicbeans.MagicContainer;
import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.ui.AbstractBaseContainer.ActionWrapper;
import org.devyant.magicbeans.ui.listeners.UpdateButtonActionListener;

/**
 * AbstractMagicContainer is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @param <C> The container to use for the binding
 * @since Oct 6, 2005 12:29:43 PM
 */
public abstract class AbstractMagicContainer<C>
        extends AbstractMagicComponent<C> implements MagicContainer<C> {
    
    /**
     * The toolkit specific component factory.
     */
    private final UIFactory<?> factory;
    
    /**
     * The <code>MagicLayout</code>.
     */
    protected MagicLayout layout;
    
    /**
     * Creates a new <b>labeled</b> <code>AbstractMagicContainer</code> instance.
     * @see #AbstractMagicContainer(UIFactory, boolean)
     */
    public AbstractMagicContainer(final UIFactory<?> factory) {
        this(factory, true);
    }

    /**
     * Creates a new <code>AbstractMagicContainer</code> instance.
     * @param factory The apropriate factory, considering which
     * toolkit you're using.
     * <p>This factory may easilly be fetched using, for example,
     * <code>MagicFactory.swing()</code> (for the Swing toolkit).</p>
     * @param labeled Whether this is a labeled container
     */
    public AbstractMagicContainer(final UIFactory<?> factory,
            final boolean labeled) {
        super(labeled);
        this.factory = factory;
        // TODO: wtf?
        // nested -> !standalone
        //this.setStandalone(!this.nested);
    }

    /**
     * To be optionally overriden.
     */
    protected void finalizeNestedComponent() {
        // to be optionally overriden
    }

    /**
     * @see org.devyant.magicbeans.MagicContainer#setMagicLayout(org.devyant.magicbeans.MagicLayout)
     */
    public void setMagicLayout(final MagicLayout layout) {
        this.layout = layout;
    }

    /**
     * The getter method for the factory property.
     * @return The property's <code>AbstractBaseContainer</code> value
     */
    public final UIFactory<?> getFactory() {
        return this.factory;
    }

}

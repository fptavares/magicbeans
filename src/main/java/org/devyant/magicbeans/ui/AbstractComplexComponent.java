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

import org.devyant.magicbeans.MagicComplexComponent;
import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.ui.listeners.WindowListener;

/**
 * AbstractComplexComponent is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @param <C> The most basic type of this toolkit's components
 * @since Oct 6, 2005 12:29:43 PM
 */
public abstract class AbstractComplexComponent<C>
        extends AbstractMagicComponent<C> implements MagicComplexComponent<C> {
    
    /**
     * The toolkit specific component factory.
     */
    private UIFactory<C> factory;
    
    /**
     * The <code>MagicLayout</code>.
     */
    protected MagicLayout<C> layout;
    
    /**
     * Creates a new <b>labeled</b> <code>AbstractComplexComponent</code> instance.
     * @see #AbstractComplexComponent(boolean)
     */
    public AbstractComplexComponent() {
        this(true);
    }

    /**
     * Creates a new <code>AbstractComplexComponent</code> instance.
     * @param labeled Whether this is a labeled container
     */
    public AbstractComplexComponent(final boolean labeled) {
        super(labeled);
    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#createComponent()
     */
    @Override
    protected final C createComponent() {
        return this.getFactory().createContainer();
    }

    /**
     * @see org.devyant.magicbeans.MagicComplexComponent#setMagicLayout(org.devyant.magicbeans.MagicLayout)
     */
    public void setMagicLayout(final MagicLayout<C> layout) {
        this.layout = layout;
    }
    
    /**
     * @see org.devyant.magicbeans.MagicComplexComponent#setUIFactory(org.devyant.magicbeans.ui.UIFactory)
     */
    public void setUIFactory(UIFactory<C> factory) {
        this.factory = factory;
    }
    
    /**
     * @see org.devyant.magicbeans.MagicComplexComponent#createAndShowWindow(java.lang.Object, org.devyant.magicbeans.ui.listeners.WindowListener)
     */
    public Object createAndShowWindow(C parent, final WindowListener listener)
            throws MagicException {
        return getFactory().createAndShowWindow(
                parent, getName(), render(), listener);
    }

    /**
     * The getter method for the factory property.
     * @return The property's <code>AbstractMagicContainer</code> value
     */
    public final UIFactory<C> getFactory() {
        return this.factory;
    }

}

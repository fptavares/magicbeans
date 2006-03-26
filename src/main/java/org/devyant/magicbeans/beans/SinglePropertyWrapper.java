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
package org.devyant.magicbeans.beans;

/**
 * SinglePropertyWrapper is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 18/Jun/2005 3:39:51
 */
public class SinglePropertyWrapper implements AuxiliarBean {
    /**
     * The property <code>Object</code>.
     */
    private Object property;
    
    
    /**
     * Creates a new <code>SinglePropertyWrapper</code> instance.
     * @param property
     */
    public SinglePropertyWrapper(Object property) {
        this.property = property;
    }

    /**
     * The getter method for the wrapped property.
     * @return The property
     */
    public final Object getProperty() {
        return this.property;
    }

    /**
     * The getter method for the wrapped property.
     * @param property The property
     */
    public final void setProperty(Object property) {
        this.property = property;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return "* " + String.valueOf(this.property);
    }
}

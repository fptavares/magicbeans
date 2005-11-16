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
package org.devyant.magicbeans.generalizers;

import java.util.Date;

/**
 * DateGeneralizer is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 12/Jun/2005 4:42:08
 */
public abstract class DateGeneralizer {
    /**
     * @param date An instance which represents some form of date
     * @return The equivelent <code>java.util.Date</code> value
     */
    protected abstract Date getTheValue(Object date);
    /**
     * @param date An instance which represents some form of date
     * @param value The date's value in the form of a
     * <code>java.util.Date</code> instance
     */
    protected abstract void setTheValue(Object date, Date value);
    /**
     * @return An instance which represents some form of date
     */
    protected abstract Object newInstance();
    
    /**
     * @see #getTheValue(Object)
     */
    public Date getValue(Object date) {
        return getTheValue(date);
    }
    /**
     * @see #setTheValue(Object, Date)
     */
    public void setValue(Object date, Date value) {
        if (date == null) {
            date = newInstance();
        }
        setTheValue(date, value);
    }
}

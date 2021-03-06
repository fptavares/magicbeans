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
package org.devyant.magicbeans.generalizers.date;

import java.util.Calendar;
import java.util.Date;

import org.devyant.magicbeans.generalizers.DateGeneralizer;

/**
 * CalendarGeneralizerImpl is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 12/Jun/2005 4:48:29
 */
public class CalendarGeneralizerImpl extends DateGeneralizer {
    /**
     * @see org.devyant.magicbeans.generalizers.DateGeneralizer#getTheValue(java.lang.Object)
     */
    protected final Date getTheValue(Object date) {
        return ((Calendar) date).getTime();
    }
    /**
     * @see org.devyant.magicbeans.generalizers.DateGeneralizer#setTheValue(java.lang.Object, java.util.Date)
     */
    protected final void setTheValue(Object date, Date value) {
        ((Calendar) date).setTime(value);
    }
    /**
     * @see org.devyant.magicbeans.generalizers.DateGeneralizer#newInstance()
     */
    protected Object newInstance() {
        return Calendar.getInstance();
    }
}

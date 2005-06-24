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

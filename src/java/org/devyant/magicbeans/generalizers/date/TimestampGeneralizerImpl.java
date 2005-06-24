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
package org.devyant.magicbeans.generalizers.date;

import java.sql.Timestamp;
import java.util.Date;

import org.devyant.magicbeans.generalizers.DateGeneralizer;

/**
 * TimestampGeneralizerImpl is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 12/Jun/2005 4:50:44
 */
public class TimestampGeneralizerImpl extends DateGeneralizer {
    /**
     * @see org.devyant.magicbeans.generalizers.DateGeneralizer#getTheValue(java.lang.Object)
     */
    protected Date getTheValue(Object date) {
        return new Date(((Timestamp) date).getTime());
    }
    /**
     * @see org.devyant.magicbeans.generalizers.DateGeneralizer#setTheValue(java.lang.Object, java.util.Date)
     */
    protected void setTheValue(Object date, Date value) {
        ((Timestamp) date).setTime(value.getTime());
    }
    /**
     * @see org.devyant.magicbeans.generalizers.DateGeneralizer#newInstance()
     */
    protected Object newInstance() {
        return new Timestamp(0);
    }
}

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
package org.devyant.magicbeans.utils.beans;

/**
 * SinglePropertyWrapper is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 18/Jun/2005 3:39:51
 */
public class SinglePropertyWrapper {
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
    public final Object get() {
        return property;
    }

    /**
     * The getter method for the wrapped property.
     * @param property The property
     */
    public final void set(Object property) {
        this.property = property;
    }
    
    public final String toString() {
        return "* " + String.valueOf(property);
    }
}

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
package org.devyant.magicbeans.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.devyant.magicbeans.MagicUtils;

/**
 * MagicProperty is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Abr/2005 0:00:04
 */
public class MagicProperty {
    /**
     * The getter <code>Method</code>.
     */
    private final Method getter;
    /**
     * The setter <code>Method</code>.
     */
    private final Method setter;
    /**
     * The object that contains the property.
     */
    private final Object object;
    /**
     * The property's name.
     */
    private final String name;
    
    /**
     * Creates a new <code>MagicProperty</code> instance.
     * @param object The object that contains the property
     * @param property The proeprty
     * @throws NoSuchMethodException
     * {@link MagicUtils#getGetterMethod(Class, String)}
     * {@link MagicUtils#getSetterMethod(Class, String)}
     */
    public MagicProperty(final Object object, final String property)
            throws NoSuchMethodException {
        super();
        this.getter = MagicUtils.getGetterMethod(object.getClass(), property);
        this.setter = MagicUtils.getSetterMethod(object.getClass(), property);
        this.object = object;
        this.name = MagicUtils.decapitalize(property);
    }
    
    /**
     * Creates a new <code>MagicProperty</code> instance.
     * @param object The object that contains the property
     * @param getter The getter method for the property
     * @param setter The setter method for the property
     */
    public MagicProperty(final Object object, final Method getter,
            final Method setter) {
        super();
        this.object = object;
        this.getter = getter;
        this.setter = setter;
        this.name = MagicUtils.decapitalize(setter.getName().substring(3));
    }

    /**
     * Creates a new <code>MagicProperty</code> instance.
     * @param object The object that contains the property
     * @param property The property
     * @param allowNullMethods Getter and setter methods may be unavailable
     * @throws NoSuchMethodException
     * {@link MagicUtils#getGetterMethod(Class, String)}
     * {@link MagicUtils#getSetterMethod(Class, String)}
     */
    public MagicProperty(Object object, String property,
            boolean allowNullMethods) throws NoSuchMethodException {
        super();
        
        if (allowNullMethods) {
            Method getter = null;
            try {
                getter = MagicUtils.getGetterMethod(object.getClass(), property);
            } catch (NoSuchMethodException e) {
            } finally {
                this.getter = getter;
            }
            Method setter = null;
            try {
                setter = MagicUtils.getSetterMethod(object.getClass(), property);
            } catch (NoSuchMethodException e) {
            } finally {
                this.setter = setter;
            }
        } else {
            this.getter = MagicUtils.getGetterMethod(object.getClass(), property);
            this.setter = MagicUtils.getSetterMethod(object.getClass(), property);
        }
        
        this.object = object;
        this.name = MagicUtils.decapitalize(property);
    }

    /**
     * @return The property's value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public final Object get() throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        return getter.invoke(object, new Object[0]);
    }
    
    /**
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public final void set(final Object value) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        if (setter != null) { // Objects need not to be set, they may be final
            setter.invoke(object, new Object[] {value});
        }
    }
    
    /**
     * @return The property's class
     */
    public final Class getType() {
        return getter.getReturnType();
    }
    
    /**
     * @return Returns the name of the property.
     */
    public final String getName() {
        return name;
    }
}

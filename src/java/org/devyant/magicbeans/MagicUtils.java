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
package org.devyant.magicbeans;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

import org.devyant.magicbeans.beans.MagicProperty;

/**
 * MagicUtils is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @since 18/Abr/2005 23:45:28
 */
public abstract class MagicUtils {
    /**
     * This is a utility class, therefor it should not be instatiated.
     */
    protected MagicUtils() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * The getter method for certain property from a certain
     * class. The method can be an is or get method.
     *
     * @param theClass The property's class
     * @param property The property
     * @return The getter method
     * @throws NoSuchMethodException Thrown if method does not exist
     */
    public static final Method getGetterMethod(
            final Class theClass, final String property)
        throws NoSuchMethodException {
        Method decorateMethod;
        final String propertyName = capitalize(property);
        try {
            decorateMethod = theClass
                .getMethod("is" + propertyName, new Class[]{});
        } catch (Exception ex) {
            // no is method, so look for getter
            decorateMethod = theClass
                .getMethod("get" + propertyName, new Class[]{});
        }
        return decorateMethod;
    }
    
    /**
     * The setter method for certain property from a certain
     * class.
     *
     * @param theClass The property's class
     * @param property The property
     * @return The getter method
     * @throws NoSuchMethodException Thrown if method does not exist
     */
    public static final Method getSetterMethod(
            final Class theClass, final String property)
        throws NoSuchMethodException {
        Method decorateMethod;
        final String propertyName = capitalize(property);
        decorateMethod = theClass
            .getMethod("set" + propertyName, new Class[]{});
        return decorateMethod;
    }
    
    /**
     * Capitalize first word of String.
     * @param string The String
     * @return Capitalized String
     */
    public static final String capitalize(final String string) {
        int len;

        if ((string == null) || (len = string.length()) == 0) {
            return string;
        }

        final StringBuffer buf = new StringBuffer(len);
        buf.append(Character.toTitleCase(string.charAt(0)));
        buf.append(string.substring(1));

        return buf.toString();
    }
    /**
     * Decapitalize first word of String.
     * @param string The String
     * @return Decapitalized String
     */
    public static final String decapitalize(final String string) {
        int len;

        if ((string == null) || (len = string.length()) == 0) {
            return string;
        }

        final StringBuffer buf = new StringBuffer(len);
        buf.append(Character.toLowerCase(string.charAt(0)));
        buf.append(string.substring(1));

        return buf.toString();
    }

    /**
     * @param object The bean that contains the properties
     * @param parent The parent property
     * @return The class's accessible properties
     */
    public static final Collection getProperties(final Object object,
            final MagicProperty parent) {
        final Method [] methods = object.getClass().getMethods();
        
        final Hashtable getters = new Hashtable();
        final Hashtable setters = new Hashtable();
        
        String name = "";
        for (int i = 0; i < methods.length; i++) {
            name = methods[i].getName();
            System.out.println(name);
            if (name.startsWith("is")) {
                getters.put(name.substring(2), methods[i]);
            } else if (name.startsWith("get")) {
                getters.put(name.substring(3), methods[i]);
            } else if (name.startsWith("set")) {
                setters.put(name.substring(3), methods[i]);
            }
        }
        
        // Collection to return (size will never be greater than getters.size())
        final Collection properties = new ArrayList(getters.size());
        
        Object key;
        for (Enumeration i = getters.keys(); i.hasMoreElements(); ) {
            key = i.nextElement();
            // if exists both setter and getter methods...
            if (setters.containsKey(key)) {
                // create a Collection of MagicProperties
                properties.add(new MagicProperty(parent, object,
                        (Method) getters.get(key), (Method) setters.get(key)));
            }
        }
        
        return properties;
    }
    
    private static final boolean DEBUG = true;
    /**
     * @param string The debug text
     */
    public static final void debug(Object string) {
        if (DEBUG) {
            System.out.println(string);
        }
    }
    /**
     * @param e The exception for debugging
     */
    public static final void debug(Throwable e) {
        if (DEBUG) {
            e.printStackTrace();
        }
    }
    /**
     * This exception should be handled.
     * @param e The exception
     * @todo handle?
     */
    public static final void error(Throwable e) {
        if (DEBUG) {
            e.printStackTrace();
        }
    }
}

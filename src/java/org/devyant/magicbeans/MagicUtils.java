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
package org.devyant.magicbeans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.utils.BeanPathToXPathConverter;
import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Node;

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
    
    public static final boolean cannotStandalone(
            final MagicComponent component) {
        return (component == null
                || (!(component instanceof MagicContainer)));
    }
    
    /**
     * The getter method for certain property from a certain
     * class. The method can be an is or get method.
     *
     * @param theClass The bean's class
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
        } catch (NoSuchMethodException ex) {
            // no is method, so look for getter
            decorateMethod = theClass
                .getMethod("get" + propertyName, new Class[]{});
        }
        return decorateMethod;
    }
    
    /**
     * The setter method for certain property from a certain class.
     * 
     * @param theClass The bean's class
     * @param property The property
     * @param propertyClass The property's class
     * @return The getter method
     * @throws NoSuchMethodException Thrown if method does not exist
     */
    public static final Method getSetterMethod(
            final Class theClass, final String property, Class propertyClass)
        throws NoSuchMethodException {
        Method decorateMethod;
        final String propertyName = capitalize(property);
        decorateMethod = theClass
            .getMethod("set" + propertyName, new Class[]{propertyClass});
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
    
    public static final String XML_VALUE = "xml";
    public static final String TYPE_VALUE = "type";
    public static final String PROPERTY_VALUE = "property";

    /**
     * @param object The bean
     * @param parent The parent property
     * @return A collection of <code>MagicProperty</code>'s
     * @throws MagicException XPath error
     */
    public static final Collection getProperties(
            final Object object, final MagicProperty parent)
            throws MagicException {
        final String order =
            parent.getConfiguration().get(MagicConfiguration.GUI_ORDER_KEY);
        
        if (XML_VALUE.equals(order)) {
            try {
                return getPropertiesFromXML(object, parent);
            } catch (JaxenException e) {
                throw new MagicException("XPath error.", e);
            }
        } else if (TYPE_VALUE.equals(order)) {
            final List properties = getAllProperties(object, parent);
            // sort the properties by their type
            Collections.sort(properties, new Comparator() {
                public int compare(final Object o1, final Object o2) {
                    final Class class1;
                    final Class class2;
                    try {
                        class1 = ((MagicProperty) o1).getType();
                        class2 = ((MagicProperty) o2).getType();
                    } catch (MagicException e) {
                        return 0;
                    }
                    return class1.getName().compareTo(class2.getName());
                }
            });
            // return the sorted collection
            return properties;
        } else if (PROPERTY_VALUE.equals(order)) {
            // they already come ordered by name
            return getAllProperties(object, parent);
        } else {
            // try instatiate a comparator with the configuration value
            final Comparator comparator = (Comparator) parent.getConfiguration()
                    .getClassInstance(MagicConfiguration.GUI_ORDER_KEY);
            // get all the declared properties
            final List properties = getAllProperties(object, parent);
            // sort the properties by their type
            Collections.sort(properties, comparator);
            // return the sorted collection
            return properties;
        }
    }

    /**
     * Helper method.
     */
    private static List getAllProperties(final Object object,
            final MagicProperty parent) {
        if (object instanceof Map) {
            return getMappedProperties(object, parent);
        } else {
            return getDeclaredProperties(object, parent);
        }
    }

    /**
     * Helper method.
     * @throws JaxenException XPath error
     */
    private static Collection getPropertiesFromXML(final Object object,
            final MagicProperty parent) throws JaxenException {
        // convert BeanPath to XPath
        final String xpath = BeanPathToXPathConverter.convert(
                parent.getSuperBeanClassName(), parent.getBeanPath()) + "/*";
        // get the properties
        final Collection names =
            new DOMXPath(xpath).selectNodes(MagicConfiguration.getXML_DOC());
        
        // Collection to return
        final Collection properties = new ArrayList(names.size());
        
        debug("names: " + names);
        
        for (Iterator i = names.iterator(); i.hasNext(); ) {
            final String name = ((Node) i.next()).getNodeName();
            if (MagicConfiguration.CONFIGURATION_NODE.equals(name)) {
                continue; // ignore the configuration node
            }
            properties.add(new MagicProperty(parent, object, name));
        }
        
        return properties;
    }

    /**
     * Helper method.
     */
    private static final List getMappedProperties(final Object object,
            final MagicProperty parent) {
        final Map map = (Map) object;
        
        // Collection to return
        final List properties = new ArrayList(map.size());
        
        for (Iterator i = map.keySet().iterator(); i.hasNext(); ) {
            properties.add(new MagicProperty(parent, object, (String) i.next()));
        }
        
        return properties;
    }

    /**
     * Helper method.
     */
    private static final List getDeclaredProperties(final Object object,
            final MagicProperty parent) {
        final PropertyDescriptor [] descriptors =
                PropertyUtils.getPropertyDescriptors(object);
        
        // Collection to return
        final List properties = new ArrayList(descriptors.length);
        
        for (int i = 0; i < descriptors.length; i++) {
            final String name = descriptors[i].getName();
            if (PropertyUtils.isReadable(object, name)
                    && PropertyUtils.isWriteable(object, name)) {
                properties.add(new MagicProperty(parent, object, name));
            }
        }
        
        return properties;
    }
    
    /**
     * @param object The bean that contains the properties
     * @param parent The parent property
     * @return The class's accessible properties
     */
    /*public static final Collection old_getProperties(final Object object,
            final MagicProperty parent) {
        final Method [] methods = object.getClass().getMethods();
        
        final Hashtable getters = new Hashtable();
        final Hashtable setters = new Hashtable();
        
        String name = "";
        for (int i = 0; i < methods.length; i++) {
            name = methods[i].getName();
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
                debug("Found a property: " + key);
                // create a Collection of MagicProperties
                properties.add(new MagicProperty(parent, object,
                        (Method) getters.get(key), (Method) setters.get(key),
                        false));
            }
        }
        
        return properties;
    }*/

    /**
     * @param string The info text
     */
    public static final void info(Object string) {
        System.out.println("[INFO] " + string);
    }
    private static final boolean DEBUG = true;
    /**
     * @param string The debug text
     */
    public static final void debug(Object string) {
        if (DEBUG) {
            System.out.println("[DEBUG] " + string);
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

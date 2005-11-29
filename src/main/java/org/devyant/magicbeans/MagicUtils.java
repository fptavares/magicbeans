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

    public static final boolean mayBeIsolated(MagicComponent component) {
        return !cannotStandalone(component);
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
    public static final Collection<MagicProperty> getProperties(
            final Object object, final MagicProperty parent)
            throws MagicException {
        final String order =
            parent.getConfiguration().get(MagicConfiguration.GUI_ORDER_KEY);
        
        if (XML_VALUE.equals(order)) {
            try {
                return getPropertiesFromXML(object, parent);
            } catch (JaxenException e) {
                throw new MagicException("XPath error.", e); //$NON-NLS-1$
            }
        } else if (TYPE_VALUE.equals(order)) {
            final List<MagicProperty> properties = getAllProperties(object, parent);
            // sort the properties by their type
            Collections.sort(properties, new Comparator<MagicProperty>() {
                public int compare(final MagicProperty o1,
                        final MagicProperty o2) {
                    try {
                        return o1.getType().getName().compareTo(
                                o2.getType().getName());
                    } catch (MagicException e) {
                        return 0;
                    }
                    
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
            final List<MagicProperty> properties = getAllProperties(object, parent);
            // sort the properties by their type
            Collections.sort(properties, comparator);
            // return the sorted collection
            return properties;
        }
    }

    /**
     * Helper method.
     */
    private static List<MagicProperty> getAllProperties(final Object object,
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
    private static Collection<MagicProperty> getPropertiesFromXML(final Object object,
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
    private static final List<MagicProperty> getMappedProperties(final Object object,
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
    private static final List<MagicProperty> getDeclaredProperties(final Object object,
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

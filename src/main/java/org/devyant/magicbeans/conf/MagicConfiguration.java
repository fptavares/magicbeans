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
package org.devyant.magicbeans.conf;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.utils.BeanPathToXPathConverter;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * MagicConfiguration is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 11/Jun/2005 2:06:33
 */
public class MagicConfiguration {
    /**
     * The INSTANTIATION_EXCEPTION_MESSAGE <code>String</code>.
     */
    private static final String INSTANTIATION_EXCEPTION_MESSAGE =
        "A problem occured while trying to instantiate the configured layout.";
    
    /**
     * The key's prefix.
     */
    protected static final String KEY_PREFIX = "magic.";
    
    /**
     * The CONFIGURATION_NODE <code>String</code>.
     */
    public static final String CONFIGURATION_NODE = "Configuration";
    
    /**
     * The configuration cache.
     */
    private static final ConfigurationCache cache;
    
    /**
     * A <code>DocumentBuilder</code> instance helper.
     */
    private static transient DocumentBuilder builder;
    
    /**
     * The DEFAULT_CONF <code>Configuration</code>.
     */
    private static Configuration DEFAULT_CONF = null;
    /**
     * The XML_DOC <code>Document</code>.
     */
    private static Document XML_DOC = null;
    /**
     * Initializes the default configuration.
     */
    static {
        // read the properties doc
        try {
            DEFAULT_CONF = new PropertiesConfiguration("magic.properties");
        } catch (ConfigurationException e) {
            MagicUtils.debug(e);
        }
        // read the XML doc
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            XML_DOC = builder.parse(MagicConfiguration.class.getResourceAsStream("/magic.xml"));
        } catch (ParserConfigurationException e) {
            MagicUtils.debug(e);
        } catch (SAXException e) {
            MagicUtils.debug(e);
        } catch (IOException e) {
            MagicUtils.debug(e);
        }
        // initialize cache map
        cache = new ConfigurationCache();
    }
    
    /**
     * Magic Beans configuration properties.
     */
    private Configuration conf = new CompositeConfiguration();
    
    
    /**
     * Creates a new <code>MagicConfiguration</code> instance.
     * @param property The property
     */
    public MagicConfiguration(final MagicProperty property) {
        
        final String range = getFromDefault(CONFIGURATION_KEY);
        
        /* specific */
        if (NORMAL_CONF_VALUE.equals(range)
                || COMPLETE_CONF_VALUE.equals(range)) {
            final Configuration specific = getSpecificConf(
                    property.getSuperBeanClassName(), property.getBeanPath());
            // if exists, add to this configuration
            if (specific != null) {
                ((CompositeConfiguration) this.conf).addConfiguration(specific);
            }
        }
        
        /* type configuration */
        if (COMPLETE_CONF_VALUE.equals(range)) {
            try {
                final Configuration type = getSpecificConf(
                        property.getType().getName(), "");
                // if exists, add to this configuration
                if (type != null) {
                    ((CompositeConfiguration) this.conf).addConfiguration(type);
                }
            } catch(MagicException e) {
            }
        }
        
        /* default */
        ((CompositeConfiguration) conf).addConfiguration(DEFAULT_CONF);
        
    }


    /**
     * @todo switch XPath implementation
     * @param className
     * @param beanPath
     */
    private Configuration getSpecificConf(
            final String className, final String beanPath) {
        // get cached configuration
        Configuration specific = cache.get(className, beanPath);
        MagicUtils.debug("Class name: " + className);
        MagicUtils.debug("Bean path:  " + beanPath);
        
        if (specific == null) { // retrieve configuration from xml file
            try {
                
                // get the specific configuration's node
                final String xpath =
                    BeanPathToXPathConverter.convert(className, beanPath)
                        + "/" + CONFIGURATION_NODE;
                MagicUtils.debug("XPath:      " + xpath);
                //final Node node = XPathAPI.selectSingleNode(XML_DOC, xpath);
                final XPath expression = new DOMXPath(xpath);
                final Node node = (Node) expression.selectSingleNode(XML_DOC);
                
                // if specific exists
                if (node != null) {
                    final Document document = builder.newDocument();
                    final Node newNode = document.importNode(node, true);
                    
                    // Insert the root element node
                    document.appendChild(newNode);
                    
                    // initialize the XML conf with the specific node
                    specific = new XMLConfiguration();
                    ((XMLConfiguration) specific).initProperties(document, true);
                    
                    // add configuration to cache
                    cache.put(className, beanPath, specific);
                }
                
            } catch (JaxenException e) {
                MagicUtils.debug(e);
            }
        } else {
            MagicUtils.debug("Using cached configuration.");
        }
        
        // return the specific configuration
        return specific;
    }
    
    
    /**
     * The getter method for the xML_DOC property.
     * @return The property's <code>MagicConfiguration</code> value
     */
    public static Document getXML_DOC() {
        return XML_DOC;
    }

    public static final int NORMAL = 1;
    public static final int DEFAULT = 2;
    public static final int SPECIAL = 3;

    /*
     * Get properties from the default configuration only.
     */
    public static final String getFromDefault(final String key) {
        return DEFAULT_CONF.getString(KEY_PREFIX + key);
    }
    public static final int getIntFromDefault(final String key) {
        return DEFAULT_CONF.getInt(KEY_PREFIX + key);
    }
    
    /*
     * Basic get properties.
     */
    public final String get(final String key) {
        return get(key, NORMAL);
    }
    public final int getInt(final String key) {
        return getInt(key, NORMAL);
    }
    public final boolean getBoolean(final String key) {
        return getBoolean(key, NORMAL);
    }
    public Class getClass(final String key)
            throws InvalidConfigurationException {
        return getClass(key, NORMAL);
    }
    public Object getClassInstance(final String key) throws MagicException {
        return getClassInstance(key, NORMAL);
    }
    
    /*
     * Get special properties (no prefix added).
     */
    public final String getSpecial(final String key) {
        return get(key, SPECIAL);
    }
    public final int getSpecialInt(final String key) {
        return getInt(key, SPECIAL);
    }
    public final boolean getSpecialBoolean(final String key) {
        return getBoolean(key, SPECIAL);
    }
    public final Object getSpecialClassInstance(final String key)
            throws MagicException {
        return getClassInstance(key, SPECIAL);
    }
    
    /*
     * Common method for property retrieval.
     */
    public final String get(final String key, final int configuration) {
        return conf.getString(computeKey(key, configuration));
    }
    public final int getInt(final String key, final int configuration) {
        return conf.getInt(computeKey(key, configuration));
    }
    public final boolean getBoolean(final String key, final int configuration) {
        return conf.getBoolean(computeKey(key, configuration));
    }
    public Class<?> getClass(final String key, final int configuration)
            throws InvalidConfigurationException {
        final String realKey = computeKey(key, configuration);
        final String name = conf.getString(realKey);
        if (name == null) {
            throw new InvalidConfigurationException(realKey, name);
        }
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new InvalidConfigurationException(realKey, name);
        }
    }
    public Object getClassInstance(final String key, final int configuration)
            throws MagicException {
        try {
            return getClass(key, configuration).newInstance();
        } catch (InstantiationException e) {
            throw new MagicException(INSTANTIATION_EXCEPTION_MESSAGE, e);
        } catch (IllegalAccessException e) {
            throw new MagicException(INSTANTIATION_EXCEPTION_MESSAGE, e);
        }
    }
    
    /**
     * Helper method. 
     */
    private static final String computeKey(
            final String key, final int configuration) {
        
        switch (configuration) {
            case NORMAL:
            case DEFAULT:
                return KEY_PREFIX + key;
            case SPECIAL:
                return key;
            default:
                return key;
        }
    }
    
    /*
     * DEFAULT PROPERTIES KEYS
     */

    private static final String CONFIGURATION_KEY = "configuration";

    public static final String GUI_TYPE_KEY = "gui.type";

    public static final String GUI_ORDER_KEY = "gui.order";
    
    public static final String GUI_ISOLATED_TYPE_KEY = "gui.isolated.type";

    public static final String GUI_COLLECTIONS_STYLE_KEY = "gui.collections.style";

    public static final String GUI_LAYOUT_PREFIX = "gui.";
    public static final String GUI_LAYOUT_SUFIX = ".layout";

    public static final String RESOURCES_FILE_KEY = "resources.file";
    public static final String RESOURCES_FILE = getFromDefault(RESOURCES_FILE_KEY);

    public static final String RESOURCES_PROPERTY_PREFIX_KEY = "resources.property.prefix";
    
    /*
     * DEFAULT PROPERTIES VALUES
     */

    static public final String AWT_VALUE = "awt";
    static public final String SWING_VALUE = "swing";

    static private final String COMPLETE_CONF_VALUE = "complete";
    static private final String NORMAL_CONF_VALUE = "normal";
    static private final String BASIC_CONF_VALUE = "basic";
    
    static public final String ISOLATED_TREE_VALUE = "tree";
    static public final String ISOLATED_TABBED_VALUE = "tabbed";
    static public final String ISOLATED_CHILD_VALUE = "child";
    
    
    /*
     * XML (SPECIAL) PROPERTIES KEYS
     */

    public static final String XML_VISIBLE = "visible";

    public static final String XML_NESTED = "nested";

    public static final String XML_COLLECTION_CLASS = "collection.class";
    
    /*
     * i18n
     */
    public static final MagicResources resources =
        new MagicResources(RESOURCES_FILE);
}

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
import org.apache.commons.lang.StringUtils;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
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
     * The CONFIGURATION_NODE <code>String</code>.
     */
    private static final String CONFIGURATION_NODE = "Configuration";

    /**
     * The CLASS_ATTR <code>String</code>.
     */
    private static final String CLASS_ATTR = "class";

    /**
     * The key's prefix.
     */
    protected static final String KEY_PREFIX = "magic.";
    
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
    }
    
    /**
     * Magic Beans configuration properties.
     */
    private Configuration conf = new CompositeConfiguration();
    
    
    /**
     * Creates a new <code>MagicConfiguration</code> instance.
     * @param className The class's fully qualified name
     * @param beanPath The path between properties.
     * <p>Example: <code>property1.property2.property3</code></p>
     */
    public MagicConfiguration(String className, String beanPath) {
        
        /* specific */
        final XMLConfiguration specificConf = new XMLConfiguration();
        try {
            
            // get the specific configuration's node
            String xpath = "/*/bean[@" + CLASS_ATTR + "=\"" + className + "\"]";
            if (!StringUtils.isBlank(beanPath)) {
                // replace '.' for '/'
                xpath += "/" + beanPath.replaceAll("\\.", "/");
            }
            xpath += "/" + CONFIGURATION_NODE;
            MagicUtils.debug("Bean path: " + beanPath);
            MagicUtils.debug("XPath:     " + xpath);
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
                specificConf.initProperties(document, true);
                ((CompositeConfiguration) conf).addConfiguration(specificConf);
            }
            
        } catch (JaxenException e) {
            MagicUtils.debug(e);
        }
        
        /* default */
        ((CompositeConfiguration) conf).addConfiguration(DEFAULT_CONF);
    }

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
        return conf.getString(KEY_PREFIX + key);
    }
    public final int getInt(final String key) {
        return conf.getInt(KEY_PREFIX + key);
    }
    public final boolean getBoolean(final String key) {
        return conf.getBoolean(KEY_PREFIX + key);
    }
    public Class getClass(final String key) throws InvalidConfigurationException {
        final String name = get(key);
        if (name == null) {
            throw new InvalidConfigurationException(key, name);
        }
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new InvalidConfigurationException(key, name);
        }
    }

    public Object getClassInstance(final String key) throws MagicException {
        try {
            return getClass(key).newInstance();
        } catch (InvalidConfigurationException e) {
            throw new MagicException(INSTANTIATION_EXCEPTION_MESSAGE, e);
        } catch (InstantiationException e) {
            throw new MagicException(INSTANTIATION_EXCEPTION_MESSAGE, e);
        } catch (IllegalAccessException e) {
            throw new MagicException(INSTANTIATION_EXCEPTION_MESSAGE, e);
        }
    }
    
    /*
     * Get special properties (no prefix added).
     */
    public final String getSpecial(final String key) {
        return conf.getString(key);
    }
    public final int getSpecialInt(final String key) {
        return conf.getInt(key);
    }
    public final boolean getSpecialBoolean(final String key) {
        return conf.getBoolean(key);
    }
    
    /*
     * DEFAULT PROPERTIES KEYS
     */

    public static final String GUI_TYPE_KEY = "gui.type";

    public static final String GUI_COLLECTIONS_STYLE_KEY = "gui.collections.style";

    public static final String GUI_LAYOUT_IMPL = "gui.layout.impl";

    public static final String GUI_AUX_UPDATE_ACTION = "gui.aux.update.action";

    public static final String RESOURCES_FILE_KEY = "resources.file";
    public static final String RESOURCES_FILE = getFromDefault(RESOURCES_FILE_KEY);

    public static final String RESOURCES_PROPERTY_PREFIX_KEY = "resources.property.prefix";
    
    /*
     * XML (SPECIAL) PROPERTIES KEYS
     */

    public static final String XML_VISIBLE = "visible";

    public static final String XML_NESTED = "nested";
    
    /*
     * i18n
     */
    public static final MagicResources resources =
        new MagicResources(RESOURCES_FILE);
}

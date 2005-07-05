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
package org.devyant.magicbeans.i18n;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.MagicConfiguration;

/**
 * MagicResources is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Abr/2005 19:28:52
 * @see java.util.ResourceBundle
 */
public class MagicResources {
    /**
     * The base prefix.
     */
    public static final String BASE_KEY_PREFIX = "magicbeans.";
    /**
     * The MESSAGE_KEY_PREFIX.
     */
    public static final String MESSAGE_KEY_PREFIX = BASE_KEY_PREFIX + "message.";
    /**
     * The PROPERTY_KEY_PREFIX.
     * @todo could this become non-static?
     */
    public static final String PROPERTY_KEY_PREFIX =
        MagicConfiguration.getFromDefault(
                MagicConfiguration.RESOURCES_PROPERTY_PREFIX_KEY);
    
    // messages
    
    public static final String MESSAGE_UPDATED = "updated";

    // strings
    
    public static final String STRING_TITLE = "title";
    
    public static final String STRING_OKBUTTON = "button.ok";
    public static final String STRING_ADDBUTTON = "button.add";
    public static final String STRING_EDITBUTTON = "button.edit";
    public static final String STRING_REMOVEBUTTON = "button.remove";
    
    /**
     * The resources to use.
     */
    private final ResourceBundle bundle;
    
    
    /**
     * Creates a new <code>MagicResources</code> instance.
     * @param bundle The resource bundle
     */
    public MagicResources(final ResourceBundle bundle) {
        this.bundle = bundle;
    }
    
    /**
     * Creates a new <code>MagicResources</code> instance.
     * @param bundle The resources file
     */
    public MagicResources(final String bundle) {
        this.bundle = ResourceBundle.getBundle(bundle);
    }
    
    /**
     * @param name The message's name
     * @return The message
     */
    public final String getMessage(final String name) {
        return bundle.getString(MESSAGE_KEY_PREFIX + name);
    }
    
    /**
     * @param property The property
     * @return The property's "display name"
     */
    public final String getProperty(final MagicProperty property) {
        try {
            return bundle.getString(PROPERTY_KEY_PREFIX + property.getBeanPath());
        } catch (MissingResourceException e) {
            return StringUtils.capitalize(property.getName());
        }
    }
    
    /**
     * @param name The string's name
     * @return The resource
     */
    public final String getString(final String name) {
        return bundle.getString(BASE_KEY_PREFIX + name);
    }
}

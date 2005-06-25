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
package org.devyant.magicbeans.i18n;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
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
     */
    public static final String PROPERTY_KEY_PREFIX = MagicConfiguration.RESOURCES_PROPERTY_PREFIX;
    
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
     * @param name The property's name
     * @return The resource
     */
    public final String getProperty(final String name) {
        try {
            return bundle.getString(PROPERTY_KEY_PREFIX + name);
        } catch (MissingResourceException e) {
            return StringUtils.capitalize(name);
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

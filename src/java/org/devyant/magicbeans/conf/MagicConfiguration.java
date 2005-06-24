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
package org.devyant.magicbeans.conf;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.devyant.magicbeans.MagicUtils;

/**
 * MagicConfiguration is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 11/Jun/2005 2:06:33
 */
public abstract class MagicConfiguration {
    /**
     * The key's prefix.
     */
    protected static final String KEY_PREFIX = "magic.";
    
    /**
     * Magic Beans configuration properties.
     */
    private static Configuration conf = null;
    static {
        try {
            conf = new PropertiesConfiguration("magic.properties");
            /*conf.load(MagicConfiguration.class.getClassLoader()
                    .getResource().openStream());*/
        } catch (ConfigurationException e) {
            MagicUtils.debug(e);
        }
    }
    private static final String get(String key) {
        return conf.getString(KEY_PREFIX + key);
    }
    private static final int getInt(String key) {
        return conf.getInt(KEY_PREFIX + key);
    }
    
    /*
     * PUBLIC CONSTANTS
     */

    public static final String GUI_TYPE_KEY = "gui.type";
    public static final String GUI_TYPE = get(GUI_TYPE_KEY);

    public static final String GUI_STACKING_TOLERANCE_KEY = "gui.stacking.tolerance";
    public static final int GUI_STACKING_TOLERANCE = getInt(GUI_STACKING_TOLERANCE_KEY);

    public static final String GUI_COLLECTIONS_STYLE_KEY = "gui.collections.style";
    public static final String GUI_COLLECTIONS_STYLE = get(GUI_COLLECTIONS_STYLE_KEY);

    public static final String RESOURCES_PROPERTY_PREFIX_KEY = "resources.property.prefix";
    public static final String RESOURCES_PROPERTY_PREFIX = get(RESOURCES_PROPERTY_PREFIX_KEY);
}

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
package org.devyant.magicbeans.utils.containers;

import java.awt.Component;
import java.awt.Container;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicUtils;

/**
 * DefaultContainerBehaviour is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 17/Jun/2005 1:28:17
 */
public abstract class DefaultContainerBehaviour {
    /**
     * This is a utility class, therefor it should not be instatiated.
     */
    protected DefaultContainerBehaviour() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Common implementation for all <code>MagicContainer</code>s
     * {@link org.devyant.magicbeans.MagicComponent#update()} method.
     * @param container
     * @see org.devyant.magicbeans.MagicComponent
     */
    public static final void doUpdateForContainer(Container container) {
        final Component [] components = container.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof MagicComponent) {
                MagicUtils.debug(components[i].getClass());
                try {
                	// do layer-by-layer update
                    ((MagicComponent) components[i]).update();
                    // debug
                    MagicUtils.debug(
                            ((MagicComponent) components[i]).getProperty().getName()
                            + ": "
                            + ((MagicComponent) components[i]).getProperty().get()
                            );
                } catch(Exception e) {
                	MagicUtils.debug(e);
                }
            }
        }
    }
}

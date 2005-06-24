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

import org.devyant.magicbeans.beans.MagicProperty;

/**
 * MagicComponent is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @since 18/Abr/2005 20:56:13
 */
public interface MagicComponent {
    /**
     * Updates the original data container.
     * @throws Exception
     */
    public void update() throws Exception;
    /**
     * Binds the component with the object's property.
     * @param property The property
     * @throws Exception Something went wrong
     */
    public void bindTo(final MagicProperty property) throws Exception;
    /**
     * Getter method for the property.
     * @return The <code>MagicProperty</code> instance binded to this component
     */
    public MagicProperty getProperty();
}

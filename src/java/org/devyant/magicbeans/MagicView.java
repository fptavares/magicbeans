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

import java.awt.Container;

import org.devyant.magicbeans.swing.listeners.UpdateButtonActionHandler;

/**
 * MagicView is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 17/Jun/2005 3:35:04
 */
public interface MagicView extends MagicContainer, UpdateButtonActionHandler {
    /**
     * Performs last minute UI settings and returns the the container,
     * usually it self.
     * @return A <code>Container</code> containing the forms
     */
    public Container render();
}

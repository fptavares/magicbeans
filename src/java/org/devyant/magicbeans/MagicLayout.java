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

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;

/**
 * MagicLayout is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jun 24, 2005 9:52:07 PM
 */
public interface MagicLayout extends LayoutManager {
    /**
     * Add a pair of components (label + MagicComponent)
     * that represent a property.
     * @param container The container
     * @param label The label component
     * @param component The magic component
     */
    public void addLabeledComponent(final Container container,
            final Component label, final Component component);
    /**
     * Add a single magic component that represent a property.
     * @param container The container
     * @param component The magic component
     */
    public void addUnlabeledComponent(final Container container,
            final Component component);
    /**
     * Add a component which is controlled by a series of components.
     * @param container The container
     * @param component The magic component
     * @param controllers The control components
     */
    public void addControledComponent(final Container container,
            final Component component, final Component [] controllers);
    /**
     * Add the update button.
     * @param container The container
     * @param button The button component
     */
    public void addButton(final Container container, final Component button);
    /**
     * Add the status bar.
     * @param container The container
     * @param status The status bar
     */
    public void addStatus(final Container container, final Component status);
}

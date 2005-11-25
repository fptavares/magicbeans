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
package org.devyant.magicbeans.ui;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.exceptions.MagicException;

/**
 * AbstractUIFactory is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Sep 15, 2005 4:29:54 PM
 */
public interface UIFactory {

    /**
     * This method returns a nested component for the specified property.
     * @param property The property
     * @return A renderer for the property
     * @throws MagicException
     */
    public abstract MagicComponent getNestedComponentInstanceFor(
            final MagicProperty property) throws MagicException;

    /**
     * This method returns a nested component for the specified property.
     * @param property The property
     * @return A renderer for the property
     * @throws MagicException
     */
    public abstract MagicComponent getBaseComponentInstanceFor(
            final MagicProperty property) throws MagicException;

    /**
     * @return An isolated component if the property is mapped to an
     *  instance of a MagicContainer. If not, the normal component is returned.
     * @throws MagicException
     *  {@link #getNestedComponentInstanceFor(MagicProperty)}
     */
    public abstract MagicComponent getIsolatedComponentFor(
            final MagicProperty property) throws MagicException;
    
    /**
     * Create and return an apropriate UI component for the OK button.
     * @param text The button's label
     * @return A button component instance
     */
    public Object createButton(final String text);
    /**
     * Create a label component showing the specified <code>String</code>.
     * @param string The label's <code>String</code>
     * @return The toolkit specific component
     */
    public Object createLabel(final String string);
    /**
     * Create and return an apropriate UI component for the status label.
     * @return A status label instance
     */
    public Object createStatus();

}
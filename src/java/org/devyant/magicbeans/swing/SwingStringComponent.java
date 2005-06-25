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
package org.devyant.magicbeans.swing;

import java.awt.Dimension;
import javax.swing.JTextField;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.MagicConfiguration;

/**
 * SwingStringComponent is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ ($Author$)
 * @since 18/Abr/2005 19:36:41
 */
public class SwingStringComponent extends JTextField implements MagicComponent {
    /**
     * The property to bind to.
     */
    private MagicProperty property;
    
    /**
     * @see org.devyant.magicbeans.MagicComponent#update()
     */
    public void update() throws Exception {
        property.set(getText());
    }
    /**
     * @see org.devyant.magicbeans.MagicComponent#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    public void bindTo(MagicProperty property) throws Exception {
        this.property = property;
        // fill with property's value
        final Object value = this.property.get();
        if (value != null) {
            setText(value.toString());
        }
        // ui stuff
        setMinimumSize(MagicConfiguration.GUI_COMPONENT_MINIMUM_SIZE);
    }
    /**
     * @see org.devyant.magicbeans.MagicComponent#getProperty()
     */
    public final MagicProperty getProperty() {
        return property;
    }
}

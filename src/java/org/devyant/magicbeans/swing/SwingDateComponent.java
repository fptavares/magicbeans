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

import java.util.Date;

import net.sf.nachocalendar.components.DateField;
import net.sf.nachocalendar.components.DefaultDayRenderer;
import net.sf.nachocalendar.components.DefaultHeaderRenderer;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.generalizers.DateGeneralizer;

/**
 * SwingDateComponent is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 11/Jun/2005 5:01:43
 */
public class SwingDateComponent extends DateField implements MagicComponent {
    /**
     * The property to bind to.
     */
    private MagicProperty property;
    /**
     * The <code>DateGeneralizer</code>.
     */
    private DateGeneralizer generalizer;

    /**
     * Creates a new <code>SwingDateComponent</code> instance.
     * @param generalizer The generalizer instance
     */
    public SwingDateComponent(DateGeneralizer generalizer) {
        this.generalizer = generalizer;
        // replace nachocalendar factory's configure method
        setRenderer(new DefaultDayRenderer());
        setHeaderRenderer(new DefaultHeaderRenderer());
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#update()
     */
    public void update() throws Exception {
        this.generalizer.setValue(this.property.get(), (Date) getValue());
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    public void bindTo(MagicProperty property) throws Exception {
        this.property = property;
        // fill with property's value
        final Object value = this.property.get();
        if (value != null)
            setValue(this.generalizer.getValue(value));
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#getProperty()
     */
    public MagicProperty getProperty() {
        return property;
    }

}

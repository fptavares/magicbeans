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

import javax.swing.border.TitledBorder;

import org.devyant.magicbeans.utils.components.UnlabeledComponent;

/**
 * SwingNestedContainer is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 12/Jun/2005 6:54:31
 */
public class SwingNestedContainer extends SwingContainer implements UnlabeledComponent {
    /**
     * @see org.devyant.magicbeans.utils.components.UnlabeledComponent#setTitle(java.lang.String)
     */
    public void setTitle(String title) {
        setBorder(new TitledBorder(null, title,
                TitledBorder.LEFT, TitledBorder.TOP));
    }      
}

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
package org.devyant.magicbeans.swing.utils;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;

/**
 * BasicDialog is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Jun/2005 4:02:55
 */
public class BasicDialog extends AbstractDialog {

    /**
     * Creates a new <code>BasicDialog</code> instance.
     * @param bean
     * @param parent
     * @param title
     * @param modal
     * @throws Exception
     */
    public BasicDialog(Container container, Dialog parent, String title,
            boolean modal) throws Exception {
        super(container, parent, title, modal);
    }

    /**
     * Creates a new <code>BasicDialog</code> instance.
     * @param bean
     * @param parent
     * @param title
     * @param modal
     * @throws Exception
     */
    public BasicDialog(Container container, Frame parent, String title,
            boolean modal) throws Exception {
        super(container, parent, title, modal);
    }

    /**
     * Does nothing.
     * @see org.devyant.magicbeans.swing.utils.AbstractDialog#closeWindowEvent()
     */
    public void closeWindowEvent() {
    }

}

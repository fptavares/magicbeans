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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JDialog;

/**
 * AbstractDialog is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Jun/2005 3:49:34
 */
public abstract class AbstractDialog extends JDialog {

    /**
     * Creates a new <code>AbstractDialog</code> instance.
     * @param bean
     * @param parent
     * @param title
     * @param modal
     * @throws Exception
     */
    public AbstractDialog(final Container container, final Dialog parent,
            final String title, boolean modal) throws Exception {
        super(parent, title, modal);
        init(container, parent, modal);
    }

    /**
     * Creates a new <code>AbstractDialog</code> instance.
     * @param bean
     * @param parent
     * @param title
     * @param modal
     * @throws Exception
     */
    public AbstractDialog(final Container container, final Frame parent,
            final String title, boolean modal) throws Exception {
        super(parent, title, modal);
        init(container, parent, modal);
    }

    /**
     * @param bean
     * @param parent
     * @param modal
     * @throws Exception
     */
    private void init(final Container container, final Component parent,
            boolean modal) throws Exception {
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeWindowEvent();
            }
        });
        setContentPane(container);
        pack();
        setLocationRelativeTo(parent);
        setModal(modal);
    }

    /**
     * An instance that mimics a subject's behaviour.
     */
    //private BasicSubjectBehaviour subjectBehaviour = new BasicSubjectBehaviour();
    
    public abstract void closeWindowEvent();
}

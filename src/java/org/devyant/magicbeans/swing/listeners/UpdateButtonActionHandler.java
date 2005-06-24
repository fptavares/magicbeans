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
package org.devyant.magicbeans.swing.listeners;

/**
 * UpdateButtonActionHandler is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 20/Jun/2005 1:21:16
 */
public interface UpdateButtonActionHandler {
    /**
     * Add a listener for the update button.
     */
    public void addUpdateButtonActionListener(final UpdateButtonActionListener l);
    /**
     * Reports an update button action.
     */
    public void fireUpdateButtonAction();
}

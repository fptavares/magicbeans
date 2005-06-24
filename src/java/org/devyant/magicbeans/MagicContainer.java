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

import org.devyant.magicbeans.i18n.MagicResources;

/**
 * MagicContainer is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 11/Jun/2005 1:56:35
 */
public interface MagicContainer extends MagicComponent {
    /**
     * Set the resource bundle which contains the messages for the form labels.
     * @param resources The <code>MagicResources</code> to use.
     */
    public void setResources(MagicResources resources);
}

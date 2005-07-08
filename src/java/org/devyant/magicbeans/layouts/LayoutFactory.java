/*
 * Magic Beans: a library for GUI generation and component-bean mapping.
 * Copyright (C) 2005  Filipe Tavares
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * devYant, devyant@devyant.org
 * Rua Simao Bolivar 203 6C, 4470-214 Maia, Portugal.
 *
 */
package org.devyant.magicbeans.layouts;

import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;

/**
 * LayoutFactory is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 7, 2005 7:13:06 PM
 */
public class LayoutFactory {
    /**
     * Instantiates the appropriate layout using the given configuration.
     * @param conf The configuration
     * @return The layout instance
     * @throws MagicException Class not found or unable to instantiate it
     */
    public static final MagicLayout createLayout(final MagicConfiguration conf)
            throws MagicException {
        return (MagicLayout) conf.getClassInstance(
                MagicConfiguration.GUI_LAYOUT_IMPL);
    }
}
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
package org.devyant.magicbeans.exceptions;

/**
 * This is the base exception for all exceptions thrown by Magic Beans.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 4, 2005 11:04:39 PM
 */
public class MagicException extends Exception {
    /**
     * Creates a new <code>MagicException</code> instance.
     * @param s The message
     */
    public MagicException(final String s) {
        super(s);
    }
    
    /**
     * Creates a new <code>MagicException</code> instance.
     * @param s The message
     * @param cause The cause exception
     */
    public MagicException(final String s, final Throwable cause) {
        super(s, cause);
    }

    /**
     * Creates a new <code>MagicException</code> instance.
     * @param cause The cause exception
     */
    public MagicException(Throwable cause) {
        super(cause);
    }
    
}

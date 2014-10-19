package org.theeuropeanlibrary.maia.common.converter;

/**
 * This exception is thrown by all public methods in the {@link Converter}.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class ConverterException extends Exception {

    /**
     * For inheritance reasons, pipes through to the super constructor.
     *
     * @param message description of the error
     */
    public ConverterException(String message) {
        super(message);
    }

    /**
     * For inheritance reasons, pipes through to the super constructor.
     *
     * @param message description of the error
     * @param cause root cause of the error
     */
    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}

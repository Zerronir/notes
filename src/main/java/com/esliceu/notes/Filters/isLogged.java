package com.esliceu.notes.Filters;

import javax.servlet.annotation.WebFilter;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@WebFilter(urlPatterns = {"/*"})
public class isLogged implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}

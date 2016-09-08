package com.tsystems.si.aviation.imf.messageHandle.handles.tools;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {
	private AtomicInteger    i         = new AtomicInteger(1);

    private static final int MAX_VALUE = 999999999;
    
    public String generateNextNumber() {
        if (i.get() >= MAX_VALUE) {
            i.set(1);
        } 
            return i.incrementAndGet() + "";
       
    }
}

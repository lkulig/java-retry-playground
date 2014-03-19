package com.lkulig.retry;

import static org.mockito.Mockito.doThrow;
import org.mockito.Spy;
import org.mockito.stubbing.Stubber;
import com.lkulig.retry.service.FabulousBusinessService;

public abstract class AbstractUnitTest {

    @Spy
    protected FabulousBusinessService fabulousBusinessService = new FabulousBusinessService();

    protected void throwIllegalArgumentExceptionTimes(int numberOfThrows) {
        int i = 1;
        Stubber stubber = doThrow(new IllegalArgumentException());
        do {
            stubber = stubber.doThrow(new IllegalArgumentException());
            i++;
        } while (i < numberOfThrows);
        stubber.doCallRealMethod().when(fabulousBusinessService).superbBusinessOperation();
    }
}

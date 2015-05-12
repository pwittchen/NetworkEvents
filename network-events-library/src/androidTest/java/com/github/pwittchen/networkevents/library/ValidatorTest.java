package com.github.pwittchen.networkevents.library;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        validator = new Validator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNotNullShouldThrowAnExceptionWhenObjectIsNull() {
        // given
        Object object = null;

        // when
        validator.checkNotNull(object, "object == null");

        // then throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void checkNotSetShouldThrowAnExceptionWhenObjectIsNotNull() {
        // given
        Object object = new Object();

        // when
        validator.checkNotSet(object, "object is already set");

        // then throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void checkSetShouldThrowAnExceptionWhenObjectIsNull() {
        // given
        Object object = null;

        // when
        validator.checkSet(object, "object == null");

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkPositiveShouldThrowAnExceptionWhenValueIsNegative() {
        // given
        int value = -5;

        // when
        validator.checkPositive(value, "value has to be positive");

        // throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkPositiveShouldThrowAnExceptionWhenValueIsEqualToZero() {
        // given
        int value = 0;

        // when
        validator.checkPositive(value, "value has to be positive");

        // throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUrlShouldThrowAnExceptionWhenUrlHasNoProtocol() {
        // given
        String urlWithoutProtocol = "www.google.com";

        // when
        validator.checkUrl(urlWithoutProtocol, "invalid url");

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUrlShouldThrowAnExceptionWhenUrlHasInvalidProtocol() {
        // given
        String urlWithoutProtocol = "gth://www.google.com";

        // when
        validator.checkUrl(urlWithoutProtocol, "invalid url");

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUrlShouldThrowAnExceptionWhenUrlHasInvalidAddress() {
        // given
        String invalidAddress = "abds$#@%$#.cafafd0";

        // when
        validator.checkUrl(invalidAddress, "invalid url");

        // then throw an exception
    }
}

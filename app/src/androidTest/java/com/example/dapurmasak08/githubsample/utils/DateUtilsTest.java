package com.example.dapurmasak08.githubsample.utils;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DateUtilsTest {
    @Test
    public void format() {
        {
            String formattedDateString = DateUtils.format(null);
            Assert.assertEquals("", formattedDateString);
        }
        {
            String formattedDateString = DateUtils.format("");
            Assert.assertEquals("", formattedDateString);
        }
        {
            String formattedDateString = DateUtils.format("2015-03-22T15:15:14Z");
            Assert.assertEquals("Mar 22, 2015", formattedDateString);
        }
    }
}


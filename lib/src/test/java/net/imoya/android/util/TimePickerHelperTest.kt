package net.imoya.android.util

import android.os.Build
import android.widget.TimePicker
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class TimePickerHelperTest {
    private var mockTimePicker: TimePicker = Mockito.mock(TimePicker::class.java)

    @Before
    @Suppress("deprecation")
    fun beforeEvery() {
        Mockito.reset(mockTimePicker)
        Mockito.doReturn(1).`when`(mockTimePicker).currentHour
        Mockito.doReturn(11).`when`(mockTimePicker).currentMinute
        Mockito.doReturn(2).`when`(mockTimePicker).hour
        Mockito.doReturn(12).`when`(mockTimePicker).minute
    }

    @Test
    fun testGetHourCallingLegacy() {
        val sdkInt = Build.VERSION.SDK_INT

        val mock = Mockito.mock(TimePickerHelper::class.java)
        Mockito.`when`(mock.getHourLegacy()).thenReturn(1)
        Mockito.`when`(mock.getHourM()).thenReturn(2)
        Mockito.`when`(mock.getHour()).thenCallRealMethod()

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), 22)
        Assert.assertEquals(1, mock.getHour())
        Mockito.verify(mock, Mockito.times(1)).getHourLegacy()

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), sdkInt)
    }

    @Test
    fun testGetHourCallingM() {
        val sdkInt = Build.VERSION.SDK_INT

        val mock = Mockito.mock(TimePickerHelper::class.java)
        Mockito.`when`(mock.getHourLegacy()).thenReturn(1)
        Mockito.`when`(mock.getHourM()).thenReturn(2)
        Mockito.`when`(mock.getHour()).thenCallRealMethod()

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), 23)
        Assert.assertEquals(2, mock.getHour())
        Mockito.verify(mock, Mockito.times(1)).getHourM()

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), sdkInt)
    }

    @Test
    @SuppressWarnings("deprecation")
    fun testGetHourLegacy() {
        val helper = TimePickerHelper(mockTimePicker)
        Assert.assertEquals(1, helper.getHourLegacy())
    }

    @Test
    @SuppressWarnings("deprecation")
    fun testGetHourM() {
        val helper = TimePickerHelper(mockTimePicker)
        Assert.assertEquals(2, helper.getHourM())
    }

    @Test
    fun testGetMinuteCallingLegacy() {
        val sdkInt = Build.VERSION.SDK_INT

        val mock = Mockito.mock(TimePickerHelper::class.java)
        Mockito.`when`(mock.getMinuteLegacy()).thenReturn(11)
        Mockito.`when`(mock.getMinuteM()).thenReturn(12)
        Mockito.`when`(mock.getMinute()).thenCallRealMethod()

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), 22)
        Assert.assertEquals(11, mock.getMinute())
        Mockito.verify(mock, Mockito.times(1)).getMinuteLegacy()

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), sdkInt)
    }

    @Test
    fun testGetMinuteCallingM() {
        val sdkInt = Build.VERSION.SDK_INT

        val mock = Mockito.mock(TimePickerHelper::class.java)
        Mockito.`when`(mock.getMinuteLegacy()).thenReturn(11)
        Mockito.`when`(mock.getMinuteM()).thenReturn(12)
        Mockito.`when`(mock.getMinute()).thenCallRealMethod()

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), 23)
        Assert.assertEquals(12, mock.getMinute())
        Mockito.verify(mock, Mockito.times(1)).getMinuteM()

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), sdkInt)
    }

    @Test
    @SuppressWarnings("deprecation")
    fun testGetMinuteLegacy() {
        val helper = TimePickerHelper(mockTimePicker)
        Assert.assertEquals(11, helper.getMinuteLegacy())
    }

    @Test
    @SuppressWarnings("deprecation")
    fun testGetMinuteM() {
        val helper = TimePickerHelper(mockTimePicker)
        Assert.assertEquals(12, helper.getMinuteM())
    }

    @Test
    fun testSetHourAndMinuteCallingM() {
        val sdkInt = Build.VERSION.SDK_INT

        val mock = Mockito.mock(TimePickerHelper::class.java)
        Mockito.doNothing().`when`(mock).setHourAndMinuteLegacy(Mockito.anyInt(), Mockito.anyInt())
        Mockito.doNothing().`when`(mock).setHourAndMinuteM(Mockito.anyInt(), Mockito.anyInt())
        Mockito.`when`(mock.setHourAndMinute(Mockito.anyInt(), Mockito.anyInt())).thenCallRealMethod()

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), 23)
        mock.setHourAndMinute(0, 1)
        Mockito.verify(mock, Mockito.times(1)).setHourAndMinuteM(Mockito.anyInt(), Mockito.anyInt())

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), sdkInt)
    }

    @Test
    fun testSetHourAndMinuteCallingLegacy() {
        val sdkInt = Build.VERSION.SDK_INT

        val mock = Mockito.mock(TimePickerHelper::class.java)
        Mockito.doNothing().`when`(mock).setHourAndMinuteLegacy(Mockito.anyInt(), Mockito.anyInt())
        Mockito.doNothing().`when`(mock).setHourAndMinuteM(Mockito.anyInt(), Mockito.anyInt())
        Mockito.`when`(mock.setHourAndMinute(Mockito.anyInt(), Mockito.anyInt())).thenCallRealMethod()

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), 22)
        mock.setHourAndMinute(2, 3)
        Mockito.verify(mock, Mockito.times(1)).setHourAndMinuteLegacy(Mockito.anyInt(), Mockito.anyInt())

        setStaticField(Build.VERSION::class.java.getDeclaredField("SDK_INT"), sdkInt)
    }

    @Test
    @Suppress("deprecation")
    fun testSetHourAndMinuteLegacy() {
        TimePickerHelper(mockTimePicker).setHourAndMinuteLegacy(1, 11)
        Mockito.verify(mockTimePicker).currentHour = 1
        Mockito.verify(mockTimePicker).currentMinute = 11
    }

    @Test
    fun testSetHourAndMinuteM() {
        TimePickerHelper(mockTimePicker).setHourAndMinuteM(2, 12)
        Mockito.verify(mockTimePicker).hour = 2
        Mockito.verify(mockTimePicker).minute = 12
    }

    @Throws(Exception::class)
    fun setStaticField(field: Field, newValue: Any?) {
        field.isAccessible = true
        val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, newValue)
    }
}
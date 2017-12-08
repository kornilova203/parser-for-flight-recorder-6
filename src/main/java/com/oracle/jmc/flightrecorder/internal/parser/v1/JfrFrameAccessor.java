package com.oracle.jmc.flightrecorder.internal.parser.v1;

import com.oracle.jmc.common.IMemberAccessor;
import com.oracle.jmc.common.item.IItem;
import com.oracle.jmc.common.util.FormatToolkit;
import com.oracle.jmc.flightrecorder.internal.parser.v1.StructTypes.JfrFrame;
import com.oracle.jmc.flightrecorder.internal.parser.v1.StructTypes.JfrStackTrace;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is located in flightrecorder's
 * parser package to have access to some classes and fields
 */
public class JfrFrameAccessor {
    /**
     * A3_2 object allows us to assess stacks (field name value 2) in Item5 objects
     * (Item5 class extends Item3 class)
     */
    private IMemberAccessor<Object, IItem> A3_2;

    public JfrFrameAccessor() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> itemBuilderClass = Class.forName("com.oracle.jmc.flightrecorder.internal.parser.ItemBuilder");
        Field f = itemBuilderClass.getDeclaredField("A3_2");
        f.setAccessible(true);
        //noinspection unchecked
        A3_2 = (IMemberAccessor<Object, IItem>) f.get(null);
    }

    public String getStack(IItem iItem) {
        JfrStackTrace member = (JfrStackTrace) A3_2.getMember(iItem);
        Object[] frames = (Object[]) member.frames;
        List<String> methodCalls = new ArrayList<>(20);
        for (int i = frames.length - 1; i >= 0; i--) {
            JfrFrame frame = (JfrFrame) frames[i];
            methodCalls.add(FormatToolkit.getHumanReadable(frame.getMethod()));
        }
        return String.join(";", methodCalls);
    }
}

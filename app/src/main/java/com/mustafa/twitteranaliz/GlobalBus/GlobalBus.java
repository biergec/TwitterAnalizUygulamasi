package com.mustafa.twitteranaliz.GlobalBus;


import org.greenrobot.eventbus.EventBus;

public class GlobalBus {

    private static EventBus eventBus;

    public static EventBus getEventBus(){
        if (eventBus!=null)
            eventBus=EventBus.getDefault();
        return eventBus;
    }
}

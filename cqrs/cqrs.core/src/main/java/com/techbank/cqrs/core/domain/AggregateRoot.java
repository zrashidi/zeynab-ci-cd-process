package com.techbank.cqrs.core.domain;

import com.techbank.cqrs.core.event.BaseEvent;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {

    protected  String id;
    protected  int version=-1;

    private final List<BaseEvent> changes= new ArrayList<>();
    private final Logger   logger =Logger.getLogger(AggregateRoot.class.getName());


    public String getId() {
        return id;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public   List<BaseEvent> getChanges() {
        return changes;
    }
public  void markChanngesAsComitted(){
        this.changes.clear();
}

       public  List<BaseEvent> getUnComittedChannges(){
        return this.changes;
    }


    protected void applayChanges(BaseEvent event,Boolean isNewEvent) {
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            logger.log(Level.WARNING, MessageFormat.format("The apply method was not found in the aggregate for {0}", event.getClass().getName()));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error applying event to aggregate", e);
        } finally {
            if (isNewEvent) {
                changes.add(event);


            }
        }
    }
    protected  void  raidEvent(BaseEvent event){
        applayChanges(event,true);

    }

    public   void  replayEvent(Iterable<BaseEvent> events){
        events.forEach(event -> applayChanges(event,false));

    }

    public Logger getLogger() {
        return logger;
    }
}

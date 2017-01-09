package controller;

import parcel.Parcel;
import parcel.SystemException;
import system.SystemParent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Willi on 12/30/2016.
 */
public class SubscriberManager{

    static HashMap<SystemParent, ArrayList<Subscriber>> subscriptions = new HashMap<>();
    static HashMap<SystemParent, Parcel> lastState = new HashMap<>();

    public static void subscribe(Subscriber s, SystemParent system){
        if(subscriptions.containsKey(system)){

        }
        else{
            subscriptions.put(system, new ArrayList<>());
        }
        subscriptions.get(system).add(s);
    }

    public static void checkUpdate(SystemParent system){
        if(subscriptions.containsKey(system)){
            try {
                Parcel newState =system.process(Parcel.GET_PARCEL("","state")).getParcel("payload");
                if(!newState.equals(lastState.get(system))){
                    System.out.println("System Alert!!!\n" + newState.toString());
                    for(Subscriber s : subscriptions.get(system)){
                        s.subscriptionAlert(newState);
                    }
                    lastState.put(system,newState);
                }

            } catch (SystemException e) {
               // e.printStackTrace();
            }
        }
    }
    public static void register(SystemParent system){
        subscriptions.put(system, new ArrayList<>());
    }


}
